package com.frame.member.frag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Utils.CommonUtil;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.BaseActivity;
import com.frame.member.activity.PaymentActivity;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * 
 * 预约课程
 * @author Ron
 *
 */
public class BookingCourseFrag extends BaseFrag implements OnClickListener {
	
	private TextView tv_mypayment_pay_commit,tv_money_lift_cur_month,
	tv_pay_date_per_month,tv_payed_rent_month,tv_rent_per_month,tv_should_payment,tv_payed_deposit,
	tv_contract_validdate,tv_signed_deposit_total,tv_contract_startdate,tv_charge_status,tv_contract_deadline;
	
	private LinearLayout ll_pay_rec_container;
	
	public static BookingCourseFrag newInstance(String title) {
		BookingCourseFrag fragment = new BookingCourseFrag();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.FRAG_TITLE_KEY, title);
        fragment.setArguments(bundle);
        return fragment;
    }
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.frag_charge, container,
				false);
		
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("约课");
		
		
		requestPayFeeList();
		return rootView;
	}
	
	
	
	private void requestPayFeeList(){
		HttpRequest request_pay_info = new HttpRequestImpl(getActivity(),
				AppConstants.PAYFEELIST, null);

		request_pay_info.addParam(
				"cell",
				(String) SPUtils.getAppSpUtil().get(getActivity(),
						SPUtils.KEY_PHONENUM, ""));

		((BaseActivity) getActivity()).getDataFromServer(request_pay_info,
				false, new DataCallback<String>() {

					@Override
					public void processData(String object,
							RequestResult result) {
						if (result == RequestResult.Success && !TextUtils.isEmpty(object)) {

//							MyLog.i(TAG_FRAGMENT,
//									"缴费接口返回信息----》" + object);
							try {
								JSONObject resultObj = new JSONObject(object);
								if(resultObj != null && "200".equals(resultObj.optString("status"))){
									String conTerm = resultObj.optString("conTerm");
									tv_contract_deadline.setText(conTerm);
									
									String status = resultObj.optString("status");
									tv_charge_status.setText(status);
									
									String conValidDate = resultObj.optString("conValidDate");
									tv_contract_startdate.setText(conValidDate);
									
									String signedCortAmount = resultObj.optString("signedCortAmount");
									tv_signed_deposit_total.setText(signedCortAmount);
									
//									String conTerm = resultObj.optString("conTerm"); // 合同有效日期
									
									String payCort = resultObj.optString("payCort");
									tv_payed_deposit.setText(payCort);
									
									String curSdFee = resultObj.optString("curSdFee");
									tv_should_payment.setText(curSdFee);
									
									String perMonthRent = resultObj.optString("perMonthRent");
									tv_rent_per_month.setText(perMonthRent);
									
									String cerrPayMoney = resultObj.optString("cerrPayMoney");
									tv_payed_rent_month.setText(cerrPayMoney);
									
									String perPayDay = resultObj.optString("perPayDay");
									tv_pay_date_per_month.setText(perPayDay);
									
									String curMonFee = resultObj.optString("curMonFee");
									tv_money_lift_cur_month.setText(curMonFee);
									
									JSONArray payJSONArray = resultObj.optJSONArray("payList");
									if(payJSONArray != null && payJSONArray.length() > 0){
										for(int i = 0; i < payJSONArray.length(); i++){
											JSONObject payJSONObject = payJSONArray.optJSONObject(i);
											if(payJSONObject != null){
												String payDate = payJSONObject.optString("payDate");
												String payMoney = payJSONObject.optString("payMoney");
												
												View item_pay_rec = View.inflate(getActivity(), R.layout.item_pay_rec, null);
												
												TextView tv_pay_rec_item_date = (TextView) item_pay_rec.findViewById(R.id.tv_pay_rec_item_date);
												TextView tv_pay_rec_item_count = (TextView) item_pay_rec.findViewById(R.id.tv_pay_rec_item_count);
												
												tv_pay_rec_item_date.setText(payDate);
												tv_pay_rec_item_count.setText(payMoney);
												
												ll_pay_rec_container.addView(item_pay_rec, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, CommonUtil.dip2px(getActivity(), 40)));
											}
										}
									}
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
				});
	}


	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(),PaymentActivity.class);
		startActivity(intent);
	}
}
