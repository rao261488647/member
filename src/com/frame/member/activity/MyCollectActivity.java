package com.frame.member.activity;
import java.util.ArrayList;
import java.util.List;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.frame.member.R;
import com.frame.member.adapters.MyCollectClassAdapter;
import com.frame.member.adapters.MyCollectTeacherAdapter;
import com.frame.member.widget.swipemenulistview.SwipeMenu;
import com.frame.member.widget.swipemenulistview.SwipeMenuCreator;
import com.frame.member.widget.swipemenulistview.SwipeMenuItem;
import com.frame.member.widget.swipemenulistview.SwipeMenuListView;
/**
 * 我的收藏
 * Ron
 */

public class MyCollectActivity extends BaseActivity {
	 private List<String> mAppList;
	 private MyCollectTeacherAdapter teacherAdapter;
	 private MyCollectClassAdapter classAdapter;
	 private SwipeMenuListView mTeacherListView,classListView;
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
	}
	
	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
		iv_title_back.setVisibility(0);
	}
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
