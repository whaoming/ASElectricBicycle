package com.wxxiaomi.ming.electricbicycle.view.custom;

import java.util.List;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class PersonalActButtonBehavior extends
		CoordinatorLayout.Behavior<FloatingActionButton> {

	public PersonalActButtonBehavior() {
	}

	public PersonalActButtonBehavior(Context context, AttributeSet attrs) {
		super(context, attrs);
		// this.
	}

	@Override
	public boolean layoutDependsOn(CoordinatorLayout parent,
			FloatingActionButton child, View dependency) {
		
		return true;
	}

	@Override
	public boolean onDependentViewChanged(CoordinatorLayout parent,
			final FloatingActionButton child, View dependency) {
		Log.i("wang", "onDependentViewChanged");
		List<View> dependencies = parent.getDependencies(child);
		for (int i = 0, z = dependencies.size(); i < z; i++) {
			final View view = dependencies.get(i);
			if (view instanceof ViewPager) {
				Log.i("wang", "gunle");
				Log.i("wang", " view.getTranslationX()="+view.getTranslationX());
			}
		}
		return true;

	}


}
