package com.frame.member.frag;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.MainCourseParser;
import com.frame.member.Parsers.MainNotifyPaser;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.ImageHandler;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.adapters.CondensationPagerAdapter;
import com.frame.member.adapters.MainCourseNewsAdapter;
import com.frame.member.bean.MainCourseBean.MainCourseBanner;
import com.frame.member.bean.MainCourseBean.MainCourseResult;
import com.frame.member.bean.MainNotifyBean;
import com.frame.member.bean.MainNotifyBean.Notify;
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
	private PullToRefreshScrollView sv_conden_list_body;
	public List<MainCourseBanner> mainBannerData = new ArrayList<MainCourseBanner>();
	CondensationPagerAdapter pagerAdapter;
	LinearLayout ll_sort_conden_sport, ll_sort_conden_hotTopic,
			ll_sort_conden_classicAction;
	private MainCourseNewsAdapter sAdapter = null;
	protected TextView tv_title_left;
	private int oldPosition = 0;// 记录上一次点的位置
	private ArrayList<View> dots;
	private ListView main_course_lv;
	public ViewPager vp_condensation;
	public ImageHandler handler = new ImageHandler(new WeakReference<BaseFrag>(
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
		sv_conden_list_body = (PullToRefreshScrollView) findViewById(R.id.main_course_list_body);
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
		//从服务器获取数据
		getMainCourseData();
		
		return rootView;
	}
	/**
	 * 初始化控件
	 * @author Ron
	 * @date 2016-8-3  上午1:02:25
	 */
	private void findViewByIds() {
		main_course_lv = (ListView)findViewById(R.id.main_course_lv);
	}

	
	/**
	 * 获取首页教程列表
	 * @author Ron
	 * @date 2016-7-7  下午10:31:53
	 */
	BaseParser<MainCourseResult> parser = new MainCourseParser();
	private void getMainCourseData() {
		String url = AppConstants.APP_SORT_STUDENT+"/indexcourse";
		HttpRequestImpl request = new HttpRequestImpl(getActivity(),
				url, parser,HttpRequest.RequestMethod.post);
		request.addParam("token", (String) SPUtils.getAppSpUtil().get(getActivity(), SPUtils.KEY_TOKEN, ""));
		mContext.getDataFromServer(request, callback);
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
						ImageHandler.MSG_PAGE_CHANGED, position, 0));
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				switch (arg0) {
				case ViewPager.SCROLL_STATE_DRAGGING:
					handler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
					break;
				case ViewPager.SCROLL_STATE_IDLE:
					handler.sendEmptyMessageDelayed(
							ImageHandler.MSG_UPDATE_IMAGE,
							ImageHandler.MSG_DELAY);
					break;
				default:
					break;
				}
			}
		});
		vp_condensation.setCurrentItem(Integer.MAX_VALUE / 2);// 默认在中间，使用户看不到边界
		// 开始轮播效果
		handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE,
				ImageHandler.MSG_DELAY);
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

	/**
	 * 回调方法
	 */
	private DataCallback<MainCourseResult> callback = new DataCallback<MainCourseResult>() {

		@Override
		public void processData(final MainCourseResult object, RequestResult result) {
			if(result == RequestResult.Success){
				if(object != null){
					if("200".equals(object.code)){
						mainBannerData.addAll(object.mainBannerData);
						
						for (int i = 0; i < object.mainBannerData.size(); i++) {
							ImageView imageView = condensation_pager_list
									.get(i);
							if(object.mainBannerData.size()>0){
								String url = object.mainBannerData.get(i).bannerPhoto;
								TTApplication.getInstance()
										.disPlayImageDef(url, imageView);
							}
						}
						//加载课程信息到页面
						sAdapter = new MainCourseNewsAdapter(getActivity(), object.mainNewsData);
						main_course_lv.setAdapter(sAdapter);
						setListViewHeight(main_course_lv);
					
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
}
