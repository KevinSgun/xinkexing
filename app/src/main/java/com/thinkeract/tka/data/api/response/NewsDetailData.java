package com.thinkeract.tka.data.api.response;

import com.thinkeract.tka.data.api.entity.NewsItem;

import java.util.List;

/**
 * Created by ymh on 2017/4/9 10:42
 * e-mail:minhengyan@gmail.com
 */

public class NewsDetailData {

    /**
     * news : {"visits":2,"descr":"<p>你头部的治疗傻逼药吃了吗？你头部的治疗傻逼药吃了吗？你头部的治疗傻逼药吃了吗？你头部的治疗傻逼药吃了吗？<\/p>\n","date":"2017-04-01 10:58:31","title":"你头部的治疗傻逼药吃了吗？","cover":"http://120.24.95.8:11111/upload/20170408//head/1491641327967.jpg","subTitle":"你头部的治疗傻逼药吃了吗？","id":13}
     */

    private NewsHead news;

    private List<NewsItem> commendList;

    private NewsItem newsItem;

    public NewsHead getNews() {
        return news;
    }

    public void setNews(NewsHead news) {
        this.news = news;
    }

    public List<NewsItem> getCommendList() {
        return commendList;
    }

    public void setCommendList(List<NewsItem> commendList) {
        this.commendList = commendList;
    }

    public NewsItem getNewsItem() {
        return newsItem;
    }

    public void setNewsItem(NewsItem newsItem) {
        this.newsItem = newsItem;
    }

    public static class NewsHead {
        /**
         * visits : 2
         * descr : <p>你头部的治疗傻逼药吃了吗？你头部的治疗傻逼药吃了吗？你头部的治疗傻逼药吃了吗？你头部的治疗傻逼药吃了吗？</p>

         * date : 2017-04-01 10:58:31
         * title : 你头部的治疗傻逼药吃了吗？
         * cover : http://120.24.95.8:11111/upload/20170408//head/1491641327967.jpg
         * subTitle : 你头部的治疗傻逼药吃了吗？
         * id : 13
         */

        private int visits;
        private String descr;
        private String date;
        private String title;
        private String cover;
        private String subTitle;
        private int id;

        public int getVisits() {
            return visits;
        }

        public void setVisits(int visits) {
            this.visits = visits;
        }

        public String getDescr() {
            return descr;
        }

        public void setDescr(String descr) {
            this.descr = descr;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
