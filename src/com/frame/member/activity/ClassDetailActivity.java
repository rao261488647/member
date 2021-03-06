package com.frame.member.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.ClassDetailParser;
import com.frame.member.Parsers.CoachMembersCommentsParser;
import com.frame.member.Parsers.NoBackParser;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.adapters.CoachMemberCommentsAdapter;
import com.frame.member.bean.BaseBean;
import com.frame.member.bean.ClassDetailResult;
import com.frame.member.bean.CoachMembersCommentsResult;
import com.frame.member.frag.ClassBookingDialogFrag;
import com.frame.member.widget.MyRantingBar;

import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ClassDetailActivity extends BaseActivity implements OnClickListener{
	private TextView tv_class_meet,tv_class_name,tv_members_price,tv_price_per_day,
					tv_item_time_num,tv_skifield_info,tv_class_collection;
//	private RatingBar rb_booking_class;
	private MyRantingBar mMyRantingBar;
	private ImageView iv_class_vedio_cover;
	private ListView lv_member_comments;
	private RelativeLayout rl_class_states;
	private TextView tv_class_states_full,tv_class_content_info;
	private CoachMemberCommentsAdapter mAdapter;
	private String collect = "";
	public ClassDetailResult result_class = new ClassDetailResult();
	
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_class_detail);
	}

	@Override
	protected void findViewById() {
		tv_class_meet = (TextView) findViewById(R.id.tv_class_meet);
		tv_class_name = (TextView) findViewById(R.id.tv_class_name);
		tv_members_price = (TextView) findViewById(R.id.tv_members_price);
		tv_price_per_day = (TextView) findViewById(R.id.tv_price_per_day);
		tv_item_time_num = (TextView) findViewById(R.id.tv_item_time_num);
		tv_skifield_info = (TextView) findViewById(R.id.tv_skifield_info);
		tv_class_collection = (TextView) findViewById(R.id.tv_class_collection);
		tv_class_states_full = (TextView) findViewById(R.id.tv_class_states_full);
		tv_class_content_info = (TextView) findViewById(R.id.tv_class_content_info);
//		rb_booking_class = (RatingBar) findViewById(R.id.rb_booking_class);
		mMyRantingBar = (MyRantingBar) findViewById(R.id.mMyRantingBar);
		iv_class_vedio_cover = (ImageView) findViewById(R.id.iv_class_vedio_cover);
		lv_member_comments = (ListView) findViewById(R.id.lv_member_comments);
		rl_class_states = (RelativeLayout) findViewById(R.id.rl_class_states);
	}
	
	@Override
	protected void setListener() {
		tv_class_meet.setOnClickListener(this);
		tv_class_collection.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		tv_title.setText("课程详情");
		tv_title_right.setText("查看评论");
		tv_title_right.setVisibility(View.INVISIBLE);
		tv_title_right.setTextColor(0xffffffff);
		iv_title_back.setImageResource(R.drawable.btn_back_normal);
		getData();
		getComments();
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_class_meet:
			ClassBookingDialogFrag frag = new ClassBookingDialogFrag();
			frag.setStyle(DialogFragment.STYLE_NORMAL, R.style.YouDialog);
			frag.show(getSupportFragmentManager(), "ClassBookingDialog");
			break;
		case R.id.tv_class_collection:
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
			request.addParam("courseId", getIntent().getStringExtra("courseId"))
					.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
					.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""))
					.addParam("type", "2")
					.addParam("status", collect);
			DataCallback<BaseBean> callback = new DataCallback<BaseBean>() {

				@Override
				public void processData(BaseBean object, RequestResult result) {
					if(object != null){
						showToast(object.message);
						if(ClassDetailActivity.this.collect != null){
							if(ClassDetailActivity.this.collect == "0"){
								ClassDetailActivity.this.collect = "1";
								tv_class_collection.setText("收藏");
							}else{
								ClassDetailActivity.this.collect = "0";
								tv_class_collection.setText("已收藏");
							}
						}
						
					}
				}
			};
			getDataFromServer(request, callback);
		}
	
	//获取主数据
	private void getData(){
		BaseParser<ClassDetailResult> parser = new ClassDetailParser();
		HttpRequest request = new HttpRequestImpl(this, AppConstants.APP_SORT_STUDENT + "/classinfo",
				parser);
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""))
				.addParam("courseId", getIntent().getStringExtra("courseId"));
		DataCallback<ClassDetailResult> callBack = new DataCallback<ClassDetailResult>() {

			@Override
			public void processData(ClassDetailResult object, RequestResult result) {
				if(object != null){
					result_class = object;
					tv_class_name.setText(object.courseName);
					tv_members_price.setText("蓝卡价： "+object.discountPrice+"元/人");
					tv_price_per_day.setText("¥"+object.originalPrice);
					StringBuffer sb = new StringBuffer();
					sb.append(object.beginTime.replace('-', '.')).append("~")
						.append(object.overTime.replace('-', '.')).append("  共"+object.hadDay+"天");
					tv_item_time_num.setText(sb.toString());
//					rb_booking_class.setRating(object.goal);
					mMyRantingBar.setRantCount(object.goal);
					TTApplication.getInstance().disPlayImageDef(object.videoPhoto, iv_class_vedio_cover);
					tv_skifield_info.setText(object.skifieldAddr);
					if("0".equals(object.collect)){
						tv_class_collection.setText("收藏");
						collect = "1";
					}else{
						tv_class_collection.setText("已收藏");
						collect = "0";
					}
					if(Integer.parseInt(object.signedUpNum) < Integer.parseInt(object.personNumber)){
						tv_class_states_full.setVisibility(View.GONE);
					}else{
						tv_class_states_full.setVisibility(View.VISIBLE);
					}
					tv_class_content_info.setText(object.courseIntro);
				}
				
			}

		};
		getDataFromServer(request, callBack);
	}
	private List<CoachMembersCommentsResult> list_comments = new ArrayList<CoachMembersCommentsResult>();
	//获取评论数据
	private void getComments(){
		BaseParser<List<CoachMembersCommentsResult>> parser = new CoachMembersCommentsParser();
		HttpRequestImpl request = new HttpRequestImpl(this, 
				AppConstants.APP_SORT_STUDENT + "/classcomment", parser);
		request.addParam("courseId", getIntent().getStringExtra("courseId"))
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
			mAdapter = new CoachMemberCommentsAdapter(ClassDetailActivity.this,list_comments);
			lv_member_comments.setAdapter(mAdapter);
		}else{
			mAdapter.notifyDataSetChanged();
		}
	}
}
