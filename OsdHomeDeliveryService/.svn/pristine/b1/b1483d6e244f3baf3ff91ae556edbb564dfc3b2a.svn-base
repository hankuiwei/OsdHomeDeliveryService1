package com.lenovo.csd.eservice.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.lenovo.csd.eservice.entity.base.BaseData;
import com.lenovo.csd.eservice.entity.base.TencentLocationData;
import com.lenovo.csd.eservice.http.ErrorCode;
import com.lenovo.csd.eservice.http.HttpClientManager;
import com.lenovo.csd.eservice.http.NetInterface;
import com.lenovo.csd.eservice.http.callback.adapter.JsonHttpCallBack;
import com.lenovo.csd.eservice.schedule.TimerScheduleClient;
import com.lenovo.csd.eservice.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class LenovoLocService extends Service {
    private static TimerScheduleClient scheduleClient;
    private LocationClient mClient;
    private LocationClientOption mOption;

    //
    public static final long PERIOD = 5 * 60* 1000;
    public static final String TAG = "LenovoLocationService";

    public LenovoLocService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initSchecdule();
        return super.onStartCommand(intent, flags, startId);
    }

    private void initSchecdule() {
        if (scheduleClient == null) {
            scheduleClient = TimerScheduleClient.getInstance(getApplicationContext(), new TimerScheduleClient.TimerScheduleCallback() {
                @Override
                public void doSchedule() {
                    if(!Utils.isTimeInRange(8,20)){//如果时间不在8:00---20:00之间，则不上传地理位置
                        return;
                    }
                    if (mClient == null) {
                        mClient = new LocationClient(getApplicationContext());
                    }
                    if (mOption == null) {
                        initLocationOption();
                    }
                    mClient.setLocOption(mOption);
                    mClient.registerLocationListener(new BDLocationListener() {
                        @Override
                        public void onReceiveLocation(BDLocation bdLocation) {
//                            Utils.writeFile("end_location");
                            int locType = bdLocation.getLocType();
                            if (locType == BDLocation.TypeGpsLocation || locType == BDLocation.TypeNetWorkLocation || locType == BDLocation.TypeOffLineLocation) {
//                                Utils.writeFile("lat="+bdLocation.getLatitude()+" -- lng="+bdLocation.getLongitude());
                                double latitude = bdLocation.getLatitude();
                                double longtitude = bdLocation.getLongitude();
                                getTecentLocation(latitude, longtitude);
                            }
                            if (mClient != null) {
                                mClient.unRegisterLocationListener(this);
                                mClient.stop();
                            }
                        }
                    });
                    mClient.start();
//                    Utils.writeFile("start_location");
                }
            });
        }
        scheduleClient.start(0, PERIOD, PERIOD, PERIOD / 2);

    }

    /**
     * 创建一个LocationOption
     */
    public void initLocationOption() {
        mOption = new LocationClientOption();
        mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        mOption.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        mOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
        mOption.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
        mOption.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        mOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        mOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        mOption.setIsNeedAltitude(false);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        mOption.setTimeOut(10 * 1000);
    }

    /**
     * 上传地理位置
     */
    private void reportLocation(String bdLat, String bdLng, String txLat, String txLng) {
        if (Utils.checkInternetStatus(this) == 0)
            return;
        String token = Utils.getToken(this);
        String userCode = Utils.getUserCode(this);
        if (TextUtils.isEmpty(token) || TextUtils.isEmpty(userCode))
            return;
        HashMap<String, String> params = new HashMap();
        params.put("user_code", userCode);
        params.put("baidu_lat", bdLat + "");
        params.put("baidu_lng", bdLng + "");
        params.put("tencent_lat", txLat + "");
        params.put("tencent_lng", txLng + "");
        HttpClientManager.post(NetInterface.UPLOAD_LOCATION + "token=" + token, params, new JsonHttpCallBack<BaseData>() {

            @Override
            public void onSuccess(BaseData result) {
                if (result != null && Integer.parseInt(result.getStatus_code()) == ErrorCode.STATUS_TOKEN_ERROR) {
                    if (scheduleClient != null) {
//                        Log.e(TAG,"SCHEDULE STOP");
                        scheduleClient.stop();
                    }
                }
            }

            @Override
            public void onError(Exception e) {
            }
        });
    }

    /**
     * 获取腾讯地理位置坐标
     */
    public void getTecentLocation(final double bdLat, final double bdLng) {
        HttpClientManager.get(NetInterface.GET_TENCENT_LOCATION + bdLat + "," + bdLng, new JsonHttpCallBack<TencentLocationData>() {
            @Override
            public void onSuccess(TencentLocationData result) {
                if (result != null && result.getLocations() != null && result.getLocations().size() > 0) {
                    TencentLocationData.TencentLocation location = result.getLocations().get(0);
                    double txLat = location.getLat();
                    double txLng = location.getLng();
                    reportLocation(bdLat + "", bdLng + "", txLat + "", txLng + "");
                } else {
                    reportLocation(bdLat + "", bdLng + "", "", "");
                }
            }

            @Override
            public void onError(Exception e) {
                reportLocation(bdLat + "", bdLng + "", "", "");
            }
        });
    }

    public static void stopSchedule() {
        if (scheduleClient != null) {
            scheduleClient.stop();
        }
    }
}
