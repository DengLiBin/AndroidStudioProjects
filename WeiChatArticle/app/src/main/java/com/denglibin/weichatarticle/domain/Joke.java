package com.denglibin.weichatarticle.domain;

/**
 * Created by DengLibin on 2016/5/7.
 */
public class Joke {
    public String contentJok;
    public String uptime;

    public Joke(String contentJok, String uptime) {
        this.contentJok = contentJok;
        this.uptime = uptime;
    }

    @Override
    public String toString() {
        return "Joke{" +
                "contentJok='" + contentJok + '\'' +
                ", uptime='" + uptime + '\'' +
                '}';
    }
}
