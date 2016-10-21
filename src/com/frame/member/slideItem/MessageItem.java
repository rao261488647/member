package com.frame.member.slideItem;

import com.frame.member.bean.MyCollectBean.CollectCoach;

/**
 * 
 * item显示信息的类，包含删除按钮，要显示的信息
 * 
 */
public class MessageItem {
	private CollectCoach collectCoach;
	private SlideView slideView;

	public SlideView getSlideView() {
		return slideView;
	}

	public void setSlideView(SlideView slideView) {
		this.slideView = slideView;
	}

	public CollectCoach getCollectCoach() {
		return collectCoach;
	}

	public void setCollectCoach(CollectCoach collectCoach) {
		this.collectCoach = collectCoach;
	}
	

}
