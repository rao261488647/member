package com.frame.member.activity;

import com.frame.member.R;
import com.frame.member.bean.NotifyBean;
import com.frame.member.bean.MainNotifyBean.Notify;

import android.widget.TextView;

public class MainNotifyDetailActivity extends BaseActivity {
	
	public static final String TAG_INTENT_MAIN_NOTIFY_DATA = "tag_intent_main_notify_data";
	
	private TextView tv_main_notify_detail_title,tv_main_notify_detail_date,tv_main_notify_detail_content;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_main_notify_detail);
	}

	@Override
	protected void findViewById() {
		tv_main_notify_detail_title = (TextView) findViewById(R.id.tv_main_notify_detail_title);
		tv_main_notify_detail_date = (TextView) findViewById(R.id.tv_main_notify_detail_date);
		tv_main_notify_detail_content = (TextView) findViewById(R.id.tv_main_notify_detail_content);
	}

	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
		tv_title.setText("公告详情");
		iv_title_back.setVisibility(0);
		
		Notify mNotifyBean = (Notify) getIntent().getSerializableExtra(TAG_INTENT_MAIN_NOTIFY_DATA);
		if(mNotifyBean != null){
			tv_main_notify_detail_title.setText(mNotifyBean.name);
			tv_main_notify_detail_date.setText(mNotifyBean.date);
			tv_main_notify_detail_content.setText(mNotifyBean.detail);
		}
		
	}

}
