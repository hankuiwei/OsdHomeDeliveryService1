package com.lenovo.csd.eservice.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.util.Utils;

/**
 * Created by hankw on 16-7-20.
 * 我的二维码
 */
public class MyQRCodeActivity extends BaseActivity {

    private ImageView mimageView;
    private String mQrUrl;
    private final static String ORURL="QR";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        initView();

    }

    @Override
    protected void provideLayout() {
        setContentView(R.layout.activity_my_qr_code);
    }

    @Override
    protected void initView() {
        mimageView = (ImageView)findViewById(R.id.myQRCodeimg);
        Utils.loadImageWithGlide(MyQRCodeActivity.this,mQrUrl,mimageView,R.drawable.default_load_img);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mQrUrl = intent.getStringExtra(ORURL);

    }

    @Override
    protected void setClickLintenner() {

    }

    public static void toQRCode(Activity activity,String url){
        Intent intent = new Intent(activity,MyQRCodeActivity.class);
        intent.putExtra(ORURL,url);
        activity.startActivity(intent);
    }
}
