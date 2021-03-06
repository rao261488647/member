package com.frame.member.adapters;

import java.util.List;
import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.adapters.CoachSearchAdapter.ImageAndText;
import com.frame.member.bean.FollowListResult.Coach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAttentionCoachAdapter extends BaseAdapter{
	private Context context;
	private List<Coach> list;

	public MyAttentionCoachAdapter(Context context,List<Coach> list) {
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Coach getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		Coach result = list.get(position);
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_my_attention_coach, null);
			holder = new ViewHolder();
			holder.iv_member_profile = (ImageView) convertView.findViewById(R.id.iv_member_profile);
			holder.tv_member_name = (TextView) convertView.findViewById(R.id.tv_member_name);
			holder.tv_level_coach = (TextView) convertView.findViewById(R.id.tv_level_coach);
			holder.tv_info_item_detail = (TextView) convertView.findViewById(R.id.tv_info_item_detail);
			holder.tv_attention_button = (TextView) convertView.findViewById(R.id.tv_attention_button);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		TTApplication.getInstance().disPlayImageDef(result.headImg, holder.iv_member_profile);
		holder.tv_member_name.setText(result.coachName);
		holder.tv_level_coach.setText(result.titleName);
		holder.tv_info_item_detail.setText(result.coachSign);
		
		return convertView;
	}
	
	public class ViewHolder{
		ImageView iv_member_profile;
		TextView tv_member_name,tv_level_coach,tv_info_item_detail,tv_attention_button;
	}

}
