package com.frame.member.widget;

import com.frame.member.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

public class CondenSortPopupWindow extends PopupWindow {
	 private Button  btn_conden_photo, btn_conden_video;  
	    private View mMenuView;  
	    public CondenSortPopupWindow(Activity context,OnClickListener itemsOnClick) {  
	        super(context); 
	        LayoutInflater inflater = (LayoutInflater) context  
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
//	        mMenuView = inflater.inflate(R.layout.popupwindow_conden_sort, null);  
//	        btn_conden_photo = (Button) mMenuView.findViewById(R.id.btn_conden_photo);  
//	        btn_conden_video = (Button) mMenuView.findViewById(R.id.btn_conden_video);  
	        //取消按钮
//	        btn_cancel.setOnClickListener(new OnClickListener() {  
//	            public void onClick(View v) {  
//	                //销毁弹出框
//	                dismiss();  
//	            }  
//	        });  
	        //设置按钮监听
	        btn_conden_photo.setOnClickListener(itemsOnClick);  
	        btn_conden_video.setOnClickListener(itemsOnClick);  
	        //设置SelectPicPopupWindow的View
	        this.setContentView(mMenuView);  
	        //设置SelectPicPopupWindow弹出窗体的宽
	        this.setWidth(LayoutParams.FILL_PARENT);  
	        //设置SelectPicPopupWindow弹出窗体的高
	        this.setHeight(LayoutParams.WRAP_CONTENT);  
	        //设置SelectPicPopupWindow弹出窗体可点击
	        this.setFocusable(true);  
	        //设置SelectPicPopupWindow弹出窗体动画效果
//	        this.setAnimationStyle(R.style.AnimBottom);  
	        //实例化一个ColorDrawable颜色为半透明
	        ColorDrawable dw = new ColorDrawable(0xb0000000);  
	        //设置SelectPicPopupWindow弹出窗体的背景
	        this.setBackgroundDrawable(dw);  
	        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
//	        mMenuView.setOnTouchListener(new OnTouchListener() {  
//	            public boolean onTouch(View v, MotionEvent event) {  
//	                int height = mMenuView.findViewById(R.id.pop_layout).getTop();  
//	                int y=(int) event.getY();  
//	                if(event.getAction()==MotionEvent.ACTION_UP){  
//	                    if(y<height){  
//	                        dismiss();  
//	                    }  
//	                }                 
//	                return true;  
//	            }

//	        });  
	    }  
}
