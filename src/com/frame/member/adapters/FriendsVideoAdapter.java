package com.frame.member.adapters;

import java.util.List;
import com.frame.member.R;
import com.frame.member.adapters.CoachSearchAdapter.ImageAndText;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

public class FriendsVideoAdapter extends BaseAdapter{
	private Context context;
	private List<ImageAndText> list;

	public FriendsVideoAdapter(Context context,List<ImageAndText> list) {
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			View view = LayoutInflater.from(context).inflate(R.layout.item_his_video, null);
			holder = new ViewHolder();
			holder.iv_vedio_cover = (ImageView) view.findViewById(R.id.iv_vedio_cover);
			convertView = view;
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.iv_vedio_cover.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
			}
		});
		return convertView;
	}
	
	public class ViewHolder{
		ImageView iv_vedio_cover;
	}

}
