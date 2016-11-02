package com.lenovo.csd.eservice.entity.base;

import java.util.List;

/**
 * Created by hankw on 16-8-24.
 */
public class ChangeBoxsInfo extends BaseData{

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data extends BaseData{
        private List<BoxList> box_list;

        public List<BoxList> getBoxList() {
            return box_list;
        }

        public void setBoxList(List<BoxList> box_list) {
            this.box_list = box_list;
        }
    }

    public class  BoxList extends BaseData{
        private String id;
        private String desc;
        private String code;
        private String sn;
        private String type;
        private String price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
