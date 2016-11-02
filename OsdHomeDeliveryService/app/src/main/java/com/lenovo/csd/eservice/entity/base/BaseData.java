package com.lenovo.csd.eservice.entity.base;

import java.io.Serializable;

/**
 * Created by shengtao
 * Date:2016/7/21
 * Time:11:03
 */
public class BaseData implements Serializable {

    private static final long serialVersionUID = -8482875365690168431L;
    public String status_code;
    public String message;

    public String getStatus_code() {
        return status_code;
    }

    public String getMessage() {
        return message;
    }
}
