package com.example.administrator.xmpp.config;

import android.provider.BaseColumns;

import org.jivesoftware.smack.XMPPConnection;

/**
 * 定义的一些常量
 * Created by DengLibin on 2016/4/17 0017.
 */
public class Constant {
    public static final String SERVER_URL = "192.168.1.103";
    public static final int SERVER_PORT = 5222;
    public static final String SERVER_NAME = "my_open_fire";
    public static XMPPConnection conn = null;//连接服务器成功后被赋值
    public static String m_account = null;//当前登录的账号，登录成功后被赋值
    public static final String TABLE_CONTACT = "t_contacts";//联系人的表名
    public static final String TABLE_SMS = "t_sms";//消息的表名

    //联系人表的字段
    public class ContactTable implements BaseColumns {//默认会给我们添加一列 _id
        public static final String ACCTOUN = "account";//账号
        public static final String NICKNAME = "nickname";//昵称
        public static final String AVATAR = "avatar";//头像
        public static final String PINYIN = "pinyin";//账号拼音

    }

    //消息表的字段
    public class SmsTable implements BaseColumns {
        public static final String FROM_ACCOUNT = "from_account";//消息来自哪个账号
        public static final String TO_ACCOUNT = "TO_account";//消息要发给哪个账号
        public static final String BODY = "body";//消息的内容
        public static final String TYPE = "type";//消息的类型
        public static final String TIME = "time";//发送时间
        public static final String SESSION_ACCOUNT = "session_account";//对方账号（对于消息这个对象来说，没有对方己方，只有来自来哪里要去哪里
                                                                        //该字段内容的确定要根据用户正在和谁聊天来确定 ），如果该条消息是发出去的，
                                                                        //那么该字段的值就是to_account的值，如果该条消息是接收到的，那么该字段的值
                                                                        //就是from_account的值

    }

    public class Contact {
        public static final String CONTACT_ACCOUNT = "account";
        public static final String CONTACT_NICKNAME = "nickname";
    }
    //对话消息列表，当前的item是接收到的消息还是发出去的消息
    public class Chat_item_type{
        public static final int RECECIVE=1;
        public static final int SEND=0;
    }
}