package com.frame.member.frag;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.SelectVedioActivity;
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
 * 求教
 * 
 * @author long
 * 
 */
public class AdviceFrag extends BaseFrag implements OnClickListener {

	private LinearLayout ll_title_left_booking, ll_title_right_booking;
	private TextView tv_title_left_booking, tv_title_right_booking, tv_change_view_attention;
	private ImageView iv_search_coach;
	// private BookingCourseOneFrag mBookingCourseOneFrag;
	private AdviceFindFrag mAdviceFindFrag;
	private AdviceAttentionFrag mAdviceAttentionFrag;
	private FragmentManager mFragmentManager;
	private BaseFrag mCurrentFrag;
	private int rank = 0;

	boolean isRight;

	public static AdviceFrag newInstance(String title) {

		AdviceFrag fragment = new AdviceFrag();
		Bundle bundle = new Bundle();
		bundle.putString(AppConstants.FRAG_TITLE_KEY, title);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.frag_advice, container, false);
		initView();
		initOnclick();
		initProgress();

		return rootView;
	}

	private void initView() {
		ll_title_left_booking = (LinearLayout) findViewById(R.id.ll_title_left_booking);
		ll_title_right_booking = (LinearLayout) findViewById(R.id.ll_title_right_booking);
		tv_title_left_booking = (TextView) findViewById(R.id.tv_title_left_booking);
		tv_title_right_booking = (TextView) findViewById(R.id.tv_title_right_booking);
		tv_change_view_attention = (TextView) findViewById(R.id.tv_change_view_attention);
		iv_search_coach = (ImageView) findViewById(R.id.iv_search_coach);
	}

	private void initOnclick() {
		ll_title_left_booking.setOnClickListener(this);
		ll_title_right_booking.setOnClickListener(this);
		iv_search_coach.setOnClickListener(this);
		tv_change_view_attention.setOnClickListener(this);
	}

	// 主逻辑代码
	private void initProgress() {
		tv_title_left_booking.setTextColor(0xffffffff);
		tv_title_right_booking.setTextColor(0xff878788);
		tv_title_left_booking.setTextSize(16);
		tv_title_right_booking.setTextSize(14);
		mFragmentManager = getActivity().getSupportFragmentManager();
		FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
		hideFragment(mTransaction);
		if (mAdviceFindFrag == null) {
			mAdviceFindFrag = AdviceFindFrag.newInstance();
			mTransaction.add(R.id.fragment_content_advice, mAdviceFindFrag).commit();
		} else {
			mTransaction.show(mAdviceFindFrag).commit();
		}
		mCurrentFrag = mAdviceFindFrag;

	}

	@Override
	public void onClick(View v) {
		FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
		switch (v.getId()) {
		case R.id.ll_title_left_booking:
			if (isRight) {
				tv_change_view_attention.setVisibility(8);
				changeFrag();
				hideFragment(mTransaction);
				if (mAdviceFindFrag == null) {
					mAdviceFindFrag = AdviceFindFrag.newInstance();
					mTransaction.add(R.id.fragment_content_advice, mAdviceFindFrag).commit();
				} else {
					mTransaction.show(mAdviceFindFrag).commit();
				}
				mCurrentFrag = mAdviceFindFrag;
			}
			break;
		case R.id.ll_title_right_booking:
			if (!isRight) {
				checkFirstAccNotice();
				changeFrag();
				hideFragment(mTransaction);
				if (mAdviceAttentionFrag == null) {
					mAdviceAttentionFrag = AdviceAttentionFrag.newInstance();
					mTransaction.add(R.id.fragment_content_advice, mAdviceAttentionFrag).commit();
				} else {
					mTransaction.show(mAdviceAttentionFrag).commit();
				}
				mCurrentFrag = mAdviceAttentionFrag;
				mAdviceAttentionFrag.getMyAttention();
			}

			break;

		case R.id.iv_search_coach:
			startActivity(new Intent(getActivity(), SelectVedioActivity.class));
			break;
		case R.id.tv_change_view_attention:
			if (mCurrentFrag == mAdviceAttentionFrag) {
				if (rank > 2)
					rank = 0;
				mAdviceAttentionFrag.setStates(rank++);
			} else {
				showToast("请在关注界面操作");
			}
			break;

		}
	}
	
	private void checkFirstAccNotice(){
		boolean isFirstAccNotice = (Boolean) SPUtils.getAppSpUtil().get(getActivity(), SPUtils.KEY_ISFIRST_ACC_NOTICE, true);
		if(isFirstAccNotice){
			tv_change_view_attention.setVisibility(0);
			SPUtils.getAppSpUtil().put(getActivity(), SPUtils.KEY_ISFIRST_ACC_NOTICE, false);
		}else
			tv_change_view_attention.setVisibility(8);
	}

	// 通过点击进行Fragment的切换
	private void changeFrag() {
		if (isRight) {
			tv_title_left_booking.setTextColor(0xffffffff);
			tv_title_right_booking.setTextColor(0xff878788);
			tv_title_left_booking.setTextSize(16);
			tv_title_right_booking.setTextSize(14);
			isRight = false;
		} else {
			tv_title_right_booking.setTextColor(0xffffffff);
			tv_title_left_booking.setTextColor(0xff878788);
			tv_title_right_booking.setTextSize(16);
			tv_title_left_booking.setTextSize(14);
			isRight = true;
		}
	}

	private void hideFragment(FragmentTransaction transaction) {
		if (mAdviceFindFrag != null)
			transaction.hide(mAdviceFindFrag);
		if (mAdviceAttentionFrag != null)
			transaction.hide(mAdviceAttentionFrag);

	}

}
