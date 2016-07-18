package com.example.administrator.xmpp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.administrator.xmpp.config.Constant;
import com.example.administrator.xmpp.dbhelper.ContactOpenHelper;
import com.example.administrator.xmpp.dbhelper.SmsOpenHelper;

/**
 * 操作消息数据库的一些操作
 * Created by Administrator on 2016/4/20.
 */
public class SmsProvider extends ContentProvider {
    public final static Uri SMS_PROVIDER_URI=Uri.parse("content://com.example.administrator.xmpp.provider.smsProvider/sms");
    public final static String SMS_AUTHORITY="com.example.administrator.xmpp.provider.smsProvider";
    public final  static int SMS=1;
    public SmsOpenHelper m_helper;
    static UriMatcher m_uriMatcher;
    static {
        m_uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        //添加匹配规则
        m_uriMatcher.addURI(SMS_AUTHORITY,"sms",SMS);
    }
    @Override
    public boolean onCreate() {
        //创建数据库和表
        m_helper=new SmsOpenHelper(getContext());
        if(m_helper!=null){
            return true;//创建成功
        }
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor=null;
        switch(m_uriMatcher.match(uri)){
            case SMS:
                cursor=m_helper.getReadableDatabase().query(Constant.TABLE_SMS,projection,selection,selectionArgs,null,null,null);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch(m_uriMatcher.match(uri)){
            case SMS:
                //插入之后对应的具体的id，每次只能插入一条数据
                long id=m_helper.getWritableDatabase().insert(Constant.TABLE_SMS,null,values);
                if(id>0){
                    System.out.println("-----------消息插入数据库成功-------------");

                    //通知ConentObserver数据发生了变化，在ChatActivity中已经注册了一个ContentObserver
                    getContext().getContentResolver().notifyChange(SMS_PROVIDER_URI,null);
                    uri= ContentUris.withAppendedId(uri,id);
                }
                break;
            default:
                break;
        }
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleteCount=0;
        switch(m_uriMatcher.match(uri)){
            case SMS:
                //具体删除了几条数据
                deleteCount=m_helper.getWritableDatabase().delete(Constant.TABLE_SMS,selection,selectionArgs);
                if(deleteCount>0){
                    System.out.println("---------------消息从数据库删除成功---------------");
                }
                break;
            default:
                break;
        }
        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int updateCount=0;
        switch(m_uriMatcher.match(uri)){
            case SMS:
                updateCount=m_helper.getWritableDatabase().update(Constant.TABLE_SMS,values,selection,selectionArgs);
                if(updateCount>0){
                    System.out.println("-----------消息数据库更新成功------------");
                }
                break;
            default:
                break;
        }
        return updateCount;
    }
}
