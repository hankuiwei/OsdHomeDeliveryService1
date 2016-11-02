package com.lenovo.csd.eservice.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

/**
 * Created by hankw on 16-8-3.
 */
public class RefreshHeaderView extends TextView implements SwipeRefreshTrigger,SwipeTrigger {

    private final String TAG = getClass().getSimpleName();

    public RefreshHeaderView(Context context) {
        super(context);
    }

    public RefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    public RefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onRefresh() {
        setText("正在刷新...");
    }

    @Override
    public void onPrepare() {


    }

    @Override
    public void onMove(int i, boolean b, boolean b1) {

        if (!b) {
            if (i >= getHeight()) {
                setText("释放立即刷新");
            } else {
                setText("下拉刷新");

            }
        }/* else {
            setText("REFRESH RETURNING");
            Log.d(TAG,"RELEASE TO REFRESH");
        }*/
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        setText("刷新成功");
    }

    @Override
    public void onReset() {
    }
}
