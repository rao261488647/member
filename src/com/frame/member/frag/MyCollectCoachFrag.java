package com.frame.member.frag;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.MyCollectCoachParser;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.BaseActivity;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.adapters.MyCollectCoachAdapter;
import com.frame.member.bean.MyCollectBean.CollectCoach;
import com.frame.member.bean.MyCollectBean.MyCollectCoachResult;
import com.frame.member.widget.refreshlistview.PullToRefreshBase;
import com.frame.member.widget.refreshlistview.PullToRefreshBase.Mode;
import com.frame.member.widget.refreshlistview.PullToRefreshScrollView;
import com.frame.member.widget.swipemenulistview.SwipeMenu;
import com.frame.member.widget.swipemenulistview.SwipeMenuCreator;
import com.frame.member.widget.swipemenulistview.SwipeMenuItem;
import com.frame.member.widget.swipemenulistview.SwipeMenuListView;

/**
 * 收藏 -教练
 * @author Ron
 * @date 2016-8-18  下午11:52:27
 */
public class MyCollectCoachFrag extends BaseFrag implements OnClickListener {

	private MyCollectCoachAdapter adapter;
	private List<CollectCoach> dataList = new ArrayList<CollectCoach>();
	private SwipeMenuListView coachListView;
	private PullToRefreshScrollView pullListView;
	public static MyCollectCoachFrag newInstance(String title) {

		MyCollectCoachFrag fragment = new MyCollectCoachFrag();
		Bundle bundle = new Bundle();
		bundle.putString(AppConstants.FRAG_TITLE_KEY, title);
		fragment.setArguments(bundle);
        return fragment;
    }
	
	/**
	 * 页面创建
	 * @author Ron
	 * @date 2016-8-19  下午11:25:42
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.frag_my_collect_coach, container,
				false);
		
		initView();
		initOnclick();
		initProgress();
		coachListViewInit();
		
		return rootView;
	}
	private void initView(){
		pullListView = (PullToRefreshScrollView)findViewById(R.id.my_collect_coach_sv);
		coachListView = (SwipeMenuListView) findViewById(R.id.my_collect_coach_listView);
	}
	/**
	 * 点击监听事件绑定
	 * @author Ron
	 * @date 2016-8-19  下午11:21:34
	 */
	private void initOnclick(){
	}
	/**
	 * 主逻辑代码
	 * @author Ron
	 * @date 2016-8-19  下午11:21:49
	 */
	private void initProgress(){
		pullListView.setMode(Mode.BOTH);
		pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				page = 1;
				pullListView.setMode(Mode.BOTH);
				getData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				page ++;
				getData();
			}
		});
		getData();
	}
	int page;
	//请求获取服务端数据
	private void getData(){
		if(page == 0)
			page = 1;
		BaseParser parser = new MyCollectCoachParser();
		HttpRequest request = new HttpRequestImpl(getActivity(), 
				AppConstants.APP_SORT_STUDENT +"/mycollect", parser);
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(getActivity(), SPUtils.KEY_MEMBERUSERID, "")); //用户id 
		request.addParam("token", (String) SPUtils.getAppSpUtil().get(getActivity(), SPUtils.KEY_TOKEN, ""));
		request.addParam("memberUserId","89"); //写死
		request.addParam("page_size", "10").addParam("page_num", "" + page);
		((BaseActivity)getActivity()).getDataFromServer(request, false,callBack);
		
	}
	/**
	 * 网络请求回调事件
	 */
	private DataCallback<MyCollectCoachResult> callBack = new DataCallback<MyCollectCoachResult>() {

		@Override
		public void processData(MyCollectCoachResult object, RequestResult result) {
			pullListView.onRefreshComplete();
			if(null != object){
				if("200".equals(object.code)){
					if(page == 1){
						dataList.clear();
					}
					if(object.collectCoachList != null && object.collectCoachList.size() > 0){
						dataList.addAll(object.collectCoachList);
						notifyAdapter();
						setListViewHeight(coachListView);
					}else{
						showToast("没有更多数据！");
					}
				}else{
					showToast("服务器正忙，请稍后尝试！");
				}
			}
		}
		
	};
	
	/**
	 * 通知适配器展示数据
	 * @author Ron
	 * @date 2016-8-20  上午12:22:37
	 */
	private void notifyAdapter() {
		if(adapter == null){
			adapter = new MyCollectCoachAdapter(getActivity(),dataList );
			coachListView.setAdapter(adapter);
		}else{
			adapter.notifyDataSetChanged();
		}
	}
	/**
	 * 初始化listview 带滑动按钮的
	 * @author Ron
	 * @date 2016-10-11  下午11:11:27
	 */
	private void coachListViewInit(){
		 // step 1. create a MenuCreator
       SwipeMenuCreator creator = new SwipeMenuCreator() {

           @Override
           public void create(SwipeMenu menu) {
               // create "open" item
//               SwipeMenuItem openItem = new SwipeMenuItem(
//                       getApplicationContext());
//               // set item background
//               openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
//                       0xCE)));
//               // set item width
//               openItem.setWidth(dp2px(90));
//               // set item title
//               openItem.setTitle("Open");
//               // set item title fontsize
//               openItem.setTitleSize(18);
//               // set item title font color
//               openItem.setTitleColor(Color.WHITE);
//               // add to menu
//               menu.addMenuItem(openItem);

               // create "delete" item
               SwipeMenuItem deleteItem = new SwipeMenuItem(
                       getActivity());
               // set item background
               deleteItem.setBackground(R.color.my_collect_delete_yellow);
               // set item width
               deleteItem.setWidth(dp2px(90));
               // set a icon
               deleteItem.setIcon(R.drawable.icon_my_collect_delete);
               // add to menu
               menu.addMenuItem(deleteItem);
           }
       };
		// set creator
       coachListView.setMenuCreator(creator);
       

       // step 2. listener item click event
       coachListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
               switch (index) {
                   case 0:
                   	// delete
//					delete(item);
                   	dataList.remove(position);
                   	adapter.notifyDataSetChanged();
                       break;
                   case 1:
                   	// open
                   	//open(item);
                       break;
               }
               return false;
           }
       });

       // set SwipeListener
       coachListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

           @Override
           public void onSwipeStart(int position) {
               // swipe start
           }

           @Override
           public void onSwipeEnd(int position) {
               // swipe end
           }
       });

       // set MenuStateChangeListener
       coachListView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
           @Override
           public void onMenuOpen(int position) {
           }

           @Override
           public void onMenuClose(int position) {
           }
       });

       // test item long click
       coachListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view,
                                          int position, long id) {
               showToast(position + " long click");
               return false;
           }
       });
	}
	
	/**
	 * 重新计算ListView的高度，解决ScrollView和ListView两个View都有滚动的效果，在嵌套使用时起冲突的问题
	 * @param listView
	 */
	public void setListViewHeight(ListView listView) {  
		  
	    // 获取ListView对应的Adapter  
	  
	    ListAdapter listAdapter = listView.getAdapter();  
	  
	    if (listAdapter == null) {  
	        return;  
	    }  
	    int totalHeight = 0;  
	    for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目  
	        View listItem = listAdapter.getView(i, null, listView);  
	        listItem.measure(0, 0); // 计算子项View 的宽高  
	        totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度  
	    }  
	  
	    ViewGroup.LayoutParams params = listView.getLayoutParams();  
	    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));  
	    listView.setLayoutParams(params);  
	}  
	
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
	/**
	 *点击事件判断 
	 * @author Ron
	 * @date 2016-8-19  下午11:24:54
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
