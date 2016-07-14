package com.frame.member.activity;
/**
 *  流水列表
 *  @author hanshengkun
 */
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.widget.ListView;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.StatementsListParser;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.adapters.StatementsListAdapter;
import com.frame.member.bean.StatementsListBean;

public class StatementsListActivity extends BaseActivity {
	
	private ListView lv_statemetns_list;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_statements_list);
	}

	@Override
	protected void findViewById() {
		lv_statemetns_list = (ListView) findViewById(R.id.lv_statements_list);
		tv_title.setText("流水列表");
	}
	
	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
		iv_title_back.setVisibility(0);
		
		getStatemetnsList();
	}

	private void getStatemetnsList() {
		HttpRequest request_main_data = new HttpRequestImpl(
				StatementsListActivity.this, AppConstants.STATEMENTLIST, new StatementsListParser());

		Format format = new SimpleDateFormat("yyyy-MM");
		request_main_data
				.addParam(
						"tel",
						(String) SPUtils.getAppSpUtil().get(
								StatementsListActivity.this, SPUtils.KEY_PHONENUM, "")).addParam("date", format.format(new Date()));
		
		getDataFromServer(request_main_data, new DataCallback<StatementsListBean>() {
		
			@Override
			public void processData(StatementsListBean object, RequestResult result) {
				if (result == RequestResult.Success) {
					
					StatementsListAdapter adapter = new StatementsListAdapter(StatementsListActivity.this, object.statementsList);
					lv_statemetns_list.setAdapter(adapter);
				}
			}
		}, "加载中...");
	}

}
