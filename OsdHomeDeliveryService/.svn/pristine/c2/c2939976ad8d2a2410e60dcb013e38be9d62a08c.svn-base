package com.lenovo.csd.eservice.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.adapter.SuggestionPicAdapter;
import com.lenovo.csd.eservice.cache.SharedPrefManager;
import com.lenovo.csd.eservice.entity.base.BaseData;
import com.lenovo.csd.eservice.http.AttachmentUploadUtils;
import com.lenovo.csd.eservice.http.ErrorCode;
import com.lenovo.csd.eservice.http.HttpClientManager;
import com.lenovo.csd.eservice.http.NetInterface;
import com.lenovo.csd.eservice.http.ResultUtils;
import com.lenovo.csd.eservice.http.callback.NoDoubleClickLinstenner;
import com.lenovo.csd.eservice.http.callback.adapter.JsonHttpCallBack;
import com.lenovo.csd.eservice.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FadeBackActivity extends BaseActivity implements SuggestionPicAdapter.OnAddPicLinstenner, AttachmentUploadUtils.UploadLinstenner {
    private RelativeLayout mRelativeBack;
    private EditText mEditSuggestion;
    private Button mBtnCommit;
    private RecyclerView mRecyclerPics;
    private FrameLayout frameBack;
    private LinearLayout linearGetpics;
    private LinearLayout linearThumb;
    private LinearLayout linearCamera;
    private LinearLayout linearCancel;
    private Animation showAnim;//图片方式出现
    private Animation hideAnim;//图片方式消失


    //全局变量声明
    //建议内容
    private String mContent;
    private SharedPreferences mSharedPreference;
    private String token;
    private String userCode;
    private String app_version;
    private String machine_name;
    private String system_version;
    private Uri fileUri;//获取到图片的Uri
    private String filePath;//获取到图片的路径

    private ArrayList<String> filePaths;
    private ArrayList<String> fileUrls;
    private SuggestionPicAdapter recycleAdapter;
    private boolean isCommiting;
    private int count;
    private AttachmentUploadUtils.UploadAttachAsyncTask task = null;
    private boolean isStop;
    //常量
    public static final int REQUEST_THUMB = 10;
    public static final int REQUEST_CAMERA = 11;
    public static final int REQUEST_CAMERA_PERMISSION = 12;
    public static final int REQUEST_FILE_PERMISSION = 13;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void provideLayout() {
        setContentView(R.layout.activity_fade_back);
    }

    @Override
    protected void initView() {
        mRelativeBack = (RelativeLayout) findViewById(R.id.relative_back);
        mEditSuggestion = (EditText) findViewById(R.id.edit_inputSuggestions);
        mBtnCommit = (Button) findViewById(R.id.btn_commite);
        mRecyclerPics = (RecyclerView) findViewById(R.id.recycler_pics);
        frameBack = (FrameLayout) findViewById(R.id.frame_backGound);
        linearGetpics = (LinearLayout) findViewById(R.id.linear_getPic);
        linearThumb = (LinearLayout) findViewById(R.id.linear_picThumb);
        linearCamera = (LinearLayout) findViewById(R.id.linear_picCamera);
        linearCancel = (LinearLayout) findViewById(R.id.linear_picCancel);
    }

    @Override
    protected void initData() {
        mSharedPreference = SharedPrefManager.getSystemSharedPref(this);
        token = mSharedPreference.getString(SharedPrefManager.LOGIN_TOKEN, "");
        userCode = mSharedPreference.getString(SharedPrefManager.LOGIN_USER_CODE, "");
        app_version = Utils.getAppVersion(this);
        machine_name = Build.MODEL;
        system_version = Build.VERSION.RELEASE;
        filePaths = new ArrayList<>();
        fileUrls = new ArrayList<>();
        recycleAdapter = new SuggestionPicAdapter(this, filePaths, this);
        mRecyclerPics.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerPics.setItemAnimator(new DefaultItemAnimator());
        mRecyclerPics.setAdapter(recycleAdapter);
        showAnim = AnimationUtils.loadAnimation(this, R.anim.anim_show_frombottom);
        hideAnim = AnimationUtils.loadAnimation(this, R.anim.anim_hide_tobottom);
        hideAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linearGetpics.setVisibility(View.GONE);
                frameBack.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    @Override
    protected void setClickLintenner() {
        mRelativeBack.setOnClickListener(noDoubleClickListener);
        mBtnCommit.setOnClickListener(noDoubleClickListener);
        frameBack.setOnClickListener(noDoubleClickListener);
        linearThumb.setOnClickListener(noDoubleClickListener);
        linearCamera.setOnClickListener(noDoubleClickListener);
        linearCancel.setOnClickListener(noDoubleClickListener);
    }

    private NoDoubleClickLinstenner noDoubleClickListener = new NoDoubleClickLinstenner() {
        @Override
        public void doClick(View view) {
            switch (view.getId()) {
                case R.id.relative_back:
                    finish();
                    break;
                case R.id.btn_commite:
                    if(!Utils.checkCameraAndFilePermission(FadeBackActivity.this,REQUEST_CAMERA))
                        return;
                    commitSuggestions();
                    break;
                case R.id.linear_picThumb://图库挑选图片
                    animPiclinear(false);
                    if(!Utils.checkCameraAndFilePermission(FadeBackActivity.this,REQUEST_CAMERA_PERMISSION))
                        return;
                    Utils.openPictureSelect(FadeBackActivity.this, REQUEST_THUMB);
                    break;
                case R.id.linear_picCamera://照相
                    animPiclinear(false);
                    if(!Utils.checkCameraAndFilePermission(FadeBackActivity.this,REQUEST_CAMERA))
                        return;
                    fileUri = Utils.openCamera(FadeBackActivity.this, REQUEST_CAMERA);
                    break;
                case R.id.linear_picCancel://取消
                    animPiclinear(false);
                    break;
                case R.id.frame_backGound:
                    animPiclinear(false);
                    break;
            }
        }
    };

    /**
     * 照相方式UI
     *
     * @param show
     */
    private void animPiclinear(boolean show) {
        if (show) {//
            frameBack.setVisibility(View.VISIBLE);
            linearGetpics.setVisibility(View.VISIBLE);
            linearGetpics.startAnimation(showAnim);
        } else {
            linearGetpics.startAnimation(hideAnim);
        }
    }

    /**
     * 提交反馈
     */
    public void commitSuggestions() {
        //1，判断网络
        if (Utils.checkInternetStatus(this) == 0) {
            Toast.makeText(this, R.string.text_internet_unavalible, Toast.LENGTH_SHORT).show();
            return;
        }
        //2,框内内容的判空
        mContent = mEditSuggestion.getText().toString().trim();
        if (mContent == null || TextUtils.isEmpty(mContent)) {
            Toast.makeText(this, R.string.text_content_null, Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO: 2016/8/26 上传图片文件数组
        if (filePaths == null || filePaths.size() == 0) {
            //没有图片，直接上传表单
            commiteWithoutPic();
        } else {
//            count = filePaths.size();
            showLoadingDialog();
            count = 0;
            isCommiting = true;
//            for (int index = 0; index < count; index++) {
            task = AttachmentUploadUtils.newTask(this, null, NetInterface.FADE_BACKPIC_URL + "token=" + token, this, true);
            task.execute(filePaths.get(count));
        }
    }

    //    }
    @Override
    public void attachUploadStart() {
//        showLoadingDialog();
    }

    @Override
    public void attachUpResult(String result) {
//        dismissLoadingDialog();
        count++;
        if (result == null) {
            isCommiting = false;
            task.cancel(true);
            Toast.makeText(FadeBackActivity.this, getResources().getString(R.string.text_fadeback_fail), Toast.LENGTH_SHORT).show();
            dismissLoadingDialog();
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(result);
            int status = jsonObject.getInt("status_code");
            if (status == ErrorCode.STATUS_SUCCESS) {
                JSONObject urlJson = jsonObject.getJSONObject("data");
                String imgUrl = urlJson.getString("img");
                fileUrls.add(imgUrl);
            }

            if (count == filePaths.size()) {//图片上传完毕，上传文本表单
                commiteWithoutPic();
            } else if (count < filePaths.size()) {
                task.cancel(true);
                task = null;
                //如果页面finish，结束提交
                if(isStop)
                    return;
//                task = new AttachmentUploadUtils.UploadAttachAsyncTask(FadeBackActivity.this, null, NetInterface.FADE_BACKPIC_URL + "token=" + token, this, true);
                task = AttachmentUploadUtils.newTask(FadeBackActivity.this, null, NetInterface.FADE_BACKPIC_URL + "token=" + token, this, true);
                task.execute(filePaths.get(count));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            dismissLoadingDialog();
            isCommiting = false;
            task.cancel(true);
            Toast.makeText(FadeBackActivity.this, getResources().getString(R.string.text_fadeback_fail), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 不带文件的上传
     */
    public void commiteWithoutPic() {
        HashMap<String, String> params = new HashMap<>();
        params.put("user_code", userCode);
        params.put("app_version", app_version);
        params.put("machine_name", machine_name);
        params.put("system_version", system_version);
        params.put("content", mContent);
        for (int index = 0; index < fileUrls.size(); index++) {
            params.put("image_" + index, fileUrls.get(index));
        }
        showLoadingDialog();
        isCommiting = true;
        HttpClientManager.post(NetInterface.FADE_BACK_URL + "token=" + token, params, new JsonHttpCallBack<BaseData>() {
            @Override
            public void onSuccess(BaseData result) {
                isCommiting = false;
                dismissLoadingDialog();
                if (ResultUtils.isSuccess(FadeBackActivity.this,result)) {
                    Toast.makeText(FadeBackActivity.this, R.string.text_fadeback_success, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onError(Exception e) {
                isCommiting = false;
                dismissLoadingDialog();
                Utils.showServerError(FadeBackActivity.this);
            }
        });
    }

    /**
     * 点击添加图片操作
     */
    @Override
    public void onAddClick() {
        frameBack.setVisibility(View.VISIBLE);
        linearGetpics.setVisibility(View.VISIBLE);
        linearGetpics.startAnimation(showAnim);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_THUMB:
                if (resultCode == RESULT_OK) {
                    if (data != null && data.getData() != null) {
                        fileUri = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(fileUri,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        filePath = cursor.getString(columnIndex);
                        cursor.close();
                        recycleAdapter.addPic(filePath);
                    } else {
                        Toast.makeText(FadeBackActivity.this, "图库出现异常", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    if (fileUri != null)
                        filePath = fileUri.getPath();
                    recycleAdapter.addPic(filePath);
                } else {
//                    Toast.makeText(AddAttachmentActivity.this, "拍照出现异常", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public static void openActivity(Activity context) {
        Intent intent = new Intent(context, FadeBackActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isStop = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isStop = true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
