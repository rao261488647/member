package com.frame.member.widget.refreshlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;

public class MyParallaxRefreshListView extends PullToRefreshListView {
	
	public interface ScrollListener{
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount);
	}
	
	public ScrollListener listener;
	
	public MyParallaxRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyParallaxRefreshListView(Context context) {
		super(context);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		if(listener!=null)
		{
			listener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}

}
