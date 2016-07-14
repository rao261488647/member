package com.frame.member.Utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

public class TimeCount extends CountDownTimer {

	TextView text;
	int num;
	Context context;
	
	private boolean isTicking;
	
	public boolean getIsTicking() {
		return isTicking;
	}

	public TimeCount(long millisInFuture, long countDownInterval,TextView text,int num ,Context context) {
		super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
		this.text = text;
		this.num = num;
		this.context = context;
	}

	@Override
	public void onTick(long millisUntilFinished) {//计时过程显示
		isTicking = true;
		switch (num) {
		case 0:
			text.setClickable(false);
			text.setText(millisUntilFinished /1000+"秒后重新获取");
			break;
		case 1:
			text.setText("倒计时：" + millisUntilFinished / 1000 + "秒");
			break;
		case 2:
			text.setText(millisUntilFinished / 1000 + "");
			break;
		}
		
	}
	
	

	@Override
	public void onFinish() {//计时完毕时触发
		MyLog.i("TimeCount", "onFinish");
		isTicking =false;
		switch (num) {
		case 0:
			text.setText("发送验证码");
			text.setClickable(true);
			break;
		case 1:
			break;
		case 2:
			break;

		}
		
	}


}
