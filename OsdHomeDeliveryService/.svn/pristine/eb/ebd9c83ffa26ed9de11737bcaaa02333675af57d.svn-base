package com.lenovo.csd.eservice.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.http.NetInterface;
import com.lenovo.csd.eservice.util.Utils;

/**
 * Created by hankw on 16-8-17.
 */
public class CollectUserinfoActivity extends BaseActivity{

    private final String TAG = getClass().getSimpleName();
    private String mOrderId;// = "00005";
    private String mType;//="server";
    private WebView mWebVCollect;
    private final static String ORDERID = "orderId";
    private final static String INFO_TYPE = "type";

    @Override
    protected void provideLayout() {
        setContentView(R.layout.activity_collectuserinfo);


    }

    @Override
    protected void initView() {
       /* if (getst != null) {
            mWebView.restoreState(savedInstanceState);
        } else {
            mWebView.loadUrl("http://freebsd.csie.nctu.edu.tw/~freedom/html5/");
            //mWebView.loadUrl("file:///data/bbench/index.html");
        }*/
        mWebVCollect = (WebView) findViewById(R.id.webVCollect);

    }

    @Override
    protected void initData() {
        int netState =  Utils.checkInternetStatus(CollectUserinfoActivity.this);
        if (netState == 0) {
            Toast.makeText(CollectUserinfoActivity.this,"网络不可用,请连接网络后重试",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = getIntent();
        // TODO: 16-8-17  实现跳转后传数据
        mOrderId = intent.getStringExtra(ORDERID);
        mType = intent.getStringExtra(INFO_TYPE);
        if (mOrderId == null || "".equals(mOrderId)||mType == null || "".equals(mType))
            return;
        String url = NetInterface.COLLECTUSERINFO+"/type/"+mType+"?orderid="+mOrderId;
        mWebVCollect.loadUrl(url);
        mWebVCollect.setVerticalScrollBarEnabled(false);
        mWebVCollect.setHorizontalScrollBarEnabled(false);
        WebSettings settings = mWebVCollect.getSettings();
        settings.setJavaScriptEnabled(true); //加上这句话才能使用javascript方法
        settings.setDomStorageEnabled(true);
        mWebVCollect.requestFocus();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setSavePassword(false);
        settings.setSaveFormData(false);
        settings.setSupportZoom(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

    }

    @Override
    protected void setClickLintenner() {
        findViewById(R.id.collect_Lin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mWebVCollect.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showLoadingDialog();
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                dismissLoadingDialog();
                super.onPageFinished(view, url);
            }
        });
        //boolean flag;
        mWebVCollect.setWebChromeClient(new WebChromeClient(){

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }




            @Override
            public void onCloseWindow(WebView window) {
                super.onCloseWindow(window);
            }
        });


    }

    public static void toCollect(Activity activity,String orderId,String type){
        Intent intent = new Intent(activity,CollectUserinfoActivity.class);
        intent.putExtra(ORDERID,orderId);
        intent.putExtra(INFO_TYPE,type);
        activity.startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mWebVCollect.stopLoading();
    }
}
