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
import com.frame.member.bean.MainInfoBean.MainNews;
import com.frame.member.widget.swipemenulistview.BaseSwipListAdapter;

/**
 * 主页新闻适配器
 * @author Ron
 * @date 2016-7-10  下午10:28:47
 */
public class MainNewsAdapter extends BaseSwipListAdapter{

	private List<MainNews> mainNewsList;
	private Context context;
	public MainNewsAdapter(Context context,
			 List<MainNews> mainNewsList) {
		this.context = context;
		this.mainNewsList = mainNewsList;
	}
	
	
	@Override
	public int getCount() {
		return mainNewsList == null ? 0 : mainNewsList.size();
	}

	@Override
	public MainNews getItem(int position) {
		return mainNewsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MainNewsHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_main_info, null);
			holder = new MainNewsHolder();

			holder.news_img1 = (ImageView) convertView
					.findViewById(R.id.news_img1);
			
			holder.news_text1 = (TextView) convertView
					.findViewById(R.id.news_text1);
			holder.news_text2 = (TextView) convertView
					.findViewById(R.id.news_text2);
			convertView.setTag(holder);
		} else {
			holder = (MainNewsHolder) convertView.getTag();
		}
		
		
		MainNews news = getItem(position);

		String url = news.newsPhoto;
		TTApplication.getInstance()
				.disPlayImageDef(url, holder.news_img1);
//		holder.news_img1.setText(news.name);
		holder.news_text1.setText(news.newsTitle);
		holder.news_text2.setText(news.newsIntro);
		holder.news_text2.setEllipsize(TextUtils.TruncateAt.END);
//		holder.news_text2.setSingleLine();
//		holder.news_text1.setText("生活的意义");
//		holder.news_text2.setText("生活不只有眼前的苟且");
		
		return convertView;
	}

	static class MainNewsHolder {

		private ImageView news_img1;
		private TextView news_text1,news_text2;
	}
}
