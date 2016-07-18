package com.denglibin.myapp.fragment;

import android.content.BroadcastReceiver;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.denglibin.myapp.R;
import com.denglibin.myapp.utils.ParseJson;
import com.denglibin.myapp.utils.UiUtils;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

/**
 * Created by Administrator on 2016/5/1.
 */
public class WeatherFragment extends Fragment {
    private RelativeLayout rlCity;
    private PullToRefreshScrollView scrollView;
    private String CITY_NAME="成都";
    private String WEATHER_KEY="784b5fd75d7eb8ab68078ec8f5f5c5cd";
    private String AIR_KEY="0a4fb80e1835c11c96874073f56409a6";
    BroadcastReceiver receiver;
    SharedPreferences preferences;
    ParseJson parse;
    boolean flag=true;
    private int userIconId;
    private View headerView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(UiUtils.getContext()).inflate(R.layout.fragment_weather,null);
        return view;
    }
}
