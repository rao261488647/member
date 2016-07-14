package com.frame.member.Parsers;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.frame.member.bean.ContractTerm;
import com.frame.member.bean.RentCarListBean;
import com.frame.member.bean.RentCarListBean.RentCarItem;

public class RentCarListParser extends BaseParser<RentCarListBean> {

	@Override
	public RentCarListBean parseJSON(String text) throws JSONException {

		RentCarListBean result = new RentCarListBean();

		JSONObject resultObj = new JSONObject(text);
		if (resultObj != null) {
			result.code = resultObj.optString("code");
			result.message = resultObj.optString("message");

			if (TAG_STATUS_SUCC.equals(result.code)) {
				JSONArray dataArray = resultObj.optJSONArray("data");
				for (int i = 0; i < dataArray.length(); i++) {
					JSONObject dataObj = dataArray.optJSONObject(i);
					RentCarItem rentCarItem = new RentCarItem();
					rentCarItem.url = dataObj.optString("url");
					rentCarItem.carType = dataObj.optString("carType");
					rentCarItem.deposit = dataObj.optString("deposit");
					rentCarItem.rentPerPay = dataObj.optString("rentPerPay");
					rentCarItem.plat = dataObj.optString("plat");
					
					rentCarItem.rentCarId=dataObj.optString("rentCarId");

					JSONObject contractTermObj = dataObj
							.optJSONObject("contractTerm");

					if (contractTermObj != null) {
						Iterator<String> keyIterator = contractTermObj.keys();
						while (keyIterator.hasNext()) {
							ContractTerm contractTerm = new ContractTerm();
							String value = new String();
							String key = keyIterator.next();
							if("threeMon".equals(key)){
								value=contractTermObj.optString(key);
								key = "三个月";
								contractTerm.setContractTermKey(key);
								contractTerm.setContractTermVal(value);
							}else if("twoMon".equals(key)){
								value=contractTermObj.optString(key);
								key = "两个月";
								contractTerm.setContractTermKey(key);
								contractTerm.setContractTermVal(value);
							}else if("sixMon".equals(key)){
								value=contractTermObj.optString(key);
								key = "六个月";
								contractTerm.setContractTermKey(key);
								contractTerm.setContractTermVal(value);
							}else if("oneYear".equals(key)){
								value=contractTermObj.optString(key);
								key = "一年";
								contractTerm.setContractTermKey(key);
								contractTerm.setContractTermVal(value);
							}
							rentCarItem.contractTermList.add(contractTerm);
						}
					}

					result.rentCarList.add(rentCarItem);
					rentCarItem = null;

				}
			}
		}

		return result;
	}

}
