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
public class MyCenterFrag extends BaseFrag implements OnClickListener {


	
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
