package com.frame.member.activity;

import com.frame.member.R;
import android.graphics.Color;

/**
 * 选择手机本地视频界面
 * 
 * @author yinqilong
 *
 */
public class SelectVedioActivity extends BaseActivity {
	
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_vedio_selector);
	}

	@Override
	protected void findViewById() {
		tv_title.setText("选择分享视频");
		tv_title.setTextColor(Color.WHITE);
		iv_title_back.setVisibility(0);
	}
	
	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
		iv_title_back.setVisibility(0);
	}
}
