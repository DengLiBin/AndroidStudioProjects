package com.denglibin.administrator.viewpager.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.denglibin.administrator.viewpager.MainActivity;
import com.denglibin.administrator.viewpager.R;
import com.denglibin.administrator.viewpager.domain.Ad;

import java.util.List;

/**
 * Created by Administrator on 2016/4/22.
 */
public class MainPagerAdapter  extends PagerAdapter {
    private List<Ad> list;
    MainActivity  mainActivity;
    public MainPagerAdapter(MainActivity mainActivity,List<Ad> list){
        this.mainActivity=mainActivity;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size()+1000;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    /**
     * 销毁page
     * position： 当前需要消耗第几个page
     * object:当前需要消耗的page
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
    /**
     * 类似于BaseAdapger的getView方法
     * 用了将数据设置给view
     * 由于它最多就3个界面，不需要viewHolder
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //return super.instantiateItem(container, position);
        View view=View.inflate(mainActivity, R.layout.content_main_viewpager,null);
        ImageView imageView= (ImageView) view.findViewById(R.id.iv_head);
        imageView.setImageResource(list.get(position%(list.size())).getImage_id());
        container.addView(view);//不能少
        return view;
    }
}
