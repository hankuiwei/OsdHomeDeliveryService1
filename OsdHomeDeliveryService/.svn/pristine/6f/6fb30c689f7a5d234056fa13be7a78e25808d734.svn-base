package com.lenovo.csd.eservice.http;

import android.content.Context;
import android.widget.Toast;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.entity.base.BaseData;
import com.lenovo.csd.eservice.util.Utils;

/**
 * Created by 彤 on 2016/8/1.
 */
public class ResultUtils {
    //判断网络请求回调结果
    public static boolean isSuccess(Context context, BaseData data) {
        if (data != null && Integer.parseInt(data.getStatus_code()) == ErrorCode.STATUS_SUCCESS) {
            return true;
        } else {
            if (data != null) {
                int errorCode = Integer.parseInt(data.getStatus_code());
                switch (errorCode) {
                    case ErrorCode.STATUS_APPVERSION_ERROR:
                        break;
                    case ErrorCode.STATUS_TOKEN_ERROR:
                        if (context != null) {
                            Toast.makeText(context, R.string.text_token_failed, Toast.LENGTH_SHORT).show();
                            Utils.exitToLogin(context);
                        }
                        break;
                    case ErrorCode.STATUS_LACK_TOKEN:
                    case ErrorCode.STATUS_REQUESTMETHOD_ERROR:
                    case ErrorCode.STATUS_PARAM_ERROR:
                    case ErrorCode.STATUS_OTHER_ERROR:
                        if (data != null && context != null)
                            Toast.makeText(context, data.getMessage(), Toast.LENGTH_SHORT).show();
                        break;
                }
            } else {
                if (context != null) {
                    Toast.makeText(context, "获取数据有误", Toast.LENGTH_SHORT).show();
                }
            }
        }
        return false;
    }
}
