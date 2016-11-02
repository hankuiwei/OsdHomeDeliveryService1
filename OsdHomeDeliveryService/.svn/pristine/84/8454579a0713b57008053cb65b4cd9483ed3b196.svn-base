package com.lenovo.csd.eservice.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.util.Utils;

/**
 * 首页容纳四个tab底部的tab，item
 * Created by shengtao
 * on 2016/7/27
 * 08:35
 */
public class HomeTabItem extends View {

    private int mIconSize = 0;
    private int mTextSize = 12;
    private int mTextColorSelect = 0xffff2d4b;
    private int mTextColorNormal = 0xff585858;
    private Paint mTextPaintNormal;
    private Paint mTextPaintSelect;
    private Paint mRedDotPaint;
    private int mViewHeight, mViewWidth;
    private String mTextValue ;
    private Bitmap mIconNormal;
    private Bitmap mIconSelect;
    private Rect mBoundText;//text的长度
    private Paint mIconPaintSelect;
    private Paint mIconPaintNormal;
    private float mIconCenterX;//icon x的中心点
    private float mTextCenterX;//text x的中心点
    private boolean mShowRedDot = false;
    private RectF mIconRectF; // icon的Rect
    private final float mRedDotRadius = Utils.dip2px(getContext(), 4.0f);
    

    public HomeTabItem(Context context) {
        this(context, null);
    }

    public HomeTabItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeTabItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initText();
    }

    private void initView() {
        mBoundText = new Rect();
    }

    private void initText() {
        mTextPaintNormal = new Paint();
        mTextPaintNormal.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize, getResources().getDisplayMetrics()));
        mTextPaintNormal.setColor(mTextColorNormal);
        mTextPaintNormal.setAntiAlias(true);
        mTextPaintNormal.setAlpha(0xff);

        mTextPaintSelect = new Paint();
        mTextPaintSelect.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize, getResources().getDisplayMetrics()));
        mTextPaintSelect.setColor(mTextColorSelect);
        mTextPaintSelect.setAntiAlias(true);
        mTextPaintSelect.setAlpha(0);

        mIconPaintSelect = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        mIconPaintSelect.setAlpha(0);

        mIconPaintNormal = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        mIconPaintNormal.setAlpha(0xff);
        
        mRedDotPaint = new Paint();
        mRedDotPaint.setColor(getContext().getResources().getColor(R.color.red));
        mRedDotPaint.setAntiAlias(true);
        mRedDotPaint.setAlpha(0xff);
    }

    private void measureText() {
    	//http://relex.me/android-measure-text-width/
        mTextPaintNormal.getTextBounds(mTextValue, 0, mTextValue.length(), mBoundText);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0, height = 0;

        measureText();
        int contentWidth = Math.max(mBoundText.width(), mIconNormal.getWidth());
        int desiredWidth = getPaddingLeft() + getPaddingRight() + contentWidth;
        switch (widthMode) {
            case MeasureSpec.AT_MOST:
                width = Math.min(widthSize, desiredWidth);
                break;
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                width = desiredWidth;
                break;
        }
        int contentHeight = mBoundText.height() + mIconNormal.getHeight();
        int desiredHeight = getPaddingTop() + getPaddingBottom() + contentHeight;
        switch (heightMode) {
            case MeasureSpec.AT_MOST:
                height = Math.min(heightSize, desiredHeight);
                break;
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                height = contentHeight;
                break;
        }
        setMeasuredDimension(width, height);
        mViewWidth = getMeasuredWidth() ;
        mViewHeight = getMeasuredHeight() ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBitmap(canvas) ;
        drawText(canvas) ;
        drawRedDot(canvas) ;
    }

    private void drawBitmap(Canvas canvas) {
    	float left = (mViewWidth - mIconNormal.getWidth())/2.0f ;
        float top = (mViewHeight - mIconNormal.getHeight() - mBoundText.height()) /2f ;
        mIconRectF = new RectF(left, top, left + mIconNormal.getWidth(), top + mIconNormal.getHeight());
        mIconCenterX = mIconRectF.centerX();
        canvas.drawBitmap(mIconNormal, left, top, mIconPaintNormal);//先画正常的
        canvas.drawBitmap(mIconSelect, left, top, mIconPaintSelect);//再画带有颜色的
    }
    private void drawText(Canvas canvas) {
    	float x = (mViewWidth - mBoundText.width())/2.0f;
        float y = (mViewHeight + mIconNormal.getHeight() + mBoundText.height()) /2.0F ;
        //调整一下中心的位置，有时候会偏移
        mTextCenterX = x + mBoundText.centerX();
        if(mTextCenterX != mIconCenterX){
        	x += mIconCenterX - mTextCenterX;
        }
        canvas.drawText(mTextValue, x, y, mTextPaintNormal);
        canvas.drawText(mTextValue, x, y, mTextPaintSelect);
    }

    private void drawRedDot(Canvas canvas){
    	if(mShowRedDot){
    		//小红点(推送可能用到，如果有的话）的位置是icon rect外的右上
    		canvas.drawCircle(mIconRectF.right + mRedDotRadius, mIconRectF.top + mRedDotRadius, mRedDotRadius, mRedDotPaint);
    	}
    }
    
    public void setTextSize(int textSize) {
        this.mTextSize = textSize;
        mTextPaintNormal.setTextSize(textSize);
        mTextPaintSelect.setTextSize(textSize);
    }

    public void setTextColorSelect(int mTextColorSelect) {
        this.mTextColorSelect = mTextColorSelect;
        mTextPaintSelect.setColor(mTextColorSelect);
        mTextPaintSelect.setAlpha(0);
    }

    public void setTextColorNormal(int mTextColorNormal) {
        this.mTextColorNormal = mTextColorNormal;
        mTextPaintNormal.setColor(mTextColorNormal);
        mTextPaintNormal.setAlpha(0xff);
    }

    public void setTextValue(String TextValue) {
        this.mTextValue = TextValue;
    }

    /**
     * 一定要先设定iconSize
     * @param iconSelId
     * @param TextValue
     */
    public void setIconText(int[] iconSelId,String TextValue) {
        if(mIconSize > 0) {// 缩放icon到指定大小
            Bitmap bm = BitmapFactory.decodeResource(getResources(), iconSelId[0]);
            mIconSelect = Bitmap.createScaledBitmap(bm , mIconSize, mIconSize, true);
            if(mIconSelect != bm){
                bm.recycle();
            }
            bm = BitmapFactory.decodeResource(getResources(), iconSelId[1]);
            mIconNormal = Bitmap.createScaledBitmap(bm , mIconSize, mIconSize, true);
            if(mIconNormal != bm){
                bm.recycle();
            }
        }
        else{// 使用icon的原始大小
            this.mIconSelect = BitmapFactory.decodeResource(getResources(), iconSelId[0]);
            this.mIconNormal = BitmapFactory.decodeResource(getResources(), iconSelId[1]);
        }
        this.mTextValue = TextValue;
    }

    public void setShowRedDot(boolean ShowRedDot){
    	this.mShowRedDot = ShowRedDot;
    	invalidate();
    }

    /**
     * 设定view的显示alpha
     * @param alpha 1-->选中，0-->未选中，0-1中间状态
     */
    public void setTabAlpha(float alpha){
        int paintAlpha = (int)(alpha*255) ;
        mIconPaintSelect.setAlpha(paintAlpha);
        mIconPaintNormal.setAlpha(255-paintAlpha);
        mTextPaintSelect.setAlpha(paintAlpha);
        mTextPaintNormal.setAlpha(255-paintAlpha);
        invalidate();
    }

    public void setIconSize(int iconSize){
        mIconSize = iconSize;
    }

    public void setIconBitmap(Bitmap iconSelect, Bitmap iconNormal){
        // 都不为null的时候才设定
        if(iconSelect != null && iconNormal != null) {
            if(mIconSize > 0){
                mIconNormal = Bitmap.createScaledBitmap(iconNormal , mIconSize, mIconSize, true);
                mIconSelect = Bitmap.createScaledBitmap(iconSelect , mIconSize, mIconSize, true);
                if(mIconNormal != iconNormal){
                    iconNormal.recycle();
                }
                if(mIconSelect != iconSelect){
                    iconSelect.recycle();
                }
            }
            else{
                mIconNormal = iconNormal;
                mIconSelect = iconSelect;
            }
            invalidate();
        }
    }
}
