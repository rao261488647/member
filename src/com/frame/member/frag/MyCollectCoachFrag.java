package com.frame.member.frag;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
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
import com.frame.member.widget.refreshlistview.PullToRefreshListView;

/**
 * 收藏 -教练
 * @author Ron
 * @date 2016-8-18  下午11:52:27
 */
public class MyCollectCoachFrag extends BaseFrag implements OnClickListener {

	private MyCollectCoachAdapter adapter;
	private PullToRefreshListView pullListView;
	private List<CollectCoach> dataList = new ArrayList<CollectCoach>();
	public int MID;
	  
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
		
		return rootView;
	}
	private void initView(){
		pullListView = (PullToRefreshListView)findViewById(R.id.my_collect_coach_listView);
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
		ListView v = new ListView(getActivity());
		v.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				return false;
			}
		});
		pullListView.setMode(Mode.BOTH);
		//长按事件
		pullListView.getRefreshableView().setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.e("setOnLongClickListener", "longclick");
				return false;
			}
		});
//		pullListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////				startActivity(new Intent(getActivity(),ClassDetailActivity.class));
//				showToast("点击！！！");
//			}
//		});
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
		
		itemOnLongClick1();
		
		getData();
	}
	/**
	 * 创建删除菜单
	 * @author Ron
	 * @date 2016-10-13  下午10:32:09
	 */
	private void itemOnLongClick1() {
		//注：setOnCreateContextMenuListener是与下面onContextItemSelected配套使用的
		pullListView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
  
	        public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
            	menu.add(0,0,0,"删除");
            }
        });
	}
	// 长按菜单响应函数
	@Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                        .getMenuInfo();
        switch(item.getItemId()) {
        case 0:
        		showToast("item--"+info.position);
                break;
        default:
                break;
        }
        return super.onContextItemSelected(item);
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
//						CommonUtil.setListViewHeight(coachListView);
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
			// 设置适配器
			pullListView.setAdapter(adapter);
		}else{
			adapter.notifyDataSetChanged();
		}
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
