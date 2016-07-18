package com.denglibin.weichatarticle.net;

/**
 * Created by DengLibin on 2016/5/7.
 */
public class GetJokeJson {
    public GetJokeJson(String url,final GetJokeJson.SuccessCallback successCallback,final GetJokeJson.FailCallback failCallback){

        new NetConnection(url, HttpMethod.GET, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                successCallback.onSuccess(result);
            }
        },
        new NetConnection.FailCallback(){
            @Override
            public void onFail() {
                failCallback.onFail();
            }
        });
    }

    public static interface SuccessCallback{
        void onSuccess(String result);
    }
    public static interface FailCallback{
        void onFail();
    }
}
