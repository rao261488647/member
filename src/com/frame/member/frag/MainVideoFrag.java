package com.frame.member.frag;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 首页-视频 frag
 * @author Ron
 * @date 2016-6-27  下午10:42:39
 */
public class MainVideoFrag extends BaseFrag {
	
	public static MainVideoFrag newInstance(String title) {

		MainVideoFrag fragment = new MainVideoFrag();
		Bundle bundle = new Bundle();
		bundle.putString(AppConstants.FRAG_TITLE_KEY, title);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.frag_main_video, container,
				false);
		return rootView;
	}
	
}
