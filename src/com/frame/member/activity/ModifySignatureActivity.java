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
 * 签名修改
 * Ron
 */

public class ModifySignatureActivity extends BaseActivity  implements OnClickListener{
	
	private TextView submit;
	private EditText my_modify_signature;
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_my_info_modify_signature);
		
	}

	@Override
	protected void findViewById() {
		tv_title.setText("更改签名");
		submit = (TextView) findViewById(R.id.my_nick_submit);
		my_modify_signature = (EditText) findViewById(R.id.my_modify_signature);
	}
	
	@Override
	protected void setListener() {
		submit.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		iv_title_back.setVisibility(0);

		Intent intent = getIntent();
        String message = intent.getStringExtra("signature").toString().trim();
        my_modify_signature.setText(message);
		
	}

	/**
	 * 提交数据
	 * @author Ron
	 * @date 2016-9-21  上午12:12:18
	 */
	private void submitData(){
		BaseParser<CommonBean> parser = new CommonParser();
		HttpRequestImpl request = new HttpRequestImpl(ModifySignatureActivity.this, 
				AppConstants.APP_SORT_STUDENT+"/mychangeinfo", parser);
		request.addParam("token", (String) SPUtils.getAppSpUtil().get(ModifySignatureActivity.this, SPUtils.KEY_TOKEN, ""));
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, "")); //用户id 
		request.addParam("memberSign", my_modify_signature.getText().toString()); 
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
						intent.setClass(ModifySignatureActivity.this, MyInfoActivity.class);
		                intent.putExtra("signature", my_modify_signature.getText().toString());
		                setResult(1003, intent);
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
			if(StringUtils.isEmpty(my_modify_signature.getText().toString())){
				showToast("签名不能为空！");
			}else{
				submitData();
			}
			break;

		default:
			break;
		}
	}
}
