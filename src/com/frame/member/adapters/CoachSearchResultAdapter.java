package com.frame.member.adapters;

import java.util.List;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.NoBackParser;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.BaseActivity;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.bean.BaseBean;
import com.frame.member.bean.CoachSearchResult;
import com.frame.member.bean.CoachSearchResult.Badges;
import com.frame.member.bean.IntroduceAttentionResult.Coach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
		final CoachSearchResult result = list.get(position);
		if(convertView == null){
			View view = LayoutInflater.from(context).inflate(R.layout.item_my_attention_coach, null);
			holder = new ViewHolder();
			holder.iv_member_profile = (ImageView) view.findViewById(R.id.iv_member_profile);
			holder.iv_icon_coach_train = (ImageView) view.findViewById(R.id.iv_icon_coach_train);
			holder.iv_icon_coach_act = (ImageView) view.findViewById(R.id.iv_icon_coach_act);
			holder.iv_icon_coach_referee = (ImageView) view.findViewById(R.id.iv_icon_coach_referee);
			holder.tv_member_name = (TextView) view.findViewById(R.id.tv_member_name);
			holder.tv_level_coach = (TextView) view.findViewById(R.id.tv_level_coach);
			holder.tv_info_item_detail = (TextView) view.findViewById(R.id.tv_info_item_detail);
			holder.tv_attention_button = (TextView) view.findViewById(R.id.tv_attention_button);
			convertView = view;
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		TTApplication.getInstance().disPlayImageDef(result.headImg, holder.iv_member_profile);
		holder.tv_member_name.setText(result.coachName);
		holder.tv_level_coach.setText(result.coachTitle);
		//关注按钮逻辑
		if("0".equals(result.followCoach)){
			holder.tv_attention_button.setText("+关注");
			holder.tv_attention_button.setBackgroundResource(R.drawable.shape_hollow_yellow);
			holder.tv_attention_button.setTextColor(0xffe8ce39);
		}else{
			holder.tv_attention_button.setText("已关注");
			holder.tv_attention_button.setBackgroundResource(R.drawable.shape_solid_yellow);
			holder.tv_attention_button.setTextColor(0xff505050);
		}
		holder.tv_attention_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toAttention(result.coachId,v);
			}
		});
		//图标逻辑
		holder.iv_icon_coach_train.setVisibility(View.GONE);
		holder.iv_icon_coach_act.setVisibility(View.GONE);
		holder.iv_icon_coach_referee.setVisibility(View.GONE);
		if(result.badges != null && result.badges.size()>0){
			for(Badges badge:result.badges){
				if("1".equals(badge.badgeId)){
					holder.iv_icon_coach_train.setVisibility(View.VISIBLE);
				}
				if("2".equals(badge.badgeId)){
					holder.iv_icon_coach_act.setVisibility(View.VISIBLE);
				}
				if("3".equals(badge.badgeId)){
					holder.iv_icon_coach_referee.setVisibility(View.VISIBLE);
				}
			}
			
		}
		
		return convertView;
	}
	// 关注\取消关注coach接口
		private void toAttention(String coachId, final View v) {
			int status;
			if ("已关注".equals(((TextView) v).getText().toString())) {
				status = 0;
			} else {
				status = 1;
			}
			BaseParser<BaseBean> parser = new NoBackParser();
			HttpRequestImpl request = new HttpRequestImpl(context, AppConstants.APP_SORT_STUDENT + "/followcoach", parser);
			request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(context, SPUtils.KEY_MEMBERUSERID, ""))
					.addParam("coachId", coachId).addParam("status", "" + status)
					.addParam("token", (String) SPUtils.getAppSpUtil().get(context, SPUtils.KEY_TOKEN, ""));
			DataCallback<BaseBean> callBack = new DataCallback<BaseBean>() {

				@Override
				public void processData(BaseBean object, RequestResult result) {
					if (object != null) {
						// Toast.makeText(context, object.message,
						// Toast.LENGTH_SHORT).show();
						if ("已关注".equals(((TextView) v).getText().toString())) {
							((TextView) v).setText("+关注");
							((TextView) v).setBackgroundResource(R.drawable.shape_hollow_yellow);
							((TextView) v).setTextColor(0xffe8ce39);
							((BaseActivity)context).showToast("取消关注成功");
						} else {
							((TextView) v).setText("已关注");
							((TextView) v).setBackgroundResource(R.drawable.shape_solid_yellow);
							((TextView) v).setTextColor(0xff505050);
							((BaseActivity)context).showToast("关注成功");
						}
					}
				}
			};
			((BaseActivity) context).getDataFromServer(request, false, callBack);

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
		ImageView iv_member_profile,iv_icon_coach_train,iv_icon_coach_act,iv_icon_coach_referee;
		TextView tv_member_name,tv_level_coach,tv_info_item_detail,tv_attention_button;
	}

}
