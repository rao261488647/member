package com.frame.member.activity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Utils.AppFileUtil;
import com.frame.member.widget.imageSelector.imageloader.ImageSelectorActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class ProfileImageActivity extends BaseActivity implements
		OnClickListener {

	Button btn_take_phote, btn_pick_photo, btn_cancel;
	 public static final String KEY_PICKED_PIC_PATH = "picture_path";

	public final static int PHOTO_ZOOM = 0;
	public final static int TAKE_PHOTO = 1;
	public final static int PHOTO_RESULT = 2;
	public static final String IMAGE_UNSPECIFIED = "image/*";
	public static final String PROFILE_PATH = AppConstants.IMAGE_CHCHE
			+ "/profile.jpg";
//	public static final String PROFILE_CONDEN_CREATE_PATH_1 = AppConstants.IMAGE_CHCHE
//			+ "/profile_conden_1.jpg";
//	public static final String PROFILE_CONDEN_CREATE_PATH_2 = AppConstants.IMAGE_CHCHE
//			+ "/profile_conden_2.jpg";
//	public static final String PROFILE_CONDEN_CREATE_PATH_3 = AppConstants.IMAGE_CHCHE
//			+ "/profile_conden_3.jpg";
//	public static final String PROFILE_CONDEN_CREATE_PATH_4 = AppConstants.IMAGE_CHCHE
//			+ "/profile_conden_4.jpg";
//	public static final String PROFILE_CONDEN_CREATE_PATH_5 = AppConstants.IMAGE_CHCHE
//			+ "/profile_conden_5.jpg";
//	public static final String PROFILE_CONDEN_CREATE_PATH_6 = AppConstants.IMAGE_CHCHE
//			+ "/profile_conden_6.jpg";
	public String path_conden; 
	private int key;
	private Uri pick_zoom_uri;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_profile_image);
	}

	@Override
	protected void findViewById() {
		btn_take_phote = (Button) findViewById(R.id.btn_take_photo);
		btn_pick_photo = (Button) findViewById(R.id.btn_pick_photo);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		key = getIntent().getIntExtra("key", 0);
	}

	@Override
	protected void setListener() {
		btn_take_phote.setOnClickListener(this);
		btn_pick_photo.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_take_photo: // 拍照时已经创建文件
			Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent1.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(getLocalPicFile()));
			startActivityForResult(intent1, TAKE_PHOTO);
			break;
		case R.id.btn_pick_photo: // 从相册选择 未创建文件
			if(key == 1001){
				Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
				intent2.setType(IMAGE_UNSPECIFIED);
				Intent wrapperIntent = Intent.createChooser(intent2, null);
				startActivityForResult(wrapperIntent, PHOTO_ZOOM);
			}else{
				Intent intent3 = new Intent(this, ImageSelectorActivity.class);
				intent3.putExtra("imageNum", getIntent().getIntExtra("imageNum", 0));
				startActivityForResult(intent3, PHOTO_ZOOM);
			}
			break;
		case R.id.btn_cancel:
			finish();
			break;

		}
	}

	// 是头像时剪切图片
	public void photoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);

		intent.putExtra("crop", "true");

		intent.putExtra("aspectX", 1);

		intent.putExtra("aspectY", 1);

		intent.putExtra("outputX", 500);

		intent.putExtra("outputY", 500);
		
		//防止头像有黑框

		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true);

		intent.putExtra("return-data", false);

		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

		intent.putExtra("noFaceDetection", true); // no face detection

		startActivityForResult(intent, PHOTO_RESULT);
	}

	private String getSelectPicPath(Intent data) {
		Uri selectedImage = data.getData();
		String[] filePathColumns = { MediaColumns.DATA };
		Cursor c = this.getContentResolver().query(selectedImage,
				filePathColumns, null, null, null);
		if (c == null)
			return "";
		c.moveToFirst();
		int columnIndex = c.getColumnIndex(filePathColumns[0]);
		String picturePath = c.getString(columnIndex);
		c.close();
		return picturePath;
	}

	private File getLocalPicFile() {
		File profile = null;
		switch (key) {
		case 1001:
			profile = AppFileUtil.createFile(PROFILE_PATH);
			break;
		case 1101:
			path_conden = AppConstants.IMAGE_CHCHE + "/" + System.currentTimeMillis() + ".jpg";
//			profile = AppFileUtil.createFile(PROFILE_CONDEN_CREATE_PATH_1);
			profile = AppFileUtil.createFile(path_conden);
			break;
//		case 1102:
//			profile = AppFileUtil.createFile(PROFILE_CONDEN_CREATE_PATH_2);
//			break;
//		case 1103:
//			profile = AppFileUtil.createFile(PROFILE_CONDEN_CREATE_PATH_3);
//			break;
//		case 1104:
//			profile = AppFileUtil.createFile(PROFILE_CONDEN_CREATE_PATH_4);
//			break;
//		case 1105:
//			profile = AppFileUtil.createFile(PROFILE_CONDEN_CREATE_PATH_5);
//			break;
//		case 1106:
//			profile = AppFileUtil.createFile(PROFILE_CONDEN_CREATE_PATH_6);
//			break;
		}
		return profile;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == PHOTO_ZOOM) { // 从相册里选图片
				if (key == 1001) {
					pick_zoom_uri = data.getData();
					photoZoom(pick_zoom_uri);
				} else {
//					String picturePath = getSelectPicPath(data);
//					try {
//						Intent intent = new Intent();
//						intent.putExtra("isFromPhoto", true);
//						intent.putExtra("picturePath", picturePath);
//						setResult(RESULT_OK,intent);
//						finish();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
					ArrayList<String> path_list = new ArrayList<String>();
					path_list = data.getStringArrayListExtra("KEY_PATHS");
					Intent intent = new Intent();
					intent.putStringArrayListExtra("picturePaths", path_list);
					intent.putExtra("isFromPhoto", true);
					setResult(1005,intent);
					finish();
				}
			}
			if (requestCode == TAKE_PHOTO) { // 拍照
				File picture = null;
				switch (key) {
				case 1001:
					picture = new File(PROFILE_PATH);
					pick_zoom_uri = Uri.fromFile(picture);
					photoZoom(pick_zoom_uri);
					break;
				case 1101:
//				case 1102:
//				case 1103:
//				case 1104:
//				case 1105:
//				case 1106:
					Intent intent = new Intent();
					intent.putExtra("isFromPhoto", false);
					intent.putExtra("picturePath", path_conden);
					setResult(1005,intent);
					finish();
					break;
				}
			}

			if (requestCode == PHOTO_RESULT) { //系统缩放剪裁返回
				Bitmap photo = null;
				if (pick_zoom_uri != null) {
					photo = decodeUriAsBitmap(pick_zoom_uri);// decode bitmap
				}
				if (photo != null) {
					File profile = AppFileUtil.createFile(PROFILE_PATH); // 这里只有可能是头像
					dealPicData(profile, photo);
				}
			}
		}
	}

	private Bitmap decodeUriAsBitmap(Uri uri) {

		Bitmap bitmap = null;

		try {

			bitmap = BitmapFactory.decodeStream(getContentResolver()
					.openInputStream(uri));
		} catch (FileNotFoundException e) {

			e.printStackTrace();

			return null;

		}

		return bitmap;

	}

	private void dealPicData(File profile, Bitmap photo) {
		try {
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
					new FileOutputStream(profile));
			boolean compress = photo.compress(Bitmap.CompressFormat.JPEG, 100,
					bufferedOutputStream);
			if (compress) {
				setResult(1005);
				finish();
			} else
				showToast("图片压缩失败");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
