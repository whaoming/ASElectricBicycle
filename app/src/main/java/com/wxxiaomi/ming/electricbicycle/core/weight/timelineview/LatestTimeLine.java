package com.wxxiaomi.ming.electricbicycle.core.weight.timelineview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.wxxiaomi.ming.electricbicycle.R;


/**
 * Created by Administrator on 2016/12/6.
 */

public class LatestTimeLine extends View {

    private  Paint mPaint;
    private Drawable mMarker;
    private Drawable mStartLine;
    private Drawable mEndLine;

    private Rect mBounds;
    private Context mContext;
    public LatestTimeLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LatestTimeLine(Context context) {
        this(context, null);
    }

//    private int myTextSize =100;

    /**
     *   <attr name="point_color" format="color"/>
     <attr name="point_size" format="dimension"/>
     <attr name="text_color" format="color"/>
     <attr name="text_size" format="dimension"/>
     <attr name="line_color" format="color"/>
     <attr name="line_size" format="dimension"/>
     * @param context
     * @param attrs
     */

    private int text_size;
    private int text_color;
    private int point_color;
    private int point_size;
    private int line_color;
    private int line_size;

    private int mMarkerSize = 30;
    private String below_text="";
    private int padding_left = 2;
    private int pading_bewteen = 2;
    private int paddingRight = 2;
    private String up_text="";

    private Rect mTimeBound;
    private Rect mOptionBound;

    //模拟上下文字的距离
    private int demo = 5;

    private int topLine = 20;

    public LatestTimeLine(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LatestTimeLine, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.LatestTimeLine_me_line_color:
                    line_color = a.getColor(attr,0);
                    mStartLine = a.getDrawable(R.styleable.LatestTimeLine_me_line_color);
                    mEndLine = a.getDrawable(R.styleable.LatestTimeLine_me_line_color);
                    break;
                case R.styleable.LatestTimeLine_me_line_size:
                    line_size =  a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            10, getResources().getDisplayMetrics()));;
                    break;
                case R.styleable.LatestTimeLine_me_point_color:
                    mMarker = a.getDrawable(attr);
//                    point_color =  a.getColor(attr,0);
                    break;
                case R.styleable.LatestTimeLine_me_point_size:
                    point_size =  a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            10, getResources().getDisplayMetrics()));;
                    break;
                case R.styleable.LatestTimeLine_me_text_color:
                    text_color = a.getColor(attr,0);
                    break;
                case R.styleable.LatestTimeLine_me_text_size:
                    text_size =  a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            16, getResources().getDisplayMetrics()));;
                    break;

                case R.styleable.LatestTimeLine_me_below_text:
//                    text_size =  a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
//                            16, getResources().getDisplayMetrics()));;
                    below_text = a.getString(attr);
                    break;
                case R.styleable.LatestTimeLine_me_up_text:
                    up_text =  a.getString(attr);
                    break;
                case R.styleable.LatestTimeLine_me_padding_left:
                    padding_left =  a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            10, getResources().getDisplayMetrics()));;
                    break;
                case R.styleable.LatestTimeLine_me_pading_bewteen:
                    pading_bewteen =  a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            10, getResources().getDisplayMetrics()));;
                    break;
                case R.styleable.LatestTimeLine_me_padding_Right:
                    paddingRight =  a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            10, getResources().getDisplayMetrics()));;
                    break;
                case R.styleable.LatestTimeLine_me_mMarkerSize:
                    mMarkerSize =  a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            100, getResources().getDisplayMetrics()));;
                    Log.i("wang","mMarkerSize:"+mMarkerSize);
                    break;
//                case R.styleable.LatestTimeLine_me_pading_bewteen:
//                    text_size =  a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
//                            16, getResources().getDisplayMetrics()));;
//                    break;
            }

        }
        if(mMarker==null){
            mMarker = mContext.getResources().getDrawable(R.drawable.marker);
        }

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mTimeBound = new Rect();
        mOptionBound = new Rect();
        mPaint.setTextSize(text_size);
        mPaint.setColor(text_color);
        // 计算了描绘字体需要的范围
        if(below_text!=""){
            mPaint.getTextBounds(below_text, 0, below_text.length(), mOptionBound);
        }
        if(up_text!=""){
            mPaint.getTextBounds(up_text, 0, up_text.length(), mTimeBound);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //getPaddingLeft()+paddingRight+mMarkerSize+padding_left+pading_bewteen+point_size+
        //Math.max(mOptionBound.width(),mTimeBound.width());
        int w = 20+padding_left+Math.max(mOptionBound.width(),mTimeBound.width())
                +pading_bewteen+mMarkerSize+paddingRight;
        int h =  getPaddingTop() + getPaddingBottom();
        int widthSize = resolveSizeAndState(w, widthMeasureSpec, 0);
        int heightSize = resolveSizeAndState(h, heightMeasureSpec, 0);
        setMeasuredDimension(widthSize, heightSize);
        initDrawable();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initDrawable();
    }

    private void initDrawable() {
//        int pLeft = getPaddingLeft();
//        int pRight = getPaddingRight();
        int pTop = getPaddingTop();
//        int pBottom = getPaddingBottom();
//
//        int width = getWidth();// Width of current custom view
        int height = getHeight();


            int maxSize = Math.max(mTimeBound.width(),mOptionBound.width());
            if(mMarker != null) {
                mMarker.setBounds(padding_left+maxSize+pading_bewteen+5,pTop+topLine,padding_left+maxSize+pading_bewteen+mMarkerSize+5,pTop+mMarkerSize+topLine);
                mBounds = mMarker.getBounds();
            }
//        }

        int centerX = mBounds.centerX();
        int lineLeft = centerX ;

                mStartLine.setBounds(lineLeft, 0, 2 + lineLeft, mBounds.top);
        mEndLine.setBounds(lineLeft, mBounds.bottom, 2 + lineLeft, height);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(up_text!=""){

            canvas.drawText(up_text,padding_left,mTimeBound.height()+topLine,mPaint);
        }
        if(below_text!=""){
            canvas.drawText(below_text,padding_left+mTimeBound.width()-mOptionBound.width(),mTimeBound.height()+mOptionBound.height()+demo+topLine,mPaint);
        }


        if(mMarker != null) {
            mMarker.draw(canvas);
        }

        if(mStartLine != null) {
            mStartLine.draw(canvas);
        }

        if(mEndLine != null) {
            mEndLine.draw(canvas);
        }
    }

    public void setMarker(Drawable marker) {
        mMarker = marker;
        initDrawable();
    }

    public void setStartLine(Drawable startline) {
        mStartLine = startline;
        initDrawable();
    }

    public void setEndLine(Drawable endLine) {
        mEndLine = endLine;
        initDrawable();
    }
//
//    public void setMarkerSize(int markerSize) {
//        mMarkerSize = markerSize;
//        initDrawable();
//    }
//
//    public void setLineSize(int lineSize) {
////        mLineSize = lineSize;
//        initDrawable();
//    }

    public void initLine(int viewType) {

        if(viewType == LineType.BEGIN) {
            setStartLine(null);
        } else if(viewType == LineType.END) {
            setEndLine(null);
        } else if(viewType == LineType.ONLYONE) {
            setStartLine(null);
            setEndLine(null);
        }

        initDrawable();
    }

    public static int getTimeLineViewType(int position, int total_size) {

        if(total_size == 1) {
            return LineType.ONLYONE;
        } else if(position == 0) {
            return LineType.BEGIN;
        } else if(position == total_size - 1) {
            return LineType.END;
        } else {
            return LineType.NORMAL;
        }
    }

    public int getLineWidth(){
        return mBounds.centerX();
    }
}
