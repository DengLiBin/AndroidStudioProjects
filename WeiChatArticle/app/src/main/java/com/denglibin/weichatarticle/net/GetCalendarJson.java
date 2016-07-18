package com.denglibin.weichatarticle.net;

/**
 * Created by DengLibin on 2016/5/8.
 */
public class GetCalendarJson {
    public GetCalendarJson(String url, final GetCalendarJson.SuccessCallback successCallback, final GetCalendarJson.FailCallback failCallback){
        new NetConnection(url, HttpMethod.GET, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                successCallback.onSuccess(result);
            }
        }, new NetConnection.FailCallback() {
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
