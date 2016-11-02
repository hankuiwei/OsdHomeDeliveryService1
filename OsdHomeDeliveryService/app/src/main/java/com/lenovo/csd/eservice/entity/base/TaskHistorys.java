package com.lenovo.csd.eservice.entity.base;

/**
 * Created by hankw on 16-8-12.
 */
public class TaskHistorys extends BaseData {
    private String id;
    private String name;
    private String apply_time;

    public String getApply_time() {
        return apply_time;
    }

    public void setApply_time(String apply_time) {
        this.apply_time = apply_time;
    }

    private String create_by;
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

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }
}
