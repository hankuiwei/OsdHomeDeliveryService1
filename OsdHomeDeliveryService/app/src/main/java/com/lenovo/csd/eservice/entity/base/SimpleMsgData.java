package com.lenovo.csd.eservice.entity.base;

/**
 * Created by 彤 on 2016/8/11.
 */
public class SimpleMsgData extends BaseData {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private String result_msg;
        private String warning_msg;

        public String getResult_msg() {
            return result_msg;
        }

        public void setResult_msg(String result_msg) {
            this.result_msg = result_msg;
        }

        public String getWarning_msg() {
            return warning_msg;
        }

        public void setWarning_msg(String warning_msg) {
            this.warning_msg = warning_msg;
        }
    }
}
