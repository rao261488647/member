package com.frame.member.widget.imageSelector.imageloader;

import com.frame.member.R;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ArtworkActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_artwork);
		
		String path_img = getIntent().getStringExtra("artwork");
		
		((ImageView)findViewById(R.id.iv_artwork)).setImageURI(Uri.parse(path_img));
		((ImageView)findViewById(R.id.iv_artwork)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
