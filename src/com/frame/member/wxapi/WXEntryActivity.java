package com.frame.member.wxapi;

import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.RegisterLoginParser;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.BaseActivity;
import com.frame.member.activity.LoginActivity;
import com.frame.member.activity.MainActivity;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.bean.RegisterLoginResult;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.content.Intent;


public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler{
	

	private IWXAPI api;
	private String code;

	@Override
	public void onResp(BaseResp resp) {
		
		if(resp instanceof SendAuth.Resp){
			SendAuth.Resp newResp = (SendAuth.Resp) resp;
			
			//获取微信传回的code
			code = newResp.code;
			toLoginByWx();
		}
		
	}


	@Override
	public void onReq(BaseReq arg0) {
		
	}


	


	@Override
	protected void loadViewLayout() {
		
	}


	@Override
	protected void findViewById() {
		
	}


	@Override
	protected void setListener() {
		
	}


	@Override
	protected void processLogic() {
		//注册API
    	api = WXAPIFactory.createWXAPI(this, AppConstants.APP_ID_WX);
        api.handleIntent(getIntent(), this);
	}
	private void toLoginByWx(){
		BaseParser<RegisterLoginResult> parser = new RegisterLoginParser();
		HttpRequestImpl request = new HttpRequestImpl(this, 
				AppConstants.APP_SORT_STUDENT+"/wxtoapplogin", parser);
		request.addParam("code", code);
		
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
									WXEntryActivity.this, SPUtils.KEY_MEMBERUSERID, object.memberUserId);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_MEMBERIDEN, object.memberIden);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_MEMBERLLEVEL, object.memberlLevel);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_MEMBERGRADE, object.memberGrade);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_ISTEACHER, object.isTeacher);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_WXOPENID, object.wxOpenId);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_WXHEAD, object.wxHead);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_WXNICHENG, object.wxNiCheng);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_PHONENUM, object.memberTel);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_MEMBERNAME, object.memberName);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_MEMBERSEX, object.memberSex);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_MEMBERSIGN, object.memberSign);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_MEMBERBIRTH, object.memberBirth);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_MEMBERPROVINCE, object.memberProvince);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_MEMBERCITY, object.memberCity);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_MEMBERAREA, object.memberArea);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_MEMBERADDRESS, object.memberAddress);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_REGTIME, object.regtime);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_MEMBERPOINTS, object.memberPoints);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_ISOPEN, object.isOpen);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_UPDETIME, object.updeTime);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_RECOFOLLOW, object.recofollow);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_NOTICESET, object.noticeset);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_MEMBERFROM, object.memberFrom);
							SPUtils.getAppSpUtil().put(
									WXEntryActivity.this, SPUtils.KEY_TOKEN, object.token);
							startActivity(new Intent(WXEntryActivity.this,MainActivity.class));
							
						}
				}
			}
		}
	};
	
}
