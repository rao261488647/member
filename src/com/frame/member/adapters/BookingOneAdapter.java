package com.frame.member.adapters;

import java.util.ArrayList;

import javax.crypto.spec.IvParameterSpec;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.Utils.CommonUtil;
import com.frame.member.bean.BookingOneResult;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BookingOneAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<BookingOneResult.Coach> list_coach = new ArrayList<BookingOneResult.Coach>();
	
	public BookingOneAdapter(Context context,ArrayList<BookingOneResult.Coach> list_coach){
		this.mContext = context;
		this.list_coach = list_coach;
	}

	@Override
	public int getCount() {
		return list_coach == null ? 0 : list_coach.size();
	}

	@Override
	public BookingOneResult.Coach getItem(int position) {
		return list_coach.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		ViewHolder holder;
		if(view == null){
			view = View.inflate(mContext, R.layout.item_booking_one, null);
			holder = new ViewHolder();
			holder.iv_cover_booking_one = (ImageView) view.findViewById(R.id.iv_cover_booking_one);
			holder.tv_name_coach = (TextView) view.findViewById(R.id.tv_name_coach);
			holder.tv_title_coach = (TextView) view.findViewById(R.id.tv_title_coach);
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}
		holder.iv_cover_booking_one.getLayoutParams().height =
					CommonUtil.getScreenWidth((Activity) mContext)/2;
		holder.iv_cover_booking_one.setImageResource(R.drawable.booking_one_example_1);
		TTApplication.getInstance().disPlayImageDef(list_coach.get(position).headImg, holder.iv_cover_booking_one);
		holder.tv_name_coach.setText(list_coach.get(position).coachName);
		holder.tv_title_coach.setText(list_coach.get(position).levelName);
		return view;
	}
	static class ViewHolder{
		ImageView iv_cover_booking_one;
		TextView tv_name_coach,tv_title_coach;
	}

}
