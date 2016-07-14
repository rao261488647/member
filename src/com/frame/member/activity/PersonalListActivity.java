package com.frame.member.activity;
/**
 *  个人列表
 *  @author hanshengkun
 */
import android.widget.ListView;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.PersonalListParser;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.adapters.PersonalListAdapter;
import com.frame.member.bean.PersonalListBean;

public class PersonalListActivity extends BaseActivity {
	
	private ListView lv_personal_list;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_personal_list);
	}

	@Override
	protected void findViewById() {
		lv_personal_list = (ListView) findViewById(R.id.lv_personal_list);
		tv_title.setText("个人信息");
	}
	
	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
		iv_title_back.setVisibility(0);
		
		getPersonalList();
	}

	private void getPersonalList() {
		HttpRequest request_main_data = new HttpRequestImpl(
				PersonalListActivity.this, AppConstants.PROFILEINFO, new PersonalListParser());

		request_main_data
				.addParam(
						"tel",
						(String) SPUtils.getAppSpUtil().get(PersonalListActivity.this, SPUtils.KEY_PHONENUM, ""));

		getDataFromServer(request_main_data, new DataCallback<PersonalListBean>() {
			@Override
			public void processData(PersonalListBean object, RequestResult result) {
				if (result == RequestResult.Success) {
					
					PersonalListAdapter adapter = new PersonalListAdapter(PersonalListActivity.this, object.personalList);
					lv_personal_list.setAdapter(adapter);
				}
			}
		}, "加载中...");
	}

}
