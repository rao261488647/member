package com.frame.member.interfaces;

public interface NotifyDataObserver {
	/**
	 * 
	 * @param isUnReadedExist true:有未读通知
	 */
	void onNotifyDataChanged(boolean isUnReadedExist);
}
