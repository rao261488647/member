package com.frame.member.adapters;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.bean.MainCourseBean.MainCourseNews;

/**
 * 主页-教程——》新闻适配器
 * @author Ron
 * @date 2016-7-10  下午10:28:47
 */
public class MainCourseNewsAdapter extends BaseAdapter {

	private List<MainCourseNews> mainCourseNewsList;
	private Context context;
	public MainCourseNewsAdapter(Context context,
			 List<MainCourseNews> mainCourseNewsList) {
		this.context = context;
		this.mainCourseNewsList = mainCourseNewsList;
	}
	
	
	@Override
	public int getCount() {
		return mainCourseNewsList == null ? 0 : mainCourseNewsList.size();
	}

	@Override
	public MainCourseNews getItem(int position) {
		return mainCourseNewsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MainCourseNewsHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_main_info, null);
			holder = new MainCourseNewsHolder();

			holder.news_img1 = (ImageView) convertView
					.findViewById(R.id.news_img1);
			
			holder.news_text1 = (TextView) convertView
					.findViewById(R.id.news_text1);
			holder.news_text2 = (TextView) convertView
					.findViewById(R.id.news_text2);
			convertView.setTag(holder);
		} else {
			holder = (MainCourseNewsHolder) convertView.getTag();
		}
		
		
		MainCourseNews news = getItem(position);

		String url = news.infoPhoto;
		TTApplication.getInstance()
				.disPlayImageDef(url, holder.news_img1);
		holder.news_text1.setText(news.infoTitle);
		holder.news_text2.setText(news.infoIntro);
		holder.news_text2.setEllipsize(TextUtils.TruncateAt.END);
		
		return convertView;
	}

	static class MainCourseNewsHolder {

		private ImageView news_img1;
		private TextView news_text1,news_text2;
	}
}
