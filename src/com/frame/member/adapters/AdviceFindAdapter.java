package com.frame.member.adapters;

import java.util.List;
import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.Utils.CommonUtil;
import com.frame.member.activity.AdviceDetailActivity;
import com.frame.member.bean.AdviceFindResult;
import com.frame.member.bean.AdviceDetailResult.Friends;
import com.frame.member.widget.RoundImageView;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class AdviceFindAdapter extends BaseAdapter{
	private Context context;
	private List<AdviceFindResult> list;

	public AdviceFindAdapter(Context context,List<AdviceFindResult> list) {
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
		final AdviceFindResult result = list.get(position);
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_advice_find, null);
			holder = new ViewHolder();
			holder.iv_person_profile = (ImageView) convertView.findViewById(R.id.iv_person_profile);
			holder.iv_vedio_cover = (ImageView) convertView.findViewById(R.id.iv_vedio_cover);
			holder.tv_name_person = (TextView) convertView.findViewById(R.id.tv_name_person);
			holder.tv_member_level = (TextView) convertView.findViewById(R.id.tv_member_level);
			holder.tv_time_release = (TextView) convertView.findViewById(R.id.tv_time_release);
			holder.tv_attention_button = (TextView) convertView.findViewById(R.id.tv_attention_button);
			holder.tv_vedio_info = (TextView) convertView.findViewById(R.id.tv_vedio_info);
			holder.tv_favour_num = (TextView) convertView.findViewById(R.id.tv_favour_num);
			holder.tv_comments_num = (TextView) convertView.findViewById(R.id.tv_comments_num);
			holder.ll_person_favor_profile = (LinearLayout) convertView.findViewById(R.id.ll_person_favor_profile);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		TTApplication.getInstance().disPlayImageDef(result.user.appHeadThumbnail, holder.iv_person_profile);
		TTApplication.getInstance().disPlayImageDef(result.videoPhoto, holder.iv_vedio_cover);
		holder.tv_name_person.setText(result.user.memberName);
		holder.tv_member_level.setText("LV."+result.user.memberGrade);
		holder.tv_time_release.setText(result.sendTime);
		if("0".equals(result.user.followAuthor)){
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
		holder.tv_comments_num.setText(result.commentNum);
		holder.iv_vedio_cover.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,AdviceDetailActivity.class); 
				intent.putExtra("subjectId", result.subjectId);
				context.startActivity(intent);
			}
		});
		//动态加载点赞头像
		if(result.list_friends != null && result.list_friends.size() > 0){
			holder.ll_person_favor_profile.removeAllViews();
			int rank = 0;
			for(com.frame.member.bean.AdviceFindResult.Friends friend:result.list_friends){
				RoundImageView child = new RoundImageView(context);
				LayoutParams params = new LayoutParams(
						CommonUtil.dip2px(context, 35),
						CommonUtil.dip2px(context, 35));
				if(rank != 0){
					params.leftMargin = CommonUtil.dip2px(context, -5);
					
				}
				child.setImageResource(R.drawable.profile_example_1);
				if(!TextUtils.isEmpty(friend.appHeadThumbnail)){
					TTApplication.getInstance().disPlayImageDef(
							friend.appHeadThumbnail, child);
				}
				holder.ll_person_favor_profile.addView(child,params);
				rank++;
			}
		}
		return convertView;
	}
	
	public class ViewHolder{
		ImageView iv_person_profile,iv_vedio_cover;
		TextView tv_name_person,tv_member_level,tv_time_release,
				tv_attention_button,tv_vedio_info,tv_favour_num,tv_comments_num;
		LinearLayout ll_person_favor_profile;
	}

}
