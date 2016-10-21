package com.frame.member.frag;

import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.member.activity.BaseActivity;

public class BaseFrag extends Fragment {

	protected final String TAG_FRAGMENT = getClass().getSimpleName();
	/** 基础上下文 */
	protected BaseActivity mContext;
	protected Application mApplication;

	protected View rootView;

	protected ImageView iv_title_left, iv_title_right, iv_title_right2;
	protected TextView tv_title, tv_title_left, tv_title_right;
	
	protected boolean isViewCreated;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		isViewCreated = true;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = (BaseActivity) getActivity();
		mApplication = (Application) mContext.getApplicationContext();
	}

	protected View findViewById(int id) {
		if (rootView == null)
			throw new NullPointerException("你的Fragment视图没有初始化");
		return rootView.findViewById(id);
	} 

	protected void showToast(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
	}

}
