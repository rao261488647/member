package com.frame.member.activity;

import com.frame.member.R;
import com.frame.member.frag.ClassBookingDialogFrag;

import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ClassDetailActivity extends BaseActivity implements OnClickListener{
	private TextView tv_class_meet;
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_class_detail);
	}

	@Override
	protected void findViewById() {
		tv_class_meet = (TextView) findViewById(R.id.tv_class_meet);
	}
	
	@Override
	protected void setListener() {
		tv_class_meet.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		tv_title.setText("课程详情");
		tv_title_right.setText("查看评论");
		tv_title_right.setTextColor(0xffffffff);
		iv_title_back.setImageResource(R.drawable.btn_back_normal);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_class_meet:
			ClassBookingDialogFrag frag = new ClassBookingDialogFrag();
			frag.setStyle(DialogFragment.STYLE_NORMAL, R.style.YouDialog);
			frag.show(getSupportFragmentManager(), "ClassBookingDialog");
			break;

		default:
			break;
		}
	}
}
