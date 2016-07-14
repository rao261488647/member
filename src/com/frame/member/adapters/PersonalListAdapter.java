package com.frame.member.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.bean.PersonalListBean.PersonalItem;

public class PersonalListAdapter extends BaseAdapter {

	private Context context;
	private List<PersonalItem> personalItemList;

	public PersonalListAdapter(Context context,
			List<PersonalItem> personalItemList) {
		this.context = context;
		this.personalItemList = personalItemList;
	}

	@Override
	public int getCount() {
		return personalItemList == null ? 0 : personalItemList.size();
	}

	@Override
	public PersonalItem getItem(int position) {
		return personalItemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PersonalHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_personal, null);
			holder = new PersonalHolder();

			holder.tv_item_name_val = (TextView) convertView
					.findViewById(R.id.tv_item_name_val);
			holder.tv_item_tel_val = (TextView) convertView
					.findViewById(R.id.tv_item_tel_val);
			holder.tv_item_firstGetProDate_val = (TextView) convertView
					.findViewById(R.id.tv_item_firstGetProDate_val);
			holder.tv_item_driveNum_val = (TextView) convertView
					.findViewById(R.id.tv_item_driveNum_val);
			holder.tv_item_isTrainning_val = (TextView) convertView
					.findViewById(R.id.tv_item_isTrainning_val);
			holder.tv_item_speDeiverProperty_val = (TextView) convertView
					.findViewById(R.id.tv_item_speDeiverProperty_val);
			holder.tv_item_regDate_val = (TextView) convertView
					.findViewById(R.id.tv_item_regDate_val);
			convertView.setTag(holder);
		} else {
			holder = (PersonalHolder) convertView.getTag();
		}

		PersonalItem personalItem = getItem(position);

		holder.tv_item_name_val.setText(personalItem.name);
		holder.tv_item_tel_val.setText(personalItem.tel);
		holder.tv_item_firstGetProDate_val.setText(personalItem.firstGetProDate);
		holder.tv_item_driveNum_val.setText(personalItem.driveNum);
		holder.tv_item_isTrainning_val.setText(personalItem.isTrainning);
		holder.tv_item_speDeiverProperty_val.setText(personalItem.speDeiverProperty);
		holder.tv_item_regDate_val.setText(personalItem.regDate);

		return convertView;
	}

	static class PersonalHolder {

		private TextView tv_item_name_val, tv_item_tel_val,
				tv_item_firstGetProDate_val, tv_item_driveNum_val,
				tv_item_isTrainning_val, tv_item_speDeiverProperty_val,
				tv_item_regDate_val;
	}

}
