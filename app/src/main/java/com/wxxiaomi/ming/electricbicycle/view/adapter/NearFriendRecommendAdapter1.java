package com.wxxiaomi.ming.electricbicycle.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.bean.format.NearByPerson;
import com.wxxiaomi.ming.electricbicycle.view.activity.UserInfoActivity;
import com.wxxiaomi.ming.electricbicycle.view.adapter.base.BaseAdapter;
import com.wxxiaomi.ming.electricbicycle.view.custom.CircularImageView;

import java.util.List;

/**
 * 附近好友推荐的adapter
 * 总共需要三种状态：
 * 1.加载数据ing
 * 2.加载失败
 * 3.无数据
 * 4.有item
 */
public class NearFriendRecommendAdapter1 extends BaseAdapter {

	private Context context;
		private List<NearByPerson.UserLocatInfo> userInfos;

	public NearFriendRecommendAdapter1(Context context, List<NearByPerson.UserLocatInfo> userInfos) {
		super(context);
		this.context = context;
		this.userInfos = userInfos;
	}

	@Override
	public ViewHolder onCreateViewHolderOnChild(ViewGroup viewGroup, int viewType) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.item_near_friend_recommend, viewGroup, false);
		return new ItemViewHolder(view);
	}

	@Override
	public void onBindViewHolderOnChild(ViewHolder viewHolder, int position) {
		if(viewHolder instanceof ItemViewHolder){
			ItemViewHolder holder = (ItemViewHolder) viewHolder;
			final NearByPerson.UserLocatInfo userCommonInfo = userInfos.get(position);
			holder.tv_name.setText(userCommonInfo.userCommonInfo.name);
			holder.tv_reason.setText("用户描述");
			holder.rl_item.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Bundle bundle = new Bundle();
					bundle.putSerializable("userInfo", userCommonInfo.userCommonInfo);
					Intent intent4 = new Intent(context, UserInfoActivity.class);
					intent4.putExtra("value", bundle);
					context.startActivity(intent4);
				}
			});
			Glide.with(context).load(userCommonInfo.userCommonInfo.head).into(holder.iv_head);
		}
	}

	@Override
	public int getItemCountOnChild() {
		return userInfos.size();
	}


//	private Context context;
//	private List<NearByPerson.UserLocatInfo> userInfos;
//	//private final int LOADINGDATAING = 1;
//	//private final int LOADINGFAIL = 2;
//	//private final int NODATA = 3;
//	private final int HAVADATA = 4;
//	private final int OTHERSTATE = 5;
//
//	/**
//	 * 加载是否完成
//	 */
//	private boolean loadingComplete;
//	/**
//	 * 是否加载失败
//	 */
//	private boolean loadingFail;
//
//	/**
//	 * 加载数据失败
//	 */
//	public void setLoadingFail(){
//		loadingFail = true;
//
//		notifyDataSetChanged();
//
//	}
//
//	/**
//	 * 加载数据完成
//	 */
//	public void setLoadingComplete(){
//		loadingComplete = true;
//		notifyDataSetChanged();
//		Log.i("wang","在NearFriendRecommendAdapter中完成数据获取后userInfos.size="+userInfos.size());
//	}
//
//	public NearFriendRecommendAdapter1(Context context, List<NearByPerson.UserLocatInfo> userInfos) {
//		super();
//		this.context = context;
//		this.userInfos = userInfos;
//		loadingComplete = false;
//		loadingFail = false;
//	}
//
//	@Override
//	public int getItemViewType(int position) {
////		if(!loadingComplete){
////			//正在加载
////			return LOADINGDATAING;
////		}else if(loadingComplete && userInfos.size() ==0){
////			//加载完成，但没有数据
////			return NODATA;
////		}else if(loadingComplete){
////			//加载完成有数据
////			return HAVADATA;
////		}else if(loadingComplete && loadingFail){
////			//加载完成，但加载失败
////			return LOADINGFAIL;
////		}
////		return 0;
//		if(userInfos.size() == 0){
//			return OTHERSTATE;
//		}else{
//			return HAVADATA;
//		}
//	}
//
//	@Override
//	public int getItemCount() {
//
//		return userInfos.size()==0?1:userInfos.size();
//	}
//
//	@Override
//	public void onBindViewHolder(ViewHolder viewHolder, final int position) {
//		if(viewHolder instanceof ItemViewHolder){
//			ItemViewHolder holder = (ItemViewHolder) viewHolder;
//			final NearByPerson.UserLocatInfo userCommonInfo = userInfos.get(position);
//			holder.tv_name.setText(userCommonInfo.userCommonInfo.name);
//			holder.tv_reason.setText("用户描述");
//			holder.rl_item.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					Bundle bundle = new Bundle();
//					bundle.putSerializable("userInfo", userCommonInfo.userCommonInfo);
//					Intent intent4 = new Intent(context, UserInfoActivity.class);
//					intent4.putExtra("value", bundle);
//					context.startActivity(intent4);
//				}
//			});
//			Glide.with(context).load(userCommonInfo.userCommonInfo.head).into(holder.iv_head);
//		}else if(viewHolder instanceof OtherStateViewHolder){
//			OtherStateViewHolder holder = (OtherStateViewHolder) viewHolder;
//			if(!loadingComplete){
//				//正在加载中
//				holder.rl_loading.setVisibility(View.VISIBLE);
//				holder.rl_fail.setVisibility(View.GONE);
//				holder.rl_nodata.setVisibility(View.GONE);
//			}else if(loadingComplete && loadingFail){
//				holder.rl_loading.setVisibility(View.GONE);
//				holder.rl_fail.setVisibility(View.VISIBLE);
//				holder.rl_nodata.setVisibility(View.GONE);
//			}else if(loadingComplete && userInfos.size() == 0){
//				holder.rl_loading.setVisibility(View.GONE);
//				holder.rl_fail.setVisibility(View.GONE);
//				holder.rl_nodata.setVisibility(View.VISIBLE);
//			}
//		}
//
//	}
//
//	@Override
//	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//		View view;
//		switch (viewType){
////			case LOADINGDATAING:
////				//正在加载数据
////			case LOADINGFAIL:
////				//加载数据失败
////			case NODATA:
////				//没有数据
//			case OTHERSTATE:
//				view = LayoutInflater.from(context).inflate(
//						R.layout.item_recycleview_state, viewGroup, false);
//				return new OtherStateViewHolder(view);
//			case HAVADATA:
//				//有数据
//				view = LayoutInflater.from(context).inflate(
//						R.layout.item_near_friend_recommend, viewGroup, false);
//				return new ItemViewHolder(view);
//		}
//		return null;
//	}
//
	public class ItemViewHolder extends ViewHolder{
		public TextView tv_name;
		public TextView tv_reason;
		public RelativeLayout rl_item;
		public CircularImageView iv_head;

		public ItemViewHolder(View view) {
			super(view);
			tv_name = (TextView) view.findViewById(R.id.tv_name);
			tv_reason = (TextView) view.findViewById(R.id.tv_description);
			rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
			iv_head = (CircularImageView) view.findViewById(R.id.iv_head);
		}
	}
//
//	public class OtherStateViewHolder extends ViewHolder{
//		public RelativeLayout rl_fail;
//		public RelativeLayout rl_loading;
//		public RelativeLayout rl_nodata;
//		public OtherStateViewHolder(View view) {
//			super(view);
//			rl_fail = (RelativeLayout) view.findViewById(R.id.rl_fail);
//			rl_loading = (RelativeLayout) view.findViewById(R.id.rl_loading);
//			rl_nodata = (RelativeLayout) view.findViewById(R.id.rl_nodata);
//		}
//	}
}
