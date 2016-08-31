package com.frame.member.activity;

import java.util.ArrayList;
import java.util.List;
import com.frame.member.R;
import com.frame.member.adapters.FriendsVideoAdapter;
import com.frame.member.adapters.CoachSearchAdapter.ImageAndText;
import com.frame.member.adapters.FriendsPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
/**
 * 雪友空间
 * @author long
 *
 */
public class FriendsSpaceActivity extends BaseActivity {
	private ListView lv_space_friends;
	private FriendsVideoAdapter adapter;
	private ViewPager viewPager_space_friends;
	private FriendsPagerAdapter adapter_pager;
	private ImageView iv_friends_background;
	private ImageView iv_dot_item_1,iv_dot_item_2;
	
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
	}
	
	@Override
	protected void setListener() {
		
	}

	@Override
	protected void processLogic() {
		iv_title_back.setImageResource(R.drawable.btn_back_normal);
		tv_title.setText("雪友空间");
		iv_friends_background.setColorFilter(0x90000000);
		//设置ViewPager内容
		View view_1 = getLayoutInflater().inflate(R.layout.viewpager_friends_item_1, null);
		View view_2 = getLayoutInflater().inflate(R.layout.viewpager_friends_item_2, null);
		List<View> list_view = new ArrayList<View>();
		list_view.add(view_1);
		list_view.add(view_2);
		adapter_pager = new FriendsPagerAdapter(list_view);
		viewPager_space_friends.setAdapter(adapter_pager);
		viewPager_space_friends.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				if(arg0 == 0){
					iv_dot_item_1.setImageResource(R.drawable.dot_friends_space_current);
					iv_dot_item_2.setImageResource(R.drawable.dot_friends_space);
				}else{
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
		//设置ListView内容
		List<ImageAndText> list = new ArrayList<ImageAndText>(); 
		list.add(new ImageAndText(R.drawable.coach_profile, "老李"));
		list.add(new ImageAndText(R.drawable.coach_profile, "老孙"));
		list.add(new ImageAndText(R.drawable.coach_profile, "老王"));
		adapter = new FriendsVideoAdapter(this, list);
		lv_space_friends.setAdapter(adapter);
		
		
		
	}
}
