package com.lenovo.csd.eservice.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 *
 * Created by shengtao
 * on 2016/7/27
 * 11:02
 * 容纳四个滑动的Fragment的viewPager
 */

public class HomeViewPager extends ViewPager {
	public HomeViewPager(Context context) {
        super(context);
    }

    public HomeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent arg0) {
//        return false;
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent arg0) {
//        return false;
//    }
}
