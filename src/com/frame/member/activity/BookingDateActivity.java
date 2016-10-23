package com.frame.member.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.OtoCoachMeetParser;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.bean.OtoCoachMeetResult;
import com.frame.member.bean.OtoCoachMeetResult.Date;
import com.frame.member.bean.OtoCoachMeetResult.SkifieldChoices;
import com.frame.member.frag.CoachBookingDialogFrag;
import com.frame.member.widget.calendar.CalendarCard;
import com.frame.member.widget.calendar.CalendarCard.OnCellClickListener;
import com.frame.member.widget.calendar.CalendarViewAdapter;
import com.frame.member.widget.calendar.ClickPoint;
import com.frame.member.widget.calendar.CustomDate;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.PopupWindow.OnDismissListener;

public class BookingDateActivity extends BaseActivity implements OnCellClickListener, OnClickListener {

	private ViewPager mViewPager;
	private ImageView iv_left_month, iv_right_month;
	private PopupWindow mPop;
	private View view_black_filter;
	private View container_pop;
	private ListView lv_booking_pop;
	private ArrayAdapter<String> adapter_list ;
	private TextView tv_date_coach_big, tv_date_coach_small, tv_date_item_1, tv_date_item_2, tv_date_item_3,
			tv_date_item_4, tv_date_item_5, tv_date_item_6, tv_booking_now,tv_booking_place,tv_booking_snow_area;
	private int mCurrentIndex = 498;
	private CalendarCard[] mShowViews;
	private CalendarViewAdapter<CalendarCard> adapter;
	private SildeDirection mDirection = SildeDirection.NO_SILDE;
	
	private List<Date> list_date = new ArrayList<Date>();

	enum SildeDirection {
		RIGHT, LEFT, NO_SILDE;
	}
	public String skifieldId,skifieldName;

//	// 已经选中的日期数量
//	private int num_selected = 0;
	// 选中的日期集合
	public LinkedList<CustomDate> mDateList = new LinkedList<CustomDate>();

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
		tv_booking_place = (TextView) findViewById(R.id.tv_booking_place);
		tv_booking_snow_area = (TextView) findViewById(R.id.tv_booking_snow_area);
		view_black_filter = findViewById(R.id.view_black_filter);

	}

	@Override
	protected void setListener() {
		iv_left_month.setOnClickListener(this);
		iv_right_month.setOnClickListener(this);
		tv_booking_now.setOnClickListener(this);
		tv_booking_snow_area.setOnClickListener(this);
	}
	private Set<ClickPoint> set_point = new HashSet<ClickPoint>();
	
	@Override
	protected void processLogic() {
		iv_title_back.setImageResource(R.drawable.btn_back_normal);
		tv_title.setText("选择预约日期");
		CalendarCard[] views = new CalendarCard[12];
		for (int i = 0; i < 12; i++) {
			views[i] = new CalendarCard(this, this,set_point);
		}
		adapter = new CalendarViewAdapter<CalendarCard>(views);
		mShowViews = adapter.getAllItems();
		setViewPager();
		view_black_filter.setAlpha(0.0f);
		container_pop = getLayoutInflater().inflate(R.layout.item_booking_pop, null);
		lv_booking_pop = (ListView) container_pop.findViewById(R.id.lv_booking_pop);
		adapter_list = new ArrayAdapter<String>(
				this, R.layout.item_pop_list,list_field);
		lv_booking_pop.setAdapter(adapter_list);
		lv_booking_pop.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				tv_booking_snow_area.setText(list_field.get(position));
				skifieldId = list_ski.get(position).skifieldId;
				skifieldName = list_ski.get(position).skifieldName;
				mPop.dismiss();
				
			}
		});
		getData();
		
	}
	
	private List<String> list_field = new ArrayList<String>();
	private List<SkifieldChoices> list_ski = new ArrayList<SkifieldChoices>();
	public OtoCoachMeetResult result_coach = new OtoCoachMeetResult();
	//获取主数据
	private void getData(){
		BaseParser<OtoCoachMeetResult> parser = new OtoCoachMeetParser();
		HttpRequestImpl request = new HttpRequestImpl(this, 
				AppConstants.APP_SORT_STUDENT + "/otocoachmeet", parser);
		request.addParam("coachId", getIntent().getStringExtra("coachId"))
				.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""));
				
		DataCallback<OtoCoachMeetResult> callback = new DataCallback<OtoCoachMeetResult>() {

			@Override
			public void processData(OtoCoachMeetResult object, RequestResult result) {
				if(object != null){
					result_coach = object;
					tv_booking_place.setText(object.areaName);
					tv_booking_snow_area.setText("请选择预约雪场");
					list_field.clear();
					list_ski.clear();
					list_ski.addAll(object.skifieldChoices);
					for(SkifieldChoices field:object.skifieldChoices){
						list_field.add(field.skifieldName);
					}
					adapter_list.notifyDataSetChanged();
					list_date.clear();
					list_date.addAll(object.list_date);
					mShowViews[498 % 12].changeCells((ArrayList<Date>) list_date);
					
				}
			}
		};
		getDataFromServer(request, callback);
	}
	
	private void showPopwindow(){
		
		if(mPop == null){
			mPop = new PopupWindow(container_pop, 
					LinearLayout.LayoutParams.MATCH_PARENT, 
					LinearLayout.LayoutParams.WRAP_CONTENT,true);
		}
		mPop.setBackgroundDrawable(
				new BitmapDrawable(getResources(),(Bitmap)null));
//		mPop.setOutsideTouchable(true);
		mPop.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				view_black_filter.setAlpha(0.0f);
				
			}
		});
//		mPop.showAsDropDown(ll_booking_item,0,0);
		mPop.showAtLocation(view_black_filter, Gravity.BOTTOM, 0, 0);
		view_black_filter.setAlpha(0.5f);
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
				}
//				else{
//					showToast("最多预约六天");
//				}
				
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
		case R.id.tv_booking_now:
			if(mDateList == null || mDateList.size() < 1){
				showToast("请选择日期");
			}else if(TextUtils.isEmpty(skifieldId)){
				showToast("请选择预约雪场");
			}else{
				showDialog();
			}
			
			break;
		case R.id.tv_booking_snow_area:
			showPopwindow();
			break;
		default:
			break;
		}
	}
	private void showDialog(){
		CoachBookingDialogFrag frag = new CoachBookingDialogFrag();
		frag.setStyle(DialogFragment.STYLE_NORMAL, R.style.YouDialog);
		frag.show(getSupportFragmentManager(), "CoachBookingDialog");
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

	@Override
	public void attempAddDateFailed(int flag) {
		if(flag == CalendarCard.FLAG_DATE_FULL)
			showToast("最多预约六天");
	}

}
