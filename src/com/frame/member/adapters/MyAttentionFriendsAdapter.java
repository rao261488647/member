package com.frame.member.adapters;

import java.util.List;
import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.NoBackParser;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.BaseActivity;
import com.frame.member.activity.FriendsSpaceActivity;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.bean.BaseBean;
import com.frame.member.bean.FollowListResult.Friends;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAttentionFriendsAdapter extends BaseAdapter {
	private Context context;
	private List<Friends> list;

	public MyAttentionFriendsAdapter(Context context, List<Friends> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Friends getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		final Friends result = list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_my_attention_friends, null);
			holder = new ViewHolder();
			holder.iv_member_profile = (ImageView) convertView.findViewById(R.id.iv_member_profile);
			holder.tv_member_name = (TextView) convertView.findViewById(R.id.tv_member_name);
			holder.tv_member_level = (TextView) convertView.findViewById(R.id.tv_member_level);
			holder.tv_info_item_detail = (TextView) convertView.findViewById(R.id.tv_info_item_detail);
			holder.tv_attention_button = (TextView) convertView.findViewById(R.id.tv_attention_button);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.iv_member_profile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, FriendsSpaceActivity.class);
				intent.putExtra(FriendsSpaceActivity.TAG_FRIEND_ID, result.friendId);
				context.startActivity(intent);
			}
		});

		holder.tv_attention_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toAttention(result);
			}
		});

		holder.tv_member_name.setText(result.memberName);
		holder.tv_member_level.setText("LV." + result.memberGrade);
		holder.tv_info_item_detail.setText(result.memberSign);
		holder.iv_member_profile.setImageResource(R.drawable.profile_example_1);
		if(!TextUtils.isEmpty(result.appHeadThumbnail)){
			TTApplication.getInstance().disPlayImageDef(result.appHeadThumbnail, holder.iv_member_profile);
		}
		return convertView;
	}

	private void toAttention(final Friends mFriends) {
		BaseParser<BaseBean> parser = new NoBackParser();
		HttpRequestImpl request = new HttpRequestImpl(context, AppConstants.APP_SORT_STUDENT + "/followfriend", parser);
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(context, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("friendId", mFriends.friendId).addParam("status", "0")
				.addParam("token", (String) SPUtils.getAppSpUtil().get(context, SPUtils.KEY_TOKEN, ""));
		DataCallback<BaseBean> callBack = new DataCallback<BaseBean>() {

			@Override
			public void processData(BaseBean object, RequestResult result) {
				if (object != null) {
					((BaseActivity) context).showToast("取消关注成功");
					list.remove(mFriends);
					notifyDataSetChanged();
				}
			}
		};
		((BaseActivity) context).getDataFromServer(request, false, callBack);

	}

	public class ViewHolder {
		ImageView iv_member_profile;
		TextView tv_member_name, tv_member_level, tv_info_item_detail, tv_attention_button;
	}

}
