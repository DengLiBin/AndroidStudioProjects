package com.denglibin.topnews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.denglibin.topnews.adapter.PagerFragmentAdapter;
import com.denglibin.topnews.bean.NewsClassify;
import com.denglibin.topnews.fragment.NewsFragment;
import com.denglibin.topnews.utils.DesityUtils;
import com.denglibin.topnews.utils.GetJsonStringUtils;
import com.denglibin.topnews.utils.ToastUtils;
import com.denglibin.topnews.view.ColumHorizontalScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ColumHorizontalScrollView m_columHorizontalScrollView;
    private LinearLayout m_RadioGroup_content;
    LinearLayout ll_more_columns;
    RelativeLayout rl_column;
    private List<NewsClassify> newsClassfy = new ArrayList<NewsClassify>();
    private ViewPager m_ViewPager;
    private List<Fragment> fragments;
    /**
     * 当前选中的栏目
     */
    private int columnSelectIndex = 0;
    /**
     * 屏幕宽度
     */
    private int mScreenWidth = 0;
    /**
     * Item宽度
     */
    private int mItemWidth = 0;
    /**
     * 左阴影部分
     */
    public ImageView shade_left;
    /**
     * 右阴影部分
     */
    public ImageView shade_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mScreenWidth = DesityUtils.getWindowsWidth(this);//获取屏幕的宽度
        mItemWidth = mScreenWidth / 7;// 一个Item宽度为屏幕的1/7
        initView();
        initData();
        initTabColumn();
        selectTab(0);//将第0个tab置为选中状态
        initListener();
    }

    private void initView() {
        m_columHorizontalScrollView = (ColumHorizontalScrollView) findViewById(R.id.mColumHorizotalScrollView);
        m_RadioGroup_content = (LinearLayout) findViewById(R.id.mRadioGroup_content);
        shade_left = (ImageView) findViewById(R.id.shade_left);
        shade_right = (ImageView) findViewById(R.id.shade_right);
        ll_more_columns = (LinearLayout) findViewById(R.id.ll_more_columns);
        rl_column = (RelativeLayout) findViewById(R.id.rl_column);
        m_ViewPager = (ViewPager) findViewById(R.id.vp_content);
    }

    private void initData() {
        String json = GetJsonStringUtils.getJson(this);
        pareJson(json);
        initFragment();
    }

    /**
     * 初始化Column栏目项
     */
    private void initTabColumn() {
        m_RadioGroup_content.removeAllViews();
        int count = newsClassfy.size();
        m_columHorizontalScrollView.setParam(this, mScreenWidth, m_RadioGroup_content, shade_left, shade_right, ll_more_columns, rl_column);

        for (int i = 0; i < count; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mItemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
            TextView columnTextView = new TextView(this);
            columnTextView.setTextSize(16);
            columnTextView.setBackgroundResource(R.drawable.radio_button_bg);
            columnTextView.setGravity(Gravity.CENTER);
            columnTextView.setPadding(5, 5, 5, 5);
            columnTextView.setId(i);//设置id就为它在集合中的index
            columnTextView.setText(newsClassfy.get(i).getTitle());
            columnTextView.setTextColor(getResources().getColorStateList(R.color.top_category_scroll_view_text_normal_day));

            if (columnSelectIndex == i) {
                columnTextView.setSelected(true);
            }
            columnTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击某个textview时，遍历一次所有的textview找到当前点击的那个
                    for (int i = 0; i < m_RadioGroup_content.getChildCount(); i++) {
                        View localView = m_RadioGroup_content.getChildAt(i);
                        if (localView != v) {
                            localView.setSelected(false);
                        } else {
                            localView.setSelected(true);
                            m_ViewPager.setCurrentItem(i);
                        }
                    }
                    ToastUtils.showShort(MainActivity.this, newsClassfy.get(v.getId()).getTitle());
                }
            });
            m_RadioGroup_content.addView(columnTextView, i, params);//将viwe添加进来
        }
    }

    /**
     * 将目标position的tab置为选中状态
     *
     * @param position
     */
    private void selectTab(int position) {
        int count = m_RadioGroup_content.getChildCount();
        for (int i = 0; i < count; i++) {
            View checkView = m_RadioGroup_content.getChildAt(position);
            int k = checkView.getMeasuredWidth();
            int l = checkView.getLeft();
            int i2 = l + k / 2 - mScreenWidth / 2;
            // rg_nav_content.getParent()).smoothScrollTo(i2, 0);
            m_columHorizontalScrollView.smoothScrollTo(i2, 0);
        }
        for (int j = 0; j < count; j++) {
            if (j != position) {
                m_RadioGroup_content.getChildAt(j).setSelected(false);
            } else {
                m_RadioGroup_content.getChildAt(j).setSelected(true);
            }
        }
    }

    private void pareJson(String json) {
        try {
            System.out.println("json:" + json);
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("NewsTab");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object1 = array.getJSONObject(i);
                String title = object1.getString("title");
                newsClassfy.add(new NewsClassify(title));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        fragments = new ArrayList<Fragment>();
        for (int i = 0; i < newsClassfy.size(); i++) {
            Bundle data = new Bundle();
            data.putString("text", newsClassfy.get(i).getTitle());
            NewsFragment newsFragment = new NewsFragment();
            newsFragment.setArguments(data);
            fragments.add(newsFragment);
        }
        m_ViewPager.setAdapter(new PagerFragmentAdapter(getSupportFragmentManager(), fragments));
    }
    private void initListener(){
        m_ViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                m_ViewPager.setCurrentItem(position);
                selectTab(position);//将对应的tab设置为选中状态
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
