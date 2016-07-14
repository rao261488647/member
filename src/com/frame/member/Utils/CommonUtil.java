package com.frame.member.Utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.frame.member.R;
import com.frame.member.bean.ContractTerm;
import com.frame.member.bean.Plat;
import com.frame.member.bean.Regtime;
import com.frame.member.listener.AsyncHttpListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/***
 * 全局常用帮助类
 * 
 * @author Arvin
 * 
 */
public class CommonUtil {

	public static String getAppVersion(Context context) {
		PackageManager pm = context.getPackageManager();
		try {
			return pm.getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			return null;
		}
	}

	public static int getAppVersionCode(Context context) {
		PackageManager pm = context.getPackageManager();
		try {
			return pm.getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			return 0;
		}
	}
	
	public static String getDeviceIMEI(Context context){
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}
	
	/**
	 * 获取手机型号
	 * @param context
	 * @return
	 */
	public static String getDeviceModel(){
		return android.os.Build.MODEL;
	}

	/**
	 * 网络是否连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasNetwork(Context context) {
		ConnectivityManager con = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo workinfo = con.getActiveNetworkInfo();
		if (workinfo != null && workinfo.isAvailable())
			return true;

		return false;
	}

	/**
	 * 网络类型
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifiNetWork(Context context) {
		ConnectivityManager con = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = con.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isAvailable()
				&& networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
	}

	public static int getNetWorkType(Context context) {
		ConnectivityManager con = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = con.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isAvailable())
			return networkInfo.getType();
		return -1;
	}

	@SuppressWarnings("deprecation")
	public static int getScreenHeight(Activity activity) {
		return activity.getWindowManager().getDefaultDisplay().getHeight();
	}

	@SuppressWarnings("deprecation")
	public static int getScreenWidth(Activity activity) {
		return activity.getWindowManager().getDefaultDisplay().getWidth();
	}

	public static int getHeight(Activity activity, int width, int height) {
		return height * getScreenWidth(activity) / width;
	}

	public static int getHeight(Activity activity) {
		return (getScreenWidth(activity) - dip2px(activity, 10)) / 2;
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,1-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	public static void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 * 文件拷贝
	 * 
	 * @param in
	 *            输入流 源文件
	 * @param out
	 *            资源输出流 目标文件
	 * @throws IOException
	 */
	public static void copy(InputStream in, OutputStream out)
			throws IOException {
		byte[] b = new byte[1024 * 3];
		int read;
		while ((read = in.read(b)) != -1) {
			out.write(b, 0, read);
		}
	}

	/**
	 * 将网络图片地址转换成bitmap
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap GetNetBitmap(String url) {
		Bitmap bitmap = null;
		InputStream in = null;
		BufferedOutputStream out = null;
		try {
			in = new BufferedInputStream(new URL(url).openStream(), 1024 * 3);
			final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			out = new BufferedOutputStream(dataStream, 1024 * 3);
			copy(in, out);
			out.flush();
			byte[] data = dataStream.toByteArray();
			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			data = null;
			return bitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Bitmap returnBitMap(String url) {

		Bitmap bitmap = null;
		URL myFileUrl;
		try {
			myFileUrl = new URL(url);
			URLConnection con = myFileUrl.openConnection();
			bitmap = BitmapFactory.decodeStream(con.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 转换文件大小
	 * 
	 * @param fileS
	 * @return
	 */
	public static String FormetFileSize(long fileS) {
		if (fileS == 0)
			return "0 M";
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	//合同弹出框
	public static void postSelectDialog(final List<ContractTerm> postList,
			Activity activity, final AsyncHttpListener rollback) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle("请选择合同期限");
		View view = View.inflate(activity, R.layout.contract_term_select, null);
		final ListView postListView = (ListView) view
				.findViewById(R.id.list_select);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		ArrayAdapter arrayAdapter = new ArrayAdapter(activity,
				android.R.layout.simple_list_item_1, postList);
		postListView.setAdapter(arrayAdapter);
		postListView.setSelector(R.drawable.list_selector_style);

		postListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int index, long arg3) {
				ContractTerm selectPost = (ContractTerm) postList.get(index);
				if (selectPost != null) {
					rollback.setContractTerm(selectPost);
					rollback.onSucess("");
				}

			}
		});
		
		builder.setPositiveButton("确  定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		builder.setView(view);
		Dialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}
	
	//平台
		public static void platSelectDialog(final List<Plat> postList,
				Activity activity, final AsyncHttpListener rollback) {
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setTitle("请选择平台");
			View view = View.inflate(activity, R.layout.plat_select, null);
			final ListView postListView = (ListView) view
					.findViewById(R.id.plat_select);
			@SuppressWarnings({ "unchecked", "rawtypes" })
			ArrayAdapter arrayAdapter = new ArrayAdapter(activity,
					android.R.layout.simple_list_item_1, postList);
			postListView.setAdapter(arrayAdapter);
			postListView.setSelector(R.drawable.list_selector_style);

			postListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View arg1,
						int index, long arg3) {
					Plat selectPost = (Plat) postList.get(index);
					if (selectPost != null) {
						rollback.setPlat(selectPost);
						//rollback.setContractTerm(selectPost);
						
						rollback.onSucess("");
					}

				}
			});
			
			builder.setPositiveButton("确  定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			builder.setView(view);
			Dialog dialog = builder.create();
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
		}
		//上门注册时间
				public static void regTimeSelectDialog(final List<Regtime> postList,
						Activity activity, final AsyncHttpListener rollback) {
					AlertDialog.Builder builder = new AlertDialog.Builder(activity);
					builder.setTitle("请选择上门注册时间");
					View view = View.inflate(activity, R.layout.plat_select, null);
					final ListView postListView = (ListView) view
							.findViewById(R.id.plat_select);
					@SuppressWarnings({ "unchecked", "rawtypes" })
					ArrayAdapter arrayAdapter = new ArrayAdapter(activity,
							android.R.layout.simple_list_item_1, postList);
					postListView.setAdapter(arrayAdapter);
					postListView.setSelector(R.drawable.list_selector_style);

					postListView.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent, View arg1,
								int index, long arg3) {
							Regtime selectPost = (Regtime) postList.get(index);
							if (selectPost != null) {
								rollback.setRegtime(selectPost);
								//rollback.setContractTerm(selectPost);
								
								rollback.onSucess("");
							}

						}
					});
					
					builder.setPositiveButton("确  定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.cancel();
								}
							});
					builder.setView(view);
					Dialog dialog = builder.create();
					dialog.setCanceledOnTouchOutside(true);
					dialog.show();
				}
}
