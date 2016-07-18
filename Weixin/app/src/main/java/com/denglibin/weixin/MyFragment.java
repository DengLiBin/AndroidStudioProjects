package com.denglibin.weixin;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by DengLibin on 2016/3/11 0011.
 */
public class MyFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        TextView textView=new TextView(getActivity());
        textView.setText("MyFragment");
        return  textView;
    }
}
