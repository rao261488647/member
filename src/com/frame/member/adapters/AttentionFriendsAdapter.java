package com.frame.member.adapters;

import java.util.ArrayList;
import java.util.List;
import com.frame.member.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AttentionFriendsAdapter extends BaseAdapter{
	private Context mContext;
	private List<AttentionFriendsHolder> list_coach = new ArrayList<AttentionFriendsHolder>();
	
	public AttentionFriendsAdapter(Context context,List<AttentionFriendsHolder> list_coach){
		this.mContext = context;
		this.list_coach = list_coach;
	}

	@Override
	public int getCount() {
		return list_coach == null ? 0 : list_coach.size();
	}

	@Override
	public AttentionFriendsHolder getItem(int position) {
		return list_coach.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		ViewHolder holder;
		AttentionFriendsHolder result = list_coach.get(position);
		if(view == null){
			view = View.inflate(mContext, R.layout.item_attention_friends_list, null);
			holder = new ViewHolder();
			holder.tv_name_friends = (TextView) view.findViewById(R.id.tv_name_friends);
			holder.tv_level_friends = (TextView) view.findViewById(R.id.tv_level_friends);
			
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}
		
		holder.tv_level_friends.setText(result.level);
		holder.tv_name_friends.setText(result.name);
		
		return view;
	}
	public static class AttentionFriendsHolder{
		String name,level;
		public AttentionFriendsHolder(String name,String level){
			this.name = name;
			this.level = level;
		}
	}
	static class ViewHolder{
		TextView tv_name_friends,tv_level_friends;
	}

}
