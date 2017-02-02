package com.wxxiaomi.ming.electricbicycle.ui.weight.custom;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wxxiaomi.ming.electricbicycle.service.ShowerProvider;


/**
 * Created by jkyeo on 16/7/7.
 */
public class SplashView extends FrameLayout {

    ImageView splashImageView;
    TextView skipButton;

    private static final String IMG_URL = "splash_img_url";
    private static final String ACT_URL = "splash_act_url";
    private static String IMG_PATH = null;
    private static final String SP_NAME = "splash";
    private static final int skipButtonSizeInDip = 36;
    private static final int skipButtonMarginInDip = 16;
    private Integer duration = 6;
    private static final int delayTime = 1000;   // 每隔1000 毫秒执行一次

    private String imgUrl = null;
    private String actUrl = null;

    private boolean isActionBarShowing = true;

    private Context mActivity = null;

    private OnSplashViewActionListener mOnSplashViewActionListener = null;

    private Handler handler = new Handler();
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (1 == duration) {
                dismissSplashView(false);
                return;
            } else {
                setDuration(--duration);
            }
            handler.postDelayed(timerRunnable, delayTime);
        }
    };

    private void setImage(Bitmap image) {
        splashImageView.setImageBitmap(image);
    }

    public SplashView(Activity context) {
        super(context);
        mActivity = context;
        initComponents();
    }

    public SplashView(Activity context, AttributeSet attrs) {
        super(context, attrs);
        mActivity = context;
        initComponents();
    }

    public SplashView(Activity context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mActivity = context;
        initComponents();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SplashView(Activity context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mActivity = context;
        initComponents();
    }

    private GradientDrawable splashSkipButtonBg = new GradientDrawable();

    void initComponents() {
        splashSkipButtonBg.setShape(GradientDrawable.OVAL);
        splashSkipButtonBg.setColor(Color.parseColor("#66333333"));

        splashImageView = new ImageView(mActivity);
        splashImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        splashImageView.setBackgroundColor(mActivity.getResources().getColor(android.R.color.white));
        LayoutParams imageViewLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(splashImageView, imageViewLayoutParams);
        splashImageView.setClickable(true);

        skipButton = new TextView(mActivity);
        int skipButtonSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, skipButtonSizeInDip, mActivity.getResources().getDisplayMetrics());
        LayoutParams skipButtonLayoutParams = new LayoutParams(skipButtonSize, skipButtonSize);
        skipButtonLayoutParams.gravity = Gravity.TOP| Gravity.RIGHT;
        int skipButtonMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, skipButtonMarginInDip, mActivity.getResources().getDisplayMetrics());
        skipButtonLayoutParams.setMargins(0, skipButtonMargin, skipButtonMargin, 0);
        skipButton.setGravity(Gravity.CENTER);
        skipButton.setTextColor(mActivity.getResources().getColor(android.R.color.white));
        skipButton.setBackgroundDrawable(splashSkipButtonBg);
        skipButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        this.addView(skipButton, skipButtonLayoutParams);

        skipButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissSplashView(true);
            }
        });

        setDuration(duration);
        handler.postDelayed(timerRunnable, delayTime);
    }

    private void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private void setActUrl(String actUrl) {
        this.actUrl = actUrl;
    }

    private void setDuration(Integer duration) {
        this.duration = duration;
        skipButton.setText(String.format("跳过\n%d s", duration));
    }

    private void setOnSplashImageClickListener(@Nullable final OnSplashViewActionListener listener) {
        if (null == listener) return;
        mOnSplashViewActionListener = listener;
        splashImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSplashImageClick(actUrl);
            }
        });
    }

    /**
     * static method, show splashView on above of the activity
     * you should called after setContentView()
     * @param activity  activity instance
     * @param durationTime  time to countDown
     * @param defaultBitmapRes  if there's no cached bitmap, show this default bitmap;
     *                          if null == defaultBitmapRes, then will not show the splashView
     * @param listener  splash view listener contains onImageClick and onDismiss
     */
    public static void showSplashView(@NonNull Activity activity,
                                      @Nullable Integer durationTime,
                                      @Nullable Integer defaultBitmapRes,
                                      String imgUrl,
                                      ViewGroup view,
                                      @Nullable OnSplashViewActionListener listener) {

        IMG_PATH = imgUrl;
        SplashView splashView = new SplashView(activity);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        splashView.setOnSplashImageClickListener(listener);
        if (null != durationTime) splashView.setDuration(durationTime);
        ShowerProvider.showSplashImage(activity,splashView.splashImageView, defaultBitmapRes,IMG_PATH);
        view.addView(splashView, param);
    }


    private void dismissSplashView(boolean initiativeDismiss) {
        Log.i("wang","时间到了");
        if (null != mOnSplashViewActionListener) mOnSplashViewActionListener.onSplashViewDismiss(initiativeDismiss);
//
//
        handler.removeCallbacks(timerRunnable);
//        final ViewGroup parent = (ViewGroup) this.getParent();
//        if (null != parent) {
//            ObjectAnimator animator = ObjectAnimator.ofFloat(SplashView.this, "scale", 0.0f, 0.5f).setDuration(600);
//            animator.start();
//            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    float cVal = (Float) animation.getAnimatedValue();
//                    SplashView.this.setAlpha(1.0f - 2.0f * cVal);
//                    SplashView.this.setScaleX(1.0f + cVal);
//                    SplashView.this.setScaleY(1.0f + cVal);
//                }
//            });
//            animator.addListener(new Animator.AnimatorListener() {
//                @Override
//                public void onAnimationStart(Animator animation) {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    parent.removeView(SplashView.this);
////                    showSystemUi();
//                }
//
//                @Override
//                public void onAnimationCancel(Animator animation) {
//                    parent.removeView(SplashView.this);
////                    showSystemUi();
//                }
//
//                @Override
//                public void onAnimationRepeat(Animator animation) {
//
//                }
//            });
//        }
    }

    public interface OnSplashViewActionListener {
        void onSplashImageClick(String actionUrl);
        void onSplashViewDismiss(boolean initiativeDismiss);
    }

}
