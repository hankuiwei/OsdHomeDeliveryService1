package com.lenovo.csd.eservice.entity.base;

import java.util.List;

/**
 * Created by hankw on 16-7-22.
 */
public class MyCommentList extends BaseData{

    private String status;
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public class Data{
        private String userimg;
        private int score;
        private String[] lables;
        private String content;

        public String getUserimg() {
            return userimg;
        }

        public void setUserimg(String userimg) {
            this.userimg = userimg;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String[] getLables() {
            return lables;
        }

        public void setLables(String[] lables) {
            this.lables = lables;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }



}
