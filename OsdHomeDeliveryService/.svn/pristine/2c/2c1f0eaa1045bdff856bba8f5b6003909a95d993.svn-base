package com.lenovo.csd.eservice.http.callback;

/**
 * Created by shengtao
 * Date:2016/7/21
 * Time:11:03
 */
public abstract class HttpCallBack<T> {

    public void onStart() {
    }

    public void onLoading(int progress) {
    }

    public abstract void onSuccess(T result);

    public abstract void onError(Exception e);

    public abstract T parseData(String result);
}

