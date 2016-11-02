package com.lenovo.csd.eservice.http.callback.adapter;


import com.lenovo.csd.eservice.http.callback.HttpCallBack;

/**
 * Created by turing
 * Date:2016/7/21
 * Time:11:03
 */
public abstract class StringHttpCallBack extends HttpCallBack<String> {
    @Override
    public String parseData(String result) {
        return result;
    }
}
