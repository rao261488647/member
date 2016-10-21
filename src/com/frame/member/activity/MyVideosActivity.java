package com.frame.member.activity;
import java.util.ArrayList;
import java.util.List;

/**
 *  关于
 *  @author hanshengkun
 */
import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.MyVideosParser;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.adapters.MyVideoAdapter;
import com.frame.member.bean.MyVideoResult;
import com.frame.member.widget.refreshlistview.PullToRefreshBase;
import com.frame.member.widget.refreshlistview.PullToRefreshBase.Mode;
import com.frame.member.widget.refreshlistview.PullToRefreshListView;

import android.graphics.Color;

public class MyVideosActivity extends BaseActivity {
	
	private PullToRefreshListView ptrl_my_videos;
	private MyVideoAdapter adapter;
	private int page;
	private List<MyVideoResult> list_find = new ArrayList<MyVideoResult>();
	
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_my_videos);
	}

	@Override
	protected void findViewById() {
		tv_title.setText("我的视频");
		tv_title.setTextColor(Color.WHITE);
	}
	
	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
		iv_title_back.setVisibility(0);
		
		ptrl_my_videos =(PullToRefreshListView) findViewById(R.id.ptrl_my_videos);
		
		ptrl_my_videos.setMode(Mode.BOTH);
		ptrl_my_videos.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				page = 1;
				ptrl_my_videos.setMode(Mode.BOTH);
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
	
	private void getData(){
		if(page < 1)
			page = 1;
		BaseParser<List<MyVideoResult>> parser = new MyVideosParser();
		HttpRequestImpl request = new HttpRequestImpl(
				this, AppConstants.APP_SORT_STUDENT + AppConstants.MYVIDEODYNAMIC, parser);
		request.addParam("memberUserId", 
				(String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""));
		getDataFromServer(request, callBack);
	}
	
	private DataCallback<List<MyVideoResult>> callBack = new DataCallback<List<MyVideoResult>>() {

		@Override
		public void processData(List<MyVideoResult> object, RequestResult result) {
			ptrl_my_videos.onRefreshComplete();
			if(object != null && object.size()> 0){
				if(page == 1){
					list_find.clear();
				}
				list_find.addAll(object);
				notifyAdapter();
			}else{
				if(page == 1){
					list_find.clear();
					notifyAdapter();
					return;
				}
				ptrl_my_videos.setMode(Mode.PULL_FROM_START);
			}
		}
	}; 
	
	private void notifyAdapter(){
		if(adapter == null){
			adapter = new MyVideoAdapter(this, list_find);
			ptrl_my_videos.setAdapter(adapter);
		}else{
			adapter.notifyDataSetChanged();
		}
	}
	
}
