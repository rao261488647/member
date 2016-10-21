package com.frame.member.activity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.MyOrderParser;
import com.frame.member.Utils.CommonUtil;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.adapters.MyOrderClassAdapter;
import com.frame.member.adapters.MyOrderCoachAdapter;
import com.frame.member.bean.MyOrderBean.MyOrder;
import com.frame.member.bean.MyOrderBean.MyOrderResult;
import com.frame.member.bean.MyOrderBean.Order;
import com.frame.member.bean.MyOrderBean.OrderCoach;
import com.frame.member.bean.MyOrderBean.OrderCourse;
import com.frame.member.bean.MyOrderBean.OrderTeach;
/**
 * 我的预约
 * @author 饶鹏
 */
/**
 * 我的预约
 * @author 饶鹏
 */

public class MyOrderActivity extends BaseActivity {
	
	private LinearLayout orderNewRl,orderOldRl;
	private BaseParser parser = new MyOrderParser();
	
	private List<MyOrder> orderList = new ArrayList<MyOrder>();
	
	private List<OrderTeach> currentOrderTeachNewList = new ArrayList<OrderTeach>(); //当前季度新预约(教练) 未签到/未评价/未过期
	private List<OrderTeach> currentOrderTeachOldList = new ArrayList<OrderTeach>(); //当前季度历史预约(教练) 已签到/已退订/已过期
	
	private List<OrderCourse> currentOrderCourseNewList = new ArrayList<OrderCourse>();//当前季度新预约(课程) 未签到/未评价/未过期
	private List<OrderCourse> currentOrderCourseOldList = new ArrayList<OrderCourse>();//当前季度新预约(课程) 已签到/已退订/已过期
	
	private List<OrderTeach> oldOrderTeachList = new ArrayList<OrderTeach>(); //往季预约(教练)
	
	private List<OrderCourse> oldOrderCourseList = new ArrayList<OrderCourse>();//往季预约(课程)
	
	//往季教练预约，包含往季所属年度， map 的key就是所属年度
	private List<Map<String,List<OrderTeach>>> teachList = new ArrayList<Map<String,List<OrderTeach>>>();
	//往季课程预约，包含往季所属年度， map 的key就是所属年度
	private List<Map<String,List<OrderCourse>>> courseList = new ArrayList<Map<String,List<OrderCourse>>>();
	
	private int page;
	
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_my_order);
		getData();
	}

	@Override
	protected void findViewById() {
	  	//lv_statemetns_list = (ListView) findViewById(R.id.lv_statements_list);
		orderNewRl = (LinearLayout) findViewById(R.id.my_order_rl_new);
		orderOldRl = (LinearLayout) findViewById(R.id.my_order_rl_old);
		tv_title.setText("我的预约");
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
//		url = "http://testapi.flowerski.com/v2/student/mymeet"; //V2版本 暂时写死
		HttpRequestImpl request = new HttpRequestImpl(this,
				url, parser,HttpRequest.RequestMethod.post);
		request.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""));
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, "")); //用户id 
		request.addParam("memberUserId","2262"); //写死
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
//							notifyAdapter();
							orderList = object.orderList;
							assembleList();
							initCurrentNewOrder();
							initCurrentOldOrder();
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
	 * 
	* <p>Description: 用于组装各个list</p>
	* @author raopeng
	* @date 2016-10-20 下午7:23:55
	 */
	private void assembleList(){
		if(orderList != null && orderList.size() > 0){
			for(MyOrder order : orderList){
				//当季
				List<Order> orders = order.orders;
				if("1".equals(order.flag)){
					for(Order o : orders){
						if(o.course != null){
							if("未签到".equals(o.course.status) || "未评价".equals(o.course.status) || "未过期".equals(o.course.status)){
								currentOrderCourseNewList.add(o.course);
							}else{
								currentOrderCourseOldList.add(o.course);
							}
						}
						if(o.teach != null){
							if("未签到".equals(o.teach.status) || "未评价".equals(o.teach.status) || "未过期".equals(o.teach.status)){
								currentOrderTeachNewList.add(o.teach);
							}else{
								currentOrderTeachOldList.add(o.teach);
							}
						}
					}
				}else{
					String reason = order.season; //获取所属年度
					Map<String, List<OrderTeach>> teachMap = new HashMap<String, List<OrderTeach>>();
					Map<String, List<OrderCourse>> courseMap = new HashMap<String, List<OrderCourse>>();
					oldOrderCourseList.clear(); //用完清理
					oldOrderTeachList.clear(); //清理
					for(Order o : orders){
						if(o.course != null){
							oldOrderCourseList.add(o.course);
						}
						if(o.teach != null){
							oldOrderTeachList.add(o.teach);
						}
					}
					courseMap.put(reason, oldOrderCourseList);
					teachMap.put(reason, oldOrderTeachList);
					courseList.add(courseMap);
					teachList.add(teachMap);
				}
			}
		}
	}
	/**
	* <p>Description: 初始化当季有效的预约</p>
	* @author raopeng
	* @date 2016-10-20 下午7:45:17
	 */
	@SuppressLint("ResourceAsColor")
	private void initCurrentNewOrder(){
		if(currentOrderCourseNewList != null &&currentOrderCourseNewList.size() > 0){
			initMyOrderClassAdapter(currentOrderCourseNewList,orderNewRl);
		}
		if(currentOrderTeachNewList != null &&currentOrderTeachNewList.size() > 0){
			//添加分割线
			initSplitLine(orderNewRl);
			initMyOrderTeachAdapter(currentOrderTeachNewList,orderNewRl);
		}
		
	}
	
	/**
	* <p>Description:初始化历史订单，当季的 </p>
	* @author raopeng
	* @date 2016-10-20 下午8:41:05
	 */
	@SuppressLint("ResourceAsColor")
	private void initCurrentOldOrder(){
		
		OrderCourse c = new OrderCourse();
		c.beginTime="2016年10月10日";
		c.categoryName="指导员培训";
		c.courseId="1";
		c.hadDay = 5;
		c.overTime="2016年10月25日";
		c.courseName="国职滑雪教练培训班—五级";
		c.skifield="崇礼 多乐美地滑雪场";
		c.status="已过期";
		
		currentOrderCourseOldList.add(c);
		currentOrderCourseOldList.add(c);
		
		OrderCoach h = new OrderCoach();
		h.coachHead="http://test.flowerski.com/upload/coach/head/20_20831447167229.jpg";
		h.coachName="李白";
		OrderTeach t = new OrderTeach();
		t.skifield="崇礼 多乐美地滑雪场";
		t.status="已过期";
		t.date="2016年12月10日";
		t.coach = h;
		
		currentOrderTeachOldList.add(t);
		currentOrderTeachOldList.add(t);
		
		
		
		
		//添加历史订单标题
		if((currentOrderCourseOldList != null && currentOrderCourseOldList.size() > 0) || (currentOrderTeachOldList != null &&currentOrderTeachOldList.size() > 0)){
			initOrderTitle(orderNewRl,"历史订单");
		}
		
		if(currentOrderCourseOldList != null &&currentOrderCourseOldList.size() > 0){
			initMyOrderClassAdapter(currentOrderCourseOldList, orderNewRl);
		}
		
		if(currentOrderTeachOldList != null &&currentOrderTeachOldList.size() > 0){
			//添加分割线
			initSplitLine(orderNewRl);
			initMyOrderTeachAdapter(currentOrderTeachOldList,orderNewRl);
		}
		
	}
	
	/**
	* <p>Description: 加载往季预约订单</p>
	* @author raopeng
	* @date 2016-10-21 上午10:06:00
	 */
	@SuppressLint("ResourceAsColor")
	private void initOldOrder(){
		boolean isHaveCourse = false;
		boolean isHaveTeach = false;
		//往季订单 只有 当教练、课程信息 有一个不为空的时候 才进行加载
		if((isHaveCourse = (courseList != null && courseList.size() > 0)) || (isHaveTeach = (teachList != null && teachList.size() > 0))){
			//存在课程预约
			if(isHaveCourse){
				for(Map<String, List<OrderCourse>> courseMap : courseList){
					for(Map.Entry<String, List<OrderCourse>> entry : courseMap.entrySet()){
						String ckey = entry.getKey();
						initOrderTitle(orderOldRl, ckey+"年度"); //初始化标题
						List<OrderCourse> cList = entry.getValue();
						initMyOrderClassAdapter(cList,orderOldRl); //初始化listview
						//如果同时存在相同年度的教练订单，需要放到一起
						if(isHaveTeach){
							for(Map<String, List<OrderTeach>> teachMap : teachList){
								List<OrderTeach> tList = teachMap.get(ckey);
								if(tList != null){
									initMyOrderTeachAdapter(tList,orderOldRl); //初始化listview
									//教练添加完后进行移除，因为可能会存在课程与教练数量不一致的情况，如果出现该情况，需要再把剩余的教练加载到列表
									teachMap.remove(tList); 
								}
							}
						}
					}
				}
				//课程循环完毕，再去看是否有剩余的只有教练，没有课程的年度预约，如果有，继续循环
				if(teachList != null && teachList.size() > 0){
					
				}
				
			}
		}
	}
	
	/**
	* <p>Description: 初始化年度标题</p>
	* @author raopeng
	* @date 2016-10-21 上午10:47:08
	 */
	@SuppressLint("ResourceAsColor")
	private void initOrderTitle(LinearLayout ll,String title){
		//添加标题
		RelativeLayout v = new RelativeLayout(this);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		v.setLayoutParams(params);
		v.setBackgroundColor(R.color.gray_ED);
		
		TextView tv1= new TextView(this);
		tv1.setPadding(20, 20, 0, 20);
		tv1.setText(title);
		tv1.setTextSize(14);
		v.addView(tv1);
		ll.addView(v);
	}
	
	/**
	* <p>Description: 新建listview通用方法提炼</p>
	* @author raopeng
	* @date 2016-10-21 上午10:26:49
	 */
	private void initMyOrderClassAdapter(List<OrderCourse> cList,LinearLayout ll){
		ListView lv = new ListView(this);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
			}
		});
		MyOrderClassAdapter adpater = new MyOrderClassAdapter(this, cList);
		lv.setAdapter(adpater);
		ll.addView(lv);
		
		CommonUtil.setListViewHeight(lv);
	}
	/**
	* <p>Description: 新建listview通用方法提炼</p>
	* @author raopeng
	* @date 2016-10-21 上午10:26:49
	 */
	private void initMyOrderTeachAdapter(List<OrderTeach> tList,LinearLayout ll){
		ListView lv = new ListView(this);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
			}
		});
		MyOrderCoachAdapter adpater = new MyOrderCoachAdapter(this, tList);
		lv.setAdapter(adpater);
		ll.addView(lv);
		
		CommonUtil.setListViewHeight(lv);
	}
	
	/**
	* <p>Description: 分割线添加通用方法，linearlayout</p>
	* @author raopeng
	* @date 2016-10-21 上午10:30:07
	 */
	@SuppressLint("ResourceAsColor")
	private void initSplitLine(LinearLayout ll){
		//添加分割线
		RelativeLayout v = new RelativeLayout(this);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,1);
		params.setMargins(0, 10, 0, 10);
		v.setLayoutParams(params);
		v.setBackgroundColor(R.color.black);
		ll.addView(v);
	}
	
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
