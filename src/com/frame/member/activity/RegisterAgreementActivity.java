package com.frame.member.activity;
/**
 *  注册用户协议
 *  @author long
 */
import com.frame.member.R;

public class RegisterAgreementActivity extends BaseActivity {
	
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_register_agreement);
	}

	@Override
	protected void findViewById() {
	}
	
	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
		iv_title_back.setImageResource(R.drawable.btn_back_normal);
		tv_title.setText("郝世花滑雪服务协议");
	}
}
