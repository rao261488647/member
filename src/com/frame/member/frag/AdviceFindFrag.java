package com.frame.member.frag;


import java.util.ArrayList;
import java.util.List;
import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.AdviceFindParser;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.CoachDetailParser;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.BaseActivity;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.adapters.AdviceFindAdapter;
import com.frame.member.adapters.CoachSearchAdapter.ImageAndText;
import com.frame.member.bean.AdviceFindResult;
import com.frame.member.bean.BaseBean;
import com.frame.member.bean.CoachDetailResult;
import com.frame.member.widget.refreshlistview.PullToRefreshBase;
import com.frame.member.widget.refreshlistview.PullToRefreshBase.Mode;
import com.frame.member.widget.refreshlistview.PullToRefreshListView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * 求教发现
 * 
 *
 */
public class AdviceFindFrag extends BaseFrag{
	
	private PullToRefreshListView lv_advice_find;
	private AdviceFindAdapter adapter;
	
	private static AdviceFindFrag mFrag;
	public static AdviceFindFrag newInstance() {
		if(mFrag == null)
			mFrag = new AdviceFindFrag();
        
        return mFrag;
    }
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.frag_advice_find, container,
				false);
		
		return rootView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
		getData();
		
	}
	private void init(){
		lv_advice_find =(PullToRefreshListView) findViewById(R.id.lv_advice_find);
//		List<ImageAndText> list = new ArrayList<ImageAndText>(); 
//		list.add(new ImageAndText(R.drawable.coach_profile, "老李"));
//		list.add(new ImageAndText(R.drawable.coach_profile, "老孙"));
//		list.add(new ImageAndText(R.drawable.coach_profile, "老王"));
//		adapter = new AdviceFindAdapter(mContext, list);
//		lv_advice_find.setAdapter(adapter);
		lv_advice_find.setMode(Mode.BOTH);
		lv_advice_find.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				page = 1;
				lv_advice_find.setMode(Mode.BOTH);
				getData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				page ++;
				getData();
			}
		});
		
	}
	private void getData(){
		if(page < 1)
			page = 1;
		BaseParser<List<AdviceFindResult>> parser = new AdviceFindParser();
		HttpRequestImpl request = new HttpRequestImpl(
				mContext, AppConstants.APP_SORT_STUDENT + "/found", parser);
		request.addParam("memberUserId", 
				(String) SPUtils.getAppSpUtil().get(mContext, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("page_size", 10+"")
				.addParam("page_num", page+"");
		mContext.getDataFromServer(request, callBack);
	}
	private int page;
	private List<AdviceFindResult> list_find = new ArrayList<AdviceFindResult>();
	private DataCallback<List<AdviceFindResult>> callBack = new DataCallback<List<AdviceFindResult>>() {

		@Override
		public void processData(List<AdviceFindResult> object, RequestResult result) {
			lv_advice_find.onRefreshComplete();
			if(object != null && object.size()> 0){
				if(page == 1){
					list_find.clear();
				}
				list_find.addAll(object);
				notifyAdapter();
			}else{
				if(page == 1){
					list_find.clear();
					notifyAdapter();
					return;
				}
				lv_advice_find.setMode(Mode.PULL_FROM_START);
			}
		}
	}; 
	
	private void notifyAdapter(){
		if(adapter == null){
			adapter = new AdviceFindAdapter(mContext, list_find);
			lv_advice_find.setAdapter(adapter);
		}else{
			adapter.notifyDataSetChanged();
		}
	}

	
	
	
	
	
	




}
