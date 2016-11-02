package com.lenovo.csd.eservice.entity.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 彤 on 2016/8/18.
 */
public class CurrentTaskData extends BaseData {
    private List<CurrentTaskBean> data;

    public List<CurrentTaskBean> getTaskBean() {
        return data;
    }

    public void setTaskBean(List<CurrentTaskBean> taskBeans) {
        this.data = taskBeans;
    }

    //第一层子data
    public class CurrentTaskBean {
        private String task_id;
        private String task_name;
        private List<TaskSource> task_sources;

        public String getTask_id() {
            return task_id;
        }

        public void setTask_id(String task_id) {
            this.task_id = task_id;
        }

        public String getTask_name() {
            return task_name;
        }

        public void setTask_name(String task_name) {
            this.task_name = task_name;
        }

        public List<TaskSource> getTask_sources() {
            return task_sources;
        }

        public void setTask_sources(List<TaskSource> task_sources) {
            this.task_sources = task_sources;
        }
    }

    //第二层子data
    public class TaskSource {
        private String source_name;
        private List<SourceOption> source_options;
        private String source_type;
        private List<ComeType> come_types;

        public String getSource_name() {
            return source_name;
        }

        public void setSource_name(String source_name) {
            this.source_name = source_name;
        }

        public List<SourceOption> getSource_options() {
            return source_options;
        }

        public void setSource_options(List<SourceOption> source_options) {
            this.source_options = source_options;
        }

        public String getSource_type() {
            return source_type;
        }

        public void setSource_type(String source_type) {
            this.source_type = source_type;
        }

        public List<ComeType> getCome_types() {
            return come_types;
        }

        public void setCome_types(List<ComeType> come_types) {
            this.come_types = come_types;
        }
    }

    //第三次data
    class SourceOption {
        private String id;
        private String name;
        private String task_back;
        private String task_remark;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTask_back() {
            return task_back;
        }

        public void setTask_back(String task_back) {
            this.task_back = task_back;
        }

        public String getTask_remark() {
            return task_remark;
        }

        public void setTask_remark(String task_remark) {
            this.task_remark = task_remark;
        }
    }

    public class ComeType {
        private String come;
        private String rule;
        private String type;
        private List<TabControl> tab_controls;

        public String getCome() {
            return come;
        }

        public void setCome(String come) {
            this.come = come;
        }

        public String getRule() {
            return rule;
        }

        public void setRule(String rule) {
            this.rule = rule;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<TabControl> getTab_controls() {
            return tab_controls;
        }

        public void setTab_controls(List<TabControl> tab_controls) {
            this.tab_controls = tab_controls;
        }
    }

    //第四层data
    public class TabControl {
        private String id;
        private String name;
        private ArrayList<TabControl> controls;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<TabControl> getControls() {
            return controls;
        }

        public void setControls(ArrayList<TabControl> controls) {
            this.controls = controls;
        }
    }


}
