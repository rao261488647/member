package com.frame.member.receiver;

import java.util.HashMap;
import java.util.Map;

import com.frame.member.Utils.MyLog;

import android.text.TextUtils;

public class NotifyIdGenerater {

	private Map<String, Integer> ids = new HashMap<String, Integer>();

	private void NotifyIdGenerater() {}

	private static NotifyIdGenerater instance = new NotifyIdGenerater();

	public static NotifyIdGenerater getInstance() {
		return instance;
	}

	public int generateNotifyId(String date) {
		if (TextUtils.isEmpty(date))
			return 0;
		String dateformat = date.replace(" ", "").replaceAll(":", "").replace("-", "");
		int id = Integer.parseInt(dateformat.substring(4,13));
		try {
			if (!ids.containsKey(dateformat))
				ids.put(dateformat, id);
			return ids.get(dateformat);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public int getNotifyId(String date){
		String dateformat = date.replace(" ", "").replaceAll(":", "").replace("-", "");
		
		MyLog.i("MainAcitivity", dateformat);
		MyLog.i("MainAcitivity", ids.toString());
		
		return ids.get(dateformat);
	}

}
