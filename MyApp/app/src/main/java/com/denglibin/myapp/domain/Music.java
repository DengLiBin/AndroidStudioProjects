package com.denglibin.myapp.domain;

/**
 * Created by Administrator on 2016/4/30.
 */
public class Music  {
    private String title;
    private String artist;
    private String path;

    public Music(String title,String artist, String path) {
        this.artist = artist;
        this.path = path;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getPath() {
        return path;
    }
}
