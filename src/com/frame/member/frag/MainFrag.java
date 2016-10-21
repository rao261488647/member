package com.frame.member.frag;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.adapters.CondensationPagerAdapter;

public class MainFrag extends BaseFrag implements OnClickListener {

	GridView gv_conden;

	CondensationPagerAdapter pagerAdapter;
	LinearLayout ll_sort_conden_sport, ll_sort_conden_hotTopic,
			ll_sort_conden_classicAction;
	private FragmentManager fragmentManager ;

	protected TextView tv_title_left;

	private String curFragTag; //当前选中的 主页上面标签
	private String[] main_tabs;// 主页上面标签集合
	private TextView main_tab_1,main_tab_2,main_tab_3,main_tab_4;


	public static MainFrag newInstance(String title) {

		MainFrag fragment = new MainFrag();
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
		fragmentManager = this.getFragmentManager();
		main_tabs = getResources().getStringArray(R.array.main_top_tabs);
		
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		findViewByIds(); //获取控件
		restoreTitle(); //初始化 curFragTag
		changeFrag(); //先初始化一下 fragment
		
		super.onActivityCreated(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.frag_main, container,
				false);
		return rootView;
	}

	private void findViewByIds() {
		main_tab_1 = (TextView) findViewById(R.id.main_tab_1);
		main_tab_2 = (TextView) findViewById(R.id.main_tab_2);
		main_tab_3 = (TextView) findViewById(R.id.main_tab_3);
		main_tab_4 = (TextView) findViewById(R.id.main_tab_4);
		
		//设置单击监听事件
		main_tab_1.setOnClickListener(this);
		main_tab_2.setOnClickListener(this);
		main_tab_3.setOnClickListener(this);
		main_tab_4.setOnClickListener(this);
	}
	
	
	
	private void restoreTitle() {
		if (TextUtils.isEmpty(curFragTag)) {
			curFragTag = main_tabs[0];
		}
	}
	
	private void checkTabChange(int index) {
		if (main_tabs[index].equals(curFragTag))
			return;
		curFragTag = main_tabs[index];

		changeFrag();
	}
	
	/**
	 * 获取需要切换的fragment
	 * @author Ron
	 * @date 2016-6-28  上午12:38:29
	 * @return
	 */
	private BaseFrag genFragment() {
		if (main_tabs[0].equals(curFragTag)) // 资讯首页
			return MainInfoFrag.newInstance(curFragTag);
		else if (main_tabs[1].equals(curFragTag)) // 视频
			return MainVideoFrag.newInstance(curFragTag);
		else if (main_tabs[2].equals(curFragTag)) // 教程
			return MainCourseFrag.newInstance(curFragTag);
		else if (main_tabs[3].equals(curFragTag)) // 精华
			return MainEssenceFrag.newInstance(curFragTag);
		return null;
	}
	private BaseFrag mCurFrag;
	/**
	 * 切换fragment
	 * @author Ron
	 * @date 2016-6-28  上午12:38:11
	 */
	private void changeFrag() {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		if (mCurFrag != null){
			transaction.hide(mCurFrag);
		}
		mCurFrag = (BaseFrag) fragmentManager.findFragmentByTag(curFragTag);
		if (mCurFrag == null) {
			mCurFrag = genFragment();
			if(!(mCurFrag instanceof MainFrag))
				transaction.add(R.id.main_framelayout, mCurFrag, curFragTag).commit();
		} else {
			transaction.show(mCurFrag).commit();
		}
		changeTabBtnStatus();
	}
	
	/**
	 * 改变tab的状态
	 * @author Ron
	 * @date 2016-6-28  上午12:37:34
	 */
	@SuppressLint("ResourceAsColor")
	private void changeTabBtnStatus() {
		// 资讯首页
		if (main_tabs[0].equals(curFragTag)){
			main_tab_1.setTextColor(this.getResources().getColor(R.color.main_tab_check));
			main_tab_2.setTextColor(this.getResources().getColor(R.color.main_tab_uncheck));
			main_tab_3.setTextColor(this.getResources().getColor(R.color.main_tab_uncheck));
			main_tab_4.setTextColor(this.getResources().getColor(R.color.main_tab_uncheck));
			
			main_tab_1.setTextSize(24);
			main_tab_2.setTextSize(20);
			main_tab_3.setTextSize(20);
			main_tab_4.setTextSize(20);
			
		// 视频
		}else if (main_tabs[1].equals(curFragTag)){
			main_tab_1.setTextColor(this.getResources().getColor(R.color.main_tab_uncheck));
			main_tab_2.setTextColor(this.getResources().getColor(R.color.main_tab_check));
			main_tab_3.setTextColor(this.getResources().getColor(R.color.main_tab_uncheck));
			main_tab_4.setTextColor(this.getResources().getColor(R.color.main_tab_uncheck));
			
			main_tab_1.setTextSize(20);
			main_tab_2.setTextSize(24);
			main_tab_3.setTextSize(20);
			main_tab_4.setTextSize(20);
			
		// 教程
		}else if (main_tabs[2].equals(curFragTag)){
			main_tab_1.setTextColor(this.getResources().getColor(R.color.main_tab_uncheck));
			main_tab_2.setTextColor(this.getResources().getColor(R.color.main_tab_uncheck));
			main_tab_3.setTextColor(this.getResources().getColor(R.color.main_tab_check));
			main_tab_4.setTextColor(this.getResources().getColor(R.color.main_tab_uncheck));
			
			main_tab_1.setTextSize(20);
			main_tab_2.setTextSize(20);
			main_tab_3.setTextSize(24);
			main_tab_4.setTextSize(20);
		// 精华
		}else if (main_tabs[3].equals(curFragTag)){
			main_tab_1.setTextColor(this.getResources().getColor(R.color.main_tab_uncheck));
			main_tab_2.setTextColor(this.getResources().getColor(R.color.main_tab_uncheck));
			main_tab_3.setTextColor(this.getResources().getColor(R.color.main_tab_uncheck));
			main_tab_4.setTextColor(this.getResources().getColor(R.color.main_tab_check));
			
			main_tab_1.setTextSize(20);
			main_tab_2.setTextSize(20);
			main_tab_3.setTextSize(20);
			main_tab_4.setTextSize(24);
		}
			
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 注意点击的控件id
		case R.id.main_tab_1:
			checkTabChange(0);
			break;
		case R.id.main_tab_2:
			checkTabChange(1);
			break;
		case R.id.main_tab_3:
			checkTabChange(2);
			break;
		case R.id.main_tab_4:
			checkTabChange(3);
			break;
	
		}
	};

}
