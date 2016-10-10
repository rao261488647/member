package com.frame.member.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.bean.MyMsgBean.Comment;
import com.frame.member.widget.swipemenulistview.BaseSwipListAdapter;
/**
 * 消息通知-评论适配器
 * @author Ron
 * @date 2016-8-20  上午12:14:33
 */
public class MyMsgCommentAdapter extends BaseSwipListAdapter {
	private Context context;
	private List<Comment> mAppList;
	public MyMsgCommentAdapter(Context context,
			 List<Comment> mAppList) {
		this.context = context;
		this.mAppList = mAppList;
	}
	
	

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public Comment getItem(int position) {
        return mAppList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context,
                    R.layout.item_my_msg_comment, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.item_my_msg_comment_tv_1); //名字
            holder.date = (TextView) convertView.findViewById(R.id.item_my_msg_comment_tv_2); //时间
            holder.headImg = (ImageView) convertView.findViewById(R.id.item_my_msg_comment_iv_1); //头像
            holder.videoPhoto = (ImageView) convertView.findViewById(R.id.item_my_msg_comment_iv_3); //视频封面
            holder.isCoach = (ImageView) convertView.findViewById(R.id.item_my_msg_comment_iv_2);//是否教练评论
            convertView.setTag(holder);
        }else{
        	holder = (ViewHolder) convertView.getTag();
        }
        Comment item = getItem(position);
        
        holder.name.setText(item.memberName+" 评论了你的视频");
        holder.date.setText(item.noticeTime);
        TTApplication.getInstance()
		.disPlayImageDef(item.appHeadThumbnail, holder.headImg);
        TTApplication.getInstance()
		.disPlayImageDef(item.videoPhoto, holder.videoPhoto);
        if(item.isCoachComment == 0){
        	holder.isCoach.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }


    @Override
    public boolean getSwipEnableByPosition(int position) {
        if(position % 2 == 0){
            return false;
        }
        return true;
    }

    class ViewHolder {
        ImageView headImg,videoPhoto,isCoach;
        TextView name,date;
    }
}
