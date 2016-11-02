package com.lenovo.csd.eservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.entity.base.LoginData;
import com.lenovo.csd.eservice.entity.base.OrderTask;
import com.lenovo.csd.eservice.entity.base.TaskHistorys;
import com.lenovo.csd.eservice.http.ErrorCode;
import com.lenovo.csd.eservice.http.HttpClientManager;
import com.lenovo.csd.eservice.http.NetInterface;
import com.lenovo.csd.eservice.http.callback.adapter.JsonHttpCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hankw on 16-7-21.
 */
public class TaskListViewAdapter extends BaseAdapter {

    private final String TAG = getClass().getSimpleName();

    private View view;
    private List<TaskHistorys> list;
    private Context mContext;
    private String orderId;
    private String userCode;
    private String token;
    private Map<String,String> params;
    private List<OrderTask.TaskData> mTaskDataList;
    private int isshow;
    private String orderState;
    private boolean firstflag;
    private boolean isBack;

    public TaskListViewAdapter(List<TaskHistorys> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        this.params = new HashMap<String, String>();
        this.mTaskDataList = new ArrayList<OrderTask.TaskData>();
        this.isshow = 1;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    int iconFlag;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_workorder_flow,parent,false);
            int[] ids = {R.id.img_OrderTaskIcon,R.id.text_OrderTaskName,R.id.text_OrderFlowItemTimeLine,R.id.text_OrderTaskResult,R.id.btn_OrderTaskRevoke,R.id.img_OrderTaskVLine};

            viewHolder = new ViewHolder(convertView,ids);
            View[] views = viewHolder.getItemView();


            int[] icons ={R.drawable.icon_task1,R.drawable.icon_task2,R.drawable.icon_task3,R.drawable.icon_task4,R.drawable.icon_task5,R.drawable.icon_task6};
            if (iconFlag == 6) {
                iconFlag = 0;
            }
           final TaskHistorys mTaskHistorys= list.get(position);
           // if (mTaskHistorys != null)
            ImageView mTaskIcon = (ImageView) views[0];
            TextView mTaskName = (TextView) views[1];
            TextView mTaskTime = (TextView) views[2];
            TextView mTaskResult = (TextView) views[3];
            Button mTaskRevoke = (Button) views[4];
            ImageView mVLine = (ImageView) views[5];
            // TODO: 16-8-13 此处等UI给切图后再设置
            mTaskIcon.setBackgroundResource(icons[iconFlag]);
            String name  = mTaskHistorys.getName();
            //name = name.substring(0,name.indexOf("("));
            String result = mTaskHistorys.getTask_back();
            String time = mTaskHistorys.getApply_time();
            time = time.substring(time.indexOf(" "));
            mTaskName.setText(name);
            mTaskResult.setText(result);
            mTaskTime.setText(time);
             int flagBtn = getCount()-1;



            //if (flagBtn == position || flagBtn  == -1) {
          int i = (int) getItemId(position);
            if (flagBtn ==getItemId(position)) {
                if (!"服务完".equals(orderState)) {
                   // pos = 0;
                    mTaskRevoke.setVisibility(View.VISIBLE);
                    firstflag = true;
                }

                //isshow = 2;
                mVLine.setVisibility(View.GONE);

            }
            iconFlag ++;
           // pos++;


            mTaskRevoke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String soTaskId = mTaskHistorys.getId();
                    params.put("so_task_id",soTaskId);
                    HttpClientManager.post(NetInterface.REVOKETASK, params, new JsonHttpCallBack<LoginData>() {
                        @Override
                        public void onSuccess(LoginData result) {
                            if (Integer.parseInt(result.getStatus_code()) == ErrorCode.STATUS_SUCCESS){
                                HttpClientManager.post(NetInterface.GET_ORDER_TASK_HIS, params, new JsonHttpCallBack<OrderTask>() {
                                    @Override
                                    public void onSuccess(OrderTask result) {
                                        if (Integer.parseInt(result.getStatus_code()) == ErrorCode.STATUS_SUCCESS){
                                            mTaskDataList = result.getData();
                                            list.clear();
                                            list.addAll(mTaskDataList.get(0).getTask_historys());

                                        }
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                    }
                                });

                            }
                        }

                        @Override
                        public void onError(Exception e) {
                        }
                    });
                }
            });

        }
        return convertView;
    }

    public class ViewHolder {
        private View[] itemView;

        private ViewHolder(View v, int[] ids) {
            itemView = new View[ids.length];
            for (int i = 0; i < ids.length; i++ ) {
                itemView [i] = v.findViewById(ids [i] );
            }
            v.setTag(this);
        }

        public View[] getItemView() {
            return itemView;
        }

    }



    public void setTaskRevokeInfo(Map<String,String> paramsMap) {
        params.putAll(paramsMap);

    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public void setNeedBack(boolean back) {
        this.isBack = back;
    }
}
