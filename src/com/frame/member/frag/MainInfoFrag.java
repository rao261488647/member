package com.frame.member.frag;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.MainInfoParser;
import com.frame.member.Utils.CommonUtil;
import com.frame.member.Utils.DateUtil;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.ImageHandler;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.adapters.CondensationPagerAdapter;
import com.frame.member.adapters.MainNewsAdapter;
import com.frame.member.bean.MainInfoBean.MainBanner;
import com.frame.member.bean.MainInfoBean.MainInfoResult;
import com.frame.member.bean.MainInfoBean.MainNews;
import com.frame.member.bean.MainInfoBean.MainRemmendClass;
import com.frame.member.widget.refreshlistview.PullToRefreshBase;
import com.frame.member.widget.refreshlistview.PullToRefreshBase.Mode;
import com.frame.member.widget.refreshlistview.PullToRefreshScrollView;

/**
 * 
 * 首页
 * 
 * @author long
 * 
 */

public class MainInfoFrag extends BaseFrag implements OnClickListener {

//	private PullToRefreshListView pullListView;
	private ListView listView;
	private PullToRefreshScrollView pullListView;

	public CondensationPagerAdapter pagerAdapter;
	LinearLayout ll_sort_conden_sport, ll_sort_conden_hotTopic,
			ll_sort_conden_classicAction;
	private int oldPosition = 0;// 记录上一次点的位置
	private ArrayList<View> dots;
	private List<MainNews> tempList;

	private List<ImageView> condensation_pager_list = new ArrayList<ImageView>();
	public List<MainBanner> mainBannerDate = new ArrayList<MainBanner>();
	private int page;
	
	public ViewPager vp_condensation;
	public ImageHandler handler = new ImageHandler(new WeakReference<MainInfoFrag>(
			this));

	private LinearLayout main_info_container,main_linear_container;

	private MainNewsAdapter adapter;
	
	private TextView main_class_t1,main_class_t2,main_class_t3,main_class_tt1,main_class_tt2,main_class_tt3;
	
	
	public static MainInfoFrag newInstance(String title) {

		MainInfoFrag fragment = new MainInfoFrag();
		Bundle bundle = new Bundle();
		bundle.putString(AppConstants.FRAG_TITLE_KEY, title);
		fragment.setArguments(bundle);
		return fragment;
	}

	/**
	 * 创建方法
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.frag_main_info, container,
				false);
		findViewByIds(); //获取控件
		initPhotoCarousel(); //初始化图片轮播控件
		//将页面定位到头部
		main_linear_container.setFocusable(true);
		main_linear_container.setFocusableInTouchMode(true);
		main_linear_container.requestFocus();

		
		pullListView.setMode(Mode.BOTH);
//		pullListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				startActivity(new Intent(getActivity(),ClassDetailActivity.class));
//			}
//		});
		pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				page = 1;
				pullListView.setMode(Mode.BOTH);
//				getMainCourseData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				page ++;
//				getMainCourseData();
			}
		});
		
		getMainInfoData();

		return rootView;
	}

	/**
	 * 获取首页资讯列表
	 * @author Ron
	 * @date 2016-7-7  下午10:31:53
	 */
	BaseParser<MainInfoResult> parser = new MainInfoParser();
	private void getMainInfoData() {
		String url = AppConstants.APP_SORT_STUDENT+"/indexinformation";
		HttpRequestImpl request = new HttpRequestImpl(getActivity(),
				url, parser,HttpRequest.RequestMethod.post);
		request.addParam("token", (String) SPUtils.getAppSpUtil().get(getActivity(), SPUtils.KEY_TOKEN, ""));
		mContext.getDataFromServer(request, callback);
	}

	/**
	 * 回调方法
	 */
	private DataCallback<MainInfoResult> callback = new DataCallback<MainInfoResult>() {

		@Override
		public void processData(final MainInfoResult object, RequestResult result) {
			if(result == RequestResult.Success){
				if(object != null){
					if("200".equals(object.code)){
						mainBannerDate.addAll(object.mainBannerData);
						
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
						try {
							initCourseClass(object.mainRemmendClassData);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						tempList = object.mainNewsData;
						notifyAdapter(); //适配器设置
						CommonUtil.setListViewHeight(listView);
					}
				}
			}
		}
	};

	/**
	 * 加载课程信息
	 * @author Ron
	 * @date 2016-7-9  下午10:13:41
	 * @param mainRemmendClassList
	 * @throws ParseException 
	 */
	private void initCourseClass(List<MainRemmendClass> mainRemmendClassList) throws ParseException{
		if(mainRemmendClassList != null && mainRemmendClassList.size() >0){
			for(int i=0;i<mainRemmendClassList.size();i++){
				switch (i) {
				case 0:
					MainRemmendClass c = mainRemmendClassList.get(i);
					String cTime = DateUtil.getDateTime(DateUtil.DATE_FORMAT_1, DateUtil.convertStringToDate(DateUtil.DATE_FORMAT, c.beginTime))
							+"~"+DateUtil.getDateTime(DateUtil.DATE_FORMAT_1, DateUtil.convertStringToDate(DateUtil.DATE_FORMAT, c.overTime));
					main_class_t1.setText(c.courseName);
					main_class_t2.setText(cTime);
					main_class_t3.setText(c.skifieldAddr);
					break;
				case 1:
					c = mainRemmendClassList.get(i);
					cTime = DateUtil.getDateTime(DateUtil.DATE_FORMAT_1, DateUtil.convertStringToDate(DateUtil.DATE_FORMAT, c.beginTime))
							+"~"+DateUtil.getDateTime(DateUtil.DATE_FORMAT_1, DateUtil.convertStringToDate(DateUtil.DATE_FORMAT, c.overTime));
					main_class_tt1.setText(c.courseName);
					main_class_tt2.setText(cTime);
					main_class_tt3.setText(c.skifieldAddr);
					break;

				default:
					break;
				}
			}
		}
	}
	
	/**
	 * 首页，资讯页面图片轮播初始化
	 * @author Ron
	 * @date 2016-7-8  上午12:27:42
	 */
	private void initPhotoCarousel(){
		
		// 显示的点
		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.main_dot_0));
		dots.add(findViewById(R.id.main_dot_1));
		dots.add(findViewById(R.id.main_dot_2));

		vp_condensation = (ViewPager) rootView
				.findViewById(R.id.main_vp_condensation);

		main_info_container = (LinearLayout) findViewById(R.id.main_info_container);
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
//					handler.sendEmptyMessageDelayed(
//							ImageHandler.MSG_UPDATE_IMAGE,
//							ImageHandler.MSG_DELAY);
					break;
				default:
					break;
				}
			}
		});
//		vp_condensation.setCurrentItem(Integer.MAX_VALUE / 2);// 默认在中间，使用户看不到边界
		// 开始轮播效果
//		handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE,
//				ImageHandler.MSG_DELAY);
	}
	
	
	/**
	 * 初始化控件
	 * @author Ron
	 * @date 2016-8-4  上午12:28:26
	 */
	private void findViewByIds() {
		main_class_t1 = (TextView) findViewById(R.id.main_class_t1);
		main_class_t2 = (TextView) findViewById(R.id.main_class_t2);
		main_class_t3 = (TextView) findViewById(R.id.main_class_t3);
		main_class_tt1 = (TextView) findViewById(R.id.main_class_tt1);
		main_class_tt2 = (TextView) findViewById(R.id.main_class_tt2);
		main_class_tt3 = (TextView) findViewById(R.id.main_class_tt3);
		
		listView = (ListView) findViewById(R.id.main_info_lv);
		pullListView = (PullToRefreshScrollView) findViewById(R.id.main_info_scroll_lv);
		main_linear_container =  (LinearLayout) findViewById(R.id.main_linear_container);
	}

	/**
	 * 通知适配器展示数据
	 * @author Ron
	 * @date 2016-8-20  上午12:22:37
	 */
	private void notifyAdapter() {
		if(adapter == null){
			adapter = new MainNewsAdapter(getActivity(),tempList );
			listView.setAdapter(adapter);
		}else{
			adapter.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 注意点击的控件id
		case R.id.ll_title_left:
			break;
	
		}
	};
	
	@Override
	public void onDestroy(){
		super.onDestroy();
	}

}
