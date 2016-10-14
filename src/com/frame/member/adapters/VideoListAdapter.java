package com.frame.member.adapters;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
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
    	final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context,
                    R.layout.item_video_list, null);
            holder = new ViewHolder();
            
            holder.main_video_list_rl =  (RelativeLayout) convertView.findViewById(R.id.main_video_list_rl);
            holder.main_video_list_title = (TextView) convertView.findViewById(R.id.main_video_list_title);
            holder.main_video_list_introduce = (TextView) convertView.findViewById(R.id.main_video_list_introduce);
            
            convertView.setTag(holder);
        } else {
			holder = (ViewHolder) convertView.getTag();
		}
        final MainVideo item = getItem(position);
        holder.main_video_list_title.setText(item.videoName); //视频图片
        holder.main_video_list_introduce.setText(item.videoIntro); //视频介绍
        
        final Handler mHandler = new Handler() {  
        	  
            @Override  
            public void handleMessage(Message msg) {  
                super.handleMessage(msg);  
                switch (msg.what) {  
                case 0:  
                    //完成主界面更新,拿到数据  
                    Drawable drawable = (Drawable)msg.obj;  
                    holder.main_video_list_rl.setBackgroundDrawable(drawable); //设置背景
                      
                    break;  
                default:  
                    break;  
                }  
            }  
      
        };  
        new Thread(new Runnable(){  
        	  
            @Override  
            public void run() {  
            	Bitmap bitmap = CommonUtil.getHttpBitmap(item.videoPhoto); //从网上取图片
            	Drawable drawable =new BitmapDrawable(bitmap);	
                //耗时操作，完成之后发送消息给Handler，完成UI更新；  
                mHandler.sendEmptyMessage(0);  
                  
                //需要数据传递，用下面方法；  
                Message msg =new Message();  
                msg.obj = drawable;//可以是基本类型，可以是对象，可以是List、map等；  
                mHandler.sendMessage(msg);  
            }  
              
        }).start();  
        
      
        
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
