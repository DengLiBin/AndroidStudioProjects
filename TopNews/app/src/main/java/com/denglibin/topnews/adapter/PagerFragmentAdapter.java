package com.denglibin.topnews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/4/26.
 */
public class PagerFragmentAdapter extends FragmentPagerAdapter{
    private List<Fragment> fragments;
    private FragmentManager fm;
    public PagerFragmentAdapter(FragmentManager fm) {
        super(fm);
        this.fm=fm;
    }
    public PagerFragmentAdapter(FragmentManager fm,List<Fragment> fragments){
        super(fm);
        this.fm=fm;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
