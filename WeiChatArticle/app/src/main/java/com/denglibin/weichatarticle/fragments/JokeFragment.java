package com.denglibin.weichatarticle.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.denglibin.weichatarticle.MainActivity;
import com.denglibin.weichatarticle.R;
import com.denglibin.weichatarticle.adpter.JokeListAdapter;
import com.denglibin.weichatarticle.domain.Joke;
import com.denglibin.weichatarticle.net.GetJokeJson;
import com.denglibin.weichatarticle.view.PullToRefreshView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DengLibin on 2016/5/7.
 */
public class JokeFragment extends Fragment {
    private MainActivity mainActivity;
    private ListView listView;
    private List<Joke> jokes;
    public  int JOKE_PAGE=1;
    public  String JOKE_JSON_URL="";
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result= (String) msg.obj;
            parseJson(result);//解析数据
            listView.setAdapter(new JokeListAdapter(jokes,mainActivity));
            super.handleMessage(msg);
        }
    };
    private SharedPreferences sp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainActivity= (MainActivity)container.getContext();
        sp = mainActivity.getSharedPreferences("config", Context.MODE_APPEND);
        JOKE_PAGE=sp.getInt("page",1);
        JOKE_JSON_URL="http://japi.juhe.cn/joke/content/list.from?key=981961561faa77150a1d8ff0c81f884a&page="+JOKE_PAGE+"&pagesize=15&sort=asc&time=1418745237";
        View view=LayoutInflater.from(mainActivity).inflate(R.layout.fragment_joke,null);
        listView = (ListView) view.findViewById(R.id.lv_joke);
        final PullToRefreshView pullToRefreshView= (PullToRefreshView) view.findViewById(R.id.refresh);
        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("page:"+JOKE_PAGE+""+JOKE_JSON_URL);
                        jokes.clear();
                        JOKE_JSON_URL="http://japi.juhe.cn/joke/content/list.from?key=981961561faa77150a1d8ff0c81f884a&page="+(++JOKE_PAGE)+"&pagesize=15&sort=asc&time=1418745237";
                        initData();
                        pullToRefreshView.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser){
            initData();

        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initData(){
        new GetJokeJson(JOKE_JSON_URL, new GetJokeJson.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                System.out.println("URL:"+JOKE_JSON_URL);
                System.out.println("joke数据:"+result);
                Message msg=Message.obtain();
                msg.obj=result;
                handler.sendMessage(msg);
            }
        }, new GetJokeJson.FailCallback() {
            @Override
            public void onFail() {
                Toast.makeText(mainActivity,"对不起，数据访问失败，请稍后再试！",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseJson(String jokeJson){
        jokes=new ArrayList<Joke>();
        try {
            JSONObject object=new JSONObject(jokeJson);
            JSONObject result=object.getJSONObject("result");
            JSONArray data=result.getJSONArray("data");
            for(int i=0;i<data.length();i++){
                String content=data.getJSONObject(i).getString("content");
                String uptime=data.getJSONObject(i).getString("updatetime");
                jokes.add(new Joke(content,uptime));
            }
            System.out.println("解析joke数据完成："+jokes);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt("page",JOKE_PAGE).commit();
        super.onPause();
    }
}
