package com.denglibin.myapp.adapter;

import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.denglibin.myapp.R;
import com.denglibin.myapp.domain.Music;
import com.denglibin.myapp.fragment.MusicFragment;
import com.denglibin.myapp.utils.MediaUtils;
import com.denglibin.myapp.utils.UiUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/4/30.
 */
public class MusicPagerAdapter extends PagerAdapter {
    private List<View> views;
    private ListView lv_songs;

    public MusicPagerAdapter(List<View> views){
        this.views=views;
    }
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
       if(views!=null&&views.size()!=0){
           View view=views.get(position);
           switch (position){
               case 0:
                   break;
               /**----------------*/
               case 1:
                   lv_songs = (ListView) view.findViewById(R.id.lv_songs);
                   lv_songs.setAdapter(new SongListAdapter());
                   break;
               case 2:
                   break;
               default:
                   break;
           }
           container.addView(view);
           return view;
       }else{
           System.out.println("music pager 的view集合为空或没有元素");
       }
        return null;
    }

    public ListView getSongsListView(){
        return lv_songs;
    }
}
