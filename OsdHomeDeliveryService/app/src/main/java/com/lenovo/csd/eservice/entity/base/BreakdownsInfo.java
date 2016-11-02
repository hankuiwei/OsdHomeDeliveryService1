package com.lenovo.csd.eservice.entity.base;

import java.util.List;

/**
 * Created by hankw on 16-8-19.
 */
public class BreakdownsInfo extends BaseData {
    private String change_code;
    private List<ReasonList> reason_list;

    public String getChangeCode() {
        return change_code;
    }

    public void setChangeCode(String change_code) {
        this.change_code = change_code;
    }

    public List getReasonList() {
        return reason_list;
    }

    public void setReasonList(List<ReasonList> reason_list) {
        this.reason_list = reason_list;
    }
}
