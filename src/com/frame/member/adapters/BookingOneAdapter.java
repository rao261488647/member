package com.frame.member.adapters;

import java.util.ArrayList;
import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.Utils.CommonUtil;
import com.frame.member.bean.BookingOneResult;
import com.frame.member.bean.BookingOneResult.Badges;
import com.frame.member.bean.BookingOneResult.Coach;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
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
		Coach result = list_coach.get(position);
		if(view == null){
			view = View.inflate(mContext, R.layout.item_booking_one, null);
			holder = new ViewHolder();
			holder.iv_cover_booking_one = (ImageView) view.findViewById(R.id.iv_cover_booking_one);
			holder.iv_icon_coach_train = (ImageView) view.findViewById(R.id.iv_icon_coach_train);
			holder.iv_icon_coach_act = (ImageView) view.findViewById(R.id.iv_icon_coach_act);
			holder.iv_icon_coach_referee = (ImageView) view.findViewById(R.id.iv_icon_coach_referee);
			holder.tv_name_coach = (TextView) view.findViewById(R.id.tv_name_coach);
			holder.tv_title_coach = (TextView) view.findViewById(R.id.tv_title_coach);
			holder.tv_num_meet = (TextView) view.findViewById(R.id.tv_num_meet);
			holder.rb_booking_one = (RatingBar) view.findViewById(R.id.rb_booking_one);
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}
		holder.iv_cover_booking_one.getLayoutParams().height =
					CommonUtil.getScreenWidth((Activity) mContext)/2;
		holder.iv_cover_booking_one.setImageResource(R.drawable.booking_one_example_1);
		TTApplication.getInstance().disPlayImageDef(result.headImg, holder.iv_cover_booking_one);
		holder.tv_name_coach.setText(result.coachName);
		holder.tv_title_coach.setText(result.levelName);
		holder.tv_num_meet.setText("累计被约"+result.meetNum+"次");
		float goal = result.goal;
		holder.rb_booking_one.setRating(goal);
		//图标逻辑
		holder.iv_icon_coach_train.setVisibility(View.GONE);
		holder.iv_icon_coach_act.setVisibility(View.GONE);
		holder.iv_icon_coach_referee.setVisibility(View.GONE);
		if(result.badges != null && result.badges.size()>0){
			for(Badges badge:result.badges){
				if("1".equals(badge.badgeId)){
					holder.iv_icon_coach_train.setVisibility(View.VISIBLE);
				}
				if("2".equals(badge.badgeId)){
					holder.iv_icon_coach_act.setVisibility(View.VISIBLE);
				}
				if("3".equals(badge.badgeId)){
					holder.iv_icon_coach_referee.setVisibility(View.VISIBLE);
				}
			}
		}
		
		return view;
	}
	static class ViewHolder{
		ImageView iv_cover_booking_one,iv_icon_coach_train,iv_icon_coach_act,iv_icon_coach_referee;
		TextView tv_name_coach,tv_title_coach,tv_num_meet;
		RatingBar rb_booking_one;
	}

}
