package com.frame.member.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;

public class PictureViewPager extends ViewPager {

	public PictureViewPager(Context context) {
		super(context);
	}
	
	@Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            // TODO Auto-generated method stub
		 int height = 0;
	        //下面遍历所有child的高度
	        for (int i = 0; i < getChildCount(); i++) {
	            View child = getChildAt(i);
	            child.measure(widthMeasureSpec,
	                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
	            int h = child.getMeasuredHeight();
	            if (h > height) //采用最大的view的高度。
	                height = h;
	        }
	 
	        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
	                MeasureSpec.EXACTLY);
	 
	        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }  

}
