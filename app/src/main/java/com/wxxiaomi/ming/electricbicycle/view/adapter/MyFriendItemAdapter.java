package com.wxxiaomi.ming.electricbicycle.view.adapter;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.view.custom.CircularImageView;


public class MyFriendItemAdapter extends RecyclerView.Adapter<ViewHolder> {

	private Context context;
	private List<User.UserCommonInfo> userInfos;
	private OnItemClick lis;

//	public List<InviteMessage> getInfoList() {
//		return infos;
//	}

	public MyFriendItemAdapter(Context context, List<User.UserCommonInfo> userInfos, OnItemClick lis) {
		super();
		this.context = context;
		this.userInfos = userInfos;
		this.lis = lis;
	}



	@Override
	public int getItemCount() {
		return userInfos.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, final int position) {
		ItemViewHolder holder = (ItemViewHolder) viewHolder;
		final User.UserCommonInfo userCommonInfo = userInfos.get(position);
		holder.tv_name.setText(userCommonInfo.name);
		Glide.with(context).load(userCommonInfo.head).into(holder.iv_my_head);
//		holder.ib_contact.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// 添加好友
//				lis.onClick(userCommonInfo);
//			}
//		});
		holder.ll_all.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			lis.onClick(userCommonInfo);
				
			}
		});

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.item_contact, viewGroup, false);
		return new ItemViewHolder(view);
	}

	public class ItemViewHolder extends ViewHolder {
		public TextView tv_name;
		public CircularImageView iv_my_head;
		public LinearLayout ll_all;

		public ItemViewHolder(View view) {
			super(view);
			tv_name = (TextView) view.findViewById(R.id.tv_name);
			iv_my_head = (CircularImageView) view.findViewById(R.id.iv_my_head);
			ll_all = (LinearLayout) view.findViewById(R.id.ll_all);
		}


		// @Override
		// public void onClick(View v) {
		// Log.i("wang", "getAdapterPosition()="+getAdapterPosition());
		// Log.i("wang", "getItemId="+getItemId());
		// Log.i("wang", "getLayoutPosition()="+getLayoutPosition());
		// Log.i("wang", "getPosition()="+getPosition());
		// lis.click(getAdapterPosition());
		// }
	}

	public interface OnItemClick{
		void onClick(User.UserCommonInfo info);
	}
	
	
}
