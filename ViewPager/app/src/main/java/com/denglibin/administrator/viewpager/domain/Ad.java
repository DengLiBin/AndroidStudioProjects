package com.denglibin.administrator.viewpager.domain;

/**
 * Created by Administrator on 2016/4/22.
 */
public class Ad {
    private int image_id;
    private String title;

    public Ad(int image_id, String title) {
        this.image_id = image_id;
        this.title = title;
    }

    public int getImage_id() {
        return image_id;
    }

    public String getTitle() {
        return title;
    }
}
