package com.baidu.bvideoviewsample1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Process;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.bvideoviewsample1.view.SubtitleSettingPopupWindow;
import com.baidu.cyberplayer.core.BMediaController;
import com.baidu.cyberplayer.core.BVideoView;
import com.baidu.cyberplayer.core.BVideoView.OnCompletionListener;
import com.baidu.cyberplayer.core.BVideoView.OnCompletionWithParamListener;
import com.baidu.cyberplayer.core.BVideoView.OnErrorListener;
import com.baidu.cyberplayer.core.BVideoView.OnInfoListener;
import com.baidu.cyberplayer.core.BVideoView.OnPlayingBufferCacheListener;
import com.baidu.cyberplayer.core.BVideoView.OnPositionUpdateListener;
import com.baidu.cyberplayer.core.BVideoView.OnPreparedListener;
import com.baidu.cyberplayer.core.BVideoView.OnTotalCacheUpdateListener;
import com.baidu.cyberplayer.subtitle.SubtitleManager;
import com.baidu.cyberplayer.subtitle.utils.SubtitleError;
import com.baidu.cyberplayer.subtitle.utils.SubtitleErrorCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VideoViewPlayingActivity extends Activity implements OnPreparedListener, 
                            OnCompletionListener,
                            OnErrorListener, 
                            OnInfoListener,
                            OnPlayingBufferCacheListener,
                            OnCompletionWithParamListener,
                            OnPositionUpdateListener,
                            OnTotalCacheUpdateListener
                            {
    private final String TAG = "VideoViewPlayingActivity";
    
    /**
     * 您的AK 
     * 请到http://console.bce.baidu.com/iam/#/iam/accesslist获取
     */
    private String AK = "cc9e449e499a4a67b952829ffc543d9d";   // 请录入您的AK !!!
        
    private String mVideoSource = null;
    
    private BVideoView mVV = null;
    private BMediaController mVVCtl = null;
    private RelativeLayout mViewHolder = null;
    private ListView mResolutionSelector = null;
    private LinearLayout mControllerHolder = null;
    
    private boolean mIsHwDecode = false;
    
    private EventHandler mEventHandler;
    private HandlerThread mHandlerThread;
    
    private final Object SYNC_Playing = new Object();
        
    private static final int UI_EVENT_PLAY = 0;
    private static final int UI_EVENT_TAKESNAPSHOT = 2;

    private WakeLock mWakeLock = null;
    private static final String POWER_LOCK = "VideoViewPlayingActivity";
    private Toast toast;
    
    private String[] mAvailableResolution = null;
    
    /**
     * 播放状态
     */
    private  enum PLAYER_STATUS {
        PLAYER_IDLE, PLAYER_PREPARING, PLAYER_PREPARED,
    }
    
    private PLAYER_STATUS mPlayerStatus = PLAYER_STATUS.PLAYER_IDLE;
    
    
    /**
     * 记录播放位置
     */
    private int mLastPos = 0;
    
    //add for subtitle
    private Button mSubtitleButton;
    private SubtitleSettingPopupWindow mSubtitleSettingWindow;
    private RelativeLayout mRoot;
    private SubtitleManager mSubtitleManager;
    
    class EventHandler extends Handler {
        public EventHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UI_EVENT_PLAY:
                    /**
                     * 如果已经播放了，等待上一次播放结束
                     */
                    if (mPlayerStatus != PLAYER_STATUS.PLAYER_IDLE) {
                        synchronized (SYNC_Playing) {
                            try {
                                SYNC_Playing.wait();
                                //Log.v(TAG, "wait player status to idle");
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
    
                    /**
                     * 设置播放url
                     */
    
//                    mVV.setLogLevel(4);
                    mVV.setVideoPath(mVideoSource);
                   // mVV.setVideoScalingMode(BVideoView.VIDEO_SCALING_MODE_SCALE_TO_FIT);
                    /**
                     * 续播，如果需要如此
                     */
                    if (mLastPos > 0) {
    
                        mVV.seekTo(mLastPos);
                        mLastPos = 0;
                    }
    
                    /**
                     * 显示或者隐藏缓冲提示 
                     */
                    mVV.showCacheInfo(true);
                    /**
                     * 开始播放
                     */
                    mVV.start();
    
                    mPlayerStatus = PLAYER_STATUS.PLAYER_PREPARING;
                    break;
                case UI_EVENT_TAKESNAPSHOT:
                    boolean sdCardExist = isExternalStorageWritable();
                    File sdDir = null;
                    String strpath = null;
                    Bitmap bitmap = null;
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSSS");
                    String time = formatter.format(date);
                   // Log.i(TAG, "sdcardExist=" + sdCardExist);
                    if (sdCardExist) {
                        sdDir = Environment.getExternalStorageDirectory();
                        // check the dir is existed or not!.
                        // File file = new File(sdDir.toString());
                        strpath = sdDir.toString() + "/" + time + ".jpg";
                    } else {
                        strpath = "/data/data/" + "/" + time + ".jpg";
                    }
                   // Log.e(TAG, "snapshot save path=" + strpath);
    
                    bitmap = mVV.takeSnapshot();
                    if (bitmap != null) {
                        FileOutputStream os = null;
                        try {
                            os = new FileOutputStream(strpath, false);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                            os.flush();
                            os.close();
                        } catch (Throwable t) {
                            t.printStackTrace();
                        } finally {
                            if (os != null) {
                                try {
                                    os.close();
                                } catch (Throwable t) {
                                   // Log.e(TAG, "Error occurred while saving snapshot!");
                                }
                            }
                        }
                        os = null;
                    }
    
                    if (toast == null) {
                        toast = Toast.makeText(VideoViewPlayingActivity.this, strpath, Toast.LENGTH_SHORT);
                    } else {
                        toast.setText(strpath);
                    }
                    toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
                    toast.show();
    
                    break;
                default:
                    break;
            }
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
        return true;
        }
        return false;
    }

    /**
     * 实现切换示例
     */
    private View.OnClickListener mPreListener = new View.OnClickListener() {
        
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            //Log.v(TAG, "pre btn clicked");
            /**
             * 如果已经开发播放，先停止播放
             */
            if(mPlayerStatus != PLAYER_STATUS.PLAYER_IDLE){
                mVV.stopPlayback();
            }
            
            /**
             * 发起一次新的播放任务
             */
            if (mEventHandler.hasMessages(UI_EVENT_PLAY)) {
                mEventHandler.removeMessages(UI_EVENT_PLAY);
            }
            mEventHandler.sendEmptyMessage(UI_EVENT_PLAY);
        }
    };
    
    private View.OnClickListener mNextListener = new View.OnClickListener() {
        
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
           // Log.v(TAG, "next btn clicked");
        }
    };

    private View.OnClickListener mSnapshotListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            //mVV.takeSnapshot(null);
            mEventHandler.sendEmptyMessage(UI_EVENT_TAKESNAPSHOT);
          //  Log.v(TAG, "Snapshot clicked");
        }
    };
    
    private View.OnClickListener mSelectResolutionListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
           // Log.v(TAG, "Show resolution clicked");
            if (mResolutionSelector != null && mAvailableResolution != null && mAvailableResolution.length > 0) {
                mResolutionSelector.setAdapter(
                            new ArrayAdapter<String>(VideoViewPlayingActivity.this,
                            R.layout.resolution_item, mAvailableResolution));
                mResolutionSelector.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                        
        setContentView(R.layout.controllerplaying);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, POWER_LOCK);
        
        mIsHwDecode = getIntent().getBooleanExtra("isHW", false);
        Uri uriPath = getIntent().getData();
        System.out.println("uriPath="+uriPath);
        if (null != uriPath) {
            String scheme = uriPath.getScheme();
            if (null != scheme) {
                mVideoSource = uriPath.toString();
            } else {
                mVideoSource = uriPath.getPath();
            }
        }
        System.out.println("mVideoSources="+mVideoSource);
        initUI();
        /**
         * 开启后台事件处理线程
         */
        mHandlerThread = new HandlerThread("event handler thread",
                Process.THREAD_PRIORITY_BACKGROUND);
        mHandlerThread.start();
        mEventHandler = new EventHandler(mHandlerThread.getLooper());
    }
    
    /**
     * 初始化界面
     */
    private void initUI() {        
        mViewHolder = (RelativeLayout)findViewById(R.id.view_holder);
        mControllerHolder = (LinearLayout) findViewById(R.id.controller_holder);
        mResolutionSelector = (ListView) findViewById(R.id.resolution_selector);
        mResolutionSelector.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (mVV != null) {
                    mVV.selectResolutionType(arg2);
                }
                mResolutionSelector.setVisibility(View.GONE);
            }
        });
        
        /**
         * 设置ak
         */
        BVideoView.setAK(AK);
        mVV = new BVideoView(this);
        mVV.setCacheTime(0.5f);
        
        // Getting media-info, as well as the supported resolutions
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                BVideoView.getMediaInfo(VideoViewPlayingActivity.this, mVideoSource);
//                mAvailableResolution = BVideoView.getSupportedResolution();
//            }
//        }).start();
        
        /**
         *创建BVideoView和BMediaController
         */
        
        mVVCtl = new BMediaController(this);
        mViewHolder.addView(mVV);
        mControllerHolder.addView(mVVCtl);
        
        /**
         * 注册listener
         */
        mVV.setOnPreparedListener(this);
        mVV.setOnCompletionListener(this);
        mVV.setOnCompletionWithParamListener(this);
        mVV.setOnErrorListener(this);
        mVV.setOnPlayingBufferCacheListener(this);
        mVV.setOnTotalCacheUpdateListener(this);
        mVV.setOnInfoListener(this);
        mVV.setOnPositionUpdateListener(this);
        mVVCtl.setPreNextListener(mPreListener, mNextListener);
        mVVCtl.setSnapshotListener(mSnapshotListener);
        mVVCtl.setResolutionListener(mSelectResolutionListener);
        /**
         * 关联BMediaController
         */
        mVV.setMediaController(mVVCtl);
//disable dolby audio effect
//        mVV.setEnableDolby(false);
        /**
         * 设置解码模式
         */
        mVV.setDecodeMode(BVideoView.DECODE_SW);
        mVV.setWatermarkText("Test Watermark!");
        mVV.setVideoScalingMode(BVideoView.VIDEO_SCALING_MODE_SCALE_TO_FIT);
        initSubtitleSetting();
    }
    
       
    private void initSubtitleSetting() {
        mSubtitleManager = mVV.getSubtitlePlayManger(new SubtitleErrorCallback() {

            @Override
            public void onSubtitleError(SubtitleError error) {
                Log.w("test_subtitle", "code: " + error.errorCode + " msg: " + error.errorMsg);
            }
        });

        mRoot = (RelativeLayout) findViewById(R.id.root);
        mSubtitleButton = (Button) findViewById(R.id.subtitle_setting);
        mSubtitleButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                trigerSubtitleSettingPopupWindow();
            }

        });

    }

    protected void trigerSubtitleSettingPopupWindow() {
        if (mSubtitleSettingWindow == null) {
            mSubtitleSettingWindow = createSubtitleSettingWindow();
        }
        if (mSubtitleSettingWindow == null) {
            return;
        }

        if (mSubtitleSettingWindow.isShowing()) {
            mSubtitleSettingWindow.dismiss();
        } else {
            mSubtitleSettingWindow.showAtLocation(mRoot, Gravity.CENTER, 0, 0);
        }
        
    }

    private SubtitleSettingPopupWindow createSubtitleSettingWindow() {
        // TODO Auto-generated method stub
        return new SubtitleSettingPopupWindow(this, mSubtitleManager);
    }
    
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
       // Log.v(TAG, "onPause");
        /**
         * 在停止播放前 你可以先记录当前播放的位置,以便以后可以续播
         */
        if (mVV.isPlaying() && (mPlayerStatus != PLAYER_STATUS.PLAYER_IDLE)) {
            mLastPos = (int) mVV.getCurrentPosition();
//when scree lock,paus is good select than stop
            // don't stop pause
            // mVV.stopPlayback();
            mVV.pause();
        }    
    }

    
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
       // Log.v(TAG, "onResume");
        if (null != mWakeLock && (!mWakeLock.isHeld())) {
            mWakeLock.acquire();
        }
        // 发起一次播放任务,当然您不一定要在这发起
        if (!mVV.isPlaying() && (mPlayerStatus != PLAYER_STATUS.PLAYER_IDLE)) {
            mVV.resume();
        } else {
            mEventHandler.sendEmptyMessage(UI_EVENT_PLAY);
        }
    }
    
    @Override
    protected void onStop(){
        super.onStop();
      //  Log.v(TAG, "onStop");
        // 在停止播放前 你可以先记录当前播放的位置,以便以后可以续播
        if (mVV.isPlaying() && (mPlayerStatus != PLAYER_STATUS.PLAYER_IDLE)) {
            mLastPos = (int) mVV.getCurrentPosition();
            // don't stop pause
            // mVV.stopPlayback();
            mVV.pause();
        }
        if (toast != null){
            toast.cancel();
        }
    }
    
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if ((mPlayerStatus != PLAYER_STATUS.PLAYER_IDLE)) {
            mLastPos = (int) mVV.getCurrentPosition();
            mVV.stopPlayback();
        }
        if(toast != null){
            toast.cancel();
        }
        /**
         * 结束后台事件处理线程
         */
        mHandlerThread.quit();
        //Log.v(TAG, "onDestroy");
    }

    @Override
    public boolean onInfo(int what, int extra) {
        // TODO Auto-generated method stub
        switch(what){
        /**
         * 开始缓冲
         */
        case BVideoView.MEDIA_INFO_BUFFERING_START:
            break;
        /**
         * 结束缓冲
         */
        case BVideoView.MEDIA_INFO_BUFFERING_END:

            break;
        default:
            break;
        }
        return false;
    }
    
    /**
     * 当前缓冲的百分比， 可以配合onInfo中的开始缓冲和结束缓冲来显示百分比到界面
     */
    @Override
    public void onPlayingBufferCache(int percent) {
        // TODO Auto-generated method stub
        
    }    

    /**
     * 播放出错
     */
    @Override
    public boolean onError(int what, int extra) {
        // TODO Auto-generated method stub
        //Log.v(TAG, "onError");
        synchronized (SYNC_Playing) {
            SYNC_Playing.notify();
        }
        mPlayerStatus = PLAYER_STATUS.PLAYER_IDLE;
        return true;
    }

    /**
     * 播放完成
     */
    @Override
    public void onCompletion() {
        // TODO Auto-generated method stub
       // Log.v(TAG, "onCompletion");
        
        synchronized (SYNC_Playing) {
            SYNC_Playing.notify();
        }
        mPlayerStatus = PLAYER_STATUS.PLAYER_IDLE;
    }

    /**
     * 播放准备就绪
     */
    @Override
    public void onPrepared() {
        // TODO Auto-generated method stub
        //Log.v(TAG, "onPrepared");
        mPlayerStatus = PLAYER_STATUS.PLAYER_PREPARED;
    }
    
    @Override
    public void OnCompletionWithParam(int param) {
//        param = 307 is end of stream
        // TODO Auto-generated method stub
        //Log.v(TAG, "OnCompletionWithParam=" + param);
    }
    
    public void onClickEmptyArea(View v) {
        if (mResolutionSelector != null
                && mResolutionSelector.getVisibility() == View.VISIBLE) {
            mResolutionSelector.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onPositionUpdate(long pos) {
       // Log.i(TAG, "Current playing position is " + pos + " milliseconds.");
        return true;
    }

    @Override
    public void onTotalCacheUpdate(long sec) {
        //Log.i(TAG, "Totally caching position is " + sec + " seconds.");
    }
}
