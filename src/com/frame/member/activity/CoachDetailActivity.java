package com.frame.member.activity;

import java.util.ArrayList;
import java.util.Arrays;

import com.frame.member.R;
import com.frame.member.adapters.CoachMemberCommentsAdapter;
import com.frame.member.widget.MyListView;

import android.widget.ScrollView;


public class CoachDetailActivity extends BaseActivity{
	
	private MyListView lv_member_comments;
	private CoachMemberCommentsAdapter mAdapter;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_coach_details);
	}

	@Override
	protected void findViewById() {
		lv_member_comments = (MyListView) findViewById(R.id.lv_member_comments);
		
	}

	@Override
	protected void setListener() {
		
	}

	@Override
	protected void processLogic() {
		iv_title_back.setImageResource(R.drawable.btn_back_normal);
		tv_title.setText("教练详情");
		tv_title_right.setText("查看评论");
		mAdapter = new CoachMemberCommentsAdapter(this,Arrays.asList("李欢","李兴策","福城阳","周杰伦"));
		lv_member_comments.setAdapter(mAdapter);
	}

}
