package com.wxxiaomi.ming.electricbicycle.ui.weight.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.easeui.utils.EaseUserUtils;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.dao.bean.InviteMessage;
import com.wxxiaomi.ming.electricbicycle.support.easemob.EmHelper2;
import com.wxxiaomi.ming.electricbicycle.ui.weight.custom.CircularImageView;


public class NewFriendAddItemAdapter extends RecyclerView.Adapter<ViewHolder> {

	private List<InviteMessage> infos;
	private Context context;
	ItemAddOnClick lis;

	public List<InviteMessage> getInfoList() {
		return infos;
	}

	public void refersh() {
		//获取全部的邀请消息
//		List<InviteMessage> msgs = EmHelper2.getInstance().getInviteMsgList();
//		infos.clear();
//		infos.addAll(msgs);
//		notifyDataSetChanged();
	}

	@Override
	public int getItemViewType(int position) {
		if(infos.size() == 0){
			return 123;
		}else{
			return 321;
		}

	}

	public NewFriendAddItemAdapter(Context context, List<InviteMessage> info,
								   ItemAddOnClick lis) {
		super();
		infos = new ArrayList<InviteMessage>();
		this.context = context;
		this.infos = info;
		this.lis = lis;
	}

	public void addInfo(InviteMessage info) {
		infos.add(info);
	}

	public void clear() {
		infos.clear();

	}

	//public void update() {
//		notifyItemChanged(infos.size());
//	}

	@Override
	public int getItemCount() {
		return infos.size()==0?1:infos.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, final int position) {
		if(viewHolder instanceof ItemViewHolder){
			InviteMessage info = infos.get(position);
			ItemViewHolder holder = (ItemViewHolder) viewHolder;
//			final UserCommonInfo userCommonInfo = userInfos.get(position);
			EaseUserUtils.setUserNick(infos.get(position).getFrom(),holder.tv_name);
//			holder.tv_name.setText(userCommonInfo.name);
			holder.tv_reason.setText(info.getReason());
//			holder.rl_item.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					Bundle bundle = new Bundle();
//					bundle.putSerializable("userInfo", userCommonInfo);
//					Intent intent4 = new Intent(context, UserInfoAct.class);
//					intent4.putExtra("value", bundle);
//					context.startActivity(intent4);
//				}
//			});
			EaseUserUtils.setUserHead(context,infos.get(position).getFrom(),holder.iv_head);
			holder.ib_contact.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 添加好友
					lis.onClick(infos.get(position).getFrom());
				}
			});
		}else if(viewHolder instanceof NoDataViewHolder){

		}


	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		Log.i("wang","NewFriendAddItemAdapter->onCreateViewHolder，viewType="+viewType);
		switch (viewType){
			case 123:
				//没有数据
				View view1 = LayoutInflater.from(context).inflate(
						R.layout.item_recycleview_nodata, viewGroup, false);
				return new NoDataViewHolder(view1);

			case 321:
				//正常item
				View view = LayoutInflater.from(context).inflate(
						R.layout.item_new_friend_add, viewGroup, false);
				return new ItemViewHolder(view);
		}
		return null;
	}

	public class ItemViewHolder extends ViewHolder {
		public TextView tv_name;
		public TextView tv_reason;
		public ImageButton ib_contact;
		public RelativeLayout rl_item;
		public CircularImageView iv_head;

		public ItemViewHolder(View view) {
			super(view);
			// tv_name = (TextView) view.findViewById(R.id.tv_name);
			// tv_info = (TextView) view.findViewById(R.id.tv_info);
			// tv_collectCount = (TextView)
			// view.findViewById(R.id.tv_collectCount);
			// tv_borrow = (TextView) view.findViewById(R.id.tv_borrow);
			// tv_number = (TextView) view.findViewById(R.id.tv_number);
			tv_name = (TextView) view.findViewById(R.id.tv_name);
			tv_reason = (TextView) view.findViewById(R.id.tv_reason);
			ib_contact = (ImageButton) view.findViewById(R.id.ib_contact);
			rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
			iv_head = (CircularImageView) view.findViewById(R.id.iv_head);
			// rl_item.setOnClickListener(this);
			// view.setOnClickListener(this);

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

	public class NoDataViewHolder extends ViewHolder {
//		public TextView tv_name;
//		public TextView tv_reason;
//		public ImageButton ib_contact;
//		public RelativeLayout rl_item;
//		public CircularImageView iv_head;

		public NoDataViewHolder(View view) {
			super(view);
			// tv_name = (TextView) view.findViewById(R.id.tv_name);
			// tv_info = (TextView) view.findViewById(R.id.tv_info);
			// tv_collectCount = (TextView)
			// view.findViewById(R.id.tv_collectCount);
			// tv_borrow = (TextView) view.findViewById(R.id.tv_borrow);
			// tv_number = (TextView) view.findViewById(R.id.tv_number);
//			tv_name = (TextView) view.findViewById(R.id.tv_name);
//			tv_reason = (TextView) view.findViewById(R.id.tv_reason);
//			ib_contact = (ImageButton) view.findViewById(R.id.ib_contact);
//			rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
//			iv_head = (CircularImageView) view.findViewById(R.id.iv_head);
//			// rl_item.setOnClickListener(this);
			// view.setOnClickListener(this);

		}


	}

	public interface ItemAddOnClick {
		void onClick(String userEmName);
	}

}
