package com.frame.member.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.bean.StatementsListBean.StatementsItem;

public class StatementsListAdapter extends BaseAdapter {

	private Context context;
	private List<StatementsItem> statementsCarList;
	
	public StatementsListAdapter(Context context,
			List<StatementsItem> statementsCarList) {
		this.context = context;
		this.statementsCarList = statementsCarList;
	}

	@Override
	public int getCount() {
		return statementsCarList == null ? 0 : statementsCarList.size();
	}

	@Override
	public StatementsItem getItem(int position) {
		return statementsCarList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		StatemetnsHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_statements_car,
					null);
			holder = new StatemetnsHolder();

			holder.tv_item_submit_date = (TextView) convertView
					.findViewById(R.id.tv_item_submit_date);
			holder.tv_item_statement_car_plat = (TextView) convertView
					.findViewById(R.id.tv_item_statement_car_plat);
			holder.tv_item_statement_money = (TextView) convertView
					.findViewById(R.id.tv_item_statement_money);
			convertView.setTag(holder);
		} else {
			holder = (StatemetnsHolder) convertView.getTag();
		}

		StatementsItem statementsItem = getItem(position);

		holder.tv_item_submit_date.setText(statementsItem.submit_date);
		holder.tv_item_statement_car_plat.setText(statementsItem.plat);
		holder.tv_item_statement_money.setText(statementsItem.money+"元");

		return convertView;
	}

	static class StatemetnsHolder {

		private TextView tv_item_submit_date, tv_item_statement_car_plat,
				tv_item_statement_money;
	}

}
