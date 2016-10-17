package com.frame.member.adapters;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.bean.MainEssenceBean.EssenceBestComment;
import com.frame.member.bean.MainEssenceBean.EsssenceBadge;
import com.frame.member.widget.RoundImageView;
import com.frame.member.widget.swipemenulistview.BaseSwipListAdapter;
/**
 * 精华-教练评论适配器
 * @author Ron
 * @date 2016-8-20  上午12:14:33
 */
public class MainEssenceCoachCommentAdapter extends BaseSwipListAdapter {
	private Context context;
	private List<EssenceBestComment> mAppList;
	public MainEssenceCoachCommentAdapter(Context context,
			 List<EssenceBestComment> mAppList) {
		this.context = context;
		this.mAppList = mAppList;
	}
	
	

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public EssenceBestComment getItem(int position) {
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
                    R.layout.item_essence_coach_comment, null);
            holder = new ViewHolder();
            
            holder.head = (RoundImageView) convertView.findViewById(R.id.main_essence_coach_comment_head);
            holder.coach_name = (TextView) convertView.findViewById(R.id.main_essence_coach_comment_name);
            holder.coach_level = (TextView) convertView.findViewById(R.id.main_essence_coach_comment_levelname);
            holder.date = (TextView) convertView.findViewById(R.id.main_essence_coach_comment_time);
            holder.comment = (TextView) convertView.findViewById(R.id.main_essence_coach_comment_content);
            
            holder.badgeLL = (LinearLayout) convertView.findViewById(R.id.main_essence_coach_comment_badge_ll);
            
            convertView.setTag(holder);
        } else {
			holder = (ViewHolder) convertView.getTag();
		}
        EssenceBestComment item = getItem(position);
        
        if(!TextUtils.isEmpty(item.headImg)){
        	TTApplication.getInstance()
			.disPlayImageDef(item.headImg,  holder.head);
        }
        holder.coach_name.setText(item.coachName);
        holder.coach_level.setText(item.levelname);
        holder.date.setText("尚未提供");
        holder.comment.setText(item.commentcontent);
        
        if(item.badges != null && item.badges.size() > 0){
        	holder.badgeLL.removeAllViews();
        	for(EsssenceBadge b : item.badges){
        		ImageView v = new ImageView(context);
        		if(b.badgeName.equals("表演队")){
        			v.setImageResource(R.drawable.icon_act_2x);
        		}
        		if(b.badgeName.equals("裁判队")){
        			v.setImageResource(R.drawable.icon_referee_2x);
        		}
        		if(b.badgeName.equals("培训队")){
        			v.setImageResource(R.drawable.icon_train_2x);
        		}
        		holder.badgeLL.addView(v);
        	}
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
    	RoundImageView head; 
    	LinearLayout badgeLL;
        TextView coach_name,coach_level,date,comment;
        
    }
}
