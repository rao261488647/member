package com.frame.member.activity;

import com.frame.member.R;
import com.frame.member.TTApplication;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.ClassDetailParser;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.bean.ClassDetailResult;
import com.frame.member.frag.ClassBookingDialogFrag;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ClassDetailActivity extends BaseActivity implements OnClickListener{
	private TextView tv_class_meet,tv_class_name,tv_members_price,tv_price_per_day,
					tv_item_time_num,tv_skifield_info;
	private RatingBar rb_booking_class;
	private ImageView iv_class_vedio_cover;
	
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_class_detail);
	}

	@Override
	protected void findViewById() {
		tv_class_meet = (TextView) findViewById(R.id.tv_class_meet);
		tv_class_name = (TextView) findViewById(R.id.tv_class_name);
		tv_members_price = (TextView) findViewById(R.id.tv_members_price);
		tv_price_per_day = (TextView) findViewById(R.id.tv_price_per_day);
		tv_item_time_num = (TextView) findViewById(R.id.tv_item_time_num);
		tv_skifield_info = (TextView) findViewById(R.id.tv_skifield_info);
		rb_booking_class = (RatingBar) findViewById(R.id.rb_booking_class);
		iv_class_vedio_cover = (ImageView) findViewById(R.id.iv_class_vedio_cover);
	}
	
	@Override
	protected void setListener() {
		tv_class_meet.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		tv_title.setText("课程详情");
		tv_title_right.setText("查看评论");
		tv_title_right.setTextColor(0xffffffff);
		iv_title_back.setImageResource(R.drawable.btn_back_normal);
		getData();
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_class_meet:
			ClassBookingDialogFrag frag = new ClassBookingDialogFrag();
			frag.setStyle(DialogFragment.STYLE_NORMAL, R.style.YouDialog);
			frag.show(getSupportFragmentManager(), "ClassBookingDialog");
			break;

		default:
			break;
		}
	}
	//获取主数据
	private void getData(){
		BaseParser<ClassDetailResult> parser = new ClassDetailParser();
		HttpRequest request = new HttpRequestImpl(this, AppConstants.APP_SORT_STUDENT + "/classinfo",
				parser);
		request.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_MEMBERUSERID, ""))
				.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""))
				.addParam("courseId", getIntent().getStringExtra("courseId"));
		DataCallback<ClassDetailResult> callBack = new DataCallback<ClassDetailResult>() {

			@Override
			public void processData(ClassDetailResult object, RequestResult result) {
				if(object != null){
					tv_class_name.setText(object.courseName);
					tv_members_price.setText("蓝卡价： "+object.discountPrice+"元/人");
					tv_price_per_day.setText("¥"+object.originalPrice);
					StringBuffer sb = new StringBuffer();
					sb.append(object.beginTime.replace('-', '.')).append("~")
						.append(object.overTime.replace('-', '.')).append("  共"+object.hadDay+"天");
					tv_item_time_num.setText(sb.toString());
					rb_booking_class.setRating(object.goal);
					TTApplication.getInstance().disPlayImageDef(object.videoPhoto, iv_class_vedio_cover);
					tv_skifield_info.setText(object.skifieldAddr);
				}
				
			}

		};
		getDataFromServer(request, callBack);
	}
}
