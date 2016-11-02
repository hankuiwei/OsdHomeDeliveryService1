package com.lenovo.csd.eservice.activity.manager;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity活动管理类，方便管理所有活动。
 * Created by shengtao
 * on 2016/8/16
 * 10:09
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();
    /**
     * 添加活动
     * @param activity
     */
    public static void addActivity(Activity activity){
        if(!activities.contains(activity)){
            activities.add(activity);
        }
    }
    /**
     * 移除活动
     * @param activity
     */
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    /**
     * 结束所有活动
     */
    public static void finishAll(){
        for(Activity activity : activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
