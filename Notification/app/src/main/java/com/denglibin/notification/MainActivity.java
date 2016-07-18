package com.denglibin.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final int NOTIFICATIONID=1200;
    public static final  int REQUESTCODE=1200;
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

        Button button= (Button) findViewById(R.id.btn_notify);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //创建Intent，通过该intent启动其他activity，并可以传递参数
                Intent notificationIntent =new Intent(MainActivity.this, OtherActivity.class);
                notificationIntent.putExtra("date","需要传递的参数");

                // FLAG_UPDATE_CURRENT 更新数据，如果有多个PendingIntent，且requestCode相同，则会替换为最新extra数据
                //如果需要通过不同的extra数据，进行处理，就需要requestCode不相同
                PendingIntent contentItent = PendingIntent.getActivity(MainActivity.this, REQUESTCODE, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder=new NotificationCompat.Builder(MainActivity.this);
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setContentTitle("哇哦，有一个新消息");
                builder.setContentText("你已经可以创建新的Notification了");
                builder.addAction(R.mipmap.ic_launcher,"点击进入",contentItent);
                Notification notification=builder.build();
                NotificationManager manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(NOTIFICATIONID,notification);
            }
        });
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
}
