package com.frame.member.activity;
/**
 * 个人消费流水
 * Ron
 */
import com.frame.member.R;

public class ModifyNickNameActivity extends BaseActivity {
	
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_my_info_modify_nick);
	}

	@Override
	protected void findViewById() {
		tv_title.setText("更改昵称");
	}
	
	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
		iv_title_back.setVisibility(0);
	}
}
