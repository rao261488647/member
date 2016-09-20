package com.frame.member.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.bean.MyCenterBean.Menu;
import com.frame.member.bean.MyCenterBean.User;

/**
 * 个人中心适配器
 * @author Ron
 * @date 2016-7-10  下午10:28:47
 */
public class MyCenterAdapter extends BaseAdapter {

	private List<Menu> menuList;
	private Context context;
	public MyCenterAdapter(Context context,User user,
			 List<Menu> menuList) {
		this.context = context;
		this.menuList = menuList;
	}
	
	
	@Override
	public int getCount() {
		return menuList == null ? 0 : menuList.size();
	}

	@Override
	public Menu getItem(int position) {
		return menuList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MenuHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.frag_my, null);
			holder = new MenuHolder();

			holder.headimg = (ImageView) convertView
					.findViewById(R.id.my_center_headimg);
			
			convertView.setTag(holder);
		} else {
			holder = (MenuHolder) convertView.getTag();
		}
		
		
//		User item = getItem(position);
//
//		String url = item.appHeadThumbnail; //头像地址
//		TTApplication.getInstance()
//				.disPlayImageDef(url, holder.category_img1);
//		holder.item_img1.setText(category.name);
//		holder.category_text1.setText(category.categoryTitle);
//		holder.category_text2.setText(category.categoryIntro);
//		holder.category_text2.setEllipsize(TextUtils.TruncateAt.END);
//		holder.category_text2.setSingleLine();
//		holder.category_text1.setText("生活的意义");
//		holder.category_text2.setText("生活不只有眼前的苟且");
		
		return convertView;
	}

	static class MenuHolder {

		private TextView grade,level,point,nickname;
		
		private ImageView headimg;
	}
}
