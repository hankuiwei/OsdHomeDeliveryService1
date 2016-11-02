package com.lenovo.csd.eservice.entity.base;

import java.io.Serializable;

/**
 * Created by hankw on 16-7-22.
 */
public class MyComment implements Serializable{

    private int status;
    private int total;
    private String message;
    private Comment data;

    public Comment getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public int getTotal() {
        return total;
    }

    public String getMessage() {
        return message;
    }

    public class Comment{
        private int allcount;//点评总数
        private int goodcount;//好评数
        private int  commcount;//中评数
        /*private String allcount;//点评总数
        private String goodcount;//好评数
        private String  commcount;//中评数*/
        private String badcount;//差评数
        private String invatecount;//邀评数


        public int getAllcount() {
            return allcount;
        }

        public int getGoodcount() {
            return goodcount;
        }

        public int getCommcount() {
            return commcount;
        }


        public String getBadcount() {
            return badcount;
        }

        public String getInvatecount() {
            return invatecount;
        }
    }

}
