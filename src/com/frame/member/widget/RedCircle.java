package com.frame.member.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.frame.member.R;
import com.frame.member.Utils.CommonUtil;

public class RedCircle extends View {

	private int radiusCircle;

	private String radiusColor;
	
	private Paint paint;

	public RedCircle(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.RedCircle);

		radiusColor = ta.getString(R.styleable.RedCircle_colorCircle);
		radiusCircle = ta.getInteger(R.styleable.RedCircle_radiusCircle, 2);

		ta.recycle();
		
		radiusCircle = CommonUtil.dip2px(context, radiusCircle);
		if(TextUtils.isEmpty(radiusColor))
			radiusColor = "#FC5756";
		
		paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.parseColor(radiusColor));
		paint.setAntiAlias(true);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(radiusCircle * 2, radiusCircle * 2);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, radiusCircle, paint);
	}
}
