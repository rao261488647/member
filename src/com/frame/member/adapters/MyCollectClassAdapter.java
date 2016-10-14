package com.frame.member.adapters;

import java.text.ParseException;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.Utils.DateUtil;
import com.frame.member.bean.MyCollectBean.Badge;
import com.frame.member.bean.MyCollectBean.CollectClass;
import com.frame.member.widget.swipemenulistview.BaseSwipListAdapter;

public class MyCollectClassAdapter extends BaseSwipListAdapter {
	private Context context;
	private List<CollectClass> mAppList;
	
	public MyCollectClassAdapter(Context context,
			 List<CollectClass> mAppList) {
		this.context = context;
		this.mAppList = mAppList;
	}
	
	

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public CollectClass getItem(int position) {
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
                    R.layout.item_my_collect_class, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        CollectClass item = getItem(position);
        holder.my_collect_class_text_1.setText("￥"+item.planPrice+"/人");
        holder.my_collect_class_text_2.setText(item.personNumber+"人班  已报名"+item.signedUpNum+"人");
        holder.my_collect_class_text_3.setText(item.categoryName);
        String sDate = "";
		try {
			sDate = DateUtil.getDateTime("MM月dd日", DateUtil.convertStringToDate("yyyy-MM-dd", item.beginTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String eDate = "";
		try {
			eDate = DateUtil.getDateTime("MM月dd日", DateUtil.convertStringToDate("yyyy-MM-dd", item.overTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        holder.my_collect_class_text_4.setText(sDate+"-"+eDate+" 共"+item.hadDay+"天");
        holder.my_collect_class_text_5.setText(item.courseName+"("+item.sdPlate+")");
        holder.my_collect_class_text_6.setText(item.skifieldAddr);
        holder.my_collect_class_text_7.setText(item.courseIntro);
        if(item.signedUpStatus.equals("已满员")){
        	
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
        TextView my_collect_class_text_1,my_collect_class_text_2,my_collect_class_text_3,
        my_collect_class_text_4,my_collect_class_text_5,my_collect_class_text_6,my_collect_class_text_7;
        ImageView my_collect_class_img_1;

        public ViewHolder(View view) {
        	my_collect_class_img_1 = (ImageView) view.findViewById(R.id.my_collect_class_img_1);
        	my_collect_class_text_1 = (TextView) view.findViewById(R.id.my_collect_class_text_1);
        	my_collect_class_text_2 = (TextView) view.findViewById(R.id.my_collect_class_text_2);
        	my_collect_class_text_3 = (TextView) view.findViewById(R.id.my_collect_class_text_3);
        	my_collect_class_text_4 = (TextView) view.findViewById(R.id.my_collect_class_text_4);
        	my_collect_class_text_5 = (TextView) view.findViewById(R.id.my_collect_class_text_5);
        	my_collect_class_text_6 = (TextView) view.findViewById(R.id.my_collect_class_text_6);
        	my_collect_class_text_7 = (TextView) view.findViewById(R.id.my_collect_class_text_7);
            view.setTag(this);
        }
    }
}
