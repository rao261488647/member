package com.frame.member.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.adapters.MyBillAdapter.ViewHolder;
import com.frame.member.bean.MyMsgBean.Notice;
import com.frame.member.widget.swipemenulistview.BaseSwipListAdapter;
/**
 * 消息通知-评论适配器
 * @author Ron
 * @date 2016-8-20  上午12:14:33
 */
public class MyMsgNoticeAdapter extends BaseSwipListAdapter {
	private Context context;
	private List<Notice> mAppList;
	public MyMsgNoticeAdapter(Context context,
			 List<Notice> mAppList) {
		this.context = context;
		this.mAppList = mAppList;
	}
	
	

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public Notice getItem(int position) {
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
                    R.layout.item_my_msg_notice, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.item_my_msg_notice_1);
            holder.content = (TextView) convertView.findViewById(R.id.item_my_msg_notice_2);
            holder.date = (TextView) convertView.findViewById(R.id.item_my_msg_notice_3);
            convertView.setTag(holder);
        }else{
        	holder = (ViewHolder) convertView.getTag();
        }
        Notice item = getItem(position);
        
        holder.title.setText(item.noticeTitle);
        holder.content.setText(item.noticeContent);
        holder.date.setText(item.noticeTime);
        
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
        TextView title,content,date;
    }
}
