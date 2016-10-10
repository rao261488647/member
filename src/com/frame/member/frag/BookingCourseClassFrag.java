package com.frame.member.frag;

import java.util.ArrayList;
import java.util.List;
import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.BookingClassParser;
import com.frame.member.Parsers.BookingClassSelectedParser;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.activity.BaseActivity;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.activity.ClassDetailActivity;
import com.frame.member.adapters.BookingClassAdapter;
import com.frame.member.bean.BookingClassResult;
import com.frame.member.bean.BookingClassSelectedResult;
import com.frame.member.bean.BookingClassSelectedResult.AreaChoices;
import com.frame.member.bean.BookingClassSelectedResult.CategoryChoices;
import com.frame.member.bean.BookingClassSelectedResult.SkifieldChoices;
import com.frame.member.widget.refreshlistview.PullToRefreshBase.Mode;
import com.frame.member.widget.refreshlistview.PullToRefreshBase;
import com.frame.member.widget.refreshlistview.PullToRefreshListView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 
 * 滑雪班
 * 
 *
 */
public class BookingCourseClassFrag extends BaseFrag implements OnClickListener {

	private LinearLayout ll_booking_item, ll_item_1_booking, ll_item_2_booking, ll_item_3_booking, ll_item_4_booking;
	private TextView tv_item_1_booking, tv_item_2_booking, tv_item_3_booking, tv_item_4_booking;
	private ImageView iv_item_1_booking, iv_item_2_booking, iv_item_3_booking, iv_item_4_booking;
	private PullToRefreshListView lv_booking_course_class;
	private BookingClassAdapter adapter;
	private PopupWindow mPop;
	private View container_pop;
	private ListView lv_booking_pop;
	private ArrayAdapter<String> adapter_list;
	private View view_black_filter;
	private int rank = -1;

	private List<AreaChoices> list_area = new ArrayList<BookingClassSelectedResult.AreaChoices>();
	private List<SkifieldChoices> list_skifield = new ArrayList<BookingClassSelectedResult.SkifieldChoices>();
	private List<CategoryChoices> list_category = new ArrayList<BookingClassSelectedResult.CategoryChoices>();
	private ArrayList<String> list_area_str = new ArrayList<String>();
	private ArrayList<String> list_skifield_str = new ArrayList<String>();
	private ArrayList<String> list_category_str = new ArrayList<String>();
	private ArrayList<String> list_sdplate = new ArrayList<String>();
	// 所有的item的popwindow的数据全是用这个代替
	private ArrayList<String> list_standard = new ArrayList<String>();
	
	private String areaId="",skifieldId="",categoryId="",sdplate="";//雪区，雪场，职称类型，单双板

	private static BookingCourseClassFrag mFrag;

	public static BookingCourseClassFrag newInstance() {
		if (mFrag == null)
			mFrag = new BookingCourseClassFrag();

		return mFrag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.frag_booking_course_class, container, false);

		initView();
		initOnclick();
		initProgress();
		return rootView;
	}

	private void initView() {
		ll_item_1_booking = (LinearLayout) findViewById(R.id.ll_item_1_booking);
		ll_item_2_booking = (LinearLayout) findViewById(R.id.ll_item_2_booking);
		ll_item_3_booking = (LinearLayout) findViewById(R.id.ll_item_3_booking);
		ll_item_4_booking = (LinearLayout) findViewById(R.id.ll_item_4_booking);
		tv_item_1_booking = (TextView) findViewById(R.id.tv_item_1_booking);
		tv_item_2_booking = (TextView) findViewById(R.id.tv_item_2_booking);
		tv_item_3_booking = (TextView) findViewById(R.id.tv_item_3_booking);
		tv_item_4_booking = (TextView) findViewById(R.id.tv_item_4_booking);
		iv_item_1_booking = (ImageView) findViewById(R.id.iv_item_1_booking);
		iv_item_2_booking = (ImageView) findViewById(R.id.iv_item_2_booking);
		iv_item_3_booking = (ImageView) findViewById(R.id.iv_item_3_booking);
		iv_item_4_booking = (ImageView) findViewById(R.id.iv_item_4_booking);
		lv_booking_course_class = (PullToRefreshListView) findViewById(R.id.lv_booking_course_class);
		view_black_filter = findViewById(R.id.view_black_filter);
		ll_booking_item = (LinearLayout) findViewById(R.id.ll_booking_item);
	}

	private void initOnclick() {
		ll_item_1_booking.setOnClickListener(this);
		ll_item_2_booking.setOnClickListener(this);
		ll_item_3_booking.setOnClickListener(this);
		ll_item_4_booking.setOnClickListener(this);
	}

	// 主逻辑代码
	private void initProgress() {
		view_black_filter.setAlpha(0.0f);
		container_pop = getActivity().getLayoutInflater().inflate(R.layout.item_booking_pop, null);
		lv_booking_pop = (ListView) container_pop.findViewById(R.id.lv_booking_pop);
		adapter_list = new ArrayAdapter<String>(mContext, R.layout.item_pop_list, list_standard);
		lv_booking_pop.setAdapter(adapter_list);
		lv_booking_pop.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (rank) {
				case 0:
					if(position != 0){
						areaId = list_area.get(position).areaId;
						tv_item_1_booking.setText(list_standard.get(position));
					}else{
						areaId = "";
						tv_item_1_booking.setText("雪区");
					}
					break;
				case 1:
					if(position != 0){
						skifieldId = list_skifield.get(position).skifieldId;
						tv_item_2_booking.setText(list_standard.get(position));
					}else{
						skifieldId = "";
						tv_item_2_booking.setText("雪场");
					}
					break;
				case 2:
					if(position != 0){
						categoryId = list_category.get(position).categoryId;
						tv_item_3_booking.setText(list_standard.get(position));
					}else{
						categoryId = "";
						tv_item_3_booking.setText("类型");
					}
					break;
				case 3:
					if(position != 0){
						sdplate = list_sdplate.get(position);
						tv_item_4_booking.setText(list_standard.get(position));
					}else{
						sdplate = "";
						tv_item_4_booking.setText("单双板");
					}
					break;
			
				}
				mPop.dismiss();
				page = 1;
				lv_booking_course_class.setMode(Mode.BOTH);
				getData(areaId,skifieldId,categoryId,sdplate);
			}
		});
		lv_booking_course_class.setMode(Mode.BOTH);
		lv_booking_course_class.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), ClassDetailActivity.class);
				intent.putExtra("courseId", list_result.get(position-1).courseId);
				startActivity(intent);
			}
		});
		lv_booking_course_class.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				page = 1;
				lv_booking_course_class.setMode(Mode.BOTH);
				getData(areaId,skifieldId,categoryId,sdplate);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				page++;
				getData(areaId,skifieldId,categoryId,sdplate);
			}
		});
		getData(areaId,skifieldId,categoryId,sdplate);
		getSelectedChoice();
	}

	int page;
	private List<BookingClassResult> list_result = new ArrayList<BookingClassResult>();

	// 请求获取主数据
	private void getData(String areaId,String skifieldId,String categoryId,String sdplate) {
		if (page == 0)
			page = 1;
		BaseParser<List<BookingClassResult>> parser = new BookingClassParser();
		HttpRequest request = new HttpRequestImpl(getActivity(), AppConstants.APP_SORT_STUDENT + "/skiingclass",
				parser);
		request.addParam("page_size", "10").addParam("page_num", "" + page)
				.addParam("areaId", areaId)
				.addParam("skifieldId", skifieldId)
				.addParam("sdplate", sdplate)
				.addParam("categoryId", categoryId);
		((BaseActivity) getActivity()).getDataFromServer(request, true, callBack);

	}

	private DataCallback<List<BookingClassResult>> callBack = new DataCallback<List<BookingClassResult>>() {

		@Override
		public void processData(List<BookingClassResult> object, RequestResult result) {
			lv_booking_course_class.onRefreshComplete();
				if (object != null && object.size()>0) {
//					showToast(object.get(0).message);
					if ("200".equals(object.get(0).code)) {
						if (page == 1)
							list_result.clear();
						list_result.addAll(object);
						notifyAdapter();
					} else {
						lv_booking_course_class.setMode(Mode.PULL_FROM_START);
					}
				} else {
					lv_booking_course_class.setMode(Mode.PULL_FROM_START);
					if (page == 1) {
						list_result.clear();
						notifyAdapter();
					}
				}
		}

	};

	private void notifyAdapter() {
		if (adapter == null) {
			adapter = new BookingClassAdapter(getActivity(), list_result);
			lv_booking_course_class.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
	}

	//  获取筛选信息接口
	private void getSelectedChoice() {
		BaseParser<BookingClassSelectedResult> parser = new BookingClassSelectedParser();
		HttpRequest request = new HttpRequestImpl(getActivity(), AppConstants.APP_SORT_STUDENT + "/skiingclassselect",
				parser);
		DataCallback<BookingClassSelectedResult> callBack = new DataCallback<BookingClassSelectedResult>() {

			@Override
			public void processData(BookingClassSelectedResult object, RequestResult result) {
				lv_booking_course_class.onRefreshComplete();
				if (object != null) {
					list_area_str.clear();
					list_sdplate.clear();
					list_category_str.clear();
					list_skifield_str.clear();
					list_area.clear();
					list_category.clear();
					list_skifield.clear();
					list_area.addAll(object.areaChoices);
					list_skifield.addAll(object.skifieldChoices);
					list_category.addAll(object.categoryChoices);
					for(AreaChoices area:object.areaChoices){
						list_area_str.add(area.areaName);
					}
					
					for(SkifieldChoices field:object.skifieldChoices){
						list_skifield_str.add(field.skifieldName);
					}
					for(CategoryChoices field:object.categoryChoices){
						list_category_str.add(field.categoryName);
					}
					list_sdplate.addAll(object.sdplateChoices);
				}
			}

		};
		((BaseActivity) getActivity()).getDataFromServer(request, false, callBack);
	}

	private void showPopwindow() {
		if (mPop == null) {
			mPop = new PopupWindow(container_pop, LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT, true);
		}
		mPop.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
		// mPop.setOutsideTouchable(true);
		mPop.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				resetItem();
				view_black_filter.setAlpha(0.0f);

			}
		});
		mPop.showAsDropDown(ll_booking_item, 0, 0);
		view_black_filter.setAlpha(0.5f);
	}

	@Override
	public void onClick(View v) {
		list_standard.clear();
		switch (v.getId()) {

		case R.id.ll_item_1_booking:
			list_standard.addAll(list_area_str);
			adapter_list.notifyDataSetChanged();
			setItem(0);

			break;
		case R.id.ll_item_2_booking:
			list_standard.addAll(list_skifield_str);
			adapter_list.notifyDataSetChanged();
			setItem(1);

			break;
		case R.id.ll_item_3_booking:
			list_standard.addAll(list_category_str);
			adapter_list.notifyDataSetChanged();
			setItem(2);

			break;
		case R.id.ll_item_4_booking:
			list_standard.addAll(list_sdplate);
			adapter_list.notifyDataSetChanged();
			setItem(3);
			break;

		}

	}

	// 通过position值来设置Item的状态效果
	private void setItem(int position) {
		if (rank != position) {
			changeItem(position);
			showPopwindow();
		} else {
			mPop.dismiss();

		}

	}

	// 通过点击按钮来进行状态变化
	private void changeItem(int rank) {
		resetItem();
		switch (rank) {
		case 0:
			tv_item_1_booking.setTextColor(0xff5a5a5a);
			iv_item_1_booking.setImageResource(R.drawable.more_choice_selected);
			this.rank = 0;
			break;
		case 1:
			tv_item_2_booking.setTextColor(0xff5a5a5a);
			iv_item_2_booking.setImageResource(R.drawable.more_choice_selected);
			this.rank = 1;
			break;
		case 2:
			tv_item_3_booking.setTextColor(0xff5a5a5a);
			iv_item_3_booking.setImageResource(R.drawable.more_choice_selected);
			this.rank = 2;
			break;
		case 3:
			tv_item_4_booking.setTextColor(0xff5a5a5a);
			iv_item_4_booking.setImageResource(R.drawable.more_choice_selected);
			this.rank = 3;
			break;

		}
	}

	// 重置Item
	private void resetItem() {
		tv_item_1_booking.setTextColor(0xff8d8d8d);
		tv_item_2_booking.setTextColor(0xff8d8d8d);
		tv_item_3_booking.setTextColor(0xff8d8d8d);
		tv_item_4_booking.setTextColor(0xff8d8d8d);
		iv_item_1_booking.setImageResource(R.drawable.more_choices);
		iv_item_2_booking.setImageResource(R.drawable.more_choices);
		iv_item_3_booking.setImageResource(R.drawable.more_choices);
		iv_item_4_booking.setImageResource(R.drawable.more_choices);
		rank = -1;
	}

}
