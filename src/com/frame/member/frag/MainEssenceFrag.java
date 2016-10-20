package com.frame.member.frag;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.CommonParser;
import com.frame.member.Parsers.MainEssenceParser;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.BaseActivity;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.adapters.MainEssenceAdapter;
import com.frame.member.bean.CommonBean;
import com.frame.member.bean.MainEssenceBean.EssenceInfo;
import com.frame.member.bean.MainEssenceBean.EssenceResult;
import com.frame.member.widget.refreshlistview.PullToRefreshBase;
import com.frame.member.widget.refreshlistview.PullToRefreshBase.Mode;
import com.frame.member.widget.refreshlistview.PullToRefreshListView;

/**
 * 首页-精华 frag
 * @author Ron
 * @date 2016-6-27  下午10:42:39
 */
public class MainEssenceFrag extends BaseFrag implements OnClickListener {
	
	// private PullToRefreshGridView mPullRefreshGridView;
	private PullToRefreshListView pullListView;
	private List<EssenceInfo> dataList = new ArrayList<EssenceInfo>();
	private MainEssenceAdapter adapter = null;
	LinearLayout ll_sort_conden_sport, ll_sort_conden_hotTopic,
			ll_sort_conden_classicAction;
	private int page;

	
	public static MainEssenceFrag newInstance(String title) {

		MainEssenceFrag fragment = new MainEssenceFrag();
		Bundle bundle = new Bundle();
		bundle.putString(AppConstants.FRAG_TITLE_KEY, title);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.frag_main_essence, container,
				false);
		initView();
		initOnclickListener();
		initProgress();
		return rootView;
	}
	/**
	 * 加载控件
	 */
	private void initView(){
		pullListView = (PullToRefreshListView)findViewById(R.id.main_essence_pull_sv);
	}
	/**
	 * 初始化单击事件
	 */
	private void initOnclickListener(){
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
	
	//请求获取服务端数据
	private void getData(){
		if(page == 0)
			page = 1;
		BaseParser parser = new MainEssenceParser();
		HttpRequest request = new HttpRequestImpl(getActivity(), 
				AppConstants.APP_SORT_STUDENT +"/indexbest", parser);
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(getActivity(), SPUtils.KEY_MEMBERUSERID, "")); //用户id 
		request.addParam("token", (String) SPUtils.getAppSpUtil().get(getActivity(), SPUtils.KEY_TOKEN, ""));
		request.addParam("memberUserId","89"); //写死
		request.addParam("page_size", "10").addParam("page_num", "" + page);
		((BaseActivity)getActivity()).getDataFromServer(request, false,callBack);
		
	}
	
	/**
	 * 网络请求回调事件
	 */
	private DataCallback<EssenceResult> callBack = new DataCallback<EssenceResult>() {

		@Override
		public void processData(EssenceResult object, RequestResult result) {
			pullListView.onRefreshComplete();
			if(null != object){
				if("200".equals(object.code)){
					if(page == 1){
						dataList.clear();
					}
					if(object.essenceInfoList != null && object.essenceInfoList.size() > 0){
						dataList.addAll(object.essenceInfoList);
						notifyAdapter();
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
			adapter = new MainEssenceAdapter(getActivity(),dataList );
			// 设置适配器
			pullListView.setAdapter(adapter);
		}else{
			adapter.notifyDataSetChanged();
		}
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_essence_guanzhu:
			break;

		default:
			break;
		}
	}
}
