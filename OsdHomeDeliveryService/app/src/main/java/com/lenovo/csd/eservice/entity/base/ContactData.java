package com.lenovo.csd.eservice.entity.base;

import java.util.ArrayList;

/**
 * Created by 彤 on 2016/10/13.
 */
public class ContactData extends BaseData {
    private ArrayList<ContactItemData> data;

    public ArrayList<ContactItemData> getData() {
        return data;
    }

    public void setData(ArrayList<ContactItemData> data) {
        this.data = data;
    }

    public class ContactItemData extends TypeData {
        private String consult_type;
        private String create_by;
        private String create_time;
        private String cus_name;
        private String customer_id;
        private String item_states;
        private String last_sln;
        private String quest_phenomenon;
        private String remark;
        private String sr_item_id;

        public String getConsult_type() {
            return consult_type;
        }

        public void setConsult_type(String consult_type) {
            this.consult_type = consult_type;
        }

        public String getCreate_by() {
            return create_by;
        }

        public void setCreate_by(String create_by) {
            this.create_by = create_by;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCus_name() {
            return cus_name;
        }

        public void setCus_name(String cus_name) {
            this.cus_name = cus_name;
        }

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }

        public String getItem_states() {
            return item_states;
        }

        public void setItem_states(String item_states) {
            this.item_states = item_states;
        }

        public String getLast_sln() {
            return last_sln;
        }

        public void setLast_sln(String last_sln) {
            this.last_sln = last_sln;
        }

        public String getQuest_phenomenon() {
            return quest_phenomenon;
        }

        public void setQuest_phenomenon(String quest_phenomenon) {
            this.quest_phenomenon = quest_phenomenon;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getSr_item_id() {
            return sr_item_id;
        }

        public void setSr_item_id(String sr_item_id) {
            this.sr_item_id = sr_item_id;
        }

        @Override
        public int getType() {
            return 2;
        }
    }

    public class MoreCustomTitle extends TypeData {
        private String title;

        public MoreCustomTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public int getType() {
            return 1;
        }
    }
}
