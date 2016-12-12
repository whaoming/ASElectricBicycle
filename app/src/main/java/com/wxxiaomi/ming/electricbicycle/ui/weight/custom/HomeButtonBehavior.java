package com.wxxiaomi.ming.electricbicycle.ui.weight.custom;

import java.util.List;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;

public class HomeButtonBehavior extends
		CoordinatorLayout.Behavior<FloatingActionButton> {

	public HomeButtonBehavior() {
	}

	public HomeButtonBehavior(Context context, AttributeSet attrs) {
		super(context, attrs);
		// this.
	}

	@Override
	public boolean layoutDependsOn(CoordinatorLayout parent,
			FloatingActionButton child, View dependency) {
		if (dependency instanceof Snackbar.SnackbarLayout) {
			return false;
		}else if(dependency instanceof FloatingActionButton){
			return false;
		}
		else {
			child.setTag(false);
		}
		return true;
	}

	@Override
	public boolean onDependentViewChanged(CoordinatorLayout parent,
			final FloatingActionButton child, View dependency) {
		float translationY = getFabTranslationYForSnackbar(parent, child);
		if (tt) {
			slideview(child, translationY, 0);
		} else {
			slideview(child, 0, translationY);
		}
		return true;

	}

//	private float tempY;
	boolean isSN = false;
	boolean tt = false;

	private float getFabTranslationYForSnackbar(CoordinatorLayout parent,
			View fab) {
		float minOffset = 0;
		final List<View> dependencies = parent.getDependencies(fab);
		for (int i = 0, z = dependencies.size(); i < z; i++) {
			final View view = dependencies.get(i);
//			Log.i("wang",
//					"ViewCompat.getTranslationY(view)="
//							+ ViewCompat.getTranslationY(view));
			if (ViewCompat.getTranslationY(view) < 0) {
				isSN = true;
			}
			float translationY = ViewCompat.getTranslationY(view);

			minOffset = Math.min(minOffset, translationY - view.getHeight());
		}

		return minOffset;
	}

	public void slideview(final View view, final float p1, final float p2) {
		tt = !tt;
		TranslateAnimation animation;
		if (p2 == 0) {
			animation = new TranslateAnimation(0, 0, p1, 0);
		} else {
			animation = new TranslateAnimation(0, 0, p1, p2);
		}

		animation.setInterpolator(new OvershootInterpolator());
		animation.setDuration(1000);
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
//				Observable.create()
				int left = view.getLeft();
				int top;
				if (p2 == 0) {
					top = view.getTop();
				} else {
					top = view.getTop() + (int) (p2 - p1);
				}

				int width = view.getWidth();
				int height = view.getHeight();
				view.clearAnimation();
				view.layout(left, top, left + width, top + height);
			}
		});
		view.startAnimation(animation);
	}
}
