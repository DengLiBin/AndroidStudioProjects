package com.denglibin.myapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denglibin.myapp.R;
import com.denglibin.myapp.utils.UiUtils;
import com.denglibin.myapp.view.WatchView;

/**
 * Created by Administrator on 2016/5/1.
 */
public class WatchFragment extends Fragment{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(UiUtils.getContext()).inflate(R.layout.fragment_watch,null);
        WatchView wv= (WatchView) view.findViewById(R.id.wv);
        wv.start();
        return view;
    }
}
