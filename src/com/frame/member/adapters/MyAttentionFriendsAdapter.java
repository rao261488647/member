package com.frame.member.adapters;

import java.util.List;
import com.frame.member.R;
import com.frame.member.adapters.CoachSearchAdapter.ImageAndText;
import com.frame.member.bean.FollowListResult.Friends;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAttentionFriendsAdapter extends BaseAdapter{
	private Context context;
	private List<Friends> list;

	public MyAttentionFriendsAdapter(Context context,List<Friends> list) {
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
		Friends result = list.get(position);
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_my_attention_friends, null);
			holder = new ViewHolder();
			holder.iv_member_profile = (ImageView) convertView.findViewById(R.id.iv_member_profile);
			holder.tv_member_name = (TextView) convertView.findViewById(R.id.tv_member_name);
			holder.tv_member_level = (TextView) convertView.findViewById(R.id.tv_member_level);
			holder.tv_info_item_detail = (TextView) convertView.findViewById(R.id.tv_info_item_detail);
			holder.tv_attention_button = (TextView) convertView.findViewById(R.id.tv_attention_button);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_member_name.setText(result.memberName);
		holder.tv_member_level.setText("LV."+result.memberGrade);
		holder.tv_info_item_detail.setText(result.memberSign);
		
		return convertView;
	}
	
	public class ViewHolder{
		ImageView iv_member_profile;
		TextView tv_member_name,tv_member_level,tv_info_item_detail,tv_attention_button;
	}

}
