package com.wxxiaomi.ming.electricbicycle.core.weight.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.wxxiaomi.ming.electricbicycle.R;


/**
 * Created by Mr.W on 2016/12/5.
 * E-maiil：122627018@qq.com
 * github：https://github.com/122627018
 */
public class RoundView extends View {
    /**
     * 控件的宽
     */
    private int mWidth;
    /**
     * 控件的高
     */
    private int mHeight;
    /**
     * 控件中的图片
     */
  //  private Bitmap mImage;
    /**
     * 图片的缩放模式
     */
//    private int mImageScale;
//    private static final int IMAGE_SCALE_FITXY = 0;
//    private static final int IMAGE_SCALE_CENTER = 1;
    /**
     * 图片的介绍
     */
//    private String mTitle;
    /**
     * 字体的颜色
     */
//    private int mTextColor;
    /**
     * 字体的大小
     */
//    private int mTextSize;

    private Paint mPaint;
    /**
     * 对文本的约束
     */
    private Rect mTextBound;
    /**
     * 控制整体布局
     */
    private Rect rect;


    private String below_text;
    private int below_text_size;
    private int below_text_color;
    private int border_color;
    private int fill_color;
    private Bitmap icon_img;
    private int border_size;

    public RoundView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundView(Context context) {
        this(context, null);
    }

    /**
     * 初始化所特有自定义类型
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public RoundView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyCustomView, defStyle, 0);

        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.MyCustomView_below_text:
                    below_text = a.getString(attr);
                    break;
                case R.styleable.MyCustomView_below_text_color:
                    below_text_color = a.getColor(attr,0);
                    break;
                case R.styleable.MyCustomView_below_text_size:
                    below_text_size =  a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            16, getResources().getDisplayMetrics()));;
                    break;
                case R.styleable.MyCustomView_border_size:
                    border_size =  a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            0, getResources().getDisplayMetrics()));;
                    break;
                case R.styleable.MyCustomView_border_color:
                    border_color = a.getColor(attr,0);
                    break;
                case R.styleable.MyCustomView_fill_color:
                    fill_color = a.getColor(attr,Color.WHITE);
                    break;
                case R.styleable.MyCustomView_icon_img:
                    icon_img = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;

            }
        }
        a.recycle();
        rect = new Rect();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mTextBound = new Rect();
        mPaint.setTextSize(below_text_size);
        // 计算了描绘字体需要的范围
        mPaint.getTextBounds("Fllowers", 0, 8, mTextBound);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i("wang","widthMeasureSpec:"+widthMeasureSpec+",heightMeasureSpec:"+heightMeasureSpec);
//        /**
//         * 设置宽度
//         */
//        int specMode = MeasureSpec.getMode(widthMeasureSpec);
//        int specSize = MeasureSpec.getSize(widthMeasureSpec);
//        Log.i("wang","specSize:"+specSize);
        int width = measureWidth(widthMeasureSpec);
//        int height = measureHeight(heightMeasureSpec);
//        Log.i("wang","width:"+width+",height:"+height);
//        if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
//        {
//            Log.e("xxx", "EXACTLY");
//            mWidth = specSize;
//        } else {
//            // 由图片决定的宽
//            int desireByImg = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
//            // 由字体决定的宽
//            int desireByTitle = getPaddingLeft() + getPaddingRight() + mTextBound.width();
//        mWidth = getPaddingLeft() + getPaddingRight() + mTextBound.width() + 80;
        mWidth = width;
        Log.i("wang","mWidth:"+mWidth);
        mHeight = mWidth;
//            if (specMode == MeasureSpec.AT_MOST)// wrap_content
//            {
//                int desire = Math.max(desireByImg, desireByTitle);
//                mWidth = Math.min(desire, specSize);
//                Log.e("xxx", "AT_MOST");
//            }
//        }

        /***
         * 设置高度
         */

//        specMode = MeasureSpec.getMode(heightMeasureSpec);
//        specSize = MeasureSpec.getSize(heightMeasureSpec);
//        if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
//        {
//            mHeight = specSize;
//        } else {
//            int desire = getPaddingTop() + getPaddingBottom() + mImage.getHeight() + mTextBound.height();
//            if (specMode == MeasureSpec.AT_MOST)// wrap_content
//            {
//                mHeight = Math.min(desire, specSize);
//            }
//        }
        setMeasuredDimension(mWidth, mWidth);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        /**
         * 边框
         */
//        mPaint.setStrokeWidth(4);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setColor(Color.CYAN);
//        canvas.drawCircle(0, 0, getMeasuredWidth(), mPaint);

//        int verticalCenter    =  getHeight() / 2;
//        int horizontalCenter  =  getWidth() / 2;
//        int circleRadius      = 200;
//        Paint paints = new Paint();
//        paints.setAntiAlias(false);
//        paints.setColor(Color.RED);
//        canvas.drawCircle( horizontalCenter, verticalCenter-250, circleRadius, paints);
//        paints.setAntiAlias(true);
//        paints.setStyle(Paint.Style.STROKE);
//        paints.setStrokeWidth(20);
//        canvas.drawCircle( horizontalCenter, verticalCenter+250, circleRadius, paints);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(fill_color);
        int yuanxinX = mWidth / 2;
        canvas.drawCircle(yuanxinX, yuanxinX, getMeasuredHeight() / 2-border_size, mPaint);
        if(border_size!=0){
            mPaint.setStrokeWidth(border_size);
            mPaint.setStyle(Style.STROKE);
            if(border_color==0){
                mPaint.setColor(Color.BLACK);
            }else{
                mPaint.setColor(border_color);
            }

            canvas.drawCircle(yuanxinX, yuanxinX, getMeasuredHeight() / 2-border_size/2, mPaint);
        }




//        rect.left = getPaddingLeft();
//        rect.right = mWidth - getPaddingRight();
//        rect.top = getPaddingTop();
//        rect.bottom = mHeight - getPaddingBottom();
        if(below_text_color==0){
            mPaint.setColor(Color.BLACK);
        }else{
            mPaint.setColor(below_text_color);
        }

        mPaint.setStyle(Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        /**
         * 当前设置的宽度小于字体需要的宽度，将字体改为xxx...
         */
//        if (mTextBound.width() > mWidth) {
//            TextPaint paint = new TextPaint(mPaint);
//            String msg = TextUtils.ellipsize(mTitle, paint, (float) mWidth - getPaddingLeft() - getPaddingRight(),
//                    TextUtils.TruncateAt.END).toString();
//            canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);
//
//        } else {
        //正常情况，将字体居中
        canvas.drawText(below_text, mWidth/2, mHeight / 2 + +mTextBound.height() + getPaddingBottom()+30, mPaint);
//        }

        //取消使用掉的快
//        rect.bottom -= mTextBound.height();

//        if (mImageScale == IMAGE_SCALE_FITXY) {
//           // canvas.drawBitmap(mImage, null, rect, mPaint);
//        } else {
        //计算居中的矩形范围
//        rect.left = mWidth / 2 - mImage.getWidth() / 2;
//        rect.right = mWidth / 2 + mImage.getWidth() / 2;
////            rect.top = (mHeight - mTextBound.height()) / 2 - mImage.getHeight() / 2;
////            rect.bottom = (mHeight - mTextBound.height()) / 2 + mImage.getHeight() / 2;
//        rect.top = mHeight / 2 - mImage.getHeight() - 5;
//        rect.bottom = mHeight / 2 - 5;

//        rect.top = mHeight / 4;
//        rect.bottom = mHeight / 2;
//        rect.left = ((mWidth+30) / 2)-( icon_img.getWidth()*(mHeight / 2/icon_img.getHeight()))/2;
//        rect.right = ((mWidth-30) / 2) + ( icon_img.getWidth()*(mHeight / 2/icon_img.getHeight()))/2;;
        rect.left = mWidth / 2 - icon_img.getWidth() / 2;
        rect.right = mWidth / 2 + icon_img.getWidth() / 2;
        rect.top = (mHeight - mTextBound.height()) / 2 - icon_img.getHeight() / 2;
        rect.bottom = (mHeight - mTextBound.height()) / 2 + icon_img.getHeight() / 2;
        canvas.drawBitmap(icon_img, null, rect, mPaint);
//        }
        /**
         * 边框
         */


    }

    //根据xml的设定获取宽度
    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //wrap_content
        if (specMode == MeasureSpec.AT_MOST){

        }
        //fill_parent或者精确值
        else if (specMode == MeasureSpec.EXACTLY){
        }
        return specSize;
    }



}

