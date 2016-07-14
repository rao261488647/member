package com.frame.member.adapters;

import java.util.List;

import com.frame.member.R;
import com.frame.member.bean.NotifyBean;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoAdapter extends BaseAdapter {

	private Context context;
	private List<NotifyBean> notifyDataList;

	public InfoAdapter(Context context,List<NotifyBean> notifyDataList) {
		this.context = context;
		this.notifyDataList = notifyDataList;
	}

	@Override
	public int getCount() {
		return notifyDataList == null ? 0 : notifyDataList.size();
	}

	@Override
	public NotifyBean getItem(int position) {
		return notifyDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		NotifyBean mNotifyBean = getItem(position);

		if (convertView == null) {
			convertView = View
					.inflate(context, R.layout.item_layout_info, null);
		}

		ImageView iv = (ImageView) convertView
				.findViewById(R.id.iv_info_item_fake);
		
		TextView tv_info_item_title = (TextView) convertView
				.findViewById(R.id.tv_info_item_title);
		TextView tv_info_item_date = (TextView) convertView
				.findViewById(R.id.tv_info_item_date);
		TextView tv_info_item_detail = (TextView) convertView
				.findViewById(R.id.tv_info_item_detail);
		TextView tv_info_item_money = (TextView) convertView
				.findViewById(R.id.tv_info_item_money);

		if (!"yes".equals(mNotifyBean.isReaded))
			iv.setImageResource(R.drawable.icon_info_sel);
		else
			iv.setImageResource(R.drawable.icon_info);
		
		tv_info_item_title.setText(mNotifyBean.type);
		tv_info_item_date.setText(mNotifyBean.sendDate.split(" ")[0]);
		tv_info_item_detail.setText(mNotifyBean.detail);
		tv_info_item_money.setText("金额：" + mNotifyBean.amount + "元");

		return convertView;
	}
	
}
