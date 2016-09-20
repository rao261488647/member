package com.frame.member.frag;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.bean.MyCenterBean.Menu;
import com.frame.member.bean.MyCenterBean.User;
import com.ta.utdid2.android.utils.StringUtils;

public class MyCenterHeadTwoFrag  extends BaseFrag {
	
	private TextView sign; //个人中心上部分的数据--
	
	private User user;
	public MyCenterHeadTwoFrag(User user){
		this.user = user;
	}
	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
        rootView = inflater.inflate(R.layout.frag_my_center_head_2, container, false);//关联布局文件  
        
        findViewByIds();
        initMyCenterPage();
        return rootView;  
    }  
	/**
	 * 初始化控件
	 * @author Ron
	 * @date 2016-8-3  上午1:02:25
	 */
	private void findViewByIds() {
		
		sign = (TextView)findViewById(R.id.my_center_signature); //签名
		
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
			if(!StringUtils.isEmpty(user.memberSign)){
				sign.setText(user.memberSign);
			}
		}
	}
}
