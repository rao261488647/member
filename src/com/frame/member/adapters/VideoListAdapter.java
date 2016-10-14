package com.frame.member.adapters;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.Utils.CommonUtil;
import com.frame.member.bean.VideoListBean.MainVideo;
import com.frame.member.widget.swipemenulistview.BaseSwipListAdapter;
/**
 * 消费流水适配器
 * @author Ron
 * @date 2016-8-20  上午12:14:33
 */
public class VideoListAdapter extends BaseSwipListAdapter {
	private Context context;
	private List<MainVideo> mAppList;
	public VideoListAdapter(Context context,
			 List<MainVideo> mAppList) {
		this.context = context;
		this.mAppList = mAppList;
	}
	
	

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public MainVideo getItem(int position) {
        return mAppList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context,
                    R.layout.item_my_bill, null);
            holder = new ViewHolder();
            
            holder.main_video_list_rl =  (RelativeLayout) convertView.findViewById(R.id.main_video_list_rl);
            holder.main_video_list_title = (TextView) convertView.findViewById(R.id.main_video_list_title);
            holder.main_video_list_introduce = (TextView) convertView.findViewById(R.id.main_video_list_introduce);
            
            convertView.setTag(holder);
        } else {
			holder = (ViewHolder) convertView.getTag();
		}
        MainVideo item = getItem(position);
        Bitmap bitmap = CommonUtil.getHttpBitmap(item.videoPhoto); //从网上取图片
        Drawable drawable =new BitmapDrawable(bitmap);
        holder.main_video_list_rl.setBackgroundDrawable(drawable); //设置背景
        holder.main_video_list_title.setText(item.videoName); //视频图片
        holder.main_video_list_introduce.setText(item.videoIntro); //视频介绍
        
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
    	RelativeLayout main_video_list_rl;
        TextView main_video_list_title,main_video_list_introduce;
    }
}
