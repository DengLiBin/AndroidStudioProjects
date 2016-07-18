package com.denglibin.left_menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.denglibin.left_menu.view.SlidingMenu;

public class MainActivity extends AppCompatActivity {
    private SlidingMenu sm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm= (SlidingMenu) findViewById(R.id.sm);
    }
    public void clickTitle(View view){
        String text =((TextView)view).getText().toString();
        Toast.makeText(this,"点击了"+text,Toast.LENGTH_SHORT).show();
        sm.switchMenu(false);
    }
}
