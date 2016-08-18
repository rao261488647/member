package com.frame.member.activity;
/**
 * 消息通知、评论、点赞
 * Ron
 */
import com.frame.member.R;

public class MyMsgActivity extends BaseActivity {
	
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_my_bill);
	}

	@Override
	protected void findViewById() {
		tv_title.setText("消费流水");
	}
	
	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
		iv_title_back.setVisibility(0);
	}
}
