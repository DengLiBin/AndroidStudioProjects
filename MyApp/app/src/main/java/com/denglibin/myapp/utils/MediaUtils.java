package com.denglibin.myapp.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.denglibin.myapp.Config.Constant;
import com.denglibin.myapp.domain.Music;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/30.
 */
public class MediaUtils {
    public static int playing_position=0;//當前正在播放的那首歌的位置
    public static int play_state= Constant.PLAY_STATE_STOP;//播放狀態
    public static List<Music> songList=new ArrayList<Music>();

    //加载手机里面的音乐
    public static List<Music> initSongList(Context context){
        songList.clear();
        Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection={MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DATA};
        Cursor cursor=context.getContentResolver().query(uri,projection,null,null,null);

        while(cursor.moveToNext()){
            String title=cursor.getString(cursor.getColumnIndex(projection[0]));
            String artist=cursor.getString(cursor.getColumnIndex(projection[1]));
            String path=cursor.getString(cursor.getColumnIndex(projection[2]));
            songList.add(new Music(title,artist,path));
        }
        cursor.close();
        return  songList;
    }
    public static int getSongsCount(){
        return songList.size();
    }
    //格式化歌曲时间长度
    public static String fomatTime(int duration){
        String result="";
        int i=duration/1000;
        int min=i/60;
        int sec=i%60;
        if(min>9&&sec>9){
            result=min+":"+sec;
            return result;
        }else if(min<=9){
            if(sec>9){
                result="0"+min+":"+sec;
                return result;
            }else if(sec<=9){
                result="0"+min+":0"+sec;
                return result;
            }
        }
        return result;
    }
    //获取歌词文件，歌词文件和歌曲文件在同一个目录
    public static File getLrc(String path){
        File file;
        String filePath=path.replace(".mp3",".lrc");
        file=new File(filePath);
        if(!file.exists()){
            filePath=path.replace(".mp3",".txt");//与歌曲名相同的txt文件
            file=new File(filePath);
            if(!file.exists()){
                return  null;
            }
        }
        return  file;
    }
}
