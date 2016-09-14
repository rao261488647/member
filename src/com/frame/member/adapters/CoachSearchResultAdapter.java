package com.frame.member.adapters;

import java.util.List;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.bean.CoachSearchResult;
import com.frame.member.bean.IntroduceAttentionResult.Coach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CoachSearchResultAdapter extends BaseAdapter{
	private Context context;
	private List<CoachSearchResult> list;

	public CoachSearchResultAdapter(Context context,List<CoachSearchResult> list) {
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		return list == null? 0:list.size();
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
		CoachSearchResult result = list.get(position);
		if(convertView == null){
			View view = LayoutInflater.from(context).inflate(R.layout.item_my_attention_coach, null);
			holder = new ViewHolder();
			holder.iv_member_profile = (ImageView) view.findViewById(R.id.iv_member_profile);
			holder.tv_member_name = (TextView) view.findViewById(R.id.tv_member_name);
			holder.tv_level_coach = (TextView) view.findViewById(R.id.tv_level_coach);
			convertView = view;
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		TTApplication.getInstance().disPlayImageDef(result.headImg, holder.iv_member_profile);
		holder.tv_member_name.setText(result.coachName);
		holder.tv_level_coach.setText(result.coachTitle);
		
		return convertView;
	}
	public static class ImageAndText{
		
		public int id_drawable;
		public String name;
		public ImageAndText(int id,String name) {
			this.id_drawable = id;
			this.name = name;
		}
	}
	public class ViewHolder{
		ImageView iv_member_profile;
		TextView tv_member_name,tv_level_coach,tv_info_item_detail,tv_attention_button;
	}

}
