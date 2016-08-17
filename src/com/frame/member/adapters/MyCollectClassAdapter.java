package com.frame.member.adapters;

import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.BaseSwipListAdapter;
import com.frame.member.R;
import com.frame.member.bean.MainVideoBean.MainVideoCategory;

public class MyCollectClassAdapter extends BaseSwipListAdapter {
	private Context context;
	private List<String> mAppList;
	
	public MyCollectClassAdapter(Context context,
			 List<String> mAppList) {
		this.context = context;
		this.mAppList = mAppList;
	}
	
	

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public String getItem(int position) {
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
                    R.layout.item_my_collect_class_list, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        String item = getItem(position);
//        holder.my_collect_class_img_1.setImageResource(R.drawable.icon_my_collect_full);
//        holder.my_collect_class_text_1.setText("亚特兰大");
//        holder.my_collect_class_text_2.setText("擅长双板（大回转、小回转）野雪，猫跳公园系列，个人爱雪徒步");
        holder.my_collect_class_img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "iv_icon_click", Toast.LENGTH_SHORT).show();
            }
        });
        holder.my_collect_class_text_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"iv_icon_click",Toast.LENGTH_SHORT).show();
            }
        });
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
        ImageView my_collect_class_img_1;
        TextView my_collect_class_text_1,my_collect_class_text_2,my_collect_class_text_3,
        my_collect_class_text_4,my_collect_class_text_5,my_collect_class_text_6,
        my_collect_class_text_7;
        RatingBar my_collect_ratingBar1;

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
