package com.frame.member.adapters;

import java.util.List;
import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.activity.CoachDetailActivity;
import com.frame.member.bean.IntroduceAttentionResult.Coach;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CoachSearchAdapter extends BaseAdapter{
	private Context context;
	private List<Coach> list;

	public CoachSearchAdapter(Context context,List<Coach> list) {
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
		final Coach result = list.get(position);
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
		TTApplication.getInstance().disPlayImageDef(result.headImg, holder.iv_profile_above);
		holder.iv_profile_above.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,CoachDetailActivity.class);
				intent.putExtra("coachId", result.coachId);
				context.startActivity(intent);
			}
		});
		holder.tv_name_below.setText(result.coachName);
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
