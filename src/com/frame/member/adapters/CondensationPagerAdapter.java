package com.frame.member.adapters;

import java.util.List;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

public class CondensationPagerAdapter extends PagerAdapter {

	private List<ImageView> mListViews;

	public CondensationPagerAdapter(List<ImageView> mListViews) {
		this.mListViews = mListViews;// 构造方法，参数是我们的页卡，这样比较方便。
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
//		container.removeView(mListViews.get(position));// 删除页卡
//		((ViewPager) container).removeView((View)object);
		
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) { // 这个方法用来实例化页卡
		//对ViewPager页号求模取出View列表中要显示的项
        position %= mListViews.size();
        if (position<0){
            position = mListViews.size()+position;
        }
        ImageView view = mListViews.get(position);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp =view.getParent();
        if (vp!=null){
            ViewGroup parent = (ViewGroup)vp;
            parent.removeView(view);
        }
		container.addView(view, 0);// 添加页卡
		return view;
	}

	@Override
	public int getCount() {
        return mListViews.size(); 
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;// 是否是同一张图片
	}
	
	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

}
