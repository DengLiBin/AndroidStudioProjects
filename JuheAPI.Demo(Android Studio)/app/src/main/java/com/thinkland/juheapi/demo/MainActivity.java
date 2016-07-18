package com.thinkland.juheapi.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

public class MainActivity extends Activity {

	private Context mContext;
	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		tv = (TextView) findViewById(R.id.tv);

		/**
		 * 请不要添加key参数.
		 */
		Parameters params = new Parameters();
		params.add("ip", "www.juhe.cn");
		params.add("dtype", "xml");
		/**
		 * 请求的方法 参数: 第一个参数 当前请求的context;
		 * 				  第二个参数 接口id;
		 * 				  第三个参数 接口请求的url;
		 * 				  第四个参数 接口请求的方式;
		 * 				  第五个参数 接口请求的参数,键值对com.thinkland.sdk.android.Parameters类型;
		 * 				  第六个参数请求的回调方法,com.thinkland.sdk.android.DataCallBack;
		 * 
		 */
		JuheData.executeWithAPI(mContext, 1, "http://apis.juhe.cn/ip/ip2addr",
				JuheData.GET, params, new DataCallBack() {
					/**
					 * 请求成功时调用的方法 statusCode为http状态码,responseString为请求返回数据.
					 */
					@Override
					public void onSuccess(int statusCode, String responseString) {
						// TODO Auto-generated method stub
						tv.append(responseString + "\n");
					}

					/**
					 * 请求完成时调用的方法,无论成功或者失败都会调用.
					 */
					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "finish",
								Toast.LENGTH_SHORT).show();
					}

					/**
					 * 请求失败时调用的方法,statusCode为http状态码,throwable为捕获到的异常
					 * statusCode:30002 没有检测到当前网络.
					 * 			  30003 没有进行初始化.
					 * 			  0 未明异常,具体查看Throwable信息.
					 * 			  其他异常请参照http状态码.
					 */
					@Override
					public void onFailure(int statusCode,
							String responseString, Throwable throwable) {
						// TODO Auto-generated method stub
						tv.append(throwable.getMessage() + "\n");
					}
				});

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		/**
		 * 关闭当前页面正在进行中的请求.
		 */
		JuheData.cancelRequests(mContext);
	}

}
