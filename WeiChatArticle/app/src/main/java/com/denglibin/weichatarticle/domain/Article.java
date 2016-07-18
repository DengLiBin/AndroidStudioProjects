package com.denglibin.weichatarticle.domain;

/**
 * Created by DengLibin on 2016/5/4 0004.
 */
public class Article {
    public String firstImg;
    public String id;
    public String sources;
    public String title;
    public String url;
    public Article(String firstImg, String id, String sources, String title, String url) {
        this.firstImg = firstImg;
        this.id = id;
        this.sources = sources;
        this.title = title;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
