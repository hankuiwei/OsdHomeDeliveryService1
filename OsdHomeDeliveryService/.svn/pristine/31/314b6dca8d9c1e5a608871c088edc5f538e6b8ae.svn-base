package com.lenovo.csd.eservice.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.baidu.crabsdk.CrabSDK;
import com.lenovo.csd.eservice.activity.manager.ActivityCollector;
import com.lenovo.csd.eservice.widget.CustomProgressDialog;

public abstract class BaseActivity extends Activity {
    /**
     * 记录处于前台的Activity
     */
//    private static BaseActivity mForegroundActivity = null;
    protected Dialog loadingDialog;
    private static Handler activityLoadingHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        super.onCreate(savedInstanceState);
        //每创建一个活动，就加入到活动管理器中
        ActivityCollector.addActivity(this);
        loadingDialog = CustomProgressDialog.createDialog(this);
        loadingDialog.setCanceledOnTouchOutside(false);
        provideLayout();
        initView();
        initData();
        setClickLintenner();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        AppForegroundStateManager.getInstance().onActivityVisible(this);
    }

    @Override
    protected void onResume() {
//        mForegroundActivity = this;
        CrabSDK.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        CrabSDK.onPause(this);
//        mForegroundActivity = null;
        super.onPause();
    }

    @Override
    protected void onStop() {
//        AppForegroundStateManager.getInstance().onActivityNotVisible(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //每销毁一个活动，就从活动管理器中移除
        ActivityCollector.removeActivity(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        CrabSDK.dispatchTouchEvent(ev, this);
        return super.dispatchTouchEvent(ev);
    }

    /**
     * lllllll
     */
    abstract protected void provideLayout();

    abstract protected void initView();

    abstract protected void initData();

    abstract protected void setClickLintenner();

    //此处actionBar可以不用系统的
    protected void initActionBar() {

    }
    /**
     * 获取当前处于前台的activity
     */
//    public static BaseActivity getForegroundActivity() {
//        return mForegroundActivity;
//    }
    protected void initFindViewById() {

    }

    protected void initEvent() {

    }
    public void showLoadingDialog() {
        activityLoadingHandler.post(new Runnable() {
            @Override
            public void run() {
                // 可能存在某种情况下回收了activity，又重新create，导致无attach view
                try {
                    if (loadingDialog != null) {
                        if (!loadingDialog.isShowing())
                            loadingDialog.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void dismissLoadingDialog() {
        activityLoadingHandler.post(new Runnable() {
            @Override
            public void run() {
                // 可能存在某种情况下回收了activity，又重新create，导致无attach view
                try {
                    if (loadingDialog != null) {
                        if (loadingDialog.isShowing()) {
                            loadingDialog.dismiss();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

    }

}

