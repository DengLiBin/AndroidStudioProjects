package com.example.administrator.xmpp.utils;

import opensource.jpinyin.PinyinFormat;
import opensource.jpinyin.PinyinHelper;

/**
 * Created by Administrator on 2016/4/19.
 */
public class PinyinUtils {
    public static String getPinyin(String str){
        return PinyinHelper.convertToPinyinString(str,"", PinyinFormat.WITHOUT_TONE);
    }
}
