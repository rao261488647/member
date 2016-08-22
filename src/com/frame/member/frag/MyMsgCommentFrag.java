package com.frame.member.frag;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.BookingClassParser;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.activity.BaseActivity;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.activity.ClassDetailActivity;
import com.frame.member.adapters.MyMsgCommentAdapter;
import com.frame.member.bean.BookingClassResult;
import com.frame.member.widget.refreshlistview.PullToRefreshBase;
import com.frame.member.widget.refreshlistview.PullToRefreshBase.Mode;
import com.frame.member.widget.refreshlistview.PullToRefreshListView;

/**
 * 消息通知--评论页面
 * @author Ron
 * @date 2016-8-18  下午11:52:27
 */
public class MyMsgCommentFrag extends BaseFrag implements OnClickListener {

	public static MyMsgCommentFrag newInstance(String title) {

		MyMsgCommentFrag fragment = new MyMsgCommentFrag();
		Bundle bundle = new Bundle();
		bundle.putString(AppConstants.FRAG_TITLE_KEY, title);
		fragment.setArguments(bundle);
        return fragment;
    }
	
	private MyMsgCommentAdapter adapter;
	private List<String> tempList;
	private PullToRefreshListView pullListView;
	/**
	 * 页面创建
	 * @author Ron
	 * @date 2016-8-19  下午11:25:42
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.frag_my_msg_comment, container,
				false);
		tempList = new ArrayList<String>();
		tempList.add(new String("1"));
		tempList.add(new String("2"));
		tempList.add(new String("3"));
		tempList.add(new String("4"));
		tempList.add(new String("5"));
		
		
		
		initView();
		initOnclick();
		initProgress();
		notifyAdapter();
		return rootView;
	}
	/**
	 * 初始化控件
	 * @author Ron
	 * @date 2016-8-20  上午12:24:35
	 */
	private void initView(){
		pullListView = (PullToRefreshListView)findViewById(R.id.my_msg_comment_lv);
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
		pullListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startActivity(new Intent(getActivity(),ClassDetailActivity.class));
			}
		});
		pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				page = 1;
//				pullListView.setMode(Mode.BOTH);
//				getData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				page ++;
//				getData();
			}
		});
		//getData();
	}
	int page;
	private List<BookingClassResult> list_result = new ArrayList<BookingClassResult>();
	//请求获取服务端数据
	private void getData(){
		if(page == 0)
			page = 1;
		BaseParser<List<BookingClassResult>> parser = new BookingClassParser();
		HttpRequest request = new HttpRequestImpl(getActivity(), 
				AppConstants.APP_SORT_STUDENT +"/skiingclass", parser);
		request.addParam("page_size", "10")
				.addParam("page_num", ""+page);
		((BaseActivity)getActivity()).getDataFromServer(request, false,callBack);
		
	}
	/**
	 * 网络请求回调事件
	 */
	private DataCallback<List<BookingClassResult>> callBack = new DataCallback<List<BookingClassResult>>() {

		@Override
		public void processData(List<BookingClassResult> object, RequestResult result) {
		}
		
	};
	/**
	 * 通知适配器展示数据
	 * @author Ron
	 * @date 2016-8-20  上午12:22:37
	 */
	private void notifyAdapter() {
		if(adapter == null){
			adapter = new MyMsgCommentAdapter(getActivity(),tempList );
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
