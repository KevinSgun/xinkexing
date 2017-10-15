package com.thinkeract.tka.data.api.response;

import java.util.List;

/**
 * Created by ymh on 2017/10/15 15:58
 * e-mail:minhengyan@gmail.com
 */

public class CheckResultData {

    /**
     * grade : 90
     * items : [{"normalRange":"20-50","isCheck":1,"score":40,"name":"心跳","id":137},{"normalRange":"80-110","isCheck":0,"score":0,"name":"脉搏跳动","id":138}]
     */

    private int grade;
    private List<ItemsBean> items;

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * normalRange : 20-50
         * isCheck : 1
         * score : 40
         * name : 心跳
         * id : 137
         */

        private String normalRange;
        private int isCheck;
        private int score;
        private String name;
        private int id;

        public String getNormalRange() {
            return normalRange;
        }

        public void setNormalRange(String normalRange) {
            this.normalRange = normalRange;
        }

        public int getIsCheck() {
            return isCheck;
        }

        public void setIsCheck(int isCheck) {
            this.isCheck = isCheck;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
