package com.frame.member.frag;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.MainVideoParser;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.activity.VideoListActivity;
import com.frame.member.adapters.MainVideoAdapter;
import com.frame.member.bean.MainVideoBean.MainVideoCategory;
import com.frame.member.bean.MainVideoBean.MainVideoResult;

/**
 * 首页-视频 frag
 * @author Ron
 * @date 2016-6-27  下午10:42:39
 */
public class MainVideoFrag extends BaseFrag {
	
	private MainVideoAdapter sAdapter = null;
	private List<MainVideoCategory> dataList = new ArrayList<MainVideoCategory>();
	private ListView main_video_lv;
	
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
		
		findViewByIds(); //初始化控件
		
		getMainVideoData(); 
		return rootView;
	}
	/**
	 * 获取首页视频列表
	 * @author Ron
	 * @date 2016-7-7  下午10:31:53
	 */
	BaseParser<MainVideoResult> parser = new MainVideoParser();
	private void getMainVideoData() {
		String url = AppConstants.APP_SORT_STUDENT+"/indexvideocategory";
		HttpRequestImpl request = new HttpRequestImpl(getActivity(),
				url, parser,HttpRequest.RequestMethod.post);
		request.addParam("token", (String) SPUtils.getAppSpUtil().get(getActivity(), SPUtils.KEY_TOKEN, ""));
		mContext.getDataFromServer(request, callback);
	}

	/**
	 * 回调方法
	 */
	private DataCallback<MainVideoResult> callback = new DataCallback<MainVideoResult>() {

		@Override
		public void processData(final MainVideoResult object, RequestResult result) {
			if(result == RequestResult.Success){
				if(object != null){
					if("200".equals(object.code)){
						dataList = object.mainVideoCategoryData;
						sAdapter = new MainVideoAdapter(getActivity(), dataList);
						main_video_lv.setAdapter(sAdapter);
						main_video_lv.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								Intent intent = new Intent(getActivity(), VideoListActivity.class);
							    intent.putExtra("categoryId", dataList.get(position).categoryId);
							    intent.putExtra("title", dataList.get(position).categoryName);
							    startActivity(intent);
							}
						});
						setListViewHeight(main_video_lv);
					
					}
				}
			}
		}
	};
	
	
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
	
	/**
	 * 初始化控件
	 * @author Ron
	 * @date 2016-8-4  上午12:28:26
	 */
	private void findViewByIds() {
		main_video_lv = (ListView) findViewById(R.id.main_video_lv);
	}
	
}
