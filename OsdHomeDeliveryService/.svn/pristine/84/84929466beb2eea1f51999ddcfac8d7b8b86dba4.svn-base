package com.lenovo.csd.eservice.services;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.lenovo.csd.eservice.ServiceApplication;
import com.lenovo.csd.eservice.cache.SharedPrefManager;
import com.lenovo.csd.eservice.entity.base.LoginData;
import com.lenovo.csd.eservice.http.ErrorCode;
import com.lenovo.csd.eservice.http.HttpClientManager;
import com.lenovo.csd.eservice.http.NetInterface;
import com.lenovo.csd.eservice.http.callback.adapter.JsonHttpCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.support.v4.app.ActivityCompat.requestPermissions;
import static android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale;

/**
 * Created by hankw on 16-8-30.
 */
public class LoadLocatService extends Service {

    private final String TAG = getClass().getSimpleName();

    private final int SDK_PERMISSION_REQUEST = 127;
    private String permissionInfo;
    private LocationService locService;
    private  static  final String  ORDERCODE = "orderid";
    private String orderid;

    @Override
    public void onCreate() {
        super.onCreate();
        getPersimmions();
        locService = ((ServiceApplication) getApplication()).locationService;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //此处获取工单编号
         orderid =   intent.getStringExtra(ORDERCODE);
        flags = START_STICKY;
        locService.registerListener(listener);
        if (flags == START_STICKY) {
            //LocationClientOption mOption = locService.getDefaultLocationClientOption();
            locService.setLocationOption(locService.getOption());
        } else {
            locService.setLocationOption(locService.getDefaultLocationClientOption());
        }
        locService.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
			/*
			 * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }

            if (permissions.size() > 0) {
                requestPermissions((Activity) getApplicationContext(), permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale((Activity) getApplicationContext(), permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }


    /***
     * 定位结果回调，在此方法中处理定位结果
     */
    private BDLocationListener listener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {

            // TODO Auto-generated method stub
            //Log.d(TAG, "onError() == > location.getLocType() is (" + location.getLocType()+ ")");
            if (location != null && (location.getLocType() == 161 || location.getLocType() == 66)) {
                //Message locMsg = locHander.obtainMessage();
                // TODO: 16-8-31 此处进行坐标上传操作 ,需要从share中获取token,工程师编号，上面的orderId
                SharedPreferences preferences = SharedPrefManager.getSystemSharedPref(getBaseContext());
                String userCode = SharedPrefManager.getStringInSharePref(preferences, SharedPrefManager.LOGIN_USER_CODE, "userCode");
                String token = SharedPrefManager.getStringInSharePref(preferences, SharedPrefManager.LOGIN_TOKEN, "mToken");
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                if (orderid != null &&!"userCode".equals(userCode) && !"mToken".equals(token)) {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("token", token);
                    params.put("user_code", userCode);
                    params.put("order_code",orderid);
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    params.put("lng",""+longitude);
                    params.put("lat",""+latitude);
                    HttpClientManager.post(NetInterface.UPLOAD_LOCATION, params, new JsonHttpCallBack<LoginData>() {
                        @Override
                        public void onSuccess(LoginData result) {
                            if (Integer.parseInt(result.getStatus_code()) == ErrorCode.STATUS_SUCCESS) {
                                stopLocService((Activity) getApplicationContext());
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                        }
                    });
                }


            }
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        locService.unregisterListener(listener); //注销掉监听
        locService.stop(); //停止定位服务

    }

    public static void  startLocService(Activity activity,String orderid){
        Intent intent = new Intent(activity,LoadLocatService.class);
            intent.putExtra(ORDERCODE,orderid);
            activity.startService(intent);
    }

    public static void stopLocService(Activity activity){
        Intent intent = new Intent(activity,LoadLocatService.class);
        activity.stopService(intent);
    }



}
