package com.lenovo.csd.eservice.entity.base;

import java.util.ArrayList;

/**
 * Created by 彤 on 2016/9/7.
 */
public class ChangeRecordData extends BaseData {
    private RecordBean data;

    public RecordBean getData() {
        return data;
    }

    public void setData(RecordBean data) {
        this.data = data;
    }

    public class RecordBean {
        private int total;
        private ArrayList<RecordItem> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public ArrayList<RecordItem> getList() {
            return list;
        }

        public void setList(ArrayList<RecordItem> list) {
            this.list = list;
        }
    }

    public class RecordItem {
        private String id;
        private String create_time;
        private String esd_change_code;
        private String status_desc;
        private String change_type;
        private String change_code;
        private String type;
        private String machine_down_code;
        private String machine_down_desc;
        private String machine_down_sn;
        private String machine_up_code;
        private String machine_up_desc;
        private String machine_up_sn;
        private String action_desc;
        private String sp_type;
        private String sp_desc;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getEsd_change_code() {
            return esd_change_code;
        }

        public void setEsd_change_code(String esd_change_code) {
            this.esd_change_code = esd_change_code;
        }

        public String getStatus_desc() {
            return status_desc;
        }

        public void setStatus_desc(String status_desc) {
            this.status_desc = status_desc;
        }

        public String getChange_type() {
            return change_type;
        }

        public void setChange_type(String change_type) {
            this.change_type = change_type;
        }

        public String getMachine_down_code() {
            return machine_down_code;
        }

        public void setMachine_down_code(String machine_down_code) {
            this.machine_down_code = machine_down_code;
        }

        public String getMachine_down_desc() {
            return machine_down_desc;
        }

        public void setMachine_down_desc(String machine_down_desc) {
            this.machine_down_desc = machine_down_desc;
        }

        public String getMachine_down_sn() {
            return machine_down_sn;
        }

        public void setMachine_down_sn(String machine_down_sn) {
            this.machine_down_sn = machine_down_sn;
        }

        public String getMachine_up_code() {
            return machine_up_code;
        }

        public void setMachine_up_code(String machine_up_code) {
            this.machine_up_code = machine_up_code;
        }

        public String getMachine_up_desc() {
            return machine_up_desc;
        }

        public void setMachine_up_desc(String machine_up_desc) {
            this.machine_up_desc = machine_up_desc;
        }

        public String getMachine_up_sn() {
            return machine_up_sn;
        }

        public void setMachine_up_sn(String machine_up_sn) {
            this.machine_up_sn = machine_up_sn;
        }

        public String getChange_code() {
            return change_code;
        }

        public void setChange_code(String change_code) {
            this.change_code = change_code;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAction_desc() {
            return action_desc;
        }

        public void setAction_desc(String action_desc) {
            this.action_desc = action_desc;
        }

        public String getSp_type() {
            return sp_type;
        }

        public void setSp_type(String sp_type) {
            this.sp_type = sp_type;
        }

        public String getSp_desc() {
            return sp_desc;
        }

        public void setSp_desc(String sp_desc) {
            this.sp_desc = sp_desc;
        }
    }

}
