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
import com.frame.member.bean.MyBillBean.BillInfo;
import com.frame.member.widget.swipemenulistview.BaseSwipListAdapter;
import com.ta.utdid2.android.utils.StringUtils;
/**
 * 消费流水适配器
 * @author Ron
 * @date 2016-8-20  上午12:14:33
 */
public class MyBillAdapter extends BaseSwipListAdapter {
	private Context context;
	private List<BillInfo> mAppList;
	public MyBillAdapter(Context context,
			 List<BillInfo> mAppList) {
		this.context = context;
		this.mAppList = mAppList;
	}
	
	

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public BillInfo getItem(int position) {
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
                    R.layout.item_my_bill, null);
            holder = new ViewHolder();
            
            holder.my_bill_name = (TextView) convertView.findViewById(R.id.my_bill_name);
            holder.my_bill_money = (TextView) convertView.findViewById(R.id.my_bill_money);
            holder.my_bill_cancel = (TextView) convertView.findViewById(R.id.my_bill_cancel);
            holder.my_bill_date = (TextView) convertView.findViewById(R.id.my_bill_date);
            
            convertView.setTag(holder);
        } else {
			holder = (ViewHolder) convertView.getTag();
		}
        BillInfo item = getItem(position);
        
        holder.my_bill_name.setText(item.remarks);
        holder.my_bill_money.setText(item.money);
        if(!item.money.contains("-")){
        	holder.my_bill_money.setTextColor(R.color.yellow_e6);
        }
        //未取消，不显示取消提示
        if(!StringUtils.isEmpty(item.unsubscribe) && item.unsubscribe.equals("0")){
        	holder.my_bill_cancel.setVisibility(View.INVISIBLE);
        }
        //毫秒转日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(item.time);
        
        String newDate = DateUtil.getDateTime("yyyy年MM月dd日", calendar.getTime());
        holder.my_bill_date.setText(newDate);
        
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
        TextView my_bill_name,my_bill_money,my_bill_cancel,
        my_bill_date;
    }
}
