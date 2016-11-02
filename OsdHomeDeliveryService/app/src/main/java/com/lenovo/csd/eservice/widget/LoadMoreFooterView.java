package com.lenovo.csd.eservice.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

/**
 * Created by hankw on 16-8-3.
 */
public class LoadMoreFooterView extends TextView implements SwipeLoadMoreTrigger,SwipeTrigger {
    public LoadMoreFooterView(Context context) {
        super(context);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    public LoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onMove(int i, boolean b, boolean b1) {
        if (!b) {
            if (i <= -getHeight()) {
                //setText("RELEASE TO LOAD MORE");
                setText("释放立即加载");

            } else {
                setText("上拉加载更多");
            }
        } /*else {
            setText("LOAD MORE RETURNING");
        }*/
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        setText("加载成功");
    }

    @Override
    public void onReset() {

    }

    @Override
    public void onLoadMore() {
        setText("正在拼命加载数据...");
    }
}
