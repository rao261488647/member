package com.frame.member.activity;

import java.util.ArrayList;
import java.util.List;
import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.FollowlListParser;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.adapters.MyAttentionCoachAdapter;
import com.frame.member.adapters.MyAttentionFriendsAdapter;
import com.frame.member.bean.FollowListResult;
import com.frame.member.bean.FollowListResult.Coach;
import com.frame.member.bean.FollowListResult.Friends;

import android.widget.ListView;
/**
 *  我的关注
 *  @author long
 */
public class MyAttentionActivity extends BaseActivity {
	
	private ListView lv_coach_concerned,lv_friends_concerned;
	private MyAttentionCoachAdapter coach_adapter;
	private MyAttentionFriendsAdapter friends_adapter;
//	private List<ImageAndText> list;
	private List<Coach> list_coach = new ArrayList<FollowListResult.Coach>();
	private List<Friends> list_friends = new ArrayList<Friends>();
	
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_my_attention);
	}

	@Override
	protected void findViewById() {
	  	lv_coach_concerned = (ListView) findViewById(R.id.lv_coach_concerned);
	  	lv_friends_concerned = (ListView) findViewById(R.id.lv_friends_concerned);
	}
	
	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
		iv_title_back.setImageResource(R.drawable.btn_back_normal);
		tv_title.setText("关注列表");
//		list = new ArrayList<CoachSearchAdapter.ImageAndText>();
//		list.add(new ImageAndText(R.drawable.profile_example_1, "阴三儿"));
//		list.add(new ImageAndText(R.drawable.profile_friends, "赵四"));
//		coach_adapter = new MyAttentionCoachAdapter(this, list);
//		friends_adapter = new MyAttentionFriendsAdapter(this, list);
//		lv_coach_concerned.setAdapter(coach_adapter);
//		lv_friends_concerned.setAdapter(friends_adapter);
		getDate();
		
		
	}
	//获取主数据
	private void getDate(){
		BaseParser<FollowListResult> parser = new FollowlListParser();
		HttpRequestImpl request = new HttpRequestImpl(
				this, AppConstants.APP_SORT_STUDENT+"/followlist", parser);
		request.addParam("memberUserId", 
				(String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
		.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""));
		getDataFromServer(request, callBack);
				
	}
	private DataCallback<FollowListResult> callBack = new DataCallback<FollowListResult>() {

		@Override
		public void processData(FollowListResult object, RequestResult result) {
			if(object != null){
				list_coach.clear();
				list_coach.addAll(object.list_coach);
				list_friends.clear();
				list_friends.addAll(object.list_friends);
				notifyAdapter();
			}
		}
	};
	private void notifyAdapter(){
		if(coach_adapter == null){
			coach_adapter = new MyAttentionCoachAdapter(this, list_coach);
			lv_coach_concerned.setAdapter(coach_adapter);
		}else{
			coach_adapter.notifyDataSetChanged();
		}
		if(friends_adapter == null){
			friends_adapter = new MyAttentionFriendsAdapter(this, list_friends);
			lv_friends_concerned.setAdapter(friends_adapter);
		}else{
			friends_adapter.notifyDataSetChanged();
		}
	}
}
