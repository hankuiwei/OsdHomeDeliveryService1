package com.lenovo.csd.eservice.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.adapter.LocalAttachAdapter;
import com.lenovo.csd.eservice.adapter.OrderFragmentAdapter;
import com.lenovo.csd.eservice.fragment.UnuploadAttachFragment;
import com.lenovo.csd.eservice.fragment.UploadAttachFragment;
import com.lenovo.csd.eservice.http.callback.NoDoubleClickLinstenner;

import java.util.ArrayList;

public class MyAttachments extends BaseFragmentActivity implements ViewPager.OnPageChangeListener, LocalAttachAdapter.UploadResult {
    //ui声明
    private RelativeLayout mBackLayout;
    private TextView mTxtUnuploadLine;
    private RelativeLayout mUnuploadTab;
    private ImageView mImgUnupload;
    private TextView mTxtUnupload;
    private TextView mTxtUploadLine;
    private RelativeLayout mUploadTab;
    private ImageView mImgUpload;
    private TextView mTxtUpload;
    private ViewPager mViewPager;

    //变量声明

    private OrderFragmentAdapter mPagerAdapter;
    private ArrayList<Fragment> fragments;
    private UnuploadAttachFragment unUploadFrag;//待上传
    private UploadAttachFragment uploadFrag;//已上传
    private int selectedPosition;//选中的位置
    private boolean isStop;


    //常量声明
    public static final int TAB_UNUPLOAD = 0;
    public static final int TAB_UPLOAD = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        provideLayout();
        initView();
        initData();
        setClickLintenner();
    }

    protected void provideLayout() {
        setContentView(R.layout.activity_my_attachments);
    }

    protected void initView() {
        mBackLayout = (RelativeLayout) findViewById(R.id.relative_back);
        mTxtUnuploadLine = (TextView) findViewById(R.id.text_unUploadAttachLine);
        mUnuploadTab = (RelativeLayout) findViewById(R.id.relative_unUploadTab);
        mImgUnupload = (ImageView) findViewById(R.id.img_unUploadAttach);
        mTxtUnupload = (TextView) findViewById(R.id.text_unUploadAttach);
        mTxtUploadLine = (TextView) findViewById(R.id.text_uploadAttachLine);
        mUploadTab = (RelativeLayout) findViewById(R.id.relative_uploadTab);
        mImgUpload = (ImageView) findViewById(R.id.img_uploadAttach);
        mTxtUpload = (TextView) findViewById(R.id.text_uploadAttach);
        mViewPager = (ViewPager) findViewById(R.id.viewPager_myAttachments);

    }

    protected void initData() {
        fragments = new ArrayList<>();
        unUploadFrag = UnuploadAttachFragment.newInstance();
        uploadFrag = UploadAttachFragment.newInstance();
        fragments.add(unUploadFrag);
        fragments.add(uploadFrag);
        mPagerAdapter = new OrderFragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mPagerAdapter);

    }

    protected void setClickLintenner() {
        mBackLayout.setOnClickListener(noDoubleClickLinstenner);
        mUnuploadTab.setOnClickListener(noDoubleClickLinstenner);
        mUploadTab.setOnClickListener(noDoubleClickLinstenner);
        mViewPager.setOnPageChangeListener(this);
    }

    //双击监听事件
    private NoDoubleClickLinstenner noDoubleClickLinstenner = new NoDoubleClickLinstenner() {
        @Override
        public void doClick(View view) {
            switch (view.getId()) {
                case R.id.relative_back:
                    finish();
                    break;
                case R.id.relative_unUploadTab:
                    if (selectedPosition == TAB_UNUPLOAD)
                        return;
                    changeUIStatus(TAB_UNUPLOAD);
                    mViewPager.setCurrentItem(TAB_UNUPLOAD);
                    selectedPosition = TAB_UNUPLOAD;
                    break;
                case R.id.relative_uploadTab:
                    if (selectedPosition == TAB_UPLOAD)
                        return;
                    changeUIStatus(TAB_UPLOAD);
                    mViewPager.setCurrentItem(TAB_UPLOAD);
                    selectedPosition = TAB_UPLOAD;
                    break;
            }
        }
    };

    /**
     * 切换顶部UI视图和fragment页面
     */
    private void changeUIStatus(int position) {
        switch (position) {
            case TAB_UNUPLOAD:
                mTxtUnuploadLine.setBackgroundColor(getResources().getColor(R.color.red_E47B78));
                mTxtUnupload.setTextColor(getResources().getColor(R.color.red_E47B78));
                mImgUnupload.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_waiting_selected));
                mTxtUploadLine.setBackgroundColor(getResources().getColor(R.color.gray_F8F8F8));
                mTxtUpload.setTextColor(getResources().getColor(R.color.gray_cccccc));
                mImgUpload.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_success_unselected));
                break;
            case TAB_UPLOAD:
                mTxtUnuploadLine.setBackgroundColor(getResources().getColor(R.color.gray_F8F8F8));
                mTxtUnupload.setTextColor(getResources().getColor(R.color.gray_cccccc));
                mImgUnupload.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_waiting_unselected));

                mTxtUploadLine.setBackgroundColor(getResources().getColor(R.color.red_E47B78));
                mTxtUpload.setTextColor(getResources().getColor(R.color.red_E47B78));
                mImgUpload.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_success_selected));
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectedPosition = position;
        changeUIStatus(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 跳转到附件页面
     */
    public static void openAttachmentActivity(Context context) {
        Intent intent = new Intent(context, MyAttachments.class);
        context.startActivity(intent);
    }

    //未上传附件页面上传结果回调
    @Override
    public void onSuccess(int position) {
        if (isStop)
            return;
        //已上传的fragment刷新数据
//        unUploadFrag.getDatafromDB();
        unUploadFrag.removeItem(position);
//        unUploadFrag.getDatafromDB();
        uploadFrag.performRefresh();
    }

    @Override
    public void onFail(String orderId) {
        if (isStop)
            return;
        Toast.makeText(this, "工单" + orderId + "附件上传失败，请稍后重试", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDelete(int position) {
        unUploadFrag.removeItem(position);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isStop = false;
    }

    @Override
    protected void onStop() {
        isStop = true;
        super.onStop();
    }
}
