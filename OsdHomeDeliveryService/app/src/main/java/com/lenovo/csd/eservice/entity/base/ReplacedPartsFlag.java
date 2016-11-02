package com.lenovo.csd.eservice.entity.base;

import java.util.List;

/**
 * Created by hankw on 16-8-19.
 */
public class ReplacedPartsFlag extends BaseData {
    private String code;
    private String value;
    private List<ReplacedPartsDescs> replaced_parts_descs;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<ReplacedPartsDescs> getReplacedPartsDescs() {
        return replaced_parts_descs;
    }

    public void setReplacedPartsDescs(List<ReplacedPartsDescs> replaced_parts_descs) {
        this.replaced_parts_descs = replaced_parts_descs;
    }
}
