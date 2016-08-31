package com.frame.member.activity;

import java.util.ArrayList;
import java.util.List;

import com.frame.member.R;
import com.frame.member.adapters.CoachSearchAdapter;
import com.frame.member.adapters.CoachSearchAdapter.ImageAndText;
import com.frame.member.adapters.MyAttentionCoachAdapter;
import com.frame.member.adapters.MyAttentionFriendsAdapter;

import android.widget.ListView;
/**
 *  我的关注
 *  @author long
 */
public class MyAttentionActivity extends BaseActivity {
	
	private ListView lv_coach_concerned,lv_friends_concerned;
	private MyAttentionCoachAdapter coach_adapter;
	private MyAttentionFriendsAdapter friends_adapter;
	private List<ImageAndText> list;
	
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
		list = new ArrayList<CoachSearchAdapter.ImageAndText>();
		list.add(new ImageAndText(R.drawable.profile_example_1, "阴三儿"));
		list.add(new ImageAndText(R.drawable.profile_friends, "赵四"));
		coach_adapter = new MyAttentionCoachAdapter(this, list);
		friends_adapter = new MyAttentionFriendsAdapter(this, list);
		lv_coach_concerned.setAdapter(coach_adapter);
		lv_friends_concerned.setAdapter(friends_adapter);
		
		
	}
}
