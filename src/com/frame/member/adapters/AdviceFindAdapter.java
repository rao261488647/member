package com.frame.member.adapters;

import java.util.List;
import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.NoBackParser;
import com.frame.member.Utils.CommonUtil;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.AdviceDetailActivity;
import com.frame.member.activity.BaseActivity;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.activity.FriendsSpaceActivity;
import com.frame.member.bean.AdviceFindResult;
import com.frame.member.bean.BaseBean;
import com.frame.member.widget.RoundImageView;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class AdviceFindAdapter extends BaseAdapter {
	private Context context;
	private List<AdviceFindResult> list;
	// private int isAttention;

	public AdviceFindAdapter(Context context, List<AdviceFindResult> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		final AdviceFindResult result = list.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_advice_find, null);
			holder.iv_person_profile = (ImageView) convertView.findViewById(R.id.iv_person_profile);
			holder.iv_vedio_cover = (ImageView) convertView.findViewById(R.id.iv_vedio_cover);
			holder.iv_comments_num = (ImageView) convertView.findViewById(R.id.iv_comments_num);
			holder.iv_favour_num = (ImageView) convertView.findViewById(R.id.iv_favour_num);
			holder.tv_name_person = (TextView) convertView.findViewById(R.id.tv_name_person);
			holder.tv_member_level = (TextView) convertView.findViewById(R.id.tv_member_level);
			holder.tv_time_release = (TextView) convertView.findViewById(R.id.tv_time_release);
			holder.tv_attention_button = (TextView) convertView.findViewById(R.id.tv_attention_button);
			holder.tv_vedio_info = (TextView) convertView.findViewById(R.id.tv_vedio_info);
			holder.tv_favour_num = (TextView) convertView.findViewById(R.id.tv_favour_num);
			holder.tv_comments_num = (TextView) convertView.findViewById(R.id.tv_comments_num);
			holder.ll_person_favor_profile = (LinearLayout) convertView.findViewById(R.id.ll_person_favor_profile);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		TTApplication.getInstance().disPlayImageDef(result.user.appHeadThumbnail, holder.iv_person_profile);
		holder.iv_person_profile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, FriendsSpaceActivity.class);
				intent.putExtra(FriendsSpaceActivity.TAG_FRIEND_ID, result.user.friendId);
				context.startActivity(intent);
			}
		});
		TTApplication.getInstance().disPlayImageDef(result.videoPhoto, holder.iv_vedio_cover);
		holder.tv_name_person.setText(result.user.memberName);
		holder.tv_member_level.setText("LV." + result.user.memberGrade);
		holder.tv_time_release.setText(result.sendTime);
		if ("0".equals(result.user.followAuthor)) {
			holder.tv_attention_button.setText("+关注");
			holder.tv_attention_button.setBackgroundResource(R.drawable.shape_hollow_yellow);
			holder.tv_attention_button.setTextColor(0xffe8ce39);
		} else {
			holder.tv_attention_button.setText("已关注");
			holder.tv_attention_button.setBackgroundResource(R.drawable.shape_solid_yellow);
			holder.tv_attention_button.setTextColor(0xff505050);
		}
		holder.tv_vedio_info.setText(result.subjectName);
		holder.tv_favour_num.setText(result.praiseNum);
		if ("1".equals(result.praiseAuthor)) {
			holder.iv_favour_num.setImageResource(R.drawable.zan_2x);
			holder.iv_favour_num.setTag(R.drawable.zan_2x);
		} else {
			holder.iv_favour_num.setImageResource(R.drawable.un_zan_2x);
			holder.iv_favour_num.setTag(R.drawable.un_zan_2x);
		}
		holder.tv_comments_num.setText(result.commentNum);
		holder.tv_attention_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toAttention(result.user.friendId, v);
			}
		});
		holder.iv_favour_num.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toPaiseFriends(result,result.user.friendId, result.subjectId, v);
			}
		});
		// 动态加载点赞头像
		if (result.list_friends != null && result.list_friends.size() > 0) {
			holder.ll_person_favor_profile.removeAllViews();
			int rank = 0;
			for (com.frame.member.bean.AdviceFindResult.Friends friend : result.list_friends) {
				RoundImageView child = new RoundImageView(context);
				LayoutParams params = new LayoutParams(CommonUtil.dip2px(context, 35), CommonUtil.dip2px(context, 35));
				if (rank != 0) {
					params.leftMargin = CommonUtil.dip2px(context, -5);

				}
				child.setImageResource(R.drawable.profile_example_1);
				if (!TextUtils.isEmpty(friend.appHeadThumbnail)) {
					TTApplication.getInstance().disPlayImageDef(friend.appHeadThumbnail, child);
				}
				holder.ll_person_favor_profile.addView(child, params);
				rank++;
			}
		}

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, AdviceDetailActivity.class);
				intent.putExtra("subjectId", result.subjectId);
				context.startActivity(intent);
			}
		});
		return convertView;
	}

	// 点赞雪友
	private void toPaiseFriends(final AdviceFindResult mAdviceFindResult,String friendId, String subjectId, final View v) {
		int status;
		if (R.drawable.zan_2x == (Integer) v.getTag()) {
			status = 0;

		} else {
			status = 1;
		}
		BaseParser<BaseBean> parser = new NoBackParser();
		HttpRequestImpl request = new HttpRequestImpl(context, AppConstants.APP_SORT_STUDENT + "/praisefriend", parser);
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(context, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("friendId", friendId).addParam("status", "" + status).addParam("subjectId", subjectId)
				.addParam("token", (String) SPUtils.getAppSpUtil().get(context, SPUtils.KEY_TOKEN, ""));
		DataCallback<BaseBean> callBack = new DataCallback<BaseBean>() {

			@Override
			public void processData(BaseBean object, RequestResult result) {
				if (object != null) {
					// Toast.makeText(context, object.message,
					// Toast.LENGTH_SHORT).show();
					if ("1".equals(mAdviceFindResult.praiseAuthor)) {
//						((ImageView) v).setImageResource(R.drawable.un_zan_2x);
//						((ImageView) v).setTag(R.drawable.un_zan_2x);
						mAdviceFindResult.praiseAuthor = "0";
						int cur_praise = Integer.parseInt(mAdviceFindResult.praiseNum);
						mAdviceFindResult.praiseNum = --cur_praise + "";
						notifyDataSetChanged();
						((BaseActivity)context).showToast("取消点赞成功");
					} else {
//						((ImageView) v).setImageResource(R.drawable.zan_2x);
//						((ImageView) v).setTag(R.drawable.zan_2x);
						mAdviceFindResult.praiseAuthor = "1";
						int cur_praise = Integer.parseInt(mAdviceFindResult.praiseNum);
						mAdviceFindResult.praiseNum = ++cur_praise + "";
						notifyDataSetChanged();
						((BaseActivity)context).showToast("点赞成功");
					}
				}
			}
		};
		((BaseActivity) context).getDataFromServer(request, false, callBack);
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
		HttpRequestImpl request = new HttpRequestImpl(context, AppConstants.APP_SORT_STUDENT + "/followfriend", parser);
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(context, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("friendId", friendId).addParam("status", "" + status)
				.addParam("token", (String) SPUtils.getAppSpUtil().get(context, SPUtils.KEY_TOKEN, ""));
		DataCallback<BaseBean> callBack = new DataCallback<BaseBean>() {

			@Override
			public void processData(BaseBean object, RequestResult result) {
				if (object != null) {
					// Toast.makeText(context, object.message,
					// Toast.LENGTH_SHORT).show();
					if ("已关注".equals(((TextView) v).getText().toString())) {
						((TextView) v).setText("+关注");
						((TextView) v).setBackgroundResource(R.drawable.shape_hollow_yellow);
						((TextView) v).setTextColor(0xffe8ce39);
					} else {
						((TextView) v).setText("已关注");
						((TextView) v).setBackgroundResource(R.drawable.shape_solid_yellow);
						((TextView) v).setTextColor(0xff505050);
					}
				}
			}
		};
		((BaseActivity) context).getDataFromServer(request, false, callBack);

	}

	public class ViewHolder {
		ImageView iv_person_profile, iv_vedio_cover, iv_comments_num, iv_favour_num;
		TextView tv_name_person, tv_member_level, tv_time_release, tv_attention_button, tv_vedio_info, tv_favour_num,
				tv_comments_num;
		LinearLayout ll_person_favor_profile;
	}

}
