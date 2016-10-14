package com.frame.member.activity;

import java.io.File;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Utils.MediaUtils;
import com.frame.member.Utils.ThreadTask;
import com.frame.member.widget.ProgressWheel;
import com.frame.member.widget.TextureVideoView.OnPlayStateListener;
import com.frame.member.widget.VideoSelectionView;
import com.frame.member.widget.VideoSelectionView.OnBackgroundColorListener;
import com.frame.member.widget.VideoSelectionView.OnSeekBarChangeListener;
import com.frame.member.widget.VideoSelectionView.OnSwich60sListener;
import com.frame.member.widget.VideoSelectionView.OnVideoChangeScaleTypeListener;
import com.frame.member.widget.VideoViewTouch;
import com.yixia.camera.demo.log.Logger;
import com.yixia.camera.demo.ui.record.helper.RecorderHelper;
import com.yixia.videoeditor.adapter.UtilityAdapter;
import com.yixia.weibo.sdk.FFMpegUtils;
import com.yixia.weibo.sdk.api.HttpRequest;
import com.yixia.weibo.sdk.model.MediaObject;
import com.yixia.weibo.sdk.util.DeviceUtils;
import com.yixia.weibo.sdk.util.FileUtils;
import com.yixia.weibo.sdk.util.Log;
import com.yixia.weibo.sdk.util.StringUtils;
import com.yixia.weibo.sdk.util.ToastUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ImportVideoActivity extends Activity implements OnPreparedListener, OnPlayStateListener, OnInfoListener,
		OnVideoSizeChangedListener, OnErrorListener, OnSeekCompleteListener, OnSeekBarChangeListener,
		OnSwich60sListener, OnBackgroundColorListener, OnVideoChangeScaleTypeListener {

	/** 显示正在加载 */
	private View mVideoLoading;
	/** 播放控件 */
	private VideoViewTouch mVideoView;
	/** 显示播放 */
	private ImageView mPlayController;
	/** 视频区域选择 */
	private VideoSelectionView mVideoSelection;
	/** 操作提示文字 */
	private LinearLayout mPreviewLinearLayout;
	/** 屏幕的宽度 */
	/** 播放路径 */
	private String mSourcePath;
	protected ProgressWheel mProgressWheel;
	/** 中间画面拖动提示 */
	// private boolean mAreaTips;
	private static final String DESKTOP_USERAGENT = "Mozilla/5.0 (X11; "
			+ "Linux x86_64) AppleWebKit/534.24 (KHTML, like Gecko) " + "Chrome/11.0.696.34 Safari/534.24";
	private boolean mIsFitCenter, mIsWhiteBackground;
	protected MediaObject mMediaObject;
	/** 视频旋转角度 */
	private int mVideoRotation = 0;
	/** 视频临时目录 */
	private String mTargetPath;
	/** 预先裁剪是否完成 */

	private Button btn_cancle_select_video, btn_ok_select_video;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_import_video);
		TextView tv_title = ((TextView) findViewById(R.id.tv_title));
		tv_title.setText("编辑视频");
		tv_title.setTextColor(Color.WHITE);
		initIntent();
		if (mMediaObject == null) {
			String dirName = System.currentTimeMillis() + "";
			String directory = AppConstants.CLIP_VIDEO_CACHE;
			if (StringUtils.isNotEmpty(mTargetPath)) {
				File f = new File(mTargetPath);
				if (!f.exists()) {
					f.mkdirs();
				}
				dirName = f.getName();
				directory = f.getParent() + "/";
			}
			mTargetPath = directory + dirName;
			mMediaObject = new MediaObject(directory, dirName, RecorderHelper.getVideoBitrate(),
					MediaObject.MEDIA_PART_TYPE_IMPORT_VIDEO);
		}
		initView();
		registeBroadCastReceiver();
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
		}
	};

	private void registeBroadCastReceiver() {
		registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_ACT_PUB_SUCC));
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	private void initIntent() {
		mVideoRotation = getIntent().getIntExtra("orientation", 0);
		/** 存储 */
		mTargetPath = getIntent().getStringExtra("target");
	}

	private void initView() {

		mVideoLoading = findViewById(R.id.video_loading);
		mVideoView = (VideoViewTouch) findViewById(R.id.preview);
		mPreviewLinearLayout = (LinearLayout) mVideoView.getParent();
		mPlayController = (ImageView) findViewById(R.id.play_controller);
		mVideoSelection = (VideoSelectionView) findViewById(R.id.video_selection_view);

		btn_cancle_select_video = (Button) findViewById(R.id.btn_cancle_select_video);
		btn_ok_select_video = (Button) findViewById(R.id.btn_ok_select_video);
		btn_cancle_select_video.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btn_ok_select_video.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startEncoding();
			}
		});

		mVideoView.setOnPreparedListener(this);
		mVideoView.setOnPlayStateListener(this);
		mVideoView.setOnTouchEventListener(mOnVideoTouchListener);
		mVideoView.setOnInfoListener(this);
		mVideoView.setOnVideoSizeChangedListener(this);
		mVideoView.setOnErrorListener(this);
		mVideoView.setOnSeekCompleteListener(this);

		mVideoSelection.setOnSeekBarChangeListener(this);
		mVideoSelection.setOnSwich60sListener(this);
		mVideoSelection.setOnBackgroundColorListener(this);
		mVideoSelection.setOnVideoChangeScaleTypeListener(this);

		initSurfaceView();

		parseIntentUrl(getIntent());
	}

	private void initSurfaceView() {
		int w = DeviceUtils.getScreenWidth(this);// 屏幕宽度

		// 宽高一致
		View preview_layout = findViewById(R.id.preview_layout);
		LinearLayout.LayoutParams mParams = (LinearLayout.LayoutParams) preview_layout.getLayoutParams();
		mParams.height = w;
		preview_layout.setVisibility(View.VISIBLE);

	}

	/**
	 * 解析url
	 * 
	 * @param intent
	 * @return -1 解析失败 0 正在解析 1解析成功
	 */
	private void parseIntentUrl(Intent intent) {
		if (intent != null) {
			try {
				mSourcePath = intent.getStringExtra("source");
				if (StringUtils.isEmpty(mSourcePath)) {
					Uri uri = intent.getData();
					if (uri == null) {
						Bundle b = intent.getExtras();
						Object o = b.get(Intent.EXTRA_STREAM);
						uri = Uri.parse(o.toString());
					}
					if (uri != null) {
						if (uri.getScheme().startsWith("file")) {
							mSourcePath = uri.toString();
						} else {
							ContentResolver contentResolver = getContentResolver();
							Cursor cursor = contentResolver.query(uri, null, null, null, null);
							cursor.moveToFirst();
							if (cursor != null) {
								cursor.moveToFirst();
								int index2 = cursor.getColumnIndex("mime_type");
								String type = cursor.getString(index2);
								if (type != null && type.indexOf("video") != -1) {

								} else {
									return;
								}
								int index = cursor.getColumnIndex("_data");
								if (index > -1) {
									if (cursor.getString(index) != null) {
										mSourcePath = cursor.getString(index);
									}
								} else {

								}
								cursor.close();
							}
						}
					}
				}
			} catch (Exception e) {
			}
		}
		// 本地地址检测是否存在
		if (StringUtils.isEmpty(mSourcePath) || (MediaUtils.isNative(mSourcePath) && !new File(mSourcePath).exists())) {
			ToastUtils.showToast(ImportVideoActivity.this, "视频不存在");
			finish();
		} else {
			if (getIntent().getBooleanExtra("parse", false)) {
				parseShortVideo(mSourcePath);
			} else {
				mVideoView.setVideoPath(mSourcePath);
			}
		}
	}

	/** 解析短视频 */
	private void parseShortVideo(final String url) {
		if (StringUtils.isNotEmpty(url)) {
			new ThreadTask<Void, Void, String>() {

				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					mVideoLoading.setVisibility(View.GONE);
					ProgressDialog dialog = showEncodingDialog("正在缓冲...");
					if (dialog != null) {
						if (mProgressWheel != null) {
							mProgressWheel.spin();
						}
						dialog.setCancelable(true);
						dialog.setOnCancelListener(new OnCancelListener() {

							@Override
							public void onCancel(DialogInterface dialog) {
								ToastUtils.showToast(ImportVideoActivity.this, "导入视频失败");
								finish();
							}
						});
					}
				}

				@Override
				protected String doInBackground(Void... params) {
					if (url.indexOf("meipai.com/media/") > -1) {
						String html = getRequestString(url);
						if (StringUtils.isNotEmpty(html)) {
							String video = StringUtils.substring(html, "data-video=\"", "\"");
							if (StringUtils.isNotEmpty(video) && video.endsWith(".mp4")) {
								return video;
							}
						}
					}

					return null;
				}

				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					if (mProgressWheel != null) {
						mProgressWheel.stopSpinning();
					}
					hideProgress();
					if (!isFinishing() && StringUtils.isNotEmpty(result)) {
						mVideoLoading.setVisibility(View.VISIBLE);
						mSourcePath = result;
						mVideoView.setVideoPath(mSourcePath);
					} else {
						ToastUtils.showToast(ImportVideoActivity.this, "导入视频失败");
						finish();
					}
				}

			}.execute();
		}
	}

	public static String getRequestString(String url) {
		try {
			HttpRequest request = HttpRequest.get(url).acceptGzipEncoding().uncompress(true).trustAllCerts()
					.trustAllHosts().readTimeout(10000);
			request.userAgent(DESKTOP_USERAGENT);
			return request.body();
		} catch (Exception ex) {
			Logger.e(ex);
		} catch (OutOfMemoryError ex) {
			Logger.e(ex);
		}
		return "";
	}

	private VideoViewTouch.OnTouchEventListener mOnVideoTouchListener = new VideoViewTouch.OnTouchEventListener() {

		@Override
		public boolean onClick() {

			if (mVideoView.isPlaying()) {
				mVideoView.pauseClearDelayed();
			} else {
				mVideoView.start();
				mHandler.sendEmptyMessage(HANDLE_PROGRESS);
			}
			return true;
		}

		@Override
		public void onVideoViewDown() {
		}

		@Override
		public void onVideoViewUp() {

		}
	};

	protected ProgressDialog mProgressDialog;

	public ProgressDialog showProgress(String title, String message) {
		return showProgress(title, message, -1);
	}

	public ProgressDialog showProgress(String title, String message, int theme) {
		if (mProgressDialog == null) {
			if (theme > 0)
				mProgressDialog = new ProgressDialog(this, theme);
			else
				mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			mProgressDialog.setCanceledOnTouchOutside(false);// 不能取消
			mProgressDialog.setIndeterminate(true);// 设置进度条是否不明确
		}

		if (!StringUtils.isEmpty(title))
			mProgressDialog.setTitle(title);
		mProgressDialog.setMessage(message);
		mProgressDialog.show();
		return mProgressDialog;
	}

	public void hideProgress() {
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
	}

	public ProgressDialog showEncodingDialog(String message) {
		Logger.d("[RecordBaseActivity]showEncodingDialog...");
		ProgressDialog dialog = showProgress("", "");
		View convertView = LayoutInflater.from(this).inflate(R.layout.dialog_record_transcoding, null);
		// mProgressTextView = (TextView)
		// convertView.findViewById(android.R.id.message);
		mProgressWheel = (ProgressWheel) convertView.findViewById(R.id.progress);
		dialog.setContentView(convertView);
		if (StringUtils.isNotEmpty(message)) {
			mProgressWheel.setProgressEx(0);
		}
		// mProgressDialog.getWindow().setBackgroundDrawableResource(R.drawable.uploader_dialog_bg);
		dialog.setCancelable(false);
		return dialog;
	}

	/** 显示进度 */
	private static final int HANDLE_PROGRESS = 1;
	/** 选区延迟检测 */
	private static final int HANDLE_SEEKTO = 2;

	/**
	 * 重置时间前的开始时间（关键帧的问题，导致可能需要重新设置开始和结束时间）
	 */
	private int mPreChangedStartTime;
	/**
	 * 重置时间前的结束时间
	 */
	private int mPreChangedEndTime;
	/**
	 * 是否重置时间标记
	 */
	private boolean mIsChangeTime;

	private long lastPosition = 0;

	/**
	 * 更新进度线的位置
	 */
	private void setLinePosition() {
		if (mVideoView != null) {

			int startTime = mVideoSelection.getStartTime();
			int endTime = mVideoSelection.getEndTime();

			long position = mVideoView.getCurrentPosition();
			if (lastPosition != 0 && Math.abs(position - lastPosition) > 500) {

				mPreChangedStartTime = startTime;
				mPreChangedEndTime = endTime;

				endTime = (int) position + endTime - startTime;
				startTime = (int) position;
				mVideoSelection.setStartTime(startTime);
				mVideoSelection.setEndTime(endTime);

				mVideoSelection.setStartTime(startTime);
				mVideoSelection.setEndTime(endTime);

				mIsChangeTime = true;
			}
			lastPosition = position;

			if (mVideoSelection != null) {
				if (mVideoSelection.mVideoSelection != null) {
					mVideoSelection.mVideoSelection.setLinePosition(position, startTime, endTime);
				}
			}
		}
	}

	/** 视频暂停时 把进度线也隐藏 */
	private void clearLine() {
		if (mVideoSelection != null) {
			if (mVideoSelection.mVideoSelection != null) {
				mVideoSelection.mVideoSelection.clearLine();
			}
		}
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HANDLE_PROGRESS:
				if (mVideoView.isPlaying()) {
					// 播放到结束时间的位置 则暂停不要循环
					long position = mVideoView.getCurrentPosition();

					if ((position >= mVideoSelection.getEndTime()
							&& (lastPosition != 0 && Math.abs(position - lastPosition) < 500))
							|| position == mVideoView.getDuration()) {
						Logger.e("simon", "step1");
						if (mIsChangeTime) {
							Logger.e("simon", "当前重设的历史StartTime>>" + mPreChangedStartTime + ">>>当前记录的历史endTime>>>"
									+ mPreChangedEndTime);
							mVideoSelection.setStartTime(mPreChangedStartTime);
							mVideoSelection.setEndTime(mPreChangedEndTime);
							mIsChangeTime = false;
						}
						Logger.e("simon", "暂停了?position ::" + position + "endTime::" + mVideoSelection.getEndTime()
								+ "view.getDuration::" + mVideoView.getDuration());
						final int startTime = mVideoSelection.getStartTime();
						mVideoView.pauseClearDelayed();
						mVideoView.seekTo(startTime);
					} else {
						Logger.e("simon", "step2");
						setLinePosition();
						sendEmptyMessageDelayed(HANDLE_PROGRESS, 20);
					}
					setProgress();
				} else if (mVideoView.isPaused()) {
					Logger.e("simon", "step3");
					if (mIsChangeTime) {
						Logger.e("", "当前重设的历史StartTime>>" + mPreChangedStartTime + ">>>当前记录的历史endTime>>>"
								+ mPreChangedEndTime);
						mVideoSelection.setStartTime(mPreChangedStartTime);
						mVideoSelection.setEndTime(mPreChangedEndTime);
						mIsChangeTime = false;
					}
					final int startTime = mVideoSelection.getStartTime();
					mVideoView.seekTo(startTime);
					setProgress();
				}
				break;
			case HANDLE_SEEKTO:
				if (!isFinishing()) {
					final int startTime = mVideoSelection.getStartTime();
					// Log.e("simon","HANDLE_SEEKTO::StartTime>>"+startTime+">>>endTime>>>"+mVideoSelection.getEndTime());
					if (mVideoView.isPlaying())
						mVideoView.loopDelayed(startTime, mVideoSelection.getEndTime());
					else
						mVideoView.seekTo(startTime);
					setProgress();
				}
				break;
			}
			super.handleMessage(msg);
		}
	};

	private long setProgress() {
		// if (mVideoView == null)
		return 0;
	}

	private int scale;

	@Override
	public void onChanged(int scale) {
		this.scale = scale;
		setVideoMode(scale);
	}

	@Override
	public void onChanged(boolean isWhiteBackground) {
		if (isWhiteBackground) {
			mPreviewLinearLayout.setBackgroundColor(getResources().getColor(R.color.white));
			mIsWhiteBackground = true;
		} else {
			mIsWhiteBackground = false;
			mPreviewLinearLayout.setBackgroundColor(getResources().getColor(R.color.black));
		}
	}

	@Override
	public void onChanged() {
		if (mVideoView != null) {
			mVideoView.pauseClearDelayed();
			mVideoView.seekTo(0);
		}
	}

	@Override
	public void onProgressChanged() {
		if (mVideoView != null) {
			// hideFirstTips();
			if (mVideoView.isPlaying()) {
				mVideoView.pauseClearDelayed();
			}
			int startTime = mVideoSelection.getStartTime();

			mVideoView.seekTo(startTime);
			setProgress();
		}
	}

	@Override
	public void onProgressEnd() {
		if (mHandler.hasMessages(HANDLE_SEEKTO))
			mHandler.removeMessages(HANDLE_SEEKTO);
		mHandler.sendEmptyMessageDelayed(HANDLE_SEEKTO, 20);
	}

	@Override
	public void onSeekComplete(MediaPlayer mp) {
		lastPosition = 0;
		mPreChangedStartTime = 0;
		mPreChangedEndTime = 0;
		mIsChangeTime = false;
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		return false;
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

	}

	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		return false;
	}

	@Override
	public void onStateChanged(boolean isPlaying) {
		if (isPlaying) {
			mHandler.removeMessages(HANDLE_PROGRESS);
			mHandler.sendEmptyMessage(HANDLE_PROGRESS);
			mPlayController.setVisibility(View.GONE);
		} else {
			clearLine();
			mHandler.removeMessages(HANDLE_PROGRESS);
			mPlayController.setVisibility(View.VISIBLE);
		}
	}

	/** 视频时长 */
	private int mDuration = -1;

	@Override
	public void onPrepared(MediaPlayer mp) {
		// 检测
		mVideoLoading.setVisibility(View.GONE);
		mDuration = mVideoView.getDuration();

		if (mDuration < 3000) {
			ToastUtils.showToast(ImportVideoActivity.this, "视频太短");
			finish();
			return;
		}

		setVideoMode(scale);
		mVideoSelection.init(FileUtils.getCacheDiskPath(this, "thumbs"), mSourcePath, mDuration, 15 * 1000, 3 * 1000);

		mVideoView.start();
	}

	private void setVideoMode(int scale) {
		if (scale == VideoSelectionView.FIT_XY) {
			mVideoView.resize();
			if (mVideoView.getCropX() == 0 && mVideoView.getCropY() == 0) {
				mVideoView.centerXY();
			}
			mIsFitCenter = false;
			mPreviewLinearLayout.setGravity(Gravity.NO_GRAVITY);
		} else if (scale == VideoSelectionView.FIT_CENTER) {
			mVideoView.fitCenter();
			mPreviewLinearLayout.setGravity(Gravity.CENTER);
			mIsFitCenter = true;
		}
	}

	/** 下一步转码 */
	@SuppressLint("NewApi")
	private void startEncoding() {
		// 检测磁盘空间
		if (!TTApplication.isAvailableSpace()) {
			return;
		}

		if (mVideoSelection != null) {
			mVideoSelection.killSnapImage();
		}
		if (mMediaObject != null) {
			// 生成片段信息
			com.yixia.weibo.sdk.model.MediaObject$MediaPart part = mMediaObject.getLastPart();
			if (part == null) {
				part = mMediaObject.buildMediaPart(-1, ".mp4");
			}

			// 暂停播放
			mVideoView.pauseClearDelayed();

			Log.i("Arvin", " mVideoSelection.getStartTime()" + mVideoSelection.getStartTime() + "<><>mPreStartTime::"
					+ mPreChangedStartTime);

			final com.yixia.weibo.sdk.model.MediaObject$MediaPart mediaPart = part;
			final int videoWidth = mVideoView.getVideoWidth();
			final int videoHeight = mVideoView.getVideoHeight();
			final int cropX = mVideoView.getCropX();
			final int cropY = mVideoView.getCropY();
			final float scale = mVideoView.getScale();

			int startTimetmp = 0;
			int endTimetmp = 0;
			if (mIsChangeTime) {
				startTimetmp = mPreChangedStartTime;
				endTimetmp = mPreChangedEndTime;
			} else {
				startTimetmp = mVideoSelection.getStartTime();
				endTimetmp = mVideoSelection.getEndTime();
			}

			final int startTime = startTimetmp;
			final int endTime = endTimetmp;
			String output = mediaPart.mediaPath;
			final String cutpath = output.replace("0.mp4", System.currentTimeMillis() + ".mp4");

			part.duration = endTime - startTime;

			Log.i("Arvin", "startTime / 1000F, (endTime - startTime) / 1000F " + startTime / 1000F + ","
					+ mVideoSelection.getVideoTime() / 1000F);

			new ThreadTask<Void, Void, Boolean>() {

				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					showDialog();
				}

				@Override
				protected Boolean doInBackground(Void... params) {

					while (mVideoSelection.isThumbLoading()) {
						SystemClock.sleep(500);
					}

					if (mVideoRotation <= 0) {
						mVideoRotation = UtilityAdapter.VideoGetMetadataRotate(mSourcePath);
					}
					Log.i("Arvin", cutpath);
					if (StringUtils.isNotEmpty(cutpath)) {
						File f = new File(cutpath);
						if (!f.exists()) {
							String parentPath = f.getParent();
							File file = new File(parentPath);
							if (!file.exists()) {
								try {
									// 按照指定的路径创建文件夹
									file.mkdirs();
								} catch (Exception e) {
								}
							}
							File dir = new File(cutpath);
							if (!dir.exists()) {
								try {
									// 在指定的文件夹中创建文件
									dir.createNewFile();
								} catch (Exception e) {
								}
							}
						}
					}
					String cmd = String.format(
							"ffmpeg %s -ss %.1f -i \"%s\" -t %.1f -vcodec copy -acodec copy  -f mp4 -movflags faststart \"%s\"",
							FFMpegUtils.getLogCommand(), startTime / 1000F, mSourcePath,
							mVideoSelection.getVideoTime() / 1000F, cutpath);

					boolean result = UtilityAdapter.FFmpegRun("", cmd) == 0;
					return result;
				}

				@Override
				protected void onPostExecute(Boolean result) {
					super.onPostExecute(result);
					hideDialog();
					if (!isFinishing()) {
						if (result) {
							mMediaObject.cropX = cropX;
							mMediaObject.cropY = cropY;
							mMediaObject.videoWidth = videoWidth;
							mMediaObject.videoHeight = videoHeight;
							mMediaObject.mVideoRotation = mVideoRotation;
							mMediaObject.scale = scale;
							mMediaObject.mIsFitCenter = mIsFitCenter;
							mMediaObject.mIsWhiteBackground = mIsWhiteBackground;

							Log.i("Arvin",
									"视频截取成功----》cropX:" + cropX + "---cropY:" + cropY + "---videoWidth:" + videoWidth
											+ "/n" + "---videoHeight:" + videoHeight + "---mVideoRotation:"
											+ mVideoRotation + "---scale:" + scale + "/n" + "---mIsFitCenter:"
											+ mIsFitCenter + "---mIsWhiteBackground:" + mIsWhiteBackground);

							// ToastUtils.showToast(ImportVideoActivity.this,
							// "视频转码成功");
							Intent intent = new Intent(ImportVideoActivity.this, ShareVideoActivity.class);
							intent.putExtra("videoPath", cutpath);
							startActivity(intent);

						} else {
							ToastUtils.showToast(ImportVideoActivity.this, "视频转码失败");
						}
					}
				}
			}.execute();
		}
	}

	@Override
	public void onBackPressed() {
		hideDialog();
		// 删除临时文件
		if (mMediaObject != null) {
			mMediaObject.cancel();
		}
		finish();
	}

	ProgressDialog mEncodingProgressDialog;

	private void showDialog() {
		if (isFinishing()) {
			return;
		}
		if (mEncodingProgressDialog == null)
			mEncodingProgressDialog = showVideoProcessDialog(this, "视频处理中...");
		mEncodingProgressDialog.show();
	}

	private void hideDialog() {
		if (!isFinishing() && mEncodingProgressDialog != null && mEncodingProgressDialog.isShowing()) {
			mEncodingProgressDialog.dismiss();
			mEncodingProgressDialog = null;
		}
	}

	public static ProgressDialog showVideoProcessDialog(Context mContext, String text) {
		ProgressDialog dialog = new ProgressDialog(mContext);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setIndeterminate(true);
		dialog.show();
		View convertView = LayoutInflater.from(mContext).inflate(R.layout.dialog_encoding_novalue, null);
		dialog.setContentView(convertView);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		TextView textview = (TextView) convertView.findViewById(R.id.text);
		if (StringUtils.isEmpty(text)) {
			textview.setVisibility(View.GONE);
		} else {
			textview.setVisibility(View.VISIBLE);
			textview.setText(text);
		}
		return dialog;
	}

}
