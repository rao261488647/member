package com.frame.member.activity;

import org.json.JSONObject;

import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.DatePicker;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Utils.CommCallBack;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;

public class ContractActivity extends BaseActivity implements OnDateSetListener {

	// private RelativeLayout rl_old_pwd, rl_new_pwd, rl_renew_pwd;
	private TextView qiandingren, qiandingriqi, cheliangpinpai, xinghao,
			chepaihao, bangdingshoujihao, meiyuezujin, yajinjiner,
			meiyuejiaozuri, yijiaozujin, shagnqijiaozuri,
			zhuangtai,didizhuangche, youbuzhuangche, yihaozhuangche,
			qitapingtai, sijizhuangtai, beizhu;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_contract);
		
	}

	@Override
	protected void findViewById() {

		tv_title.setText("合同详情");
		
		qiandingren =  (TextView) findViewById(R.id.qiandingren);
		qiandingriqi =  (TextView) findViewById(R.id.qiandingriqi);
		cheliangpinpai =  (TextView) findViewById(R.id.cheliangpinpai);
		xinghao =  (TextView) findViewById(R.id.xinghao);
		chepaihao =  (TextView) findViewById(R.id.chepaihao);
		bangdingshoujihao =  (TextView) findViewById(R.id.bangdingshoujihao);
		meiyuezujin =  (TextView) findViewById(R.id.meiyuezujin);
		//yajinjiner =  (TextView) findViewById(R.id.yajinjiner);
		meiyuejiaozuri =  (TextView) findViewById(R.id.meiyuejiaozuri);
		yijiaozujin =  (TextView) findViewById(R.id.yijiaozujin);
		//shagnqijiaozuri =  (TextView) findViewById(R.id.shagnqijiaozuri);
		zhuangtai =  (TextView) findViewById(R.id.zhuangtai);
		didizhuangche =  (TextView) findViewById(R.id.didizhuangche);
		youbuzhuangche =  (TextView) findViewById(R.id.youbuzhuangche);
		//yihaozhuangche =  (TextView) findViewById(R.id.yihaozhuangche);
		//qitapingtai =  (TextView) findViewById(R.id.qitapingtai);
		sijizhuangtai =  (TextView) findViewById(R.id.sijizhuangtai);
		beizhu =  (TextView) findViewById(R.id.beizhu);
		//tv_cancel_contract_commit = (TextView) findViewById(R.id.tv_cancel_contract_commit);
		
		getContractInfoFromServer();
	}

	@Override
	protected void processLogic() {
		iv_title_back.setVisibility(0);
	}

	private boolean isVibrate() {
		return true;
	}

	private boolean isCloseOnSingleTapDay() {
		return false;
	}


	@Override
	protected void setListener() {
		
//		tv_cancel_contract_commit.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//
//				HttpRequest request_reg = new HttpRequestImpl(ContractActivity.this,
//						AppConstants.DISSCONTRACT, null);
//
//				request_reg.addParam("tel",
//										(String) SPUtils.getAppSpUtil().get(
//												ContractActivity.this,
//												SPUtils.KEY_PHONENUM, ""));
//
//				getDataFromServer(request_reg, new CommCallBack(ContractActivity.this,
//						"", new CommCallBack.CallBackSucc() {
//
//							@Override
//							public void onRequestSucc(JSONObject object) {
//								if("200".equals(object.optString("status"))){
//									showToast("合同解除成功!");
//									tv_cancel_contract_commit.setVisibility(View.INVISIBLE);
//								}else{
//									showToast("合同解除失败，请联系客服!");
//								}
//							}
//						}), "");
//				
//			
//			}
//		});
	}
	
	private void getContractInfoFromServer() {
		HttpRequest request_reg = new HttpRequestImpl(ContractActivity.this,
				AppConstants.CONTRACTINFO, null);

		request_reg.addParam("tel",
								(String) SPUtils.getAppSpUtil().get(
										ContractActivity.this,
										SPUtils.KEY_PHONENUM, ""));

		getDataFromServer(request_reg, new CommCallBack(ContractActivity.this,
				"", new CommCallBack.CallBackSucc() {

					@Override
					public void onRequestSucc(JSONObject object) {
						qiandingren.setText(object.optString("signPerson"));
						qiandingriqi.setText(object.optString("signDate"));
						cheliangpinpai.setText(object.optString("carBrand"));
						xinghao.setText(object.optString("model"));
						chepaihao.setText(object.optString("carNum"));
						bangdingshoujihao.setText(object.optString("bandTel"));
						meiyuezujin.setText(object.optString("perMonMoney"));
						//yajinjiner.setText(object.optString("depositMoney"));
						meiyuejiaozuri.setText(object.optString("perPayDay"));
						yijiaozujin.setText(object.optString("dealDeposit"));
						//shagnqijiaozuri.setText(object.optString("lastPayDate"));
						zhuangtai.setText(object.optString("status"));
						didizhuangche.setText(object.optString("didi"));
						youbuzhuangche.setText(object.optString("yobu"));
						//yihaozhuangche.setText(object.optString("yiho"));
						//qitapingtai.setText(object.optString("otherPlatform"));
						sijizhuangtai.setText(object.optString("specCarState"));
						beizhu.setText(object.optString("remark"));
					}
				}), "");
		
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		
	}

}
