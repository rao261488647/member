package com.frame.member.activity;
/**
 *  租车列表
 *  @author hanshengkun
 */
import android.widget.ListView;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.RentCarListParser;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.adapters.RentCarListAdapter;
import com.frame.member.bean.RentCarListBean;

public class RentCarActivity extends BaseActivity {
	
	private ListView lv_rentcar_list;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_rent_car);
	}

	@Override
	protected void findViewById() {
		lv_rentcar_list = (ListView) findViewById(R.id.lv_rentcar_list);
	}

	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
		iv_title_back.setVisibility(0);
		
		getRentCarList();
	}

	private void getRentCarList() {
		HttpRequest request_main_data = new HttpRequestImpl(
				RentCarActivity.this, AppConstants.RENTCARLIST, new RentCarListParser());

		request_main_data
				.addParam(
						"cell",
						(String) SPUtils.getAppSpUtil().get(
								RentCarActivity.this, SPUtils.KEY_PHONENUM, ""))
				.addParam("currentPage", 1 + "")
				.addParam("loadCount", 5 + "");

		getDataFromServer(request_main_data, new DataCallback<RentCarListBean>() {

			@Override
			public void processData(RentCarListBean object, RequestResult result) {
				if (result == RequestResult.Success) { }
			}
		}, "加载中...");
	}

}
