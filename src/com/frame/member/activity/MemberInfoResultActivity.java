package com.frame.member.activity;
/**
 *  会员升级结果（成功）
 *  @author long
 */
import com.frame.member.R;
import com.frame.member.Utils.SPUtils;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MemberInfoResultActivity extends BaseActivity {
	private ImageView iv_member_info;
	private TextView tv_login_button;
	
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_member_info_result);
	}

	@Override
	protected void findViewById() {
		iv_member_info = (ImageView) findViewById(R.id.iv_member_info);
		tv_login_button = (TextView) findViewById(R.id.tv_login_button);
	}
	
	@Override
	protected void setListener() {
		tv_login_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void processLogic() {
		if(1 == (Integer)SPUtils.getAppSpUtil().get(this, SPUtils.KEY_WXPAY_MEMBERTYPE, 1)){
			iv_member_info.setImageResource(R.drawable.card_blue_success);
			tv_title.setText("蓝卡会员");
		}else{
			iv_member_info.setImageResource(R.drawable.card_black_success);
			tv_title.setText("黑卡会员");
		}
	}
}
