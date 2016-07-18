package com.denglibin.designsupport;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by DengLibin on 2016/3/30 0030.
 */
public class TabActivity extends AppCompatActivity {
    private List<String> titles;
    private List<Fragment> fragments;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        TabLayout tabLayout= (TabLayout) findViewById(R.id.tabs);
        titles=new ArrayList<String>();
        fragments=new ArrayList<Fragment>();
        for(int i=0;i<5;i++){
            String title="Tab"+(i+1);
            tabLayout.addTab(tabLayout.newTab().setText(title));
            TabFragment tabFragment=new TabFragment(title);
            titles.add(title);
            fragments.add(tabFragment);
        }

        ViewPager viewPager= (ViewPager) findViewById(R.id.view_pager);
        Adapter mAdpter=new Adapter(getSupportFragmentManager(),titles,fragments);
        viewPager.setAdapter(mAdpter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(mAdpter);
    }

    class Adapter extends FragmentStatePagerAdapter{
        private  List<String> titles;
        private List<Fragment> fragments;
        public Adapter (FragmentManager fm,List<String> titles,List<Fragment> fragments){
            super(fm);
            this.titles=titles;
            this.fragments=fragments;
        }
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
