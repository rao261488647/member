package com.frame.member.wxapi;

import java.util.ArrayList;
import java.util.List;
import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.OrderSuccessParser;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.MyLog;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.BaseActivity;
import com.frame.member.activity.BookingDateActivity;
import com.frame.member.activity.ClassDetailActivity;
import com.frame.member.activity.MemberInfoResultActivity;
import com.frame.member.activity.MyOrderActivity;
import com.frame.member.bean.OrderSuccessResult;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

	private IWXAPI api;
	private LinearLayout ll_class_booked_info,ll_coach_booked_info;
	private TextView tv_coach_name,tv_booked_date,tv_booked_place,tv_back_to_book,tv_coach_meet;
	private ImageView iv_coach_profile;
	private List<String> list_date = new ArrayList<String>();
	
	private TextView tv_class_name,tv_booked_date_class,tv_booked_place_class;
	private int type = 0; //0为一对一，1为班课,3为会员升级

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		type = (Integer) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_WXPAY_TYPE, 0);
		Log.d("WXPayEntryActivity","onResp type = "+type);
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			MyLog.i("微信支付结果", "微信支付结果------->" + String.valueOf(resp.errCode));
//			Toast.makeText(getApplicationContext(), "微信支付结果: "+String.valueOf(resp.errCode)+"type ="+type, 0).show();
			if(resp.errCode == 0){
//				showToast("支付成功"+"type = "+type);
				
				if(type == 0){
					ll_class_booked_info.setVisibility(View.GONE);
					ll_coach_booked_info.setVisibility(View.VISIBLE);
					getData();
				}else if(type == 1){
					ll_class_booked_info.setVisibility(View.VISIBLE);
					ll_coach_booked_info.setVisibility(View.GONE);
					getData();
				}else if(type == 2){
//					showToast("这是会员升级！！");
					startActivity(new Intent(WXPayEntryActivity.this,MemberInfoResultActivity.class));
					finish();
				}
				
			}else if(resp.errCode == -1){
				showToast("支付失败");
				finish();
			}else if(resp.errCode == -2){
				showToast("取消支付");
				finish();
			}
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_booking_finished);
//		type = (Integer) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_WXPAY_TYPE, 0);
		Log.d("WXPayEntryActivity","loadViewLayout type = "+type);
//		if(type == 2){
//			showToast("这是会员升级！！");
//			appManager.finishActivity();
//			startActivity(new Intent(WXPayEntryActivity.this,MemberInfoResultActivity.class));
//		}
	}

	@Override
	protected void findViewById() {
		ll_class_booked_info = (LinearLayout) findViewById(R.id.ll_class_booked_info);
		ll_coach_booked_info = (LinearLayout) findViewById(R.id.ll_coach_booked_info);
		
		tv_coach_name = (TextView) findViewById(R.id.tv_coach_name);
		tv_booked_date = (TextView) findViewById(R.id.tv_booked_date);
		tv_booked_place = (TextView) findViewById(R.id.tv_booked_place);
		tv_back_to_book = (TextView) findViewById(R.id.tv_back_to_book);
		tv_coach_meet = (TextView) findViewById(R.id.tv_coach_meet);
		iv_coach_profile = (ImageView) findViewById(R.id.iv_coach_profile);
		
		tv_class_name = (TextView) findViewById(R.id.tv_class_name);
		tv_booked_date_class = (TextView) findViewById(R.id.tv_booked_date_class);
		tv_booked_place_class = (TextView) findViewById(R.id.tv_booked_place_class);
	}

	@Override
	protected void setListener() {
		tv_back_to_book.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(0 == type){//0为约教
					appManager.finishActivity();
					appManager.finishActivity(BookingDateActivity.class);
				}else{
					appManager.finishActivity();
					appManager.finishActivity(ClassDetailActivity.class);
				}
				
			}
		});
		tv_coach_meet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(WXPayEntryActivity.this, MyOrderActivity.class));
			}
		});
	}

	@Override
	protected void processLogic() {
		api = WXAPIFactory.createWXAPI(this, AppConstants.APP_ID_WX);
		api.handleIntent(getIntent(), this);
		
	}
	//获取支付成功之后的主数据
	private void getData(){
		BaseParser<OrderSuccessResult> parser = new OrderSuccessParser();
		HttpRequestImpl request = new HttpRequestImpl(
				this, AppConstants.APP_SORT_STUDENT+"/ordermeetsuccess", parser);
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""))
				.addParam("payorderNum", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_PAYORDERNUM, ""));
		DataCallback<OrderSuccessResult> callback = new DataCallback<OrderSuccessResult>() {

			@Override
			public void processData(OrderSuccessResult object, RequestResult result) {
				if(object != null){
					if("1".equals(object.courseType)){//"1"为约教，2为班课
						tv_coach_name.setText(object.coachName);
						TTApplication.getInstance().disPlayImageDef(object.headImg, iv_coach_profile);
						tv_booked_place.setText(object.skifieldName);
						String[] arr_dates = object.dates.split(" ");
						StringBuffer sb = new StringBuffer();
						if(arr_dates != null && arr_dates.length>0){
							for(int i = 0;i<arr_dates.length;i++){
								if(i == 2){
									sb.append(arr_dates[i]+"\n");
								}else{
									sb.append(arr_dates[i]+"、");
								}
								
							}
							if(arr_dates.length == 3){
								sb.deleteCharAt(sb.length()-2);
							}else{
								sb.deleteCharAt(sb.length()-1);
							}
							
						}
						tv_booked_date.setText(sb.toString());
					}else{
						tv_class_name.setText(object.courseName);
						tv_booked_date_class.setText(object.dates);
						tv_booked_place_class.setText(object.skifieldName);
						tv_back_to_book.setText("返回列表");
					}
					
					
					
				}
			}
		};
		getDataFromServer(request, callback);
	}
	
}