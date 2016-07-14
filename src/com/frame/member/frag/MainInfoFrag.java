package com.frame.member.frag;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.MainInfoParser;
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

	private PullToRefreshScrollView sv_conden_list_body;

	CondensationPagerAdapter pagerAdapter;
	LinearLayout ll_sort_conden_sport, ll_sort_conden_hotTopic,
			ll_sort_conden_classicAction;

	protected TextView tv_title_left;
	private int oldPosition = 0;// 记录上一次点的位置
	private ArrayList<View> dots;


	private List<ImageView> condensation_pager_list = new ArrayList<ImageView>();
	public List<MainBanner> mainBannerDate = new ArrayList<MainBanner>();
	private int pageCur = 0, totalCount;
	
	public ViewPager vp_condensation;
	public ImageHandler handler = new ImageHandler(new WeakReference<BaseFrag>(
			this));

	private LinearLayout main_info_container;

	private MainNewsAdapter sAdapter = null;
	
	private TextView main_class_t1,main_class_t2,main_class_t3,main_class_tt1,main_class_tt2,main_class_tt3;
	private ImageView main_class_img1,main_class_img2,main_class_imgg1,main_class_imgg2;
	
	private ListView main_info_lv;
	
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
		sv_conden_list_body = (PullToRefreshScrollView) findViewById(R.id.sv_conden_list_body);
		sv_conden_list_body.setMode(Mode.PULL_FROM_END);

		sv_conden_list_body
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase refreshView) {

					}

					@Override
					public void onPullUpToRefresh(PullToRefreshBase refreshView) {
//						if (mainBannerDate.size() < totalCount) {
//							sv_conden_list_body.setRefreshing(true);
//							getMainInfoData();
//						} else {
//							Toast.makeText(mContext, "没有更多数据", 0).show();
//							sv_conden_list_body.onRefreshComplete();
//						}
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
//					    HashMap<String, Object> map = null;
//					    List<HashMap<String,Object>> mList = new ArrayList<HashMap<String,Object>>();
//					    for(int i =0;i<object.mainNewsData.size();i++){
//					    	MainNews news = object.mainNewsData.get(i);
//					    	map = new HashMap<String, Object>();
//					    	map.put("newsUrl", news.newsUrl);
//					    	map.put("newsTitle", news.newsTitle);
//					    	map.put("newsIntro", news.newsIntro.replace("\r\n", ""));
//					    	mList.add(map);
//					    }
//						String itemStr[] = new String[]{"newsPhoto","newsTitle","newsIntro"};
//						int itemRes[] = new int[] {R.id.news_img1,R.id.news_text1,R.id.news_text2};
//						sAdapter = new SimpleAdapter(this, object.mainRemmendClassData, R.layout.item_main_info, new String[]{"newsTitle","newsIntro","newsPhoto"},new int[] {R.id.news_img1,R.id.news_text1,R.id.news_text2});
//						sAdapter = new SimpleAdapter(this, mList , R.layout.item_main_info, new String[]{"newsTitle","newsIntro","newsPhoto"},new int[] {R.id.news_img1,R.id.news_text1,R.id.news_text2});
//						sAdapter = new SimpleAdapter(getActivity(), mList, R.layout.item_main_info, itemStr, itemRes);
						sAdapter = new MainNewsAdapter(getActivity(), object.mainNewsData);
						main_info_lv.setAdapter(sAdapter);
						setListViewHeight(main_info_lv);
					
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
		dots.add(findViewById(R.id.dot_0));
		dots.add(findViewById(R.id.dot_1));
		dots.add(findViewById(R.id.dot_2));

		vp_condensation = (ViewPager) rootView
				.findViewById(R.id.vp_condensation);

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
	
	
	
	
	protected void findViewByIds() {
		main_class_t1 = (TextView) findViewById(R.id.main_class_t1);
		main_class_t2 = (TextView) findViewById(R.id.main_class_t2);
		main_class_t3 = (TextView) findViewById(R.id.main_class_t3);
		main_class_tt1 = (TextView) findViewById(R.id.main_class_tt1);
		main_class_tt2 = (TextView) findViewById(R.id.main_class_tt2);
		main_class_tt3 = (TextView) findViewById(R.id.main_class_tt3);
		
		main_class_img1 = (ImageView) findViewById(R.id.main_class_img1);
		main_class_img2 = (ImageView) findViewById(R.id.main_class_img2);
		
		main_class_imgg1 = (ImageView) findViewById(R.id.main_class_imgg1);
		main_class_imgg2 = (ImageView) findViewById(R.id.main_class_imgg2);
		
		main_info_lv = (ListView) findViewById(R.id.main_info_lv);
	}

	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 注意点击的控件id
		case R.id.ll_title_left:
			break;
	
		}
	};

}
