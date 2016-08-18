package com.frame.member.frag;


import java.util.ArrayList;
import java.util.List;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.BookingClassParser;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.activity.BaseActivity;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.activity.ClassDetailActivity;
import com.frame.member.adapters.BookingClassAdapter;
import com.frame.member.bean.BookingClassResult;
import com.frame.member.widget.refreshlistview.PullToRefreshBase.Mode;
import com.frame.member.widget.refreshlistview.PullToRefreshBase;
import com.frame.member.widget.refreshlistview.PullToRefreshListView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * 滑雪班
 * 
 *
 */
public class BookingCourseClassFrag extends BaseFrag implements OnClickListener{
	
	private LinearLayout ll_item_1_booking,
				ll_item_2_booking,ll_item_3_booking,ll_item_4_booking;
	private TextView tv_item_1_booking,
				tv_item_2_booking,tv_item_3_booking,tv_item_4_booking;
	private ImageView iv_item_1_booking,iv_item_2_booking,iv_item_3_booking,
				iv_item_4_booking;
	private PullToRefreshListView lv_booking_course_class;
	private BookingClassAdapter adapter;
	private int rank = -1;
	
	private static BookingCourseClassFrag mFrag;
	public static BookingCourseClassFrag newInstance() {
		if(mFrag == null)
			mFrag = new BookingCourseClassFrag();
        
        return mFrag;
    }
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.frag_booking_course_class, container,
				false);
		
		initView();
		initOnclick();
		initProgress();
		return rootView;
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
		lv_booking_course_class = (PullToRefreshListView) findViewById(R.id.lv_booking_course_class);
	}
	private void initOnclick(){
		ll_item_1_booking.setOnClickListener(this);
		ll_item_2_booking.setOnClickListener(this);
		ll_item_3_booking.setOnClickListener(this);
		ll_item_4_booking.setOnClickListener(this);
	}
	//主逻辑代码
	private void initProgress(){
		lv_booking_course_class.setMode(Mode.BOTH);
		lv_booking_course_class.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startActivity(new Intent(getActivity(),ClassDetailActivity.class));
			}
		});
		lv_booking_course_class.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				page = 1;
				lv_booking_course_class.setMode(Mode.BOTH);
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
	int page;
	private List<BookingClassResult> list_result = new ArrayList<BookingClassResult>();
	//请求获取服务端数据
	private void getData(){
		if(page == 0)
			page = 1;
		BaseParser<List<BookingClassResult>> parser = new BookingClassParser();
		HttpRequest request = new HttpRequestImpl(getActivity(), 
				AppConstants.APP_SORT_STUDENT +"/skiingclass", parser);
		request.addParam("page_size", "10")
				.addParam("page_num", ""+page);
		((BaseActivity)getActivity()).getDataFromServer(request, false,callBack);
		
	}
	private DataCallback<List<BookingClassResult>> callBack = new DataCallback<List<BookingClassResult>>() {

		@Override
		public void processData(List<BookingClassResult> object, RequestResult result) {
			lv_booking_course_class.onRefreshComplete();
			if(result == RequestResult.Success){
				if(object != null){
					showToast(object.get(0).message);
					if("200".equals(object.get(0).code)){
						if(page == 1)
							list_result.clear();
						list_result.addAll(object);
						notifyAdapter();
					}else{
						lv_booking_course_class.setMode(Mode.PULL_FROM_START);
					}
				}else{
					lv_booking_course_class.setMode(Mode.PULL_FROM_START);
					if(page == 1){
						list_result.clear();
						notifyAdapter();
					}
				}
			}
		}
		
	};
	private void notifyAdapter() {
		if(adapter == null){
			adapter = new BookingClassAdapter(getActivity(), list_result);
			lv_booking_course_class.setAdapter(adapter);
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
