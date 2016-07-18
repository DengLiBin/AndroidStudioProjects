package com.example.administrator.xmpp.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.administrator.xmpp.config.Constant;

/**
 * Created by Administrator on 2016/4/20.
 */
public class SmsOpenHelper extends SQLiteOpenHelper {
    public SmsOpenHelper(Context context){
        super(context,"sms.db", null,1);
        System.out.println("消息数据库创建成功");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + Constant.TABLE_SMS + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                Constant.SmsTable.FROM_ACCOUNT + " TEXT," +
                Constant.SmsTable.TO_ACCOUNT + " TEXT," +
                Constant.SmsTable.BODY + " TEXT," +
                Constant.SmsTable.TYPE + " TEXT," +
                Constant.SmsTable.TIME + " TEXT," +
                Constant.SmsTable.SESSION_ACCOUNT + " TEXT);";
        System.out.println("消息表创建");
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
