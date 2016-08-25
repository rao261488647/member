package com.frame.member.activity;

import java.util.Arrays;

import com.frame.member.R;
import com.frame.member.adapters.CoachMemberCommentsAdapter;

import android.content.Context;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

public class AdviceDetailActivity extends BaseActivity {

	private ListView lv_member_comments;
	private CoachMemberCommentsAdapter mAdapter;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_advice_detail);
	}

	@Override
	protected void findViewById() {
		lv_member_comments = (ListView) findViewById(R.id.lv_member_comments);
	}

	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
		tv_title.setText("动态详情");
		iv_title_right.setImageResource(R.drawable.btn_more_normal3x);
		iv_title_back.setImageResource(R.drawable.btn_back_normal);
		mAdapter = new CoachMemberCommentsAdapter(this, Arrays.asList("李欢", "李兴策", "福城阳", "周杰伦"));
		lv_member_comments.setAdapter(mAdapter);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

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
