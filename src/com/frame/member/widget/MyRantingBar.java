package com.frame.member.widget;

import com.frame.member.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MyRantingBar extends LinearLayout implements View.OnClickListener {
	
	private ImageView iv_my_rantingbar_1,iv_my_rantingbar_2,iv_my_rantingbar_3,iv_my_rantingbar_4,iv_my_rantingbar_5;

	private Context context;
	
	private int rant_count;
	
	public MyRantingBar(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public MyRantingBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public MyRantingBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();
	}

	private void init() {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRoot = inflater.inflate(R.layout.layout_my_rantingbar, this);
        iv_my_rantingbar_1 = (ImageView) viewRoot.findViewById(R.id.iv_my_rantingbar_1);
        iv_my_rantingbar_2 = (ImageView) viewRoot.findViewById(R.id.iv_my_rantingbar_2);
        iv_my_rantingbar_3 = (ImageView) viewRoot.findViewById(R.id.iv_my_rantingbar_3);
        iv_my_rantingbar_4 = (ImageView) viewRoot.findViewById(R.id.iv_my_rantingbar_4);
        iv_my_rantingbar_5 = (ImageView) viewRoot.findViewById(R.id.iv_my_rantingbar_5);
        
        iv_my_rantingbar_1.setOnClickListener(this);
        iv_my_rantingbar_2.setOnClickListener(this);
        iv_my_rantingbar_3.setOnClickListener(this);
        iv_my_rantingbar_4.setOnClickListener(this);
        iv_my_rantingbar_5.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		clearSelect();
		switch (v.getId()) {
		case R.id.iv_my_rantingbar_1:
			rant_count = 1;
			iv_my_rantingbar_1.setImageResource(R.drawable.ratingbar_selected_small);
			break;
		case R.id.iv_my_rantingbar_2:
			rant_count = 2;
			iv_my_rantingbar_1.setImageResource(R.drawable.ratingbar_selected_small);
			iv_my_rantingbar_2.setImageResource(R.drawable.ratingbar_selected_small);
			break;
		case R.id.iv_my_rantingbar_3:
			rant_count = 3;
			iv_my_rantingbar_1.setImageResource(R.drawable.ratingbar_selected_small);
			iv_my_rantingbar_2.setImageResource(R.drawable.ratingbar_selected_small);
			iv_my_rantingbar_3.setImageResource(R.drawable.ratingbar_selected_small);
			
			break;
		case R.id.iv_my_rantingbar_4:
			rant_count = 4;
			iv_my_rantingbar_1.setImageResource(R.drawable.ratingbar_selected_small);
			iv_my_rantingbar_2.setImageResource(R.drawable.ratingbar_selected_small);
			iv_my_rantingbar_3.setImageResource(R.drawable.ratingbar_selected_small);
			iv_my_rantingbar_4.setImageResource(R.drawable.ratingbar_selected_small);
			
			break;
		case R.id.iv_my_rantingbar_5:
			rant_count = 5;
			iv_my_rantingbar_1.setImageResource(R.drawable.ratingbar_selected_small);
			iv_my_rantingbar_2.setImageResource(R.drawable.ratingbar_selected_small);
			iv_my_rantingbar_3.setImageResource(R.drawable.ratingbar_selected_small);
			iv_my_rantingbar_4.setImageResource(R.drawable.ratingbar_selected_small);
			iv_my_rantingbar_5.setImageResource(R.drawable.ratingbar_selected_small);
			break;
		}
	}
	
	public void setRantCount(int count){
		if(count > 5)
			return;
		this.rant_count = count;
		switch (count) {
		case 1:
			iv_my_rantingbar_1.performClick();
			break;
		case 2:
			iv_my_rantingbar_2.performClick();
			break;
		case 3:
			iv_my_rantingbar_3.performClick();
			break;
		case 4:
			iv_my_rantingbar_4.performClick();
			break;
		case 5:
			iv_my_rantingbar_5.performClick();
			break;
		}
	}
	
	public int getRantCount(){
		return this.rant_count;
	}
	
	private void clearSelect(){
		iv_my_rantingbar_1.setImageResource(R.drawable.ratingbar_normal_small);
		iv_my_rantingbar_2.setImageResource(R.drawable.ratingbar_normal_small);
		iv_my_rantingbar_3.setImageResource(R.drawable.ratingbar_normal_small);
		iv_my_rantingbar_4.setImageResource(R.drawable.ratingbar_normal_small);
		iv_my_rantingbar_5.setImageResource(R.drawable.ratingbar_normal_small);
	}

}
