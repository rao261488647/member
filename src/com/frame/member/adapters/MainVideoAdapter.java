package com.frame.member.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.bean.MainVideoBean.MainVideoCategory;

/**
 * 主页新闻适配器
 * @author Ron
 * @date 2016-7-10  下午10:28:47
 */
public class MainVideoAdapter extends BaseAdapter {

	private List<MainVideoCategory> mainVideoCategoryList;
	private Context context;
	public MainVideoAdapter(Context context,
			 List<MainVideoCategory> mainVideoCategoryList) {
		this.context = context;
		this.mainVideoCategoryList = mainVideoCategoryList;
	}
	
	
	@Override
	public int getCount() {
		return mainVideoCategoryList == null ? 0 : mainVideoCategoryList.size();
	}

	@Override
	public MainVideoCategory getItem(int position) {
		return mainVideoCategoryList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MainVideoCategoryHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_main_video, null);
			holder = new MainVideoCategoryHolder();

			holder.category_img1 = (ImageView) convertView
					.findViewById(R.id.item_main_video_iv);
			
			convertView.setTag(holder);
		} else {
			holder = (MainVideoCategoryHolder) convertView.getTag();
		}
		
		
		MainVideoCategory category = getItem(position);

		String url = category.categoryPhoto;
		TTApplication.getInstance()
				.disPlayImageDef(url, holder.category_img1);
//		holder.category_img1.setText(category.name);
//		holder.category_text1.setText(category.categoryTitle);
//		holder.category_text2.setText(category.categoryIntro);
//		holder.category_text2.setEllipsize(TextUtils.TruncateAt.END);
//		holder.category_text2.setSingleLine();
//		holder.category_text1.setText("生活的意义");
//		holder.category_text2.setText("生活不只有眼前的苟且");
		
		return convertView;
	}

	static class MainVideoCategoryHolder {

		private ImageView category_img1;
	}
}
