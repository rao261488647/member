package com.frame.member.adapters;

import java.util.ArrayList;
import java.util.List;
import com.frame.member.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CoachMemberCommentsAdapter extends BaseAdapter{
	private Context mContext;
	private List<String> list_name = new ArrayList<String>();
	
	public CoachMemberCommentsAdapter(Context context,List<String> list_name){
		this.mContext = context;
		this.list_name = list_name;
	}

	@Override
	public int getCount() {
		return list_name == null ? 0 : list_name.size();
	}

	@Override
	public String getItem(int position) {
		return list_name.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		ViewHolder holder = new ViewHolder();
		if(view == null){
			view = LayoutInflater.from(mContext).inflate(
					R.layout.item_coach_member_comments, null);
			holder.tv_member_name = (TextView) view.findViewById(R.id.tv_member_name);
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}
		holder.tv_member_name.setText(list_name.get(position));
		
		
		return view;
	}
	
	private static class ViewHolder{
		public TextView tv_member_name; 
	}
	

}
