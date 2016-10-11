package com.frame.member.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import com.frame.member.R;
import com.frame.member.Utils.MD5;
import com.frame.member.adapters.LayerAdapter;
import com.frame.member.bean.Video;
import com.yixia.camera.demo.log.Logger;
import com.yixia.weibo.sdk.FFMpegUtils;
import com.yixia.weibo.sdk.util.ConvertToUtils;
import com.yixia.weibo.sdk.util.DeviceUtils;
import com.yixia.weibo.sdk.util.FileUtils;
import com.yixia.weibo.sdk.util.StringUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * 选择手机本地视频界面
 * 
 */
public class SelectVedioActivity extends BaseActivity {

	private static Map<ImageView, String> mImageViews;
	private GridView gv_local_vedios;
	
	private static final String[] VIDEO_PROJECT = { MediaStore.Video.Media._ID, MediaStore.Video.Media.DATE_MODIFIED,
			MediaStore.Video.Media.DURATION, MediaStore.Video.Media.DATA };

	private static final String[] THUMB_PROJECT = { MediaStore.Video.Thumbnails.DATA,
			MediaStore.Video.Thumbnails.VIDEO_ID };

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_vedio_selector);
	}

	@Override
	protected void findViewById() {
		tv_title.setText("选择分享视频");
		tv_title.setTextColor(Color.WHITE);
		iv_title_back.setVisibility(0); 
		
		gv_local_vedios = (GridView) findViewById(R.id.gv_local_vedios);
		
	}

	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
		iv_title_back.setVisibility(0);

		mImageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
		mThumbCacheDir = com.frame.member.Utils.FileUtils.getCacheDiskPath(this, "thumbs");
		if (mThumbCacheDir != null && !mThumbCacheDir.exists()) {
			mThumbCacheDir.mkdirs();
		}
		
		
		new AsyncTask<Void, Void, List<Video>>() {
			
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showProgressDialog("正在扫描...");
			}

			@Override
			protected void onPostExecute(List<Video> result) {
				super.onPostExecute(result);
				
				gv_local_vedios.setAdapter(new LayerAdapter<Video>(result) {

					@Override
					protected View getItemView(int position, View convertView) {
						if(convertView == null)
							convertView = View.inflate(SelectVedioActivity.this, R.layout.item_select_video, null);
						
						ImageView iv_item_select_video = (ImageView) convertView.findViewById(R.id.iv_item_select_video);
						
						Video item = getItem(position);
						
						if (StringUtils.isNotEmpty(item.url)) {
							if (!item.faild) {
								loadVideoThumb(iv_item_select_video,item);
							}
						}
						iv_item_select_video.setTag(item);
						
//						convertView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
						
						return convertView;
					}
				});

				closeProgressDialog();
			}
			
			@Override
			protected List<Video> doInBackground(Void... params) {
				return loadData();
			}
		}.execute();
	}

	private List<Video> loadData() {

		List<Video> result = new ArrayList<Video>();
		Cursor cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, VIDEO_PROJECT, null,
				null, MediaStore.MediaColumns.DATE_MODIFIED + " DESC");
		if (cursor != null) {

			int idxId = cursor.getColumnIndex(MediaStore.MediaColumns._ID);
			int idxModified = cursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED);
			int indxSize = cursor.getColumnIndex(MediaStore.Video.Media.DURATION);
			int idxData = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);

			while (cursor.moveToNext()) {
				long _id = cursor.getLong(idxId);
				long modified = cursor.getLong(idxModified);
				int duration = cursor.getInt(indxSize);
				String path = cursor.getString(idxData);
				if (!TextUtils.isEmpty(path)) {
					File file = new File(path);
					if (file != null && file.canRead()) {
						Video video = getVideo(_id, path, modified, duration);
						result.add(video);
					}
				}
			}
			cursor.close();
		}

		// 排序
		Collections.sort(result, new Comparator<Video>() {

			@Override
			public int compare(Video lhs, Video rhs) {
				return lhs.url.compareTo(rhs.url);
			}

		});

		return result;
	}


	public Uri getFileUri(String path) {

		Logger.e("simon", "getFile Uri>>>" + path);

		return Uri.parse("file:///" + path);
	}

	private File mThumbCacheDir;

	private void loadVideoThumb(final ImageView view, final Video video){
		if (mThumbCacheDir == null || video == null || StringUtils.isEmpty(video.url)) {
			return;
		}

		final String videoPath = video.url;

		mImageViews.put(view, videoPath);

		if (StringUtils.isNotEmpty(video.thumb)) {
			view.setImageURI(getFileUri(video.thumb));
			return;
		}
		new Thread(new Runnable() {

			/** 现实缩略图 */
			private void showThumb() {
				if (video != null && !Thread.currentThread().isInterrupted() && SelectVedioActivity.this != null) {
					((Activity) SelectVedioActivity.this).runOnUiThread(new Runnable() {
						@Override
						public void run() {
							String tag = mImageViews.get(view);
							if (tag != null && tag.equals(videoPath)) {
								// if (video.equals(view.getTag())) {
								// Logger.e("[ImportHelper]loadVideoThumb...1"
								// + video.faild);
								if (video.faild) {
									view.setImageResource(R.drawable.import_image_default);
								} else if (StringUtils.isNotEmpty(video.thumb)) {
									view.setImageURI(getFileUri(video.thumb));
								}
							}
						}
					});
				}
			}

			@Override
			public void run() {
				// Logger.e("[ImportHelper]loadVideoThumb...2" +
				// video.faild);
				// 先检测截图缓存文件夹是否已经存在截图
				final String key = MD5.md5(videoPath);
				final File thumbFile = new File(mThumbCacheDir, key + ".jpg");
				if (FileUtils.checkFile(thumbFile)) {
					video.thumb = thumbFile.getPath();
					showThumb();
					return;
				}

				// Logger.e("[ImportHelper]loadVideoThumb...3" +
				// video.faild);

				// 从系统相册缓冲中取
				final ContentResolver mContentResolver = SelectVedioActivity.this.getContentResolver();
				if (mContentResolver != null) {
					Cursor thumbCursor = mContentResolver.query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, THUMB_PROJECT, MediaStore.Video.Thumbnails.VIDEO_ID + "=" + video._id, null, null);
					if (thumbCursor != null) {
						// 检测有没有截图，没有截图马上截图
						if (thumbCursor.getCount() == 0) {
							try {
								Bitmap bitmap = MediaStore.Video.Thumbnails.getThumbnail(mContentResolver, video._id, MediaStore.Video.Thumbnails.MINI_KIND, null);
								if (bitmap != null) {
									if (!bitmap.isRecycled())
										bitmap.recycle();
									bitmap = null;
								}
							} catch (OutOfMemoryError e) {
								Logger.e(e);
							} catch (Exception e) {
								Logger.e(e);
							}
							thumbCursor.close();
							thumbCursor = mContentResolver.query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, THUMB_PROJECT, MediaStore.Video.Thumbnails.VIDEO_ID + "=" + video._id, null, null);
						}

						if (thumbCursor != null && thumbCursor.moveToFirst()) {
							String thumb = thumbCursor.getString(0);
							thumbCursor.close();
							if (FileUtils.checkFile(thumb)) {
								video.thumb = thumb;
								showThumb();
								return;
							}
						}
						if (thumbCursor != null && !thumbCursor.isClosed())
							thumbCursor.close();
					}
				}

				if (Thread.currentThread().isInterrupted())
					return;

				// 系统截图失败，调用FFMPEG截图，截图第一帧
				try {
					Logger.e("samuel", "调用ffmpeg截屏");
					if (FFMpegUtils.captureThumbnails(videoPath, thumbFile.getPath(), video.orientation)) {
						if (Thread.currentThread().isInterrupted())
							return;
						if (FileUtils.checkFile(thumbFile)) {
							video.thumb = thumbFile.getPath();
							showThumb();
							return;
						}
					}
				} catch (Exception e) {
					Logger.e(e);
				}

				if (Thread.currentThread().isInterrupted())
					return;

				// 截图完全失败，视频有问题
				video.faild = true;
				showThumb();
			}
		}).run();
		
	}

	@SuppressLint("InlinedApi")
	private Video getVideo(long id, String path, long modified, long duration) {
		MediaMetadataRetriever metadata = new MediaMetadataRetriever();
		int orientation = 0;
		if (DeviceUtils.hasJellyBeanMr1()) {
			try {
				metadata.setDataSource(path);
				orientation = ConvertToUtils
						.toInt(metadata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION), 0);
			} catch (Exception e) {
				Logger.e("path = " + path);
			}
		}

		Video video = new Video(path, modified, duration);
		video._id = id;
		video.orientation = orientation;
		return video;
	}
}
