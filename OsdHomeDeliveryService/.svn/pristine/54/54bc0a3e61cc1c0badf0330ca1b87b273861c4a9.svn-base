package com.lenovo.csd.eservice.activity;
import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import com.igexin.sdk.PushManager;
import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.activity.manager.ActivityCollector;
import com.lenovo.csd.eservice.push.PushBoradCastReceiver;
import com.lenovo.csd.eservice.util.Utils;

import java.util.ArrayList;
/**
 * Created by shengtao
 * on 2016/7/21
 * 16:48
 */
public class HomeActivity extends BaseFragmentActivity {
    /**
     * 个推start,第三方应用Master Secret
     */
    private static final int REQUEST_PERMISSION = 0;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final int ADDTAG = 100;//添加Tag
    private static final int VERSION = 101;//当前版本
    private static final int SILENTTIME = 102;//设置静默时间
    private static final int EXIT = 103;//退出
    private static final int GETCLIENTID = 106;//手动获取CID
    private String permissionInfo;
    /**
     * SDK服务是否启动
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        super.onCreate(savedInstanceState);
        requestPermission();
        getLocationPermissions();
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_home);
    }
    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)){
                return true;
            }else{
                permissionsList.add(permission);
                return false;
            }

        }else{
            return true;
        }
    }
    @TargetApi(23)
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            // 定位精确位置
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            //个推的
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
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
                requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_PERMISSION);
            }
            else {
                Utils.openLocationService(this);
                PushManager.getInstance().initialize(this.getApplicationContext());
            }
        } else {
            Utils.openLocationService(this);
            PushManager.getInstance().initialize(this.getApplicationContext());
        }
    }
    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if ((grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                PushManager.getInstance().initialize(this.getApplicationContext());
                Utils.openLocationService(this);
            } else {
                requestPermission();
            }
        } else {
            onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    public void onDestroy() {
        PushBoradCastReceiver.payloadData.delete(0, PushBoradCastReceiver.payloadData.length());
        super.onDestroy();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 返回键最小化程序
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void goHomeActivity(Context context, boolean isNewTask) {
        Intent homeIntent = new Intent(context, HomeActivity.class);
        if (isNewTask) {
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //在广播接受者中启动活动需要添加这个标志
        }
        context.startActivity(homeIntent);
    }

    @TargetApi(23)
    public void getLocationPermissions() {
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
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_LOCATION_PERMISSION);
            } else {
                Utils.openLocationService(this);
            }
        }else{
            Utils.openLocationService(this);
        }
    }

}
