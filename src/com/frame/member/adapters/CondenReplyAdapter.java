package com.frame.member.adapters;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.apache.http.util.TextUtils;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.bean.CondenCommentsResult;
import com.frame.member.widget.RoundImageView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CondenReplyAdapter extends BaseAdapter {

	private Context mContext;
	private List<CondenCommentsResult> list;

	RoundImageView iv_conden_reply_profile;
	TextView tv_conden_reply_nickName, tv_conden_reply_date;
//	XRTextView tv_conden_reply_content;
	TextView tv_conden_reply_content;

	public CondenReplyAdapter(Context c, List<CondenCommentsResult> list) {
		this.mContext = c;
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
		CondenCommentsResult result = (CondenCommentsResult) getItem(position);
//		if (convertView == null) {
//			convertView = LayoutInflater.from(mContext).inflate(
//					R.layout.item_conden_reply, null);
//		}
//		iv_conden_reply_profile = (RoundImageView) convertView
//				.findViewById(R.id.iv_conden_reply_profile);
//		iv_conden_reply_profile.setTag(position);
//		tv_conden_reply_nickName = (TextView) convertView
//				.findViewById(R.id.tv_conden_reply_nickName);
//		tv_conden_reply_date = (TextView) convertView
//				.findViewById(R.id.tv_conden_reply_date);
////		tv_conden_reply_content = (XRTextView) convertView
////				.findViewById(R.id.tv_conden_reply_content);
//		tv_conden_reply_content = (TextView) convertView
//				.findViewById(R.id.tv_conden_reply_content);

		if(TextUtils.isEmpty(result.user.profileImaeUrl)|| "null".equals(result.user.profileImaeUrl)){
			iv_conden_reply_profile.setImageResource(R.drawable.default_avatar_3x);
		}else{
			TTApplication.getInstance().disPlayImageDef(
					result.user.profileImaeUrl, iv_conden_reply_profile);
		}
		final String targetUserId = result.user.uid;
		iv_conden_reply_profile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(mContext, PersonShowActivity.class);
//				intent.putExtra("uid", targetUserId);
//				mContext.startActivity(intent);
			}
		});
		try {
			tv_conden_reply_nickName.setText(URLDecoder.decode(result.user.nickName, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		tv_conden_reply_date.setText(result.createDate);
		String str = URLDecoder.decode(result.commentContent);
//		if("".equals(result.targetName)){
//			tv_conden_reply_content.setText(str);
//		}else{
//			tv_conden_reply_content.setText("回复  " + result.targetName + ":" + str);
//		}
		tv_conden_reply_content.setText(str);
		
		return convertView;
	}
	
	
	
	
}
