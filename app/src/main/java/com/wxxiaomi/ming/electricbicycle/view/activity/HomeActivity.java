package com.wxxiaomi.ming.electricbicycle.view.activity;//package com.wxxiaomi.electricbicycle.view.activity;
//
//
//import android.content.Intent;
//import android.view.View;
//
//import com.wxxiaomi.electricbicycle.R;
//import com.wxxiaomi.electricbicycle.view.activity.base.BaseActivity;
//
//public class HomeActivity extends BaseActivity {
//
////	/**
////	 * 注意：mapView 需要处理 onResume onPause desotroy
////	 */
////	protected MapView mapview;
////	/**
////	 * 地图引擎管理工具
////	 */
////	protected BMapManager manager;
////	/**
////	 *  地图的管理工具——控制地图的缩放和移动（旋转）
////	 */
////	protected MapController controller;
////	/**
////	 * 定位相关的驱动器
////	 */
////	public LocationClient mLocationClient = null;
////	/**
////	 * 定位成功后的监听器
////	 */
////	public BDLocationListener myListener;
////	/**
////	 * 点击后弹出的pop
////	 */
////	private View pop;
////	/**
////	 * pop的title
////	 */
////	private TextView title;
////	/**
////	 * 标识是否是第一次定位 根据这个标识做相关处理
////	 */
////	private boolean isFirstLocat = true;
////	/**
////	 * 地图覆盖物
////	 * 自我定位成功后的覆盖物
////	 */
////	private MyLocationOverlay overlay;
////	/**
////	 * 标识上次定位的结果，用于处理下次定位判断是否结果一样
////	 * 如果一样就不用更新ui
////	 */
////	private double lastLatitude = 0.0;
////	
////	private Button btn_route;
////	private Button btn_personal;
////	private Button btn_contact;
////	
////
//	
//	
//	@Override
//	protected void initView() {
////		checkBaiduKey();
////		myListener = new MyLocationListener();
////		setContentView(R.layout.activity_home);
////		btn_route = (Button) findViewById(R.id.btn_route);
////		btn_personal = (Button) findViewById(R.id.btn_personal);
////		btn_contact = (Button) findViewById(R.id.btn_contact);
////		btn_route.setOnClickListener(this);
////		btn_personal.setOnClickListener(this);
////		btn_contact.setOnClickListener(this);
////		mapview = (MapView) findViewById(R.id.mpaview);
////		initBaiduMap();
////		pop = View.inflate(this, R.layout.pop, null);
////		title = (TextView) pop.findViewById(R.id.title);
////
////		MapView.LayoutParams layoutParams = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
////				MapView.LayoutParams.WRAP_CONTENT, null, MapView.LayoutParams.BOTTOM_CENTER);
////		pop.setVisibility(View.INVISIBLE);
////		mapview.addView(pop, layoutParams);
////
//	}
////
//	/**
//	 * 初始化参数
//	 */
//	@Override
//	protected void initData() {
////		initLocationMap();
//	}
////
////	/**
////	 * 设置百度地图的一些必要参数
////	 */
////	private void initBaiduMap() {
////		// ②放大和缩小的按钮——MapView extends ViewGroup
////		mapview.setBuiltInZoomControls(true);// addView
////
////		// ①放大级别
////		controller = mapview.getController();// 对于Controller 管理某个具体的mapview信息
////		controller.setZoom(18);// 放大级别的设置(在V2.0以后的版本支持3-19的放大级别，如果是V1.x3-18)
////
////	}
////
////	/**
////	 * 初始化定位,注册监听,开启定位
////	 */
////	private void initLocationMap() {
////		mLocationClient = new LocationClient(getApplicationContext());
////		LocationClientOption option = new LocationClientOption();
////		// option.setLocationMode(LocationClientOption.Hight_Accuracy);
////		option.setOpenGps(true);
////		option.setAddrType("all");// 返回的定位结果包含地址信息
////		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
////		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
////		option.disableCache(true);// 禁止启用缓存定位
////		option.setPoiNumber(5); // 最多返回POI个数
////		option.setPoiDistance(1000); // poi查询距离
////		option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
////		mLocationClient.setLocOption(option);
////		// myListener = new MyLocationListener();
////		mLocationClient.registerLocationListener(myListener);
////		mLocationClient.start();
////
////	}
////
////	
////
////	/**
////	 * 处理定位返回的结果
////	 * 
////	 * @param location
////	 */
////	private void ProcessLocatResult(BDLocation location) {
////		LocationData data = new LocationData();
////		data.latitude = location.getLatitude();
////		data.longitude = location.getLongitude();
////		if (location.getLatitude() == 4.9E-324) {
////			// 地图定位不到，这次不做处理
////			return;
////		}
////		if(isFirstLocat){
////			//如果是第一次定位，做服务器相关的操作
//////			Log.i("wang", "向服务器发送获取附近的人请求");
////			getNearByFromServer(data.latitude,data.longitude);
////		}
////		if (lastLatitude != data.latitude) {
////			// 如果定位出来的结果和上次不一样
////			if (overlay != null) {
////				// 清除原先定位的那个点，更新
////				mapview.getOverlays().remove(overlay);
////			}
////			overlay = new MyLocationOverlay(mapview);
////			// 经纬度的数据获取--
////			overlay.setData(data);
////			mapview.getOverlays().add(overlay);
////			mapview.refresh();
////			Log.i("wang", "data.latitude=" + data.latitude
////					+ "--data.longitude=" + data.longitude);
////
////			// 移动地图
////			if (isFirstLocat) {
////				int latitude = (int) (data.latitude * 1E6);
////				int longitude = (int) (data.longitude * 1E6);
////				GeoPoint geoPoint = new GeoPoint(latitude, longitude);
////				controller.animateTo(geoPoint);
////			}
////		}
////		isFirstLocat = false;
////	}
////
////	/**
////	 * 获取附近的人
////	 * 发送自己的坐标给服务器
////	 * 服务器回送附近的人
////	 * @param latitude  经度	
////	 * @param longitude 纬度
////	 */
////	private void getNearByFromServer(final double latitude, final double longitude) {
////		new AsyncTask<String, Void, ReceiceData<NearByPerson>>() {
////			@Override
////			protected ReceiceData<NearByPerson> doInBackground(String... params) {
////				MapEngineImpl engine = new MapEngineImpl();
////				return engine.getNearByFromServer(latitude, longitude);
////			}
////
////			@Override
////			protected void onPostExecute(ReceiceData<NearByPerson> result) {
////				if (result!=null) {
////					if(result.state == 200){
////						//新增覆盖物
////						processNearByData(result.infos.userLocatList);
////					}else{
////						Log.i("wang", "登录失败，错误信息："+result.error);
////					}
////				} else {
////					Log.i("wang", "登录失败，连接不上服务器");
////				}
////			}
////		}.execute();
////		
////	}
////
////	
////	
////	/**
////	 * 处理获取附近的人结果
////	 * @param userLocatList
////	 */
////	protected void processNearByData(List<UserLocatInfo> userLocatList) {
////		@SuppressWarnings("deprecation")
////		ItemizedOverlay<OverlayItem> overlay = new ItemizedOverlay<OverlayItem>(getResources().getDrawable(R.drawable.eat_icon)
////				, mapview){
////			
////			@Override
////			protected boolean onTap(int index) {
////				pop.setVisibility(View.VISIBLE);
////				// 显示的位置
////
////				OverlayItem item = getItem(index);
////				// item.getTitle();
////				// item.getPoint();
////
////				// 修改显示的位置
////				MapView.LayoutParams layoutParams = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
////						MapView.LayoutParams.WRAP_CONTENT, item.getPoint(), MapView.LayoutParams.BOTTOM_CENTER);
////				mapview.updateViewLayout(pop, layoutParams);
////
////				title.setText(item.getTitle());
////				return super.onTap(index);
////			}
////		};
////		setOverLay(overlay,userLocatList);
////		mapview.getOverlays().add(overlay);
////		mapview.refresh();
////	}
////
////	/**
////	 * 根据点的个数设置覆盖物
////	 * @param overlay
////	 * @param userLocatList
////	 */
////	private void setOverLay(ItemizedOverlay<OverlayItem> overlay, List<UserLocatInfo> userLocatList) {
////		GeoPoint point;
////		OverlayItem item;
////		for(int i = 0;i<userLocatList.size();i++){
////			point = new GeoPoint((int)(userLocatList.get(i).locat[0]*1E6)
////					, (int)(userLocatList.get(i).locat[1]*1E6));
////			item = new OverlayItem(point, userLocatList.get(i).userCommonInfo.name, "附近的人");
////			overlay.addItem(item);
////		}
////		
////		
////	}
////
//	/**
//	 * 处理点击事件
//	 */
//	@Override
//	protected void processClick(View v) {
//		switch (v.getId()) {
//		case R.id.btn_contact:
//			//联系人页面
//			Intent intent = new Intent(ct,ContactActivity.class);
//			startActivity(intent);
//			break;
//
//		default:
//			break;
//		}
//
//	}
////
////	/**
////	 * 自定义监听
////	 * 用于获取位置成功后的回调
////	 * @author Administrator
////	 *
////	 */
////	public class MyLocationListener implements BDLocationListener {
////		@Override
////		public void onReceiveLocation(BDLocation location) {
////			if (location == null) {
////				Log.i("wang", "location == null");
////				return;
////			}
////
////			ProcessLocatResult(location);
////		}
////
////		public void onReceivePoi(BDLocation poiLocation) {
////			if (poiLocation == null) {
////				Log.i("wang", "poiLocation == null");
////				return;
////			}
////		}
////	}
////
////	/**
////	 * 检查百度key是否正确
////	 */
////	private void checkBaiduKey() {
////		// if(manager!=null)
////		manager = new BMapManager(getApplicationContext());
////		// strKey - 申请的授权验证码:null ""
////		// listener - 注册回调事件
////		manager.init(ConstantValue.KEY,
////				new MKGeneralListener() {
////					@Override
////					public void onGetNetworkState(int iError) {
////						// TODO 网络状态的判断(MKEvent常量信息)
////						if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
////							Log.i("wang", "无网络");
////						}
////
////					}
////
////					@Override
////					public void onGetPermissionState(int iError) {
////						// TODO 授权的验证
////						if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
////							Log.i("wang", "key授权失败");
////						}
////					}
////				});
////	}
////
////	@Override
////	protected void onResume() {
////		// 定位GPS——耗电
////		mLocationClient.start();// 开启定位
////		super.onResume();
////	}
////
////	@Override
////	protected void onPause() {
////		mLocationClient.stop();// 结束定位
////		super.onPause();
////	}
//
//}
