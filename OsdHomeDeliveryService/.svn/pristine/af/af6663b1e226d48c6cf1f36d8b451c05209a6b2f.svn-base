package com.lenovo.csd.eservice.entity.base;

import java.util.List;

/**
 * Created by hankw on 16-8-19.
 */
public class InitUnChange extends BaseData {

    private UnChangeInfo data;

    public UnChangeInfo getData() {
        return data;
    }

    public void setData(UnChangeInfo data) {
        this.data = data;
    }

    public class UnChangeInfo {
        private int CCSend;
        private List<MatelialClass> MatelialClasss;


        public int getCCSend() {
            return CCSend;
        }

        public void setCCSend(int CCSend) {
            this.CCSend = CCSend;
        }

        public List<MatelialClass> getMatelialClasss() {
            return MatelialClasss;
        }

        public void setMatelialClasss(List<MatelialClass> matelialClasss) {
            MatelialClasss = matelialClasss;
        }
    }

    public class MatelialClass{
        private List<Action> Actions;
        private String SPType;
        private String SPTypeDesc;

        public List<Action> getActions() {
            return Actions;
        }

        public void setActions(List<Action> actions) {
            Actions = actions;
        }

        public String getSPType() {
            return SPType;
        }

        public void setSPType(String SPType) {
            this.SPType = SPType;
        }

        public String getSPTypeDesc() {
            return SPTypeDesc;
        }

        public void setSPTypeDesc(String SPTypeDesc) {
            this.SPTypeDesc = SPTypeDesc;
        }
    }

    public class Action{
        private String Code;
        private String Value;

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String value) {
            Value = value;
        }
    }
}
