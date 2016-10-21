package com.frame.member.activity;

import com.frame.member.R;
import com.frame.member.bean.NotifyBean;

import android.widget.TextView;

public class InfoDetailActivity extends BaseActivity {
	
	public static final String TAG_INTENT_NOTIFYBEAN = "tag_intent_notifybean";
	
	private TextView tv_info_detail_title,tv_info_detail_date,tv_info_detail_content;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_info_detail);
	}

	@Override
	protected void findViewById() {
		tv_info_detail_title = (TextView) findViewById(R.id.tv_info_detail_title);
		tv_info_detail_date = (TextView) findViewById(R.id.tv_info_detail_date);
		tv_info_detail_content = (TextView) findViewById(R.id.tv_info_detail_content);
	}

	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
		tv_title.setText("通知详情");
		iv_title_back.setVisibility(0);
		
		NotifyBean mNotifyBean = (NotifyBean) getIntent().getSerializableExtra(TAG_INTENT_NOTIFYBEAN);
		if(mNotifyBean != null){
			tv_info_detail_title.setText(mNotifyBean.type);
			tv_info_detail_date.setText(mNotifyBean.sendDate);
			tv_info_detail_content.setText(mNotifyBean.detail);
		}
		
	}

}
