package com.frame.member.activity;

import java.util.ArrayList;
import java.util.List;

import com.frame.member.R;
import com.frame.member.adapters.CoachSearchAdapter;
import com.frame.member.adapters.CoachSearchAdapter.ImageAndText;

import android.widget.GridView;

public class SearchCoachActivity extends BaseActivity{

	private GridView gv_coach_suggest;
	private CoachSearchAdapter mAdapter;
	private List<CoachSearchAdapter.ImageAndText> mList = 
			new ArrayList<CoachSearchAdapter.ImageAndText>();
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_search_coach);
	}

	@Override
	protected void findViewById() {
		gv_coach_suggest = (GridView) findViewById(R.id.gv_coach_suggest);
	}

	@Override
	protected void setListener() {
		
	}

	@Override
	protected void processLogic() {
		tv_title.setText("教练搜索");
		iv_title_back.setImageResource(R.drawable.btn_back_normal);
		mList.add(new ImageAndText(R.drawable.coach_profile,"王教练"));
		mList.add(new ImageAndText(R.drawable.coach_profile, "张教练"));
		mList.add(new ImageAndText(R.drawable.profile_example_1, "李教练"));
		mAdapter = new CoachSearchAdapter(this, mList);
		gv_coach_suggest.setAdapter(mAdapter);
	}

}
