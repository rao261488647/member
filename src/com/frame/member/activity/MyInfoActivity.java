package com.frame.member.activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.frame.member.R;
/**
 * 个人基本信息
 * Ron
 */

public class MyInfoActivity extends BaseActivity implements OnClickListener{
	
	private RelativeLayout my_info_relative_nick;
	
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_my_info);
	}

	@Override
	protected void findViewById() {
		tv_title.setText("基本信息");
		my_info_relative_nick = (RelativeLayout)findViewById(R.id.my_info_relative_nick);
	}
	
	@Override
	protected void setListener() {
		my_info_relative_nick.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		iv_title_back.setVisibility(0);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		//修改昵称
		case R.id.my_info_relative_nick:
			intent = new Intent(this, ModifyNickNameActivity.class);
			this.startActivity(intent);
			break;

		default:
			break;
		}
	}
}
