package com.wxxiaomi.ming.electricbicycle.view.activity;//package com.wxxiaomi.electricbicycle.view.activity;
//
//import java.util.List;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.BaseAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.wxxiaomi.electricbicycle.R;
//import com.wxxiaomi.electricbicycle.bean.User.UserCommonInfo;
//import com.wxxiaomi.electricbicycle.dao.impl.UserDaoImpl;
//import com.wxxiaomi.electricbicycle.view.activity.base.BaseActivity;
//
///**
// * 联系人activity
// * 
// * @author Administrator
// * 
// */
//public class ContactActivity extends BaseActivity {
//
//	private ListView lv_listview;
//	private DemoListAdapter adapter;
//	List<UserCommonInfo> friendList;
//
//	@Override
//	protected void initView() {
//		setContentView(R.layout.activity_contact);
//		lv_listview = (ListView) findViewById(R.id.lv_listview);
//	}
//
//	@Override
//	protected void initData() {
////		friendList = GlobalParams.friendList;
//		UserDaoImpl impl = new UserDaoImpl(ct);
//		friendList = impl.getFriendList();
////		Log.i("wang", "initData()中friendList.size=" + friendList.size());
//		adapter = new DemoListAdapter(friendList);
//		lv_listview.setAdapter(adapter);
//		lv_listview.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// demo中直接进入聊天页面，实际一般是进入用户详情页
//				Intent intent = new Intent(ct, ChatActivity.class).putExtra(
//						"userId", friendList.get(position).emname);
//				startActivity(intent);
//			}
//		});
//
//	}
//
//	@Override
//	protected void processClick(View v) {
//		// TODO Auto-generated method stub
//
//	}
//
//	public class DemoListAdapter extends BaseAdapter {
//		private List<UserCommonInfo> friendList;
//
//		public DemoListAdapter(List<UserCommonInfo> friendList) {
//			super();
//			this.friendList = friendList;
//		}
//
//		@SuppressLint("ViewHolder")
//		@Override
//		public View getView(int index, View convertView, ViewGroup parent) {
//			convertView = View.inflate(ct, R.layout.item_contact, null);
//			TextView tv_name = (TextView) convertView
//					.findViewById(R.id.tv_name);
//			TextView tv_emname = (TextView) convertView
//					.findViewById(R.id.tv_emname);
//			tv_name.setText(friendList.get(index).name);
//			tv_emname.setText(friendList.get(index).emname);
//			// TextView desc = (TextView) convertView.findViewById(R.id.desc);
//			// title.setText(DEMOS[index].title);
//			// desc.setText(DEMOS[index].desc);
//			// if (index >= 16) {
//			// title.setTextColor(Color.YELLOW);
//			// }
//			return convertView;
//		}
//
//		@Override
//		public int getCount() {
//			// return DEMOS.length;
//			return friendList.size();
//		}
//
//		@Override
//		public Object getItem(int index) {
//			// return DEMOS[index];
//			return friendList.get(index);
//		}
//
//		@Override
//		public long getItemId(int id) {
//			return id;
//		}
//	}
//
//}
