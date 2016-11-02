package com.lenovo.csd.eservice;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.baidu.mapapi.SDKInitializer;
import com.lenovo.csd.eservice.fragment.OrderFinishFragment;
import com.lenovo.csd.eservice.fragment.OrderUnFinishFragment;
import com.lenovo.csd.eservice.services.LocationService;

import com.baidu.crabsdk.CrabSDK;

/**
 * Created by shengtao
 * Date:2016/7/21
 * Time:11:03
 * application是用来保存全局变量的，并且是在package创建的时候就跟着存在了。所以当我们需要创建全局变量的时候，不需要再像 j2se那样需要创建public权限的static变量，而直接在application中去实现
 */


public class ServiceApplication extends Application {

    private static ServiceApplication mInstance;
    //private final String baiduCrabAppKey = "93cf97154eea3fd4"; //盛涛个人账号的生成的key
    private final String baiduCrabAppKey = "d7f2170addcc0f8c"; //郑诗圣给的公司的账号生成的key
    public LocationService locationService;//百度定位

    public Vibrator mVibrator;
    // 共享变量
    //private OrderUnFinishFragment.UnHandler uhandler = null;
   // private OrderFinishFragment.FHandler fhandler = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(getApplicationContext());
        //  ToastUtils.init(this);
        //SDKInitializer.initialize(this);
        // 初始化百度crab SDK
        CrabSDK.init(this, baiduCrabAppKey);
        CrabSDK.openNativeCrashHandler();
        CrabSDK.setBehaviorRecordLimit(5);
        CrabSDK.setUploadCrashOnlyWifi(false);//是否仅wifi环境下上传，默认为false。
    }

    public static ServiceApplication getInstance() {
        return mInstance;
    }

  /*  // set方法
    public void setHandler(OrderUnFinishFragment.UnHandler handler) {
        this.uhandler = handler;
    }

    public void setFhandler(OrderFinishFragment.FHandler fhandler) {
        this.fhandler = fhandler;
    }

    // get方法
    public OrderUnFinishFragment.UnHandler getHandler() {
        return uhandler;
    }

    public OrderFinishFragment.FHandler getFhandler() {
        return fhandler;
    }*/

}
