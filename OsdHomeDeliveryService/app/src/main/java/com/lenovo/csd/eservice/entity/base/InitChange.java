package com.lenovo.csd.eservice.entity.base;

import java.util.List;

/**
 * Created by hankw on 16-8-19.
 */
public class InitChange extends BaseData {

    private ChangeInfo data;

    public ChangeInfo getData() {
        return data;
    }

    public void setData(ChangeInfo data) {
        this.data = data;
    }

    public class ChangeInfo {
        private String bar_code;
        private boolean box_visible;
        private List<UpChangeInfo> up_changes;
        private List<BreakdownsInfo> breakdowns;
        private List<ChangeCategorys> change_categorys;


        public String getBarCode() {
            return bar_code;
        }

        public void setBarCode(String bar_code) {
            this.bar_code = bar_code;
        }

        public boolean isBoxVisible() {
            return box_visible;
        }

        public void setBoxVisible(boolean box_visible) {
            this.box_visible = box_visible;
        }

        public List<UpChangeInfo> getUpChanges() {
            return up_changes;
        }

        public void setUpChanges(List<UpChangeInfo> up_changes) {
            this.up_changes = up_changes;
        }

        public List<BreakdownsInfo> getBreakdowns() {
            return breakdowns;
        }

        public void setBreakdowns(List<BreakdownsInfo> breakdowns) {
            this.breakdowns = breakdowns;
        }

        public List<ChangeCategorys> getChangeCategorys() {
            return change_categorys;
        }

        public void setChangeCategorys(List<ChangeCategorys> change_categorys) {
            this.change_categorys = change_categorys;
        }
    }
}
