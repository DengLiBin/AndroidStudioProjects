package com.denglibin.myapp.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.denglibin.myapp.Config.Constant;
import com.denglibin.myapp.utils.MediaUtils;
import com.denglibin.myapp.utils.UiUtils;

/**
 * Created by Administrator on 2016/4/30.
 */
public class MusicPlayer extends Service implements  MediaPlayer.OnErrorListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,AudioManager.OnAudioFocusChangeListener{
    private MediaPlayer mediaPlayer;
    private Messenger messenger;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    //多次启动也只调用一次
    @Override
    public void onCreate() {
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        super.onCreate();
    }
    ////每次启动都为来到此方法
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String option=intent.getStringExtra("option");
        if ("play".equals(option)) {
            String path = intent.getStringExtra("path");
            messenger = (Messenger) intent.getExtras().get("messenger");
            play(path);//调用播放方法
        }else if("pause".equals(option)){
            pause();
        }else if("continue".equals(option)){
            continuePlay();
        }
        return super.onStartCommand(intent, flags, startId);
    }
    //销毁时调用该方法
    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        super.onDestroy();
    }
    //開始播放音樂，需要音樂文件的路徑
    private void play(String path){
        try{
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();//准备，本地音乐使用同步准备就可以了
            mediaPlayer.start();//開始播放
            MediaUtils.play_state= Constant.PLAY_STATE_RUNNING;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //暫停
    private void pause(){
        if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            MediaUtils.play_state=Constant.PLAY_STATE_PAUSE;
        }
    }
    //繼續播放
    private void continuePlay(){
        if (mediaPlayer!= null) {
            mediaPlayer.start();
            MediaUtils.play_state = Constant.PLAY_STATE_RUNNING;
        }
    }
    @Override
    public void onAudioFocusChange(int focusChange) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        MediaUtils.play_state=Constant.PLAY_STATE_STOP;
        try {
            //service发送消息.告诉activity,当前的歌曲播放完了
            Message msg = Message.obtain();
            //发送消息
            messenger.send(msg);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }
}
