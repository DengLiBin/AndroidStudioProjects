package com.denglibin.readvalidatecode;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DengLibin on 2016/4/1 0001.
 */
public class SmsObserver extends ContentObserver {

    private Context mContext;
    private Handler mHandler;
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public SmsObserver(Context context,Handler handler) {
        super(handler);
        this.mContext=context;
        this.mHandler=handler;
    }

    /**
     * 必须复写该方法
     * @param selfChange
     * @param uri
     */
    @Override
    public void onChange(boolean selfChange,Uri uri) {
        super.onChange(selfChange, uri);
        System.out.println("短信发生了变化");

        String code="";
        if(uri.toString().equals("content://sms/raw")){
            return;
        }
        Uri inboxUri=Uri.parse("content://sms/inbox");

        Cursor cursor=mContext.getContentResolver().query(inboxUri, null, null, null, "date desc");
        if(cursor!=null){
            if(cursor.moveToFirst()){
                String address=cursor.getString(cursor.getColumnIndex("address"));
                String body=cursor.getString(cursor.getColumnIndex("body"));

                if(!address.equals("15555215556")){//判断该短信的发送者
                    return;
                }
                System.out.println("发件人为：" + address + " " + "内容为：" + body + ";");

                Pattern pattern=Pattern.compile("(\\d{6})");//正则表达式，连续6个数字的字符串
                Matcher matcher=pattern.matcher(body);//提取连续6个数字的字符串

                if(matcher.find()){
                    code=matcher.group(0);
                    System.out.println("验证码为："+code);

                    //发送消息，MainActivity.MSG_RECEIVED_CODE————msg.what
                    //code————msg.obj
                    mHandler.obtainMessage(MainActivity.MSG_RECEIVED_CODE, code).sendToTarget();
                }
            }
            cursor.close();
        }
    }
}
