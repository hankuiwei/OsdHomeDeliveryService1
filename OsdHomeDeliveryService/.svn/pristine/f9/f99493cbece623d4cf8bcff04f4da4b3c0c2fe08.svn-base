package com.lenovo.csd.eservice.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.adapter.GuideAdapter;
import com.lenovo.csd.eservice.cache.SharedPrefManager;
import com.lenovo.csd.eservice.http.callback.NoDoubleClickLinstenner;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ViewPager mPager;
    private CircleIndicator mIndicator;
    private ArrayList<View> views;
    private GuideAdapter mGuideAdapter;
    private LayoutInflater inflater;
    private View view1,view2,view3,view4;
    private Button mBtnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void provideLayout() {
        setContentView(R.layout.activity_guide);

    }

    @Override
    protected void initView() {
        mPager = (ViewPager) findViewById(R.id.viewPagerGuide);
        mIndicator = (CircleIndicator) findViewById(R.id.pagerIndicator);
        inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.guide_view_no1, null, false);
        view2 = inflater.inflate(R.layout.guide_view_no2, null, false);
        view3 = inflater.inflate(R.layout.guide_view_no3, null, false);
        view4 = inflater.inflate(R.layout.guide_view_no4, null, false);
        mBtnStart = (Button) view4.findViewById(R.id.btnStartApp);
    }

    @Override
    protected void initData() {
        views = new ArrayList<>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        mGuideAdapter = new GuideAdapter(this, views);
        mPager.setAdapter(mGuideAdapter);
        mPager.setOnPageChangeListener(this);
        mIndicator.setViewPager(mPager);
    }

    @Override
    protected void setClickLintenner() {
    mBtnStart.setOnClickListener(new NoDoubleClickLinstenner() {
        @Override
        public void doClick(View view) {
            SharedPreferences preferences = SharedPrefManager.getSystemSharedPref(GuideActivity.this);
            SharedPrefManager.saveBooleanInSharePref(preferences,SharedPrefManager.APP_FIRST_OPEN,false);
            Intent loginIntent = new Intent(GuideActivity.this,LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);
            finish();
        }
    });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 打开引导页
     */
    public static void showGuideActivity(Activity context){
        Intent guideIntent = new Intent(context,GuideActivity.class);
        guideIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(guideIntent);
    }


}
