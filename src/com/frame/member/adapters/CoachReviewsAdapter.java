package com.frame.member.adapters;

import java.util.List;
import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.bean.CoachSpaceResult;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CoachReviewsAdapter extends BaseAdapter{
	private Context context;
	private List<CoachSpaceResult> list;

	public CoachReviewsAdapter(Context context,List<CoachSpaceResult> list) {
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public CoachSpaceResult getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		CoachSpaceResult result = list.get(position);
		ViewHolder holder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_coach_reviews, null);
			holder = new ViewHolder();
			holder.tv_name_person = (TextView) convertView.findViewById(R.id.tv_name_person);
			holder.tv_time_release = (TextView) convertView.findViewById(R.id.tv_time_release);
			holder.tv_member_level = (TextView) convertView.findViewById(R.id.tv_member_level);
			holder.tv_attention_button = (TextView) convertView.findViewById(R.id.tv_attention_button);
			holder.tv_vedio_info = (TextView) convertView.findViewById(R.id.tv_vedio_info);
			holder.tv_favour_num = (TextView) convertView.findViewById(R.id.tv_favour_num);
			holder.tv_comments_num = (TextView) convertView.findViewById(R.id.tv_comments_num);
			holder.tv_name_coach = (TextView) convertView.findViewById(R.id.tv_name_coach);
			holder.tv_level_coach = (TextView) convertView.findViewById(R.id.tv_level_coach);
			holder.tv_comments_coach = (TextView) convertView.findViewById(R.id.tv_comments_coach);
			holder.tv_time_published = (TextView) convertView.findViewById(R.id.tv_time_published);
			holder.tv_favour_coach_num = (TextView) convertView.findViewById(R.id.tv_favour_coach_num);
			
			holder.iv_person_profile = (ImageView) convertView.findViewById(R.id.iv_person_profile);
			holder.iv_vedio_cover = (ImageView) convertView.findViewById(R.id.iv_vedio_cover);
			holder.iv_profile_coach = (ImageView) convertView.findViewById(R.id.iv_profile_coach);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
//		holder.tv_name_person.setText(list.get(position).name);
//		holder.iv_person_profile.setImageResource(list.get(position).id_drawable);
		holder.tv_name_person.setText(result.user.memberName);
		holder.tv_time_release.setText(result.sendTime);
		holder.tv_member_level.setText("LV."+result.user.memberGrade);
		if("0".equals(result.followStudent)){
			holder.tv_attention_button.setText("+关注");
			holder.tv_attention_button.setBackgroundResource(R.drawable.shape_hollow_yellow);
			holder.tv_attention_button.setTextColor(0xffe8ce39);
		}else{
			holder.tv_attention_button.setText("已关注");
			holder.tv_attention_button.setBackgroundResource(R.drawable.shape_solid_yellow);
			holder.tv_attention_button.setTextColor(0xff505050);
		}
		holder.tv_vedio_info.setText(result.subjectName);
		holder.tv_favour_num.setText(result.praiseNum);
		holder.tv_comments_coach.setText(result.bestcomment.comment);
		holder.tv_time_published.setText(result.commentTime);
		holder.tv_favour_coach_num.setText(result.cPraiseNum);
		TTApplication.getInstance().disPlayImageDef(result.user.appHeadThumbnail, holder.iv_person_profile);
		TTApplication.getInstance().disPlayImageDef(result.videoPhoto, holder.iv_vedio_cover);
		return convertView;
	}
	
	public class ViewHolder{
		TextView tv_name_person,tv_time_release,tv_member_level,tv_attention_button,
					tv_vedio_info,tv_favour_num,tv_comments_num,tv_name_coach,tv_level_coach,
					tv_comments_coach,tv_time_published,tv_favour_coach_num;
		ImageView iv_person_profile,iv_vedio_cover,iv_profile_coach;
	}

}
