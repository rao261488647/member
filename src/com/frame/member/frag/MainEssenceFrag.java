package com.frame.member.frag;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.MainNotifyPaser;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.ImageHandler;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.MainNotifyDetailActivity;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.adapters.CondensationPagerAdapter;
import com.frame.member.bean.MainNotifyBean;
import com.frame.member.bean.MainNotifyBean.Notify;
import com.frame.member.widget.refreshlistview.PullToRefreshBase;
import com.frame.member.widget.refreshlistview.PullToRefreshScrollView;
import com.frame.member.widget.refreshlistview.PullToRefreshBase.Mode;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

/**
 * 首页-视频 frag
 * @author Ron
 * @date 2016-6-27  下午10:42:39
 */
public class MainEssenceFrag extends BaseFrag {
	
	// private PullToRefreshGridView mPullRefreshGridView;
	private PullToRefreshScrollView sv_conden_list_body;

	LinearLayout ll_sort_conden_sport, ll_sort_conden_hotTopic,
			ll_sort_conden_classicAction;



	private LinearLayout ll_main_container;
	
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
		ll_main_container = (LinearLayout) findViewById(R.id.main_essence_container);


		sv_conden_list_body = (PullToRefreshScrollView) findViewById(R.id.main_essence_list_body);
		sv_conden_list_body.setMode(Mode.PULL_FROM_END);

		sv_conden_list_body
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase refreshView) {

					}

					@Override
					public void onPullUpToRefresh(PullToRefreshBase refreshView) {
						if (mainpage_data.size() < totalCount) {
							sv_conden_list_body.setRefreshing(true);
							getMainPageData();
						} else {
							Toast.makeText(mContext, "没有更多数据", 0).show();
							sv_conden_list_body.onRefreshComplete();
						}
					}
				});
		return rootView;
	}
	
	private List<ImageView> condensation_pager_list = new ArrayList<ImageView>();
	public List<Notify> mainpage_data = new ArrayList<MainNotifyBean.Notify>();

	private int pageCur = 0, totalCount;

	private void getMainPageData() {
		HttpRequest request_main_data = new HttpRequestImpl(getActivity(),
				AppConstants.GETANNOUNCEMENTLIST, new MainNotifyPaser());

		request_main_data
				.addParam(
						"cell",
						(String) SPUtils.getAppSpUtil().get(mContext,
								SPUtils.KEY_PHONENUM, ""))
				.addParam("currentPage", ++pageCur + "")
				.addParam("loadCount", 5 + "");

		mContext.getDataFromServer(request_main_data, pageCur == 1,
				new DataCallback<MainNotifyBean>() {

					@Override
					public void processData(MainNotifyBean object,
							RequestResult result) {
						if (sv_conden_list_body.isRefreshing())
							sv_conden_list_body.onRefreshComplete();
						if (result == RequestResult.Success) {

							if (pageCur > 1)
								Toast.makeText(mContext, "加载成功", 0).show();
							totalCount = object.totoalRecord;

							mainpage_data.addAll(object.mainpage_data);

							for (int i = 0; i < object.mainpage_data.size(); i++) {
								ll_main_container
										.addView(genNotifyLayout(object.mainpage_data
												.get(i)));
							}

							if (pageCur == 1) {
								for (int i = 0; i < 3; i++) {
									ImageView imageView = condensation_pager_list
											.get(i);
									
									if(object.mainpage_urls.size()>0){
										String url = object.mainpage_urls.get(i);
										TTApplication.getInstance()
												.disPlayImageDef(url, imageView);
									}
								}
							}
						}
					}
				}, "加载中...");
	}
	

	private View genNotifyLayout(final Notify notify) {
		View result = View.inflate(mContext, R.layout.item_main_notify, null);
		TextView tv_main_page_item_title = (TextView) result
				.findViewById(R.id.tv_main_page_item_title);
		TextView tv_main_page_item_content = (TextView) result
				.findViewById(R.id.tv_main_page_item_content);

		tv_main_page_item_title.setText(notify.name);
		tv_main_page_item_content.setText(notify.detail);
		
		result.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),MainNotifyDetailActivity.class);
				intent.putExtra(MainNotifyDetailActivity.TAG_INTENT_MAIN_NOTIFY_DATA, notify);
				getActivity().startActivity(intent);
			}
		});
		
		return result;
	}
	
}
