package com.frame.member.activity;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.CoachDetailParser;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.adapters.CoachMemberCommentsAdapter;
import com.frame.member.bean.CoachDetailResult;
import com.frame.member.widget.MyListView;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


public class CoachDetailActivity extends BaseActivity implements OnClickListener{
	
	private MyListView lv_member_comments;
	private CoachMemberCommentsAdapter mAdapter;
	private TextView tv_coach_meet,tv_name_coach,tv_level_coach,
					tv_num_meet,tv_price_num,tv_skifield_info;
	private ImageView iv_coach_profile;
	private RatingBar rb_booking_one;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_coach_details);
	}

	@Override
	protected void findViewById() {
		lv_member_comments = (MyListView) findViewById(R.id.lv_member_comments);
		tv_coach_meet = (TextView) findViewById(R.id.tv_coach_meet);
		tv_name_coach = (TextView) findViewById(R.id.tv_name_coach);
		tv_level_coach = (TextView) findViewById(R.id.tv_level_coach);
		tv_num_meet = (TextView) findViewById(R.id.tv_num_meet);
		tv_price_num = (TextView) findViewById(R.id.tv_price_num);
		tv_skifield_info = (TextView) findViewById(R.id.tv_skifield_info);
		iv_coach_profile = (ImageView) findViewById(R.id.iv_coach_profile);
		rb_booking_one = (RatingBar) findViewById(R.id.rb_booking_one);
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
		mAdapter = new CoachMemberCommentsAdapter(this,null);
		lv_member_comments.setAdapter(mAdapter);
		getData();
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
	private void getData(){
		BaseParser<CoachDetailResult> parser = new CoachDetailParser();
		HttpRequestImpl request = new HttpRequestImpl(
				this, AppConstants.APP_SORT_STUDENT + "/otocoachmeet", parser);
		request.addParam("memberUserId", 
				(String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""))
				.addParam("coachId", getIntent().getStringExtra("coachId"));
		getDataFromServer(request, callBack);
	}
	private DataCallback<CoachDetailResult> callBack = new DataCallback<CoachDetailResult>() {

		@Override
		public void processData(CoachDetailResult object, RequestResult result) {
			if(object != null){
				tv_num_meet.setText("累计被约"+getIntent().getStringExtra("meetNum")+"次");
				TTApplication.getInstance().disPlayImageDef(object.headImg, iv_coach_profile);
				tv_level_coach.setText(object.titleName);
				tv_price_num.setText("¥"+object.trainfee);
				rb_booking_one.setRating(Float.parseFloat(object.coachStar));
				tv_name_coach.setText(object.coachName);
				
			}
		}
	};

}
