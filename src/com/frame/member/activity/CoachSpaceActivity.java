package com.frame.member.activity;

import java.util.ArrayList;
import java.util.List;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.CoachInfoParser;
import com.frame.member.Parsers.CoachSpaceParser;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.adapters.CoachReviewsAdapter;
import com.frame.member.adapters.CoachSearchAdapter.ImageAndText;
import com.frame.member.bean.CoachInfoResult;
import com.frame.member.bean.CoachSpaceResult;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 教练主页
 * @author long
 *
 */
public class CoachSpaceActivity extends BaseActivity {
	private ListView lv_coach_reviews;
	private CoachReviewsAdapter mAdapter;
	private List<ImageAndText> list_reviews;
	private ImageView iv_coach_profile;
	private TextView tv_level_coach,tv_name_coach;
	
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_space_coach);
	}

	@Override
	protected void findViewById() {
		lv_coach_reviews = (ListView) findViewById(R.id.lv_coach_reviews);
		iv_coach_profile = (ImageView) findViewById(R.id.iv_coach_profile);
		tv_level_coach = (TextView) findViewById(R.id.tv_level_coach);
		tv_name_coach = (TextView) findViewById(R.id.tv_name_coach);
	}
	
	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
		iv_title_back.setImageResource(R.drawable.btn_back_normal);
		tv_title.setText("教练主页");
		list_reviews = new ArrayList<ImageAndText>();
		list_reviews.add(new ImageAndText(R.drawable.profile_friends, "阴三儿"));
		list_reviews.add(new ImageAndText(R.drawable.profile_friends, "赵四"));
		list_reviews.add(new ImageAndText(R.drawable.profile_friends, "王五"));
		mAdapter = new CoachReviewsAdapter(this, list_reviews);
		lv_coach_reviews.setAdapter(mAdapter);
		getCoachInfo();
		getCoachComments();
	}
	//获取教练个人信息
	private void getCoachInfo(){
		BaseParser<CoachInfoResult> parser = new CoachInfoParser();
		HttpRequestImpl request = new HttpRequestImpl(
				this, AppConstants.APP_SORT_STUDENT+"/coachinfo", parser);
		request.addParam("coachId", getIntent().getStringExtra("coachId"));
		getDataFromServer(request, callBack);
	}
	private DataCallback<CoachInfoResult> callBack = new DataCallback<CoachInfoResult>() {

		@Override
		public void processData(CoachInfoResult object, RequestResult result) {
			if(object != null){
				if("0".equals(object.followCoach)){
					tv_title_right.setText("加关注");
				}else{
					tv_title_right.setText("√已关注");
				}
				TTApplication.getInstance().disPlayImageDef(object.headImg, iv_coach_profile);
				tv_name_coach.setText(object.coachName);
				tv_level_coach.setText(object.levelName +"  "+object.titleName);
				
			}
		}
	};
	//获取教练点评数据
	private void getCoachComments(){
		BaseParser<List<CoachSpaceResult>> parser = new CoachSpaceParser();
		HttpRequestImpl request = new HttpRequestImpl(
				this, AppConstants.APP_SORT_STUDENT+"/coachspace", parser);
		request.addParam("coachId", getIntent().getStringExtra("coachId"));
		getDataFromServer(request, callback1);
	}
	private DataCallback<List<CoachSpaceResult>> callback1 = 
			new DataCallback<List<CoachSpaceResult>>() {

		@Override
		public void processData(List<CoachSpaceResult> object, RequestResult result) {
			if(object != null){
				
			}
		}
	};
}
