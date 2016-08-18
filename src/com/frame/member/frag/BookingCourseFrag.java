package com.frame.member.frag;


import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.activity.SearchCoachActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * 预约课程
 * @author Ron
 *
 */
public class BookingCourseFrag extends BaseFrag implements OnClickListener{
	
	private LinearLayout ll_title_left_booking,ll_title_right_booking;
	private TextView tv_title_left_booking,tv_title_right_booking;
	private ImageView iv_search_coach;
	private BookingCourseOneFrag mBookingCourseOneFrag;
	private BookingCourseClassFrag mBookingCourseClassFrag;
	private FragmentManager mFragmentManager;
	
	
	boolean isRight;
	
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
		rootView = inflater.inflate(R.layout.frag_booking_course, container,
				false);
		
		initView();
		initOnclick();
		initProgress();
		return rootView;
	}
	private void initView(){
		ll_title_left_booking = (LinearLayout) findViewById(R.id.ll_title_left_booking);
		ll_title_right_booking = (LinearLayout) findViewById(R.id.ll_title_right_booking);
		tv_title_left_booking = (TextView) findViewById(R.id.tv_title_left_booking);
		tv_title_right_booking = (TextView) findViewById(R.id.tv_title_right_booking);
		iv_search_coach = (ImageView) findViewById(R.id.iv_search_coach);
	}
	private void initOnclick(){
		ll_title_left_booking.setOnClickListener(this);
		ll_title_right_booking.setOnClickListener(this);
		iv_search_coach.setOnClickListener(this);
	}
	//主逻辑代码
	private void initProgress(){
		tv_title_left_booking.setTextColor(0xffffffff);
		tv_title_right_booking.setTextColor(0xff878788);
		tv_title_left_booking.setTextSize(16);
		tv_title_right_booking.setTextSize(14);
		mFragmentManager = getActivity().getSupportFragmentManager();
		FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
		hideFragment(mTransaction);
		if(mBookingCourseOneFrag == null){
			mBookingCourseOneFrag = BookingCourseOneFrag.newInstance();
			mTransaction.add(R.id.fragment_content_course, mBookingCourseOneFrag).commit();
		}else{
			mTransaction.show(mBookingCourseOneFrag).commit();
		}
			
	}
	@Override
	public void onClick(View v) {
		FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
		switch (v.getId()) {
		case R.id.ll_title_left_booking:
			if(isRight){
				changeFrag();
				hideFragment(mTransaction);
				if(mBookingCourseOneFrag == null){
					mBookingCourseOneFrag = BookingCourseOneFrag.newInstance();
					mTransaction.add(R.id.fragment_content_course, mBookingCourseOneFrag).commit();
				}else{
					mTransaction.show(mBookingCourseOneFrag).commit();
				}
			}
			break;
		case R.id.ll_title_right_booking:
			if(!isRight){
				changeFrag();
				hideFragment(mTransaction);
				if(mBookingCourseClassFrag == null){
					mBookingCourseClassFrag = BookingCourseClassFrag.newInstance();
					mTransaction.add(R.id.fragment_content_course, mBookingCourseClassFrag).commit();
				}else{
					mTransaction.show(mBookingCourseClassFrag).commit();
				}
			}
			
			break;
		
		case R.id.iv_search_coach:
			startActivity(new Intent(getActivity(), SearchCoachActivity.class));
			break;

		
		}
	}
	//通过点击进行Fragment的切换
	private void changeFrag(){
		if(isRight){
			tv_title_left_booking.setTextColor(0xffffffff);
			tv_title_right_booking.setTextColor(0xff878788);
			tv_title_left_booking.setTextSize(16);
			tv_title_right_booking.setTextSize(14);
			isRight = false;
		}else{
			tv_title_right_booking.setTextColor(0xffffffff);
			tv_title_left_booking.setTextColor(0xff878788);
			tv_title_right_booking.setTextSize(16);
			tv_title_left_booking.setTextSize(14);
			isRight = true;
		}
	}
	private void hideFragment(FragmentTransaction transaction) {
		if (mBookingCourseOneFrag != null)
			transaction.hide(mBookingCourseOneFrag);
		if (mBookingCourseClassFrag != null)
			transaction.hide(mBookingCourseClassFrag);
		
	}
	
	




}
