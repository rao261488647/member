package com.frame.member;

import java.io.File;
import android.app.Application;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Utils.BitmapUtil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.yixia.weibo.sdk.VCamera;
import com.yixia.weibo.sdk.util.FileUtils;
import com.yixia.weibo.sdk.util.ToastUtils;

public class TTApplication extends Application {

	private static TTApplication application;

	public static TTApplication getInstance() {
		return application;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		application = this;

		// 开启log输出,ffmpeg输出到logcat
		VCamera.setDebugMode(true);
		// 初始化拍摄SDK，必须
		VCamera.initialize(this);

		ImageLoader.getInstance().init(configImageLoader());
	}

	public final static int AVAILABLE_SPACE = 200;// M

	/**
	 * 检测用户手机是否剩余可用空间200M以上
	 * 
	 * @return
	 */
	public static boolean isAvailableSpace() {
		if (application == null) {
			return false;
		}
		// 检测磁盘空间
		if (FileUtils.showFileAvailable(application) < AVAILABLE_SPACE) {
			ToastUtils.showToast(application,
					application.getString(R.string.record_check_available_faild, AVAILABLE_SPACE));
			return false;
		}

		return true;
	}

	private ImageLoaderConfiguration configImageLoader() {
		// File cacheDir = StorageUtils.getOwnCacheDirectory(
		// getApplicationContext(), AppConstants.IMAGE_CHCHE);
		File cacheDir = new File(AppConstants.IMAGE_CHCHE);
		@SuppressWarnings("deprecation")
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).memoryCacheExtraOptions(480, 800)
				// max width, max height，即保存的每个缓存文件的最大长宽
				.threadPoolSize(3)
				// 线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
				// You can pass your own memory cache
				// implementation/你可以通过自己的内存缓存实现
				.memoryCacheSize(2 * 1024 * 1024).discCacheSize(50 * 1024 * 1024)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// 将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO).discCacheFileCount(100)
				// 缓存的文件数量
				.discCache(new UnlimitedDiscCache(cacheDir))
				// 自定义缓存路径
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout
																						// (5
																						// s),
																						// readTimeout
																						// (30
																						// s)超时时间
																						// .writeDebugLogs()
																						// //
																						// Remove
																						// for
																						// release
																						// app
				.build();// 开始构建
		// Initialize ImageLoader with configuration.
		return config;
	}

	/**
	 * 默认加载 更多重载方法
	 * 
	 * @param uri
	 * @param imageAware
	 */
	public void disPlayImageDef(String uri, ImageView imageAware) {
		if (!TextUtils.isEmpty(uri)) {
			ImageLoader.getInstance().displayImage(uri, imageAware, getCustomDisplayOptions(true, true));
		}
	}

	// 加载 圆角图片
	public void displayRoundBitmap(String url, final ImageView imageAware) {

		ImageLoader.getInstance().loadImage(url, getCustomDisplayOptions(true, true), new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String arg0, View arg1) {

			}

			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {

			}

			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				if (arg2 == null)
					return;
				// Bitmap bm = BitmapUtil.createCircleImage(arg2,
				// CommonUtil.dip2px(getApplicationContext(), 35));
				// if (bm != null)
				// imageAware.setImageBitmap(bm);
				imageAware.setImageBitmap(BitmapUtil.toRoundBitmap(arg2));
			}

			@Override
			public void onLoadingCancelled(String arg0, View arg1) {

			}
		});
	}

	public void disLoaderImageDef(String url, ImageLoadingListener listener) {
		ImageLoader.getInstance().loadImage(url, getCustomDisplayOptions(true, true), listener);
	}

	public interface onBitmapLoaded {
		void onLoadSucc(Bitmap bitmap);
	}

	public void disLoaderImageDef(String url, final onBitmapLoaded listener) {
		ImageLoader.getInstance().loadImage(url, getCustomDisplayOptions(true, true), new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String arg0, View arg1) {

			}

			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {

			}

			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				if (arg2 != null && listener != null)
					listener.onLoadSucc(arg2);
			}

			@Override
			public void onLoadingCancelled(String arg0, View arg1) {

			}
		});
	}

	public void displayRoundBitmap(String url, final ImageView imageAware, final int pos) {
		ImageLoader.getInstance().loadImage(url, getCustomDisplayOptions(true, true), new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String arg0, View arg1) {

			}

			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {

			}

			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				if (arg2 == null)
					return;
				Object tag = imageAware.getTag();
				if (tag != null) {
					int position = (Integer) tag;
					if (pos == position)
						imageAware.setImageBitmap(BitmapUtil.toRoundBitmap(arg2));
				}
			}

			@Override
			public void onLoadingCancelled(String arg0, View arg1) {

			}
		});
	}

	@SuppressWarnings("deprecation")
	private static DisplayImageOptions getCustomDisplayOptions(boolean isCacheInMemory, boolean isCacheOnDisc) {
		Builder builder = new DisplayImageOptions.Builder();
		// builder.showImageOnLoading(R.drawable.default_bg)
		// .showImageForEmptyUri(R.drawable.default_bg)
		// .showImageOnFail(R.drawable.default_bg);
		DisplayImageOptions options = builder.cacheInMemory(isCacheInMemory).cacheOnDisc(isCacheOnDisc)
				.bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.NONE).resetViewBeforeLoading(false)// 设置图片在下载前是否重置，复位
				// .displayer(new FadeInBitmapDisplayer(700))// 是否图片加载好后渐入的动画时间
				.build();// 构建完成
		return options;
	}
}
