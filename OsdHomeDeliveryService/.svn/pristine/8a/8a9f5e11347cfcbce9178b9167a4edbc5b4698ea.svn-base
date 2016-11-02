package com.lenovo.csd.eservice.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.activity.HomeActivity;
import com.lenovo.csd.eservice.widget.HomeTabItem;
import com.lenovo.csd.eservice.widget.HomeTabView;

import java.util.ArrayList;

/**
 * 主页面，主要容纳首页的四个tab fragment
 * Created by shengtao
 * on 2016/7/26
 * 23:48
 */
public class EServiceFragment extends BaseFragment {

    private String[] mTitle = new String[4];
    private int[] mIconNormal = {R.drawable.tab_icon_work_order_normal, R.drawable.tab_icon_spare_part_normal,
            R.drawable.tab_icon_learning_normal, R.drawable.tab_icon_at_me_normal};
    private int[] mIconSelect = {R.drawable.tab_icon_work_order_selected, R.drawable.tab_icon_spare_part_selected,
            R.drawable.tab_icon_learning_selected, R.drawable.tab_icon_at_me_selected};

    private ViewPager mViewPager;

    // 首页上frag的位置
    private static final int WORK_ORDER_FRAG_POS = 0;
    private static final int SPARE_PART_FRAG_POS = 1;
    private static final int LEARNING_FRAG_POS = 2;
    private static final int AT_ME_FRAG_POS = 3;

    private WorkOrderFragment mWorkOrderFragment;
    private SparePartFragment mSparePartFragment;
    private LearningFragment mLearningFragment;
    private AtMeFragment mAtMeFragment;
    private ArrayList<Fragment> mFragmentList;

    // private HomeBottomBar mBottomBar;
    private HomeTabView mBottomBar;
    private HomeTabItem mAtMeTabItem;
    private HomeTabItem mRankTabItem;

    private ViewGroup mViewGroup;

    private HomeActivity mHomeActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mTitle[0] = context.getResources().getString(R.string.string_work_order);
        mTitle[1] = context.getResources().getString(R.string.string_spare_part);
        mTitle[2] = context.getResources().getString(R.string.string_learning);
        mTitle[3] = context.getResources().getString(R.string.string_at_me);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        //new AtMeFragment().getMineCallBack(this);
        super.onCreate(savedInstanceState);

        mFragmentList = new ArrayList<Fragment>();
        mWorkOrderFragment = new WorkOrderFragment();
        mFragmentList.add(mWorkOrderFragment);
        mSparePartFragment = new SparePartFragment();
        mFragmentList.add(mSparePartFragment);
        mLearningFragment = new LearningFragment();
        mFragmentList.add(mLearningFragment);
        mAtMeFragment = new AtMeFragment();
        mFragmentList.add(mAtMeFragment);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (mViewGroup == null) {
            mViewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_eservice, null, false);
            mViewPager = (ViewPager) mViewGroup.findViewById(R.id.pager);
            mViewPager.setOffscreenPageLimit(3);
            mViewPager.setAdapter(new PageAdapter(getChildFragmentManager()));
            mBottomBar = (com.lenovo.csd.eservice.widget.HomeTabView) mViewGroup.findViewById(R.id.bottom_bar);
            mBottomBar.setViewPager(mViewPager);
            mAtMeTabItem = (com.lenovo.csd.eservice.widget.HomeTabItem) mBottomBar.getChildAt(AT_ME_FRAG_POS);
            mRankTabItem = (com.lenovo.csd.eservice.widget.HomeTabItem) mBottomBar.getChildAt(SPARE_PART_FRAG_POS);
            mBottomBar.setOnPageChangeListener(mOnPageChangeListener);
            mBottomBar.setOnItemClickListener(new HomeTabView.OnItemClickListener() {

                @Override
                public void onSingleClick(View v, int itemPos) {
                    mBottomBar.setSelection(itemPos, false);
                }

                @Override
                public void onDoubleClick(View v, int itemPos) {

                }


            });

        }

        return mViewGroup;
    }


    private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
//            if (mWorkOrderFragment == null || mSparePartFragment == null || mLearningFragment == null || mAtMeFragment == null)
//                return;
            if (position == WORK_ORDER_FRAG_POS) {
                mWorkOrderFragment.setSelected(true);
                mSparePartFragment.setSelected(false);
                mLearningFragment.setSelected(false);
                mAtMeFragment.setSelected(false);
            } else if (position == SPARE_PART_FRAG_POS) {
                mWorkOrderFragment.setSelected(false);
                mSparePartFragment.setSelected(true);
                mLearningFragment.setSelected(false);
                mAtMeFragment.setSelected(false);
            } else if (position == LEARNING_FRAG_POS) {
                mWorkOrderFragment.setSelected(false);
                mSparePartFragment.setSelected(false);
                mLearningFragment.setSelected(true);
                mAtMeFragment.setSelected(false);
            } else if (position == AT_ME_FRAG_POS) {
                mWorkOrderFragment.setSelected(false);
                mSparePartFragment.setSelected(false);
                mLearningFragment.setSelected(false);
                mAtMeFragment.setSelected(true);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };


    class PageAdapter extends FragmentStatePagerAdapter implements com.lenovo.csd.eservice.widget.HomeTabView.OnItemIconTextSelectListener {
        public PageAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int arg0) {

            return mFragmentList.get(arg0);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public int[] onIconSelect(int position) {
            int icon[] = new int[2];
            icon[0] = mIconSelect[position];
            icon[1] = mIconNormal[position];
            return icon;
        }

        @Override
        public String onTextSelect(int position) {
            return mTitle[position];
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mWorkOrderFragment.onActivityResult(requestCode, resultCode, data);
        mLearningFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }




	/*public boolean isCurrentHomeFragment() {
        if (mViewPager.getCurrentItem() == HOME_FRAG_POS) {
			return true;
		} else {
			return false;
		}
	}*/


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }


}
