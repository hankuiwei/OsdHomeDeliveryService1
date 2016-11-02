package com.lenovo.csd.eservice.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.adapter.GuideAdapter;
import com.lenovo.csd.eservice.cache.SharedPrefManager;

import java.util.ArrayList;


public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ViewPager mPager;
    //    private CircleIndicator mIndicator;
    private ArrayList<View> views;
    private GuideAdapter mGuideAdapter;
    private LayoutInflater inflater;
    private View view1, view2, view3;
    private ImageView imageView1,imageView2,imageView3;
    //    private Button mBtnStart;
    private int currentPos = 0;
    public static String TAG = "guide";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void provideLayout() {
        setContentView(R.layout.activity_guide);

    }

    @Override
    protected void initView() {
        mPager = (ViewPager) findViewById(R.id.viewPagerGuide);
//        mIndicator = (CircleIndicator) findViewById(R.id.pagerIndicator);
        inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.guide_view_no1, null, false);
        view2 = inflater.inflate(R.layout.guide_view_no2, null, false);
        view3 = inflater.inflate(R.layout.guide_view_no3, null, false);
        imageView1 = (ImageView) view1.findViewById(R.id.imgGuide);
        imageView2 = (ImageView) view2.findViewById(R.id.imgGuide);
        imageView3 = (ImageView) view3.findViewById(R.id.imgGuide);

//        mBtnStart = (Button) view4.findViewById(R.id.btnStartApp);
    }

    @Override
    protected void initData() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
     imageView1.setImageDrawable(zoomDrawable(this,R.drawable.guide_1,width,height));
     imageView2.setImageDrawable(zoomDrawable(this,R.drawable.guide_2,width,height));
     imageView3.setImageDrawable(zoomDrawable(this,R.drawable.guide_3,width,height));

        views = new ArrayList<>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        mGuideAdapter = new GuideAdapter(this, views);
        mPager.setAdapter(mGuideAdapter);
        mPager.setOnPageChangeListener(this);
//        mIndicator.setViewPager(mPager);
    }

    @Override
    protected void setClickLintenner() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.e(TAG, "OnpageScroll" + position);
        if (position == currentPos && currentPos == views.size() - 1) {
            SharedPreferences preferences = SharedPrefManager.getSystemSharedPref(GuideActivity.this);
            SharedPrefManager.saveBooleanInSharePref(preferences, SharedPrefManager.APP_FIRST_OPEN, false);
            Intent loginIntent = new Intent(GuideActivity.this, LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);
            finish();
        }
        currentPos = position;
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
    public static void showGuideActivity(Activity context) {
        Intent guideIntent = new Intent(context, GuideActivity.class);
        guideIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(guideIntent);
    }

    static Bitmap drawableToBitmap(Drawable drawable) // drawable 转换成 bitmap
    {
        int width = drawable.getIntrinsicWidth();   // 取 drawable 的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;         // 取 drawable 的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);     // 建立对应 bitmap
        Canvas canvas = new Canvas(bitmap);         // 建立对应 bitmap 的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);      // 把 drawable 内容画到画布中
        return bitmap;
    }

    static Drawable zoomDrawable(Context context,int drawableId, int w, int h) {
        Drawable drawable = context.getResources().getDrawable(drawableId);
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable); // drawable 转换成 bitmap
        Matrix matrix = new Matrix();   // 创建操作图片用的 Matrix 对象
        float scaleWidth = ((float) w / width);   // 计算缩放比例
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);         // 设置缩放比例
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);       // 建立新的 bitmap ，其内容是对原 bitmap 的缩放后的图
        return new BitmapDrawable(newbmp);       // 把 bitmap 转换成 drawable 并返回
    }

}
