package com.frame.member.widget;

import com.frame.member.R;
import com.frame.member.interfaces.NotifyDataObserver;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainNotifyView extends RelativeLayout implements
		NotifyDataObserver {

	private LinearLayout ll_main_notify_container;

	private RedCircle rc_red_circle;

	public MainNotifyView(Context context, AttributeSet attrs) {
		super(context, attrs);

		View contentView = LayoutInflater.from(context).inflate(
				R.layout.layout_item_notify, this, true);

		ll_main_notify_container = (LinearLayout) contentView
				.findViewById(R.id.ll_main_notify_container);

		rc_red_circle = (RedCircle) contentView
				.findViewById(R.id.rc_red_circle);

		setId(R.id.rl_main_notify);
	}

	public LinearLayout getNotifyContainer() {
		return ll_main_notify_container;
	}

	public RedCircle getRedCircle() {
		return rc_red_circle;
	}
	
	public void setNotifySelectStatus(boolean isSelected){
		ll_main_notify_container.getChildAt(0).setSelected(isSelected);
		ll_main_notify_container.getChildAt(1).setSelected(isSelected);
	}

	@Override
	public void onNotifyDataChanged(boolean isUnReadedExist) {
		rc_red_circle.setVisibility(isUnReadedExist ? 0 : 4);
	}

}
