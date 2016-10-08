package com.frame.member.frag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.OtoCoachMeetParser;
import com.frame.member.Utils.CommonUtil;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.BookingDateActivity;
import com.frame.member.activity.BookingFinishedActivity;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.bean.OtoCoachMeetResult;
import com.frame.member.widget.calendar.CustomDate;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class CoachBookingDialogFrag extends DialogFragment implements OnClickListener{
	
	private GridView gv_days_booking;
	private TextView tv_weixin_pay,tv_cancel,tv_coach_price_num;
	private List<String> list_date = new ArrayList<String>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
//		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.dialog_frag_booking_date, container);
		gv_days_booking = (GridView) view.findViewById(R.id.gv_days_booking);
		tv_weixin_pay = (TextView) view.findViewById(R.id.tv_weixin_pay);
		tv_coach_price_num = (TextView) view.findViewById(R.id.tv_coach_price_num);
		tv_weixin_pay.setOnClickListener(this);
		tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
		tv_cancel.setOnClickListener(this);
		for(CustomDate date:((BookingDateActivity)getActivity()).mDateList){
			String str_date = date.toSecString();
			list_date.add(str_date);
			str_date = null;
		}
		initView();
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
	private void initView(){
		
		gv_days_booking.setAdapter(new ArrayAdapter<String>(getActivity(), 
				R.layout.text_nopadding, 
				list_date));
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_weixin_pay:
			getActivity().startActivity(new Intent(getActivity(),BookingFinishedActivity.class));
			break;
		case R.id.tv_cancel:
			dismiss();
			break;

		default:
			break;
		}
	}

	
}
