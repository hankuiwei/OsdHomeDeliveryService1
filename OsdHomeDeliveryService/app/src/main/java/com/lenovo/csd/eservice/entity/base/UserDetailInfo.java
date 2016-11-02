package com.lenovo.csd.eservice.entity.base;

import java.util.ArrayList;

/**
 * Created by 彤 on 2016/8/22.
 */
public class UserDetailInfo extends BaseData {
    private UserInfoBean data;

    public UserInfoBean getData() {
        return data;
    }

    public void setData(UserInfoBean data) {
        this.data = data;
    }

    public class UserInfoBean {
        private String product_sn;
        private String product_type;
        private String failure_description;
        private String pre_time;
        private String so_type;
        private String order_state_name;
        private String order_type_name;
        private String callback;
        private String company_name;
        private String contact_phone;
        private String current_num;
        private String current_task_code;
        private String customer_address;
        private String customer_baidu_location;
        private String customer_email;
        private String customer_level;
        private String customer_name;
        private ArrayList customer_phones;
        private String customer_tencent_location;

        public String getProduct_type() {
            return product_type;
        }

        public void setProduct_type(String product_type) {
            this.product_type = product_type;
        }

        public String getOrder_type_name() {
            return order_type_name;
        }

        public void setOrder_type_name(String order_type_name) {
            this.order_type_name = order_type_name;
        }

        public String getProduct_sn() {
            return product_sn;
        }

        public void setProduct_sn(String product_sn) {
            this.product_sn = product_sn;
        }

        public String getFailure_description() {
            return failure_description;
        }

        public void setFailure_description(String failure_description) {
            this.failure_description = failure_description;
        }

        public String getPre_time() {
            return pre_time;
        }

        public void setPre_time(String pre_time) {
            this.pre_time = pre_time;
        }

        public String getSo_type() {
            return so_type;
        }

        public void setSo_type(String so_type) {
            this.so_type = so_type;
        }

        public String getOrder_state_name() {
            return order_state_name;
        }

        public void setOrder_state_name(String order_state_name) {
            this.order_state_name = order_state_name;
        }

        public String getCallback() {
            return callback;
        }

        public void setCallback(String callback) {
            this.callback = callback;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getContact_phone() {
            return contact_phone;
        }

        public void setContact_phone(String contact_phone) {
            this.contact_phone = contact_phone;
        }

        public String getCurrent_num() {
            return current_num;
        }

        public void setCurrent_num(String current_num) {
            this.current_num = current_num;
        }

        public String getCurrent_task_code() {
            return current_task_code;
        }

        public void setCurrent_task_code(String current_task_code) {
            this.current_task_code = current_task_code;
        }

        public String getCustomer_address() {
            return customer_address;
        }

        public void setCustomer_address(String customer_address) {
            this.customer_address = customer_address;
        }

        public String getCustomer_baidu_location() {
            return customer_baidu_location;
        }

        public void setCustomer_baidu_location(String customer_baidu_location) {
            this.customer_baidu_location = customer_baidu_location;
        }

        public String getCustomer_email() {
            return customer_email;
        }

        public void setCustomer_email(String customer_email) {
            this.customer_email = customer_email;
        }

        public String getCustomer_level() {
            return customer_level;
        }

        public void setCustomer_level(String customer_level) {
            this.customer_level = customer_level;
        }

        public String getCustomer_name() {
            return customer_name;
        }

        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }

        public ArrayList getCustomer_phones() {
            return customer_phones;
        }

        public void setCustomer_phones(ArrayList customer_phones) {
            this.customer_phones = customer_phones;
        }

        public String getCustomer_tencent_location() {
            return customer_tencent_location;
        }

        public void setCustomer_tencent_location(String customer_tencent_location) {
            this.customer_tencent_location = customer_tencent_location;
        }
    }

    public class PhoneBean {
        private String phone;
        private String type;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
