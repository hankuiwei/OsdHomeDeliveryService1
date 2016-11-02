package com.lenovo.csd.eservice.message;

/**
 * 定义事件
 * Created by shengtao
 * on 2016/8/24
 * 16:09
 */
public class MessageEvent {
    public final String message;//要传递的消息
    private Type mType;//区分出消息是哪种类型
    public Object mObj;

    public enum Type {
        NEED_GO_TO_ORDER_PAGE//是否需要从login跳到HomeFragment，然后继续根据order_id继续跳转到工单详情页面
    }

    public Type getType() {
        return mType;
    }

    public Object getObj() {
        return mObj;
    }

    public MessageEvent(String message, Type type) {
        this.message = message;
        mType = type;
    }

    public MessageEvent(String message, Type type, Object mObj) {
        this.message = message;
        mType = type;
        this.mObj = mObj;
    }
}
