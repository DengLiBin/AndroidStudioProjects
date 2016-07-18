package com.denglibin.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DengLibin on 2016/3/31 0031.
 */
class MyAdapter extends RecyclerView.Adapter {
    private MainActivity mainActivity;
    //private String[] strings;
    private List<CellData> lists;
    public MyAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        //strings=mainActivity.getResources().getStringArray(R.array.libai);//初始化字符串数组
        lists=new ArrayList<CellData>();
        addDataToList();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //返回一个ViewHolder,ViewHolder里面加载了一个布局
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cell,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;//强转成自己的ViewHolder
        vh.getTv_title().setText(lists.get(position).getTitle());
        vh.getTv_text().setText(lists.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return lists.size();//item个数
    }
    private class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_title,tv_text;
        private View root;
        public ViewHolder(View root) {
            super(root);
            this.root=root;
            tv_title= (TextView) root.findViewById(R.id.tv_title);
            tv_text= (TextView) root.findViewById(R.id.tv_text);
        }

        public TextView getTv_title() {
            return tv_title;
        }

        public TextView getTv_text() {
            return tv_text;
        }
    }

    public void addDataToList(){
        for(int i=0;i<100;i++){
            CellData data=new CellData("标题"+i,"文本"+i);
            lists.add(data);
        }
    }
}
