package com.denglibin.topnews.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/26.
 */
public class News {
    /** 新闻类别 ID */
    private Integer newsCategoryId;
    /** 新闻类型 */
    private String newsCategory;
    /** 标记状态，如推荐之类的 */
    private Integer mark;;
    /** 评论数量 */
    private Integer commentNum;
    /** ID */
    private Integer id;
    /** 新闻ID */
    private Integer newsId;
    /** 标题 */
    private String title;
    /** 新闻源 */
    private String source;
    /** 发布时间 */
    private Long publishTime;
    /** 总结 */
    private String summary;
    /** 摘要 */
    private String newsAbstract;
    /** 评论 */
    private String comment;
    /** 特殊标签，如广告推广之类的 ，可以为空 */
    private String local;
    /** 图片列表字符串 */
    private String picListString;
    /** 图片1 URL */
    private String picOne;
    /** 图片2 URL */
    private String picTwo;
    /** 图片3 URL */
    private String picThr;
    /** 图片 列表 */
    private List<String> picList;
    /** 图片类型是否为大图 */
    private Boolean isLarge;
    /** 阅读状态 ，读过的话显示灰色背景 */
    private Boolean readStatus;
    /** 收藏状态 */
    private Boolean collectStatus;
    /** 喜欢 状态 */
    private Boolean likeStatus;
    /** 感兴趣状态 */
    private Boolean interestedStatus;

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setNewsCategoryId(Integer newsCategoryId) {
        this.newsCategoryId = newsCategoryId;
    }

    public void setNewsCategory(String newsCategory) {
        this.newsCategory = newsCategory;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setPublishTime(Long publishTime) {
        this.publishTime = publishTime;
    }

    public void setNewsAbstract(String newsAbstract) {
        this.newsAbstract = newsAbstract;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setPicListString(String picListString) {
        this.picListString = picListString;
    }

    public void setPicOne(String picOne) {
        this.picOne = picOne;
    }

    public void setPicTwo(String picTwo) {
        this.picTwo = picTwo;
    }

    public void setPicThr(String picThr) {
        this.picThr = picThr;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }

    public void setIsLarge(Boolean large) {
        isLarge = large;
    }

    public void setReadStatus(Boolean readStatus) {
        this.readStatus = readStatus;
    }

    public void setCollectStatus(Boolean collectStatus) {
        this.collectStatus = collectStatus;
    }

    public void setLikeStatus(Boolean likeStatus) {
        this.likeStatus = likeStatus;
    }

    public void setInterestedStatus(Boolean interestedStatus) {
        this.interestedStatus = interestedStatus;
    }

    public Integer getNewsCategoryId() {
        return newsCategoryId;
    }

    public String getNewsCategory() {
        return newsCategory;
    }

    public Integer getMark() {
        return mark;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public Integer getId() {
        return id;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public Long getPublishTime() {
        return publishTime;
    }

    public String getSummary() {
        return summary;
    }

    public String getNewsAbstract() {
        return newsAbstract;
    }

    public String getComment() {
        return comment;
    }

    public String getLocal() {
        return local;
    }

    public String getPicListString() {
        return picListString;
    }

    public String getPicOne() {
        return picOne;
    }

    public String getPicTwo() {
        return picTwo;
    }

    public String getPicThr() {
        return picThr;
    }

    public List<String> getPicList() {
        return picList;
    }

    public Boolean getIsLarge() {
        return isLarge;
    }

    public Boolean getReadStatus() {
        return readStatus;
    }

    public Boolean getCollectStatus() {
        return collectStatus;
    }

    public Boolean getLikeStatus() {
        return likeStatus;
    }

    public Boolean getInterestedStatus() {
        return interestedStatus;
    }
}
