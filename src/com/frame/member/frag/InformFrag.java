package com.frame.member.frag;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.NotifyInfoParser;
import com.frame.member.Utils.CommCallBack;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.MyLog;
import com.frame.member.Utils.SPUtils;
import com.frame.member.activity.BaseActivity;
import com.frame.member.activity.InfoDetailActivity;
import com.frame.member.activity.MainActivity;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.adapters.InfoAdapter;
import com.frame.member.bean.NotifyBean;
import com.frame.member.bean.NotifyListBean;
import com.frame.member.interfaces.NotifyDataObserver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * 通知
 * 
 * @author long
 * 
 */
public class InformFrag extends BaseFrag {

	private ListView lv_info;

	private List<NotifyBean> notifyDataList = new ArrayList<NotifyBean>();

	private InfoAdapter adapter;

	private NotifyDataObserver listener;

	public void setNotifyDataObserver(NotifyDataObserver listener) {
		if (listener != null)
			this.listener = listener;
	}

	public static InformFrag newInstance(String title) {
		InformFrag fragment = new InformFrag();
		Bundle bundle = new Bundle();
		bundle.putString(AppConstants.FRAG_TITLE_KEY, title);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		getActivity().registerReceiver(receiver, new IntentFilter(AppConstants.BC_PUSH_COMING));
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		getActivity().unregisterReceiver(receiver);
	}
	
	
	private BroadcastReceiver receiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			NotifyBean notifyData = (NotifyBean) intent.getSerializableExtra(MainActivity.TAG_NOTIFY_DATA);
			addNotifyData(notifyData);
		}};

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}

	public void addNotifyData(final NotifyBean notifyData) {
		if (!isViewCreated) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					addNotifyData(notifyData);
				}
			}, 1000);
			return;
		}
		if (notifyData == null)
			return;
		this.notifyDataList.add(0, notifyData);

		notifyAdapterDataChanged();

		if (this.listener != null)
			this.listener.onNotifyDataChanged(true);
	}

	private void notifyAdapterDataChanged() {
		if (adapter == null) {
			adapter = new InfoAdapter(getActivity(), notifyDataList);
			lv_info.setAdapter(adapter);
			lv_info.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					NotifyBean clickItemData = notifyDataList.get(position);
					clickItemData.isReaded = "yes";
					adapter.notifyDataSetChanged();

					sendNotice2Read(clickItemData);

					logicBtmNotice();
					
					Intent intent = new Intent(getActivity(),InfoDetailActivity.class);
					intent.putExtra(InfoDetailActivity.TAG_INTENT_NOTIFYBEAN, clickItemData);
					getActivity().startActivity(intent);
				}
			});
		} else
			adapter.notifyDataSetChanged();
	}

	private void logicBtmNotice() {
		boolean isUnReadedExist = false;
		for (NotifyBean data : notifyDataList)
			if (!"yes".equals(data.isReaded)) {
				isUnReadedExist = true;
				break;
			}
		if (listener != null)
			listener.onNotifyDataChanged(isUnReadedExist);
	}

	private void sendNotice2Read(NotifyBean clickItemData) {
		HttpRequest request = new HttpRequestImpl(mContext,
				AppConstants.NOTICETOREAD, null);
		request.addParam(
				"tel",
				(String) SPUtils.getAppSpUtil().get(getActivity(),
						SPUtils.KEY_PHONENUM, "")).addParam("noticeId",
								clickItemData.noticeId);

		mContext.getDataFromServer(request, new CommCallBack(mContext, "",new CommCallBack.CallBackSucc() {
			
			@Override
			public void onRequestSucc(JSONObject object) {
				
			}
		}));

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.frag_info, container, false);

		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("我收到的通知");

		lv_info = (ListView) findViewById(R.id.lv_info);

		getNotifyDataFromServer();

		return rootView;
	}

	private void getNotifyDataFromServer() {
		HttpRequest request_pay_info = new HttpRequestImpl(getActivity(),
				AppConstants.NOTICELIST, new NotifyInfoParser());

		request_pay_info.addParam(
				"tel",
				(String) SPUtils.getAppSpUtil().get(getActivity(),
						SPUtils.KEY_PHONENUM, ""));

		((BaseActivity) getActivity()).getDataFromServer(request_pay_info,
				false, new DataCallback<NotifyListBean>() {

					@Override
					public void processData(NotifyListBean object,
							RequestResult result) {
						if (result == RequestResult.Success) {

//							MyLog.i(TAG_FRAGMENT,
//									"通知接口返回信息----》" + object.toString());
							
							for(NotifyBean mNotifyBean : object.notifyList)
								MyLog.i(TAG_FRAGMENT,
										"通知接口返回信息----》" + mNotifyBean.isReaded);

							notifyDataList.addAll(object.notifyList);
							logicBtmNotice();
							notifyAdapterDataChanged();

						}
					}
				});
	}

}
