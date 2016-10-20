package com.frame.member.adapters;

import java.util.ArrayList;
import java.util.List;

import com.frame.member.R;
import com.frame.member.bean.BookingClassResult;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BookingClassAdapter extends BaseAdapter{
	private Context mContext;
	private List<BookingClassResult> list_coach = new ArrayList<BookingClassResult>();
	
	public BookingClassAdapter(Context context,List<BookingClassResult> list_coach){
		this.mContext = context;
		this.list_coach = list_coach;
	}

	@Override
	public int getCount() {
		return list_coach == null ? 0 : list_coach.size();
	}

	@Override
	public BookingClassResult getItem(int position) {
		return list_coach.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		ViewHolder holder;
		BookingClassResult result = new BookingClassResult();
		result = list_coach.get(position);
		if(view == null){
			view = View.inflate(mContext, R.layout.item_booking_class, null);
			holder = new ViewHolder();
			holder.tv_booking_class_price = (TextView) view.findViewById(R.id.tv_booking_class_price);
			holder.tv_booking_person_num = (TextView) view.findViewById(R.id.tv_booking_person_num);
			holder.tv_categoryName = (TextView) view.findViewById(R.id.tv_categoryName);
			holder.tv_days_class = (TextView) view.findViewById(R.id.tv_days_class);
			holder.tv_class_name = (TextView) view.findViewById(R.id.tv_class_name);
			holder.tv_class_place = (TextView) view.findViewById(R.id.tv_class_place);
			holder.tv_class_info = (TextView) view.findViewById(R.id.tv_class_info);
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}
		holder.tv_booking_class_price.setText("¥"+result.planPrice);
		holder.tv_booking_person_num.setText(result.personNumber + "人班 已报名"+result.signedUpNum +"人");
		holder.tv_categoryName.setText(result.categoryName);
		String[] beginTime = result.beginTime.split("-");
		String[] overTime = result.overTime.split("-");
		holder.tv_days_class.setText(beginTime[1] +"月"+beginTime[2]+"日-"+
				overTime[1]+"月"+overTime[2]+"日 共"+result.hadDay+"天");
		holder.tv_class_name.setText(result.courseName +"("+result.sdplate+")");
		holder.tv_class_place.setText(result.skifieldAddr);
		holder.tv_class_info.setText(result.courseIntro);
		
		
		return view;
	}
	static class ViewHolder{
		TextView tv_booking_class_price,tv_booking_person_num,tv_categoryName,
				tv_days_class,tv_class_name,tv_class_place,tv_class_info;
	}

}
