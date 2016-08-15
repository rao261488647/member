package com.frame.member.activity;

import java.util.Arrays;

import com.frame.member.R;
import com.frame.member.adapters.CoachMemberCommentsAdapter;
import com.frame.member.widget.MyListView;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


public class CoachDetailActivity extends BaseActivity implements OnClickListener{
	
	private MyListView lv_member_comments;
	private CoachMemberCommentsAdapter mAdapter;
	private TextView tv_coach_meet;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_coach_details);
	}

	@Override
	protected void findViewById() {
		lv_member_comments = (MyListView) findViewById(R.id.lv_member_comments);
		tv_coach_meet = (TextView) findViewById(R.id.tv_coach_meet);
	}

	@Override
	protected void setListener() {
		tv_coach_meet.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		iv_title_back.setImageResource(R.drawable.btn_back_normal);
		tv_title.setText("教练详情");
		tv_title_right.setText("查看评论");
		mAdapter = new CoachMemberCommentsAdapter(this,Arrays.asList("李欢","李兴策","福城阳","周杰伦"));
		lv_member_comments.setAdapter(mAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_coach_meet:
			startActivity(new Intent(CoachDetailActivity.this,BookingDateActivity.class));
			break;

		default:
			break;
		}
	}

}
