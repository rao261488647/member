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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.MainNotifyPaser;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.ImageHandler;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.InfoDetailActivity;
import com.frame.member.activity.MainNotifyDetailActivity;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.adapters.CondensationPagerAdapter;
import com.frame.member.bean.MainNotifyBean;
import com.frame.member.bean.MainNotifyBean.Notify;
import com.frame.member.widget.refreshlistview.PullToRefreshBase;
import com.frame.member.widget.refreshlistview.PullToRefreshScrollView;
import com.frame.member.widget.refreshlistview.PullToRefreshBase.Mode;

/**
 * 
 * 首页
 * 
 * @author long
 * 
 */
public class MainPageFrag extends BaseFrag implements OnClickListener {

	GridView gv_conden;
	// private PullToRefreshGridView mPullRefreshGridView;
	private PullToRefreshScrollView sv_conden_list_body;

	CondensationPagerAdapter pagerAdapter;
	LinearLayout ll_sort_conden_sport, ll_sort_conden_hotTopic,
			ll_sort_conden_classicAction;

	protected TextView tv_title_left;
	private int oldPosition = 0;// 记录上一次点的位置
	private ArrayList<View> dots;

	public ViewPager vp_condensation;
	public ImageHandler handler = new ImageHandler(new WeakReference<BaseFrag>(
			this));

	private LinearLayout ll_main_container;

	public static MainPageFrag newInstance(String title) {

		MainPageFrag fragment = new MainPageFrag();
		Bundle bundle = new Bundle();
		bundle.putString(AppConstants.FRAG_TITLE_KEY, title);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.frag_condensation, container,
				false);

		// gv_conden = (GridView) rootView.findViewById(R.id.gv_conden);
		// 显示的点
		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.dot_0));
		dots.add(findViewById(R.id.dot_1));
		dots.add(findViewById(R.id.dot_2));

		vp_condensation = (ViewPager) rootView
				.findViewById(R.id.vp_condensation);

		ll_main_container = (LinearLayout) findViewById(R.id.ll_main_container);

		for (int i = 0; i < 3; i++) {
			ImageView iv = new ImageView(getActivity());
			iv.setScaleType(ScaleType.CENTER_CROP);
			switch (i) {
			case 0:
				iv.setImageResource(R.drawable.car);
				break;
			case 1:
				iv.setImageResource(R.drawable.car);
				break;
			case 2:
				iv.setImageResource(R.drawable.car);
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
		tv_title_left = (TextView) findViewById(R.id.tv_title_left);
		tv_title_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intentMain = new Intent(
//						getActivity(), MainActivity.class);
//				startActivity(intentMain);
				
			}
		});
		tv_title = (TextView) findViewById(R.id.tv_title);

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
						if (mainpage_data.size() < totalCount) {
							sv_conden_list_body.setRefreshing(true);
							getMainPageData();
						} else {
							Toast.makeText(mContext, "没有更多数据", 0).show();
							sv_conden_list_body.onRefreshComplete();
						}
					}
				});
		

		getMainPageData();

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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 注意点击的控件id
		case R.id.ll_title_left:
			// Intent intent = new Intent(getActivity(),
			// CondenFilterActivity.class);
			// intent.putExtra("sportStyle", sportStyle);
			// startActivityForResult(intent, Activity.RESULT_FIRST_USER);
			// getActivity().overridePendingTransition(R.anim.zoomin,
			// R.anim.zoomout);
			break;
		// case R.id.ll_title_right:
		// // startActivityForResult(new Intent(getActivity(),
		// // CondenCreateActivity.class), 1120);
		// menuWindow = new CondenSortPopupWindow(getActivity(), itemsOnClick);
		// menuWindow.showAtLocation(getActivity()
		// .findViewById(R.id.container), Gravity.BOTTOM
		// | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
		// break;

		}
	};

}
