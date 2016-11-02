package com.lenovo.csd.eservice.http;

import android.widget.ImageView;

import com.lenovo.csd.eservice.http.callback.HttpCallBack;
import com.lenovo.csd.eservice.http.request.HttpRequest;
import com.lenovo.csd.eservice.http.request.ImageRequest;

import java.util.Map;


/**
 * Created by shengtao
 * Date:2016/7/
 * Time:11:03
 */
public class HttpClientManager {

    public static void displayImage(ImageView iv, String url) {
        ImageRequest request = new ImageRequest.Builder().url(url).imgView(iv).create();
        ImageLoader.getProvider().loadImage(request);
    }

    public static void get(String url, HttpCallBack callBack) {
        HttpRequest request = new HttpRequest.Builder().method(HttpRequest.Method.GET).url(url).create();
        HttpHelper.getProvider().loadString(request, callBack);
    }

    public static void post(String url, Map<String, String> params, HttpCallBack callBack) {
        HttpRequest request = new HttpRequest.Builder().method(HttpRequest.Method.POST).url(url).params(params).create();
        HttpHelper.getProvider().loadString(request, callBack);
    }

}
