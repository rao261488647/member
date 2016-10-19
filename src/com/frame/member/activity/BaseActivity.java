package com.frame.member.activity;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.SocketTimeoutException;

import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.member.R;
import com.frame.member.Managers.AppManager;
import com.frame.member.Managers.ThreadPoolManager;
import com.frame.member.Utils.CommonUtil;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.NetUtil;
import com.frame.member.bean.BaseBean;

/**
 * 基础件 封装常用操作及网络请求
 * 
 * @author Arvin
 *  
 */
@SuppressLint("HandlerLeak")
public abstract class BaseActivity extends FragmentActivity {

	protected final String TT_TAG = getClass().getSimpleName();

	private ThreadPoolManager threadPoolManager;

	protected String mUniqueId;
	protected AppManager appManager;

	protected ImageView iv_title_back, iv_title_right, iv_title_right2;
	protected TextView tv_title, tv_title_left, tv_title_right;
	protected LinearLayout ll_title_left, ll_title_right, ll_title_right2;

	protected Bundle savedInstanceState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		appManager = AppManager.getAppManager();
		appManager.addActivity(this);

		threadPoolManager = ThreadPoolManager.getInstance();

		this.savedInstanceState = savedInstanceState;

		initLogic();

	}

	private void initLogic() {
		loadViewLayout();

		initTitleView();

		findViewById();
		setListener();
		processLogic();
	}
	
	private void initTitleView() {
		ll_title_left = (LinearLayout) findViewById(R.id.ll_title_left);
//		ll_title_right = (LinearLayout) findViewById(R.id.ll_title_right);
//		ll_title_right2 = (LinearLayout) findViewById(R.id.ll_title_right2);

		iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
		iv_title_right = (ImageView) findViewById(R.id.iv_title_right);
//		iv_title_right2 = (ImageView) findViewById(R.id.iv_title_right2);

		tv_title = (TextView) findViewById(R.id.tv_title);
		//tv_title_left = (TextView) findViewById(R.id.tv_title_left);
		tv_title_right = (TextView) findViewById(R.id.tv_title_right);

		if (iv_title_back != null) {
			iv_title_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	public interface DataCallback<T> {
		public void processData(T object, RequestResult result);
	}

	public void showToast(String tip) {
		Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 请求结果处理
	 * 
	 * @author Arvin
	 * 
	 */
	private class BaseHandler<T> extends Handler {
		private DataCallback<T> callBack;

		public BaseHandler(DataCallback<T> callBack) {
			this.callBack = callBack;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			closeProgressDialog();
			if (msg.what == RequestResult.Success.ordinal()) {
				if(msg.obj instanceof BaseBean){
					BaseBean result = (BaseBean)msg.obj;
					String status = result.code;
					if("200".equals(status))
						callBack.processData((T) (msg.obj), RequestResult.Success);
					else{
						showToast(result.message);
						callBack.processData(null, RequestResult.RequestError);
					}
				}else
					callBack.processData((T) (msg.obj), RequestResult.Success);
			} else if (msg.what == RequestResult.NetworkNotAvailable.ordinal()) {
				showToast(RequestResult.NetworkNotAvailable.toString());
				callBack.processData(null, RequestResult.NetworkNotAvailable);
			} else if (msg.what == RequestResult.ServerError.ordinal()) {
				showToast(RequestResult.ServerError.toString());
				callBack.processData(null, RequestResult.ServerError);
			} else if (msg.what == RequestResult.ServerTimeout.ordinal()){
				showToast(RequestResult.ServerTimeout.toString());
				callBack.processData(null, RequestResult.ServerTimeout);
			} else if (msg.what == RequestResult.NoData.ordinal()){
				showToast(RequestResult.NoData.toString());
				callBack.processData(null, RequestResult.NoData);
			}
		}
	}

	/**
	 * 请求任务封装
	 * 
	 * @author Arvin
	 * 
	 */
	class BaseTask implements Runnable {
		private Context context;
		private HttpRequest reqVo;
		private Handler handler;

		public BaseTask(Context context, HttpRequest reqVo, Handler handler) {
			this.context = context;
			this.reqVo = reqVo;
			this.handler = handler;
		}

		@Override
		public void run() {
			Object obj = null;
			Message msg = new Message();
			try {
				if (!CommonUtil.hasNetwork(context)) { // 判断是否联网
					msg.what = RequestResult.NetworkNotAvailable.ordinal();
					msg.obj = obj;
					handler.sendMessage(msg);
					return;
				}
				obj = NetUtil.handleRequest(reqVo);
				msg.what = RequestResult.Success.ordinal();
				msg.obj = obj;
				if (msg.obj == null) { // 如果返回结果为空 给予服务器错误提示
					msg.what = RequestResult.NoData.ordinal();
				}
			} catch (ConnectTimeoutException e) { // 链接超时
				System.out.println("ConnectTimeoutException----》"
						+ e.toString());
				msg.what = RequestResult.ServerTimeout.ordinal();
			} catch (SocketTimeoutException e) { // 响应超时
				System.out
						.println("SocketTimeoutException----》" + e.toString());
				msg.what = RequestResult.ServerTimeout.ordinal();
			} catch (HttpHostConnectException e) { // 响应超时
				System.out.println("HttpHostConnectException----》"
						+ e.toString());
				msg.what = RequestResult.ServerTimeout.ordinal();
			} catch (JSONException e) {
				e.printStackTrace(); // json 错误
				System.out.println("Json解析错误------》");
			} catch (Throwable e) {
				msg.what = RequestResult.ServerError.ordinal();
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				System.out.println("网络请求错误----》" + sw.toString());
			}

			if (!isCancled)
				handler.sendMessage(msg);
		}
	}

	private ProgressDialog progressDialog;
	private boolean isCancled;

	/**
	 * 显示提示框
	 */
	public void showProgressDialog(String... hint) {
		if ((!isFinishing()) && (this.progressDialog == null)) {
			this.progressDialog = new ProgressDialog(this);
			this.progressDialog.setCanceledOnTouchOutside(false);
		}
		if (this.progressDialog == null)
			return;
		this.progressDialog
				.setMessage(hint != null && hint.length > 0 ? hint[0]
						: "正在加载...");
		if (!isFinishing() && !this.progressDialog.isShowing())
			this.progressDialog.show();
	}

	/**
	 * 关闭提示框
	 */
	public void closeProgressDialog() {
		if (this.progressDialog != null && this.progressDialog.isShowing())
			this.progressDialog.dismiss();
		progressDialog = null;
	}

	/**
	 * 从服务器上获取数据，并回调处理
	 * 
	 * @param request
	 * @param isShowMiniLoading
	 * @param callBack
	 * @param isShowDialog
	 *            //是否显示加载框
	 */
	public <T> void getDataFromServer(HttpRequest request,
			DataCallback<T> callBack, String... hint) {
		getDataFromServer(request, true, callBack, hint);
	}

	/**
	 * 默认显示加载框
	 * 
	 * @param request
	 * @param callBack
	 */
	public <T> void getDataFromServer(HttpRequest request,
			DataCallback<T> callBack) {
		getDataFromServer(request, true, callBack, "加载中...");
	}

	public <T> void getDataFromServer(HttpRequest request,
			boolean isShowDialog, DataCallback<T> callBack, String... hint) {
		if (isShowDialog) {
			showProgressDialog(hint);
			if (this.progressDialog != null && this.progressDialog.isShowing())
				this.progressDialog.setOnCancelListener(new OnCancelListener() {
					// 加载中
					@Override
					public void onCancel(DialogInterface dialog) {
						isCancled = true;
					}
				});
		}

		BaseHandler<T> handler = new BaseHandler<T>(callBack);
		BaseTask taskThread = new BaseTask(this, request, handler);
		this.threadPoolManager.addTask(taskThread);
	}

	// // 点击空白区域 自动隐藏软键盘
	// // 获取点击事件
	// @Override
	// public boolean dispatchTouchEvent(MotionEvent ev) {
	// if (ev.getAction() == MotionEvent.ACTION_DOWN) {
	// View view = getCurrentFocus();
	// if (isHideInput(view, ev)) {
	// HideSoftInput(view.getWindowToken());
	// }
	// }
	// return super.dispatchTouchEvent(ev);
	// }
	//
	// // 判定是否需要隐藏
	// private boolean isHideInput(View v, MotionEvent ev) {
	// if (v != null && (v instanceof EditText)) {
	// int[] l = { 0, 0 };
	// v.getLocationInWindow(l);
	// int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
	// + v.getWidth();
	// if (ev.getX() > left && ev.getX() < right && ev.getY() > top
	// && ev.getY() < bottom) {
	// return false;
	// } else {
	// return true;
	// }
	// }
	// return false;
	// }
	//
	// // 隐藏软键盘
	// private void HideSoftInput(IBinder token) {
	// if (token != null) {
	// InputMethodManager manager = (InputMethodManager)
	// getSystemService(Context.INPUT_METHOD_SERVICE);
	// manager.hideSoftInputFromWindow(token,
	// InputMethodManager.HIDE_NOT_ALWAYS);
	// }
	// }

	/**
	 * HTTP请求结果
	 * 
	 */
	public static enum RequestResult {
		/**
		 * 请求成功
		 */
		Success,
		/**
		 * 请求错误
		 */
		RequestError,
		/**
		 * 网络不可用
		 */
		NetworkNotAvailable,
		/**
		 * 请求服务器超时
		 */
		ServerTimeout,
		/**
		 * 请求服务器出错
		 */
		ServerError,
		/**
		 * 已无更多数据
		 */
		NoData;

		@Override
		public String toString() {
			if (this == NetworkNotAvailable) {
				return "网络不可用";
			}
			if (this == ServerTimeout) {
				return "请求服务器超时";
			}
			if (this == ServerError) {
				return "服务器正忙，请稍后再试";
			}
			if (this == RequestError) {
				return "";
			}
			if(this == NoData){
				return "没有更多数据";
			}
			
			return "请求成功";
		};
	}


	/**
	 * 初始化布局
	 */
	protected abstract void loadViewLayout();

	/**
	 * 绑定控件id
	 */
	protected abstract void findViewById();

	/**
	 * 设置监听
	 */
	protected abstract void setListener();

	/**
	 * 主逻辑
	 */
	protected abstract void processLogic();

	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeProgressDialog();
	}

}
