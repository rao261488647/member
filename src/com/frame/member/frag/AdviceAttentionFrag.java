package com.frame.member.frag;

import java.util.ArrayList;
import java.util.List;
import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.AdviceFollowParser;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.IntroduceAttentionParser;
import com.frame.member.Parsers.MyAttentionParser;
import com.frame.member.Parsers.NoBackParser;
import com.frame.member.Utils.CommonUtil;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.BaseActivity;
import com.frame.member.activity.MyAttentionActivity;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.adapters.AdviceFollowAdapter;
import com.frame.member.adapters.AttentionCoachAdapter;
import com.frame.member.adapters.AttentionFriendsAdapter;
import com.frame.member.bean.AdviceFollowResult;
import com.frame.member.bean.BaseBean;
import com.frame.member.bean.IntroduceAttentionResult;
import com.frame.member.bean.IntroduceAttentionResult.Coach;
import com.frame.member.bean.IntroduceAttentionResult.Friend;
import com.frame.member.bean.MyAttentionResult;
import com.frame.member.widget.RoundImageView;
import com.frame.member.widget.refreshlistview.PullToRefreshBase;
import com.frame.member.widget.refreshlistview.PullToRefreshBase.Mode;
import com.frame.member.widget.refreshlistview.PullToRefreshListView;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

/**
 * 
 * 求教关注
 * @author long
 *
 */
public class AdviceAttentionFrag extends BaseFrag implements OnClickListener{
	// STATES_NORMAL
	private PullToRefreshListView lv_advice_attention;
	private LinearLayout ll_content_advice_attention;
	private AdviceFollowAdapter adapter;
	private List<AdviceFollowResult> list_follow = new ArrayList<AdviceFollowResult>();
	// STATES_NOATTENTION
	private RelativeLayout rl_nothing_background, rl_content_advice_attention;

	// STATES_NOBODY
	private ScrollView sv_attention_nobody;
	private GridView gv_coach_instructor;
	private ListView lv_attention_friends;
	private TextView tv_coach_transform, tv_friends_transform,tv_attention_friends_all,tv_attention_coach_all;
	private AttentionCoachAdapter mAdapter_coach;
	private AttentionFriendsAdapter mAdapter_friends;
	private List<Coach> list_coach = new ArrayList<Coach>();
	private List<Friend> list_friends = new ArrayList<Friend>();
	private LinearLayout ll_myAttention_profile;
	private TextView tv_attention_num;

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
		getMyAttention();
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
		ll_myAttention_profile = (LinearLayout) findViewById(R.id.ll_myAttention_profile);
		tv_attention_num = (TextView) findViewById(R.id.tv_attention_num);
		tv_attention_friends_all = (TextView) findViewById(R.id.tv_attention_friends_all);
		tv_attention_coach_all = (TextView) findViewById(R.id.tv_attention_coach_all);
		lv_advice_attention.setMode(Mode.BOTH);
		lv_advice_attention.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				page = 1;
				lv_advice_attention.setMode(Mode.BOTH);
				getData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				page++;
				getData();
			}
		});
		tv_coach_transform.setOnClickListener(this);
		tv_friends_transform.setOnClickListener(this);
		rl_content_advice_attention.setOnClickListener(this);
		tv_attention_friends_all.setOnClickListener(this);
		tv_attention_coach_all.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_coach_transform:
			getRandCoach();
			break;
		case R.id.tv_friends_transform:
			getRandFriend();
			break;
		case R.id.rl_content_advice_attention:
			startActivity(new Intent(mContext, MyAttentionActivity.class));
			break;
		case R.id.tv_attention_coach_all:
			toAttentionAllCoach();
			break;
		case R.id.tv_attention_friends_all:
			toAttentionAllFriend();
			break;

		default:
			break;
		}
	}

	// 根据states的值来决定显示什么界面
	private void changeViewByStates() {
		switch (states) {
		case STATES_NOBODY:
			ll_content_advice_attention.setVisibility(View.GONE);
			rl_nothing_background.setVisibility(View.GONE);
			sv_attention_nobody.setVisibility(View.VISIBLE);
			getIntroduceAttention();
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
			break;
		}
	}

	public void setStates(int states) {
		this.states = states;
		changeViewByStates();
	}

	int page = 0;

	// 获取主数据
	private void getData() {
		if (page < 1)
			page = 1;
		BaseParser<List<AdviceFollowResult>> parser = new AdviceFollowParser();
		HttpRequestImpl request = new HttpRequestImpl(mContext, AppConstants.APP_SORT_STUDENT + "/follow", parser);
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(mContext, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("token", (String) SPUtils.getAppSpUtil().get(mContext, SPUtils.KEY_TOKEN, ""))
				.addParam("page_size", "10").addParam("page_num", "" + page);
		((BaseActivity) mContext).getDataFromServer(request, callBack);
	}

	private DataCallback<List<AdviceFollowResult>> callBack = new DataCallback<List<AdviceFollowResult>>() {

		@Override
		public void processData(List<AdviceFollowResult> object, RequestResult result) {
			lv_advice_attention.onRefreshComplete();
			if (object != null && object.size() > 0) {
				if (object.get(0).totalItems == 0) {
					states = STATES_NOATTENTION;
					changeViewByStates();
				} else {
					setStates(STATES_NORMAL);
					if (page == 1) {
						list_follow.clear();
					}
					list_follow.addAll(object);
					notifyFollowAdapter();
				}
			} else {
				if (page == 1) {
					list_follow.clear();
					notifyFollowAdapter();
				}
				lv_advice_attention.setMode(Mode.PULL_FROM_START);
			}
		}
	};

	private void notifyFollowAdapter() {
		if (adapter == null) {
			adapter = new AdviceFollowAdapter(mContext, list_follow);
			lv_advice_attention.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i("Arvin", "AdviceAttention onResume");
		getMyAttention();
	}
	
	// 获取我的关注数据
	public void getMyAttention() {
		if(mContext == null)
			return;
		BaseParser<List<MyAttentionResult>> parser = new MyAttentionParser();
		HttpRequestImpl request = new HttpRequestImpl(mContext, AppConstants.APP_SORT_STUDENT + "/myfollow", parser);
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(mContext, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("token", (String) SPUtils.getAppSpUtil().get(mContext, SPUtils.KEY_TOKEN, ""));
		DataCallback<List<MyAttentionResult>> callBack = new DataCallback<List<MyAttentionResult>>() {

			@Override
			public void processData(List<MyAttentionResult> object, RequestResult result) {
				if (object != null) {
					setMyAttentionProfile(object);
					tv_attention_num.setText(object.get(0).myfollow_num);
					if ("0".equals(object.get(0).myfollow_num)) {
						setStates(STATES_NOBODY);
					} else {
						// 只要有关注就获取主数据
						getData();
					}

				} else {
					setStates(STATES_NOBODY);
				}
			}
		};
		((BaseActivity) mContext).getDataFromServer(request, callBack);
	}

	// 动态加载我的关注用户头像
	private void setMyAttentionProfile(List<MyAttentionResult> list_attention) {
		// 动态加载点赞头像
		if (list_attention != null && list_attention.size() > 0) {
			ll_myAttention_profile.removeAllViews();
			int rank = 0;
			for (MyAttentionResult result : list_attention) {
				RoundImageView child = new RoundImageView(mContext);
				LayoutParams params = new LayoutParams(CommonUtil.dip2px(mContext, 35),
						CommonUtil.dip2px(mContext, 35));
				if (rank != 0) {
					params.leftMargin = CommonUtil.dip2px(mContext, 5);

				}
				child.setImageResource(R.drawable.profile_example_1);
				if (!TextUtils.isEmpty(result.appHeadThumbnail)) {
					TTApplication.getInstance().disPlayImageDef(result.appHeadThumbnail, child);
				}
				ll_myAttention_profile.addView(child, params);
				rank++;
				if (ll_myAttention_profile.getChildCount() == 5)
					break;
			}
		}
	}

	// 当没有关注任何人时需要调用推荐关注接口
	private void getIntroduceAttention() {
		BaseParser<IntroduceAttentionResult> parser = new IntroduceAttentionParser();
		HttpRequestImpl request = new HttpRequestImpl(mContext, AppConstants.APP_SORT_STUDENT + "/followrecommend",
				parser);
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(mContext, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("token", (String) SPUtils.getAppSpUtil().get(mContext, SPUtils.KEY_TOKEN, ""));
		DataCallback<IntroduceAttentionResult> callBack = new DataCallback<IntroduceAttentionResult>() {

			@Override
			public void processData(IntroduceAttentionResult object, RequestResult result) {
				if(object != null){
					list_coach.clear();
					list_coach.addAll(object.list_coach);
					list_friends.clear();
					list_friends.addAll(object.list_friend);
					notifyIntroduceAdapter();
				}
			}
		};
		((BaseActivity) mContext).getDataFromServer(request, callBack);
	}
	//更新推荐关注界面列表
	private void notifyIntroduceAdapter(){
		if(mAdapter_coach == null){
			mAdapter_coach = new AttentionCoachAdapter(mContext, list_coach);
			gv_coach_instructor.setAdapter(mAdapter_coach);
		}else{
			mAdapter_coach.notifyDataSetChanged();
		}
		if(mAdapter_friends == null){
			mAdapter_friends = new AttentionFriendsAdapter(mContext, list_friends);
			lv_attention_friends.setAdapter(mAdapter_friends);
		}else{
			mAdapter_friends.notifyDataSetChanged();
		}
	}
	//随机教练接口
	private void getRandCoach(){
		BaseParser<IntroduceAttentionResult> parser = new IntroduceAttentionParser();
		HttpRequestImpl request = new HttpRequestImpl(mContext, AppConstants.APP_SORT_STUDENT + "/randcoach",
				parser);
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(mContext, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("token", (String) SPUtils.getAppSpUtil().get(mContext, SPUtils.KEY_TOKEN, ""));
		DataCallback<IntroduceAttentionResult> callBack = new DataCallback<IntroduceAttentionResult>() {

			@Override
			public void processData(IntroduceAttentionResult object, RequestResult result) {
				if(object != null){
					list_coach.clear();
					list_coach.addAll(object.list_coach);
					notifyIntroduceAdapter();
				}
			}
		};
		((BaseActivity) mContext).getDataFromServer(request, callBack);
	}
	//随机朋友接口
		private void getRandFriend(){
			BaseParser<IntroduceAttentionResult> parser = new IntroduceAttentionParser();
			HttpRequestImpl request = new HttpRequestImpl(mContext, AppConstants.APP_SORT_STUDENT + "/randfriend",
					parser);
			request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(mContext, SPUtils.KEY_MEMBERUSERID, ""))
					.addParam("token", (String) SPUtils.getAppSpUtil().get(mContext, SPUtils.KEY_TOKEN, ""));
			DataCallback<IntroduceAttentionResult> callBack = new DataCallback<IntroduceAttentionResult>() {

				@Override
				public void processData(IntroduceAttentionResult object, RequestResult result) {
					if(object != null){
						list_friends.clear();
						list_friends.addAll(object.list_friend);
						notifyIntroduceAdapter();
					}
				}
			};
			((BaseActivity) mContext).getDataFromServer(request, callBack);
		}
		//关注所有推荐教练
		private void toAttentionAllCoach(){
			StringBuffer coach_all = new StringBuffer();
			for(Coach coach: list_coach){
				coach_all.append(coach.coachId);
			}
			
			BaseParser<BaseBean> parser = new NoBackParser();
			HttpRequestImpl request = new HttpRequestImpl(
					mContext, AppConstants.APP_SORT_STUDENT + "/followcoach", parser);
			request.addParam("memberUserId", 
					(String) SPUtils.getAppSpUtil().get(mContext, SPUtils.KEY_MEMBERUSERID, ""))
					.addParam("coachId", coach_all.toString())
					.addParam("status", "1")
					.addParam("token", (String) SPUtils.getAppSpUtil().get(mContext, SPUtils.KEY_TOKEN, ""));
			DataCallback<BaseBean> callBack = new DataCallback<BaseBean>() {

				@Override
				public void processData(BaseBean object, RequestResult result) {
					if(object != null){
						Toast.makeText(mContext, object.message, Toast.LENGTH_SHORT).show();
						
					}
				}
			};
			((BaseActivity) mContext).getDataFromServer(request,false, callBack);
		}
		//关注所有推荐朋友
		private void toAttentionAllFriend(){
			StringBuffer friend_all = new StringBuffer();
			for(Friend coach: list_friends){
				friend_all.append(coach.friendId);
			}
			
			BaseParser<BaseBean> parser = new NoBackParser();
			HttpRequestImpl request = new HttpRequestImpl(
					mContext, AppConstants.APP_SORT_STUDENT + "/followfriend", parser);
			request.addParam("memberUserId", 
					(String) SPUtils.getAppSpUtil().get(mContext, SPUtils.KEY_MEMBERUSERID, ""))
					.addParam("friendId", friend_all.toString())
					.addParam("status", "1")
					.addParam("token", (String) SPUtils.getAppSpUtil().get(mContext, SPUtils.KEY_TOKEN, ""));
			DataCallback<BaseBean> callBack = new DataCallback<BaseBean>() {

				@Override
				public void processData(BaseBean object, RequestResult result) {
					if(object != null){
						Toast.makeText(mContext, object.message, Toast.LENGTH_SHORT).show();
						
					}
				}
			};
			((BaseActivity) mContext).getDataFromServer(request,false, callBack);
		}

	

}
