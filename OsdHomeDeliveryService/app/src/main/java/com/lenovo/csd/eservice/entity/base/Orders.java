package com.lenovo.csd.eservice.entity.base;

import java.util.List;

/**
 * Created by hankw on 16-8-3.
 */
public class Orders extends BaseData {
    private String order_id;
    private String order_state_name;
    private String order_type_name;
    private String pre_time; //预约上门时间
    //private String[] labels;//交付、销售的提示
    private List<Label> labels;
    private String customer_name;
    private String[] customer_phones;
    private String contact_phone;
    private String customer_address;
    private String customer_baidu_location;
    private String customer_tencent_location;
    private String product_sn;//机器信息
    private String product_type;//机器信息
    private String service_complete_time;
    private String parts;//备件信息
    private String parts_status;//备件状态
    private String failure_description;//故障描述
    private String current_task_code;//当前所处任务


    public String getContactPhone() {
        return contact_phone;
    }

    public void setContactPhone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_state_name() {
        return order_state_name;
    }

    public void setOrder_state_name(String order_state_name) {
        this.order_state_name = order_state_name;
    }

    public String getOrder_type_name() {
        return order_type_name;
    }

    public void setOrder_type_name(String order_type_name) {
        this.order_type_name = order_type_name;
    }

    public String getPre_time() {
        return pre_time;
    }

    public void setPre_time(String pre_time) {
        this.pre_time = pre_time;
    }

   /* public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }*/


    public List<Label> getLabels() {
        return labels;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String[] getCustomer_phones() {
        return customer_phones;
    }

    public void setCustomer_phones(String[] customer_phones) {
        this.customer_phones = customer_phones;
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

    public String getCustomer_tencent_location() {
        return customer_tencent_location;
    }

    public void setCustomer_tencent_location(String customer_tencent_location) {
        this.customer_tencent_location = customer_tencent_location;
    }

    public String getProduct_sn() {
        return product_sn;
    }

    public void setProduct_sn(String product_sn) {
        this.product_sn = product_sn;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }


    public String getServiceCompleteTime() {
        return service_complete_time;
    }

    public void setServiceCompleteTime(String service_complete_time) {
        this.service_complete_time = service_complete_time;
    }

    public String getParts() {
        return parts;
    }

    public void setParts(String parts) {
        this.parts = parts;
    }

    public String getParts_status() {
        return parts_status;
    }

    public void setParts_status(String parts_status) {
        this.parts_status = parts_status;
    }

    public String getFailure_description() {
        return failure_description;
    }

    public void setFailure_description(String failure_description) {
        this.failure_description = failure_description;
    }

    public String getCurrent_task_code() {
        return current_task_code;
    }

    public void setCurrent_task_code(String current_task_code) {
        this.current_task_code = current_task_code;
    }
}
