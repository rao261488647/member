package com.frame.member.activity;

import java.util.ArrayList;
import java.util.List;
import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.FriendSpaceParser;
import com.frame.member.Parsers.FriendsInfoParser;
import com.frame.member.Parsers.NoBackParser;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.adapters.FriendsVideoAdapter;
import com.frame.member.bean.BaseBean;
import com.frame.member.bean.FriendSpaceResult;
import com.frame.member.bean.FriendsInfoResult;
import com.frame.member.adapters.FriendsPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 雪友空间
 * 
 * @author long
 *
 */
public class FriendsSpaceActivity extends BaseActivity {
	
	public static final String TAG_FRIEND_ID = "friendId";
	
	private ListView lv_space_friends;
	private FriendsVideoAdapter adapter;
	private ViewPager viewPager_space_friends;
	private FriendsPagerAdapter adapter_pager;
	private ImageView iv_friends_background;
	private ImageView iv_dot_item_1, iv_dot_item_2;
	// ViewPager中的布局控件 item1
	private ImageView iv_friends_profile;
	private TextView tv_name_friends, tv_level_friends;
	// ViewPager中的布局控件 item2
	private TextView tv_friends_info;
	private View view_1, view_2;

	private TextView tv_add_attention;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_space_friends);
	}

	@Override
	protected void findViewById() {
		lv_space_friends = (ListView) findViewById(R.id.lv_space_friends);
		viewPager_space_friends = (ViewPager) findViewById(R.id.viewPager_space_friends);
		iv_friends_background = (ImageView) findViewById(R.id.iv_friends_background);
		iv_dot_item_1 = (ImageView) findViewById(R.id.iv_dot_item_1);
		iv_dot_item_2 = (ImageView) findViewById(R.id.iv_dot_item_2);
		tv_add_attention = (TextView) findViewById(R.id.tv_add_attention);
	}

	@Override
	protected void setListener() {
		tv_add_attention.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toAttention(getIntent().getStringExtra("friendId"), v);
			}
		});
	}

	@Override
	protected void processLogic() {
		iv_title_back.setImageResource(R.drawable.btn_back_normal);
		tv_title.setText("雪友空间");
		iv_friends_background.setColorFilter(0x90000000);
		// 设置ViewPager内容
		view_1 = getLayoutInflater().inflate(R.layout.viewpager_friends_item_1, null);
		view_2 = getLayoutInflater().inflate(R.layout.viewpager_friends_item_2, null);
		// view_1中控件初始化
		iv_friends_profile = (ImageView) view_1.findViewById(R.id.iv_friends_profile);
		tv_name_friends = (TextView) view_1.findViewById(R.id.tv_name_friends);
		tv_level_friends = (TextView) view_1.findViewById(R.id.tv_level_friends);
		// view_2中控件初始化
		tv_friends_info = (TextView) view_2.findViewById(R.id.tv_friends_info);

		// //设置ListView内容
		// List<ImageAndText> list = new ArrayList<ImageAndText>();
		// list.add(new ImageAndText(R.drawable.coach_profile, "老李"));
		// list.add(new ImageAndText(R.drawable.coach_profile, "老孙"));
		// list.add(new ImageAndText(R.drawable.coach_profile, "老王"));
		// adapter = new FriendsVideoAdapter(this, list);
		// lv_space_friends.setAdapter(adapter);
		//
		getFriendsInfo();
		getFriendsSpace();

	}

	// pager中的View数据
	List<View> list_view = new ArrayList<View>();

	// 获取雪友信息接口
	private void getFriendsInfo() {
		BaseParser<FriendsInfoResult> parser = new FriendsInfoParser();
		HttpRequestImpl request = new HttpRequestImpl(this, AppConstants.APP_SORT_STUDENT + "/friendinfo", parser);
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("friendId", getIntent().getStringExtra("friendId"));
		getDataFromServer(request, callBack);
	}

	private DataCallback<FriendsInfoResult> callBack = new DataCallback<FriendsInfoResult>() {

		@Override
		public void processData(FriendsInfoResult object, RequestResult result) {
			if (object != null) {
				TTApplication.getInstance().disPlayImageDef(object.appHeadThumbnail, iv_friends_profile);
				tv_name_friends.setText(object.memberName);
				tv_level_friends.setText("LV." + object.memberlLevel);
				tv_friends_info.setText(object.memberSign);
				list_view.add(view_1);
				list_view.add(view_2);
				adapter_pager = new FriendsPagerAdapter(list_view);
				viewPager_space_friends.setAdapter(adapter_pager);
				viewPager_space_friends.setOnPageChangeListener(new OnPageChangeListener() {

					@Override
					public void onPageSelected(int arg0) {
						if (arg0 == 0) {
							iv_dot_item_1.setImageResource(R.drawable.dot_friends_space_current);
							iv_dot_item_2.setImageResource(R.drawable.dot_friends_space);
						} else {
							iv_dot_item_1.setImageResource(R.drawable.dot_friends_space);
							iv_dot_item_2.setImageResource(R.drawable.dot_friends_space_current);
						}
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {

					}

					@Override
					public void onPageScrollStateChanged(int arg0) {

					}
				});
				if ("1".equals(object.followFriend)) {
					tv_add_attention.setText("√已关注");
				} else {
					tv_add_attention.setText("关注ta");
				}

			}
		}
	};

	// 关注\取消关注friends接口
	private void toAttention(String friendId, final View v) {
		int status;
		if ("√已关注".equals(((TextView) v).getText().toString())) {
			status = 0;
		} else {
			status = 1;
		}
		BaseParser<BaseBean> parser = new NoBackParser();
		HttpRequestImpl request = new HttpRequestImpl(this, AppConstants.APP_SORT_STUDENT + "/followfriend", parser);
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("friendId", friendId).addParam("status", "" + status)
				.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""));
		DataCallback<BaseBean> callBack = new DataCallback<BaseBean>() {

			@Override
			public void processData(BaseBean object, RequestResult result) {
				if (object != null) {
					if ("√已关注".equals(((TextView) v).getText().toString())) {
						((TextView) v).setText("关注ta");
					} else {
						((TextView) v).setText("√已关注");
					}
				}
			}
		};
		getDataFromServer(request, false, callBack);

	}

	private List<FriendSpaceResult> list_friends = new ArrayList<FriendSpaceResult>();

	// 获取雪友视频列表数据
	private void getFriendsSpace() {
		BaseParser<List<FriendSpaceResult>> parser = new FriendSpaceParser();
		HttpRequestImpl request = new HttpRequestImpl(
				this, AppConstants.APP_SORT_STUDENT + "/friendspace", parser);
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("friendId", getIntent().getStringExtra("friendId"));
		DataCallback<List<FriendSpaceResult>> callBack = new DataCallback<List<FriendSpaceResult>>() {

			@Override
			public void processData(List<FriendSpaceResult> object, RequestResult result) {
				if (object != null) {
					list_friends.clear();
					list_friends.addAll(object);
					notifyAdapter();
				}
			}
		};
		getDataFromServer(request, false, callBack);
	}

	private void notifyAdapter() {
		if (adapter == null) {
			adapter = new FriendsVideoAdapter(FriendsSpaceActivity.this, list_friends,
					getIntent().getStringExtra("friendId"));
			lv_space_friends.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
	}

}
