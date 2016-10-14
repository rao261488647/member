package com.frame.member.activity;
/**
 * 首页-视频列表-单视频类别列表
 * Ron
 */
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.VideoListParser;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.adapters.VideoListAdapter;
import com.frame.member.bean.VideoListBean.MainVideo;
import com.frame.member.bean.VideoListBean.VideoListResult;
import com.frame.member.widget.refreshlistview.PullToRefreshBase;
import com.frame.member.widget.refreshlistview.PullToRefreshBase.Mode;
import com.frame.member.widget.refreshlistview.PullToRefreshListView;

public class VideoListActivity extends BaseActivity {
	private PullToRefreshListView pullListView;
	private int page;
	private VideoListAdapter adapter;
	private String categoryId,title;
	private List<MainVideo> dataList = new ArrayList<MainVideo>();
	BaseParser<VideoListResult> parser = new VideoListParser();
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_my_video_list);
		Intent intent = getIntent();
		categoryId = intent.getStringExtra("categoryId");
		title = intent.getStringExtra("title");

		getData();
	}

	@Override
	protected void findViewById() {
		tv_title.setText(title);
		pullListView = (PullToRefreshListView) findViewById(R.id.my_video_list_lv);
	}
	/**
	 * 获取数据-消费流水
	 * @author Ron
	 * @date 2016-9-19  上午12:01:27
	 */
	private void getData() {
		if (page == 0)
			page = 1;
		String url = AppConstants.APP_SORT_STUDENT+"/teachvideolist";
		HttpRequestImpl request = new HttpRequestImpl(this,
				url, parser,HttpRequest.RequestMethod.post);
		request.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""));
//		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, "")); //用户id 
		request.addParam("page_size", "10").addParam("page_num", "" + page);
		request.addParam("categoryId", categoryId);
		getDataFromServer(request, callback1);
	}
	private DataCallback<VideoListResult> callback1 = new DataCallback<VideoListResult>() {

		@Override
		public void processData(VideoListResult object, RequestResult result) {
			pullListView.onRefreshComplete();
			if(result == RequestResult.Success){
				if(null != object){
					if("200".equals(object.code)){
						if(object.mainVideoList != null && object.mainVideoList.size() > 0){
							if (page == 1)
								dataList.clear();
							dataList.addAll(object.mainVideoList);
							notifyAdapter();
						}else{
							showToast("没有更多的数据！");
						}
					}else{
						showToast("服务器正忙，请稍后尝试！");
					}
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
			adapter = new VideoListAdapter(this,dataList );
			pullListView.setAdapter(adapter);
		}else{
			adapter.notifyDataSetChanged();
		}
	}
	
	private void initPullView(){
		pullListView.setMode(Mode.BOTH);
		pullListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				startActivity(new Intent(getActivity(),ClassDetailActivity.class));
			}
		});
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
	}
	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
		iv_title_back.setVisibility(0);
		initPullView();
	}
}
