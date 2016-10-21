package com.frame.member.adapters;

import java.text.ParseException;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.Utils.DateUtil;
import com.frame.member.bean.MyOrderBean.OrderTeach;
import com.frame.member.widget.swipemenulistview.BaseSwipListAdapter;

public class MyOrderCoachAdapter extends BaseSwipListAdapter {
	private Context context;
	private List<OrderTeach> mAppList;
	
	public MyOrderCoachAdapter(Context context,
			 List<OrderTeach> mAppList) {
		this.context = context;
		this.mAppList = mAppList;
	}
	
	

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public OrderTeach getItem(int position) {
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
                    R.layout.item_my_order_coach, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        OrderTeach item = getItem(position);
        TTApplication.getInstance().disPlayImageDef(item.coach.coachHead, holder.my_order_coach_img_1);
        holder.my_order_coach_text_1.setText(item.coach.coachName);
        holder.my_order_coach_text_2.setText(item.skifield);
        String beginTime = null;
		try {
			beginTime = DateUtil.getDateTime("MM月dd日", DateUtil.convertStringToDate("yyyy年MM月dd日",item.date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
        holder.my_order_coach_date.setText(beginTime);
        
//        holder.my_order_coach_text_3.setText(text)
        holder.my_order_coach_text_4.setText(item.status);
        
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
        ImageView my_order_coach_img_1;
        LinearLayout badgeLayout;
        TextView my_order_coach_text_1,my_order_coach_text_2,my_order_coach_date,my_order_coach_text_3,my_order_coach_text_4;

        public ViewHolder(View view) {
        	my_order_coach_img_1 = (ImageView) view.findViewById(R.id.my_order_coach_img_1);
        	badgeLayout =  (LinearLayout) view.findViewById(R.id.my_collect_item_ll);
        	my_order_coach_text_1 = (TextView) view.findViewById(R.id.my_order_coach_text_1);
        	my_order_coach_text_2 = (TextView) view.findViewById(R.id.my_order_coach_text_2);
        	my_order_coach_date  = (TextView) view.findViewById(R.id.my_order_coach_date);
        	my_order_coach_text_3  = (TextView) view.findViewById(R.id.my_order_coach_text_3);
        	my_order_coach_text_4  = (TextView) view.findViewById(R.id.my_order_coach_text_4);
        	
            view.setTag(this);
        }
    }
}
