package com.frame.member.activity;

import org.json.JSONException;
import org.json.JSONObject;
import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Utils.BitmapUtil;
import com.frame.member.Utils.HttpRequest.RequestMethod;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.Utils.ThreadTask;
import com.frame.member.widget.VideoViewTouch;
import com.tencent.upload.Const.FileType;
import com.tencent.upload.UploadManager;
import com.tencent.upload.task.ITask.TaskState;
import com.tencent.upload.task.IUploadTaskListener;
import com.tencent.upload.task.VideoAttr;
import com.tencent.upload.task.data.FileInfo;
import com.tencent.upload.task.impl.VideoUploadTask;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ShareVideoActivity extends BaseActivity implements OnClickListener, OnPreparedListener {

	private VideoViewTouch vvt_video;

	private ImageView iv_play_controller, iv_share_video_first_frame;

	private EditText et_share_video_input;

	private TextView tv_video_share;

	private boolean isVideoPrepared;

	private String videoPath;

	private UploadManager mFileUploadManager = null;

	private String appid = "10065730";

	private String bucket = null;
	private String sign = null;

	private Bitmap bitmap;// 临时视频封面

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_video_share);
	}

	@Override
	protected void findViewById() {
		tv_title.setText("分享视频");
		tv_title.setTextColor(Color.WHITE);

		iv_title_back.setVisibility(0);

		vvt_video = (VideoViewTouch) findViewById(R.id.vvt_video);
		iv_share_video_first_frame = (ImageView) findViewById(R.id.iv_share_video_first_frame);
		iv_play_controller = (ImageView) findViewById(R.id.iv_play_controller);
		et_share_video_input = (EditText) findViewById(R.id.et_share_video_input);
		tv_video_share = (TextView) findViewById(R.id.tv_video_share);

	}

	@Override
	protected void setListener() {
		tv_video_share.setOnClickListener(this);
		iv_play_controller.setOnClickListener(this);

		vvt_video.setOnPreparedListener(this);
		vvt_video.setOnTouchEventListener(mOnVideoTouchListener);
		vvt_video.setLooping(true);
		vvt_video.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				vvt_video.seekTo(0);

				iv_play_controller.setVisibility(0);
				iv_share_video_first_frame.setVisibility(0);
			}
		});
	}

	@Override
	protected void processLogic() {

		videoPath = getIntent().getStringExtra("videoPath");

		clipFirstFrame(videoPath);

		vvt_video.setVideoPath(videoPath);

		String persistenceId = "flowerski";

		mFileUploadManager = new UploadManager(this, appid, FileType.Video, persistenceId);

		getSign();
	}

	private void getSign() {
		HttpRequestImpl request = new HttpRequestImpl(ShareVideoActivity.this,
				AppConstants.APP_SORT_STUDENT + AppConstants.GETMVSINFO, null, RequestMethod.post);
		request.addParam("memberUserId",
				(String) SPUtils.getAppSpUtil().get(ShareVideoActivity.this, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("token", (String) SPUtils.getAppSpUtil().get(ShareVideoActivity.this, SPUtils.KEY_TOKEN, ""))
				.addParam("type", 1 + "");
		getDataFromServer(request, new DataCallback<String>() {

			@Override
			public void processData(String object, RequestResult result) {
				Log.i("Arvin", "获取服务器视频签名---->" + object);
				if (!TextUtils.isEmpty(object)) {
					try {
						JSONObject resultObj = new JSONObject(object);
						String code = resultObj.optString("code");
						if ("200".equals(code)) {
							JSONObject mvsObj = resultObj.optJSONObject("data").optJSONObject("mvs");
							if (mvsObj != null) {
								bucket = mvsObj.optString("bucketName");
								sign = mvsObj.optString("sign");
							}
						}
					} catch (JSONException e) {

						Log.i("Arvin", "获取服务器视频签名 服务器返回数据异常");

						e.printStackTrace();
					}
				}

			}
		});
	}

	private void clipFirstFrame(String path) {

		MediaMetadataRetriever mmr = new MediaMetadataRetriever();
		mmr.setDataSource(path);
		bitmap = mmr.getFrameAtTime();

		if (bitmap != null)
			iv_share_video_first_frame.setImageBitmap(bitmap);
	}

	private String coverUrl = "";
	private String act_desc = "";

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_video_share:
			act_desc = et_share_video_input.getText().toString();
			if (TextUtils.isEmpty(act_desc)) {
				showToast("活动描述不能为空");
				return;
			}

			showProgressDialog("正在上传封面...");
			// 1.上传封面
			if (bitmap != null) {
				HttpRequestImpl request = new HttpRequestImpl(ShareVideoActivity.this,
						AppConstants.APP_SORT_STUDENT + AppConstants.GETMVSCOVER, null, RequestMethod.post);
				request.addParam("memberUserId",
						(String) SPUtils.getAppSpUtil().get(ShareVideoActivity.this, SPUtils.KEY_MEMBERUSERID, ""))
						.addParam("token",
								(String) SPUtils.getAppSpUtil().get(ShareVideoActivity.this, SPUtils.KEY_TOKEN, ""))
						.addParam("head", BitmapUtil.Bitmap2StrByBase64(bitmap)).addParam("device", "android");
				getDataFromServer(request, false,new DataCallback<String>() {

					@Override
					public void processData(String object, RequestResult result) {
						Log.i("Arvin", "上传封面---->" + object);
						if (!TextUtils.isEmpty(object)) {
							try {
								JSONObject resultObj = new JSONObject(object);
								String code = resultObj.optString("code");
								if ("200".equals(code)) {
									JSONObject dataObj = resultObj.optJSONObject("data");
									if (dataObj != null) {
										coverUrl = dataObj.optString("coverUrl");
									}
									// 2.拼接videoattr
									genVideoAttr();
									// 3.上传视频
									showProgressDialog("正在上传视频...");
									upLoadVideo(true);
								}
							} catch (JSONException e) {

								Log.i("Arvin", "上传图片异常");

								e.printStackTrace();
							}
						}

					}
				});

			}
			break;
		case R.id.iv_play_controller:
			if (isVideoPrepared && !vvt_video.isPlaying()) {
				vvt_video.start();
				iv_play_controller.setVisibility(8);
				iv_share_video_first_frame.setVisibility(8);
			}
			break;
		}
	}

	private VideoUploadTask videoUploadTask;

	private VideoAttr videoAttr;

	private void genVideoAttr() {
		videoAttr = new VideoAttr();
		videoAttr.isCheck = false;
		videoAttr.title = genVideoName();
		videoAttr.coverUrl = coverUrl;
		videoAttr.desc = genVideoDesc();
	}

	private String genVideoDesc() {
		String result = String.format("\"{\"memberUserId\":\"%s\",\"content\":\"%s\",\"device\":\"android\"}\"",
				(String) SPUtils.getAppSpUtil().get(ShareVideoActivity.this, SPUtils.KEY_MEMBERUSERID, ""),act_desc);
		Log.i("Arvin", result);
		return result;
	}

	private String genVideoName() {
		if (TextUtils.isEmpty(videoPath)) {
			showToast("本地视频地址错误");
			return "";
		}
		String[] strarray = videoPath.split("/");
		String videoSaveName = strarray[strarray.length - 1];
		return videoSaveName;
	}
	
	private void closePages(){
		sendBroadcast(new Intent(AppConstants.ACTION_ACT_PUB_SUCC));
		finish();
	}

	public void upLoadVideo(boolean to_over_write) {

		videoUploadTask = new VideoUploadTask(bucket, videoPath, "/" + genVideoName(), "", videoAttr, to_over_write,
				new IUploadTaskListener() {
					@Override
					public void onUploadSucceed(final FileInfo result) {
						showToast("活动发布成功");
						closeProgressDialog();
						
						closePages();
						Log.i("Arvin", "上传结果:成功--->" + result.url + "----" + result.extendInfo.toString());
					}

					@Override
					public void onUploadStateChange(TaskState state) {

					}

					@Override
					public void onUploadProgress(final long totalSize, final long sendSize) {
						long p = (long) ((sendSize * 100) / (totalSize * 1.0f));
						Log.i("Arvin", "上传进度: " + p + "%");
					}

					@Override
					public void onUploadFailed(final int errorCode, final String errorMsg) {
						showToast("活动发布失败 请稍后重试");
						closeProgressDialog();
						Log.i("Arvin", "上传结果:失败! ret:" + errorCode + " msg:" + errorMsg);

					}
				});
		videoUploadTask.setAuth(sign);
		mFileUploadManager.upload(videoUploadTask); // 开始上传
	}

	private VideoViewTouch.OnTouchEventListener mOnVideoTouchListener = new VideoViewTouch.OnTouchEventListener() {

		@Override
		public boolean onClick() {
			if (vvt_video.isPlaying()) {
				vvt_video.pauseClearDelayed();
				iv_play_controller.setVisibility(0);
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

	@Override
	public void onPrepared(MediaPlayer mp) {
		isVideoPrepared = true;
	}
}
