package com.frame.member.adapters;

import java.util.List;
import com.frame.member.R;
import com.frame.member.adapters.CoachSearchAdapter.ImageAndText;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAttentionFriendsAdapter extends BaseAdapter{
	private Context context;
	private List<ImageAndText> list;

	public MyAttentionFriendsAdapter(Context context,List<ImageAndText> list) {
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
		if(convertView == null){
			View view = LayoutInflater.from(context).inflate(R.layout.item_my_attention_friends, null);
			holder = new ViewHolder();
			holder.iv_member_profile = (ImageView) view.findViewById(R.id.iv_member_profile);
			holder.tv_member_name = (TextView) view.findViewById(R.id.tv_member_name);
			convertView = view;
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.iv_member_profile.setImageResource(list.get(position).id_drawable);
		holder.tv_member_name.setText(list.get(position).name);
		
		return convertView;
	}
	
	public class ViewHolder{
		ImageView iv_member_profile;
		TextView tv_member_name;
	}

}
