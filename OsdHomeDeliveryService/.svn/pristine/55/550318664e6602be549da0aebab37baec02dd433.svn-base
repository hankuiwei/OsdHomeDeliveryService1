package com.lenovo.csd.eservice.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lenovo.csd.eservice.widget.CustomProgressDialog;


/**
 * 我的tab页面
 * Created by shengtao
 * on 2016/7/26
 * 23:48
 */

public class BaseFragment extends Fragment {
    protected Dialog loadingDialog;
    private  static Handler fragmentLoadingHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = CustomProgressDialog.createDialog(getActivity());
        loadingDialog.setCanceledOnTouchOutside(false);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onResume() {
        super.onResume();

        if (this.isHidden()) {
            return;
        }
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    public void showLoadingDialog() {
        fragmentLoadingHandler.post(new Runnable() {
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
    @Override
    public void onSaveInstanceState(Bundle outState) {
       // super.onSaveInstanceState(outState);
    }

    public void dismissLoadingDialog() {
        fragmentLoadingHandler.post(new Runnable() {
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
    /**
     * 通知子fragment父容器是否选中
     */
    public void setSelected(boolean selected){}
}
