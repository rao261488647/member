package com.frame.member.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.bean.NotifyBean;
import com.frame.member.frag.BaseFrag;
import com.frame.member.frag.InformFrag;
import com.frame.member.frag.MainFrag;
import com.frame.member.frag.BookingCourseFrag;
import com.frame.member.frag.MyCenterFrag;
import com.frame.member.frag.AdviceFrag;
import com.frame.member.widget.MainNotifyView;

public class MainActivity extends BaseActivity {
	public static final String TAG = MainActivity.class.getSimpleName();

	public static final String TYPE_NOTIFY = "type_notify";
	public static final String TAG_NOTIFY_DATA = "tag_notify_data";
	public static final int TYPE_NOTIFY_NOFITY = 0x0010;

	private MainNotifyView rl_main_notify;
	private LinearLayout ll_main_btm_mp, ll_main_btm_pm, ll_main_btm_pc, ll_main_btm_rentcar;
	private FrameLayout frame_main_layout;
	private TextView main_tab_1,main_tab_2,main_tab_3,main_tab_4;
	/** 切换Fragment **/
	private FragmentManager mFragmentManager;

	private String[] main_tabs;
	private String mCurFragTag;

	private NotifyBean notifyData;

	@Override
	protected void loadViewLayout() {
		// 注册信鸽推送
//		XGPushConfig.enableDebug(this, true);
		setContentView(R.layout.activity_main);
//		XGPushManager.registerPush(this, new XGIOperateCallback() {
//
//			@Override
//			public void onSuccess(Object data, int arg1) {
//				Log.w(TAG, "+++ register push sucess. token:" + data);
//			}
//
//			@Override
//			public void onFail(Object data, int errCode, String msg) {
//				Log.w(TAG, "+++ register push fail. token:" + data + ", errCode:" + errCode + ",msg:" + msg);
//			}
//		});
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
//		setIntent(intent);
//		onNotifyClicked(intent);
	}

	private void onNotifyClicked(Intent intent) {
//		notifyData = (NotifyBean) intent.getSerializableExtra(TAG_NOTIFY_DATA);
//
//		if (notifyData == null)
//			return;
//
//		MyLog.i(TT_TAG, notifyData.toString());

//		((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE))
//				.cancel(NotifyIdGenerater.getInstance().getNotifyId(notifyData.sendDate));
		
//		((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE))
//		.cancel(0);

//		if(intent.getIntExtra(TYPE_NOTIFY,0) == TYPE_NOTIFY_NOFITY)
		rl_main_notify.performClick();
	}

	@Override
	protected void findViewById() {
		ll_main_btm_mp = (LinearLayout) findViewById(R.id.ll_main_btm_mp);
		ll_main_btm_rentcar = (LinearLayout) findViewById(R.id.ll_main_btm_rentcar);
//		rl_main_notify = (MainNotifyView) findViewById(R.id.rl_main_notify);
		ll_main_btm_pm = (LinearLayout) findViewById(R.id.ll_main_btm_pm);
		ll_main_btm_pc = (LinearLayout) findViewById(R.id.ll_main_btm_pc);
		
		frame_main_layout = (FrameLayout)findViewById(R.id.fragment_content);
		
		//main页 顶部标签
//		main_tab_1 = (TextView) findViewById(R.id.main_tab_1);
//		main_tab_2 = (TextView) findViewById(R.id.main_tab_2);
//		main_tab_3 = (TextView) findViewById(R.id.main_tab_3);
//		main_tab_4 = (TextView) findViewById(R.id.main_tab_4);
	}

	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
//			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)frame_main_layout.getLayoutParams();
			
			switch (v.getId()) {
			case R.id.ll_main_btm_mp:
				checkTabChange(0);
				break;
			// case R.id.ll_main_btm_rentcar:
			// Intent intent = new
			// Intent(MainActivity.this,RentCarActivity.class);
			// startActivity(intent);
			// break;
//			case R.id.rl_main_notify:
//				checkTabChange(1);
//				break;
			case R.id.ll_main_btm_rentcar:
				checkTabChange(1);
				break;

			case R.id.ll_main_btm_pm:
				checkTabChange(2);
				break;
			case R.id.ll_main_btm_pc:
				checkTabChange(3);
				break;
			
//			case R.id.main_tab_1:
//				params.setMargins(0, 90, 0, 56);
//				checkTabChange(0);
//				break;
//			case R.id.main_tab_2:
//				
//				checkTabChange(4);
//				break;
//			case R.id.main_tab_3:
//				checkTabChange(5);
//				break;
//			case R.id.main_tab_4:
//				checkTabChange(6);
//				break;
		
			}
		}
	};

	private BaseFrag mCurFrag;

	private void checkTabChange(int index) {
		if (main_tabs[index].equals(mCurFragTag))
			return;
		mCurFragTag = main_tabs[index];

		changeFrag();
	}

	private void changeFrag() {
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		if (mCurFrag != null)
			transaction.hide(mCurFrag);
		mCurFrag = (BaseFrag) mFragmentManager.findFragmentByTag(mCurFragTag);
		if (mCurFrag == null) {
			mCurFrag = genFragment();
			transaction.add(R.id.fragment_content, mCurFrag, mCurFragTag).commit();
		} else {
			transaction.show(mCurFrag).commit();
		}
		if (mCurFrag instanceof InformFrag && notifyData != null) {
			((InformFrag) mCurFrag).addNotifyData(notifyData);
		}
		changeTabBtnStatus();
	}

	@Override
	protected void setListener() {
		ll_main_btm_mp.setOnClickListener(clickListener);
		ll_main_btm_rentcar.setOnClickListener(clickListener);
//		rl_main_notify.setOnClickListener(clickListener);
		ll_main_btm_pm.setOnClickListener(clickListener);
		ll_main_btm_pc.setOnClickListener(clickListener);
		
		//设置单击监听事件
//		main_tab_1.setOnClickListener(clickListener);
//		main_tab_2.setOnClickListener(clickListener);
//		main_tab_3.setOnClickListener(clickListener);
//		main_tab_4.setOnClickListener(clickListener);
	}

	@Override
	protected void processLogic() {
		main_tabs = getResources().getStringArray(R.array.main_tabs);

		mFragmentManager = this.getSupportFragmentManager();

		restoreTitle();

//		initNoticeFrag();

		changeFrag();

		Intent intent = getIntent();
		if (intent != null) {
//			onNotifyClicked(intent);
		}
	}

	private void restoreTitle() {
		if (savedInstanceState != null)
			mCurFragTag = savedInstanceState.getString(AppConstants.SAVE_INSTANCE_KEY);
		if (TextUtils.isEmpty(mCurFragTag)) {
			mCurFragTag = main_tabs[0];
		}
	}

	@SuppressLint("ResourceAsColor")
	private void changeTabBtnStatus() {
		ll_main_btm_mp.getChildAt(0).setSelected(false);
		ll_main_btm_rentcar.getChildAt(0).setSelected(false);
		ll_main_btm_pm.getChildAt(0).setSelected(false);
		ll_main_btm_pc.getChildAt(0).setSelected(false);

		if (mCurFragTag.equals(main_tabs[0])) {
			ll_main_btm_mp.getChildAt(0).setSelected(true);
		} else if (mCurFragTag.equals(main_tabs[1])) {
			ll_main_btm_rentcar.getChildAt(0).setSelected(true);
		} else if (mCurFragTag.equals(main_tabs[2])) {
			ll_main_btm_pm.getChildAt(0).setSelected(true);
		} else if (mCurFragTag.equals(main_tabs[3])) {
			ll_main_btm_pc.getChildAt(0).setSelected(true);
		}	
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString(AppConstants.SAVE_INSTANCE_KEY, mCurFragTag);
		super.onSaveInstanceState(outState);
	}

	private BaseFrag genFragment() {
		if (main_tabs[0].equals(mCurFragTag)) // 首页
			return MainFrag.newInstance(mCurFragTag);
		else if (main_tabs[1].equals(mCurFragTag)) // 求教
			return AdviceFrag.newInstance(mCurFragTag);
		else if (main_tabs[2].equals(mCurFragTag)) // 约课
			return BookingCourseFrag.newInstance(mCurFragTag);
		else if (main_tabs[3].equals(mCurFragTag)) // 我的
			return MyCenterFrag.newInstance(mCurFragTag);
		return null;
	}

	private long mExitTime;
	private boolean onece = true;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (onece) {
				showToast("再按一次退出程序");
				mExitTime = System.currentTimeMillis();
				onece = false;
			} else if ((System.currentTimeMillis() - mExitTime) > 2000) {
				showToast("再按一次退出程序");
				mExitTime = System.currentTimeMillis();

			} else {
				finish();
				// System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
