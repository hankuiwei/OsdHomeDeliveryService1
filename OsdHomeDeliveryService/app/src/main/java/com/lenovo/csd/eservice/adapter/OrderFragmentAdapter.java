package com.lenovo.csd.eservice.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by hankw on 16-8-1.
 */
public class OrderFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mfragments;

    public OrderFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mfragments.get(position);
    }

    public OrderFragmentAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        mfragments = list;
        //notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mfragments.size();
    }
}
