package com.wxxiaomi.ming.electricbicycle.view.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.baidu.navisdk.adapter.BaiduNaviManager.NaviInitListener;
import com.baidu.navisdk.adapter.BaiduNaviManager.RoutePlanListener;
import com.wxxiaomi.ming.electricbicycle.GlobalParams;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.view.activity.base.BaseActivity;


/**
 * 导航搜索页面
 * @author Mr.W
 *
 */
public class BaiduNavActivity extends BaseActivity {

	private Button btn_nav;
	
	@Override
	protected void initView() {
		setContentView(R.layout.activity_baidu_nav);
		btn_nav = (Button) findViewById(R.id.btn_nav);
		btn_nav.setOnClickListener(this);
		
	}

	@Override
	protected void initData() {
		initNav();
		
	}

	/**
	 * 初始化nav sdk
	 */
	private void initNav() {
		Log.i("wang", "初始化nav");
		BaiduNaviManager.getInstance().init(this, getSdcardDir(), "BNSDKSimpleDemo", new NaviInitListener() {
			@Override
			public void onAuthResult(int status, String msg) {
				if (0 == status) {
//					authinfo = "key校验成功!";
					Log.i("wang", "key校验成功");
				} else {
//					authinfo = "key校验失败, " + msg;
					Log.i("wang", "key校验失敗:" + msg);
				}
//				BNDemoMainActivity.this.runOnUiThread(new Runnable() {
//
//					@Override
//					public void run() {
//						Toast.makeText(BNDemoMainActivity.this, authinfo, Toast.LENGTH_LONG).show();
//					}
//				});
			}

			public void initSuccess() {
//				Toast.makeText(BNDemoMainActivity.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
//				initSetting();
				Log.i("wang", "百度导航引擎初始化成功");
				initSetting();
			}

			public void initStart() {
				Log.i("wang", "百度导航引擎初始化开始");
//				Toast.makeText(BNDemoMainActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
			}

			public void initFailed() {
				Log.i("wang", "百度导航引擎初始化失败");
//				Toast.makeText(BNDemoMainActivity.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
			}


		},  null, ttsHandler, null);
		
	}

	protected void initSetting() {
		 BNaviSettingManager.setDayNightMode(BNaviSettingManager.DayNightMode.DAY_NIGHT_MODE_DAY);
		    BNaviSettingManager.setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
		    BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);	    
	        BNaviSettingManager.setPowerSaveMode(BNaviSettingManager.PowerSaveMode.DISABLE_MODE);
	        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
		
	}

	@Override
	protected void processClick(View v) {
		switch (v.getId()) {
		case R.id.btn_nav:
			//开始导航
			routeplanToNavi(CoordinateType.WGS84);
			break;

		default:
			break;
		}
		
	}
	
	private void routeplanToNavi(CoordinateType wgs84) {
		//算路设置起、终点，算路偏好，是否模拟导航等参数
		BNRoutePlanNode sNode;
		BNRoutePlanNode eNode;
		sNode = new BNRoutePlanNode( GlobalParams.longitude,GlobalParams.latitude, "嘉应学院", null, wgs84);
		//24.2726780000,116.0893520000
						eNode = new BNRoutePlanNode(116.0893520000, 24.272678, "梅县政府", null, wgs84);
		//起始点和终点俩个点
		List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
		list.add(sNode);
		list.add(eNode);
		//主要代码
		BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new DemoRoutePlanListener(sNode));
	}
	
	public class DemoRoutePlanListener implements RoutePlanListener {
		private BNRoutePlanNode mBNRoutePlanNode = null;
		public DemoRoutePlanListener(BNRoutePlanNode node) {
			mBNRoutePlanNode = node;
		}
		@Override
		public void onJumpToNavigator() {
			/*
			 * 设置途径点以及resetEndNode会回调该接口
			 */
//			for (Activity ac : activityList) {
//				if (ac.getClass().getName().endsWith("BNDemoGuideActivity")) {
//					return;
//				}
//			}
			Intent intent = new Intent(ct, BaiduNavActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("routePlanNode", (BNRoutePlanNode) mBNRoutePlanNode);
			intent.putExtras(bundle);
			startActivity(intent);	
		}
		@Override
		public void onRoutePlanFailed() {
			// TODO Auto-generated method stub
//			Toast.makeText(BNDemoMainActivity.this, "算路失败", Toast.LENGTH_SHORT).show();
		}
	}


	private String getSdcardDir() {
		if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}
	
	/**
	 * 内部TTS播报状态回传handler
	 */
	private Handler ttsHandler = new Handler() {
	    public void handleMessage(Message msg) {
	        int type = msg.what;
	        switch (type) {
	            case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
//	                 showToastMsg("Handler : TTS play start");
	                 Log.i("wang", "Handler : TTS play start");
	                break;
	            }
	            case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
//	                 showToastMsg("Handler : TTS play end");
	                 Log.i("wang", "Handler : TTS play end");
	                break;
	            }
	            default :
	                break;
	        }
	    }
	};

}
