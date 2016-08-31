package com.frame.member.activity;

import java.util.ArrayList;
import java.util.List;

import com.frame.member.R;
import com.frame.member.adapters.CoachReviewsAdapter;
import com.frame.member.adapters.CoachSearchAdapter.ImageAndText;

import android.widget.ListView;
/**
 * 教练主页
 * @author long
 *
 */
public class CoachSpaceActivity extends BaseActivity {
	private ListView lv_coach_reviews;
	private CoachReviewsAdapter mAdapter;
	private List<ImageAndText> list_reviews;
	
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_space_coach);
	}

	@Override
	protected void findViewById() {
		lv_coach_reviews = (ListView) findViewById(R.id.lv_coach_reviews);
	}
	
	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
		iv_title_back.setImageResource(R.drawable.btn_back_normal);
		tv_title.setText("教练主页");
		tv_title_right.setText("√已关注");
		list_reviews = new ArrayList<ImageAndText>();
		list_reviews.add(new ImageAndText(R.drawable.profile_friends, "阴三儿"));
		list_reviews.add(new ImageAndText(R.drawable.profile_friends, "赵四"));
		list_reviews.add(new ImageAndText(R.drawable.profile_friends, "王五"));
		mAdapter = new CoachReviewsAdapter(this, list_reviews);
		lv_coach_reviews.setAdapter(mAdapter);
	}
}
