package com.frame.member.activity;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.frame.member.R;
import com.frame.member.adapters.MyCollectClassAdapter;
import com.frame.member.adapters.MyCollectTeacherAdapter;
import com.frame.member.frag.BaseFrag;
import com.frame.member.frag.MainFrag;
import com.frame.member.frag.MyCollectClassFrag;
import com.frame.member.frag.MyCollectCoachFrag;
import com.frame.member.frag.MyMsgCommentFrag;
import com.frame.member.frag.MyMsgNoticeFrag;
import com.frame.member.frag.MyMsgZanFrag;
import com.frame.member.widget.swipemenulistview.SwipeMenu;
import com.frame.member.widget.swipemenulistview.SwipeMenuCreator;
import com.frame.member.widget.swipemenulistview.SwipeMenuItem;
import com.frame.member.widget.swipemenulistview.SwipeMenuListView;
/**
 * 我的收藏
 * Ron
 */

public class MyCollectActivity extends BaseActivity implements OnClickListener {
	 private List<String> mAppList;
	 private MyCollectTeacherAdapter teacherAdapter;
	 private MyCollectClassAdapter classAdapter;
	 private SwipeMenuListView mTeacherListView,classListView;
	 private LinearLayout leftBtn,rightBtn; //标题栏按钮
	private TextView leftText,rightText;//标题栏文字
	private ImageView left_back;//左侧返回键
	private FragmentManager mFragmentManager;
	private BaseFrag mCurFrag;
	private String curFragTag; //当前选中的 主页上面标签
	private String[] main_tabs;// 主页上面标签集合
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_my_collect);
		
		mTeacherListView = (SwipeMenuListView) findViewById(R.id.my_collect_teacher_listView);
		classListView = (SwipeMenuListView) findViewById(R.id.my_collect_class_listView);
		
		mAppList = new ArrayList<String>();
		mAppList.add(new String("1"));
		mAppList.add(new String("2"));
		
		teacherAdapter = new MyCollectTeacherAdapter(this, mAppList);
		mTeacherListView.setAdapter(teacherAdapter);
		
		classAdapter = new MyCollectClassAdapter(this, mAppList);
		classListView.setAdapter(classAdapter);
		
		
        
        teacherListViewInit();
        classListViewInit();
	}

	private void teacherListViewInit(){
		 // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
//                SwipeMenuItem openItem = new SwipeMenuItem(
//                        getApplicationContext());
//                // set item background
//                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
//                        0xCE)));
//                // set item width
//                openItem.setWidth(dp2px(90));
//                // set item title
//                openItem.setTitle("Open");
//                // set item title fontsize
//                openItem.setTitleSize(18);
//                // set item title font color
//                openItem.setTitleColor(Color.WHITE);
//                // add to menu
//                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(R.color.my_collect_delete_yellow);
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.icon_my_collect_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
		// set creator
        mTeacherListView.setMenuCreator(creator);
        

        // step 2. listener item click event
        mTeacherListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                    	// delete
//					delete(item);
                    	mAppList.remove(position);
                    	teacherAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                    	// open
                    	//open(item);
                        break;
                }
                return false;
            }
        });

        // set SwipeListener
        mTeacherListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // set MenuStateChangeListener
        mTeacherListView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
            }

            @Override
            public void onMenuClose(int position) {
            }
        });

        // other setting
//		listView.setCloseInterpolator(new BounceInterpolator());

        // test item long click
        mTeacherListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Toast.makeText(getApplicationContext(), position + " long click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
	}
	
	private void classListViewInit(){
		 // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
//                SwipeMenuItem openItem = new SwipeMenuItem(
//                        getApplicationContext());
//                // set item background
//                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
//                        0xCE)));
//                // set item width
//                openItem.setWidth(dp2px(90));
//                // set item title
//                openItem.setTitle("Open");
//                // set item title fontsize
//                openItem.setTitleSize(18);
//                // set item title font color
//                openItem.setTitleColor(Color.WHITE);
//                // add to menu
//                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(R.color.my_collect_delete_yellow);
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.icon_my_collect_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
		
		// set creator
        classListView.setMenuCreator(creator);
        

        // step 2. listener item click event
        classListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                    	// delete
//					delete(item);
                    	mAppList.remove(position);
                    	classAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                    	// open
                    	//open(item);
                        break;
                }
                return false;
            }
        });

        // set SwipeListener
        classListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // set MenuStateChangeListener
        classListView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
            }

            @Override
            public void onMenuClose(int position) {
            }
        });

        // other setting
//		listView.setCloseInterpolator(new BounceInterpolator());

        // test item long click
        classListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Toast.makeText(getApplicationContext(), position + " long click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
	}
	
	
	@Override
	protected void findViewById() {
		tv_title.setText("我的收藏");
		leftBtn = (LinearLayout)findViewById(R.id.my_msg_left_btn);
		rightBtn = (LinearLayout)findViewById(R.id.my_msg_right_btn);
		leftText = (TextView)findViewById(R.id.my_msg_left_text);
		rightText = (TextView)findViewById(R.id.my_msg_right_text);
		left_back = (ImageView)findViewById(R.id.my_msg_back);
		main_tabs = getResources().getStringArray(R.array.my_collect_tabs);
	}
	
	@Override
	protected void setListener() {
		leftBtn.setOnClickListener(this);
		rightBtn.setOnClickListener(this);
		left_back.setOnClickListener(this);
	}

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

	@Override
	protected void processLogic() {
		iv_title_back.setVisibility(0);
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
		case R.id.my_msg_right_btn:
			checkTabChange(1);
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
		if (main_tabs[0].equals(curFragTag)) // 教练
			return MyCollectCoachFrag.newInstance(curFragTag);
		else if (main_tabs[1].equals(curFragTag)) // 课程
			return MyCollectClassFrag.newInstance(curFragTag);
		return null;
	}
	
	/**
	 * 改变tab的状态
	 * @author Ron
	 * @date 2016-6-28  上午12:37:34
	 */
	@SuppressLint("ResourceAsColor")
	private void changeTabBtnStatus() {
		// 教练
		if (main_tabs[0].equals(curFragTag)){
			leftText.setTextColor(0xffffffff);
			leftText.setTextSize(16);
			rightText.setTextColor(0xff878788);
			rightText.setTextSize(14);
		// 课程
		}else if (main_tabs[1].equals(curFragTag)){
			leftText.setTextColor(0xff878788);
			leftText.setTextSize(14);
			
			rightText.setTextColor(0xffffffff);
			rightText.setTextSize(16);
		}
			
	}
	
}
