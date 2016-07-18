package com.baidu.bvideoviewsample1;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.cyberplayer.core.BVideoView;
//added for BaiduMediaInfo
//add end
public class CyberPlayerMediaInfoTestActivity extends Activity {
    private final String TAG = "CyberPlayerMediaInfoTestActivity";
    private int colorFormat = 0;
    private String mVideoSource = null;
    final static int RAW_YUV420 = 0;
    final static int RAW_RGB565 = 1;
    final static int RAW_ARGB8888 = 2;
    final static int BMP_OBJECT = 3;
    private BVideoView mVV = null;
    private final static int thumbnailWidth = 320;
    private final static int thumbnailHeight = 180;
    TempMediaInfo TempMI;
    private final int UI_EVENT_GET = 0;
    private final int UI_EVENT_DISPLAY = 1;

    private TextView tWidth = null;
    private TextView tHeight = null;
    private TextView tAudioCodecID = null;
    private TextView tVideoCodecID = null;
    private TextView tExtension = null;
    private TextView tLongName = null;
    private TextView tTotBitRateKb = null;
    private TextView tFileSizeKb = null;
    private TextView tDuration = null;
    private TextView tTitle = null;
    private TextView tVBitRateKb = null;
    private TextView tFrameRate = null;
    private TextView tSampleRate = null;
    private TextView tChannels = null;
    private TextView tABitRateKb = null;
    private TextView tCurrPostion = null;

    private class TempMediaInfo{
        int width;
        int height;

        String AudioName;
        String VideoName;
        String Extension;
        String LongName;
        int TotBitRateKb;
        int FileSizeKb;
        int DurationUs;
        String Title;
        int VBitRateKb;
        float FrameRate;
        int SampleRate;
        int Channels;
        int ABitRateKb;
        ByteBuffer thumbnailBuf;
        Bitmap bitmap;
    }

    Handler mUIHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case UI_EVENT_GET:
                getThumbnail();
                mUIHandler.sendEmptyMessage(UI_EVENT_DISPLAY);    
                break;
            case UI_EVENT_DISPLAY:
                displayMediaInfo();
                break;
            default:
                break;
            }
        }
    };

    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cyberplayermediainfotest);
        mImageView = (ImageView) findViewById(R.id.info_image);
        Uri uriPath = getIntent().getData();
        if (null != uriPath) {
            String scheme = uriPath.getScheme();
            if (null != scheme) {
                mVideoSource = uriPath.toString();
            } else {
                mVideoSource = uriPath.getPath();
            }
        }
        intMediaInfo();
        MyThread m = new MyThread();
        new Thread(m).start();
    }

    class MyThread implements Runnable {
        public void run() {
            try {
                Log.v(TAG, "mythread");
                getThumbnail();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            CyberPlayerMediaInfoTestActivity.this.mUIHandler.sendEmptyMessage(UI_EVENT_DISPLAY); // 向Handler发送消息,更新UI

        }
    }

    // if we want to get only media info, do not need thumbnail picture, we can do following:
    //     BVideoView(this, 0, 0, -1);
    // if we want get both media infomation AND thumbnail picture, we can do following:
    //     thumbnailWidth = xxx;
    //     thumbnailHeight = xxx;
    //     colorFormat = BMP_OBJECT or RAW_RGB565 or RAW_YUV420;
    // in above. BMP_OBJECT--> to get BMP object, and the user can write it to a file or use the BMP object directlly.
    //           RAW_RGB565--> to get the ByteBuffer with RGB565 color format.
    //           RAW_YUV420--> to get the ByteBuffer with YUV420 color format.
    //     BVideoView(this, thumbnailWidth, thumbnailHeight, colorFormat);
    private void intMediaInfo(){
        colorFormat = BMP_OBJECT; // BMP_OBJECT;//RAW_YUV420;//RAW_RGB565;    
    }

    /**
     *  init the class and input parameter.
     */
    private void getThumbnail(){
        int ret = BVideoView.getMediaInfo(this, mVideoSource, thumbnailWidth, thumbnailHeight, colorFormat);
        if (ret == 0){

            TempMI = new TempMediaInfo();
            TempMI.width = BVideoView.getVideoWidthInMediaInfo();
            TempMI.height = BVideoView.getVideoHeightInMediaInfo();

            // BVideoView.getAudioCodecID();
            // BVideoView.getVideoCodecID();

            TempMI.Extension = BVideoView.getExtension();
            TempMI.LongName = BVideoView.getLongName();
            TempMI.AudioName = BVideoView.getAcodecName();
            TempMI.VideoName = BVideoView.getVcodecName();

            TempMI.TotBitRateKb = BVideoView.getTotBitRateKb();
            TempMI.FileSizeKb = BVideoView.getFileSizeKb();
            TempMI.DurationUs = BVideoView.getDurationUs();
            TempMI.Title = BVideoView.getTitle();
            TempMI.VBitRateKb = BVideoView.getVBitRateKb();
            TempMI.FrameRate = BVideoView.getFrameRate();
            TempMI.SampleRate = BVideoView.getSampleRate();
            TempMI.Channels = BVideoView.getChannels();
            TempMI.ABitRateKb = BVideoView.getABitRateKb();
            TempMI.thumbnailBuf = BVideoView.getBytebuffer();
            TempMI.bitmap = BVideoView.getBitmap();
/**
            Log.e(TAG,"show "+"extension="+TempMI.Extension
                +"TempMI.width"+TempMI.width
                +"TempMI.height"+TempMI.height
                +"LongName="+TempMI.LongName
                +"TotBitRateKb"+TempMI.TotBitRateKb
                +"TempMI.FileSizeKb"+TempMI.FileSizeKb
                +"TempMI.DurationUs"+TempMI.DurationUs
                +"TempMI.VBitRateKb"+TempMI.VBitRateKb
                +"TempMI.FrameRate"+TempMI.FrameRate
                +"TempMI.SampleRate"+TempMI.SampleRate
                +"TempMI.Channels"+TempMI.Channels
                +"TempMI.ABitRateKb"+TempMI.ABitRateKb
                +"TempMI.thumbnailBuf"+TempMI.thumbnailBuf);
*/
        } else {
            Log.e(TAG, "getMediaInfo is null");
        }

        //general
        tExtension = (TextView)findViewById(R.id.extension);
        //tLongName = (TextView)findViewById(R.id.longname);
        tFileSizeKb = (TextView)findViewById(R.id.filesizekb);
        tDuration = (TextView)findViewById(R.id.time_total);
        //tTitle = (TextView)findViewById(R.id.filetitle);
        tTotBitRateKb = (TextView)findViewById(R.id.totbitratekb);

        //video
        tVideoCodecID = (TextView)findViewById(R.id.videocodecid);
        tWidth = (TextView)findViewById(R.id.width);
        tHeight = (TextView)findViewById(R.id.height);
        tVBitRateKb = (TextView)findViewById(R.id.vbitratekb);
        tFrameRate = (TextView)findViewById(R.id.framerate);

        //audio
        tAudioCodecID = (TextView)findViewById(R.id.audiocodecid);
        tSampleRate = (TextView)findViewById(R.id.samplerate);
        tChannels = (TextView)findViewById(R.id.channel);
        tABitRateKb = (TextView)findViewById(R.id.abitratekb);


        //subtitle
        //tCurrPostion = (TextView)findViewById(R.id.currposition);
    }

    private void displayMediaInfo(){

        String strTemp = null;
        //general
        strTemp = String.format("MEDIA_TYPE:%s", TempMI.Extension);
        tExtension.setText(strTemp);

        //strTemp = String.format("%s", TempMI.LongName);
        //tLongName.setText(strTemp);

        if (TempMI.FileSizeKb > 1024){
            int temp1 = TempMI.FileSizeKb/1024;
            int temp2 = TempMI.FileSizeKb%1024;
            strTemp = String.format("FILE_SIZE:%d.%d Mb", temp1,temp2);
        }else{
            strTemp = String.format("FILE_SIZE:%d kb", TempMI.FileSizeKb);
        }

        tFileSizeKb.setText(strTemp);

        updateTextViewWithTimeFormat(tDuration, TempMI.DurationUs);

        //strTemp = String.format("%s", TempMI.Title);
        //tTitle.setText(strTemp);

        strTemp = String.format("TOT_BITRATE:%d Kb/s", TempMI.TotBitRateKb);
        tTotBitRateKb.setText(strTemp);

        //video
        strTemp = String.format("VIDEO_CODEC:%s", TempMI.VideoName);
        tVideoCodecID.setText(strTemp);

        strTemp = String.format("VIDEO_WIDTH:%d pixel", TempMI.width);
        tWidth.setText(strTemp);

        strTemp = String.format("VIDEO_HEIGHT:%d pixel", TempMI.height);
        tHeight.setText(strTemp);

        strTemp = String.format("VIDEO_BITRATE:%dKb/s", TempMI.VBitRateKb);
        tVBitRateKb.setText(strTemp);

        strTemp = String.format("VIDEO_FRAMERATE:%2.1f f/s", TempMI.FrameRate);
        tFrameRate.setText(strTemp);;

        //audio
        strTemp = String.format("AUDIO_CODEC:%s", TempMI.AudioName);
        tAudioCodecID.setText(strTemp);

        strTemp = String.format("AUDIO_SAMPLE:=%d", TempMI.SampleRate);
        tSampleRate.setText(strTemp);

        strTemp = String.format("AUDIO_CHANNEL:=%d", TempMI.Channels);
        tChannels.setText(strTemp);

        strTemp = String.format("AUDIO_BITRATE:%d Kb/s", TempMI.ABitRateKb);
        tABitRateKb.setText(strTemp);

        //subtitle
        //tCurrPostion.setText(strTemp);
        if (colorFormat == RAW_YUV420 || colorFormat == RAW_RGB565){
            /*
            * this is to save yuv/BMP to a file.
            */    
            FileOutputStream os = null;
            try {
                File sdDir = null;
                String fileName;
                boolean sdCardExist = isExternalStorageWritable();
                if(sdCardExist){
                    sdDir = Environment.getExternalStorageDirectory();
                    fileName = new String(sdDir.toString()+"/");
                }else{

                    fileName = new String("/sdcard/");
                }

                if (TempMI.Extension != null)
                    fileName = fileName.concat(TempMI.Extension);
                fileName = fileName.concat(".");
                if (colorFormat == RAW_YUV420){
                    fileName = fileName.concat("yuv");
                } else if (colorFormat == RAW_RGB565 || colorFormat == BMP_OBJECT){
                    fileName = fileName.concat("bmp");
                }
                Log.v(TAG, "fileName="+fileName);
                os = new FileOutputStream(fileName, true);
                if (colorFormat == RAW_YUV420){
                    int count = TempMI.thumbnailBuf.capacity();
                    Log.v(TAG, "count="+count);
                    byte [] mPixel = new byte[count];
                    TempMI.thumbnailBuf.get(mPixel);
                    os.write(mPixel, 0, count);
                } else if (colorFormat == RAW_RGB565){
                    // to save as bmp file.
                    int count = TempMI.thumbnailBuf.capacity();
                    Log.v(TAG, "thumbnailBUf.capacity="+count);

                    int i;
                    int [] intValue = new int[thumbnailWidth * thumbnailHeight];
                    for (i =0; i<thumbnailWidth * thumbnailHeight;i++){
                        byte low = TempMI.thumbnailBuf.get();
                        byte high = TempMI.thumbnailBuf.get();
                        //Log.e(TAG, i+"low="+low +"high="+high);
                        int r = (high &0xf8)<<16;
                        int g = (((high &0x7 )<<3) |  ((low&0xe0)>>5))<<10;
                        int b = (low&0x1f)<<3;
                        intValue[i] =  r | g | b;
                    }

                    Bitmap bitmap = Bitmap.createBitmap(intValue, thumbnailWidth, thumbnailHeight, Bitmap.Config.RGB_565);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.flush();
                    os.close();
                }
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                if (os != null)
                    try {
                        os.close();
                    } catch (Throwable t) {
                    }
            }
        } else if (colorFormat == BMP_OBJECT){
            // to save as bmp file.
            if (TempMI.bitmap != null){
                mImageView.setImageBitmap(TempMI.bitmap);
                //TempMI.bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            }
        }

        Log.v(TAG, "*************************** write");

    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private void updateTextViewWithTimeFormat(TextView view, int second){
        int hh = second / 3600;
        int mm = second % 3600 / 60;
        int ss = second % 60;
        String strTemp = null;
        if (0 != hh) {
            strTemp = String.format("FILE_DURATION:%02dH:%02dM:%02dS", hh, mm, ss);
        } else {
            strTemp = String.format("FILE_DURATION:%02dM:%02dS", mm, ss);
        }
        view.setText(strTemp);
    }


    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Log.v(TAG, "onPause");
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.v(TAG, "onResume");
        //发起一次播放任务,当然您不一定要在这发起
        //mUIHandler.sendEmptyMessage(UI_EVENT_GET);
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "onStop");
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.v(TAG, "onStart");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.v(TAG, "onDestroy");
    }
}