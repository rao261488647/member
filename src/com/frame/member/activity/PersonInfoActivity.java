package com.frame.member.activity;

/**
 *  个人详细信息
 *  @author long
 */
import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.NoBackParser;
import com.frame.member.Parsers.RegisterLoginParser;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.bean.BaseBean;
import com.frame.member.bean.RegisterLoginResult;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PersonInfoActivity extends BaseActivity implements OnClickListener{
	private EditText et_name_person,et_birthday_person;
	private TextView tv_sex_person_info,tv_login_button;
	private ImageView iv_sex_choice;
	private RelativeLayout rl_sex_person,container;
	
	private String sex = "男";
	
	
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_person_info);
	}

	@Override
	protected void findViewById() {
		et_name_person = (EditText) findViewById(R.id.et_name_person);
		rl_sex_person = (RelativeLayout) findViewById(R.id.rl_sex_person);
		et_birthday_person = (EditText) findViewById(R.id.et_birthday_person);
		container = (RelativeLayout) findViewById(R.id.container);
		tv_login_button = (TextView) findViewById(R.id.tv_login_button);
		tv_sex_person_info = (TextView) findViewById(R.id.tv_sex_person_info);
		iv_sex_choice = (ImageView) findViewById(R.id.iv_sex_choice);
	}
	
	@Override
	protected void setListener() {
		rl_sex_person.setOnClickListener(this);
		tv_login_button.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.rl_sex_person:
			if("男".equals(sex)){
				sex = "女";
				iv_sex_choice.setImageResource(R.drawable.icon_sex_woman);
				tv_sex_person_info.setText(sex);
			}else{
				sex = "男";
				iv_sex_choice.setImageResource(R.drawable.icon_sex_man);
				tv_sex_person_info.setText(sex);
			}
			break;
		case R.id.tv_login_button:
			setMyinfo();
			break;

		}
	}

	//填写个人信息
	private void setMyinfo() {
		BaseParser<BaseBean> parser = new NoBackParser();
		HttpRequestImpl request = new HttpRequestImpl(
				this, AppConstants.APP_SORT_STUDENT+"/setpwd", parser);
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
			.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN_LOGIN, ""))
			.addParam("nickName", et_name_person.getText().toString())
			.addParam("sex", sex)
			.addParam("birth", et_birthday_person.getText().toString());
		getDataFromServer(request, callBack);
	}
	private DataCallback<BaseBean> callBack = new DataCallback<BaseBean>() {

		@Override
		public void processData(BaseBean object, RequestResult result) {
			if(object != null){
				showToast(object.message);
				toLogin();
				
				
			}
		}
	};
	//登录接口
		private void toLogin(){
			BaseParser<RegisterLoginResult> parser = new RegisterLoginParser();
			HttpRequestImpl request = new HttpRequestImpl(PersonInfoActivity.this, 
					AppConstants.APP_SORT_STUDENT+"/autologin", parser);
			request.addParam("mobile", (String) SPUtils.getAppSpUtil()
					.get(PersonInfoActivity.this, SPUtils.KEY_PHONENUM, ""))
					.addParam("token", (String) SPUtils.getAppSpUtil()
							.get(PersonInfoActivity.this, SPUtils.KEY_TOKEN_LOGIN, ""));
			getDataFromServer(request, callback1);
		}
		private DataCallback<RegisterLoginResult> callback1 = new DataCallback<RegisterLoginResult>() {

			@Override
			public void processData(RegisterLoginResult object, RequestResult result) {
				if(result == RequestResult.Success){
					if(null != object){
						if("200".equals(object.code)){
							showToast(object.message);
							
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_MEMBERUSERID, object.memberUserId);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_MEMBERIDEN, object.memberIden);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_MEMBERLLEVEL, object.memberlLevel);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_MEMBERGRADE, object.memberGrade);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_ISTEACHER, object.isTeacher);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_WXOPENID, object.wxOpenId);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_WXHEAD, object.wxHead);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_WXNICHENG, object.wxNiCheng);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_PHONENUM, object.memberTel);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_MEMBERNAME, object.memberName);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_MEMBERSEX, object.memberSex);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_MEMBERSIGN, object.memberSign);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_MEMBERBIRTH, object.memberBirth);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_MEMBERPROVINCE, object.memberProvince);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_MEMBERCITY, object.memberCity);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_MEMBERAREA, object.memberArea);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_MEMBERADDRESS, object.memberAddress);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_REGTIME, object.regtime);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_MEMBERPOINTS, object.memberPoints);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_ISOPEN, object.isOpen);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_UPDETIME, object.updeTime);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_RECOFOLLOW, object.recofollow);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_NOTICESET, object.noticeset);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_MEMBERFROM, object.memberFrom);
								SPUtils.getAppSpUtil().put(
										PersonInfoActivity.this, SPUtils.KEY_TOKEN, object.token);
								startActivity(new Intent(PersonInfoActivity.this,MainActivity.class));
								
							}
					}
				}
			}
		};
}
