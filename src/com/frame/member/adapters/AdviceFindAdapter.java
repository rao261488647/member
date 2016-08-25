package com.frame.member.adapters;

import java.util.List;

import com.frame.member.R;
import com.frame.member.activity.AdviceDetailActivity;
import com.frame.member.adapters.CoachSearchAdapter.ImageAndText;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdviceFindAdapter extends BaseAdapter{
	private Context context;
	private List<ImageAndText> list;

	public AdviceFindAdapter(Context context,List<ImageAndText> list) {
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
			View view = LayoutInflater.from(context).inflate(R.layout.item_advice_find, null);
			holder = new ViewHolder();
			holder.iv_person_profile = (ImageView) view.findViewById(R.id.iv_person_profile);
			holder.iv_vedio_cover = (ImageView) view.findViewById(R.id.iv_vedio_cover);
			holder.tv_name_person = (TextView) view.findViewById(R.id.tv_name_person);
			convertView = view;
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.iv_person_profile.setImageResource(list.get(position).id_drawable);
		holder.tv_name_person.setText(list.get(position).name);
		holder.iv_vedio_cover.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context,AdviceDetailActivity.class));
			}
		});
		return convertView;
	}
	
	public class ViewHolder{
		ImageView iv_person_profile,iv_vedio_cover;
		TextView tv_name_person;
	}

}
