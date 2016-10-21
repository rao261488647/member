package com.frame.member.activity;
/**
 *  关于
 *  @author hanshengkun
 */
import com.frame.member.R;

public class AboutActivity extends BaseActivity {
	
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_about);
	}

	@Override
	protected void findViewById() {
	  	//lv_statemetns_list = (ListView) findViewById(R.id.lv_statements_list);
		tv_title.setText("关于途优汇");
	}
	
	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
		iv_title_back.setVisibility(0);
	}
}
