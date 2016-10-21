package com.frame.member.frag;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.MainCourseParser;
import com.frame.member.Utils.CommonUtil;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.activity.NewsDetailActivity;
import com.frame.member.adapters.CondensationPagerAdapter;
import com.frame.member.adapters.MainCourseNewsAdapter;
import com.frame.member.bean.MainCourseBean.MainCourseBanner;
import com.frame.member.bean.MainCourseBean.MainCourseNews;
import com.frame.member.bean.MainCourseBean.MainCourseResult;
import com.frame.member.bean.MainNotifyBean;
import com.frame.member.bean.MainNotifyBean.Notify;
import com.frame.member.handler.MainCourseImageHandler;
import com.frame.member.widget.refreshlistview.PullToRefreshBase;
import com.frame.member.widget.refreshlistview.PullToRefreshBase.Mode;
import com.frame.member.widget.refreshlistview.PullToRefreshScrollView;

/**
 * 首页-教程 frag
 * @author Ron
 * @date 2016-6-27  下午10:42:39
 */
public class MainCourseFrag extends BaseFrag {
	
	
	// private PullToRefreshGridView mPullRefreshGridView;
	private PullToRefreshScrollView pullListView;
	private List<MainCourseNews> dataList = new ArrayList<MainCourseNews>();
	private ListView listView;
	public CondensationPagerAdapter pagerAdapter;
	LinearLayout ll_sort_conden_sport, ll_sort_conden_hotTopic,
			ll_sort_conden_classicAction;
	private MainCourseNewsAdapter adapter;
	protected TextView tv_title_left;
	private int oldPosition = 0;// 记录上一次点的位置
	private ArrayList<View> dots;
	public ViewPager vp_condensation;
	private int page;
	
	private List<ImageView> condensation_pager_list = new ArrayList<ImageView>();
	public List<Notify> mainpage_data = new ArrayList<MainNotifyBean.Notify>();

	public MainCourseImageHandler handler = new MainCourseImageHandler(new WeakReference<MainCourseFrag>(
			this));
	private LinearLayout ll_main_container ,main_course_container;

	
	public static MainCourseFrag newInstance(String title) {

		MainCourseFrag fragment = new MainCourseFrag();
		Bundle bundle = new Bundle();
		bundle.putString(AppConstants.FRAG_TITLE_KEY, title);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.frag_main_course, container,
				false);

		findViewByIds(); //控件初始化
		initPhotoCarousel(); //图片轮播初始化
		initPullView(); //初始化下拉view控件
		
		return rootView;
	}
	
	private void initPullView(){
		pullListView.setMode(Mode.BOTH);
		pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				page = 1;
				pullListView.setMode(Mode.BOTH);
				getMainCourseData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				page ++;
				getMainCourseData();
			}
		});
		getMainCourseData();
	}
	
	/**
	 * 初始化控件
	 * @author Ron
	 * @date 2016-8-3  上午1:02:25
	 */
	private void findViewByIds() {
		pullListView = (PullToRefreshScrollView) findViewById(R.id.main_course_sv);
		listView = (ListView) findViewById(R.id.main_course_lv);
	}

	/**
	 * 首页，资讯页面图片轮播初始化
	 * @author Ron
	 * @date 2016-7-8  上午12:27:42
	 */
	private void initPhotoCarousel(){
		
		// 显示的点
		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.main_course_dot_0));
		dots.add(findViewById(R.id.main_course_dot_1));
		dots.add(findViewById(R.id.main_course_dot_2));

		vp_condensation = (ViewPager) rootView
				.findViewById(R.id.main_course_condensation);

		main_course_container = (LinearLayout) findViewById(R.id.main_course_container);
		for (int i = 0; i < 3; i++) {
			ImageView iv = new ImageView(getActivity());
			iv.setScaleType(ScaleType.CENTER_CROP);
			switch (i) {
				case 0:
					iv.setImageResource(R.drawable.banner_member_main1);
					break;
				case 1:
					iv.setImageResource(R.drawable.banner_member_main2);
					break;
				case 2:
					iv.setImageResource(R.drawable.banner_member_main1);
					break;
			}

			condensation_pager_list.add(iv);
		}
		pagerAdapter = new CondensationPagerAdapter(condensation_pager_list);
		vp_condensation.setAdapter(pagerAdapter);
		vp_condensation.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				dots.get(oldPosition % 3).setBackgroundResource(
						R.drawable.dot_normal);
				dots.get(position % 3).setBackgroundResource(
						R.drawable.dot_focused);
				oldPosition = position;
				handler.sendMessage(Message.obtain(handler,
						MainCourseImageHandler.MSG_PAGE_CHANGED, position, 0));
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				switch (arg0) {
				case ViewPager.SCROLL_STATE_DRAGGING:
					handler.sendEmptyMessage(MainCourseImageHandler.MSG_KEEP_SILENT);
					break;
				case ViewPager.SCROLL_STATE_IDLE:
					handler.sendEmptyMessageDelayed(
							MainCourseImageHandler.MSG_UPDATE_IMAGE,
							MainCourseImageHandler.MSG_DELAY);
					break;
				default:
					break;
				}
			}
		});
//		vp_condensation.setCurrentItem(Integer.MAX_VALUE / 2);// 默认在中间，使用户看不到边界
		// 开始轮播效果
		handler.sendEmptyMessageDelayed(MainCourseImageHandler.MSG_UPDATE_IMAGE,
				MainCourseImageHandler.MSG_DELAY);
	}
	/**
	 * 获取首页教程列表
	 * @author Ron
	 * @date 2016-7-7  下午10:31:53
	 */
	BaseParser<MainCourseResult> parser = new MainCourseParser();
	private void getMainCourseData() {
		if(page == 0)
			page = 1;
		String url = AppConstants.APP_SORT_STUDENT+"/indexcourse";
		HttpRequestImpl request = new HttpRequestImpl(getActivity(),
				url, parser,HttpRequest.RequestMethod.post);
//		request.addParam("token", (String) SPUtils.getAppSpUtil().get(getActivity(), SPUtils.KEY_TOKEN, ""));
		request.addParam("page_size", "10").addParam("page_num", "" + page);
		mContext.getDataFromServer(request, callback);
	}
	
	/**
	 * 回调方法
	 */
	private DataCallback<MainCourseResult> callback = new DataCallback<MainCourseResult>() {

		@Override
		public void processData(final MainCourseResult object, RequestResult result) {
			pullListView.onRefreshComplete();
			if(result == RequestResult.Success){
				if(object != null){
					if("200".equals(object.code)){
						if(page == 1){
							dataList.clear();
						}
						if(object.mainNewsData != null && object.mainNewsData.size() > 0 ){
							dataList.addAll(object.mainNewsData);
							notifyAdapter();
							CommonUtil.setListViewHeight(listView);
							for (int i = 0; i < object.mainBannerData.size(); i++) {
								final MainCourseBanner banner= object.mainBannerData.get(i);
								ImageView imageView = condensation_pager_list
										.get(i);
								String url = banner.bannerPhoto;
								TTApplication.getInstance()
								.disPlayImageDef(url, imageView);
								imageView.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										Intent intent = new Intent(getActivity(),NewsDetailActivity.class);
										intent.putExtra("title", "教程详情");
										intent.putExtra("newsUrl", banner.bannerLink);
										startActivity(intent);
									}
								});
							}
						}else{
							showToast("没有更多数据！");
						}
					
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
			adapter = new MainCourseNewsAdapter(getActivity(),dataList );
			//单击列表项事件
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Intent intent = new Intent(getActivity(),NewsDetailActivity.class);
					intent.putExtra("title", "教程详情");
					intent.putExtra("newsUrl", dataList.get(position).infoIdUrl);
					startActivity(intent);
				}
			});
			// 设置适配器
			listView.setAdapter(adapter);
		}else{
			adapter.notifyDataSetChanged();
		}
	}
	
}
