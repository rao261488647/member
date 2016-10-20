package com.frame.member.frag;

import java.util.ArrayList;
import java.util.List;
import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.OrderParser;
import com.frame.member.Utils.CommonUtil;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.BookingDateActivity;
import com.frame.member.activity.BaseActivity;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.bean.OrderResult;
import com.frame.member.bean.OtoCoachMeetResult;
import com.frame.member.widget.MyRantingBar;
import com.frame.member.widget.calendar.CustomDate;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class CoachBookingDialogFrag extends DialogFragment implements OnClickListener{
	
	private GridView gv_days_booking;
	private TextView tv_weixin_pay,tv_cancel,tv_coach_price_num,tv_coach_name,tv_days_booking;
	private ImageView iv_coach_profile;
//	private RatingBar rb_booking_one;
	private MyRantingBar mMyRantingBar;
	private List<String> list_date = new ArrayList<String>();
	private OtoCoachMeetResult result_coach;
	private StringBuffer yuyueDates = new StringBuffer();//预约时间字符串拼接
	
	// IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
//		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.dialog_frag_booking_date, container);
		
		gv_days_booking = (GridView) view.findViewById(R.id.gv_days_booking);
		tv_weixin_pay = (TextView) view.findViewById(R.id.tv_weixin_pay);
		tv_coach_price_num = (TextView) view.findViewById(R.id.tv_coach_price_num);
		tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
		iv_coach_profile = (ImageView) view.findViewById(R.id.iv_coach_profile);
//		rb_booking_one = (RatingBar) view.findViewById(R.id.rb_booking_one);
		mMyRantingBar = (MyRantingBar) view.findViewById(R.id.mMyRantingBar);
		tv_coach_name = (TextView) view.findViewById(R.id.tv_coach_name);
		tv_days_booking = (TextView) view.findViewById(R.id.tv_days_booking);
		
		tv_weixin_pay.setOnClickListener(this);
		tv_cancel.setOnClickListener(this);
		result_coach = ((BookingDateActivity)getActivity()).result_coach;
		for(CustomDate date:((BookingDateActivity)getActivity()).mDateList){
			String str_date = date.toSecString();
			list_date.add(str_date);
			str_date = null;
			yuyueDates.append(date.toString()+",");
		}
		yuyueDates.deleteCharAt(yuyueDates.length()-1);
		gv_days_booking.setAdapter(new ArrayAdapter<String>(getActivity(), 
				R.layout.text_nopadding, 
				list_date));
		TTApplication.getInstance().disPlayImageDef(result_coach.headImg, iv_coach_profile);
//		rb_booking_one.setRating(Float.parseFloat(result_coach.coachStar));
		mMyRantingBar.setRantCount((int) Float.parseFloat(result_coach.coachStar));
		tv_coach_name.setText(result_coach.coachName);
		tv_coach_price_num.setText("¥"+result_coach.trainfee);
		tv_days_booking.setText(result_coach.areaName+"-"+
				((BookingDateActivity)getActivity()).skifieldName+"-共"+list_date.size()+"天");
		
		return view;
	}
	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
		 Window window = getDialog().getWindow();
		 android.view.WindowManager.LayoutParams attributes = window.getAttributes();
		 int height = CommonUtil.getScreenHeight(getActivity()) - 
				 CommonUtil.getStatusHeight(getActivity()) - 100;
		 //must setBackgroundDrawable(TRANSPARENT) in onActivityCreated()
		 window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		  window.setLayout(LayoutParams.MATCH_PARENT, height);
		  window.setGravity(Gravity.CENTER);
		  window.setAttributes(attributes);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_weixin_pay:
			getWxInfo();
//			getActivity().startActivity(new Intent(getActivity(),BookingFinishedActivity.class));
			break;
		case R.id.tv_cancel:
			dismiss();
			break;

		default:
			break;
		}
	}
	//提交订单，从服务器获得微信支付需要的数据
	private void getWxInfo(){
		BaseParser<OrderResult> parser = new OrderParser();
		HttpRequestImpl request = new HttpRequestImpl(getActivity(), 
				AppConstants.APP_SORT_STUDENT + "/order", parser);
		request.addParam("coachId", getActivity().getIntent().getStringExtra("coachId"))
				.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(getActivity(), SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("token", (String) SPUtils.getAppSpUtil().get(getActivity(), SPUtils.KEY_TOKEN, ""))
				.addParam("skifieldId", ((BookingDateActivity)getActivity()).skifieldId)
				.addParam("yuyueDates", yuyueDates.toString());
				
		DataCallback<OrderResult> callback = new DataCallback<OrderResult>() {

			@Override
			public void processData(OrderResult object, RequestResult result) {
				if(object != null){
					// 通过WXAPIFactory工厂，获取IWXAPI的实例
			    	api = WXAPIFactory.createWXAPI(getActivity(), AppConstants.APP_ID_WX, false);
			    	// 将该app注册到微信
				    api.registerApp(AppConstants.APP_ID_WX);
				    PayReq req = new PayReq();
					req.appId			= object.appid;
					req.partnerId		= object.partnerid;
					req.prepayId		= object.prepayid;
					req.nonceStr		= object.noncestr;
					req.timeStamp		= object.timestamp;
					req.packageValue	= "Sign=WXPay";
					req.sign			= object.sign;
					req.extData			= "app data"; // optional
					SPUtils.getAppSpUtil().put(getActivity(), SPUtils.KEY_PAYORDERNUM, object.payorderNum);
					Toast.makeText(getActivity(), "正常调起支付", Toast.LENGTH_SHORT).show();
					Log.d("weixin", req.toString());
					// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
					api.sendReq(req);
					tv_weixin_pay.setClickable(false);
					SPUtils.getAppSpUtil().put(getActivity(), SPUtils.KEY_WXPAY_TYPE, 0);//把支付类型变成0
				}
			}
		};
		((BaseActivity)getActivity()).getDataFromServer(request, callback);
	}

	
}
