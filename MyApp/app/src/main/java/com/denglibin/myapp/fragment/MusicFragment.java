package com.denglibin.myapp.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.denglibin.myapp.Config.Constant;
import com.denglibin.myapp.MainActivity;
import com.denglibin.myapp.R;
import com.denglibin.myapp.adapter.SongListAdapter;
import com.denglibin.myapp.service.MusicPlayer;
import com.denglibin.myapp.utils.MediaUtils;
import com.denglibin.myapp.utils.UiUtils;

/**
 * Created by Administrator on 2016/4/29.
 */
public class MusicFragment extends Fragment implements  View.OnClickListener{
    private MainActivity mainActivity;
    private ImageButton btn_previous;
    private ImageButton btn_play;
    private ImageButton btn_next;
    private ImageView iv_play;
    private ListView lv_songs;
    private Intent intent;
    private FloatingActionButton btn_location;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            btn_next.performClick();//自動播放下一曲
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("创建了 MusicFragment");
        intent=new Intent(getActivity(), MusicPlayer.class);
        mainActivity= (MainActivity) getContext();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        System.out.println("执行了onCreateView方法");
        View view=LayoutInflater.from(mainActivity).inflate(R.layout.fragment_music,null);
        MediaUtils.initSongList(getActivity());
        initView(view);//拿到底部的控制按鈕
        initListener();
        return view;
    }


    private void initView(View root){
        lv_songs= (ListView) root.findViewById(R.id.lv_songs);
        btn_previous= (ImageButton) root.findViewById(R.id.ib_bottom_previous);
        btn_play= (ImageButton) root.findViewById(R.id.ib_bottom_play);
        btn_next= (ImageButton) root.findViewById(R.id.ib_bottom_next);
        iv_play= (ImageView) root.findViewById(R.id.iv_play);
        if(MediaUtils.play_state==Constant.PLAY_STATE_RUNNING){
            iv_play.setImageResource(R.mipmap.ic_pause_circle_filled_white_36dp);
        }
        btn_location= (FloatingActionButton) root.findViewById(R.id.btn_location);
        lv_songs.setAdapter(new SongListAdapter());
    }

    private void initListener(){
        btn_previous.setOnClickListener(this);
        btn_play.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_location.setOnClickListener(this);
        lv_songs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setSongTitleColor(Color.BLACK);//將上一曲的歌名顏色改為白色
                MediaUtils.playing_position=position;
                setSongTitleColor(UiUtils.getResource().getColor(R.color.colorBar));
                startPlay(intent);
            }
        });
    }
    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_bottom_play:
                if(MediaUtils.play_state==Constant.PLAY_STATE_STOP){
                    setSongTitleColor(UiUtils.getResource().getColor(R.color.colorBar));
                    startPlay(intent);//開始播放音樂
                }else if(MediaUtils.play_state==Constant.PLAY_STATE_RUNNING){
                    intent.putExtra("option","pause");
                    UiUtils.getContext().startService(intent);//暫停
                    iv_play.setImageResource(R.mipmap.ic_play_circle_filled_white_36dp);
                }else if(MediaUtils.play_state==Constant.PLAY_STATE_PAUSE){
                    intent.putExtra("option","continue");//繼續
                    UiUtils.getContext().startService(intent);
                    iv_play.setImageResource(R.mipmap.ic_pause_circle_filled_white_36dp);
                }
                break;
            case R.id.ib_bottom_previous:
                setSongTitleColor(Color.BLACK);//將上一曲的歌名顏色改為白色
                if(MediaUtils.playing_position==0){
                    MediaUtils.playing_position=MediaUtils.getSongsCount();
                }
                MediaUtils.playing_position--;
                setSongTitleColor(UiUtils.getResource().getColor(R.color.colorBar));//當前播放的歌曲名改為紅色
                startPlay(intent);
                break;
            case R.id.ib_bottom_next:
                setSongTitleColor(Color.BLACK);//將上一曲的歌名顏色改為白色
                if(MediaUtils.playing_position==MediaUtils.getSongsCount()-1){
                    MediaUtils.playing_position=-1;
                }
                MediaUtils.playing_position++;
                setSongTitleColor(UiUtils.getResource().getColor(R.color.colorBar));//當前播放的歌曲名改為紅色
                startPlay(intent);
                break;
            case R.id.btn_location:
                lv_songs.setSelection(MediaUtils.playing_position-4);//定位到正在播放的音樂
                break;
            default:
                break;
        }
    }
    //開始播放音樂
    private void startPlay(Intent intent){
        intent.putExtra("option","play");
        intent.putExtra("path",MediaUtils.songList.get(MediaUtils.playing_position).getPath());
        intent.putExtra("messenger", new Messenger(handler));
        UiUtils.getContext().startService(intent);
        iv_play.setImageResource(R.mipmap.ic_pause_circle_filled_white_36dp);
    }
    //改變正在播放的歌曲名的顏色

    private void setSongTitleColor(int color){
        TextView title = (TextView) lv_songs.findViewWithTag(MediaUtils.playing_position);
        if (title != null) {
            title.setTextColor(color);
        }
    }
}
