package com.frame.member.adapters;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class MyCenterPagerAdapter extends FragmentPagerAdapter {
	 ArrayList<Fragment> list;  
	 Fragment currentFragment;
    public MyCenterPagerAdapter(FragmentManager fm,ArrayList<Fragment> list) {  
        super(fm);  
        this.list = list;  
          
    }  

	@Override
	public Fragment getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		currentFragment = (Fragment) object;
		super.setPrimaryItem(container, position, object);
	}
}
