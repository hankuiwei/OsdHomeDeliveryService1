package com.lenovo.csd.eservice.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

import com.baidu.crabsdk.CrabSDK;
import com.lenovo.csd.eservice.activity.manager.ActivityCollector;
import com.lenovo.csd.eservice.widget.CustomProgressDialog;

/**
 * Created by shengtao
 * on 2016/09/01
 * 11:41
 */
public class BaseFragmentActivity extends FragmentActivity {
    protected Dialog loadingDialog;
    private static Handler fragmentActivityLoadingHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        loadingDialog = CustomProgressDialog.createDialog(this);
        loadingDialog.setCanceledOnTouchOutside(false);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
    @Override
    protected void onPause() {
        CrabSDK.onPause(this);
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        CrabSDK.onResume(this);
    }
    public void showLoadingDialog() {
        fragmentActivityLoadingHandler.post(new Runnable() {
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
        fragmentActivityLoadingHandler.post(new Runnable() {
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
