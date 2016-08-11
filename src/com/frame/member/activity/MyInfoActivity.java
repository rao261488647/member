package com.frame.member.activity;
/**
 * 个人基本信息
 * Ron
 */
import com.frame.member.R;

public class MyInfoActivity extends BaseActivity {
	
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_my_info);
	}

	@Override
	protected void findViewById() {
		tv_title.setText("基本信息");
	}
	
	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
		iv_title_back.setVisibility(0);
	}
}
