package com.example.administrator.xmpp.service;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.administrator.xmpp.config.Constant;
import com.example.administrator.xmpp.utils.PinyinUtils;
import com.example.administrator.xmpp.utils.ThreadUtils;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;

import java.util.Collection;

/**
 * 获取联系人保存到数据库的服务，在MainActivity中启动
 * Created by Administrator on 2016/4/19.
 */
public class GetContactsToDb extends Service {
    private Roster m_roster;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
    }

    private void initData() {
        //获取联系人,需要连接对象conn
        if (Constant.conn != null) {

            //将联系人保存到数据库
            ThreadUtils.runInThread(new Runnable() {

                @Override
                public void run() {
                    m_roster = Constant.conn.getRoster();//放到子线程中执行

                    //得到所有联系人
                    Collection<RosterEntry> entries = m_roster.getEntries();

                    for (RosterEntry entry : entries) {
                        saveOrUpdateData(entry);//保存或更新联系人
                        System.out.println("执行了保存联系人的方法");
                    }

                    //联系人改变的监听
                    m_roster.addRosterListener(new MyRosterListener());

                }
            });


        } else {
            //ToastUtils.showToastSafe(getContext(), "没有连接到服务器，请重新登录");
            //startActivity(new Intent(getContext(), LoginActivity.class));
        }
    }

    /**
     * 抽取的方法，将联系人保存到数据库,在子线程中运行
     */
    private void saveOrUpdateData(RosterEntry entry) {
        ContentValues values = new ContentValues();
        String account = entry.getUser();
        String nickname = entry.getName();
        System.out.println("联系人账号：" + account);
        //处理昵称
        if (nickname == null || "".equals(nickname)) {
            //nickname=account.substring(0,account.indexOf("@"));//billy@.qq.com
            nickname = account;
        }
        values.put(Constant.ContactTable.ACCTOUN, account);
        values.put(Constant.ContactTable.NICKNAME, nickname);
        values.put(Constant.ContactTable.AVATAR, "0");
        values.put(Constant.ContactTable.PINYIN, PinyinUtils.getPinyin(account));

        /************重要：先更新，后插入************************/
        int updateCount = getContentResolver().
                update(Uri.parse("content://com.example.administrator.xmpp/contact"),
                        values, Constant.ContactTable.ACCTOUN + "=?", new String[]{account});
        System.out.println("更新的数据条数：" + updateCount);
        if (updateCount <= 0) {//没有更新到任何数据
            getContentResolver().
                    insert(Uri.parse("content://com.example.administrator.xmpp/contact"), values);
        }
    }

    //监听有关的操作最好放到服务中进行，这样，只要联系人有改变，就会在服务中更新数据库，不用每次都在启动activity时,再从服务器获取数据
    class MyRosterListener implements RosterListener {

        @Override
        public void entriesAdded(Collection<String> collection) {//添加了联系人
            //要么更新，要么插入
            for (String address : collection) {
                RosterEntry entry = m_roster.getEntry(address);
                saveOrUpdateData(entry);//保存到数据库
            }

        }

        @Override
        public void entriesUpdated(Collection<String> collection) {//修改了联系人
            //要么更新，要么插入
            for (String address : collection) {
                RosterEntry entry = m_roster.getEntry(address);
                saveOrUpdateData(entry);//保存到数据库
            }

        }

        @Override
        public void entriesDeleted(Collection<String> collection) {//删除了联系人
            for (String account : collection) {

                //删除操作
                getContentResolver().delete(Uri.parse("content://com.example.administrator.xmpp/contact"),
                        Constant.ContactTable.ACCTOUN + "=?", new String[]{account});
            }

        }

        @Override
        public void presenceChanged(Presence presence) {

        }
    }
}
