package com.wxxiaomi.ming.electricbicycle.view.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.navisdk.adapter.BNRouteGuideManager;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviBaseCallbackModel;
import com.baidu.navisdk.adapter.BaiduNaviCommonModule;
import com.baidu.navisdk.adapter.NaviModuleFactory;
import com.baidu.navisdk.adapter.NaviModuleImpl;
import com.wxxiaomi.ming.electricbicycle.R;


/**
 * 导航页面
 * @author Mr.W
 *
 */
public class BaiduGuideActivity extends Activity {

	  private BNRoutePlanNode mBNRoutePlanNode = null;
	  private BaiduNaviCommonModule mBaiduNaviCommonModule = null;

	    /*
	     * 对于导航模块有两种方式来实现发起导航。 1：使用通用接口来实现 2：使用传统接口来实现
	     */
	    // 是否使用通用接口
	  private boolean useCommonInterface = true;
	
	  @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 createHandler();
	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	        }
	        View view = null;
	        if (useCommonInterface) {
	            //使用通用接口
	            mBaiduNaviCommonModule = NaviModuleFactory.getNaviModuleManager().getNaviCommonModule(
	                    NaviModuleImpl.BNaviCommonModuleConstants.ROUTE_GUIDE_MODULE, this,
	                    BNaviBaseCallbackModel.BNaviBaseCallbackConstants.CALLBACK_ROUTEGUIDE_TYPE, mOnNavigationListener);
	            if(mBaiduNaviCommonModule != null) {
	                mBaiduNaviCommonModule.onCreate();
	                view = mBaiduNaviCommonModule.getView();
	            }
	            
	        } else {
	            //使用传统接口
	            view = BNRouteGuideManager.getInstance().onCreate(this,mOnNavigationListener);
	        }
	        

	        if (view != null) {
	            setContentView(view);
	        }

	        Intent intent = getIntent();
	        if (intent != null) {
	            Bundle bundle = intent.getExtras();
	            if (bundle != null) {
	                mBNRoutePlanNode = (BNRoutePlanNode) bundle.getSerializable("routePlanNode");

	            }
	        }

	}
	
	@Override
    protected void onResume() {
        super.onResume();
        if(useCommonInterface) {
            if(mBaiduNaviCommonModule != null) {
                mBaiduNaviCommonModule.onResume();
            }
        } else {
            BNRouteGuideManager.getInstance().onResume();
        }
        
      
        if (hd != null) {
            hd.sendEmptyMessageAtTime(MSG_SHOW, 2000);
        }
    }

    protected void onPause() {
        super.onPause();
        
        if(useCommonInterface) {
            if(mBaiduNaviCommonModule != null) {
                mBaiduNaviCommonModule.onPause();
            }
        } else {
            BNRouteGuideManager.getInstance().onPause();
        }
      
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(useCommonInterface) {
            if(mBaiduNaviCommonModule != null) {
                mBaiduNaviCommonModule.onDestroy();
            }
        } else {
            BNRouteGuideManager.getInstance().onDestroy();
        }
//        BNDemoMainActivity.activityList.remove(this);
      
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(useCommonInterface) {
            if(mBaiduNaviCommonModule != null) {
                mBaiduNaviCommonModule.onStop();
            }
        } else {
            BNRouteGuideManager.getInstance().onStop();
        }
       
    }

    @Override
    public void onBackPressed() {
        if(useCommonInterface) {
            if(mBaiduNaviCommonModule != null) {
                mBaiduNaviCommonModule.onBackPressed(false);
            }
        } else {
            BNRouteGuideManager.getInstance().onBackPressed(false);
        }
    }

    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(useCommonInterface) {
            if(mBaiduNaviCommonModule != null) {
                mBaiduNaviCommonModule.onConfigurationChanged(newConfig);
            }
        } else {
            BNRouteGuideManager.getInstance().onConfigurationChanged(newConfig);
        }

    };

    @SuppressWarnings("deprecation")
	private void addCustomizedLayerItems() {
        List<BNRouteGuideManager.CustomizedLayerItem> items = new ArrayList<BNRouteGuideManager.CustomizedLayerItem>();
        BNRouteGuideManager.CustomizedLayerItem item1 = null;
        if (mBNRoutePlanNode != null) {
            item1 = new BNRouteGuideManager.CustomizedLayerItem(mBNRoutePlanNode.getLongitude(), mBNRoutePlanNode.getLatitude(),
                    mBNRoutePlanNode.getCoordinateType(), getResources().getDrawable(R.drawable.ic_launcher),
                    BNRouteGuideManager.CustomizedLayerItem.ALIGN_CENTER);
            items.add(item1);

            BNRouteGuideManager.getInstance().setCustomizedLayerItems(items);
        }
        BNRouteGuideManager.getInstance().showCustomizedLayer(true);
    }

    private static final int MSG_SHOW = 1;
    private static final int MSG_HIDE = 2;
    private static final int MSG_RESET_NODE = 3;
    private Handler hd = null;

    private void createHandler() {
        if (hd == null) {
            hd = new Handler(getMainLooper()) {
                public void handleMessage(android.os.Message msg) {
                    if (msg.what == MSG_SHOW) {
                        addCustomizedLayerItems();
                    } else if (msg.what == MSG_HIDE) {
                        BNRouteGuideManager.getInstance().showCustomizedLayer(false);
                    } else if (msg.what == MSG_RESET_NODE) {
                        BNRouteGuideManager.getInstance().resetEndNodeInNavi(
                                new BNRoutePlanNode(116.21142, 40.85087, "百度大厦11", null, BNRoutePlanNode.CoordinateType.GCJ02));
                    }
                };
            };
        }
    }

    private BNRouteGuideManager.OnNavigationListener mOnNavigationListener = new BNRouteGuideManager.OnNavigationListener() {

        @Override
        public void onNaviGuideEnd() {
            finish();
        }

        @Override
        public void notifyOtherAction(int actionType, int arg1, int arg2, Object obj) {

            if (actionType == 0) {
//                Log.i(TAG, "notifyOtherAction actionType = " + actionType + ",导航到达目的地！");
            }

//            Log.i(TAG, "actionType:" + actionType + "arg1:" + arg1 + "arg2:" + arg2 + "obj:" + obj.toString());
        }

    };


}

