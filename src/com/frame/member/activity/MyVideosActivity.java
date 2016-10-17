package com.frame.member.activity;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *  关于
 *  @author hanshengkun
 */
import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.AdviceFindParser;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.Utils.HttpRequest.RequestMethod;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.adapters.AdviceFindAdapter;
import com.frame.member.bean.AdviceFindResult;
import com.frame.member.widget.refreshlistview.PullToRefreshBase;
import com.frame.member.widget.refreshlistview.PullToRefreshListView;
import com.frame.member.widget.refreshlistview.PullToRefreshBase.Mode;

import android.text.TextUtils;
import android.util.Log;

public class MyVideosActivity extends BaseActivity {
	
	private PullToRefreshListView ptrl_my_videos;
	private AdviceFindAdapter adapter;
	private int page;
	private List<AdviceFindResult> list_find = new ArrayList<AdviceFindResult>();
	
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_my_videos);
	}

	@Override
	protected void findViewById() {
		tv_title.setText("我的视频");
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
	}
	
	private void getData(){
		if(page < 1)
			page = 1;
		BaseParser<List<AdviceFindResult>> parser = new AdviceFindParser();
		HttpRequestImpl request = new HttpRequestImpl(
				this, AppConstants.APP_SORT_STUDENT + AppConstants.GETMVSINFO, parser);
		request.addParam("memberUserId", 
				(String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("page_size", 10+"")
				.addParam("page_num", page+"");
		getDataFromServer(request, callBack);
	}
	
	private DataCallback<List<AdviceFindResult>> callBack = new DataCallback<List<AdviceFindResult>>() {

		@Override
		public void processData(List<AdviceFindResult> object, RequestResult result) {
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
			adapter = new AdviceFindAdapter(this, list_find);
			ptrl_my_videos.setAdapter(adapter);
		}else{
			adapter.notifyDataSetChanged();
		}
	}
	
}
