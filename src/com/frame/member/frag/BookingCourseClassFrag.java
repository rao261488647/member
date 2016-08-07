package com.frame.member.frag;


import com.frame.member.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
	}
	private void initOnclick(){
		ll_item_1_booking.setOnClickListener(this);
		ll_item_2_booking.setOnClickListener(this);
		ll_item_3_booking.setOnClickListener(this);
		ll_item_4_booking.setOnClickListener(this);
	}
	//主逻辑代码
	private void initProgress(){
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
