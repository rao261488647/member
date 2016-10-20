package com.frame.member.adapters;

import java.text.ParseException;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.Utils.DateUtil;
import com.frame.member.bean.MyOrderBean.OrderCourse;
import com.frame.member.widget.swipemenulistview.BaseSwipListAdapter;

/**
 * 我的预约-课程
 * @author raopeng
 *
 */
public class MyOrderClassAdapter extends BaseSwipListAdapter {
	private Context context;
	private List<OrderCourse> mAppList;
	
	public MyOrderClassAdapter(Context context,
			 List<OrderCourse> mAppList) {
		this.context = context;
		this.mAppList = mAppList;
	}
	
	

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public OrderCourse getItem(int position) {
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
                    R.layout.item_my_order_class, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        OrderCourse item = getItem(position);
        holder.my_study_class_text_1.setText(item.categoryName);
        String beginTime = null;
		try {
			beginTime = DateUtil.getDateTime("MM月dd日", DateUtil.convertStringToDate("yyyy年MM月dd日",item.beginTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
        String endTime = null;
		try {
			endTime = DateUtil.getDateTime("MM月dd日", DateUtil.convertStringToDate("yyyy年MM月dd日",item.overTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
        holder.my_study_class_text_4.setText(beginTime+"-"+endTime);
        holder.my_study_class_text_5.setText(item.courseName);
        holder.my_study_class_text_6.setText(item.skifield);
//        holder.my_study_class_text_7.setText(item.courseName+"("+item.sdPlate+")");
    	holder.my_study_class_text_8.setText(item.status);
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
        TextView my_study_class_text_1,my_study_class_text_4,my_study_class_text_5,
        my_study_class_text_6,my_study_class_text_7,my_study_class_text_8;

        public ViewHolder(View view) {
        	my_study_class_text_1 = (TextView) view.findViewById(R.id.my_study_class_text_1);
        	my_study_class_text_4 = (TextView) view.findViewById(R.id.my_study_class_text_4);
        	my_study_class_text_5 = (TextView) view.findViewById(R.id.my_study_class_text_5);
        	my_study_class_text_6 = (TextView) view.findViewById(R.id.my_study_class_text_6);
        	my_study_class_text_7 = (TextView) view.findViewById(R.id.my_study_class_text_7);
        	my_study_class_text_8 = (TextView) view.findViewById(R.id.my_study_class_text_8);
            view.setTag(this);
        }
    }
}
