package com.denglibin.propertyanimation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.denglibin.propertyanimation.drawableanim.ShowDrawableAnim;
import com.denglibin.propertyanimation.drawableanim.ShowExample;
import com.denglibin.propertyanimation.drawableanim.TestInterpolator;
import com.denglibin.propertyanimation.property.DingZhi;
import com.denglibin.propertyanimation.property.LayoutAnimaActivity;
import com.denglibin.propertyanimation.property.ShowProperty;
import com.denglibin.propertyanimation.property.ViewAnimateActivity;
import com.denglibin.propertyanimation.tween.AllTween;
import com.denglibin.propertyanimation.tween.ShowMain;
import com.denglibin.propertyanimation.tween.ShowTween;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        findViewById(R.id.showmain).setOnClickListener(this);
        findViewById(R.id.showtween).setOnClickListener(this);
        findViewById(R.id.alltween).setOnClickListener(this);
        findViewById(R.id.showdrawableanim).setOnClickListener(this);
        findViewById(R.id.interpolatershow).setOnClickListener(this);
        findViewById(R.id.tweendrawableanim).setOnClickListener(this);
        findViewById(R.id.propertyanim).setOnClickListener(this);
        findViewById(R.id.propertydef).setOnClickListener(this);
        findViewById(R.id.viewproperty).setOnClickListener(this);
        findViewById(R.id.layoutanim).setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.showmain:
                //TODO implement
                startActivity(new Intent(MainActivity.this, ShowMain.class));
                break;

            case R.id.showtween:
                //TODO implement
                startActivity(new Intent(MainActivity.this, ShowTween.class));
                break;
            case R.id.alltween:
                //TODO implement
                startActivity(new Intent(MainActivity.this, AllTween.class));
                break;
            case R.id.showdrawableanim:
                startActivity(new Intent(MainActivity.this, ShowDrawableAnim.class));
                break;
            case R.id.interpolatershow:
                startActivity(new Intent(MainActivity.this, TestInterpolator.class));
                break;
            case R.id.tweendrawableanim:
                startActivity(new Intent(MainActivity.this, ShowExample.class));
                break;
            case R.id.propertyanim:
                startActivity(new Intent(MainActivity.this, ShowProperty.class));
                break;
            case R.id.propertydef:
                startActivity(new Intent(MainActivity.this, DingZhi.class));
                break;
            case R.id.viewproperty:
                startActivity(new Intent(MainActivity.this, ViewAnimateActivity.class));
                break;
            case R.id.layoutanim:
                startActivity(new Intent(MainActivity.this, LayoutAnimaActivity.class));
                break;

        }
    }
}
