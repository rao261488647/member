package com.frame.member.activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.member.R;
/**
 *  新闻详情页面，web方式展示
 *  @author Ron
 */

public class NewsDetailActivity extends BaseActivity implements OnClickListener{
	private String title,newsUrl;
	private WebView news_webview;
	private TextView news_title;
	private ImageView news_share,news_back;
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_news_detail);
		Intent intent = getIntent();
		title = intent.getStringExtra("title");
		newsUrl = intent.getStringExtra("newsUrl");
	}

	@Override
	protected void findViewById() {
		
		news_webview = (WebView) findViewById(R.id.news_webview);
		news_title = (TextView) findViewById(R.id.news_title);
		news_share = (ImageView) findViewById(R.id.news_share);
		news_back = (ImageView) findViewById(R.id.news_back);
	}
	
	@Override
	protected void setListener() {
		news_share.setOnClickListener(this);
		news_back.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		news_title.setText(title);
		WebSettings webSettings = news_webview.getSettings();
//		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setUseWideViewPort(true); 
		webSettings.setDomStorageEnabled(true);
		news_webview.loadUrl(newsUrl);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.news_back:
			finish();
			break;
		case R.id.news_share:
			showToast("分享链接！");
			break;
		default:
			break;
		}
	}
}
