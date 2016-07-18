package com.denglibin.myapp.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.denglibin.myapp.R;
import com.denglibin.myapp.domain.Music;
import com.denglibin.myapp.utils.MediaUtils;
import com.denglibin.myapp.utils.UiUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/4/30.
 */
public class SongListAdapter extends BaseAdapter {
    private List<Music> songs=MediaUtils.initSongList(UiUtils.getContext());
    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Music getItem(int position) {
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(UiUtils.getContext(), R.layout.item_songs_list,null);
        /*
            ViewOutlineProvider viewOutlineProvider=new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    //可以指定圆形，矩形，圆角矩形，path
                    outline.setRect(0,0,view.getWidth(),view.getHeight());
                }
            };
            convertView.setOutlineProvider(viewOutlineProvider);
            */
            holder.tv_title= (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_artist= (TextView) convertView.findViewById(R.id.tv_artist);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();//用VeiwHoder不用每次都findViewById(id)
        }
        holder.tv_title.setText(getItem(position).getTitle());
        holder.tv_title.setTag(position);//设置tag，方便反查，相当于给每一item的TextView设置了一个position的标记
        holder.tv_artist.setText(getItem(position).getArtist());
        //因為converView的複用，所以要判斷對新加載的item的顏色進行矯正
        if(MediaUtils.playing_position!=position){
            holder.tv_title.setTextColor(Color.BLACK);
        }else{
            holder.tv_title.setTextColor(UiUtils.getResource().getColor(R.color.colorBar));
        }
        return convertView;
    }

    private static class ViewHolder{
        private TextView tv_title;
        private TextView tv_artist;
    }
}
