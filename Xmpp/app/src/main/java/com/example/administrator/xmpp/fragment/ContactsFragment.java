package com.example.administrator.xmpp.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.administrator.xmpp.R;
import com.example.administrator.xmpp.activitys.ChatActivity;
import com.example.administrator.xmpp.config.Constant;
import com.example.administrator.xmpp.utils.ThreadUtils;


public class ContactsFragment extends Fragment {
    private CursorAdapter m_adapter;
    private ListView m_lv_contacts;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Cursor cur = (Cursor) msg.obj;
            m_adapter = new CursorAdapter(getContext(), cur) {
                //继承的是BaseAdapter,如果convertView为空，则会调用该方法,返回一个View
                @Override
                public View newView(Context context, Cursor cursor, ViewGroup parent) {
                    View v = View.inflate(context, R.layout.item_contact, null);
                    return v;
                }

                //给View设置数据和显示数据
                @Override
                public void bindView(View view, Context context, Cursor cursor) {
                    ImageView iv_head = (ImageView) view.findViewById(R.id.head);
                    TextView tv_account = (TextView) view.findViewById(R.id.account);
                    TextView tv_nickname = (TextView) view.findViewById(R.id.nickname);
                    String account = cursor.getString(cursor.getColumnIndex(Constant.ContactTable.ACCTOUN));
                    String nickname = cursor.getString(cursor.getColumnIndex(Constant.ContactTable.NICKNAME));
                    tv_account.setText(account);
                    tv_nickname.setText(nickname);
                }
            };

            m_lv_contacts.setAdapter(m_adapter);//展示数据
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initData();


    }

    private void init() {
        registerConentObserver();
    }

    private void initData() {
        //从数据库查询联系人
        ThreadUtils.runInThread(new Runnable() {
            @Override
            public void run() {
                //查询联系人，显示在联系人列表
                updateAdapter();
            }
        });
    }

    //查询数据库的联系人，显示在联系人列表，子线程中运行
    private void updateAdapter() {

        Cursor cursor = getActivity().getContentResolver().query(Uri.parse("content://com.example.administrator.xmpp/contact"), null, null, null, null);
        while (cursor.moveToNext()) {
            System.out.println("遍历查询结果:" + cursor.getString(cursor.getColumnIndex(Constant.ContactTable.ACCTOUN)));
        }
        //设置adapter，然后将查询联系人的结果显示
        Message msg = Message.obtain();
        msg.obj = cursor;
        handler.sendMessage(msg);

    }

    private void initListener() {
            m_lv_contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Cursor c=m_adapter.getCursor();
                    c.moveToPosition(position);
                    String account=c.getString(c.getColumnIndex(Constant.ContactTable.ACCTOUN));
                    String nickname=c.getString(c.getColumnIndex(Constant.ContactTable.NICKNAME));
                    Intent intent=new Intent(getContext(), ChatActivity.class);

                    intent.putExtra(Constant.Contact.CONTACT_ACCOUNT,account);
                    intent.putExtra(Constant.Contact.CONTACT_NICKNAME,nickname);
                    startActivity(intent);
                }
            });

    }

    private void initView(View view) {
        m_lv_contacts = (ListView) view.findViewById(R.id.lv_contacts);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        initView(view);
        initListener();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDestroy() {
        unRegisterConentObserver();//取消监听
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    /*************************
     * 监听数据库记录的变化
     ***********************************/
    MyContentObserver m_myContentObserver = new MyContentObserver(new Handler());

    /**
     * 注册监听,可以在onCreate方法中调用
     */
    public void registerConentObserver() {
        getActivity().getContentResolver().registerContentObserver(Uri.parse("content://com.example.administrator.xmpp/contact"),
                true, m_myContentObserver);
    }

    /**
     * 取消监听,可以在onDestroy方法中调用
     */
    public void unRegisterConentObserver() {
        getActivity().getContentResolver().unregisterContentObserver(m_myContentObserver);
    }

    class MyContentObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public MyContentObserver(Handler handler) {
            super(handler);

        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange);
            if (m_adapter != null) {
                m_adapter.getCursor().requery();
            }
        }
    }
}
