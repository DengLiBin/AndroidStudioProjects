package com.denglibin.weichatarticle.adpter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.denglibin.weichatarticle.ArticleActivity;
import com.denglibin.weichatarticle.MainActivity;
import com.denglibin.weichatarticle.R;
import com.denglibin.weichatarticle.domain.Article;
import com.denglibin.weichatarticle.image.SmartImageView;

import java.util.List;


/**
 * Created by DengLibin on 2016/5/4 0004.
 */
public class RvArticleAdapter extends RecyclerView.Adapter {
    private List<Article> list;
    private MainActivity context;
    public RvArticleAdapter(List<Article> list){
        this.list=list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=(MainActivity) parent.getContext();
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_article,null);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myHodler=(MyViewHolder)holder;
        myHodler.iv_firsImage.setImageUrl(list.get(position).firstImg);
        myHodler.tv_title.setText(list.get(position).title);
        myHodler.tv_sources.setText(list.get(position).sources);
        myHodler.linearLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ArticleActivity.class);
                intent.putExtra("url",list.get(position).url);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        View root;
        SmartImageView iv_firsImage;
        TextView tv_title;
        TextView tv_sources;
        LinearLayout linearLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            root=itemView;
            iv_firsImage= (SmartImageView) root.findViewById(R.id.iv_firstImg);
            tv_sources= (TextView) root.findViewById(R.id.tv_sources);
            tv_title= (TextView) root.findViewById(R.id.tv_title);
            linearLayout= (LinearLayout) root.findViewById(R.id.line_lay_item);
        }
    }
}
