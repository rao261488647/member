package com.frame.member.adapters;

import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class LayerAdapter<T> extends BaseAdapter {

	protected List<T> datas;

	public LayerAdapter() {
		datas = new ArrayList<T>();
	}
	
	public LayerAdapter(List<T> datas){
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public T getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getItemView(position, convertView);
	}

	protected abstract View getItemView(int position, View convertView);
}
