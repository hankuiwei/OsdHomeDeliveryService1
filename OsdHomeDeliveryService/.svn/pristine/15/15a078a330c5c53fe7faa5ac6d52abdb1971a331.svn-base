package com.lenovo.csd.eservice.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.util.Utils;

public class AboutActivity extends BaseActivity {
    private RelativeLayout mRelativeBack;
    private TextView mTxtVersion;
    private String appVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void provideLayout() {
        setContentView(R.layout.activity_about);

    }

    @Override
    protected void initView() {
        mRelativeBack = (RelativeLayout) findViewById(R.id.relative_back);
        mTxtVersion = (TextView) findViewById(R.id.text_appVersion);
    }

    @Override
    protected void initData() {
        appVersion = Utils.getAppVersion(this);
        mTxtVersion.setText("当前版本：" + appVersion);

    }

    @Override
    protected void setClickLintenner() {
        mRelativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static void openAboutActivity(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }
}
