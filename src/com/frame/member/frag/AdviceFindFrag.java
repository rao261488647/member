package com.frame.member.frag;


import java.util.ArrayList;
import java.util.List;

import com.frame.member.R;
import com.frame.member.adapters.AdviceFindAdapter;
import com.frame.member.adapters.CoachSearchAdapter.ImageAndText;
import com.frame.member.widget.refreshlistview.PullToRefreshListView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * 求教发现
 * 
 *
 */
public class AdviceFindFrag extends BaseFrag{
	
	private PullToRefreshListView lv_advice_find;
	private AdviceFindAdapter adapter;
	
	private static AdviceFindFrag mFrag;
	public static AdviceFindFrag newInstance() {
		if(mFrag == null)
			mFrag = new AdviceFindFrag();
        
        return mFrag;
    }
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.frag_advice_find, container,
				false);
		
		return rootView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
		
	}
	private void init(){
		lv_advice_find =(PullToRefreshListView) findViewById(R.id.lv_advice_find);
		List<ImageAndText> list = new ArrayList<ImageAndText>(); 
		list.add(new ImageAndText(R.drawable.coach_profile, "老李"));
		list.add(new ImageAndText(R.drawable.coach_profile, "老孙"));
		list.add(new ImageAndText(R.drawable.coach_profile, "老王"));
		adapter = new AdviceFindAdapter(mContext, list);
		lv_advice_find.setAdapter(adapter);
	}

	
	
	
	
	
	




}
