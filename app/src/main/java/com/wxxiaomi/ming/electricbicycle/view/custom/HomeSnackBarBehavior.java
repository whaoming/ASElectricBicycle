package com.wxxiaomi.ming.electricbicycle.view.custom;

import java.util.List;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

public class HomeSnackBarBehavior extends CoordinatorLayout.Behavior<CoordinatorLayout> {

	public HomeSnackBarBehavior() {
	}

	public HomeSnackBarBehavior(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean layoutDependsOn(CoordinatorLayout parent, CoordinatorLayout child,
			View dependency) {
		 return dependency instanceof Snackbar.SnackbarLayout;
	}

	@Override
	public boolean onDependentViewChanged(CoordinatorLayout parent,
			final CoordinatorLayout child, View dependency) {
		final float translationY = getFabTranslationYForSnackbar(parent, child);
		child.setTranslationY(translationY);
		return false;
	}


	private float getFabTranslationYForSnackbar(CoordinatorLayout parent,
			View fab) {
		float minOffset = 0;
		final List<View> dependencies = parent.getDependencies(fab);
		for (int i = 0, z = dependencies.size(); i < z; i++) {
			final View view = dependencies.get(i);
			if (view instanceof Snackbar.SnackbarLayout &&parent.doViewsOverlap(fab, view)) {
				minOffset = Math.min(minOffset,
						ViewCompat.getTranslationY(view) - view.getHeight());
			}
		}

		return minOffset;
	}

}
