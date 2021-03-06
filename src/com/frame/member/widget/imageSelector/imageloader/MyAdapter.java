package com.frame.member.widget.imageSelector.imageloader;

import java.util.ArrayList;
import java.util.List;

import com.frame.member.R;
import com.frame.member.imageSelector.utils.CommonAdapter;
import com.frame.member.imageSelector.utils.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;


public class MyAdapter extends CommonAdapter<String> {

	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */
	public static ArrayList<String> mSelectedImage = new ArrayList<String>();

	/**
	 * 文件夹路径
	 */
	private String mDirPath;
	private int imageNum;

	public MyAdapter(Context context, List<String> mDatas, int itemLayoutId,
			String dirPath,int imageNum) {
		super(context, mDatas, itemLayoutId);
		this.mDirPath = dirPath;
		this.imageNum = imageNum;
	}

	@Override
	public void convert(final ViewHolder helper, final String item) {
		// 设置no_pic
		helper.setImageResource(R.id.id_item_image, R.drawable.pictures_no);
		// 设置no_selected
		helper.setImageResource(R.id.id_item_select,
				R.drawable.picture_unselected);
		// 设置图片
		helper.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item);

		final ImageView mImageView = helper.getView(R.id.id_item_image);
		final ImageView mSelect = helper.getView(R.id.id_item_select);

		mImageView.setColorFilter(null);
		// 设置ImageView的点击事件
		mSelect.setOnClickListener(new OnClickListener() {
			// 选择，则将图片变暗，反之则反之
			@Override
			public void onClick(View v) {

				// 已经选择过该图片
				if (mSelectedImage.contains(mDirPath + "/" + item)) {
					mSelectedImage.remove(mDirPath + "/" + item);
					mSelect.setImageResource(R.drawable.picture_unselected);
					mImageView.setColorFilter(null);
					imageNum--;
					((ImageSelectorActivity)mContext).changePic(false);
					
				} else
				// 未选择该图片
				{
					if(imageNum < 6){
						mSelectedImage.add(mDirPath + "/" + item);
						mSelect.setImageResource(R.drawable.pictures_selected);
						mImageView.setColorFilter(Color.parseColor("#77000000"));
						imageNum++;
						((ImageSelectorActivity)mContext).changePic(true);
					}else{
						Toast.makeText(mContext, "已经是达到图片上限", Toast.LENGTH_SHORT).show();
					}
					
				}

			}
		});
		mImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				Log.i("MyAdapter", mDirPath + "/" + item);
				Intent intent = new Intent(mContext,ArtworkActivity.class);
				intent.putExtra("artwork", mDirPath + "/" + item);
				mContext.startActivity(intent);
			}
		});

		/**
		 * 已经选择过的图片，显示出选择过的效果
		 */
		if (mSelectedImage.contains(mDirPath + "/" + item)) {
			mSelect.setImageResource(R.drawable.pictures_selected);
			mImageView.setColorFilter(Color.parseColor("#77000000"));
		}

	}
	
	public ArrayList<String> getSelectedImgs(){
		return mSelectedImage;
	}
	public void ImageClear(){
		mSelectedImage.clear();
	}
}
