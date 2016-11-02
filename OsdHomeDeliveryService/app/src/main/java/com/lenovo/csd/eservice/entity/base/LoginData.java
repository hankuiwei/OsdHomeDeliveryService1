package com.lenovo.csd.eservice.entity.base;

/**
 * Created by shengtao
 * Date:2016/7/21
 * Time:11:03
 */
public class LoginData extends BaseData {
    public Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        public String user_code;
        public String user_name;
        public String user_mobile;
        public String station_name;

        public String position_name;
        public String qrcode_image;
        public String head_image;
        public String set_time;

        public String token;

        public String getUser_code() {
            return user_code;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getUser_mobile() {
            return user_mobile;
        }

        public String getStation_name() {
            return station_name;
        }

        public String getPosition_name() {
            return position_name;
        }

        public String getQrcode_image() {
            return qrcode_image;
        }

        public String getHead_image() {
            return head_image;
        }

        public String getSet_time() {
            return set_time;
        }

        public String getToken() {
            return token;
        }
    }

}

