package com.frame.member.activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.MediaController;
import android.widget.VideoView;

import com.frame.member.R;
/**
 * 视频播放
 * ron
 */

public class VideoPlayActivity extends BaseActivity {
	
	private VideoView video_play_vv;
	private String title;
	
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_video_play);
		
	}

	class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {
		 
	    @Override
	    public void onCompletion(MediaPlayer mp) {
	    }
	}
	/**
	 * 加载视频
	 */
	private void initiVideo(){
//		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 设置当前activity为横屏
		Intent intent = getIntent();
		 //网络视频
	    String video_url = intent.getStringExtra("video_url");
	    
	    title = intent.getStringExtra("title");
		if(!TextUtils.isEmpty(title)){
			tv_title.setText(title);
		}
	    Uri uri = Uri.parse( video_url );
	    //设置视频控制器
	    video_play_vv.setMediaController(new MediaController(this));
	    //播放完成回调
	    video_play_vv.setOnCompletionListener( new MyPlayerOnCompletionListener());
	    //设置视频路径
	    video_play_vv.setVideoURI(uri);
	    //开始播放视频
	    video_play_vv.start();
	}
	
	@Override
	protected void findViewById() {
		tv_title.setText("视频播放");
		video_play_vv = (VideoView) findViewById(R.id.video_play_vv);
	}
	
	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
		iv_title_back.setVisibility(0);
		initiVideo();
	}
}
