package com.lenovo.csd.eservice.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.cache.SharedPrefManager;
import com.lenovo.csd.eservice.entity.base.LoginData;
import com.lenovo.csd.eservice.fragment.HintFragment;
import com.lenovo.csd.eservice.http.ErrorCode;
import com.lenovo.csd.eservice.http.HttpClientManager;
import com.lenovo.csd.eservice.http.NetInterface;
import com.lenovo.csd.eservice.http.UpdateManager;
import com.lenovo.csd.eservice.http.callback.adapter.JsonHttpCallBack;
import com.lenovo.csd.eservice.message.MessageEvent;
import com.lenovo.csd.eservice.services.AppUpdateService;
import com.lenovo.csd.eservice.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A login screen that offers login via email/password.
 *
 * @author 彤
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, HintFragment.OnFragmentInteractionListener {
    //UI VIEWS
    private EditText mEditAccount;//账号EditText
    private EditText mEditPassword;//密码EditText
    private Button mBtnLogin;//登录Button
    private TextView mTxtVersion;//APP版本
    private HintFragment mHintDialog;//错误提示信息弹窗
    private HintFragment mUpdateDialog;//更新提示框
    public static String HINT = "hint_dialog";
    public static String UPDATE = "update_dialog";
    private String downLoadUrl = null;
    private int minVersion = 0;
    private int mErrorCode;
    private boolean isRunning;//判断Activity是否正在运行
    private boolean showUpdate = true;
    private AppUpdateService updateService;
    private SharedPreferences mSharedPreference;

    private final int APP_UPDATE_YES = 0;
    private final int APP_UPDATE_NO = 1;
    private final int LOGIN_ERROR_CANCEL = 2;
    private final int LOGIN_ERROR_CONFIRM = 3;
    public static final String DEBUG = "login_activity";
    public static String pushOrderId = "";
    public static final String SHOW_UPDATE = "show_update";
    public static boolean isNeedGoToOperatorOrderPage = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSharedPreference.getBoolean(SharedPrefManager.APP_FIRST_OPEN, true)) {
            GuideActivity.showGuideActivity(LoginActivity.this);
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void provideLayout() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initView() {
        mEditAccount = (EditText) findViewById(R.id.edit_loginAccount);
        mEditPassword = (EditText) findViewById(R.id.edit_loginPassword);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mTxtVersion = (TextView) findViewById(R.id.text_versionLogin);
        //注销更新
//        if (getIntent() != null) {
//            showUpdate = getIntent().getBooleanExtra(SHOW_UPDATE, true);
//        }
    }

    @Override
    protected void initData() {
        isNeedGoToOperatorOrderPage = false;
        EventBus.getDefault().register(this);
        mSharedPreference = SharedPrefManager.getSystemSharedPref(this);
        //如果没有打开过app，跳转引导页
        if (mSharedPreference.getBoolean(SharedPrefManager.APP_FIRST_OPEN, true)) {
            return;
        }
        //如果已经登录，跳转主界面
        if (mSharedPreference.getBoolean(SharedPrefManager.LOGIN_STATUS, false)) {
            Intent mainIntent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(mainIntent);
            finish();
        } else {//初始化登录界面
            updateService = new AppUpdateService();
            mTxtVersion.setText(getResources().getString(R.string.text_app_version) + Utils.getAppVersion(this));
            //注销更新
//            checkAppVersion();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPostResume() {
        isRunning = true;
        super.onPostResume();
    }

    @Override
    protected void onStop() {
        isRunning = false;
        super.onStop();
    }

    @Override
    protected void setClickLintenner() {
        if(mBtnLogin != null)
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                attempLogin();
                break;
        }
    }

    /**
     * 登录
     */
    private void attempLogin() {
        if (Utils.checkInternetStatus(this) == 0) {
            showErrorDialog(getResources().getString(R.string.text_internet_unavalible));
            return;
        }
        //注销更新
//        if (AppUpdateService.isDownloading) {
//            Toast.makeText(this, R.string.text_login_afterdownload, Toast.LENGTH_SHORT).show();
//            return;
//        }
        String account = mEditAccount.getText().toString().trim();
        String password = mEditPassword.getText().toString();
        //验证输入格式
        if (account == null || TextUtils.isEmpty(account) || account.length() <= 3 || password == null
                || TextUtils.isEmpty(password) || password.length() <= 3) {
            Toast.makeText(LoginActivity.this, R.string.text_account_error, Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("user_code", account);
        params.put("password", password);
        params.put("imei_id", Utils.getIMEI(this));
        params.put("app_version", Utils.getVersionCode(this) + "");
        params.put("machine_name", Build.MODEL);
        showLoadingDialog();
        HttpClientManager.post(NetInterface.URL_LOGIN, params, new JsonHttpCallBack<LoginData>() {
            @Override
            public void onSuccess(LoginData result) {
                dismissLoadingDialog();
//                if (ResultUtils.isSuccess(LoginActivity.this,result)) {
                if (result != null && Integer.parseInt(result.getStatus_code()) == ErrorCode.STATUS_SUCCESS) {
                    LoginData.Data data = result.getData();
                    if (data != null) {
                        SharedPreferences sharedPreference = SharedPrefManager.getSystemSharedPref(LoginActivity.this);
                        SharedPrefManager.saveStringInSharePref(sharedPreference, SharedPrefManager.LOGIN_USER_CODE, data.user_code);
                        SharedPrefManager.saveStringInSharePref(sharedPreference, SharedPrefManager.LOGIN_USER_NAME, data.user_name);
                        SharedPrefManager.saveStringInSharePref(sharedPreference, SharedPrefManager.LOGIN_HEAD_IMAGE, data.head_image);
                        SharedPrefManager.saveStringInSharePref(sharedPreference, SharedPrefManager.LOGIN_POSITION_NAME, data.position_name);
                        SharedPrefManager.saveStringInSharePref(sharedPreference, SharedPrefManager.LOGIN_SET_TIME, data.set_time);
                        SharedPrefManager.saveStringInSharePref(sharedPreference, SharedPrefManager.LOGIN_QRCODE_IMAGE, data.qrcode_image);
                        SharedPrefManager.saveStringInSharePref(sharedPreference, SharedPrefManager.LOGIN_STATION_NAME, data.station_name);
                        SharedPrefManager.saveStringInSharePref(sharedPreference, SharedPrefManager.LOGIN_USER_MOBILE, data.user_mobile);
                        SharedPrefManager.saveStringInSharePref(sharedPreference, SharedPrefManager.LOGIN_TOKEN, data.token);
                        SharedPrefManager.saveBooleanInSharePref(sharedPreference, SharedPrefManager.LOGIN_STATUS, true);
                        Intent mainIntent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(mainIntent);
                        if (isNeedGoToOperatorOrderPage && (!TextUtils.isEmpty(pushOrderId))) {
                            OperateOrderActivity.openOrderDetail(LoginActivity.this, pushOrderId, true);
                            pushOrderId = "";
                        }
                        finish();
                    }
                } else {
                    //注销更新
                    //如果更新弹窗还没dismiss，不弹出错误信息窗口
//                    if (mUpdateDialog != null && mUpdateDialog.isResumed())
//                        return;

                    if (result != null)
                        showErrorDialog(result.getMessage());
                    else
                        Utils.showServerError(LoginActivity.this);
                    mEditPassword.setText("");
                }
            }

            @Override
            public void onError(Exception e) {
                dismissLoadingDialog();
                Utils.showServerError(LoginActivity.this);
            }
        });

    }

    /**
     * 错误弹窗信息
     */
    private void showErrorDialog(String msg) {
        if (mHintDialog == null && isRunning) {
            mHintDialog = HintFragment.newInstance(LOGIN_ERROR_CANCEL, LOGIN_ERROR_CONFIRM, null, msg,
                    null,
                    getResources().getString(R.string.text_cancel),
                    getResources().getString(R.string.text_confirm));
            mHintDialog.setCancelable(false);
        }

        if (isRunning && !mHintDialog.isAdded()) {
            mHintDialog.show(getFragmentManager(), HINT);
        }
    }

    /**
     * 错误弹窗信息
     */
    private void showUpdateDialog(ArrayList<String> items) {
        if (mUpdateDialog == null&& isRunning) {
            mUpdateDialog = HintFragment.newInstance(APP_UPDATE_NO, APP_UPDATE_YES,
                    getResources().getString(R.string.text_update_title),
                    null,
                    items,
                    getResources().getString(R.string.text_cancel),
                    getResources().getString(R.string.text_confirm));
            mUpdateDialog.setCancelable(false);
        }
        if (isRunning && !mUpdateDialog.isAdded()) {
            mUpdateDialog.show(getFragmentManager(), UPDATE);
        }
    }

    @Override
    public void onFragmentInteraction(int Action) {
        switch (Action) {
            case APP_UPDATE_YES:
                if (mUpdateDialog != null)
                    mUpdateDialog.dismiss();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (PackageManager.PERMISSION_GRANTED != checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, 0);
                        return;
                    }
                }
                Intent intent = new Intent(LoginActivity.this, AppUpdateService.class);
                intent.putExtra(AppUpdateService.DOWN_URL, downLoadUrl);
                startService(intent);
                break;
            case APP_UPDATE_NO:
                if (mUpdateDialog != null)
                    mUpdateDialog.dismiss();
                if (Utils.getVersionCode(LoginActivity.this) <= minVersion) {
                    finish();
                }
                break;
            case LOGIN_ERROR_CONFIRM:
//                if (mErrorCode == ErrorCode.STATUS_APPVERSION_ERROR) {
//                    checkAppVersion();
//                }
                break;
            case LOGIN_ERROR_CANCEL:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 && permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(LoginActivity.this, AppUpdateService.class);
            intent.putExtra(AppUpdateService.DOWN_URL, downLoadUrl);
            startService(intent);
        }
    }

    /**
     * 检测版本更新
     */
    public void checkAppVersion() {
        if (AppUpdateService.isDownloading)
            return;
        UpdateManager.getLatestAppInfo(new UpdateManager.AppInfoLinstenner() {
            @Override
            public void onResult(UpdateManager.UpdateInfo info) {
                try{
                    if (Integer.parseInt(info.getVersion()) > Utils.getVersionCode(LoginActivity.this) && showUpdate) {
                        if (mHintDialog != null && mHintDialog.isResumed())
                            mHintDialog.dismiss();
                        showUpdateDialog((ArrayList<String>) info.getNotes());
                        downLoadUrl = info.getDlurl();
                        minVersion = Integer.parseInt(info.getMinVersion() == null ? "0" : info.getMinVersion());
                    }
                }catch (Exception e){
                    Toast.makeText(LoginActivity.this,"未能获取最新版本信息",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * 跳转到LoginActivity
     */
    public static void openLoginActivity(Context context, boolean showUpload) {
        Intent intent_Login = new Intent(context, LoginActivity.class);
        intent_Login.putExtra(SHOW_UPDATE, showUpload);
        context.startActivity(intent_Login);
    }

    // This method will be called when a MessageEvent is posted
    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        if (event != null) {
            if (event.getType() == MessageEvent.Type.NEED_GO_TO_ORDER_PAGE) {
                //需要多级跳
                isNeedGoToOperatorOrderPage = true;
                pushOrderId = event.message;
            } else {
                //不需要多级跳
                isNeedGoToOperatorOrderPage = false;

            }
        }

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }
}

