package com.frame.member.activity;
import android.widget.RelativeLayout;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.MyOrderParser;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.bean.MyOrderBean.MyOrderResult;
/**
 * 我的预约
 * @author 饶鹏
 */

public class MyOrderActivity extends BaseActivity {
	
	private RelativeLayout orderNewRl,orderOldRl;
	private BaseParser parser = new MyOrderParser();
	private int page;
	
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_my_order);
		getData();
	}

	@Override
	protected void findViewById() {
	  	//lv_statemetns_list = (ListView) findViewById(R.id.lv_statements_list);
		orderNewRl = (RelativeLayout) findViewById(R.id.my_order_rl_new);
		orderOldRl = (RelativeLayout) findViewById(R.id.my_order_rl_old);
//		tv_title.setText("我的预约");
	}
	
	/**
	 * 获取数据-消费流水
	 * @author Ron
	 * @date 2016-9-19  上午12:01:27
	 */
	private void getData() {
		if (page == 0)
			page = 1;
		String url = AppConstants.APP_SORT_STUDENT+"/mymeet";
		HttpRequestImpl request = new HttpRequestImpl(this,
				url, parser,HttpRequest.RequestMethod.post);
		request.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""));
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, "")); //用户id 
		request.addParam("memberUserId","89"); //写死
//		request.addParam("page_size", "10").addParam("page_num", "" + page);
		getDataFromServer(request, callback1);
	}
	private DataCallback<MyOrderResult> callback1 = new DataCallback<MyOrderResult>() {

		@Override
		public void processData(MyOrderResult object, RequestResult result) {
			if(result == RequestResult.Success){
				if(null != object){
					if("200".equals(object.code)){
						if(object.orderList != null && object.orderList.size() > 0){
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
//		if(adapter == null){
//			adapter = new MyOrderAdapter(this,infoList );
//			pullListView.setAdapter(adapter);
//		}else{
//			adapter.notifyDataSetChanged();
//		}
	}
	
	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
//		iv_title_back.setVisibility(0);
	}
}
