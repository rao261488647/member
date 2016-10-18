package com.frame.member.Dialog;

import com.frame.member.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class CustomDialog extends Dialog {
	Context context;
	
	private int layoutId;

	public CustomDialog(Context context,int layoutId) {
		super(context, R.style.FBDialog);
		this.context = context;
		this.layoutId = layoutId;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(layoutId);

	}
}