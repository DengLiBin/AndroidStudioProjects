package com.denglibin.weichatarticle.domain;

/**
 * Created by DengLibin on 2016/5/8.
 */
public class CalenData {
    private String date;
    private String week;
    private String lundar;
    private String lundaryear;
    private String animal;
    private String holiday;
    private String desc;
    private String suit;
    private String avoid;
    public CalenData(String date, String week, String lundar, String lundaryear, String animal, String holiday, String desc, String suit, String avoid) {
        this.date = date;
        this.week = week;
        this.lundar = lundar;
        this.lundaryear = lundaryear;
        this.animal = animal;
        this.holiday = holiday;
        this.desc = desc;
        this.suit = suit;
        this.avoid = avoid;
    }

    public String getDate() {
        return date;
    }

    public String getWeek() {
        return week;
    }

    public String getLundar() {
        return lundar;
    }

    public String getLundaryear() {
        return lundaryear;
    }

    public String getAnimal() {
        return animal;
    }

    public String getHoliday() {
        return holiday;
    }

    public String getDesc() {
        return desc;
    }

    public String getSuit() {
        return suit;
    }

    public String getAvoid() {
        return avoid;
    }
}
