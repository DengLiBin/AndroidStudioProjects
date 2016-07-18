package com.denglibin.switchtoggle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.denglibin.switchtoggle.view.SwitchToggleView;

public class MainActivity extends AppCompatActivity {
    private SwitchToggleView m_stv_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        m_stv_main= (SwitchToggleView) findViewById(R.id.stv_main);
        m_stv_main.setSwitchBackground(R.mipmap.switch_background);
        m_stv_main.setSwitchSlide(R.mipmap.slide_button_background);

    }

}
