package com.denglibin.weichatarticle.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.denglibin.weichatarticle.MainActivity;
import com.denglibin.weichatarticle.R;
import com.denglibin.weichatarticle.adpter.RvArticleAdapter;
import com.denglibin.weichatarticle.common.Constant;
import com.denglibin.weichatarticle.domain.Article;
import com.denglibin.weichatarticle.utils.UiUtils;
import com.denglibin.weichatarticle.view.PullToRefreshView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DengLibin on 2016/5/6 0006.
 */
public class WeiFragment extends Fragment{
    private ListView mListView;
    private String text;
    private ImageView detail_loding;
    private TextView textView;
    private MainActivity mainActivity;
    private List<Article> articles;
    public static final int SET_NEWSLIST=1;
    private RecyclerView recyclerView;
    private List<Article> list_article=new ArrayList<Article>();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            System.out.println("收到消息，更新数据");
            initData((List<Article>)msg.obj);
            super.handleMessage(msg);
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mainActivity= (MainActivity) getActivity();
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.content_wei,null);
        initView(view);
        getDataByVolley(Constant.JSON_URL);
        return view;
    }

    private void initView(View root){
        recyclerView = (RecyclerView) root.findViewById(R.id.rv_article);
        final PullToRefreshView pullToRefreshView= (PullToRefreshView) root.findViewById(R.id.pull_to_refresh);
        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getDataByVolley(Constant.JSON_URL);
                        pullToRefreshView.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    private void getDataByVolley(String json_url){
        RequestQueue requestQueue= Volley.newRequestQueue(UiUtils.getContext());
        String jsonUrl=json_url;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, jsonUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //System.out.println("response:" + response.toString());
                        String result=response.toString();
                        System.out.println("json数据:"+result);
                        list_article.clear();//清空刷新前的数据
                        parseJson(result);
                    }
                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("对不起，有问题");
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    private void parseJson(String json){
        try {
            JSONObject obj=new JSONObject(json);
            JSONObject result=obj.getJSONObject("result");
            JSONArray list=result.getJSONArray("list");
            for(int i=0;i<list.length();i++){
                JSONObject article=list.getJSONObject(i);
                String firstImg=article.getString("firstImg");
                String id=article.getString("id");
                String sources=article.getString("source");
                String title=article.getString("title");
                String url=article.getString("url");
                list_article.add(new Article(firstImg,id,sources,title,url));
            }
            Message msg=Message.obtain();
            msg.obj=list_article;
            handler.sendMessage(msg);
            System.out.println("解析数据完成:"+list_article);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void initData(List<Article> list){

        if(list!=null&&list.size()!=0){
            RvArticleAdapter adapter=new RvArticleAdapter(list);
            recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL,false));//竖向不反转
            recyclerView.setAdapter(adapter);
        }else{
            System.out.println("集合為空");
        }

    }
}
