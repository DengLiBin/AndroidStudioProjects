package com.denglibin.weichatarticle.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denglibin.weichatarticle.MainActivity;
import com.denglibin.weichatarticle.R;
import com.denglibin.weichatarticle.domain.CalenData;
import com.denglibin.weichatarticle.net.GetCalendarJson;
import com.denglibin.weichatarticle.utils.GetCalendar;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DengLibin on 2016/5/8.
 */
public class CalendarFragment extends Fragment {
    private MainActivity mainActivity;
    private TextView tv_date,tv_weekday,tv_lundar,tv_lundarYear,tv_animal,
                    tv_holiday,tv_desc,tv_suit,tv_avoid;
    private String date=GetCalendar.getCalendar();
    private String url="http://japi.juhe.cn/calendar/day?date="+date+"&key=6db0e4500c5818958e60314e3b634547";
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            CalenData data= (CalenData) msg.obj;
            fillData(data);
            super.handleMessage(msg);
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainActivity= (MainActivity) container.getContext();
        View view=LayoutInflater.from(mainActivity).inflate(R.layout.fragment_calendar,null);
        initView(view);
        getData(url);
        return view;
    }

    //拿到控件对象,onCreateView中调用
    private void initView(View root){
        tv_date= (TextView) root.findViewById(R.id.tv_date);
        tv_weekday= (TextView) root.findViewById(R.id.tv_weekday);
        tv_lundar= (TextView) root.findViewById(R.id.tv_lunar);
        tv_lundarYear= (TextView) root.findViewById(R.id.tv_lunarYear);
        tv_animal= (TextView) root.findViewById(R.id.tv_animal);
        tv_holiday= (TextView) root.findViewById(R.id.tv_holiday);
        tv_desc= (TextView) root.findViewById(R.id.tv_desc);
        tv_suit= (TextView) root.findViewById(R.id.tv_suit);
        tv_avoid= (TextView) root.findViewById(R.id.tv_avoid);
    }

    private void getData(String url){
        new GetCalendarJson(url,new GetCalendarJson.SuccessCallback(){
            @Override
            public void onSuccess(String result) {
                System.out.println("日历数据"+result);
                Message msg=Message.obtain();
                msg.obj= parseCalendarJson(result);
                handler.sendMessage(msg);
            }
        },new GetCalendarJson.FailCallback(){
            @Override
            public void onFail() {

            }
        });
    }

    private CalenData parseCalendarJson(String json){
        try {
            JSONObject object=new JSONObject(json);
            JSONObject result=object.getJSONObject("result");
            JSONObject data=result.getJSONObject("data");
            String date=data.getString("date");
            String week=data.getString("weekday");
            String lundar=data.getString("lunar");
            String lundaryear=data.getString("lunarYear");
            String animal=data.getString("animalsYear");
            String holiday="";
            String desc="";
            try{
                holiday=data.getString("holiday");
                desc=data.getString("desc");
            }catch (Exception e){
                System.out.println("今天不是节日");
            }

            String suit=data.getString("suit");
            String avoid=data.getString("avoid");
           return new CalenData(date,week,lundar,lundaryear,animal,holiday,desc,suit,avoid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private  void fillData(CalenData data){
        tv_date.setText("公历: "+data.getDate());
        tv_weekday.setText("星期: "+data.getWeek());
        tv_lundar.setText("农历: "+data.getLundar());
        tv_lundarYear.setText("年份: "+data.getLundaryear());
        tv_animal.setText("属相: "+data.getAnimal());
        tv_holiday.setText("节日: "+data.getHoliday());
        tv_desc.setText("节日描述: "+data.getDesc());

        //判断是不是节日，不是的话就隐藏该控件
        if(TextUtils.isEmpty(data.getHoliday())){
            tv_holiday.setVisibility(View.GONE);
            tv_desc.setVisibility(View.GONE);
        }
        tv_suit.setText("宜: "+data.getSuit());
        tv_avoid.setText("忌: "+data.getAvoid());
    }
}
