package com.lenovo.csd.eservice.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.lenovo.csd.eservice.ServiceApplication;
import com.lenovo.csd.eservice.activity.HomeActivity;
import com.lenovo.csd.eservice.services.LocationService;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static android.support.v4.app.ActivityCompat.requestPermissions;
import static android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale;


/**
 * Created by hankw on 16-9-5.
 */
public class BaiduNavigation {

    private final String TAG = getClass().getSimpleName();
    public static List<Activity> activityList = new LinkedList<Activity>();
    private Context mcontext;
    private double elongitude;
    private double elatitude;
    private String maddress;
    private String permissionInfo;
    private final int SDK_PERMISSION_REQUEST = 127;
    //GeoCoder mSearch = null; // 搜索模块
    private static final String APP_FOLDER_NAME = "BNSDKSimpleDemo";
    private String mSDCardPath = null;
    private String authinfo = null;
    private LocationService locService;
    private Activity mactivity;
    public static final String ROUTE_PLAN_NODE = "routePlanNode";
   // private CoordinateType coType = CoordinateType.BD09LL;
    private boolean isLocation;

    public BaiduNavigation(Context context, Activity activity, String address) {
        this.mcontext = context;
        this.mactivity = activity;
       /* this.elongitude = lon;
        this.elatitude = lat;*/
        this.maddress = address.trim();
        getPersimmions();
        locService = ((ServiceApplication)mactivity.getApplication()).locationService;
        locService.registerListener(listener);
        locService.setLocationOption(locService.getDefaultLocationClientOption());
        locService.start();
        //((HomeActivity)mactivity).showLoadingDialog();
    }





    /***
     * 定位结果回调，在此方法中处理定位结果
     */
    private BDLocationListener listener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {

           // Log.d(TAG, "onReceiveLocation() is starting ");
            // TODO Auto-generated method stub

            //if (location != null && (location.getLocType() == 161 || location.getLocType() == 66) && !isLocation) {
            if (location != null && (location.getLocType() == 161 || location.getLocType() == 66)) {
                //Message locMsg = locHander.obtainMessage();
                //(()mactivity).dismissLoadingDialog();
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

               // Log.d("LocationFilter", "handleMessage() == > latitude is (" + latitude + ")");
               // Log.d("LocationFilter", "handleMessage() == > longitude is (" + longitude + ")");
                //routeplanToNavi(BNRoutePlanNode.CoordinateType.BD09LL,longitude,latitude);
                locService.unregisterListener(listener);
                locService.stop();
                startNavi(latitude,longitude,elatitude,elongitude,maddress);
                //routeplanToNavi(longitude,latitude,elongitude,elatitude,maddress);
                isLocation = true;
            } else {
                ((HomeActivity)mactivity).dismissLoadingDialog();
                //if (!isLocation){
                    // TODO: 16-9-2 对定位当前位置失败进行处理
                    Toast.makeText(mcontext, "暂时无法获取您的位置,请检查定位服务是否打开", Toast.LENGTH_SHORT).show();
                    locService.unregisterListener(listener);
                    locService.stop();
                    return;
               // }

            }
        }
    };





    /**
     * 启动百度地图导航(Native)
     */
    public void startNavi(double mLat1,double mLon1,double mLat2,double mLon2,String eaddress) {
        LatLng pt1 = new LatLng(mLat1, mLon1);
        LatLng pt2 = new LatLng(mLat2, mLon2);


        //移动APP调起Android百度地图方式举例
        //Log.d(TAG,"startNavi() eaddress is ("+eaddress+")");
        Intent intent = null;
        try {

            intent = Intent.getIntent("intent://map/direction?origin=latlng:"+mLat1+","+mLon1+"|name:我的位置&destination="+eaddress+"&mode=transit&src=thirdapp.navi.service.lenovo.com.getuipushtest#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            if (intent.resolveActivity(mcontext.getPackageManager()) != null) {
                mactivity.startActivity(intent); //启动调用
          // Log.e("GasStation", "百度地图客户端已经安装");
        } else {
                String amapsrt = "http://api.map.baidu.com/direction?origin=latlng:+"+mLat1+","+mLon1+"|name:我的位置&destination="+eaddress+"&mode=transit&region=中国&output=html&src=HomeDelivery";
                Uri uri = Uri.parse(amapsrt);
              Intent  inte = new Intent(Intent.ACTION_VIEW,uri);
                if(inte.resolveActivity(mcontext.getPackageManager()) != null)
                mactivity.startActivity(inte);
                else
                Toast.makeText(mcontext,"请安装百度地图",Toast.LENGTH_SHORT).show();
            //Log.e("GasStation", "没有安装百度地图客户端");
        }
        } catch (URISyntaxException e) {
            e.printStackTrace();
            showDialog();
        }
    }

    /**
     * 判定是否装有地图APP
     * @param packageName
     * @return
     */
    private boolean isInstallPackage(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }


        /**
         * 提示未安装百度地图app或app版本过低
         */
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OpenClientUtil.getLatestBaiduMapApp(mcontext);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }



    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (mcontext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (mcontext.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                requestPermissions(mactivity, permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (mcontext.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(mactivity, permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }
}
