package com.frame.member.adapters;

import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.bean.MainVideoBean.MainVideoCategory;
import com.frame.member.bean.MyCollectBean.Badge;
import com.frame.member.bean.MyCollectBean.CollectCoach;
import com.frame.member.widget.swipemenulistview.BaseSwipListAdapter;

public class MyCollectCoachAdapter extends BaseSwipListAdapter {
	private Context context;
	private List<CollectCoach> mAppList;
	
	public MyCollectCoachAdapter(Context context,
			 List<CollectCoach> mAppList) {
		this.context = context;
		this.mAppList = mAppList;
	}
	
	

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public CollectCoach getItem(int position) {
        return mAppList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context,
                    R.layout.item_my_collect_coach, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        CollectCoach item = getItem(position);
        TTApplication.getInstance().disPlayImageDef(item.headImg, holder.my_collect_item_img_1);
        if(item.badges != null && item.badges.size() > 0){
        	for(Badge b : item.badges){
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
        		holder.badgeLayout.addView(v);
        	}
        }
        holder.my_collect_item_text_1.setText(item.coachName);
        holder.my_collect_item_text_2.setText(item.specialty);
        holder.my_collect_ratingBar1.setRating(new Float(item.hshLevel));
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
        ImageView my_collect_item_img_1;
        LinearLayout badgeLayout;
        TextView my_collect_item_text_1,my_collect_item_text_2;
        RatingBar my_collect_ratingBar1;

        public ViewHolder(View view) {
        	my_collect_item_img_1 = (ImageView) view.findViewById(R.id.my_collect_item_img_1);
        	badgeLayout =  (LinearLayout) view.findViewById(R.id.my_collect_item_ll);
        	my_collect_item_text_1 = (TextView) view.findViewById(R.id.my_collect_item_text_1);
        	my_collect_item_text_2 = (TextView) view.findViewById(R.id.my_collect_item_text_2);
        	my_collect_ratingBar1 = (RatingBar) view.findViewById(R.id.my_collect_ratingBar1);
            view.setTag(this);
        }
    }
}
