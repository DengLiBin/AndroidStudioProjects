package com.example.administrator.xmpp.activitys;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.xmpp.R;
import com.example.administrator.xmpp.config.Constant;
import com.example.administrator.xmpp.dbhelper.SmsOpenHelper;
import com.example.administrator.xmpp.provider.SmsProvider;
import com.example.administrator.xmpp.utils.ThreadUtils;
import com.example.administrator.xmpp.utils.ToastUtils;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    private String account;
    private String nickname;
    private ListView m_lv_chat;
    private EditText m_et_chat;
    private Button m_btn_send;
    private CursorAdapter cursorAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            Cursor cur = (Cursor) msg.obj;
            setAdapter(cur);//给listView设置数据
        }
    };
    private ChatManager chatManager;
    String from_account;
    String to_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        init();
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        //2、创建聊天对象
        final Chat chat = chatManager.createChat(account, null);//传入一个接收消息的监听器null
        m_btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreadUtils.runInThread(new Runnable() {
                    @Override
                    public void run() {
                        final String body = m_et_chat.getText().toString().trim();//消息内容

                        //3、发送消息
                        Message msg = new Message();
                        msg.setFrom(from_account);//当前登录的账号
                        if (!account.contains("@")) {
                            to_account = account + "@pc201604171807/Smack";
                        }
                        msg.setTo(to_account);//对方的id
                        System.out.println("消息框中类容是：" + body);
                        msg.setBody(body);//消息框中的内容
                        msg.setType(Message.Type.chat);//类型就是chat
                        msg.setProperty("key", "value");//额外的信息

                        System.out.println("發送消息：" + msg.getFrom() + "到" + msg.getTo());
                        try {
                            System.out.println("下面一步是发送消息:" + msg.getBody());
                            chat.sendMessage(msg);
                            saveSmsToDb(to_account, msg);//发送消息时，将发送的消息保存到数据库
                            // 4.清空输入框
                            ThreadUtils.runInUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    m_et_chat.setText("");
                                }
                            });
                        } catch (XMPPException e) {
                            ToastUtils.showToastSafe(ChatActivity.this, "发送失败");
                            System.out.println("捕获到异常，发送消息失败，");
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    private void initData() {
        ThreadUtils.runInThread(new Runnable() {
            @Override
            public void run() {
                //查询操作放到子线程中进行
                Cursor cursor = getContentResolver().query(SmsProvider.SMS_PROVIDER_URI, null, null, null, null);//没有限制条件,全部查询出来
                android.os.Message msg = android.os.Message.obtain();
                msg.obj = cursor;
                handler.sendMessage(msg);
            }
        });


    }

    private void initView() {
        m_lv_chat = (ListView) findViewById(R.id.lv_chat);
        m_et_chat = (EditText) findViewById(R.id.et_chat);
        m_btn_send = (Button) findViewById(R.id.btn_send);
    }

    private void init() {
        //1、获取消息管理者
        from_account = Constant.m_account + "@pc201604171807/Smack";
        chatManager = Constant.conn.getChatManager();
        chatManager.addChatListener(new MyChatManagerListener());
        registerConentObserver();//注册监听
        Intent intent = getIntent();
        account = intent.getStringExtra(Constant.Contact.CONTACT_ACCOUNT);
        to_account=account;
        account=account.substring(0,account.indexOf("@"));
        nickname = intent.getStringExtra(Constant.Contact.CONTACT_NICKNAME);
        getSupportActionBar().setTitle("正在与" + nickname + "聊天");
    }

    private void setAdapter(final Cursor cur) {
        if (cursorAdapter != null) {
            Cursor cursor = cursorAdapter.getCursor();
            cursor.requery();
            m_lv_chat.setSelection(cursor.getCount() - 1);
        }
        cursorAdapter = new CursorAdapter(ChatActivity.this, cur) {
            @Override
            public int getCount() {
                return super.getCount();

            }

            //如果convertView==null，调用该方法，返回一个View
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return null;
            }

            //设置数据可以在这里进行
            @Override
            public void bindView(View view, Context context, Cursor cursor) {

            }

            //当前位置该使用那种样式的item
            @Override
            public int getItemViewType(int position) {
                cur.moveToPosition(position);//游标移到position这个位置，拿到该位置的消息的创建者
                String fromAccount = cur.getString(cur.getColumnIndex(Constant.SmsTable.FROM_ACCOUNT));
                if (!(Constant.m_account + "@pc201604171807/Smack").equals(fromAccount)) {
                    //当前消息是接收到的消息
                    return Constant.Chat_item_type.RECECIVE;
                } else {
                    return Constant.Chat_item_type.SEND;
                    //当前消息是发送的消息
                }
                //接收----当前消息的创建者不等于当前账号
                //发送----当前消息的创建者就是当前登录的账号

            }

            //listView有多种样式的item，复写该方法,这里是2种
            @Override
            public int getViewTypeCount() {
                return 2;
            }

            //该方法根据需要进行复写
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if (getItemViewType(position) == Constant.Chat_item_type.RECECIVE) {
                    if (convertView == null) {
                        convertView = View.inflate(getApplicationContext(), R.layout.item_chat_receive, null);
                        holder = new ViewHolder();
                        convertView.setTag(holder);
                        //holder赋值
                        holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                        holder.tv_sms = (TextView) convertView.findViewById(R.id.tv_sms);
                    } else {
                        holder = (ViewHolder) convertView.getTag();
                    }
                    //填充数据
                    fillData(cur, holder, position);

                } else {
                    if (convertView == null) {
                        convertView = View.inflate(getApplicationContext(), R.layout.item_chat_send, null);
                        holder = new ViewHolder();
                        convertView.setTag(holder);
                        //holder赋值
                        holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time_out);
                        holder.tv_sms = (TextView) convertView.findViewById(R.id.tv_sms_send);
                    } else {
                        holder = (ViewHolder) convertView.getTag();
                    }

                    //填充数据
                    fillData(cur, holder, position);

                }
                return convertView;
            }

            //填充数据
            private void fillData(Cursor cur, ViewHolder holder, int position) {
                cur.moveToPosition(position);
                String time = cur.getString(cur.getColumnIndex(Constant.SmsTable.TIME));
                String formatTime = new SimpleDateFormat("yyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(time)));
                String sms = cur.getString(cur.getColumnIndex(Constant.SmsTable.BODY));
                holder.tv_time.setText(formatTime);
                holder.tv_sms.setText(sms);
            }
        };
        m_lv_chat.setAdapter(cursorAdapter);
        m_lv_chat.setSelection(cur.getCount() - 1);//滚动到最后一行
    }

    /**
     * 将发送的或者接收到的消息保存到数据库
     * ContentResolver--->ContentProvider----->SQLite
     *
     * @param msg           消息对象
     * @param sessonAccount 与当前用户正在聊天的对方账号
     */
    private void saveSmsToDb(String sessonAccount, Message msg) {
        System.out.println("执行了保存消息到数据库的方法saveSmsToDb");
        if (!TextUtils.isEmpty(msg.getBody())) {
            ContentValues values = new ContentValues();
            values.put(Constant.SmsTable.FROM_ACCOUNT, msg.getFrom());
            values.put(Constant.SmsTable.TO_ACCOUNT, msg.getTo());
            values.put(Constant.SmsTable.BODY, msg.getBody());
            values.put(Constant.SmsTable.TYPE, msg.getType().name());
            values.put(Constant.SmsTable.TIME, System.currentTimeMillis());
            values.put(Constant.SmsTable.SESSION_ACCOUNT, sessonAccount);
            getContentResolver().insert(SmsProvider.SMS_PROVIDER_URI, values);
        }


    }

    @Override
    protected void onDestroy() {
        unRegisterConentObserver();//取消注册监听
        super.onDestroy();
    }

    /**
     * 接收消息的监听器
     */
    class MyChatManagerListener implements ChatManagerListener {
        @Override
        public void chatCreated(Chat chat, boolean b) {
            chat.addMessageListener(new MessageListener() {
                @Override
                public void processMessage(Chat chat, Message message) {
                    System.out.println("接收到的消息xml是：" + message.toXML());
                    System.out.println("body" + message.getBody());
                    System.out.println("type" + message.getType().name());
                    System.out.println("from" + message.getFrom());
                    System.out.println("to" + message.getTo());
                    System.out.println("properties" + message.getProperty("key"));
                    //将接收到的消息保存到数据库
                    String participant = chat.getParticipant();//对方id
                    saveSmsToDb(participant, message);
                }
            });
        }
    }


    /*************************
     * 监听数据库记录的变化
     ***********************************/
    MyContentObserver m_myContentObserver = new MyContentObserver(new Handler());

    /**
     * 注册监听,可以在onCreate方法中调用
     */
    public void registerConentObserver() {
        getContentResolver().registerContentObserver(SmsProvider.SMS_PROVIDER_URI,
                true, m_myContentObserver);
    }

    /**
     * 取消监听,可以在onDestroy方法中调用
     */
    public void unRegisterConentObserver() {
        getContentResolver().unregisterContentObserver(m_myContentObserver);
    }

    //监听数据库内容的变化
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
            if (cursorAdapter != null) {
                System.out.println("更新cursorAdapter");
                cursorAdapter.getCursor().requery();
                m_lv_chat.setSelection(cursorAdapter.getCount() - 1);
            }
        }
    }

    public class ViewHolder {
        public TextView tv_time;
        public TextView tv_sms;
    }
}
