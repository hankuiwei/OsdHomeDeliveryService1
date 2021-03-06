package com.lenovo.csd.eservice.fragment;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.adapter.OrderFragmentAdapter;
import com.lenovo.csd.eservice.util.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.lenovo.csd.eservice.R.drawable.shape_0dpcorner_white_bg;

/**
 * 工单tab页面
 * Created by shengtao
 * on 2016/7/26
 * 23:48
 */
public class WorkOrderFragment extends BaseFragment implements View.OnClickListener, OrderFinishFragment.FinishResult {

    private final String TAG = getClass().getSimpleName();

    private OrderFragmentAdapter mAdapter;
    private List<Fragment> mFragmnets;
    private View view;
    private View mOrdersUnfinishLine;
    private TextView mOrdersFinishLine;
    private LinearLayout mOrdersUnfinishBg;
    private LinearLayout mOrderFinishBg;
    private ViewPager mpager;
    private TextView mTextUnfinish;
    private TextView mTextfinish;
    private ImageView mimgOrderUnF;
    private ImageView mimgOrderF;

    private OrderUnFinishFragment mUnFinishFragment;
    private OrderFinishFragment mFinishFragment;
    private boolean unResult;
    private boolean fiResult;
    private boolean isFirstFlag;

    public static int TAB_UNFINISH = 0;
    public static int TAB_FINISH = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_work_order, null);
        mOrdersUnfinishLine = view.findViewById(R.id.orders_unfinished_linear);
        mOrdersFinishLine = (TextView) view.findViewById(R.id.orders_finished_linear);
        mOrdersUnfinishBg = (LinearLayout) view.findViewById(R.id.orders_unfinished_bg);
        mOrderFinishBg = (LinearLayout) view.findViewById(R.id.orders_finished_bg);
        mTextfinish = (TextView) view.findViewById(R.id.Txt_orders_finished);
        mTextUnfinish = (TextView) view.findViewById(R.id.Txt_orders_unfinished);
        mimgOrderUnF = (ImageView) view.findViewById(R.id.img_orderUnF);
        mimgOrderF = (ImageView) view.findViewById(R.id.img_orderF);
        mOrdersUnfinishBg.setOnClickListener(this);
        mOrderFinishBg.setOnClickListener(this);

        return view;
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mFragmnets = new ArrayList<Fragment>();
        mUnFinishFragment = new OrderUnFinishFragment();
        mFinishFragment = new OrderFinishFragment();
        mFinishFragment.setFinishResult(this);
        mFragmnets.add(mUnFinishFragment);
        mFragmnets.add(mFinishFragment);

        mpager = (ViewPager) view.findViewById(R.id.Order_Pager);
        mAdapter = new OrderFragmentAdapter(getChildFragmentManager(), mFragmnets);
        mpager.setAdapter(mAdapter);

        mpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == TAB_UNFINISH) {
                    mUnFinishFragment.setSelfSelect(true);
                    mFinishFragment.setSelfSelect(false);
                } else if (position == TAB_FINISH) {
                    mUnFinishFragment.setSelfSelect(false);
                    mFinishFragment.setSelfSelect(true);
                }


                Drawable img_f, img_unf;
                Resources res = getResources();
                if (position == 0) {
                    //mOrdersUnfinishBg.setBackgroundColor(getResources().getColor(R.color.white));
                    mOrdersUnfinishLine.setBackgroundColor(getResources().getColor(R.color.red_E47B78));
                    mTextUnfinish.setTextColor(getResources().getColor(R.color.red_E47B78));
                   /* img_unf = res.getDrawable(R.drawable.icon_waiting_selected);
                    img_unf.setBounds(0, 0, img_unf.getMinimumWidth(), img_unf.getMinimumHeight());
                    mTextUnfinish.setCompoundDrawables(img_unf, null, null, null);*/
                    Utils.setBackground(mimgOrderUnF,getResources().getDrawable(R.drawable.icon_waiting_selected));

                    //mOrderFinishBg.setBackgroundResource(R.drawable.shape_0dpcorner_gray_bg);
                    mOrdersFinishLine.setBackgroundColor(getResources().getColor(R.color.gray_F8F8F8));
                    mTextfinish.setTextColor(getResources().getColor(R.color.gray_cccccc));
                   /* img_f = res.getDrawable(R.drawable.icon_success_unselected);
                    img_f.setBounds(0, 0, img_f.getMinimumWidth(), img_f.getMinimumHeight());
                    mTextfinish.setCompoundDrawables(img_f, null, null, null);*/
                    Utils.setBackground(mimgOrderF,getResources().getDrawable(R.drawable.icon_success_unselected));
                } else {
                    //mOrderFinishBg.setBackgroundColor(getResources().getColor(R.color.white));
                    mOrdersFinishLine.setBackgroundColor(getResources().getColor(R.color.red_E47B78));
                    mTextfinish.setTextColor(getResources().getColor(R.color.red_E47B78));
                    /*img_f = res.getDrawable(R.drawable.icon_success_selected);
                    img_f.setBounds(0, 0, img_f.getMinimumWidth(), img_f.getMinimumHeight());
                    mTextfinish.setCompoundDrawables(img_f, null, null, null);*/
                    Utils.setBackground(mimgOrderF,getResources().getDrawable(R.drawable.icon_success_selected));

                    //mOrdersUnfinishBg.setBackgroundResource(R.drawable.shape_0dpcorner_gray_bg);
                    mOrdersUnfinishLine.setBackgroundColor(getResources().getColor(R.color.gray_F8F8F8));
                    mTextUnfinish.setTextColor(getResources().getColor(R.color.gray_cccccc));
                    /*img_unf = res.getDrawable(R.drawable.icon_waiting_unselected);
                    img_unf.setBounds(0, 0, img_unf.getMinimumWidth(), img_unf.getMinimumHeight());
                    mTextUnfinish.setCompoundDrawables(img_unf,null,null,null);*/
                    Utils.setBackground(mimgOrderUnF,getResources().getDrawable(R.drawable.icon_waiting_unselected));
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

        if (this.isHidden()) {
            return;
        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * tab切换要用，预留
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

            if (!isFirstFlag) {
                isFirstFlag = true;
                // Utils.showLoadingDialog(getContext());

            }
            // Utils.showLoadingDialog(getContext());
            int netState = Utils.checkInternetStatus(getContext());
            if (netState == 0) {
                dismissLoadingDialog();
                //Toast.makeText(getContext(),"网络不可用,请连接网络后重试",Toast.LENGTH_SHORT).show();
            }


            if (unResult && fiResult) {
                dismissLoadingDialog();
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.orders_unfinished_bg) {
            mpager.setCurrentItem(0);
        } else {
            mpager.setCurrentItem(1);
        }
    }

    @Override
    public void isResult(boolean flag) {
        fiResult = flag;
    }



    @Override
    public void setSelected(boolean selected) {
        if(mUnFinishFragment != null)
        mUnFinishFragment.setParentSelect(selected);
        if(mFinishFragment != null)
        mFinishFragment.setParentSelect(selected);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
