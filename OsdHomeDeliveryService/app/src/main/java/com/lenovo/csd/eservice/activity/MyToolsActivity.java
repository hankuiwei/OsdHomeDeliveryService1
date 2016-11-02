package com.lenovo.csd.eservice.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.http.callback.NoDoubleClickLinstenner;

public class MyToolsActivity extends BaseActivity {
    private LinearLayout mLinearMachine;
    private LinearLayout mLinearFillBox;
    private RelativeLayout mRelback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void provideLayout() {
        setContentView(R.layout.activity_my_tools);

    }

    @Override
    protected void initView() {
        mRelback = (RelativeLayout) findViewById(R.id.relative_back);
        mLinearMachine = (LinearLayout) findViewById(R.id.linearMachine);
        mLinearFillBox = (LinearLayout) findViewById(R.id.linearFillbox);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setClickLintenner() {
        mRelback.setOnClickListener(noDoubleClickLinstenner);
        mLinearMachine.setOnClickListener(noDoubleClickLinstenner);
        mLinearFillBox.setOnClickListener(noDoubleClickLinstenner);
    }

    private NoDoubleClickLinstenner noDoubleClickLinstenner = new NoDoubleClickLinstenner() {
        @Override
        public void doClick(View view) {
            switch (view.getId()) {
                case R.id.relative_back:
                    finish();
                    break;
                case R.id.linearMachine:
                    MoreAboutMachineActivity.openMoreAboutMachineAct(MyToolsActivity.this, "", MoreAboutMachineActivity.TYPE_MACHINE);
                    break;
                case R.id.linearFillbox:
                    MoreAboutMachineActivity.openMoreAboutMachineAct(MyToolsActivity.this, "", MoreAboutMachineActivity.TYPE_FILLBOX);
                    break;
            }
        }
    };

    public static void openToolActivity(Activity context) {
        Intent toolIntent = new Intent(context, MyToolsActivity.class);
        context.startActivity(toolIntent);
    }
}
