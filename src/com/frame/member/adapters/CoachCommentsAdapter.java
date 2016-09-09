package com.frame.member.adapters;

import java.util.ArrayList;
import java.util.List;
import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.activity.CoachSpaceActivity;
import com.frame.member.activity.FriendsSpaceActivity;
import com.frame.member.bean.CoachCommentsResult;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CoachCommentsAdapter extends BaseAdapter{
	private Context mContext;
	private List<CoachCommentsResult> list_name = new ArrayList<CoachCommentsResult>();
	
	public CoachCommentsAdapter(Context context,List<CoachCommentsResult> list_name){
		this.mContext = context;
		this.list_name = list_name;
	}

	@Override
	public int getCount() {
		return list_name == null ? 0 : list_name.size();
	}

	@Override
	public CoachCommentsResult getItem(int position) {
		return list_name.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		ViewHolder holder = new ViewHolder();
		final CoachCommentsResult result = list_name.get(position);
		if(view == null){
			view = LayoutInflater.from(mContext).inflate(
					R.layout.item_coach_comments, null);
			holder.tv_name_coach = (TextView) view.findViewById(R.id.tv_name_coach);
			holder.tv_level_coach = (TextView) view.findViewById(R.id.tv_level_coach);
			holder.tv_attention_coach = (TextView) view.findViewById(R.id.tv_attention_coach);
			holder.tv_comments_coach = (TextView) view.findViewById(R.id.tv_comments_coach);
			holder.tv_time_published = (TextView) view.findViewById(R.id.tv_time_published);
			holder.tv_favour_coach_num = (TextView) view.findViewById(R.id.tv_favour_coach_num);
			holder.iv_profile_coach = (ImageView) view.findViewById(R.id.iv_profile_coach);
			holder.iv_coach_favour = (ImageView) view.findViewById(R.id.iv_coach_favour);
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}
		holder.tv_name_coach.setText(result.coachName);
		holder.tv_level_coach.setText(result.levelName);
		if("0".equals(result.followCoach)){
			holder.tv_attention_coach.setText("+关注");
			holder.tv_attention_coach.setBackgroundResource(R.drawable.shape_hollow_yellow);
			holder.tv_attention_coach.setTextColor(0xffe8ce39);
		}else{
			holder.tv_attention_coach.setText("已关注");
			holder.tv_attention_coach.setBackgroundResource(R.drawable.shape_solid_yellow);
			holder.tv_attention_coach.setTextColor(0xff505050);
		}
		holder.tv_comments_coach.setText(result.commentContent);
		holder.tv_time_published.setText(result.commentTime);
		holder.tv_favour_coach_num.setText(result.praiseNum);
		if("0".equals(result.praiseCoach)){
			holder.iv_coach_favour.setImageResource(R.drawable.un_zan_2x);
		}else{
			holder.iv_coach_favour.setImageResource(R.drawable.zan_2x);
		}
		TTApplication.getInstance().disPlayImageDef(result.headImg, holder.iv_profile_coach);
		holder.iv_profile_coach.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, CoachSpaceActivity.class);
				intent.putExtra("coachId", result.coachId);
				mContext.startActivity(intent);
			}
		});
		
		return view;
	}
	
	private static class ViewHolder{
		private TextView tv_name_coach,tv_level_coach,tv_attention_coach,tv_comments_coach,
					tv_time_published,tv_favour_coach_num; 
		private ImageView iv_profile_coach,iv_coach_favour;
	}
	

}
