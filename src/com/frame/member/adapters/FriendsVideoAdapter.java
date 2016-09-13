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
import com.frame.member.bean.FriendSpaceResult;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FriendsVideoAdapter extends BaseAdapter{
	private Context context;
	private List<FriendSpaceResult> list;
	private String friendId;

	public FriendsVideoAdapter(Context context,List<FriendSpaceResult> list,String friendId) {
		this.context = context;
		this.list = list;
		this.friendId = friendId;
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
		final FriendSpaceResult result = list.get(position);
		if(convertView == null){
			View view = LayoutInflater.from(context).inflate(R.layout.item_his_video, null);
			holder = new ViewHolder();
			holder.iv_vedio_cover = (ImageView) view.findViewById(R.id.iv_vedio_cover);
			holder.iv_favour_num = (ImageView) view.findViewById(R.id.iv_favour_num);
			holder.tv_vedio_info = (TextView) view.findViewById(R.id.tv_vedio_info);
			holder.tv_favour_num = (TextView) view.findViewById(R.id.tv_favour_num);
			holder.tv_comments_num = (TextView) view.findViewById(R.id.tv_comments_num);
			convertView = view;
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		TTApplication.getInstance().disPlayImageDef(result.videoPhoto, holder.iv_vedio_cover);
		holder.iv_vedio_cover.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			}
		});
		if("1".equals(result.praiseAuthor)){
			holder.iv_favour_num.setImageResource(R.drawable.zan_2x);
			holder.iv_favour_num.setTag(R.drawable.zan_2x);
		}else{
			holder.iv_favour_num.setImageResource(R.drawable.un_zan_2x);
			holder.iv_favour_num.setTag(R.drawable.un_zan_2x);
		}
		holder.iv_favour_num.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toPaiseFriends(friendId, result.subjectId, v);
			}
		});
		holder.tv_comments_num.setText(result.commentNum);
		holder.tv_favour_num.setText(result.praiseNum);
		holder.tv_vedio_info.setText(result.subjectName);
		
		return convertView;
	}
	
	//点赞雪友
	private void toPaiseFriends(String friendId,String subjectId,final View v){
		int status;
		if(R.drawable.zan_2x == (Integer)v.getTag()){
			status = 0;
			
		}else{
			status = 1;
		}
		BaseParser<BaseBean> parser = new NoBackParser();
		HttpRequestImpl request = new HttpRequestImpl(
				context, AppConstants.APP_SORT_STUDENT + "/praisefriend", parser);
		request.addParam("memberUserId", 
				(String) SPUtils.getAppSpUtil().get(context, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("friendId", friendId)
				.addParam("status", ""+status)
				.addParam("subjectId", subjectId)
				.addParam("token", (String) SPUtils.getAppSpUtil().get(context, SPUtils.KEY_TOKEN, ""));
		DataCallback<BaseBean> callBack = new DataCallback<BaseBean>() {

			@Override
			public void processData(BaseBean object, RequestResult result) {
				if(object != null){
//					Toast.makeText(context, object.message, Toast.LENGTH_SHORT).show();
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
		((BaseActivity)context).getDataFromServer(request,false, callBack);
	}
	
	public class ViewHolder{
		ImageView iv_vedio_cover,iv_favour_num;
		TextView tv_vedio_info,tv_favour_num,tv_comments_num;
	}

}
