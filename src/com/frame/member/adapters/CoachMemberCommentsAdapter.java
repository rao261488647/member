package com.frame.member.adapters;

import java.util.ArrayList;
import java.util.List;
import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.activity.FriendsSpaceActivity;
import com.frame.member.bean.CoachMembersCommentsResult;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CoachMemberCommentsAdapter extends BaseAdapter{
	private Context mContext;
	private List<CoachMembersCommentsResult> list_name = new ArrayList<CoachMembersCommentsResult>();
	
	public CoachMemberCommentsAdapter(Context context,List<CoachMembersCommentsResult> list_name){
		this.mContext = context;
		this.list_name = list_name;
	}

	@Override
	public int getCount() {
		return list_name == null ? 0 : list_name.size();
	}

	@Override
	public CoachMembersCommentsResult getItem(int position) {
		return list_name.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		ViewHolder holder = new ViewHolder();
		final CoachMembersCommentsResult result = list_name.get(position);
		if(view == null){
			view = LayoutInflater.from(mContext).inflate(
					R.layout.item_coach_member_comments, null);
			holder.tv_member_name = (TextView) view.findViewById(R.id.tv_member_name);
			holder.tv_member_level = (TextView) view.findViewById(R.id.tv_member_level);
			holder.tv_info_item_detail = (TextView) view.findViewById(R.id.tv_info_item_detail);
			holder.tv_time_publish = (TextView) view.findViewById(R.id.tv_time_publish);
			holder.iv_member_profile = (ImageView) view.findViewById(R.id.iv_member_profile);
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}
		holder.tv_member_name.setText(result.memberName);
		holder.tv_member_level.setText("LV."+result.appHeadThumbnail);
		holder.tv_info_item_detail.setText(result.commentContent);
		holder.tv_time_publish.setText(result.commentTime);
		TTApplication.getInstance().disPlayImageDef(result.appHeadThumbnail, holder.iv_member_profile);
		holder.iv_member_profile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, FriendsSpaceActivity.class);
				intent.putExtra("friendId", result.friendId);
				mContext.startActivity(intent);
			}
		});
		
		return view;
	}
	
	private static class ViewHolder{
		private TextView tv_member_name,tv_member_level,tv_info_item_detail,tv_time_publish; 
		private ImageView iv_member_profile;
	}
	

}
