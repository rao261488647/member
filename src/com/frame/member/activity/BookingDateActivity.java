package com.frame.member.activity;

import java.sql.Date;
import java.util.LinkedList;

import com.frame.member.R;
import com.frame.member.widget.calendar.CalendarCard;
import com.frame.member.widget.calendar.CalendarCard.OnCellClickListener;
import com.frame.member.widget.calendar.CalendarViewAdapter;
import com.frame.member.widget.calendar.CustomDate;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class BookingDateActivity extends BaseActivity implements OnCellClickListener, OnClickListener {

	private ViewPager mViewPager;
	private ImageView iv_left_month, iv_right_month;
	private TextView tv_date_coach_big, tv_date_coach_small, tv_date_item_1, tv_date_item_2, tv_date_item_3,
			tv_date_item_4, tv_date_item_5, tv_date_item_6, tv_booking_now;
	private int mCurrentIndex = 498;
	private CalendarCard[] mShowViews;
	private CalendarViewAdapter<CalendarCard> adapter;
	private SildeDirection mDirection = SildeDirection.NO_SILDE;

	enum SildeDirection {
		RIGHT, LEFT, NO_SILDE;
	}

//	// 已经选中的日期数量
//	private int num_selected = 0;
	// 选中的日期集合
	private LinkedList<CustomDate> mDateList = new LinkedList<CustomDate>();

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_booking_date);
	}

	@Override
	protected void findViewById() {
		mViewPager = (ViewPager) findViewById(R.id.vp_calendar);
		iv_left_month = (ImageView) findViewById(R.id.iv_left_month);
		iv_right_month = (ImageView) findViewById(R.id.iv_right_month);
		tv_date_coach_big = (TextView) findViewById(R.id.tv_date_coach_big);
		tv_date_coach_small = (TextView) findViewById(R.id.tv_date_coach_small);
		tv_date_item_1 = (TextView) findViewById(R.id.tv_date_item_1);
		tv_date_item_2 = (TextView) findViewById(R.id.tv_date_item_2);
		tv_date_item_3 = (TextView) findViewById(R.id.tv_date_item_3);
		tv_date_item_4 = (TextView) findViewById(R.id.tv_date_item_4);
		tv_date_item_5 = (TextView) findViewById(R.id.tv_date_item_5);
		tv_date_item_6 = (TextView) findViewById(R.id.tv_date_item_6);
		tv_booking_now = (TextView) findViewById(R.id.tv_booking_now);

	}

	@Override
	protected void setListener() {
		iv_left_month.setOnClickListener(this);
		iv_right_month.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		iv_title_back.setImageResource(R.drawable.btn_back_normal);
		tv_title.setText("选择预约日期");
		CalendarCard[] views = new CalendarCard[12];
		for (int i = 0; i < 12; i++) {
			views[i] = new CalendarCard(this, this);
		}
		adapter = new CalendarViewAdapter<CalendarCard>(views);
		setViewPager();
	}

	private void setViewPager() {
		mViewPager.setAdapter(adapter);
		mViewPager.setCurrentItem(498);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				measureDirection(position);
				updateCalendarView(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	/**
	 * 计算方向
	 * 
	 * @param arg0
	 */
	private void measureDirection(int arg0) {

		if (arg0 > mCurrentIndex) {
			mDirection = SildeDirection.RIGHT;

		} else if (arg0 < mCurrentIndex) {
			mDirection = SildeDirection.LEFT;
		}
		mCurrentIndex = arg0;
	}

	// 更新日历视图
	private void updateCalendarView(int arg0) {
		mShowViews = adapter.getAllItems();
		if (mDirection == SildeDirection.RIGHT) {
			mShowViews[arg0 % mShowViews.length].rightSlide();
		} else if (mDirection == SildeDirection.LEFT) {
			mShowViews[arg0 % mShowViews.length].leftSlide();
		}
		mDirection = SildeDirection.NO_SILDE;
	}

	// 点击日期的事件
	@Override
	public void clickDate(CustomDate date, boolean isContain) {
		
			int position = 0;
			if (isContain) {
				if(mDateList.size() > 0){
					showToast("已经预约了");
//					for(CustomDate cd:mDateList){
//						if(cd.equals(date)){
//							mDateList.remove(cd);
//							updateDate();
//						}
//					}
					for(int i= 0; i < mDateList.size(); i++){
						if(mDateList.get(i).equals(date)){
							position = i;
							break;
						}
					}
					mDateList.remove(position);
					updateDate();
					
				}
				
			} else {
				if(mDateList.size() < 6){
					mDateList.add(date);
//					num_selected++;
					updateDate();
				}else{
					showToast("最多预约六天");
				}
				
			}

	}

	// 滑动改变月份的事件
	@Override
	public void changeDate(CustomDate date) {
		tv_date_coach_big.setText(date.year + "." + date.month);
		tv_date_coach_small.setText((date.month_eng)[date.month - 1] + " " + date.year);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_left_month:
			mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
			break;
		case R.id.iv_right_month:
			mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
			break;

		default:
			break;
		}
	}

	//更新选中的日期
	private void updateDate() {
		resetDate();
		switch (mDateList.size()) {
		case 0:
			
			break;
		case 1:
			tv_date_item_1.setBackgroundResource(R.drawable.background_date_yellow);
			tv_date_item_1.setText(mDateList.get(0).day+"");
			break;
		case 2:
			tv_date_item_1.setBackgroundResource(R.drawable.background_date_yellow);
			tv_date_item_1.setText(mDateList.get(0).day+"");
			tv_date_item_2.setBackgroundResource(R.drawable.background_date_yellow);
			tv_date_item_2.setText(mDateList.get(1).day+"");
			break;
		case 3:
			tv_date_item_1.setBackgroundResource(R.drawable.background_date_yellow);
			tv_date_item_1.setText(mDateList.get(0).day+"");
			tv_date_item_2.setBackgroundResource(R.drawable.background_date_yellow);
			tv_date_item_2.setText(mDateList.get(1).day+"");
			tv_date_item_3.setBackgroundResource(R.drawable.background_date_yellow);
			tv_date_item_3.setText(mDateList.get(2).day+"");
			break;
		case 4:
			tv_date_item_1.setBackgroundResource(R.drawable.background_date_yellow);
			tv_date_item_1.setText(mDateList.get(0).day+"");
			tv_date_item_2.setBackgroundResource(R.drawable.background_date_yellow);
			tv_date_item_2.setText(mDateList.get(1).day+"");
			tv_date_item_3.setBackgroundResource(R.drawable.background_date_yellow);
			tv_date_item_3.setText(mDateList.get(2).day+"");
			tv_date_item_4.setBackgroundResource(R.drawable.background_date_yellow);
			tv_date_item_4.setText(mDateList.get(3).day+"");
			break;
		case 5:
			tv_date_item_1.setBackgroundResource(R.drawable.background_date_yellow);
			tv_date_item_1.setText(mDateList.get(0).day+"");
			tv_date_item_2.setBackgroundResource(R.drawable.background_date_yellow);
			tv_date_item_2.setText(mDateList.get(1).day+"");
			tv_date_item_3.setBackgroundResource(R.drawable.background_date_yellow);
			tv_date_item_3.setText(mDateList.get(2).day+"");
			tv_date_item_4.setBackgroundResource(R.drawable.background_date_yellow);
			tv_date_item_4.setText(mDateList.get(3).day+"");
			tv_date_item_5.setBackgroundResource(R.drawable.background_date_yellow);
			tv_date_item_5.setText(mDateList.get(4).day+"");
			break;
		case 6:
			tv_date_item_1.setBackgroundResource(R.drawable.background_date_yellow);
			tv_date_item_1.setText(mDateList.get(0).day+"");
			tv_date_item_2.setBackgroundResource(R.drawable.background_date_yellow);
			tv_date_item_2.setText(mDateList.get(1).day+"");
			tv_date_item_3.setBackgroundResource(R.drawable.background_date_yellow);
			tv_date_item_3.setText(mDateList.get(2).day+"");
			tv_date_item_4.setBackgroundResource(R.drawable.background_date_yellow);
			tv_date_item_4.setText(mDateList.get(3).day+"");
			tv_date_item_5.setBackgroundResource(R.drawable.background_date_yellow);
			tv_date_item_5.setText(mDateList.get(4).day+"");
			tv_date_item_6.setBackgroundResource(R.drawable.background_date_yellow);
			tv_date_item_6.setText(mDateList.get(5).day+"");
			break;

		default:
			break;
		}
	}
	//重置日期显示
	private void resetDate(){
		tv_date_item_1.setBackgroundResource(R.drawable.background_date_normal);
		tv_date_item_2.setBackgroundResource(R.drawable.background_date_normal);
		tv_date_item_3.setBackgroundResource(R.drawable.background_date_normal);
		tv_date_item_4.setBackgroundResource(R.drawable.background_date_normal);
		tv_date_item_5.setBackgroundResource(R.drawable.background_date_normal);
		tv_date_item_6.setBackgroundResource(R.drawable.background_date_normal);
		tv_date_item_1.setText("");
		tv_date_item_2.setText("");
		tv_date_item_3.setText("");
		tv_date_item_4.setText("");
		tv_date_item_5.setText("");
		tv_date_item_6.setText("");
	}

}
