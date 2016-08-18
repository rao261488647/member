package com.frame.member.adapters;

import java.util.List;

import com.frame.member.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CoachSearchAdapter extends BaseAdapter{
	private Context context;
	private List<ImageAndText> list;

	public CoachSearchAdapter(Context context,List<ImageAndText> list) {
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
			View view = LayoutInflater.from(context).inflate(R.layout.item_image_and_text, null);
			holder = new ViewHolder();
			holder.iv_profile_above = (ImageView) view.findViewById(R.id.iv_profile_above);
			holder.tv_name_below = (TextView) view.findViewById(R.id.tv_name_below);
			convertView = view;
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.iv_profile_above.setImageResource(list.get(position).id_drawable);
		holder.tv_name_below.setText(list.get(position).name);
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
		ImageView iv_profile_above;
		TextView tv_name_below;
	}

}
