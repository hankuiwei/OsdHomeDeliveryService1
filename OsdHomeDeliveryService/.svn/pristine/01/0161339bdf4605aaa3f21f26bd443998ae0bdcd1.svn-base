package com.lenovo.csd.eservice.http.callback;

import android.view.View;

/**
 * Created by 彤 on 2016/8/10.
 */
public abstract class NoDoubleClickLinstenner implements View.OnClickListener {
    public static final long MIN_DURATION = 300;
    private long last_click;
    private long current_click;

    @Override
    public void onClick(View view) {
        current_click = java.util.Calendar.getInstance().getTimeInMillis();
        if (current_click - last_click >= MIN_DURATION) {
            last_click = current_click;
            doClick(view);
        }
    }

    public abstract void doClick(View view);
}
