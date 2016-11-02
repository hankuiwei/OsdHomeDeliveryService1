package com.lenovo.csd.eservice.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.activity.AboutActivity;
import com.lenovo.csd.eservice.activity.FadeBackActivity;
import com.lenovo.csd.eservice.activity.MyAttachments;
import com.lenovo.csd.eservice.activity.MyCommentActivity;
import com.lenovo.csd.eservice.activity.MyQRCodeActivity;
import com.lenovo.csd.eservice.activity.MySettingActivity;
import com.lenovo.csd.eservice.activity.MyToolsActivity;
import com.lenovo.csd.eservice.cache.SharedPrefManager;
import com.lenovo.csd.eservice.entity.base.BaseData;
import com.lenovo.csd.eservice.entity.base.SimpleMsgData;
import com.lenovo.csd.eservice.http.HttpClientManager;
import com.lenovo.csd.eservice.http.NetInterface;
import com.lenovo.csd.eservice.http.ResultUtils;
import com.lenovo.csd.eservice.http.callback.adapter.JsonHttpCallBack;
import com.lenovo.csd.eservice.util.Utils;
import com.lenovo.csd.eservice.widget.CircleImageView;

import java.util.HashMap;


/**
 * 我的tab页面
 * Created by shengtao
 * on 2016/7/26
 * 23:48
 */

public class AtMeFragment extends BaseFragment implements View.OnClickListener {

    private final String TAG = this.getClass().getSimpleName();
    private String mQRUrl;

    private boolean isSelfSelected;
    private boolean isFirstLoad = true;

    private String mheadUrl;
    private CircleImageView mcyclerImg;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences sharedPreference = SharedPrefManager.getSystemSharedPref(getActivity());

        mheadUrl = SharedPrefManager.getStringInSharePref(sharedPreference, SharedPrefManager.LOGIN_HEAD_IMAGE, "head_image");
        String mPositon = SharedPrefManager.getStringInSharePref(sharedPreference, SharedPrefManager.LOGIN_POSITION_NAME, "position_name");
        String mName = SharedPrefManager.getStringInSharePref(sharedPreference, SharedPrefManager.LOGIN_USER_NAME, "user_name");
        mQRUrl = SharedPrefManager.getStringInSharePref(sharedPreference, SharedPrefManager.LOGIN_QRCODE_IMAGE, "qrcode_image");
        View view = View.inflate(getActivity(), R.layout.fragment_at_me, null);
        TextView mtxTName = (TextView) view.findViewById(R.id.mineName);
        TextView mtxTPosition = (TextView) view.findViewById(R.id.minePositionName);
        mcyclerImg = (CircleImageView) view.findViewById(R.id.mineHead);
//        Utils.loadImageWithGlide(getActivity(), mheadUrl, mcyclerImg, R.drawable.default_load_img);
        if (!"user_name".equals(mName)) {
            mtxTName.setText(mName);
        }
        if (!"position_name".equals(mPositon)) {
            mtxTPosition.setText(mPositon);
        }
        view.findViewById(R.id.mineQRCode).setOnClickListener(this);
        view.findViewById(R.id.mineCommentRel).setOnClickListener(this);
        view.findViewById(R.id.mineAppendixRel).setOnClickListener(this);
        view.findViewById(R.id.myTools).setOnClickListener(this);
        view.findViewById(R.id.mineSettingRel).setOnClickListener(this);
        view.findViewById(R.id.mineFeedbackRel).setOnClickListener(this);
        view.findViewById(R.id.mineExitBtn).setOnClickListener(this);
        view.findViewById(R.id.mineAbout).setOnClickListener(this);
        return view;
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

    /**
     * tab切换要用，预留
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mineQRCode:
                MyQRCodeActivity.toQRCode(getActivity(), mQRUrl);
                break;
            case R.id.mineCommentRel:
                MyCommentActivity.toComment(getActivity());
                break;
            case R.id.mineAppendixRel:
                MyAttachments.openAttachmentActivity(getContext());
                break;
            case R.id.myTools:
                MyToolsActivity.openToolActivity(getActivity());
                break;
            case R.id.mineSettingRel:
                MySettingActivity.toSet(getActivity());
                break;
            case R.id.mineFeedbackRel:
                FadeBackActivity.openActivity(getActivity());
                break;
            case R.id.mineAbout:
                AboutActivity.openAboutActivity(getActivity());
                break;
            case R.id.mineExitBtn:
//                Utils.exitToLogin(getContext());
                performLogout();
                break;
        }
    }

    @Override
    public void setSelected(boolean selected) {
        isSelfSelected = selected;
        if (isSelfSelected && isFirstLoad) {
            Utils.loadImageWithGlide(getActivity(), mheadUrl, mcyclerImg, R.drawable.default_load_img);
            isFirstLoad = false;
        }
    }

    /**
     * 登出操作
     */
    public void performLogout() {
        if (Utils.checkInternetStatus(getActivity()) == 0) {
            Toast.makeText(getActivity(), R.string.text_internet_unavalible, Toast.LENGTH_SHORT).show();
            return;
        }
        String userCode = Utils.getUserCode(getActivity());
        String token = Utils.getToken(getActivity());
        showLoadingDialog();
        HttpClientManager.get(NetInterface.URL_LOGOUT + userCode + "?token=" + token, new JsonHttpCallBack<BaseData>() {
            @Override
            public void onSuccess(BaseData result) {
                dismissLoadingDialog();
                if (ResultUtils.isSuccess(getActivity(), result)) {
                    Utils.exitToLogin(getActivity());
                }
            }

            @Override
            public void onError(Exception e) {
                dismissLoadingDialog();
                Utils.showServerError(getActivity());
            }
        });


    }
}
