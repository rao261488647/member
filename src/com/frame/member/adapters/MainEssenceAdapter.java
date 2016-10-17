package com.frame.member.adapters;

import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.Utils.DateUtil;
import com.frame.member.bean.MainEssenceBean.EssenceInfo;
import com.frame.member.widget.swipemenulistview.BaseSwipListAdapter;
import com.ta.utdid2.android.utils.StringUtils;
/**
 * 适配器
 * @author Ron
 * @date 2016-8-20  上午12:14:33
 */
public class MainEssenceAdapter extends BaseSwipListAdapter {
	private Context context;
	private List<EssenceInfo> mAppList;
	public MainEssenceAdapter(Context context,
			 List<EssenceInfo> mAppList) {
		this.context = context;
		this.mAppList = mAppList;
	}
	
	

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public EssenceInfo getItem(int position) {
        return mAppList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context,
                    R.layout.item_main_essence, null);
            holder = new ViewHolder();
            
//            holder.my_Essence_name = (TextView) convertView.findViewById(R.id.my_Essence_name);
//            holder.my_Essence_money = (TextView) convertView.findViewById(R.id.my_Essence_money);
//            holder.my_Essence_cancel = (TextView) convertView.findViewById(R.id.my_Essence_cancel);
//            holder.my_Essence_date = (TextView) convertView.findViewById(R.id.my_Essence_date);
            
            convertView.setTag(holder);
        } else {
			holder = (ViewHolder) convertView.getTag();
		}
        EssenceInfo item = getItem(position);
        
        return convertView;
    }


    @Override
    public boolean getSwipEnableByPosition(int position) {
        if(position % 2 == 0){
            return false;
        }
        return true;
    }

    class ViewHolder {
        TextView my_Essence_name,my_Essence_money,my_Essence_cancel,
        my_Essence_date;
    }
}
