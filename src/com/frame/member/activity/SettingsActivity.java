package com.frame.member.activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 设置
 * Ron
 */
import com.frame.member.R;

public class SettingsActivity extends BaseActivity implements OnClickListener {
	
	
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_settings);
	}

	@Override
	protected void findViewById() {
	  	//lv_statemetns_list = (ListView) findViewById(R.id.lv_statements_list);
		tv_title.setText("设置");
		
	}
	
	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
		iv_title_back.setVisibility(0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_text2:
			Intent intent = new Intent(this, MyInfoActivity.class);
			this.startActivity(intent);
			break;

		default:
			break;
		}
	}
}
