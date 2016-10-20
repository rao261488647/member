package com.frame.member.frag;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.OrderParser;
import com.frame.member.Utils.CommonUtil;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.BaseActivity;
import com.frame.member.activity.MemberInfoActivity;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.bean.MemberInfoResult;
import com.frame.member.bean.OrderResult;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class MemberInfoDialogFrag extends DialogFragment implements OnClickListener{
	
	private TextView tv_weixin_pay,tv_cancel,tv_name_class,
					tv_coach_price_num;
	private ImageView iv_card_member;
	private MemberInfoResult result_member = new MemberInfoResult();
	// IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
//		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.dialog_frag_member_info, container);
		
		tv_weixin_pay = (TextView) view.findViewById(R.id.tv_weixin_pay);
		tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
		tv_name_class = (TextView) view.findViewById(R.id.tv_name_class);
		tv_coach_price_num = (TextView) view.findViewById(R.id.tv_coach_price_num);
		iv_card_member = (ImageView) view.findViewById(R.id.iv_card_member);
		
		result_member = ((MemberInfoActivity)getActivity()).result_info;
		tv_weixin_pay.setOnClickListener(this);
		tv_cancel.setOnClickListener(this);
		if(1 == ((MemberInfoActivity)getActivity()).current_dot){
			tv_name_class.setText("升级为蓝卡会员");
			iv_card_member.setImageResource(R.drawable.card_blue);
		}else if(2 == ((MemberInfoActivity)getActivity()).current_dot){
			tv_name_class.setText("升级为黑卡会员");
			iv_card_member.setImageResource(R.drawable.card_black);
		}
		
		tv_coach_price_num.setText("¥"+((MemberInfoActivity)getActivity()).price);
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
//			getActivity().startActivity(new Intent(getActivity(),BookingFinishedActivity.class));
			getWxInfo();
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
					AppConstants.APP_SORT_STUDENT + "/memberupgrade", parser);
			request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(getActivity(), SPUtils.KEY_MEMBERUSERID, ""))
					.addParam("token", (String) SPUtils.getAppSpUtil().get(getActivity(), SPUtils.KEY_TOKEN, ""))
					.addParam("gradeType", ""+((MemberInfoActivity)getActivity()).current_dot)
					.addParam("price", ((MemberInfoActivity)getActivity()).price);
					
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
						SPUtils.getAppSpUtil().put(getActivity(), SPUtils.KEY_WXPAY_TYPE, 2);//把支付类型变成2
						SPUtils.getAppSpUtil().put(getActivity(), 
								SPUtils.KEY_WXPAY_MEMBERTYPE, ((MemberInfoActivity)getActivity()).current_dot);//根据升级的会员类型来设置之后界面显示的样式
					}
				}
			};
			((BaseActivity)getActivity()).getDataFromServer(request, callback);
		}
	
}
