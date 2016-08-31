package com.frame.member.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.frame.member.R;
import com.frame.member.adapters.CoachMemberCommentsAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

public class AdviceDetailActivity extends BaseActivity {

	private ListView lv_member_comments;
	private CoachMemberCommentsAdapter mAdapter;
	private PopupWindow mPop;
	private View container_pop;
	private ListView lv_booking_pop;
	private ArrayAdapter<String> adapter_list ;
	private ArrayAdapter<String> adapter_list_share ;
	private View view_black_filter;
	private List<String> list_str = new ArrayList<String>();
	private ImageView iv_person_profile;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_advice_detail);
	}

	@Override
	protected void findViewById() {
		lv_member_comments = (ListView) findViewById(R.id.lv_member_comments);
		view_black_filter = findViewById(R.id.view_black_filter);
		iv_person_profile = (ImageView) findViewById(R.id.iv_person_profile);
	}

	@Override
	protected void setListener() {
		iv_title_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showPopwindow(1);
			}
		});
		iv_person_profile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(AdviceDetailActivity.this, CoachSpaceActivity.class));
			}
		});
	}

	@Override
	protected void processLogic() {
		tv_title.setText("动态详情");
		iv_title_right.setImageResource(R.drawable.btn_more_normal3x);
		iv_title_back.setImageResource(R.drawable.btn_back_normal);
		mAdapter = new CoachMemberCommentsAdapter(this, Arrays.asList("李欢", "李兴策", "福城阳", "周杰伦"));
		lv_member_comments.setAdapter(mAdapter);
		lv_member_comments.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				showPopwindow(0);
			}
		});
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		view_black_filter.setAlpha(0.0f);
		container_pop = getLayoutInflater().inflate(R.layout.item_booking_pop, null);
		lv_booking_pop = (ListView) container_pop.findViewById(R.id.lv_booking_pop);
		adapter_list = new ArrayAdapter<String>(
				this, R.layout.item_pop_list,list_str);
		lv_booking_pop.setAdapter(adapter_list);

	}
	
	private void showPopwindow(int rank){
		list_str.clear();
		switch (rank) {
		case 0:
			list_str.add("回复");
			list_str.add("举报");
			list_str.add("取消");
			adapter_list.notifyDataSetChanged();
			break;
		case 1:
			list_str.add("分享");
			list_str.add("举报");
			list_str.add("取消");
			break;	

		}
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

	// 点击空白区域 自动隐藏软键盘
	// 获取点击事件
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View view = getCurrentFocus();
			if (isHideInput(view, ev)) {
				HideSoftInput(view.getWindowToken());
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	// 判定是否需要隐藏
	private boolean isHideInput(View v, MotionEvent ev) {
		if (v != null && (v instanceof EditText)) {
			int[] l = { 0, 0 };
			v.getLocationInWindow(l);
			int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
			if (ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	// 隐藏软键盘
	private void HideSoftInput(IBinder token) {
		if (token != null) {
			InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	

}
