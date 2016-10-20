package com.frame.member.activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.CommonParser;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.bean.CommonBean;
import com.ta.utdid2.android.utils.StringUtils;
/**
 * 个人消费流水
 * Ron
 */

public class ModifyNickNameActivity extends BaseActivity  implements OnClickListener{
	
	private TextView submit;
	private EditText nickName;
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_my_info_modify_nick);
		
	}

	@Override
	protected void findViewById() {
		tv_title.setText("更改昵称");
		submit = (TextView) findViewById(R.id.my_nick_submit);
		nickName = (EditText) findViewById(R.id.my_nick_name);
	}
	
	@Override
	protected void setListener() {
		submit.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		iv_title_back.setVisibility(0);

		Intent intent = getIntent();
        String message = intent.getStringExtra("nickName").toString().trim();
        nickName.setText(message);
		
	}

	/**
	 * 提交数据
	 * @author Ron
	 * @date 2016-9-21  上午12:12:18
	 */
	private void submitData(){
		BaseParser<CommonBean> parser = new CommonParser();
		HttpRequestImpl request = new HttpRequestImpl(ModifyNickNameActivity.this, 
				AppConstants.APP_SORT_STUDENT+"/mychangeinfo", parser);
		request.addParam("token", (String) SPUtils.getAppSpUtil().get(ModifyNickNameActivity.this, SPUtils.KEY_TOKEN, ""));
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, "")); //用户id 
		request.addParam("appNiCheng", nickName.getText().toString()); //昵称
		getDataFromServer(request, callback1);
	}
	private DataCallback<CommonBean> callback1 = new DataCallback<CommonBean>() {

		@Override
		public void processData(CommonBean object, RequestResult result) {
			if(result == RequestResult.Success){
				if(null != object){
					if("200".equals(object.code)){
						Intent intent = new Intent();
						intent.setClass(ModifyNickNameActivity.this, MyInfoActivity.class);
		                intent.putExtra("nickName", nickName.getText().toString());
		                setResult(1001, intent);
						finish();
					}
				}
			}
		}
	};
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_nick_submit:
			if(StringUtils.isEmpty(nickName.getText().toString())){
				showToast("昵称不能为空！");
			}else{
				submitData();
			}
			break;

		default:
			break;
		}
	}
}
