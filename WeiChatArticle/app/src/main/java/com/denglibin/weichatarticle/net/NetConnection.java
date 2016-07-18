package com.denglibin.weichatarticle.net;

import android.os.AsyncTask;

import com.denglibin.weichatarticle.common.Constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * Created by DengLibin on 2016/4/7 0007.
 */
public class NetConnection {
    public NetConnection(final String url, final HttpMethod method, final SuccessCallback successCallback,
                         final FailCallback failCallback){

        /**
         *第一个泛型：参数类型； 第二个泛型：更新进度； 第三个泛型：doInBackground方法返回结果的数据类型
         *
         */
        new AsyncTask<Void,Void,String>(){
            //子线程中进行
            @Override
            protected String doInBackground(Void... params) {

                try {
                    URLConnection uc;//可以是post和get
                    uc=new URL(url).openConnection();//用get方式
                    BufferedReader br=new BufferedReader(new InputStreamReader(uc.getInputStream(), Constant.CHARSET));//来自服务器的输入流
                    StringBuilder result=new StringBuilder();
                    String line=null;
                    while ((line=br.readLine())!=null){
                        result.append(line);
                    }
                    return result.toString();//返回从服务器获取的字符串

                }catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            //该方法在doInbackground方法执行完后返回的数据作为参数传入该方法再执行，在主线程中
            @Override
            protected void onPostExecute(String result) {
                if(result!=null){
                    if(successCallback!=null){
                        successCallback.onSuccess(result);
                    }
                }else{
                    if(failCallback!=null){
                        failCallback.onFail();
                    }
                }
            }
        }.execute();//不要忘了执行
    }
    public static interface SuccessCallback{
        void onSuccess(String result);
    }
    public static interface FailCallback{
        void onFail();
    }
}
