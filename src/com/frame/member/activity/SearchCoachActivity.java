package com.frame.member.activity;

import java.util.ArrayList;
import java.util.List;
import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.CoachSearchParser;
import com.frame.member.Parsers.IntroduceAttentionParser;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.adapters.CoachSearchAdapter;
import com.frame.member.adapters.CoachSearchResultAdapter;
import com.frame.member.bean.CoachSearchResult;
import com.frame.member.bean.IntroduceAttentionResult;
import com.frame.member.bean.IntroduceAttentionResult.Coach;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class SearchCoachActivity extends BaseActivity {

	private GridView gv_coach_suggest;
	private CoachSearchAdapter mAdapter;
	// private List<CoachSearchAdapter.ImageAndText> mList =
	// new ArrayList<CoachSearchAdapter.ImageAndText>();
	private List<Coach> list_coach = new ArrayList<Coach>();
	private EditText et_search_coach;
	private RelativeLayout rl_introduce_coach;
	private ScrollView sv_search_coach;
	private ListView lv_search_coach;
	private CoachSearchResultAdapter mAdapter_result;
	private List<CoachSearchResult> list_result = new ArrayList<CoachSearchResult>();

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_search_coach);
	}

	@Override
	protected void findViewById() {
		gv_coach_suggest = (GridView) findViewById(R.id.gv_coach_suggest);
		et_search_coach = (EditText) findViewById(R.id.et_search_coach);
		lv_search_coach = (ListView) findViewById(R.id.lv_search_coach);
		rl_introduce_coach = (RelativeLayout) findViewById(R.id.rl_introduce_coach);
		sv_search_coach = (ScrollView) findViewById(R.id.sv_search_coach);
	}

	@Override
	protected void setListener() {
		//给EditText添加监听，每次修改内容的时候调用搜索教练接口
		et_search_coach.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
				if(TextUtils.isEmpty(et_search_coach.getText().toString())){
					rl_introduce_coach.setVisibility(View.VISIBLE);
					sv_search_coach.setVisibility(View.GONE);
				}else{
					getSearchData();
					rl_introduce_coach.setVisibility(View.GONE);
					sv_search_coach.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	@Override
	protected void processLogic() {
		tv_title.setText("教练搜索");
		iv_title_back.setImageResource(R.drawable.btn_back_normal);

		// 通过SpannableString来实现图文格式的hint
		final SpannableString ss = new SpannableString("ss 请输入教练姓名");
		// 得到drawable对象，即所要插入的图片
		Drawable d = getResources().getDrawable(R.drawable.coach_search);
		d.setBounds(0, 0, 32, 32);
		// 用这个drawable对象代替字符串ss
		ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
		ss.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		et_search_coach.setHint(ss);
		//获得推荐教练数据
		getRandCoach();
		
	}

	// 获得搜索的教练数据
	private void getSearchData() {
		BaseParser<List<CoachSearchResult>> parser = new CoachSearchParser();
		HttpRequestImpl request = new HttpRequestImpl(this, AppConstants.APP_SORT_STUDENT + "/coachseach", parser);
		request.addParam("coachName", et_search_coach.getText().toString());
		DataCallback<List<CoachSearchResult>> callBack = new DataCallback<List<CoachSearchResult>>() {

			@Override
			public void processData(List<CoachSearchResult> object, RequestResult result) {
				if (object != null) {
					list_result.clear();
					list_result.addAll(object);
					notifySearchAdapter();
				}else{
					list_result.clear();
					notifySearchAdapter();
				}
			}
		};
		getDataFromServer(request,false, callBack);
	}
	private void notifySearchAdapter(){
		if(mAdapter_result == null){
			mAdapter_result = new CoachSearchResultAdapter(this, list_result);
			lv_search_coach.setAdapter(mAdapter_result);
			lv_search_coach.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Intent intent = new Intent(SearchCoachActivity.this,CoachDetailActivity.class);
					intent.putExtra("coachId", list_result.get(position).coachId);
					startActivity(intent);
					
				}
			});
		}else{
			mAdapter_result.notifyDataSetChanged();
		}
	}

	// 随机教练接口
	private void getRandCoach() {
		BaseParser<IntroduceAttentionResult> parser = new IntroduceAttentionParser();
		HttpRequestImpl request = new HttpRequestImpl(this, AppConstants.APP_SORT_STUDENT + "/randcoach", parser);
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""));
		DataCallback<IntroduceAttentionResult> callBack = new DataCallback<IntroduceAttentionResult>() {

			@Override
			public void processData(IntroduceAttentionResult object, RequestResult result) {
				if (object != null) {
					list_coach.clear();
					list_coach.addAll(object.list_coach);
					notifyIntroduceAdapter();
				}
			}
		};
		getDataFromServer(request,false, callBack);
	}

	private void notifyIntroduceAdapter() {
		if (mAdapter == null) {
			mAdapter = new CoachSearchAdapter(this, list_coach);
			gv_coach_suggest.setAdapter(mAdapter);
		} else {
			mAdapter.notifyDataSetChanged();
		}
	}

}
