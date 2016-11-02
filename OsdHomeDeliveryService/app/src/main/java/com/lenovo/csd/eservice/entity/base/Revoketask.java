package com.lenovo.csd.eservice.entity.base;

/**
 * Created by hankw on 16-8-15.
 */
public class Revoketask extends BaseData {
    private RevokeData data;


    public RevokeData getData() {
        return data;
    }

    public class RevokeData {
        private String result_msg;
        private String warning_msg;


        public String getResult_msg() {
            return result_msg;
        }

        public String getWarning_msg() {
            return warning_msg;
        }
    }
}
