package com.denglibin.notes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DengLibin on 2016/4/11 0011.
 */
public class NotesDb extends SQLiteOpenHelper {
    public NotesDb(Context context) {
        //创建数据库，指定数据库名称，版本号
        super(context, Config.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建一张表,注意SQL语句的书写，该有空格的地方一定要有空格
        db.execSQL("CREATE TABLE " + Config.TABLE_NAME + "(" + Config.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Config.CONTENT + " TEXT NOT NULL," + Config.TIME + " TEXT NOT NULL," + Config.PICTURES + " TEXT NOT NULL,"
                + Config.VIDEO + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
