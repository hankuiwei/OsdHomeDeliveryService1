package com.lenovo.csd.eservice.entity.base;

import java.util.ArrayList;

/**
 * Created by 彤 on 2016/8/17.
 */
public class UnusedPartData extends BaseData {
    private UnusedPartBean data;

    public UnusedPartBean getBean() {
        return data;
    }

    public void setBean(UnusedPartBean data) {
        this.data = data;
    }

    public class UnusedPartBean {
        private String page_count;
        private String page_index;
        private String page_size;
        private ArrayList<UnusedPartItemData> borrow_order_info;

        public String getPage_count() {
            return page_count;
        }

        public void setPage_count(String page_count) {
            this.page_count = page_count;
        }

        public String getPage_index() {
            return page_index;
        }

        public void setPage_index(String page_index) {
            this.page_index = page_index;
        }

        public String getPage_size() {
            return page_size;
        }

        public void setPage_size(String page_size) {
            this.page_size = page_size;
        }

        public ArrayList<UnusedPartItemData> getBorrow_order_info() {
            return borrow_order_info;
        }

        public void setBorrow_order_info(ArrayList<UnusedPartItemData> borrow_order_info) {
            this.borrow_order_info = borrow_order_info;
        }
    }

    public class UnusedPartItemData {
        private long bid;
        private String branch_id;
        private String create_by;
        private String create_time;
        private String engineer_code;
        private String mark_status;
        private String material_class_name;
        private String material_name;
        private String material_no;
        private String pr_code;
        private String pr_status;
        private String so_id;
        private String sp_sn;
        private String store_area_name;
        private String yakuan_price;
        private String down_spsn;
        private String down_material_no;
        private String down_yakuan_price;
        private String down_spmark_desc;


        public long getBid() {
            return bid;
        }

        public void setBid(long bid) {
            this.bid = bid;
        }

        public String getBranch_id() {
            return branch_id;
        }

        public void setBranch_id(String branch_id) {
            this.branch_id = branch_id;
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

        public String getEngineer_code() {
            return engineer_code;
        }

        public void setEngineer_code(String engineer_code) {
            this.engineer_code = engineer_code;
        }

        public String getMark_status() {
            return mark_status;
        }

        public void setMark_status(String mark_status) {
            this.mark_status = mark_status;
        }

        public String getMaterial_class_name() {
            return material_class_name;
        }

        public void setMaterial_class_name(String material_class_name) {
            this.material_class_name = material_class_name;
        }

        public String getMaterial_name() {
            return material_name;
        }

        public void setMaterial_name(String material_name) {
            this.material_name = material_name;
        }

        public String getMaterial_no() {
            return material_no;
        }

        public void setMaterial_no(String material_no) {
            this.material_no = material_no;
        }

        public String getPr_code() {
            return pr_code;
        }

        public void setPr_code(String pr_code) {
            this.pr_code = pr_code;
        }

        public String getPr_status() {
            return pr_status;
        }

        public void setPr_status(String pr_status) {
            this.pr_status = pr_status;
        }

        public String getSo_id() {
            return so_id;
        }

        public void setSo_id(String so_id) {
            this.so_id = so_id;
        }

        public String getSp_sn() {
            return sp_sn;
        }

        public void setSp_sn(String sp_sn) {
            this.sp_sn = sp_sn;
        }

        public String getStore_area_name() {
            return store_area_name;
        }

        public void setStore_area_name(String store_area_name) {
            this.store_area_name = store_area_name;
        }

        public String getYakuan_price() {
            return yakuan_price;
        }

        public void setYakuan_price(String yakuan_price) {
            this.yakuan_price = yakuan_price;
        }

        public String getDown_spsn() {
            return down_spsn;
        }

        public void setDown_spsn(String down_spsn) {
            this.down_spsn = down_spsn;
        }

        public String getDown_material_no() {
            return down_material_no;
        }

        public void setDown_material_no(String down_material_no) {
            this.down_material_no = down_material_no;
        }

        public String getDown_yakuan_price() {
            return down_yakuan_price;
        }

        public void setDown_yakuan_price(String down_yakuan_price) {
            this.down_yakuan_price = down_yakuan_price;
        }

        public String getDown_spmark_desc() {
            return down_spmark_desc;
        }

        public void setDown_spmark_desc(String down_spmark_desc) {
            this.down_spmark_desc = down_spmark_desc;
        }
    }

}
