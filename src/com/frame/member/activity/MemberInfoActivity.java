package com.frame.member.activity;
import java.util.ArrayList;
import java.util.List;

/**
 *  会员简介
 *  @author long
 */
import com.frame.member.R;
import com.frame.member.adapters.FriendsPagerAdapter;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MemberInfoActivity extends BaseActivity {
	
	private ViewPager viewpager_member_info;
	private FriendsPagerAdapter mAdapter;
	private List<View> list_view = new ArrayList<View>();
	private View view_1;
	private View view_2;
	private View view_3;
	private TextView tv_login_button;
	private LinearLayout ll_dots;
	private int current_dot = 0;
	
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_member_info);
	}

	@Override
	protected void findViewById() {
		viewpager_member_info = (ViewPager) findViewById(R.id.viewpager_member_info);
		tv_login_button = (TextView) findViewById(R.id.tv_login_button);
		ll_dots = (LinearLayout) findViewById(R.id.ll_dots);
	}
	
	@Override
	protected void setListener() {
		tv_login_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
	}

	@Override
	protected void processLogic() {
		iv_title_back.setImageResource(R.drawable.btn_back_normal);
		tv_title.setText("会员简介");
		view_1 = getLayoutInflater().inflate(R.layout.viewpager_card_member, null);
		view_2 = getLayoutInflater().inflate(R.layout.viewpager_card_member, null);
		view_3 = getLayoutInflater().inflate(R.layout.viewpager_card_member, null);
		ImageView image_1 = (ImageView) view_1.findViewById(R.id.iv_card_member);
		image_1.setImageResource(R.drawable.card_green);
		ImageView image_2 = (ImageView) view_2.findViewById(R.id.iv_card_member);
		image_2.setImageResource(R.drawable.card_blue);
		ImageView image_3 = (ImageView) view_3.findViewById(R.id.iv_card_member);
		image_3.setImageResource(R.drawable.card_black);
		list_view.clear();
		list_view.add(view_1);
		list_view.add(view_2);
		list_view.add(view_3);
		mAdapter = new FriendsPagerAdapter(list_view);
		viewpager_member_info.setAdapter(mAdapter);
		viewpager_member_info.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				((ImageView)ll_dots.getChildAt(arg0)).setImageResource(R.drawable.dot_yellow);
				((ImageView)ll_dots.getChildAt(current_dot)).setImageResource(R.drawable.dot_gray);
				current_dot = arg0;
				switch (arg0) {
				case 0:
					tv_login_button.setText("成为绿卡会员");
					break;
				case 1:
					tv_login_button.setText("成为蓝卡会员");
					break;
				case 2:
					tv_login_button.setText("成为黑卡会员");
					break;

				
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
}
