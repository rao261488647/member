package com.frame.member.frag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.RentCarListParser;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.SpecialCarEnrollActivity;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.adapters.CondensationPagerAdapter;
import com.frame.member.bean.RentCarListBean;
import com.frame.member.bean.RentCarListBean.RentCarItem;
import com.frame.member.widget.refreshlistview.PullToRefreshBase;
import com.frame.member.widget.refreshlistview.PullToRefreshScrollView;
import com.frame.member.widget.refreshlistview.PullToRefreshBase.Mode;

/**
 * 
 * 租车报名
 * @author kun
 * 
 */
public class RegisterFrag extends BaseFrag implements OnClickListener {

	private PullToRefreshScrollView sv_conden_list_body;
	
	CondensationPagerAdapter pagerAdapter;
	LinearLayout ll_sort_conden_sport, ll_sort_conden_hotTopic,
			ll_sort_conden_classicAction;

	protected TextView tv_title_left;

	public ViewPager vp_condensation;

	private LinearLayout ll_main_container;
	
	private List<ImageView> condensation_pager_list = new ArrayList<ImageView>();

	public static RegisterFrag newInstance(String title) {

		RegisterFrag fragment = new RegisterFrag();
		Bundle bundle = new Bundle();
		bundle.putString(AppConstants.FRAG_TITLE_KEY, title);
		fragment.setArguments(bundle);
		return fragment;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.frag_rent_car, container,
				false);

		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("预约报名专车");
		
		ll_main_container = (LinearLayout) findViewById(R.id.ll_main_container);
		
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

	//private List<ImageView> condensation_pager_list = new ArrayList<ImageView>();
	public List<RentCarItem> mainpage_data = new ArrayList<RentCarItem>();

	private int pageCur = 0, totalCount;

	private void getMainPageData() {
		HttpRequest request_main_data = new HttpRequestImpl(getActivity(), AppConstants.RENTCARLIST, new RentCarListParser());

		request_main_data
				.addParam(
						"cell",
						(String) SPUtils.getAppSpUtil().get(mContext,
								SPUtils.KEY_PHONENUM, ""))
				.addParam("currentPage", ++pageCur + "")
				.addParam("loadCount", 5 + "");

		mContext.getDataFromServer(request_main_data, pageCur == 1,
				new DataCallback<RentCarListBean>() {

					@Override
					public void processData(RentCarListBean object,
							RequestResult result) {
						if (sv_conden_list_body.isRefreshing())
							sv_conden_list_body.onRefreshComplete();
						if (result == RequestResult.Success) {

							if (pageCur > 1)
								Toast.makeText(mContext, "加载成功", 0).show();
							//总条数
							//totalCount = object.totoalRecord;

							//mainpage_data.add(object.rentCarList);
							//lv_rentcar_list.setAdapter(adapter);
							for (int i = 0; i < object.rentCarList.size(); i++) {
								ll_main_container
										.addView(setRentCarLayout(object.rentCarList.get(i)));
							}
						}
					}
				}, "加载中...");
	}

	private View setRentCarLayout(final RentCarItem rentCarItem) {
		View result = View.inflate(mContext, R.layout.item_rent_car, null);
		
		TextView tv_main_page_item_title = (TextView) result
				.findViewById(R.id.tv_item_rent_car_name);
		TextView tv_item_rent_car_plat = (TextView) result
				.findViewById(R.id.tv_item_rent_car_plat);
//		TextView tv_item_rent_car_deposit = (TextView) result
//				.findViewById(R.id.tv_item_rent_car_deposit);
//		TextView tv_item_rent_car_money = (TextView) result
//				.findViewById(R.id.tv_item_rent_car_money);
		TextView tv_item_rent_car_order = (TextView) result
				.findViewById(R.id.tv_item_rent_car_order);
//		TextView tv_item_rent_car_contract = (TextView) result
//				.findViewById(R.id.tv_item_rent_car_contract);
		
		ImageView im_rent_car = (ImageView) result
				.findViewById(R.id.iv_item_rent_car);
		
		TTApplication.getInstance()
		.disPlayImageDef(rentCarItem.url, im_rent_car);
		
		tv_main_page_item_title.setText(rentCarItem.carType);
		tv_item_rent_car_plat.setText("适用平台：" +rentCarItem.plat);
		//tv_item_rent_car_deposit.setText("押金" + rentCarItem.deposit + "元");
		//tv_item_rent_car_money.setText(rentCarItem.rentPerPay);
//		tv_item_rent_car_contract.setText("合同期" + rentCarItem.contractTerm);
		
		tv_item_rent_car_order.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),SpecialCarEnrollActivity.class);
				intent.putExtra("contractTermList", (Serializable) rentCarItem.contractTermList);
				intent.putExtra("rentCarId", rentCarItem.rentCarId);
				intent.putExtra("url", rentCarItem.url);
				intent.putExtra("deposit", "押金¥"+rentCarItem.deposit);
				intent.putExtra("plat", rentCarItem.plat);
				intent.putExtra("carType", rentCarItem.carType);
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
