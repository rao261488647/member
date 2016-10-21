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
import com.frame.member.bean.IntroduceAttentionResult.Friend;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AttentionFriendsAdapter extends BaseAdapter{
	private Context mContext;
	private List<Friend> list_friend = new ArrayList<Friend>();
	
	public AttentionFriendsAdapter(Context context,List<Friend> list_friend){
		this.mContext = context;
		this.list_friend = list_friend;
	}

	@Override
	public int getCount() {
		return list_friend == null ? 0 : list_friend.size();
	}

	@Override
	public Friend getItem(int position) {
		return list_friend.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		ViewHolder holder;
		final Friend result = list_friend.get(position);
		if(view == null){
			view = View.inflate(mContext, R.layout.item_attention_friends_list, null);
			holder = new ViewHolder();
			holder.tv_name_friends = (TextView) view.findViewById(R.id.tv_name_friends);
			holder.tv_level_friends = (TextView) view.findViewById(R.id.tv_level_friends);
			holder.tv_attention_friends_list = (TextView) view.findViewById(R.id.tv_attention_friends_list);
			holder.iv_friends_profile = (ImageView) view.findViewById(R.id.iv_friends_profile);
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}
		holder.tv_name_friends.setText(result.memberName);
		holder.tv_level_friends.setText("LV."+result.memberGrade);
		TTApplication.getInstance().disPlayImageDef(result.appHeadThumbnail, holder.iv_friends_profile);
		holder.tv_attention_friends_list.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toAttention(result.friendId, v);
			}
		});
		
		
		return view;
	}
	
	static class ViewHolder{
		TextView tv_name_friends,tv_level_friends,tv_attention_friends_list;
		ImageView iv_friends_profile;
	}
	
	//关注\取消关注friends接口
		private void toAttention(String friendId,final View v){
			int status;
			if("已关注".equals(((TextView)v).getText().toString())){
				status = 0;
			}else{
				status = 1;
			}
			BaseParser<BaseBean> parser = new NoBackParser();
			HttpRequestImpl request = new HttpRequestImpl(
					mContext, AppConstants.APP_SORT_STUDENT + "/followfriend", parser);
			request.addParam("memberUserId", 
					(String) SPUtils.getAppSpUtil().get(mContext, SPUtils.KEY_MEMBERUSERID, ""))
					.addParam("friendId", friendId)
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
