package com.frame.member.receiver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Utils.MyLog;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.MainActivity;
import com.frame.member.bean.NotifyBean;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

public class MessageReceiver extends XGPushBaseReceiver {
	public static final String LogTag = "TTMessageReceiver";

	NotificationManager manager;
	private Context mContext = TTApplication.getInstance().getApplicationContext();

	// 通知展示
	@Override
	public void onNotifactionShowedResult(Context context, XGPushShowedResult notifiShowedRlt) {
		if (context == null || notifiShowedRlt == null) {
			return;
		}
		Log.d(LogTag, "您有1条新消息, " + "通知被展示 ， " + notifiShowedRlt.toString());
	}

	@Override
	public void onUnregisterResult(Context context, int errorCode) {
		if (context == null) {
			return;
		}
		String text = "";
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = "反注册成功";
		} else {
			text = "反注册失败" + errorCode;
		}
		Log.d(LogTag, text);

	}

	@Override
	public void onSetTagResult(Context context, int errorCode, String tagName) {
		if (context == null) {
			return;
		}
		String text = "";
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = "\"" + tagName + "\"设置成功";
		} else {
			text = "\"" + tagName + "\"设置失败,错误码：" + errorCode;
		}
		Log.d(LogTag, text);

	}

	@Override
	public void onDeleteTagResult(Context context, int errorCode, String tagName) {
		if (context == null) {
			return;
		}
		String text = "";
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = "\"" + tagName + "\"删除成功";
		} else {
			text = "\"" + tagName + "\"删除失败,错误码：" + errorCode;
		}
		Log.d(LogTag, text);
	}

	// 通知点击回调 actionType=1为该消息被清除，actionType=0为该消息被点击
	@Override
	public void onNotifactionClickedResult(Context context, XGPushClickedResult message) {
		if (context == null || message == null) {
			return;
		}
		String text = "";
		if (message.getActionType() == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {
			text = "通知被打开 :" + message;
		} else if (message.getActionType() == XGPushClickedResult.NOTIFACTION_DELETED_TYPE) {
			text = "通知被清除 :" + message;
		}
		Log.d(LogTag, text);
	}

	@Override
	public void onRegisterResult(Context context, int errorCode, XGPushRegisterResult message) {
		if (context == null || message == null) {
			return;
		}
		String text = "";
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			// 在这里拿token
			final String token = message.getToken();
			text = "注册成功 token:" + token;
			new Thread() {
				public void run() {
					sendDeviceToken(token);
				};
			}.start();
		} else {
			text = message + "注册失败，错误码：" + errorCode;
		}
		Log.d(LogTag, text);
	}

	// 消息透传
	@Override
	public void onTextMessage(Context context, XGPushTextMessage message) {
		String text = "收到消息:" + message.toString();
		Log.d(LogTag, text);
		Log.d(LogTag, message.getContent());

		NotifyBean notifyBean = null;
		try {
			JSONObject obj = new JSONObject(message.getContent());
			if (obj != null) {
				notifyBean = new NotifyBean();
				notifyBean.type = obj.optString("type");
				notifyBean.amount = obj.optString("amount");
				notifyBean.amountMonth = obj.optString("amountMonth");
				notifyBean.sendDate = obj.optString("sendDate");
				notifyBean.detail = obj.optString("detail");
				notifyBean.noticeId = obj.optString("noticeId");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (notifyBean != null) {
			manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
			toNotify(context, notifyBean);
		}
	}

	private void toNotify(Context context, NotifyBean notifyBean) {
		if (context == null)
			context = mContext;

		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra(MainActivity.TYPE_NOTIFY, MainActivity.TYPE_NOTIFY_NOFITY);
//		intent.putExtra(MainActivity.TAG_NOTIFY_DATA, notifyBean);
		
		Intent intent_push = new Intent(AppConstants.BC_PUSH_COMING);
		intent_push.putExtra(MainActivity.TAG_NOTIFY_DATA, notifyBean);
		context.sendBroadcast(intent_push);
		
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

		int icon = R.drawable.app_icon_76;
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, notifyBean.type, when);
		
//		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.flags = Notification.FLAG_AUTO_CANCEL; 
		RemoteViews contentView = new RemoteViews(TTApplication.getInstance().getPackageName(),
				R.layout.custom_notifycation);
		contentView.setImageViewResource(R.id.iv_notifycation, R.drawable.app_icon_180);
		contentView.setTextViewText(R.id.tv_notifycation_title, notifyBean.type);
		contentView.setTextViewText(R.id.tv_notifycation_content, notifyBean.detail);
		notification.contentIntent = pendingIntent;
		notification.contentView = contentView;
		int id = NotifyIdGenerater.getInstance().generateNotifyId(notifyBean.sendDate);
		MyLog.d(LogTag, "notify id----->" + id);
		manager.notify(id, notification);
	}

	// private void toNotify(String content,String activityId,int type){
	//
	// // PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0,
	// // new Intent(mContext, MainActivity.class), 0);
	// // // 下面需兼容Android 2.x版本是的处理方式
	// // Notification notify1 = new Notification();
	// // notify1.icon = R.drawable.app_icon_76;
	// // notify1.tickerText = "酷客运动:" + text;
	// // notify1.when = System.currentTimeMillis();
	// // notify1.defaults = Notification.DEFAULT_SOUND;
	// // notify1.setLatestEventInfo(mContext, "酷客运动",
	// // text, pendingIntent);
	// // notify1.number = 1;
	// // notify1.flags |= Notification.FLAG_AUTO_CANCEL; //
	// FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
	// // // 通过通知管理器来发起通知。如果id不同，则每click，在statu那里增加一个提示
	// // manager.notify(GAME_NOTIFYCATION, notify1);
	//
	// Intent intent = new Intent();
	//
	// switch (type) {
	// case 0:
	// intent.setClass(mContext, GameJoinDetailActivity.class);
	// break;
	// case 1:
	// intent.setClass(mContext, GameJoinDetailActivity.class);
	// break;
	// case 2:
	// intent.setClass(mContext, GameVoteDetailActivity.class);
	// break;
	// case 3:
	// intent.setClass(mContext, GameResultDetailActivity.class);
	// break;
	// }
	// intent.putExtra("activityId", activityId);
	// PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0,
	// intent, 0);
	// // 通过Notification.Builder来创建通知，注意API Level
	// // API11之后才支持
	// Notification notify = new Notification.Builder(mContext)
	// .setSmallIcon(R.drawable.app_icon_76) //
	// 设置状态栏中的小图片，尺寸一般建议在24×24，这个图片同样也是在下拉状态栏中所显示，如果在那里需要更换更大的图片，可以使用setLargeIcon(Bitmap
	// // icon)
	// .setTicker("酷客运动:比赛消息" )// 设置在status
	// .setWhen(System.currentTimeMillis())
	// // bar上显示的提示文字
	// .setContentTitle("酷客运动")// 设置在下拉status
	// .setDefaults(Notification.DEFAULT_SOUND) //
	// bar后Activity，本例子中的NotififyMessage的TextView中显示的标题
	// .setContentText("酷客运动:" + content)// TextView中显示的详细内容
	// .setContentIntent(pendingIntent) // 关联PendingIntent
	// .setNumber(type) //
	// 在TextView的右方显示的数字，可放大图片看，在最右侧。这个number同时也起到一个序列号的左右，如果多个触发多个通知（同一ID），可以指定显示哪一个。
	// .getNotification(); // 需要注意build()是在API level
	// // 16及之后增加的，在API11中可以使用getNotificatin()来代替
	// notify.flags |= Notification.FLAG_AUTO_CANCEL;
	// manager.notify(type, notify);
	// }
	private void sendDeviceToken(String token) {
		// http地址
		String httpUrl = String.format("http://www.99lc.com:8087/iBusiness/rs/cws/uploadToken");
		// httpPost连接对象
		HttpPost httpPost = new HttpPost(httpUrl);
		// 使用NameValuePair来保存要传递的post阐述
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 添加要传递的参数
		params.add(new BasicNameValuePair("phoneNum",
				(String) SPUtils.getAppSpUtil().get(mContext, SPUtils.KEY_PHONENUM, "")));
		params.add(new BasicNameValuePair("token", token));
		try {
			// 设置字符集
			HttpEntity httpEntity = new UrlEncodedFormEntity(params, "utf-8");
			httpPost.setEntity(httpEntity);
			// 取得默认的HttpClient
			HttpClient httpClient = new DefaultHttpClient();
			// 取得HttpResponse
			HttpResponse httpResponse = httpClient.execute(httpPost);
			// HttpStatus.SC_OK)表示连接成功
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 取得返回的字符串
				String result = EntityUtils.toString(httpResponse.getEntity());
				Log.d(LogTag, result);
				if (!TextUtils.isEmpty(result)) {

				}

			} else {
				Log.d(LogTag, "请求错误");
			}
		} catch (ClientProtocolException e) {
			Log.e("PostActivity", "ClientProtocolException");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("PostActivity", "IOException");
			e.printStackTrace();
		}
	}

}
