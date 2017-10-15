package com.thinkeract.tka.data.api.response;

import com.thinkeract.tka.data.api.entity.NewsItem;

import java.util.List;

/**
 * Created by minHeng on 2017/4/12 16:31.
 * mail:minhengyan@gmail.com
 */

public class HomePageData {

    private List<ScoreBean> score;
    private List<NewsItem> news;
    private int sumScore;

    public List<ScoreBean> getScore() {
        return score;
    }

    public void setScore(List<ScoreBean> score) {
        this.score = score;
    }

    public List<NewsItem> getNews() {
        return news;
    }

    public void setNews(List<NewsItem> news) {
        this.news = news;
    }

    public static class ScoreBean {
        /**
         * id : 13
         * name : 脏器类
         * maxScore : 70
         * score : 56
         */

        private int id;
        private String name;
        private int maxScore;
        private int score;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMaxScore() {
            return maxScore;
        }

        public void setMaxScore(int maxScore) {
            this.maxScore = maxScore;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }

    public int getSumScore() {
        return sumScore;
    }

    public void setSumScore(int sumScore) {
        this.sumScore = sumScore;
    }
}
