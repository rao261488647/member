package com.frame.member.activity;

import java.util.ArrayList;
import java.util.List;

/**
 *  会员简介
 *  @author long
 */
import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.MemberInfoParser;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.adapters.FriendsPagerAdapter;
import com.frame.member.bean.MemberInfoResult;
import com.frame.member.frag.MemberInfoDialogFrag;

import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
 * 
 * 会员简介
 */
public class MemberInfoActivity extends BaseActivity {

	private ViewPager viewpager_member_info;
	private FriendsPagerAdapter mAdapter;
	private List<View> list_view = new ArrayList<View>();
	private View view_1;
	private View view_2;
	private View view_3;
	private TextView tv_login_button,tv_info_members;
	private LinearLayout ll_dots;
	public int current_dot = 0;
	private int status_member = 0;// 0为非会员，1为绿卡，2为蓝卡，3为黑卡
	public String price;//升级需要的价格
	public MemberInfoResult result_info = new MemberInfoResult();
	
	

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_member_info);
	}

	@Override
	protected void findViewById() {
		viewpager_member_info = (ViewPager) findViewById(R.id.viewpager_member_info);
		tv_login_button = (TextView) findViewById(R.id.tv_login_button);
		tv_info_members = (TextView) findViewById(R.id.tv_info_members);
		ll_dots = (LinearLayout) findViewById(R.id.ll_dots);
	}

	@Override
	protected void setListener() {
		tv_login_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(current_dot == 1){
					price = result_info.flowerRank;
				}else if(current_dot == 2 && ("0".equals(result_info.level)||"1".equals(result_info.level))){//非会员和绿卡
					price = result_info.vipRank;
				}else if(current_dot == 2 && "2".equals(result_info.level)){//蓝卡
					price = result_info.blueRank;
				}
				MemberInfoDialogFrag frag = new MemberInfoDialogFrag();
				frag.setStyle(DialogFragment.STYLE_NORMAL, R.style.YouDialog);
				frag.show(getSupportFragmentManager(), "MemberInfoDialog");
				
			}
		});
	}

	@Override
	protected void processLogic() {
		tv_login_button.setVisibility(View.GONE);
		iv_title_back.setImageResource(R.drawable.btn_back_normal);
		tv_title.setText("会员简介");
		view_1 = getLayoutInflater().inflate(R.layout.viewpager_card_member, null);
		view_2 = getLayoutInflater().inflate(R.layout.viewpager_card_member, null);
		view_3 = getLayoutInflater().inflate(R.layout.viewpager_card_member, null);
		ImageView image_1 = (ImageView) view_1.findViewById(R.id.iv_card_member);
		image_1.setImageResource(R.drawable.card_green);
		ImageView image_2 = (ImageView) view_2.findViewById(R.id.iv_card_member);
		image_2.setImageResource(R.drawable.card_blue);
		ImageView image_3 = (ImageView) view_3.findViewById(R.id.iv_card_member);
		image_3.setImageResource(R.drawable.card_black);
		list_view.clear();
		list_view.add(view_1);
		list_view.add(view_2);
		list_view.add(view_3);
		mAdapter = new FriendsPagerAdapter(list_view);
		viewpager_member_info.setAdapter(mAdapter);
		viewpager_member_info.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				((ImageView) ll_dots.getChildAt(arg0)).setImageResource(R.drawable.dot_yellow);
				((ImageView) ll_dots.getChildAt(current_dot)).setImageResource(R.drawable.dot_gray);
				current_dot = arg0;
				switch (status_member) {
				case 0:
				case 1:
					switch (arg0) {
					case 0:
						tv_login_button.setVisibility(View.GONE);
						tv_info_members.setText(result_info.green);
						break;
					case 1:
						tv_login_button.setVisibility(View.VISIBLE);
						tv_login_button.setText("成为蓝卡会员");
						tv_info_members.setText(result_info.blue);
						break;
					case 2:
						tv_login_button.setVisibility(View.VISIBLE);
						tv_login_button.setText("成为黑卡会员");
						tv_info_members.setText(result_info.black);
						break;
					}
					break;
				case 2:
					switch (arg0) {
					case 0:
						tv_login_button.setVisibility(View.GONE);
						tv_info_members.setText(result_info.green);
						break;
					case 1:
						tv_login_button.setVisibility(View.GONE);
						tv_info_members.setText(result_info.blue);
						break;
					case 2:
						tv_login_button.setVisibility(View.VISIBLE);
						tv_login_button.setText("成为黑卡会员");
						tv_info_members.setText(result_info.black);
						break;
					}
					break;
				case 3:
					switch (arg0) {
					case 0:
						tv_login_button.setVisibility(View.GONE);
						tv_info_members.setText(result_info.green);
						break;
					case 1:
						tv_login_button.setVisibility(View.GONE);
						tv_info_members.setText(result_info.blue);
						break;
					case 2:
						tv_login_button.setVisibility(View.GONE);
						tv_info_members.setText(result_info.black);
						break;
					}
					break;

				}
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		
		getData();
		
	}

	// 获取主数据
	private void getData() {
		BaseParser<MemberInfoResult> parser = new MemberInfoParser();
		HttpRequestImpl request = new HttpRequestImpl(this, AppConstants.APP_SORT_STUDENT + "/myintroduction", parser);
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""));
		DataCallback<MemberInfoResult> callback = new DataCallback<MemberInfoResult>() {

			@Override
			public void processData(MemberInfoResult object, RequestResult result) {
				if (object != null) {
					result_info = object;
					if ("0".equals(object.level)) {
						status_member = 0;
					} else if ("1".equals(object.level)) {
						status_member = 1;
					} else if ("2".equals(object.level)) {
						status_member = 2;
					}
					tv_info_members.setText(object.green);
				}
			}
		};
		getDataFromServer(request, true, callback);
	}
}
