package com.frame.member.frag;

import org.json.JSONObject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Utils.CommCallBack;
import com.frame.member.Utils.CustomDialog;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.LoginActivity;
import com.frame.member.activity.MyBillActivity;
import com.frame.member.activity.MyInfoActivity;
import com.frame.member.activity.SettingsActivity;

/**
 * 
 * 个人中心
 * 
 * @author Ron
 * 
 * 
 */
public class MyCenterFrag extends BaseFrag implements OnClickListener {


	private ImageView settingView;
	private TextView myInfoBtn; //编辑个人信息
	private RelativeLayout my_relative_6;//消费流水
	private boolean flag=true;

	public static MyCenterFrag newInstance(String title) {
		MyCenterFrag fragment = new MyCenterFrag();
		Bundle bundle = new Bundle();
		bundle.putString(AppConstants.FRAG_TITLE_KEY, title);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.frag_my, container, false);

		tv_title = (TextView) findViewById(R.id.my_title);
		tv_title.setText("我的");

		findViewByIds();
		setOnClickListener();
		return rootView;
	}

	private void getContractInfoFromServer() {
		HttpRequest request_reg = new HttpRequestImpl(getActivity(),
				AppConstants.CONTRACTINFO, null);

		request_reg.addParam("tel",
								(String) SPUtils.getAppSpUtil().get(
										getActivity(),
										SPUtils.KEY_PHONENUM, ""));

		mContext.getDataFromServer(request_reg, new CommCallBack(getActivity(),
				"", new CommCallBack.CallBackSucc() {

					@Override
					public void onRequestSucc(JSONObject object) {
						flag=false;
					}
					
				}), "");
	}
	/**
	 * 初始化控件
	 * @author Ron
	 * @date 2016-8-3  上午1:02:25
	 */
	private void findViewByIds() {
		settingView = (ImageView)findViewById(R.id.my_settings);
		myInfoBtn = (TextView)findViewById(R.id.my_text2);
		my_relative_6 = (RelativeLayout)findViewById(R.id.my_relative_6);
	}
	/**
	 * 点击监听事件设置
	 * @author Ron
	 * @date 2016-8-11  上午12:56:56
	 */
	private void setOnClickListener(){
		settingView.setOnClickListener(this);
		myInfoBtn.setOnClickListener(this);
		my_relative_6.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.my_settings://设置
			intent = new Intent(getActivity(), SettingsActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.my_text2: //个人基本信息
			intent = new Intent(getActivity(), MyInfoActivity.class);
			this.startActivity(intent);
			break;
		case R.id.my_relative_6: //消费流水
			intent = new Intent(getActivity(), MyBillActivity.class);
			this.startActivity(intent);
			break;
		case R.id.rl_my_logout:
			CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
			builder.setTitle("提示");
			builder.setMessage("确定要退出？");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent myLogout = new Intent(getActivity(), LoginActivity.class);
					getActivity().startActivity(myLogout);
					
					dialog.dismiss();
					getActivity().finish();
				}
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			builder.create().show();
			
			break;
		}
	}
}
