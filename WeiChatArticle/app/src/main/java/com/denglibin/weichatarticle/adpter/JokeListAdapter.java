package com.denglibin.weichatarticle.adpter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.denglibin.weichatarticle.MainActivity;
import com.denglibin.weichatarticle.R;
import com.denglibin.weichatarticle.domain.Joke;

import java.util.List;

/**
 * Created by DengLibin on 2016/5/7.
 */
public class JokeListAdapter extends BaseAdapter {
    private List<Joke> list;
    private MainActivity mainActivity;
    public JokeListAdapter(List<Joke> jokes, MainActivity mainActivity){
        this.list=jokes;
        this.mainActivity=mainActivity;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Joke getItem(int position) {
        return list.get(position);
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
            convertView=View.inflate(mainActivity, R.layout.item_joke,null);
            holder.contentJoke= (TextView) convertView.findViewById(R.id.tv_joke);
            holder.updateTime= (TextView) convertView.findViewById(R.id.tv_uptime);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.contentJoke.setText(getItem(position).contentJok);
        holder.updateTime.setText(getItem(position).uptime);
        return convertView;
    }
    private static class ViewHolder{
        public TextView contentJoke,updateTime;
    }
}
