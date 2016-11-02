package com.lenovo.csd.eservice.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.adapter.OrderFragmentAdapter;
import com.lenovo.csd.eservice.fragment.MachineMoreFragment;
import com.lenovo.csd.eservice.fragment.OrderBoxFragment;
import com.lenovo.csd.eservice.http.callback.NoDoubleClickLinstenner;

import java.util.ArrayList;
import java.util.List;

public class MoreAboutMachineActivity extends BaseFragmentActivity implements ViewPager.OnPageChangeListener {
    private RelativeLayout mRelativeBack;
    private TextView mShowTitle;
    private LinearLayout mLinearTabs;
    private TextView mTxtMachineInfoLine;//未使用Tab指示线
    private TextView mTxtFillBoxOrderLine;//已使用Tab指示线
    private RelativeLayout mRelativeMachineInfo;
    private RelativeLayout mRelativeFillBoxOrder;
    private ImageView mImgMachineInfo;
    private ImageView mImgBoxOrder;
    private TextView mTxtMachineInfo;
    private TextView mTxtFillBoxOrder;
    private ViewPager mViewPager;
    private FrameLayout mFrameContainer;

    //变量

    private MachineMoreFragment machineMoreFragment;//未使用备件fragment
    private OrderBoxFragment orderBoxFragment;//已使用备件fragment
    private List<Fragment> fragments;//数据
    private OrderFragmentAdapter partsAdapter;//适配器\
    private String productSn;
    private String showType;
    //常量
    private static final int TAB_MACHINEINFO = 0;//未使用页面下标位置
    private static final int TAB_FILLBOX = 1;
    public static final String PRODUCT_SN = "product_sn";//传递SN号key
    public static final String SHOW_TYPE = "show_type";//是否是选择装箱单信息
    public static final String TYPE_BOTH = "type_both";//是否是选择装箱单信息
    public static final String TYPE_MACHINE = "type_machine";//是否是选择装箱单信息
    public static final String TYPE_FILLBOX = "type_fillbox";//是否是选择装箱单信息
    public static final int REQUEST_MACHINE = 1;
    public static final int REQUEST_FILLBOX = 2;
    public static final int REQUEST_MACHINECAMARA_ALLOW = 3;
    public static final int REQUEST_FILLBOXCAMARA_ALLOW = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        provideLayout();
        initView();
        initData();
        setClickLintenner();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void provideLayout() {
        setContentView(R.layout.activity_more_about_machine);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected void initView() {
        mRelativeBack = (RelativeLayout) findViewById(R.id.relative_back);
        mShowTitle = (TextView) findViewById(R.id.textShowTitle);
        mLinearTabs = (LinearLayout) findViewById(R.id.linearTabs);
        mTxtMachineInfoLine = (TextView) findViewById(R.id.text_machineInfoLine);
        mTxtFillBoxOrderLine = (TextView) findViewById(R.id.text_fillBoxLine);
        mRelativeMachineInfo = (RelativeLayout) findViewById(R.id.relative_MachineInfoTab);
        mRelativeFillBoxOrder = (RelativeLayout) findViewById(R.id.relative_FillBoxTab);
        mTxtMachineInfo = (TextView) findViewById(R.id.text_MachineInfo);
        mImgMachineInfo = (ImageView) findViewById(R.id.img_machineInfo);
        mTxtFillBoxOrder = (TextView) findViewById(R.id.text_fillBoxOrder);
        mImgBoxOrder = (ImageView) findViewById(R.id.img_orderBox);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_machineMoreInfo);
        mFrameContainer = (FrameLayout) findViewById(R.id.frameContainer);
    }

    protected void initData() {
        try {
            productSn = getIntent().getStringExtra(PRODUCT_SN);
            showType = getIntent().getStringExtra(SHOW_TYPE);
        } catch (Exception e) {
            productSn = "";
            showType = TYPE_BOTH;
        }
        if (showType.equals(TYPE_BOTH)) {//正常的更多主机信息页面
            fragments = new ArrayList<>();
            machineMoreFragment = MachineMoreFragment.newInstance(productSn, false);
            orderBoxFragment = OrderBoxFragment.newInstance(productSn, false);
            fragments.add(machineMoreFragment);
            fragments.add(orderBoxFragment);
            partsAdapter = new OrderFragmentAdapter(getSupportFragmentManager(), fragments);
            mViewPager.setAdapter(partsAdapter);

        } else if (showType.equals(TYPE_MACHINE)) {//主机信息
            mFrameContainer.setVisibility(View.VISIBLE);
            mShowTitle.setText(getString(R.string.text_hostmachine_info));
            mViewPager.setVisibility(View.GONE);
            mLinearTabs.setVisibility(View.GONE);
            machineMoreFragment = MachineMoreFragment.newInstance("", true);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().add(R.id.frameContainer, machineMoreFragment).commitAllowingStateLoss();
        } else if (showType.equals(TYPE_FILLBOX)) {//装箱单信息
            mFrameContainer.setVisibility(View.VISIBLE);
            mShowTitle.setText(getString(R.string.text_fillbox_order));
            mViewPager.setVisibility(View.GONE);
            mLinearTabs.setVisibility(View.GONE);
            orderBoxFragment = OrderBoxFragment.newInstance("", true);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().add(R.id.frameContainer, orderBoxFragment).commitAllowingStateLoss();
        }
    }

    protected void setClickLintenner() {
        mRelativeBack.setOnClickListener(noDoubleClickLinstenner);
        //设置监听
        mRelativeMachineInfo.setOnClickListener(noDoubleClickLinstenner);
        mRelativeFillBoxOrder.setOnClickListener(noDoubleClickLinstenner);
        mViewPager.setOnPageChangeListener(this);
    }

    public static void openMoreAboutMachineAct(Activity context, String sn, String type) {
        Intent intentMachine = new Intent(context, MoreAboutMachineActivity.class);
        intentMachine.putExtra(PRODUCT_SN, sn);
        intentMachine.putExtra(SHOW_TYPE, type);
        context.startActivity(intentMachine);
    }

    private NoDoubleClickLinstenner noDoubleClickLinstenner = new NoDoubleClickLinstenner() {
        @Override
        public void doClick(View view) {
            switch (view.getId()) {
                case R.id.relative_MachineInfoTab:
                    changeUIStatus(TAB_MACHINEINFO);
                    //viewpager 切换到未使用标签
                    mViewPager.setCurrentItem(TAB_MACHINEINFO);
                    break;
                case R.id.relative_FillBoxTab:
                    changeUIStatus(TAB_FILLBOX);
                    //viewpager切换到已使用标签
                    mViewPager.setCurrentItem(TAB_FILLBOX);
                    break;
                case R.id.relative_back:
                    finish();
                    break;
            }
        }
    };

    /**
     * 切换状态
     */
    private void changeUIStatus(int position) {
        switch (position) {
            case TAB_MACHINEINFO:
                mTxtMachineInfoLine.setBackgroundColor(getResources().getColor(R.color.red_E47B78));
                mTxtMachineInfo.setTextColor(getResources().getColor(R.color.red_E47B78));
                mImgMachineInfo.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_machineinfo_selected));

                mTxtFillBoxOrderLine.setBackgroundColor(getResources().getColor(R.color.gray_F8F8F8));
                mTxtFillBoxOrder.setTextColor(getResources().getColor(R.color.gray_cccccc));
                mImgBoxOrder.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_boxorder_normal));
                break;
            case TAB_FILLBOX:
                mTxtMachineInfoLine.setBackgroundColor(getResources().getColor(R.color.gray_F8F8F8));
                mTxtMachineInfo.setTextColor(getResources().getColor(R.color.gray_cccccc));
                mImgMachineInfo.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_machineinfo_normal));

                mTxtFillBoxOrderLine.setBackgroundColor(getResources().getColor(R.color.red_E47B78));
                mTxtFillBoxOrder.setTextColor(getResources().getColor(R.color.red_E47B78));
                mImgBoxOrder.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_boxorder_selected));
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        changeUIStatus(position);
        if (position == TAB_FILLBOX) {
            orderBoxFragment.setSelected(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {//成功获取二维码
            String sn = data.getExtras().getString("result");
            switch (requestCode) {
                case REQUEST_MACHINE://主机信息
                    if (!TextUtils.isEmpty(sn) && machineMoreFragment != null) {
                        machineMoreFragment.getMachineInfo(sn);
                    }
                    break;
                case REQUEST_FILLBOX://装箱单
                    if (!TextUtils.isEmpty(sn) && orderBoxFragment != null) {
                        orderBoxFragment.getOrderBoxInfo(sn);
                    }
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            switch (requestCode){
                case REQUEST_MACHINECAMARA_ALLOW:
                    if(machineMoreFragment != null && machineMoreFragment.isResumed()){
                        machineMoreFragment.openCamera();
                    }
                    break;
                case REQUEST_FILLBOXCAMARA_ALLOW:
                    if(orderBoxFragment != null && orderBoxFragment.isResumed()){
                        orderBoxFragment.openCamera();
                    }
                    break;
            }
        }
    }
}
