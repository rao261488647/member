package com.frame.member.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.bean.MyCollectBean.Badge;
import com.frame.member.bean.MyCollectBean.CollectCoach;
import com.frame.member.slideItem.MessageItem;
import com.frame.member.slideItem.SlideView;
import com.frame.member.slideItem.SlideView.OnSlideListener;

public class SlideAdapter extends BaseAdapter implements OnSlideListener {

	private List<MessageItem> mMessageItems;
	private Context mContext;
	private SlideView mLastSlideViewWithStatusOn;

	/**
	 * @param context
	 * @param messageItems显示的内容
	 */
	public SlideAdapter(Context context, List<MessageItem> messageItems) {
		super();
		this.mContext = context;
		this.mMessageItems = messageItems;
	}

	@Override
	public int getCount() {
		return mMessageItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mMessageItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		SlideView slideView = (SlideView) convertView;
		if (slideView == null) {
			View itemView = View.inflate(mContext,
					R.layout.item_my_collect_coach, null);
			// 将item显示的内容设置到Item对象中
			slideView = new SlideView(mContext);
			slideView.setContentView(itemView);

			holder = new ViewHolder(slideView);
			slideView.setOnSlideListener(SlideAdapter.this);
			slideView.setTag(holder);
		} else {
			holder = (ViewHolder) slideView.getTag();
		}
		MessageItem item = mMessageItems.get(position);
		CollectCoach coach = item.getCollectCoach();
		item.setSlideView(slideView);
		item.getSlideView().shrink();
        TTApplication.getInstance().disPlayImageDef(coach.headImg, holder.my_collect_item_img_1);
        if(coach.badges != null && coach.badges.size() > 0){
        	for(Badge b : coach.badges){
        		ImageView v = new ImageView(mContext);
        		if(b.badgeName.equals("表演队")){
        			v.setImageResource(R.drawable.icon_act_2x);
        		}
        		if(b.badgeName.equals("裁判队")){
        			v.setImageResource(R.drawable.icon_referee_2x);
        		}
        		if(b.badgeName.equals("培训队")){
        			v.setImageResource(R.drawable.icon_train_2x);
        		}
        		holder.badgeLayout.addView(v);
        	}
        }
        holder.my_collect_item_text_1.setText(coach.coachName);
        holder.my_collect_item_text_2.setText(coach.specialty);
        holder.my_collect_ratingBar1.setRating(new Float(coach.hshLevel));
		
		holder.deleteHolder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (deleteItemListener != null) {
					deleteItemListener.deleteItem(v, position);
				}
			}
		});

		return slideView;
	}

	class ViewHolder {
        ImageView my_collect_item_img_1;
        LinearLayout badgeLayout;
        TextView my_collect_item_text_1,my_collect_item_text_2;
        RatingBar my_collect_ratingBar1;
        ViewGroup deleteHolder;
        public ViewHolder(View view) {
        	my_collect_item_img_1 = (ImageView) view.findViewById(R.id.my_collect_item_img_1);
        	badgeLayout =  (LinearLayout) view.findViewById(R.id.my_collect_item_ll);
        	my_collect_item_text_1 = (TextView) view.findViewById(R.id.my_collect_item_text_1);
        	my_collect_item_text_2 = (TextView) view.findViewById(R.id.my_collect_item_text_2);
        	my_collect_ratingBar1 = (RatingBar) view.findViewById(R.id.my_collect_ratingBar1);
        	deleteHolder = (ViewGroup) view.findViewById(R.id.holder);
        	view.setTag(this);
        }
    }

	@Override
	public void onSlide(View view, int status) {

		if (mLastSlideViewWithStatusOn != null
				&& mLastSlideViewWithStatusOn != view) {//不为空，并且点击的不是当前的View。
			mLastSlideViewWithStatusOn.shrink();// 回到圆点
		}

		if (status == SLIDE_STATUS_ON) {
			mLastSlideViewWithStatusOn = (SlideView) view;
		}

	}

	public interface DeleteItemListener {
		public void deleteItem(View view, int position);
	}

	private DeleteItemListener deleteItemListener;

	/**
	 * @param deleteItemListener
	 *            设置删除Item监听事件
	 */
	public void setDeleteItemListener(DeleteItemListener deleteItemListener) {
		this.deleteItemListener = deleteItemListener;
	}

}
