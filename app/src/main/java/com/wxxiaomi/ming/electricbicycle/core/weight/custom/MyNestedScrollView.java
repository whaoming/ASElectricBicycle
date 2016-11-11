package com.wxxiaomi.ming.electricbicycle.core.weight.custom;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class MyNestedScrollView extends NestedScrollView {

	 // 滑动距离及坐标  
    private float xDistance, yDistance, xLast, yLast;  
	
	public MyNestedScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	 @Override  
	    public boolean onInterceptTouchEvent(MotionEvent ev) {  
	        switch (ev.getAction()) {  
	            case MotionEvent.ACTION_DOWN:  
	                xDistance = yDistance = 0f;  
	                xLast = ev.getX();  
	                yLast = ev.getY();  
	                break;  
	            case MotionEvent.ACTION_MOVE:  
	                final float curX = ev.getX();  
	                final float curY = ev.getY();  
	  
	                xDistance += Math.abs(curX - xLast);  
	                yDistance += Math.abs(curY - yLast);  
	                xLast = curX;  
	                yLast = curY;  
	  
	                Log.i("wang", "xDistance="+xDistance+",yDistance="+yDistance);
	                if(xDistance > yDistance){  
	                    return false;  
	                }    
	        }  
	  
	        return super.onInterceptTouchEvent(ev);  
	    }  

}
