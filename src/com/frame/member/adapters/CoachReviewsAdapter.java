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

public class CoachReviewsAdapter extends BaseAdapter{
	private Context context;
	private List<ImageAndText> list;

	public CoachReviewsAdapter(Context context,List<ImageAndText> list) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_coach_reviews, null);
			holder = new ViewHolder();
			holder.tv_name_person = (TextView) convertView.findViewById(R.id.tv_name_person);
			holder.iv_person_profile = (ImageView) convertView.findViewById(R.id.iv_person_profile);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_name_person.setText(list.get(position).name);
		holder.iv_person_profile.setImageResource(list.get(position).id_drawable);
		return convertView;
	}
	
	public class ViewHolder{
		TextView tv_name_person;
		ImageView iv_person_profile;
	}

}
