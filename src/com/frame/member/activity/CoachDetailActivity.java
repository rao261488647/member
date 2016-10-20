package com.frame.member.activity;

import java.util.ArrayList;
import java.util.List;
import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.CoachDetailParser;
import com.frame.member.Parsers.CoachMembersCommentsParser;
import com.frame.member.Parsers.NoBackParser;
import com.frame.member.Utils.CommonUtil;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.adapters.CoachMemberCommentsAdapter;
import com.frame.member.bean.BaseBean;
import com.frame.member.bean.CoachDetailResult;
import com.frame.member.bean.CoachDetailResult.Photo;
import com.frame.member.bean.CoachMembersCommentsResult;
import com.frame.member.bean.CoachSearchResult.Badges;
import com.frame.member.widget.MyListView;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;


public class CoachDetailActivity extends BaseActivity implements OnClickListener{
	
	private MyListView lv_member_comments;
	private CoachMemberCommentsAdapter mAdapter;
	private TextView tv_coach_meet,tv_name_coach,tv_level_coach,
					tv_num_meet,tv_price_num,tv_skifield_info,
					tv_coach_collection,tv_coach_content_info;
	private ImageView iv_coach_profile,iv_coach_video_cover,iv_coach_honor,iv_coach_cover,
					iv_icon_coach_train,iv_icon_coach_act,iv_icon_coach_referee;
	private RatingBar rb_booking_one;
	private LinearLayout ll_coach_picture;
	private FrameLayout fl_coach_video_cover;
	private String collect = "";

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
		tv_coach_content_info = (TextView) findViewById(R.id.tv_coach_content_info);
		tv_coach_collection = (TextView) findViewById(R.id.tv_coach_collection);
		iv_coach_profile = (ImageView) findViewById(R.id.iv_coach_profile);
		iv_coach_video_cover = (ImageView) findViewById(R.id.iv_coach_video_cover);
		iv_coach_honor = (ImageView) findViewById(R.id.iv_coach_honor);
		iv_coach_cover = (ImageView) findViewById(R.id.iv_coach_cover);
		iv_icon_coach_train = (ImageView) findViewById(R.id.iv_icon_coach_train);
		iv_icon_coach_act = (ImageView) findViewById(R.id.iv_icon_coach_act);
		iv_icon_coach_referee = (ImageView) findViewById(R.id.iv_icon_coach_referee);
		rb_booking_one = (RatingBar) findViewById(R.id.rb_booking_one);
		ll_coach_picture = (LinearLayout) findViewById(R.id.ll_coach_picture);
		fl_coach_video_cover = (FrameLayout) findViewById(R.id.fl_coach_video_cover);
	}

	@Override
	protected void setListener() {
		tv_coach_meet.setOnClickListener(this);
		tv_coach_collection.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		iv_title_back.setImageResource(R.drawable.btn_back_normal);
		tv_title.setText("教练详情");
		tv_title_right.setText("查看评论");
		tv_title_right.setVisibility(View.INVISIBLE);
//		mAdapter = new CoachMemberCommentsAdapter(this,null);
//		lv_member_comments.setAdapter(mAdapter);
		getData();
		getComments();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_coach_meet:
			Intent intent = new Intent(CoachDetailActivity.this,BookingDateActivity.class);
			intent.putExtra("coachId", getIntent().getStringExtra("coachId"));
			startActivity(intent);
			break;
		case R.id.tv_coach_collection:
			toCollect(collect);
			break;

		default:
			break;
		}
	}
	//调用收藏接口
	private void toCollect(String collect){
		BaseParser<BaseBean> parser = new NoBackParser();
		HttpRequestImpl request = new HttpRequestImpl(this, 
				AppConstants.APP_SORT_STUDENT + "/collect", parser);
		request.addParam("coachId", getIntent().getStringExtra("coachId"))
				.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""))
				.addParam("type", "1")
				.addParam("status", collect);
		DataCallback<BaseBean> callback = new DataCallback<BaseBean>() {

			@Override
			public void processData(BaseBean object, RequestResult result) {
				if(object != null){
					showToast(object.message);
					if(CoachDetailActivity.this.collect != null){
						if(CoachDetailActivity.this.collect == "0"){
							CoachDetailActivity.this.collect = "1";
							tv_coach_collection.setText("收藏");
						}else{
							CoachDetailActivity.this.collect = "0";
							tv_coach_collection.setText("已收藏");
						}
					}
					
				}
			}
		};
		getDataFromServer(request, callback);
	}
	//获取主数据
	private void getData(){
		BaseParser<CoachDetailResult> parser = new CoachDetailParser();
		HttpRequestImpl request = new HttpRequestImpl(
				this, AppConstants.APP_SORT_STUDENT + "/coachdetail", parser);
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
				//图标逻辑
				iv_icon_coach_train.setVisibility(View.GONE);
				iv_icon_coach_act.setVisibility(View.GONE);
				iv_icon_coach_referee.setVisibility(View.GONE);
				if(object.badges != null && object.badges.size()>0){
					for(Badges badge: object.badges){
						if("1".equals(badge.badgeId)){
							iv_icon_coach_train.setVisibility(View.VISIBLE);
						}
						if("2".equals(badge.badgeId)){
							iv_icon_coach_act.setVisibility(View.VISIBLE);
						}
						if("3".equals(badge.badgeId)){
							iv_icon_coach_referee.setVisibility(View.VISIBLE);
						}
					}
				}
				tv_num_meet.setText("累计被约"+object.meetNum+"次");
				TTApplication.getInstance().disPlayImageDef(object.headImg, iv_coach_profile);
				TTApplication.getInstance().disPlayImageDef(object.headImg, iv_coach_cover);
				iv_coach_cover.setColorFilter(0x30000000);
				tv_level_coach.setText(object.levelName);
				tv_price_num.setText("¥"+object.trainfee);
				rb_booking_one.setRating(object.goal);
				tv_name_coach.setText(object.coachName);
				tv_coach_content_info.setText(object.specialty);
				tv_skifield_info.setText(object.areaName);
				if(!TextUtils.isEmpty(object.videoUrl)){
					TTApplication.getInstance().disPlayImageDef(object.videoPhoto, iv_coach_video_cover);
				}else{
					fl_coach_video_cover.setVisibility(View.GONE);
				}
				
				TTApplication.getInstance().disPlayImageDef(object.honor, iv_coach_honor);
				if("0".equals(object.collect)){
					tv_coach_collection.setText("收藏");
					collect = "1";
				}else{
					tv_coach_collection.setText("已收藏");
					collect = "0";
				}
				//动态加载coach照片
				if(object.photo != null && object.photo.size() > 0){
					ll_coach_picture.setVisibility(View.VISIBLE);
					ll_coach_picture.removeAllViews();
					for(Photo photo:object.photo){
						ImageView child = new ImageView(CoachDetailActivity.this);
						LayoutParams params = new LayoutParams(
								LinearLayout.LayoutParams.MATCH_PARENT,
								CommonUtil.dip2px(CoachDetailActivity.this, 200));
						params.topMargin = CommonUtil.dip2px(CoachDetailActivity.this, 10);
						
						child.setImageResource(R.drawable.coach_skill_picture_1);
						child.setScaleType(ScaleType.CENTER_CROP);
						if(!TextUtils.isEmpty(photo.photoURL)){
							TTApplication.getInstance().disPlayImageDef(
									photo.photoURL, child);
						}
						ll_coach_picture.addView(child,params);
					}
				}else{
					ll_coach_picture.setVisibility(View.GONE);
				}
			}
		}
	};
	private List<CoachMembersCommentsResult> list_comments = new ArrayList<CoachMembersCommentsResult>();
	//获取评论列表
	private void getComments(){
		BaseParser<List<CoachMembersCommentsResult>> parser = new CoachMembersCommentsParser();
		HttpRequestImpl request = new HttpRequestImpl(this, 
				AppConstants.APP_SORT_STUDENT + "/studentcommentcoach", parser);
		request.addParam("coachId", getIntent().getStringExtra("coachId"))
				.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""));
		DataCallback<List<CoachMembersCommentsResult>> callback = new DataCallback<List<CoachMembersCommentsResult>>() {

			@Override
			public void processData(List<CoachMembersCommentsResult> object, RequestResult result) {
				if(object != null){
					list_comments.clear();
					list_comments.addAll(object);
					notifyAdapter();
				}
			}
		};
		getDataFromServer(request, callback);
	}
	private void notifyAdapter(){
		if(mAdapter == null){
			mAdapter = new CoachMemberCommentsAdapter(CoachDetailActivity.this,list_comments);
			lv_member_comments.setAdapter(mAdapter);
		}else{
			mAdapter.notifyDataSetChanged();
		}
	}

}
