package com.frame.member.frag;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.activity.MemberInfoActivity;
import com.frame.member.bean.MyCenterBean.Menu;
import com.frame.member.bean.MyCenterBean.User;
import com.ta.utdid2.android.utils.StringUtils;

public class MyCenterHeadOneFrag extends BaseFrag {
	
	private TextView grade,level,point,nickname; //个人中心上部分的数据
	private ImageView my_center_tag_1,my_center_tag_2; //轮播头部下面的原点
	
	private ImageView headimg;//头像
	
	private User user;
	private Context context;
	public MyCenterHeadOneFrag(User user,Context context){
		this.user = user;
		this.context = context;
	}
	
	
	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
        rootView = inflater.inflate(R.layout.frag_my_center_head_1, container, false);//关联布局文件  
        findViewByIds();
        initMyCenterPage();
//        setViewListener();
        return rootView;  
    }  
	
	
	/**
	 * 初始化控件
	 * @author Ron
	 * @date 2016-8-3  上午1:02:25
	 */
	private void findViewByIds() {
		
		grade = (TextView)findViewById(R.id.my_center_grade); //等级
		level = (TextView)findViewById(R.id.my_center_level); //会员等级
		point = (TextView)findViewById(R.id.my_center_point);
		nickname = (TextView)findViewById(R.id.my_center_name);
		headimg = (ImageView)findViewById(R.id.my_center_headimg);
		
		
	}
	/**
	 * 通过从接口返回的数据初始化个人中心页面
	 * @author Ron
	 * @date 2016-9-19  上午12:47:11
	 * @param user
	 * @param menu
	 */
	private void initMyCenterPage(){
		if(user != null){
			if(!StringUtils.isEmpty(user.appHeadThumbnail)){
				TTApplication.getInstance()
				.disPlayImageDef(user.appHeadThumbnail, headimg);
			}
			grade.setText("LV "+user.memberGrade);
//			if(user.memberlLevel.equals("0")){
//				level.setText("非会员");
//			}else if(user.memberlLevel.equals("1")){
//				level.setText("VIP绿卡");
//			}else if(user.memberlLevel.equals("2")){
//				level.setText("VIP蓝卡");
//			}else if(user.memberlLevel.equals("3")){
//				level.setText("VIP黑卡");
//			}
			point.setText(user.memberPoints+" 积分");
			if(!StringUtils.isEmpty(user.memberName)){
				nickname.setText(user.memberName);
			}
		}
	}
//	//设置点击事件
//	private void setViewListener(){
//		level.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				startActivity(new Intent(context,MemberInfoActivity.class));
//			}
//		});
//	}
}
