package com.frame.member.activity;
/**
 * 个人消费流水
 * Ron
 */
import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.MyBillParser;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.adapters.MyBillAdapter;
import com.frame.member.bean.MyBillBean.BillInfo;
import com.frame.member.bean.MyBillBean.Consumption;
import com.frame.member.bean.MyBillBean.MyBillResult;
import com.frame.member.widget.refreshlistview.PullToRefreshBase;
import com.frame.member.widget.refreshlistview.PullToRefreshBase.Mode;
import com.frame.member.widget.refreshlistview.PullToRefreshListView;

public class MyBillActivity extends BaseActivity {
	private PullToRefreshListView pullListView;
	private int page;
	private MyBillAdapter adapter;
	private TextView billYear,totalAmount;
	private List<BillInfo> infoList = new ArrayList<BillInfo>();
	BaseParser<MyBillResult> parser = new MyBillParser();
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_my_bill);
//		findViewById();

		getData();
	}

	@Override
	protected void findViewById() {
		tv_title.setText("消费流水");
		pullListView = (PullToRefreshListView) findViewById(R.id.my_bill_lv);
		billYear = (TextView) findViewById(R.id.my_bill_year);
		totalAmount = (TextView) findViewById(R.id.my_bill_total);
	}
	/**
	 * 获取数据-消费流水
	 * @author Ron
	 * @date 2016-9-19  上午12:01:27
	 */
	private void getData() {
		if (page == 0)
			page = 1;
		String url = AppConstants.APP_SORT_STUDENT+"/myconsumption";
		HttpRequestImpl request = new HttpRequestImpl(this,
				url, parser,HttpRequest.RequestMethod.post);
		request.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""));
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, "")); //用户id 
//		request.addParam("memberUserId","89"); //写死
		request.addParam("page_size", "10").addParam("page_num", "" + page);
		getDataFromServer(request, callback1);
	}
	private DataCallback<MyBillResult> callback1 = new DataCallback<MyBillResult>() {

		@Override
		public void processData(MyBillResult object, RequestResult result) {
			pullListView.onRefreshComplete();
			if(result == RequestResult.Success){
				if(null != object){
					if("200".equals(object.code)){
						if(object.consumptionList != null && object.consumptionList.size() > 0){
							Consumption c = object.consumptionList.get(0);
							if (page == 1)
								infoList.clear();
							infoList.addAll(c.info);
							notifyAdapter();
							billYear.setText(c.year+"雪季总消费");
							totalAmount.setText("￥"+c.totalAmount+".00元");
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
			adapter = new MyBillAdapter(this,infoList );
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
