package com.frame.member.frag;


import java.util.ArrayList;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.BookingOneParser;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.activity.BaseActivity;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.adapters.BookingOneAdapter;
import com.frame.member.bean.BookingOneResult;
import com.frame.member.widget.refreshlistview.PullToRefreshBase;
import com.frame.member.widget.refreshlistview.PullToRefreshBase.Mode;
import com.frame.member.widget.refreshlistview.PullToRefreshGridView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 
 * 一对一
 * 
 *
 */
public class BookingCourseOneFrag extends BaseFrag implements OnClickListener{
	
	private LinearLayout ll_item_1_booking,
				ll_item_2_booking,ll_item_3_booking,ll_item_4_booking;
	private TextView tv_item_1_booking,
				tv_item_2_booking,tv_item_3_booking,tv_item_4_booking;
	private ImageView iv_item_1_booking,iv_item_2_booking,iv_item_3_booking,
				iv_item_4_booking;
	private int rank = -1;
	private PullToRefreshGridView ptrg_booking;
	private BookingOneAdapter adapter;
	
	private static BookingCourseOneFrag mFrag;
	public static BookingCourseOneFrag newInstance() {
		if(mFrag == null)
			mFrag = new BookingCourseOneFrag();
        
        return mFrag;
    }
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.frag_booking_course_one, container,
				false);
		
		return rootView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initOnclick();
		initProgress();
	}
	private void initView(){
		ll_item_1_booking = (LinearLayout) findViewById(R.id.ll_item_1_booking);
		ll_item_2_booking = (LinearLayout) findViewById(R.id.ll_item_2_booking);
		ll_item_3_booking = (LinearLayout) findViewById(R.id.ll_item_3_booking);
		ll_item_4_booking = (LinearLayout) findViewById(R.id.ll_item_4_booking);
		tv_item_1_booking = (TextView) findViewById(R.id.tv_item_1_booking);
		tv_item_2_booking = (TextView) findViewById(R.id.tv_item_2_booking);
		tv_item_3_booking = (TextView) findViewById(R.id.tv_item_3_booking);
		tv_item_4_booking = (TextView) findViewById(R.id.tv_item_4_booking);
		iv_item_1_booking = (ImageView) findViewById(R.id.iv_item_1_booking);
		iv_item_2_booking = (ImageView) findViewById(R.id.iv_item_2_booking);
		iv_item_3_booking = (ImageView) findViewById(R.id.iv_item_3_booking);
		iv_item_4_booking = (ImageView) findViewById(R.id.iv_item_4_booking);
		ptrg_booking = (PullToRefreshGridView) findViewById(R.id.ptrg_booking);
		
	}
	private void initOnclick(){
		ll_item_1_booking.setOnClickListener(this);
		ll_item_2_booking.setOnClickListener(this);
		ll_item_3_booking.setOnClickListener(this);
		ll_item_4_booking.setOnClickListener(this);
	}
	int page = 1;
	private ArrayList<BookingOneResult.Coach> list_coach = new ArrayList<BookingOneResult.Coach>();
	
	//主逻辑代码
	private void initProgress(){
		ptrg_booking.setMode(Mode.BOTH);
		ptrg_booking.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				page = 1;
				ptrg_booking.setMode(Mode.BOTH);
				getData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				page ++;
				getData();
			}
		});
		getData();
		
	}
	private void getData(){
		if(page < 1){
			page = 1;
		}
		BaseParser<BookingOneResult> parser = new BookingOneParser();
		HttpRequestImpl request = new HttpRequestImpl(getActivity(), 
				AppConstants.APP_SORT_STUDENT +"/otocoach", parser);
		request.addParam("page_size", "10")
				.addParam("page_num", ""+page);
		((BaseActivity)getActivity()).getDataFromServer(request, callback);
	}
	private DataCallback<BookingOneResult> callback = new DataCallback<BookingOneResult>() {

		@Override
		public void processData(BookingOneResult object, RequestResult result) {
			ptrg_booking.onRefreshComplete();
			if(result == RequestResult.Success){
				if(object != null){
					showToast(object.message);
					if("200".equals(object.code)){
						if(object.coaches == null || object.coaches.size() == 0){
							ptrg_booking.setMode(Mode.PULL_FROM_START);
							if(page == 1){
								list_coach.clear();
								notifyAdapter();
							}
							return;
						}
						if(page == 1)
							list_coach.clear();
						list_coach.addAll(object.coaches);
						notifyAdapter();
							
					}else{
						ptrg_booking.setMode(Mode.PULL_FROM_START);
					}
				}else{
					ptrg_booking.setMode(Mode.PULL_FROM_START);
				}
			}
		}
	};
	private void notifyAdapter(){
		if(adapter == null){
			adapter = new BookingOneAdapter(getActivity(), list_coach);
			ptrg_booking.setAdapter(adapter);
		}else{
			adapter.notifyDataSetChanged();
		}
	}
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		
		case R.id.ll_item_1_booking:
			if(rank != 0){
				changeItem(0);
			}else{
				tv_item_1_booking.setTextColor(0xff8d8d8d);
				iv_item_1_booking.setImageResource(R.drawable.more_choices);
				rank = -1;
			}
			
			break;
		case R.id.ll_item_2_booking:
			
			if(rank != 1){
				changeItem(1);
			}else{
				tv_item_2_booking.setTextColor(0xff8d8d8d);
				iv_item_2_booking.setImageResource(R.drawable.more_choices);
				rank = -1;
			}
			break;
		case R.id.ll_item_3_booking:
			if(rank != 2){
				changeItem(2);
			}else{
				tv_item_3_booking.setTextColor(0xff8d8d8d);
				iv_item_3_booking.setImageResource(R.drawable.more_choices);
				rank = -1;
			}
			break;
		case R.id.ll_item_4_booking:
			if(rank != 3){
				changeItem(3);
			}else{
				tv_item_4_booking.setTextColor(0xff8d8d8d);
				iv_item_4_booking.setImageResource(R.drawable.more_choices);
				rank = -1;
			}
			break;
		

		
		}
	}

	//通过点击按钮来进行状态变化
	private void changeItem(int rank){
		tv_item_1_booking.setTextColor(0xff8d8d8d);
		tv_item_2_booking.setTextColor(0xff8d8d8d);
		tv_item_3_booking.setTextColor(0xff8d8d8d);
		tv_item_4_booking.setTextColor(0xff8d8d8d);
		iv_item_1_booking.setImageResource(R.drawable.more_choices);
		iv_item_2_booking.setImageResource(R.drawable.more_choices);
		iv_item_3_booking.setImageResource(R.drawable.more_choices);
		iv_item_4_booking.setImageResource(R.drawable.more_choices);
		switch (rank) {
		case 0:
			tv_item_1_booking.setTextColor(0xff5a5a5a);
			iv_item_1_booking.setImageResource(R.drawable.more_choice_selected);
			this.rank = 0;
			break;
		case 1:
			tv_item_2_booking.setTextColor(0xff5a5a5a);
			iv_item_2_booking.setImageResource(R.drawable.more_choice_selected);
			this.rank = 1;
			break;
		case 2:
			tv_item_3_booking.setTextColor(0xff5a5a5a);
			iv_item_3_booking.setImageResource(R.drawable.more_choice_selected);
			this.rank =2;
			break;
		case 3:
			tv_item_4_booking.setTextColor(0xff5a5a5a);
			iv_item_4_booking.setImageResource(R.drawable.more_choice_selected);
			this.rank = 3;
			break;

		}
	}
	




}
