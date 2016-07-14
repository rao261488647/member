package com.frame.member.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.activity.SpecialCarEnrollActivity;
import com.frame.member.bean.RentCarListBean.RentCarItem;

public class RentCarListAdapter extends BaseAdapter {

	private Context context;
	private List<RentCarItem> rentCarList;

	public RentCarListAdapter(Context context, List<RentCarItem> rentCarList) {
		this.context = context;
		this.rentCarList = rentCarList;
	}

	@Override
	public int getCount() {
		return rentCarList == null ? 0 : rentCarList.size();
	}

	@Override
	public RentCarItem getItem(int position) {
		return rentCarList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RentCarHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_rent_car, null);
			holder = new RentCarHolder();
			holder.iv_item_rent_car = (ImageView) convertView
					.findViewById(R.id.iv_item_rent_car);

			holder.tv_item_rent_car_name = (TextView) convertView
					.findViewById(R.id.tv_item_rent_car_name);
			holder.tv_item_rent_car_plat = (TextView) convertView
					.findViewById(R.id.tv_item_rent_car_plat);
			holder.tv_item_rent_car_order = (TextView) convertView
					.findViewById(R.id.tv_item_rent_car_order);
//			holder.tv_item_rent_car_money = (TextView) convertView
//					.findViewById(R.id.tv_item_rent_car_money);
			holder.tv_item_rent_car_deposit = (TextView) convertView
					.findViewById(R.id.tv_item_rent_car_deposit);
//			holder.tv_item_rent_car_contract = (TextView) convertView
//					.findViewById(R.id.tv_item_rent_car_contract);
			convertView.setTag(holder);
		} else {
			holder = (RentCarHolder) convertView.getTag();
		}

		RentCarItem rentCarItem = getItem(position);

//		if (!TextUtils.isEmpty(rentCarItem.url))
//		TTApplication.getInstance().disPlayImageDef(rentCarItem.url,
//					holder.iv_item_rent_car);

		holder.tv_item_rent_car_name.setText(rentCarItem.carType);
		holder.tv_item_rent_car_plat.setText("适用平台：" + rentCarItem.plat);
		holder.tv_item_rent_car_order.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,SpecialCarEnrollActivity.class);
				context.startActivity(intent);
			}
		});

//		String key = rentCarItem.contractTerm.keySet().iterator().next();
//		String value = rentCarItem.contractTerm.get(key); 

//		holder.tv_item_rent_car_money.setText("￥ " + value);
//
//		holder.tv_item_rent_car_deposit.setText("押金" + rentCarItem.deposit + "元");
//		holder.tv_item_rent_car_contract.setText("合同期" + key);

		return convertView;
	}

	static class RentCarHolder {
		private ImageView iv_item_rent_car;

		private TextView tv_item_rent_car_name, tv_item_rent_car_plat,
				tv_item_rent_car_order, tv_item_rent_car_money,
				tv_item_rent_car_deposit, tv_item_rent_car_contract;
	}

}
