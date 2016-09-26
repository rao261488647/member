package com.frame.member.frag;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.MyCenterParser;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.activity.MainActivity;
import com.frame.member.activity.ModifyNickNameActivity;
import com.frame.member.activity.MyBillActivity;
import com.frame.member.activity.MyCollectActivity;
import com.frame.member.activity.MyInfoActivity;
import com.frame.member.activity.MyMsgActivity;
import com.frame.member.activity.SettingsActivity;
import com.frame.member.adapters.MyCenterPagerAdapter;
import com.frame.member.bean.MyCenterBean.Menu;
import com.frame.member.bean.MyCenterBean.MyCenterResult;
import com.frame.member.bean.MyCenterBean.User;
import com.ta.utdid2.android.utils.StringUtils;

/**
 * 
 * 个人中心
 * 
 * @author Ron
 * 
 * 
 */
public class MyCenterFrag extends BaseFrag implements OnClickListener {


	private ImageView settingView;
	private TextView myInfoBtn; //编辑个人信息
	private RelativeLayout my_layout_msg;//消息
	private RelativeLayout my_layout_collection;//我的收藏
	private RelativeLayout my_layout_cost;//消费流水
	private ViewPager mPager;  
	private ArrayList<Fragment> fragmentList; 
	private int currIndex;//当前页卡编号  
    private int bmpW;//横线图片宽度  
    private int offset;//图片移动的偏移量  
	private TextView grade,level,point,name,sign; //个人中心上部分的数据
	private ImageView my_center_tag_1,my_center_tag_2; //轮播头部下面的原点
	
	private ImageView headimg;//头像
	private User user;

	public static MyCenterFrag newInstance(String title) {
		MyCenterFrag fragment = new MyCenterFrag();
		Bundle bundle = new Bundle();
		bundle.putString(AppConstants.FRAG_TITLE_KEY, title);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.frag_my, container, false);

		tv_title = (TextView) findViewById(R.id.my_title);
		tv_title.setText("我的");

		findViewByIds();
		setOnClickListener();
		getData();
		
		return rootView;
	}

	BaseParser<MyCenterResult> parser = new MyCenterParser();
	/**
	 * 获取个人中心首页数据
	 * @author Ron
	 * @date 2016-9-19  上午12:01:27
	 */
	private void getData() {
		String url = AppConstants.APP_SORT_STUDENT+"/mycenter";
		HttpRequestImpl request = new HttpRequestImpl(getActivity(),
				url, parser,HttpRequest.RequestMethod.post);
		request.addParam("token", (String) SPUtils.getAppSpUtil().get(getActivity(), SPUtils.KEY_TOKEN, ""));
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(getActivity(), SPUtils.KEY_MEMBERUSERID, "")); //用户id 
		mContext.getDataFromServer(request, callback);
	}
	
	/**
	 * 回调方法
	 */
	private DataCallback<MyCenterResult> callback = new DataCallback<MyCenterResult>() {

		@Override
		public void processData(final MyCenterResult object, RequestResult result) {
			if(result == RequestResult.Success){
				if(object != null){
					if("200".equals(object.code)){
						//加载数据到页面
//						initMyCenterPage(object.user, object.menuList);
						user = object.user;
						InitViewPager();
					}
				}
			}
		}
	};
	/**
     * 所有的Activity对象的返回值都是由这个方法来接收
     * requestCode:    表示的是启动一个Activity时传过去的requestCode值
     * resultCode：表示的是启动后的Activity回传值时的resultCode值
     * data：表示的是启动后的Activity回传过来的Intent对象
     */
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1001){
        	Bundle bundle = data.getExtras(); 
            String names = bundle.getString("name");
            String signature = bundle.getString("signature");
            name.setText(names);
            sign.setText(signature);
            
        }
    }
	/**
	 * 通过从接口返回的数据初始化个人中心页面
	 * @author Ron
	 * @date 2016-9-19  上午12:47:11
	 * @param user
	 * @param menu
	 */
	private void initMyCenterPage(User user,List<Menu> menu){
		if(user != null){
			if(!StringUtils.isEmpty(user.appHeadThumbnail)){
				TTApplication.getInstance()
				.disPlayImageDef(user.appHeadThumbnail, headimg);
			}
			grade.setText("LV "+user.memberGrade);
			if(user.memberlLevel.equals("0")){
				level.setText("非会员");
			}else if(user.memberlLevel.equals("1")){
				level.setText("VIP绿卡");
			}else if(user.memberlLevel.equals("2")){
				level.setText("VIP蓝卡");
			}else if(user.memberlLevel.equals("3")){
				level.setText("VIP黑卡");
			}
			point.setText(user.memberPoints+" 积分");
			if(!StringUtils.isEmpty(user.memberName)){
				name.setText(user.memberName);
			}
			if(!StringUtils.isEmpty(user.memberSign)){
				sign.setText(user.memberSign);
			}
		}
	}
	
	/* 
     * 初始化ViewPager 
     */  
    public void InitViewPager(){  
        mPager = (ViewPager)findViewById(R.id.my_center_pager);  
        fragmentList = new ArrayList<Fragment>();  
        Fragment one= new MyCenterHeadOneFrag(user);  
        Fragment two = new MyCenterHeadTwoFrag(user);  
        fragmentList.add(one);  
        fragmentList.add(two);  
        //给ViewPager设置适配器  
        mPager.setAdapter(new MyCenterPagerAdapter(getFragmentManager(), fragmentList));  
        mPager.setCurrentItem(0);//设置当前显示标签页为第一页  
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器  
        
        //加载viewpager管理的fragment里的控件
		FragmentPagerAdapter adapter = (FragmentPagerAdapter) mPager.getAdapter();
		MyCenterHeadOneFrag frag = (MyCenterHeadOneFrag) adapter.instantiateItem(mPager, 0);
		MyCenterHeadTwoFrag frag2 = (MyCenterHeadTwoFrag) adapter.instantiateItem(mPager, 1);
	    if(frag != null){
	    	name = (TextView)frag.getView().findViewById(R.id.my_center_name);
	    }
	    if(frag2 != null){
	    	sign = (TextView)findViewById(R.id.my_center_signature);
	    }
        
    } 
	
    public class MyOnPageChangeListener implements OnPageChangeListener{  
        private int one = offset *2 +bmpW;//两个相邻页面的偏移量  
          
        @Override  
        public void onPageScrolled(int arg0, float arg1, int arg2) {  
            // TODO Auto-generated method stub  
              
        }  
          
        @Override  
        public void onPageScrollStateChanged(int arg0) {  
            // TODO Auto-generated method stub  
              
        }  
          
        @Override  
        public void onPageSelected(int arg0) {  
//            Animation animation = new TranslateAnimation(currIndex*one,arg0*one,0,0);//平移动画  
//            currIndex = arg0;  
//            animation.setFillAfter(true);//动画终止时停留在最后一帧，不然会回到没有执行前的状态  
//            animation.setDuration(200);//动画持续时间0.2秒  
            
        	if(arg0 == 0){
        		my_center_tag_1.setBackgroundResource(R.drawable.my_write_round);
        		my_center_tag_2.setBackgroundResource(R.drawable.my_gray_round);
        	}else{
        		my_center_tag_1.setBackgroundResource(R.drawable.my_gray_round);
        		my_center_tag_2.setBackgroundResource(R.drawable.my_write_round);
        	}
        	
//            Log.e("个人中心-viewpager", "当前tag----"+arg0);
        }  
    }  
	
	/**
	 * 初始化控件
	 * @author Ron
	 * @date 2016-8-3  上午1:02:25
	 */
	private void findViewByIds() {
		settingView = (ImageView)findViewById(R.id.my_settings);
		myInfoBtn = (TextView)findViewById(R.id.my_text2);
		my_layout_msg = (RelativeLayout)findViewById(R.id.my_layout_msg);
		my_layout_collection = (RelativeLayout)findViewById(R.id.my_layout_collection);
		my_layout_cost = (RelativeLayout)findViewById(R.id.my_layout_cost);
		
		grade = (TextView)findViewById(R.id.my_center_grade); //等级
		level = (TextView)findViewById(R.id.my_center_level); //会员等级
		point = (TextView)findViewById(R.id.my_center_point);
		
		headimg = (ImageView)findViewById(R.id.my_center_headimg);
//		Fragment fragment = getFragmentManager().findFragmentByTag(
//		"android:switcher:"+R.id.my_center_pager+":0");
//	    if(fragment != null)  // could be null if not instantiated yet
//	    {
//	         if(fragment.getView() != null) {
//	         }
//
//	    }
//		Fragment fragment = getFragmentManager().

		my_center_tag_1 = (ImageView) findViewById(R.id.my_center_tag_1);
		my_center_tag_2 = (ImageView) findViewById(R.id.my_center_tag_2);
		
	}
	/**
	 * 点击监听事件设置
	 * @author Ron
	 * @date 2016-8-11  上午12:56:56
	 */
	private void setOnClickListener(){
		settingView.setOnClickListener(this);
		myInfoBtn.setOnClickListener(this);
		my_layout_msg.setOnClickListener(this);
		my_layout_collection.setOnClickListener(this);
		my_layout_cost.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.my_settings://设置
			intent = new Intent(getActivity(), SettingsActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.my_text2: //个人基本信息
			intent = new Intent(getActivity(), MyInfoActivity.class);
//			intent.putExtra("name", name.getText().toString());
			startActivityForResult(intent,1001);
			break;
		case R.id.my_layout_msg: //消息
			intent = new Intent(getActivity(), MyMsgActivity.class);
			this.startActivity(intent);
			break;
		case R.id.my_layout_collection: //我的收藏
			intent = new Intent(getActivity(), MyCollectActivity.class);
			this.startActivity(intent);
			break;
		case R.id.my_layout_cost: //消费流水
			intent = new Intent(getActivity(), MyBillActivity.class);
			this.startActivity(intent);
			break;
//		case R.id.rl_my_logout:
//			CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
//			builder.setTitle("提示");
//			builder.setMessage("确定要退出？");
//			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					Intent myLogout = new Intent(getActivity(), LoginActivity.class);
//					getActivity().startActivity(myLogout);
//					
//					dialog.dismiss();
//					getActivity().finish();
//				}
//			});
//			builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
//
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
//					dialog.dismiss();
//				}
//			});
//			builder.create().show();
//			
//			break;
		}
	}
}
