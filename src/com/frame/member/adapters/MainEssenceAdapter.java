package com.frame.member.adapters;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.Utils.CommonUtil;
import com.frame.member.bean.MainEssenceBean.EssenceInfo;
import com.frame.member.bean.MainEssenceBean.EssenceStudent;
import com.frame.member.bean.MainEssenceBean.EssenceUser;
import com.frame.member.widget.RoundImageView;
import com.frame.member.widget.swipemenulistview.BaseSwipListAdapter;
/**
 * 适配器
 * @author Ron
 * @date 2016-8-20  上午12:14:33
 */
public class MainEssenceAdapter extends BaseSwipListAdapter {
	private Context context;
	private List<EssenceInfo> mAppList;
	public MainEssenceAdapter(Context context,
			 List<EssenceInfo> mAppList) {
		this.context = context;
		this.mAppList = mAppList;
	}
	
	

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public EssenceInfo getItem(int position) {
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
                    R.layout.item_main_essence, null);
            holder = new ViewHolder();
            
            holder.user_name = (TextView) convertView.findViewById(R.id.main_essence_username);
            holder.user_head = (ImageView) convertView.findViewById(R.id.main_essence_user_head);
            holder.user_level = (TextView) convertView.findViewById(R.id.main_essence_user_level);
            holder.send_time = (TextView) convertView.findViewById(R.id.main_essence_send_time);
            
            holder.videoBg =(RelativeLayout) convertView.findViewById(R.id.main_essence_video_rl);
            holder.video_desc = (TextView) convertView.findViewById(R.id.main_essence_video_desc);
            
            
            holder.studentLL = (LinearLayout) convertView.findViewById(R.id.main_essence_student);
            
            holder.zan = (ImageView) convertView.findViewById(R.id.main_essence_zan);
            holder.zan_num = (TextView) convertView.findViewById(R.id.main_essence_zan_num);
            holder.pinglun = (ImageView) convertView.findViewById(R.id.main_essence_comment);
            holder.pinglun_num = (TextView) convertView.findViewById(R.id.main_essence_comment_num);
            
            
            //教练评论列表
            holder.comment_lv = (ListView) convertView.findViewById(R.id.main_essence_coach_comment_lv);
            
            convertView.setTag(holder);
        } else {
			holder = (ViewHolder) convertView.getTag();
		}
        EssenceInfo item = getItem(position);
        EssenceUser user = item.user;
        holder.user_name.setText(user.memberName);
        TTApplication.getInstance()
		.disPlayImageDef(user.appHeadThumbnail, holder.user_head);
        holder.user_level.setText("LV"+user.memberGrade);
        holder.send_time.setText(item.sendTime);
        
        if(!TextUtils.isEmpty(item.videoPhoto)){
        	childThread(holder.videoBg,item.videoPhoto); //更新背景
        }
        holder.video_desc.setText("暂时没提供");
        
        //循环获取点赞学员头像，最多循环4个，然后放置到linearLayout布局中
        List<EssenceStudent> sList = item.students;
        if(sList != null && sList.size() > 0){
        	holder.studentLL.removeAllViews();
//        	EssenceStudent s = new EssenceStudent();
//        	s.appHeadThumbnail = "http://api.flowerski.com/upload/avatar/student/2279/original_2279.jpg";
//        	sList.add(s);
//        	sList.add(s);
//        	sList.add(s);
//        	sList.add(s);
        	int i=0;
        	for(EssenceStudent st : sList){
        		RoundImageView riv = new RoundImageView(context);
        		TTApplication.getInstance()
				.disPlayImageDef(st.appHeadThumbnail, riv);
        		LayoutParams params = new LayoutParams(CommonUtil.dip2px(context, 35),
						CommonUtil.dip2px(context, 35));
				if (i != 0) {
					params.leftMargin = CommonUtil.dip2px(context, -5);

				}
				riv.setImageResource(R.drawable.profile_example_1);
				if (!TextUtils.isEmpty(st.appHeadThumbnail)) {
					TTApplication.getInstance().disPlayImageDef(st.appHeadThumbnail, riv);
				}
				holder.studentLL.addView(riv, params);
				i++;
				if (holder.studentLL.getChildCount() == 4)
					break;
        	}
        }
        
        holder.zan_num.setText(item.praiseNum+"");
        holder.pinglun_num.setText(item.commentNum+"");
        //赞
        if(!TextUtils.isEmpty(item.praiseAuthor) && "1".equals(item.praiseAuthor)){
        	holder.zan.setImageResource(R.drawable.zan_2x);
        }
        //加载教练评论
        if(item.bestComment != null && item.bestComment.size() > 0){
        	MainEssenceCoachCommentAdapter ccadapter = new MainEssenceCoachCommentAdapter(context, item.bestComment);
        	holder.comment_lv.setAdapter(ccadapter);
        }
        return convertView;
    }

    /**
     * 子线程加载图片，刷新UI
     * @author Ron
     * @date 2016-10-18  上午1:11:12
     * @param video_bg
     * @param video_bg_url
     */
    private void childThread(final RelativeLayout video_bg,final String video_bg_url){
    	final Handler mHandler = new Handler() {  
      	  
            @Override  
            public void handleMessage(Message msg) {  
                super.handleMessage(msg);  
                switch (msg.what) {  
                case 0:  
                    //完成主界面更新,拿到数据  
                    Drawable drawable = (Drawable)msg.obj;  
                    video_bg.setBackgroundDrawable(drawable); //设置背景
                      
                    break;  
                default:  
                    break;  
                }  
            }  
      
        };  
        new Thread(new Runnable(){  
        	  
            @Override  
            public void run() {  
            	Bitmap bitmap = CommonUtil.getHttpBitmap(video_bg_url); //从网上取图片
            	Drawable drawable =new BitmapDrawable(bitmap);	
                //耗时操作，完成之后发送消息给Handler，完成UI更新；  
                mHandler.sendEmptyMessage(0);  
                  
                //需要数据传递，用下面方法；  
                Message msg =new Message();  
                msg.obj = drawable;//可以是基本类型，可以是对象，可以是List、map等；  
                mHandler.sendMessage(msg);  
            }  
              
        }).start();  
    }
    
    
    @Override
    public boolean getSwipEnableByPosition(int position) {
        if(position % 2 == 0){
            return false;
        }
        return true;
    }

    class ViewHolder {
    	ImageView user_head,zan,pinglun;
        TextView user_name,user_level,user_guanzhu,send_time;
        RelativeLayout videoBg; //视频截图
        TextView video_desc; //视频描述
        LinearLayout studentLL;
        
        TextView zan_num,pinglun_num;
        
        ListView comment_lv;
    }
}
