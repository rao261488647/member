package com.frame.member.adapters;

import java.util.ArrayList;
import java.util.List;
import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.NoBackParser;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.BaseActivity;
import com.frame.member.activity.CoachSpaceActivity;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.bean.BaseBean;
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
import android.widget.Toast;

public class CoachCommentsAdapter extends BaseAdapter{
	private Context mContext;
	private List<CoachCommentsResult> list_name = new ArrayList<CoachCommentsResult>();
	private String subjectId;
	
	public CoachCommentsAdapter(Context context,List<CoachCommentsResult> list_name,String subjectId){
		this.mContext = context;
		this.list_name = list_name;
		this.subjectId = subjectId;
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
			holder.iv_coach_favour.setTag(R.drawable.un_zan_2x);
		}else{
			holder.iv_coach_favour.setImageResource(R.drawable.zan_2x);
			holder.iv_coach_favour.setTag(R.drawable.zan_2x);
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
		holder.tv_attention_coach.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toAttention(result.coachId, v);
			}
		});
		holder.iv_coach_favour.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toPaiseFriends(result.coachId, subjectId, v);
				
			}
		});
		
		return view;
	}
	
	//点赞教练
		private void toPaiseFriends(String coachId,String subjectId,final View v){
			int status;
			if(R.drawable.zan_2x == (Integer)v.getTag()){
				status = 0;
				
			}else{
				status = 1;
			}
			BaseParser<BaseBean> parser = new NoBackParser();
			HttpRequestImpl request = new HttpRequestImpl(
					mContext, AppConstants.APP_SORT_STUDENT + "/praisecoach", parser);
			request.addParam("memberUserId", 
					(String) SPUtils.getAppSpUtil().get(mContext, SPUtils.KEY_MEMBERUSERID, ""))
					.addParam("coachId", coachId)
					.addParam("status", ""+status)
					.addParam("subjectId", subjectId)
					.addParam("token", (String) SPUtils.getAppSpUtil().get(mContext, SPUtils.KEY_TOKEN, ""));
			DataCallback<BaseBean> callBack = new DataCallback<BaseBean>() {

				@Override
				public void processData(BaseBean object, RequestResult result) {
					if(object != null){
//						Toast.makeText(context, object.message, Toast.LENGTH_SHORT).show();
						if(R.drawable.zan_2x == (Integer)v.getTag()){
							((ImageView)v).setImageResource(R.drawable.un_zan_2x);
							((ImageView)v).setTag(R.drawable.un_zan_2x);
							
						}else{
							((ImageView)v).setImageResource(R.drawable.zan_2x);
							((ImageView)v).setTag(R.drawable.zan_2x);
						}
					}
				}
			};
			((BaseActivity) mContext).getDataFromServer(request,false, callBack);
		}
	
	//关注\取消关注Coach接口
	private void toAttention(String coachId,final View v){
		int status;
		if("已关注".equals(((TextView)v).getText().toString())){
			status = 0;
		}else{
			status = 1;
		}
		BaseParser<BaseBean> parser = new NoBackParser();
		HttpRequestImpl request = new HttpRequestImpl(
				mContext, AppConstants.APP_SORT_STUDENT + "/followcoach", parser);
		request.addParam("memberUserId", 
				(String) SPUtils.getAppSpUtil().get(mContext, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("coachId", coachId)
				.addParam("status", ""+status)
				.addParam("token", (String) SPUtils.getAppSpUtil().get(mContext, SPUtils.KEY_TOKEN, ""));
		DataCallback<BaseBean> callBack = new DataCallback<BaseBean>() {

			@Override
			public void processData(BaseBean object, RequestResult result) {
				if(object != null){
					if("已关注".equals(((TextView)v).getText().toString())){
						((TextView)v).setText("+关注");
						((TextView)v).setBackgroundResource(R.drawable.shape_hollow_yellow);
						((TextView)v).setTextColor(0xffe8ce39);
					}else{
						((TextView)v).setText("已关注");
						((TextView)v).setBackgroundResource(R.drawable.shape_solid_yellow);
						((TextView)v).setTextColor(0xff505050);
					}
				}
			}
		};
		((BaseActivity) mContext).getDataFromServer(request,false, callBack);
		
	}
	
	private static class ViewHolder{
		private TextView tv_name_coach,tv_level_coach,tv_attention_coach,tv_comments_coach,
					tv_time_published,tv_favour_coach_num; 
		private ImageView iv_profile_coach,iv_coach_favour;
	}
	

}
