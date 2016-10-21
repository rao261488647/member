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
 * 修改地址
 * Ron
 */

public class ModifyAddressActivity extends BaseActivity  implements OnClickListener{
	
	private TextView submit;
	private EditText my_modify_address;
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_my_info_modify_address);
		
	}

	@Override
	protected void findViewById() {
		tv_title.setText("更改地址");
		submit = (TextView) findViewById(R.id.my_nick_submit);
		my_modify_address = (EditText) findViewById(R.id.my_modify_address);
	}
	
	@Override
	protected void setListener() {
		submit.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		iv_title_back.setVisibility(0);

		Intent intent = getIntent();
        String message = intent.getStringExtra("address").toString().trim();
        my_modify_address.setText(message);
		
	}

	/**
	 * 提交数据
	 * @author Ron
	 * @date 2016-9-21  上午12:12:18
	 */
	private void submitData(){
		BaseParser<CommonBean> parser = new CommonParser();
		HttpRequestImpl request = new HttpRequestImpl(ModifyAddressActivity.this, 
				AppConstants.APP_SORT_STUDENT+"/mychangeinfo", parser);
		request.addParam("token", (String) SPUtils.getAppSpUtil().get(ModifyAddressActivity.this, SPUtils.KEY_TOKEN, ""));
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, "")); //用户id 
		request.addParam("address", my_modify_address.getText().toString()); 
		getDataFromServer(request, callback1);
	}
	private DataCallback<CommonBean> callback1 = new DataCallback<CommonBean>() {

		@Override
		public void processData(CommonBean object, RequestResult result) {
			if(result == RequestResult.Success){
				if(null != object){
					if("200".equals(object.code)){
						//showToast("修改成功！");
						Intent intent = new Intent();
						intent.setClass(ModifyAddressActivity.this, MyInfoActivity.class);
		                intent.putExtra("address", my_modify_address.getText().toString());
		                setResult(1004, intent);
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
			if(StringUtils.isEmpty(my_modify_address.getText().toString())){
				showToast("地址不能为空！");
			}else{
				submitData();
			}
			break;

		default:
			break;
		}
	}
}
