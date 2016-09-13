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
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.bean.BaseBean;
import com.frame.member.bean.IntroduceAttentionResult.Coach;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AttentionCoachAdapter extends BaseAdapter{
	private Context mContext;
	private List<Coach> list_coach = new ArrayList<Coach>();
	
	public AttentionCoachAdapter(Context context,List<Coach> list_coach){
		this.mContext = context;
		this.list_coach = list_coach;
	}

	@Override
	public int getCount() {
		return list_coach == null ? 0 : list_coach.size();
	}

	@Override
	public Coach getItem(int position) {
		return list_coach.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		ViewHolder holder;
		final Coach result = list_coach.get(position);
		if(view == null){
			view = View.inflate(mContext, R.layout.item_attention_coach_gridview, null);
			holder = new ViewHolder();
			holder.tv_name_coach_gv = (TextView) view.findViewById(R.id.tv_name_coach_gv);
			holder.tv_level_coach_gv = (TextView) view.findViewById(R.id.tv_level_coach_gv);
			holder.tv_attention_coach_gv = (TextView) view.findViewById(R.id.tv_attention_coach_gv);
			holder.iv_attention_gv_profile = (ImageView) view.findViewById(R.id.iv_attention_gv_profile);
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}
		holder.tv_name_coach_gv.setText(result.coachName);
		holder.tv_level_coach_gv.setText(result.titleName);
		TTApplication.getInstance().disPlayImageDef(result.headImg, holder.iv_attention_gv_profile);
		holder.tv_attention_coach_gv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toAttention(result.coachId, v);
			}
		});
		return view;
	}
	
	private static class ViewHolder{
		TextView tv_name_coach_gv,tv_level_coach_gv,tv_attention_coach_gv;
		ImageView iv_attention_gv_profile;
	}
	//关注\取消关注coach接口
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
//						Toast.makeText(context, object.message, Toast.LENGTH_SHORT).show();
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

}
