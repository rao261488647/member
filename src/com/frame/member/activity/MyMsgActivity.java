package com.frame.member.activity;
import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.frag.BaseFrag;
import com.frame.member.frag.MainFrag;
import com.frame.member.frag.MyMsgCommentFrag;
import com.frame.member.frag.MyMsgNoticeFrag;
import com.frame.member.frag.MyMsgZanFrag;
/**
 * 消息通知、评论、点赞
 * Ron
 */

public class MyMsgActivity extends BaseActivity implements OnClickListener {
	private LinearLayout leftBtn,centerBtn,rightBtn; //标题栏3个按钮
	private TextView leftText,centerText,rightText;//标题栏文字
	private ImageView left_back;//左侧返回键
	private FragmentManager mFragmentManager;
	private BaseFrag mCurFrag;
	private String curFragTag; //当前选中的 主页上面标签
	private String[] main_tabs;// 主页上面标签集合
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_my_msg);
	}

	@Override
	protected void findViewById() {
		leftBtn = (LinearLayout)findViewById(R.id.my_msg_left_btn);
		centerBtn = (LinearLayout)findViewById(R.id.my_msg_center_btn);
		rightBtn = (LinearLayout)findViewById(R.id.my_msg_right_btn);
		leftText = (TextView)findViewById(R.id.my_msg_left_text);
		centerText = (TextView)findViewById(R.id.my_msg_center_text);
		rightText = (TextView)findViewById(R.id.my_msg_right_text);
		left_back = (ImageView)findViewById(R.id.my_msg_back);
		main_tabs = getResources().getStringArray(R.array.my_msg_tabs);
	}
	
	@Override
	protected void setListener() {
		leftBtn.setOnClickListener(this);
		centerBtn.setOnClickListener(this);
		rightBtn.setOnClickListener(this);
		left_back.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
//		iv_title_back.setVisibility(0);
		mFragmentManager = this.getSupportFragmentManager();
		restoreTitle(); //初始化 curFragTag
		changeFrag(); //先初始化一下 fragment
	}
	
	/**
	 * 切换fragment
	 * @author Ron
	 * @date 2016-6-28  上午12:38:11
	 */
	private void changeFrag() {
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		if (mCurFrag != null){
			transaction.hide(mCurFrag);
		}
		mCurFrag = (BaseFrag) mFragmentManager.findFragmentByTag(curFragTag);
		if (mCurFrag == null) {
			mCurFrag = genFragment();
			if(!(mCurFrag instanceof MainFrag))
				transaction.add(R.id.my_msg_framelayout, mCurFrag, curFragTag).commit();
		} else {
			transaction.show(mCurFrag).commit();
		}
		changeTabBtnStatus();
	}

	/**
	 * 单击事件
	 * @author Ron
	 * @date 2016-8-23  上午12:02:39
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 注意点击的控件id
		case R.id.my_msg_left_btn:
			checkTabChange(0);
			break;
		case R.id.my_msg_center_btn:
			checkTabChange(1);
			break;
		case R.id.my_msg_right_btn:
			checkTabChange(2);
			break;
		case R.id.my_msg_back:
			finish();
			break;
		}
	}
	
	private void restoreTitle() {
		if (TextUtils.isEmpty(curFragTag)) {
			curFragTag = main_tabs[0];
		}
	}
	
	private void checkTabChange(int index) {
		if (main_tabs[index].equals(curFragTag))
			return;
		curFragTag = main_tabs[index];

		changeFrag();
	}
	/**
	 * 获取需要切换的fragment
	 * @author Ron
	 * @date 2016-6-28  上午12:38:29
	 * @return
	 */
	private BaseFrag genFragment() {
		if (main_tabs[0].equals(curFragTag)) // 通知
			return MyMsgNoticeFrag.newInstance(curFragTag);
		else if (main_tabs[1].equals(curFragTag)) // 评论
			return MyMsgCommentFrag.newInstance(curFragTag);
		else if (main_tabs[2].equals(curFragTag)) // 点赞
			return MyMsgZanFrag.newInstance(curFragTag);
		return null;
	}
	
	/**
	 * 改变tab的状态
	 * @author Ron
	 * @date 2016-6-28  上午12:37:34
	 */
	@SuppressLint("ResourceAsColor")
	private void changeTabBtnStatus() {
		// 通知
		if (main_tabs[0].equals(curFragTag)){
			leftText.setTextColor(0xffffffff);
			leftText.setTextSize(16);
			
			centerText.setTextColor(0xff878788);
			centerText.setTextSize(14);
			
			rightText.setTextColor(0xff878788);
			rightText.setTextSize(14);
		// 评论
		}else if (main_tabs[1].equals(curFragTag)){
			leftText.setTextColor(0xff878788);
			leftText.setTextSize(14);
			
			centerText.setTextColor(0xffffffff);
			centerText.setTextSize(16);
			
			rightText.setTextColor(0xff878788);
			rightText.setTextSize(14);
		// 点赞
		}else if (main_tabs[2].equals(curFragTag)){
			leftText.setTextColor(0xff878788);
			leftText.setTextSize(14);
			
			centerText.setTextColor(0xff878788);
			centerText.setTextSize(14);
			
			rightText.setTextColor(0xffffffff);
			rightText.setTextSize(16);
		}
			
	}
}
