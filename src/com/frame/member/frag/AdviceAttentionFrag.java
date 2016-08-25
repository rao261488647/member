package com.frame.member.frag;

import java.util.ArrayList;
import java.util.List;
import com.frame.member.R;
import com.frame.member.adapters.AdviceFindAdapter;
import com.frame.member.adapters.AttentionCoachAdapter;
import com.frame.member.adapters.AttentionCoachAdapter.AttentionCoachHolder;
import com.frame.member.adapters.AttentionFriendsAdapter;
import com.frame.member.adapters.AttentionFriendsAdapter.AttentionFriendsHolder;
import com.frame.member.adapters.CoachSearchAdapter.ImageAndText;
import com.frame.member.widget.refreshlistview.PullToRefreshListView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 
 * 求教关注
 * 
 *
 */
public class AdviceAttentionFrag extends BaseFrag {
	// STATES_NORMAL
	private PullToRefreshListView lv_advice_attention;
	private LinearLayout ll_content_advice_attention;
	private AdviceFindAdapter adapter;
	private List<ImageAndText> list_iv_text = new ArrayList<ImageAndText>();
	// STATES_NOATTENTION
	private RelativeLayout rl_nothing_background,rl_content_advice_attention;
	// STATES_NOBODY
	private ScrollView sv_attention_nobody;
	private GridView gv_coach_instructor;
	private ListView lv_attention_friends;
	private TextView tv_coach_transform, tv_friends_transform;
	private AttentionCoachAdapter mAdapter_coach;
	private AttentionFriendsAdapter mAdapter_friends;
	private List<AttentionCoachHolder> list_coach = new ArrayList<AttentionCoachAdapter.AttentionCoachHolder>();
	private List<AttentionFriendsHolder> list_friends = new ArrayList<AttentionFriendsAdapter.AttentionFriendsHolder>();

	// 根据三种状态选择展示的页面样式
	private final int STATES_NOBODY = 0;
	private final int STATES_NOATTENTION = 1;
	private final int STATES_NORMAL = 2;
	private int states; // 关注页面状态

	private static AdviceAttentionFrag mFrag;

	public static AdviceAttentionFrag newInstance() {
		if (mFrag == null)
			mFrag = new AdviceAttentionFrag();

		return mFrag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.frag_advice_attention, container, false);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		states = 2;
		initData();
	}

	private void initData() {
		
		changeViewByStates();
	}
	
	private void initView() {
		lv_advice_attention = (PullToRefreshListView) findViewById(R.id.lv_advice_attention);
		ll_content_advice_attention = (LinearLayout) findViewById(R.id.ll_content_advice_attention);
		rl_nothing_background = (RelativeLayout) findViewById(R.id.rl_nothing_background);
		rl_content_advice_attention = (RelativeLayout) findViewById(R.id.rl_content_advice_attention);
		sv_attention_nobody = (ScrollView) findViewById(R.id.sv_attention_nobody);
		gv_coach_instructor = (GridView) findViewById(R.id.gv_coach_instructor);
		lv_attention_friends = (ListView) findViewById(R.id.lv_attention_friends);
		tv_coach_transform = (TextView) findViewById(R.id.tv_coach_transform);
		tv_friends_transform = (TextView) findViewById(R.id.tv_friends_transform);
	}
	//根据states的值来决定显示什么界面
	private void changeViewByStates(){
		switch (states) {
		case STATES_NOBODY:
			ll_content_advice_attention.setVisibility(View.GONE);
			rl_nothing_background.setVisibility(View.GONE);
			sv_attention_nobody.setVisibility(View.VISIBLE);
			list_coach.clear();
			list_coach.add(new AttentionCoachHolder("张教练", "专职导师"));
			list_coach.add(new AttentionCoachHolder("李教练", "业内大咖"));
			list_coach.add(new AttentionCoachHolder("王教练", "联盟高级"));
			list_coach.add(new AttentionCoachHolder("孙教练", "专职导师"));
			mAdapter_coach = new AttentionCoachAdapter(mContext, list_coach);
			gv_coach_instructor.setAdapter(mAdapter_coach);
			list_friends.clear();
			list_friends.add(new AttentionFriendsHolder("我石化了","LV.3"));
			list_friends.add(new AttentionFriendsHolder("烫手的山芋","LV.3"));
			list_friends.add(new AttentionFriendsHolder("最强大脑","LV.3"));
			list_friends.add(new AttentionFriendsHolder("暴走漫画","LV.3"));
			mAdapter_friends = new AttentionFriendsAdapter(mContext, list_friends);
			lv_attention_friends.setAdapter(mAdapter_friends);
			break;
		case STATES_NOATTENTION:
			ll_content_advice_attention.setVisibility(View.VISIBLE);
			rl_content_advice_attention.setVisibility(View.VISIBLE);
			lv_advice_attention.setVisibility(View.GONE);
			sv_attention_nobody.setVisibility(View.GONE);
			rl_nothing_background.setVisibility(View.VISIBLE);
			break;
		case STATES_NORMAL:
			sv_attention_nobody.setVisibility(View.GONE);
			rl_nothing_background.setVisibility(View.GONE);
			ll_content_advice_attention.setVisibility(View.VISIBLE);
			rl_content_advice_attention.setVisibility(View.VISIBLE);
			lv_advice_attention.setVisibility(View.VISIBLE);
			list_iv_text.clear();
			list_iv_text.add(new ImageAndText(R.drawable.coach_profile, "老李"));
			list_iv_text.add(new ImageAndText(R.drawable.coach_profile, "老孙"));
			list_iv_text.add(new ImageAndText(R.drawable.coach_profile, "老王"));
			adapter = new AdviceFindAdapter(mContext, list_iv_text);
			lv_advice_attention.setAdapter(adapter);
			
			break;

		default:
			break;
		}
	}
	
	public void setStates(int states){
		this.states = states;
		changeViewByStates();
	}

}
