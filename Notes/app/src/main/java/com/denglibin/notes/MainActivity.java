package com.denglibin.notes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.denglibin.notes.db.Config;
import com.denglibin.notes.db.NotesDb;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private NotesDb notesDb;
    private SQLiteDatabase dbWriter;
    private  FloatingActionButton btn_text,btn_video,btn_pic;
    private ListView lv;
    private Intent intent;
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

        notesDb=new NotesDb(this);
        dbWriter=notesDb.getWritableDatabase();
        addDB();
        init();
    }

    public void init(){
        btn_pic= (FloatingActionButton) findViewById(R.id.btn_addPicture);
        btn_video= (FloatingActionButton) findViewById(R.id.btn_addVideo);
        btn_text= (FloatingActionButton) findViewById(R.id.btn_addtext);
        lv= (ListView) findViewById(R.id.lv_content);
        intent=new Intent(MainActivity.this,AddConentActivity.class);
        btn_text.setOnClickListener(this);
        btn_video.setOnClickListener(this);
        btn_pic.setOnClickListener(this);
    }
    /**
     * 向数据库添加数据
     */
    public void addDB(){
        ContentValues cv=new ContentValues();
        cv.put(Config.CONTENT,"hello");
        cv.put(Config.TIME,getTime());
        dbWriter.insertOrThrow(Config.TABLE_NAME,null,cv);
    }
    //获取时间
    public String getTime(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date=new Date();
        String str=format.format(date);
        return str;
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_addtext:
                intent.putExtra("flag","add_text");
                startActivity(intent);
                break;
            case R.id.btn_addPicture:
                intent.putExtra("flag","add_pic");
                startActivity(intent);
                break;
            case R.id.btn_addVideo:
                intent.putExtra("flag","add_video");
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
