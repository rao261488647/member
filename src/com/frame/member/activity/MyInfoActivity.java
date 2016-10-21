package com.frame.member.activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.CommonParser;
import com.frame.member.Parsers.MyBaseInfoParser;
import com.frame.member.Parsers.NoBackParser;
import com.frame.member.Utils.BitmapUtil;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.bean.BaseBean;
import com.frame.member.bean.CommonBean;
import com.frame.member.bean.MyBaseInfoBean.MyBaseInfoResult;
import com.frame.member.bean.MyBaseInfoBean.UserInfo;
import com.ta.utdid2.android.utils.StringUtils;
/**
 * 个人基本信息
 * Ron
 */

public class MyInfoActivity extends BaseActivity implements OnClickListener{
	BaseParser<MyBaseInfoResult> parser = new MyBaseInfoParser();
	private RelativeLayout my_info_relative_nick,my_info_relative_name,
	my_info_relative_sex,my_info_relative_signature,my_info_relative_address,rl_profile;
	private ImageView my_info_img_1,my_info_img_sex;
	private TextView weixin,nickname,name,sex,birthday,signature,address;
	private String gender = "男";
	private Bitmap profile_succ = null;//头像bitmap
	private String bitmap2StrByBase64;
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_my_info);
		getData();
	}

	@Override
	protected void findViewById() {
		tv_title.setText("基本信息");
		my_info_relative_nick = (RelativeLayout)findViewById(R.id.my_info_relative_nick);
		my_info_relative_name = (RelativeLayout)findViewById(R.id.my_info_relative_name);
		my_info_relative_sex = (RelativeLayout) findViewById(R.id.my_info_relative_sex);
		my_info_relative_signature = (RelativeLayout) findViewById(R.id.my_info_relative_signature);
		my_info_relative_address = (RelativeLayout) findViewById(R.id.my_info_relative_address);
		rl_profile = (RelativeLayout) findViewById(R.id.rl_profile);
		my_info_img_1 = (ImageView) findViewById(R.id.my_info_img1); //头像
		weixin = (TextView) findViewById(R.id.my_info_text_2);
		nickname = (TextView) findViewById(R.id.my_baseinfo_nickname);
		name = (TextView) findViewById(R.id.my_baseinfo_name);
		sex = (TextView) findViewById(R.id.my_baseinfo_sex);
		birthday = (TextView) findViewById(R.id.my_baseinfo_birthday);
		signature = (TextView) findViewById(R.id.my_baseinfo_signature);
		address = (TextView) findViewById(R.id.my_baseinfo_address);
		my_info_img_sex = (ImageView) findViewById(R.id.my_info_img_sex);
	}
	
	@Override
	protected void setListener() {
		my_info_relative_nick.setOnClickListener(this);
		my_info_relative_name.setOnClickListener(this);
		my_info_relative_sex.setOnClickListener(this);
		my_info_relative_signature.setOnClickListener(this);
		my_info_relative_address.setOnClickListener(this);
		rl_profile.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		iv_title_back.setVisibility(0);
		iv_title_back.setOnClickListener(this);
	}

	/**
	 * 获取个人中心首页数据
	 * @author Ron
	 * @date 2016-9-19  上午12:01:27
	 */
	private void getData() {
		String url = AppConstants.APP_SORT_STUDENT+"/mybase";
		HttpRequestImpl request = new HttpRequestImpl(this,
				url, parser,HttpRequest.RequestMethod.post);
		request.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""));
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, "")); //用户id 
		getDataFromServer(request, callback);
	}
	/**
	 * 提交数据，修改性别
	 * @author Ron
	 * @date 2016-9-21  上午12:12:18
	 */
	private void submitData(){
		BaseParser<CommonBean> parser = new CommonParser();
		HttpRequestImpl request = new HttpRequestImpl(MyInfoActivity.this, 
				AppConstants.APP_SORT_STUDENT+"/mychangeinfo", parser);
		request.addParam("token", (String) SPUtils.getAppSpUtil().get(MyInfoActivity.this, SPUtils.KEY_TOKEN, ""));
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(MyInfoActivity.this, SPUtils.KEY_MEMBERUSERID, "")); //用户id 
		request.addParam("memberSex", gender); //性别
		getDataFromServer(request, callback1);
	}
	private DataCallback<CommonBean> callback1 = new DataCallback<CommonBean>() {

		@Override
		public void processData(CommonBean object, RequestResult result) {
			if(result == RequestResult.Success){
				if(null != object){
					if("200".equals(object.code)){
						sex.setText(gender);
						if("男".equals(gender)){
							my_info_img_sex.setImageResource(R.drawable.icon_sex_man);
						}else{
							my_info_img_sex.setImageResource(R.drawable.icon_sex_woman);
						}
					}else{
						showToast("服务器正忙，请稍后尝试！");
					}
				}
			}
		}
	};
	/**
	 * 回调方法
	 */
	private DataCallback<MyBaseInfoResult> callback = new DataCallback<MyBaseInfoResult>() {

		@Override
		public void processData(final MyBaseInfoResult object, RequestResult result) {
			if(result == RequestResult.Success){
				if(object != null){
					if("200".equals(object.code)){
						//加载数据到页面
//						initMyCenterPage(object.user, object.menuList);
						setData(object.userInfo);
					}
				}
			}
		}
	};
	
	/**
	 * 将接口的数据展示到页面
	 * @author Ron
	 * @date 2016-9-21  上午1:51:20
	 * @param info
	 */
	private void setData(UserInfo info){
		if(info != null){
			if(!StringUtils.isEmpty(info.appHeadThumbnail)){
				TTApplication.getInstance()
				.disPlayImageDef(info.appHeadThumbnail, my_info_img_1);
			}
			
		}
		weixin.setText(info.wxAccount);
		nickname.setText(info.appNiCheng);
		name.setText(info.memberName);
		sex.setText(info.memberSex);
		gender = info.memberSex; //性别    男、女
		if("男".equals(gender)){
			my_info_img_sex.setImageResource(R.drawable.icon_sex_man);
		}else{
			my_info_img_sex.setImageResource(R.drawable.icon_sex_woman);
		}
		birthday.setText(info.memberBirth);
		signature.setText(info.memberSign);
		address.setText(info.address);
	}
	//上传头像
	private void sendProfile(String head){
		BaseParser<BaseBean> parser = new NoBackParser();
		HttpRequestImpl request = new HttpRequestImpl(this,
				AppConstants.APP_SORT_STUDENT+"/mychangehead", parser);
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""))
				.addParam("head", head)
				.addParam("device", "android");
		DataCallback<BaseBean> callback = new DataCallback<BaseBean>() {

			@Override
			public void processData(BaseBean object, RequestResult result) {
				if(object!= null){
					showToast(object.message);
					my_info_img_1.setImageBitmap(profile_succ);
				}
			}
		};
		getDataFromServer(request, callback);
	}
	
	/**
     * 所有的Activity对象的返回值都是由这个方法来接收
     * requestCode:    表示的是启动一个Activity时传过去的requestCode值
     * resultCode：表示的是启动后的Activity回传值时的resultCode值
     * data：表示的是启动后的Activity回传过来的Intent对象
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1001){
            String result_value = data.getStringExtra("nickName");
            nickname.setText(result_value);
        }
        if(resultCode == 1002){
            String result_value = data.getStringExtra("name");
            name.setText(result_value);
        }
        if(resultCode == 1003){
            String result_value = data.getStringExtra("signature");
            signature.setText(result_value);
        }
        if(resultCode == 1004){
            String result_value = data.getStringExtra("address");
            address.setText(result_value);
        }
        if(resultCode == 1005){
        	String filePath = ProfileImageActivity.PROFILE_PATH;
			Bitmap bm = BitmapFactory.decodeFile(filePath);
			if (bm != null) {
				profile_succ = bm;
//				Bitmap bitmap = BitmapUtil.toRoundBitmap(bm);
//				iv_setting_url.setImageBitmap(bm);
				if(profile_succ == null){
					bitmap2StrByBase64 = "";
				}else{
					bitmap2StrByBase64 = BitmapUtil.Bitmap2StrByBase64(profile_succ);
				}
				sendProfile(bitmap2StrByBase64);
				
			}
        }
    }
	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		//修改昵称
		case R.id.my_info_relative_nick:
			intent = new Intent(this, ModifyNickNameActivity.class);
			intent.putExtra("nickName", nickname.getText().toString());
			super.startActivityForResult(intent,1001);
			break;
		//修改姓名
		case R.id.my_info_relative_name:
			intent = new Intent(this, ModifyNameActivity.class);
			intent.putExtra("name", name.getText().toString());
			super.startActivityForResult(intent,1002);
			break;
		//修改签名
		case R.id.my_info_relative_signature:
			intent = new Intent(this, ModifySignatureActivity.class);
			intent.putExtra("signature", signature.getText().toString());
			super.startActivityForResult(intent,1003);
			break;
		//修改签名
		case R.id.my_info_relative_address:
			intent = new Intent(this, ModifyAddressActivity.class);
			intent.putExtra("address", address.getText().toString());
			super.startActivityForResult(intent,1004);
			break;
			
		//修改性别
		case R.id.my_info_relative_sex:
			if("男".equals(sex.getText().toString())){
				gender = "女";
			}else{
				gender = "男";
			}
			submitData();//调用接口修改性别
			break;
		//返回，需要返回信息
		case R.id.iv_title_back:
			Bundle bundle = new Bundle(); 
			bundle.putString("name", name.getText().toString()); 
			bundle.putString("signature", signature.getText().toString()); 
			setResult(1001, this.getIntent().putExtras(bundle)); 
			this.finish();
			break;
		case R.id.rl_profile:
			Intent intent1 = new Intent(this, ProfileImageActivity.class);
			intent1.putExtra("key", 1001);
			startActivityForResult(intent1, 1001);
			break;
		default:
			break;
		}
	}
	/**
	 * 重写返回键事件
	 */
    @Override 
    public boolean onKeyDown(int keyCode, KeyEvent event) { 
       if (keyCode == KeyEvent.KEYCODE_BACK) { 
           Bundle bundle = new Bundle(); 
           bundle.putString("name", name.getText().toString()); 
           bundle.putString("signature", signature.getText().toString()); 
           setResult(1001, this.getIntent().putExtras(bundle)); 
           
           this.finish(); 
           return true; 
       }else { 
           return super.onKeyDown(keyCode, event); 
       } 
    }
}
