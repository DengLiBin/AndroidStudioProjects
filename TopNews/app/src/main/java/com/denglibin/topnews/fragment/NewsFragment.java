package com.denglibin.topnews.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.denglibin.topnews.Config.Constant;
import com.denglibin.topnews.MainActivity;
import com.denglibin.topnews.R;
import com.denglibin.topnews.adapter.ListNewsAdapter;
import com.denglibin.topnews.bean.News;

import java.util.List;

/**
 * Created by Administrator on 2016/4/26.
 */
public class NewsFragment extends Fragment {
    private ListView mListView;
    private String text;
    private ImageView detail_loding;
    private TextView textView;
    private MainActivity mainActivity;
    private List<News> news;
    public static final int SET_NEWSLIST=1;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SET_NEWSLIST:
                    detail_loding.setVisibility(View.GONE);
                    mListView.setAdapter(new ListNewsAdapter(mainActivity,news));
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args=getArguments();
        text=(args!=null?args.getString("text"):"");
        mainActivity= (MainActivity) getActivity();
        initData();
        super.onCreate(savedInstanceState);
    }

    /**
     * 此方法意思为fragment是否可见 ,可见时候加载数据
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser){
            if(news!=null&&news.size()!=0){
                handler.obtainMessage(SET_NEWSLIST).sendToTarget();
            }else{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(2000);
                        }catch (Exception e){

                        }
                        handler.obtainMessage(SET_NEWSLIST).sendToTarget();
                    }
                }).start();
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_news,null);
        mListView= (ListView) view.findViewById(R.id.lv_news);
        textView= (TextView) view.findViewById(R.id.item_text);
        detail_loding= (ImageView) view.findViewById(R.id.iv_loading);

        return view;
    }

    private void initData(){
        news= Constant.getNewsList();
    }
}
