package com.frame.member.activity;

import java.util.ArrayList;
import java.util.List;
import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.AdviceDetailParser;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.CoachCommentsParser;
import com.frame.member.Parsers.CoachMembersCommentsParser;
import com.frame.member.Parsers.NoBackParser;
import com.frame.member.Parsers.StudentCommentsParser;
import com.frame.member.Utils.CommonUtil;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.adapters.CoachCommentsAdapter;
import com.frame.member.adapters.CoachMemberCommentsAdapter;
import com.frame.member.adapters.StudentCommentsAdapter;
import com.frame.member.bean.AdviceDetailResult;
import com.frame.member.bean.BaseBean;
import com.frame.member.bean.AdviceDetailResult.Friends;
import com.frame.member.bean.CoachCommentsResult;
import com.frame.member.bean.CoachMembersCommentsResult;
import com.frame.member.bean.StudentCommentsResult;
import com.frame.member.widget.RoundImageView;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

public class AdviceDetailActivity extends BaseActivity {

	private ListView lv_member_comments, lv_coach_comments;
	private StudentCommentsAdapter mAdapter;
	private CoachCommentsAdapter mCoachAdapter;
	private PopupWindow mPop;
	private View container_pop;
	private ListView lv_booking_pop;
	private ArrayAdapter<String> adapter_list;
	private ArrayAdapter<String> adapter_list_share;
	private View view_black_filter;
	private List<String> list_str = new ArrayList<String>();
	private ImageView iv_person_profile, iv_vedio_cover, iv_favour_num;
	private TextView tv_name_person, tv_time_release, tv_member_level, tv_attention_button, tv_content_advice_detail,
			tv_comments_num, tv_favour_num, tv_send_comments;
	private LinearLayout ll_person_favor_profile;
	private String friendId, subjectId;
	
	private EditText et_input_comments;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_advice_detail);
	}

	@Override
	protected void findViewById() {
		lv_member_comments = (ListView) findViewById(R.id.lv_member_comments);
		lv_coach_comments = (ListView) findViewById(R.id.lv_coach_comments);
		view_black_filter = findViewById(R.id.view_black_filter);
		iv_person_profile = (ImageView) findViewById(R.id.iv_person_profile);
		iv_vedio_cover = (ImageView) findViewById(R.id.iv_vedio_cover);
		iv_favour_num = (ImageView) findViewById(R.id.iv_favour_num);
		tv_name_person = (TextView) findViewById(R.id.tv_name_person);
		tv_time_release = (TextView) findViewById(R.id.tv_time_release);
		tv_member_level = (TextView) findViewById(R.id.tv_member_level);
		tv_attention_button = (TextView) findViewById(R.id.tv_attention_button);
		tv_content_advice_detail = (TextView) findViewById(R.id.tv_content_advice_detail);
		tv_comments_num = (TextView) findViewById(R.id.tv_comments_num);
		tv_favour_num = (TextView) findViewById(R.id.tv_favour_num);
		tv_send_comments = (TextView) findViewById(R.id.tv_send_comments);
		ll_person_favor_profile = (LinearLayout) findViewById(R.id.ll_person_favor_profile);
		
		et_input_comments = (EditText) findViewById(R.id.et_input_comments);

	}

	@Override
	protected void setListener() {
		iv_title_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showPopwindow(1);
			}
		});
		tv_attention_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toAttention(friendId, v);
			}
		});
		iv_favour_num.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toPaiseFriends(friendId, subjectId, v);
			}
		});
		tv_send_comments.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO
				if(!TextUtils.isEmpty(et_input_comments.getText().toString())){
					toComment();
				}else{
					showToast("内容不能为空！");
				}
				
			}
		});

	}

	@Override
	protected void processLogic() {
		tv_title.setText("动态详情");
		iv_title_right.setImageResource(R.drawable.btn_more_normal3x);
		iv_title_back.setImageResource(R.drawable.btn_back_normal);
		// mAdapter = new CoachMemberCommentsAdapter(this, Arrays.asList("李欢",
		// "李兴策", "福城阳", "周杰伦"));
		lv_member_comments.setAdapter(mAdapter);
		lv_member_comments.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				showPopwindow(0);
			}
		});
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		view_black_filter.setAlpha(0.0f);
		container_pop = getLayoutInflater().inflate(R.layout.item_booking_pop, null);
		lv_booking_pop = (ListView) container_pop.findViewById(R.id.lv_booking_pop);
		adapter_list = new ArrayAdapter<String>(this, R.layout.item_pop_list, list_str);
		lv_booking_pop.setAdapter(adapter_list);
		lv_booking_pop.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String content = list_str.get(position);
				if("举报".equals(content)){
					showToast("举报成功");
					mPop.dismiss();
				}else if("取消".equals(content)){
					mPop.dismiss();
				}
			}
		});

		getData();
		getCoachComments();
		getMembersComments();
	}

	// 点赞雪友
	private void toPaiseFriends(String friendId, String subjectId, final View v) {
		int status;
		if (R.drawable.zan_2x == (Integer) v.getTag()) {
			status = 0;

		} else {
			status = 1;
		}
		BaseParser<BaseBean> parser = new NoBackParser();
		HttpRequestImpl request = new HttpRequestImpl(this, AppConstants.APP_SORT_STUDENT + "/praisefriend", parser);
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("friendId", friendId).addParam("status", "" + status).addParam("subjectId", subjectId)
				.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""));
		DataCallback<BaseBean> callBack = new DataCallback<BaseBean>() {

			@Override
			public void processData(BaseBean object, RequestResult result) {
				if (object != null) {
					if ("0".equals(mAdviceDetailResult.praiseAuthor)) {
						iv_favour_num.setImageResource(R.drawable.zan_2x);
						iv_favour_num.setTag(R.drawable.zan_2x);
						mAdviceDetailResult.praiseAuthor = "1";
						showToast("点赞成功");
						int praiseNum = Integer.parseInt(mAdviceDetailResult.praiseNum);
						mAdviceDetailResult.praiseNum = ++praiseNum + "";
						tv_favour_num.setText(mAdviceDetailResult.praiseNum);
					} else {
						iv_favour_num.setImageResource(R.drawable.un_zan_2x);
						iv_favour_num.setTag(R.drawable.un_zan_2x);
						mAdviceDetailResult.praiseAuthor = "0";
						showToast("取消点赞成功");
						int praiseNum = Integer.parseInt(mAdviceDetailResult.praiseNum);
						mAdviceDetailResult.praiseNum = --praiseNum + "";
						tv_favour_num.setText(mAdviceDetailResult.praiseNum);
					}
				}
			}
		};
		getDataFromServer(request, false, callBack);
	}

	// 关注\取消关注friends接口
	private void toAttention(String friendId, final View v) {
		int status;
		if ("已关注".equals(((TextView) v).getText().toString())) {
			status = 0;
		} else {
			status = 1;
		}
		BaseParser<BaseBean> parser = new NoBackParser();
		HttpRequestImpl request = new HttpRequestImpl(this, AppConstants.APP_SORT_STUDENT + "/followfriend", parser);
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("friendId", friendId).addParam("status", "" + status)
				.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""));
		DataCallback<BaseBean> callBack = new DataCallback<BaseBean>() {

			@Override
			public void processData(BaseBean object, RequestResult result) {
				if (object != null) {
					if ("已关注".equals(((TextView) v).getText().toString())) {
						((TextView) v).setText("+关注");
						((TextView) v).setBackgroundResource(R.drawable.shape_hollow_yellow);
						((TextView) v).setTextColor(0xffe8ce39);
						showToast("取消关注成功");
					} else {
						((TextView) v).setText("已关注");
						((TextView) v).setBackgroundResource(R.drawable.shape_solid_yellow);
						((TextView) v).setTextColor(0xff505050);
						showToast("关注成功");
					}
				}
			}
		};
		getDataFromServer(request, false, callBack);

	}

	private AdviceDetailResult mAdviceDetailResult;
	// 获取主数据
	private void getData() {
		BaseParser<AdviceDetailResult> parser = new AdviceDetailParser();
		HttpRequestImpl request = new HttpRequestImpl(this, AppConstants.APP_SORT_STUDENT + "/dynamicdetail", parser);
		request.addParam("subjectId", getIntent().getStringExtra("subjectId"))
				.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""));
		getDataFromServer(request, callBack);
	}

	private DataCallback<AdviceDetailResult> callBack = new DataCallback<AdviceDetailResult>() {

		@Override
		public void processData(final AdviceDetailResult object, RequestResult result) {
			if (object != null) {
				mAdviceDetailResult = object;
				friendId = object.friendId;
				subjectId = object.subjectId;
				TTApplication.getInstance().disPlayImageDef(object.user.appHeadThumbnail, iv_person_profile);
				iv_person_profile.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(AdviceDetailActivity.this, FriendsSpaceActivity.class);
						intent.putExtra(FriendsSpaceActivity.TAG_FRIEND_ID, object.user.friendId);
						startActivity(intent);
					}
				});
				TTApplication.getInstance().disPlayImageDef(object.videoPhoto, iv_vedio_cover);
				iv_vedio_cover.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(AdviceDetailActivity.this,VideoPlayActivity.class);
						intent.putExtra("video_url", object.videoUrl);
						startActivity(intent);
					}
				});
				tv_name_person.setText(object.user.memberName);
				tv_time_release.setText(object.sendTime);
				tv_member_level.setText("LV." + object.user.memberGrade);
				if ("0".equals(object.followAuthor)) {
					tv_attention_button.setText("+关注");
					tv_attention_button.setBackgroundResource(R.drawable.shape_hollow_yellow);
					tv_attention_button.setTextColor(0xffe8ce39);
				} else {
					tv_attention_button.setText("已关注");
					tv_attention_button.setBackgroundResource(R.drawable.shape_solid_yellow);
					tv_attention_button.setTextColor(0xff505050);
				}
				if ("1".equals(object.praiseAuthor)) {
					iv_favour_num.setImageResource(R.drawable.zan_2x);
					iv_favour_num.setTag(R.drawable.zan_2x);
				} else {
					iv_favour_num.setImageResource(R.drawable.un_zan_2x);
					iv_favour_num.setTag(R.drawable.un_zan_2x);
				}
				tv_content_advice_detail.setText(object.subjectName);
				tv_comments_num.setText(object.commentNum);
				tv_favour_num.setText(object.praiseNum);

				// 动态加载点赞头像
				if (object.list_friends != null && object.list_friends.size() > 0) {
					ll_person_favor_profile.removeAllViews();
					int rank = 0;
					for (Friends friend : object.list_friends) {
						RoundImageView child = new RoundImageView(AdviceDetailActivity.this);
						LayoutParams params = new LayoutParams(CommonUtil.dip2px(AdviceDetailActivity.this, 35),
								CommonUtil.dip2px(AdviceDetailActivity.this, 35));
						if (rank != 0) {
							params.leftMargin = CommonUtil.dip2px(AdviceDetailActivity.this, -5);

						}
						child.setImageResource(R.drawable.profile_example_1);
						if (!TextUtils.isEmpty(friend.appHeadThumbnail)) {
							TTApplication.getInstance().disPlayImageDef(friend.appHeadThumbnail, child);
						}
						ll_person_favor_profile.addView(child, params);
						rank++;
					}
				}

			}
		}
	};

	// 获取教练评论数据
	private void getCoachComments() {
		BaseParser<List<CoachCommentsResult>> parser = new CoachCommentsParser();
		HttpRequestImpl request = new HttpRequestImpl(this, AppConstants.APP_SORT_STUDENT + "/coachcomment", parser);
		request.addParam("subjectId", getIntent().getStringExtra("subjectId"))
				.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""));
		getDataFromServer(request, callback1);
	}

	private DataCallback<List<CoachCommentsResult>> callback1 = new DataCallback<List<CoachCommentsResult>>() {

		@Override
		public void processData(List<CoachCommentsResult> object, RequestResult result) {
			if (object != null) {
				list_coach.clear();
				list_coach.addAll(object);
				notifyCoachAdapter();
			}
		}
	};

	private List<CoachCommentsResult> list_coach = new ArrayList<CoachCommentsResult>();
	// 刷新教练评论的数据
	private void notifyCoachAdapter() {
		if (mCoachAdapter == null) {
			mCoachAdapter = new CoachCommentsAdapter(AdviceDetailActivity.this, list_coach,
					getIntent().getStringExtra("subjectId"));
			lv_coach_comments.setAdapter(mCoachAdapter);
		} else {
			mCoachAdapter.notifyDataSetChanged();
		}
	}

	// 获取学员评论数据
	private void getMembersComments() {
		BaseParser<List<StudentCommentsResult>> parser = new StudentCommentsParser();
		HttpRequestImpl request = new HttpRequestImpl(this, AppConstants.APP_SORT_STUDENT + "/studentcomment", parser);
		request.addParam("subjectId", getIntent().getStringExtra("subjectId"))
				.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""));
		getDataFromServer(request, callback2);
	}

	private DataCallback<List<StudentCommentsResult>> callback2 = new DataCallback<List<StudentCommentsResult>>() {

		@Override
		public void processData(List<StudentCommentsResult> object, RequestResult result) {
			if (object != null) {
				list_student.clear();
				list_student.addAll(object);
				notifyMembersAdapter();
			}
		}
	};
	
	private List<StudentCommentsResult> list_student = new ArrayList<StudentCommentsResult>();
	// 刷新学员评论的数据
	private void notifyMembersAdapter() {
		if (mAdapter == null) {
			mAdapter = new StudentCommentsAdapter(AdviceDetailActivity.this, list_student);
			lv_member_comments.setAdapter(mAdapter);
		} else {
			mAdapter.notifyDataSetChanged();
		}
	}
	
	//发表评论
	private void toComment() {
		BaseParser<BaseBean> parser = new NoBackParser();
		HttpRequestImpl request = new HttpRequestImpl(this, AppConstants.APP_SORT_STUDENT + "/mycomment", parser);
		request.addParam("subjectId", getIntent().getStringExtra("subjectId"))
				.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""))
				.addParam("content", et_input_comments.getText().toString());
		DataCallback<BaseBean> callback2 = new DataCallback<BaseBean>() {

			@Override
			public void processData(BaseBean object, RequestResult result) {
				if (object != null) {
					showToast(object.message);
					et_input_comments.setText("");
					getMembersComments();
				}
			}
		};
		getDataFromServer(request, callback2);
	}

	

	private void showPopwindow(int rank) {
		list_str.clear();
		switch (rank) {
		case 0:
			list_str.add("回复");
			list_str.add("举报");
			list_str.add("取消");
			adapter_list.notifyDataSetChanged();
			break;
		case 1:
			// list_str.add("分享");
			list_str.add("举报");
			list_str.add("取消");
			break;

		}
		if (mPop == null) {
			mPop = new PopupWindow(container_pop, LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT, true);
		}
		mPop.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
		// mPop.setOutsideTouchable(true);
		mPop.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				view_black_filter.setAlpha(0.0f);

			}
		});
		// mPop.showAsDropDown(ll_booking_item,0,0);
		mPop.showAtLocation(view_black_filter, Gravity.BOTTOM, 0, 0);
		view_black_filter.setAlpha(0.5f);
	}

	// 点击空白区域 自动隐藏软键盘
	// 获取点击事件
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View view = getCurrentFocus();
			if (isHideInput(view, ev)) {
				HideSoftInput(view.getWindowToken());
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	// 判定是否需要隐藏
	private boolean isHideInput(View v, MotionEvent ev) {
		if (v != null && (v instanceof EditText)) {
			int[] l = { 0, 0 };
			v.getLocationInWindow(l);
			int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
			if (ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	// 隐藏软键盘
	private void HideSoftInput(IBinder token) {
		if (token != null) {
			InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

}
