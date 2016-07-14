package com.frame.member.frag;

import org.json.JSONObject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Utils.CommCallBack;
import com.frame.member.Utils.CustomDialog;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.AboutActivity;
import com.frame.member.activity.AlterpwdActivity;
import com.frame.member.activity.ContractActivity;
import com.frame.member.activity.LoginActivity;
import com.frame.member.activity.PersonalListActivity;
import com.frame.member.activity.StatementsActivity;
import com.frame.member.activity.StatementsListActivity;

/**
 * 
 * 个人中心
 * 
 * @author hanshengkun
 * 
 */
public class PerCenterFrag extends BaseFrag implements OnClickListener {

	private RelativeLayout rl_my_account, rl_my_alterpwd, rl_my_info,
			rl_my_commit, rl_my_contract_info, rl_my_qa, rl_my_fb, rl_my_about,
			rl_get_statements,rl_my_logout;

	private TextView rl_my_account_val;
	
	private boolean flag=true;

	public static PerCenterFrag newInstance(String title) {
		PerCenterFrag fragment = new PerCenterFrag();
		Bundle bundle = new Bundle();
		bundle.putString(AppConstants.FRAG_TITLE_KEY, title);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.frag_my, container, false);

		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("个人中心");

		rl_my_account_val = (TextView) findViewById(R.id.rl_my_account_val);
		rl_my_account_val.setText((String) SPUtils.getAppSpUtil().get(mContext,
				SPUtils.KEY_PHONENUM, ""));

		rl_my_account = (RelativeLayout) findViewById(R.id.rl_my_account);
		rl_my_alterpwd = (RelativeLayout) findViewById(R.id.rl_my_alterpwd);
		rl_my_info = (RelativeLayout) findViewById(R.id.rl_my_info);
		rl_my_commit = (RelativeLayout) findViewById(R.id.rl_my_commit);
		rl_my_contract_info = (RelativeLayout) findViewById(R.id.rl_my_contract_info);
		// rl_my_qa = (RelativeLayout) findViewById(R.id.rl_my_qa);
		// rl_my_fb = (RelativeLayout) findViewById(R.id.rl_my_fb);
		rl_my_about = (RelativeLayout) findViewById(R.id.rl_my_about);

		getContractInfoFromServer();
		
		rl_get_statements = (RelativeLayout) findViewById(R.id.rl_get_statements);
		
		if(flag){
			rl_my_commit.setVisibility(View.GONE);
			rl_get_statements.setVisibility(View.GONE);
		}
		rl_my_logout = (RelativeLayout) findViewById(R.id.rl_my_logout);

		rl_my_alterpwd.setOnClickListener(this);
		rl_my_info.setOnClickListener(this);
		rl_my_commit.setOnClickListener(this);
		rl_my_contract_info.setOnClickListener(this);
		// rl_my_qa.setOnClickListener(this);
		// rl_my_fb.setOnClickListener(this);
		rl_my_about.setOnClickListener(this);
		rl_get_statements.setOnClickListener(this);
		
		rl_my_logout.setOnClickListener(this);

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
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_my_alterpwd:
			Intent alterpwd = new Intent(getActivity(), AlterpwdActivity.class);
			getActivity().startActivity(alterpwd);
			break;
		case R.id.rl_my_info:
			Intent my_info = new Intent(getActivity(),
					PersonalListActivity.class);
			getActivity().startActivity(my_info);
			break;
		case R.id.rl_my_commit:
			Intent intent = new Intent(getActivity(), StatementsActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.rl_get_statements:
			Intent getStatements = new Intent(getActivity(),
					StatementsListActivity.class);
			getActivity().startActivity(getStatements);
			break;
		case R.id.rl_my_contract_info:
			Intent getContract = new Intent(getActivity(), ContractActivity.class);
			getActivity().startActivity(getContract);
			break;
		case R.id.rl_my_about:
			Intent myAbout = new Intent(getActivity(), AboutActivity.class);
			getActivity().startActivity(myAbout);
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
