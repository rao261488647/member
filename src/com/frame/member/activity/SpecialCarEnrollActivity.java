package com.frame.member.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Utils.CommCallBack;
import com.frame.member.Utils.CommonUtil;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.bean.ContractTerm;
import com.frame.member.bean.Plat;
import com.frame.member.bean.Regtime;
import com.frame.member.frag.AdviceFrag;
import com.frame.member.listener.AsyncHttpListener;

public class SpecialCarEnrollActivity extends BaseActivity implements OnDateSetListener  {

	private RelativeLayout rl_rent_name,rl_rent_tel,rl_rent_plat,rl_rent_conTrem,rl_rent_date,rl_rent_regTime;
	private TextView  tv_rent_name,tv_rent_tel,tv_rent_conTrem,rl_rent_money,tv_rent_date,tv_rent_plat,tv_rent_regTime,tv_rent_submit,tv_item_rent_car_name,tv_item_rent_car_deposit;
	private ImageView im_rent_car_list;
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_special_enroll);
		List<ContractTerm> conList = (List<ContractTerm>) getIntent().getSerializableExtra("contractTermList");
	}

	@Override
	protected void findViewById() {
		rl_rent_name = (RelativeLayout) findViewById(R.id.rl_rent_name);
		rl_rent_tel = (RelativeLayout) findViewById(R.id.rl_rent_tel);
		rl_rent_plat = (RelativeLayout) findViewById(R.id.rl_rent_plat);
		rl_rent_conTrem = (RelativeLayout) findViewById(R.id.rl_rent_conTrem);
		rl_rent_date = (RelativeLayout) findViewById(R.id.rl_rent_date);
		rl_rent_regTime = (RelativeLayout) findViewById(R.id.rl_rent_regTime);
		rl_rent_money = (TextView) findViewById(R.id.rl_rent_money);
		
		tv_rent_name = (TextView) findViewById(R.id.tv_rent_name);
		tv_rent_tel = (TextView) findViewById(R.id.tv_rent_tel);
		tv_rent_conTrem = (TextView) findViewById(R.id.tv_rent_conTrem);
		
		tv_rent_date = (TextView) findViewById(R.id.tv_rent_date);
		tv_rent_plat = (TextView) findViewById(R.id.tv_rent_plat);
		tv_rent_regTime = (TextView) findViewById(R.id.tv_rent_regTime);
		tv_rent_submit = (TextView) findViewById(R.id.tv_rent_submit);
		im_rent_car_list = (ImageView) findViewById(R.id.im_rent_car_list);
		
		TTApplication.getInstance()
		.disPlayImageDef(getIntent().getStringExtra("url"), im_rent_car_list);
		
		//	车型
		tv_item_rent_car_name = (TextView) findViewById(R.id.tv_item_rent_car_name);
		tv_item_rent_car_name.setText(getIntent().getStringExtra("carType"));
		//	押金
		tv_item_rent_car_deposit = (TextView) findViewById(R.id.tv_item_rent_car_deposit);
		tv_item_rent_car_deposit.setText(getIntent().getStringExtra("deposit"));
	}

	@Override
	protected void setListener() {
		
		final Calendar calendar = Calendar.getInstance();
//       final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance((OnDateSetListener) this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), isVibrate());
		
		
		rl_rent_name.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				final EditText name = new EditText(SpecialCarEnrollActivity.this);
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					public void run() {
						InputMethodManager inputManager = (InputMethodManager) name
								.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
						inputManager.showSoftInput(name, 0);
					}
				}, 200);
				
				new AlertDialog.Builder(SpecialCarEnrollActivity.this).setView(name).setTitle("请输入姓名")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if(!"".equals(name.getText().toString())){
							if(name.getText().toString().length() >= 15){
								Toast.makeText(SpecialCarEnrollActivity.this, "姓名过长，请重新输入!", Toast.LENGTH_SHORT).show();
								tv_rent_name.setText("");
							} else {
								tv_rent_name.setText(name.getText().toString());
							}
						}				
					}
				}).setNegativeButton("取消", null).show();
			}
		});
		rl_rent_tel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				final EditText phone = new EditText(SpecialCarEnrollActivity.this);
				phone.setInputType(InputType.TYPE_CLASS_PHONE);
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					public void run() {
						InputMethodManager inputManager = (InputMethodManager) phone
								.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
						inputManager.showSoftInput(phone, 0);
					}
				}, 200);
				
				new AlertDialog.Builder(SpecialCarEnrollActivity.this).setView(phone).setTitle("请输入手机号")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if(!"".equals(phone.getText().toString())){
							if(phone.getText().toString().length() > 11){
								Toast.makeText(SpecialCarEnrollActivity.this, "手机号不识别,请认真填写!", Toast.LENGTH_SHORT).show();
								tv_rent_tel.setText("");
							} else {
								tv_rent_tel.setText(phone.getText().toString());
							}
						}				
					}
				}).setNegativeButton("取消", null).show();
				
			}
		});
		
		rl_rent_date.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
//				datePickerDialog.setVibrate(isVibrate());
//                datePickerDialog.setYearRange(2007, 2016);
//                datePickerDialog.setCloseOnSingleTapDay(isCloseOnSingleTapDay());
//                datePickerDialog.show(getSupportFragmentManager(), "Sdatepicker");	
			}
		});
		
		
//		DatePickerDialog dpd = (DatePickerDialog) getSupportFragmentManager().findFragmentByTag("Sdatepicker");
//        if (dpd != null) {
//            dpd.setOnDateSetListener(this);
//        }
        
        // 预约提交
        tv_rent_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String name = tv_rent_name.getText().toString().trim();
				String tel = tv_rent_tel.getText().toString().trim();
				String plat = tv_rent_plat.getText().toString().trim();
				String conTrem = tv_rent_conTrem.getText().toString().trim();
				String date = tv_rent_date.getText().toString().trim();
				String regTime = tv_rent_regTime.getText().toString().trim();
				String money = rl_rent_money.getText().toString().trim();

				if (TextUtils.isEmpty(name)) {
					showToast("姓名不能为空");
					return;
				}
				if (TextUtils.isEmpty(tel)) {
					showToast("电话不能为空");
					return;
				}
				if (TextUtils.isEmpty(plat)) {
					showToast("请选择适用平台");
					return;
				}
				if (TextUtils.isEmpty(conTrem)) {
					showToast("请选择合同周期");
					return;
				}
				if (TextUtils.isEmpty(date)) {
					showToast("请选择预定上门签约日期");
					return;
				}
//				if (TextUtils.isEmpty(regTime)) {
//					showToast("请选择到场注册时间");
//					return;
//				}
				
				
				HttpRequest request_reg = new HttpRequestImpl(SpecialCarEnrollActivity.this,
						AppConstants.SPECCARPERREGISTER, null);

				request_reg.addParam("cell", tel).addParam("loginTel", (String)SPUtils.getAppSpUtil().get(
						SpecialCarEnrollActivity.this, SPUtils.KEY_PHONENUM, ""))
						.addParam("name", name).addParam("platForMto", plat)
						.addParam("contractTerm", conTrem).addParam("perRegisterDate", date)
						.addParam("boardRegisterTime", regTime).addParam("payRentMonth", money).addParam("rentCarId", getIntent().getStringExtra("rentCarId"));

				getDataFromServer(request_reg, new CommCallBack(SpecialCarEnrollActivity.this,
						"预约成功", new CommCallBack.CallBackSucc() {

							@Override
							public void onRequestSucc(JSONObject object) {
								//提交注册信息
								//Intent intent = new Intent(SpecialCarEnrollActivity.this,RegisterFrag.class);
								//startActivity(intent);
								finish();
								sendBroadcast(new Intent(AppConstants.RENT_CAR_SUBMIT_SUCC));
							}
						}), "正在提交...");
				
			}
		});
        }

	@Override
	protected void processLogic() {
		iv_title_back.setVisibility(0);
	}
	
	
	//合同期
	public void contractTermChange(View v){
		List<ContractTerm> conList = (List<ContractTerm>) getIntent().getSerializableExtra("contractTermList");
		
		CommonUtil.postSelectDialog(conList,this,new AsyncHttpListener(){
			public void onSucess(Object obj) {
				ContractTerm con= getContractTerm();
				
				String[] str = con.getContractTermKey().split("\\(");
				tv_rent_conTrem.setText(str[0]);
				rl_rent_money.setText(con.getContractTermVal());
				
			}
			@Override
			public void onFailur(Throwable e) {
			}

			@Override
			public void onJSONException(Throwable e) {
			}	
		});
	}
	
	    //平台
		public void platChange(View v){
			//List<Plat> conList = (List<Plat>) getIntent().getSerializableExtra("contractTermList");
			String plat = getIntent().getStringExtra("plat");
			
			String[] plats = plat.split(",");
			List<Plat> platList = new ArrayList();
			for(int i=0;i<plats.length;i++){
				Plat platTemp= new Plat();
				platTemp.setName(plats[i]);
				platList.add(platTemp); 
			}
			CommonUtil.platSelectDialog(platList,this,new AsyncHttpListener(){
				public void onSucess(Object obj) {
					Plat con= getPlat();
					tv_rent_plat.setText(con.getName());
				}
				@Override
				public void onFailur(Throwable e) {
				}
				
				@Override
				public void onJSONException(Throwable e) {
				}	
			});
		}
	
		  //上门注册时间段
		public void regTimeChange(View v){
			//List<Plat> conList = (List<Plat>) getIntent().getSerializableExtra("contractTermList");
			
			//先造个数据
			List<Regtime> platList = new ArrayList();
			Regtime plat = new Regtime();
			  plat.setName("9:00-12:00");
			  platList.add(plat); 
			  Regtime pla1t = new Regtime();
			  pla1t.setName("13:00-18:00");
			  platList.add(pla1t); 
			CommonUtil.regTimeSelectDialog(platList,this,new AsyncHttpListener(){
				public void onSucess(Object obj) {
					Regtime con= getRegtime();
					tv_rent_regTime.setText(con.getName());
				}
				@Override
				public void onFailur(Throwable e) {
				}

				@Override
				public void onJSONException(Throwable e) {
				}	
			});
		}
		
//	    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
//	    	month = month + 1;
//	    	String date = year + "-" + month + "-" + day;
//	    	tv_rent_date.setText(date);
//	    }
		    
		private boolean isVibrate() {
			return true;
	    }
	
	 private boolean isCloseOnSingleTapDay() {
	    	return false;
	    }

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		
	}
}
