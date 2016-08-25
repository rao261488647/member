package com.frame.member.adapters;

import java.util.ArrayList;
import java.util.List;
import com.frame.member.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AttentionCoachAdapter extends BaseAdapter{
	private Context mContext;
	private List<AttentionCoachHolder> list_coach = new ArrayList<AttentionCoachHolder>();
	
	public AttentionCoachAdapter(Context context,List<AttentionCoachHolder> list_coach){
		this.mContext = context;
		this.list_coach = list_coach;
	}

	@Override
	public int getCount() {
		return list_coach == null ? 0 : list_coach.size();
	}

	@Override
	public AttentionCoachHolder getItem(int position) {
		return list_coach.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		ViewHolder holder;
		 AttentionCoachHolder result = list_coach.get(position);
		if(view == null){
			view = View.inflate(mContext, R.layout.item_attention_coach_gridview, null);
			holder = new ViewHolder();
			holder.tv_name_coach_gv = (TextView) view.findViewById(R.id.tv_name_coach_gv);
			holder.tv_level_coach_gv = (TextView) view.findViewById(R.id.tv_level_coach_gv);
			
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}
		holder.tv_level_coach_gv.setText(result.level);
		holder.tv_name_coach_gv.setText(result.name);
		
		return view;
	}
	public static class AttentionCoachHolder{
		String name;
		String level;
		public AttentionCoachHolder(String name,String level){
			this.name = name;
			this.level = level;
		}
	}
	static class ViewHolder{
		TextView tv_name_coach_gv,tv_level_coach_gv;
	}

}
