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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.http.NetInterface;
import com.lenovo.csd.eservice.util.Utils;

/**
 * Created by hankw on 16-8-17.
 */
public class CollectUserinfoActivity2 extends BaseActivity implements View.OnClickListener{

    private final String TAG = getClass().getSimpleName();
    private String mOrderId;// = "00005";
    private String mType;//="server";
    private WebView mWebVCollect;
    private final static String ORDERID = "orderId";
    private final static String INFO_TYPE = "type";

    private LinearLayout mContent;
    private Button mSubmit;
    private LinearLayout mBackUp;

    @Override
    protected void provideLayout() {
        setContentView(R.layout.activity_collectuserinfo2);
    }

    @Override
    protected void initView() {
       mContent = (LinearLayout) findViewById(R.id.Lin_Content);
       mSubmit = (Button) findViewById(R.id.Btn_CollCommit);
        mBackUp = (LinearLayout) findViewById(R.id.collect_Lin);
    }

    @Override
    protected void initData() {
        int netState =  Utils.checkInternetStatus(CollectUserinfoActivity2.this);
        if (netState == 0) {
            Toast.makeText(CollectUserinfoActivity2.this,"网络不可用,请连接网络后重试",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = getIntent();
        // TODO: 16-8-17  实现跳转后传数据
        mOrderId = intent.getStringExtra(ORDERID);
        mType = intent.getStringExtra(INFO_TYPE);
        if (mOrderId == null || "".equals(mOrderId)||mType == null || "".equals(mType))
            return;
        String url = NetInterface.COLLECTUSERINFO+"/type/"+mType+"?orderid="+mOrderId;


    }

    @Override
    protected void setClickLintenner() {
       mBackUp.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
    }

    public static void toCollect(Activity activity,String orderId,String type){
        Intent intent = new Intent(activity,CollectUserinfoActivity2.class);
        intent.putExtra(ORDERID,orderId);
        intent.putExtra(INFO_TYPE,type);
        activity.startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.collect_Lin:
                finish();
                break;
            case R.id.Btn_CollCommit:
                break;
        }
    }
}
