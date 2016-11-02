package com.lenovo.csd.eservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.activity.OrdertaskActivity;
import com.lenovo.csd.eservice.entity.base.OrderTask;
import com.lenovo.csd.eservice.entity.base.Revoketask;
import com.lenovo.csd.eservice.entity.base.TaskHistorys;
import com.lenovo.csd.eservice.http.HttpClientManager;
import com.lenovo.csd.eservice.http.NetInterface;
import com.lenovo.csd.eservice.http.ResultUtils;
import com.lenovo.csd.eservice.http.callback.adapter.JsonHttpCallBack;
import com.lenovo.csd.eservice.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hankw on 16-8-12.
 */
public class OrderTaskAdapter extends RecyclerView.Adapter<OrderTaskAdapter.ViewHolder> {

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
    private String mshow = "y";
    private boolean isClick;
    public static final int TYPE_SHOW  = 0;
    public static final int TYPE_NSHOW = 1;
    private int colorindex = 0;

    public void setmTaskReback(TaskReback mTaskReback) {
        this.mTaskReback = mTaskReback;
    }

    private TaskReback mTaskReback;


    public OrderTaskAdapter(List<TaskHistorys> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        this.params = new HashMap<String, String>();
        this.mTaskDataList = new ArrayList<OrderTask.TaskData>();
        this.isshow = 1;
    }

    public static interface TaskReback{
        void onRebackSuccess();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workorder_flow,parent,false);
        int[] ids = {R.id.img_OrderTaskIcon,R.id.text_OrderTaskName,R.id.text_OrderFlowItemTimeLine,R.id.text_OrderTaskResult,R.id.btn_OrderTaskRevoke,R.id.img_OrderTaskVLine};
        return new ViewHolder(view,ids);
    }
    int iconFlag;
    private int pos;
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // TODO: 16-8-13  icons 存放工单流程的图标
        int[] icons ={R.drawable.icon_task1,R.drawable.icon_task2,R.drawable.icon_task3,R.drawable.icon_task4,R.drawable.icon_task5,R.drawable.icon_task6};
        if (iconFlag == 6) {
            iconFlag = 0;
        }
        final TaskHistorys mTaskHistorys= list.get(position);
        if (mTaskHistorys == null)
            return;
            View[] views = holder.getViews();
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

        int[] tablecolors ={R.color.gray_A8A8A8,R.color.gray_898989};
            if (colorindex ==2){
                colorindex=0;
            }

            mTaskResult.setTextColor(mContext.getResources().getColor(tablecolors[colorindex]));
            mTaskResult.setText(result);
                colorindex++;


            mTaskTime.setText(time);
            final int flagBtn = getItemCount()-1;
            String id = mTaskHistorys.getId();
        iconFlag ++;
        int  holdId = holder.getLayoutPosition();
        if (!isBack){
            if (flagBtn == holdId) {
                mVLine.setVisibility(View.GONE);
            }
            return;
        }



        if (flagBtn == holdId) {
            if (!"服务完".equals(orderState)) {
                //pos = 0;
                mTaskRevoke.setVisibility(View.VISIBLE);
                firstflag = true;
                mshow = "n";
                mVLine.setVisibility(View.GONE);
            }else {
                mTaskRevoke.setVisibility(View.INVISIBLE);
                mVLine.setVisibility(View.GONE);
            }

            //isshow = 2;


        } else {
            if (!"服务完".equals(orderState)) {
                mTaskRevoke.setVisibility(View.INVISIBLE);
                mVLine.setVisibility(View.VISIBLE);
            } else {
                mTaskRevoke.setVisibility(View.INVISIBLE);
                mVLine.setVisibility(View.VISIBLE);
            }

        }

        pos++;

            mTaskRevoke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isClick){
                        Toast.makeText(mContext,"不能重复提交",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    isClick = true;
                    String soTaskId = mTaskHistorys.getId();
                    params.put("so_task_id",soTaskId);
                    ((OrdertaskActivity)mContext).showLoadingDialog();
                    HttpClientManager.post(NetInterface.REVOKETASK, params, new JsonHttpCallBack<Revoketask>() {
                        @Override
                        public void onSuccess(Revoketask result) {
                            ((OrdertaskActivity)mContext).dismissLoadingDialog();
                            if (ResultUtils.isSuccess(mContext,result)){
                                /*HttpClientManager.post(NetInterface.GET_ORDER_TASK_HIS, params, new JsonHttpCallBack<OrderTask>() {
                                    @Override
                                    public void onSuccess(OrderTask result) {
                                        if (Integer.parseInt(result.getStatus_code()) == ErrorCode.STATUS_SUCCESS){
                                            mTaskDataList = result.getData();
                                            list.clear();
                                            list.addAll(mTaskDataList.get(0).getTask_historys());
                                            if (mTaskReback != null){
                                                mTaskReback.onRebackSuccess();
                                            }
                                        } else {
                                            Toast.makeText(mContext,result.getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                    }
                                });*/

                                String err = result.getData().getResult_msg();
                                Toast.makeText(mContext,err,Toast.LENGTH_SHORT).show();
                                if ("预制任务不能撤销".equals(err)){
                                    isClick = false;
                                    return;
                                }else {
                                    if (mTaskReback != null){
                                        mTaskReback.onRebackSuccess();
                                    }
                                }


                            }else {
                                isClick = false;
                                Revoketask.RevokeData data = result.getData();
                                if (data != null) {
                                    String err = data.getResult_msg();
                                    Toast.makeText(mContext,err,Toast.LENGTH_SHORT).show();
                                }

                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            isClick = false;
                            ((OrdertaskActivity)mContext).dismissLoadingDialog();
                            Utils.showServerError(mContext);

                        }
                    });
                }
            });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private View[] views;
        public ViewHolder(View itemView,int[] ids) {
            super(itemView);
            views = new View[ids.length];
            for (int i = 0; i < ids.length; i++) {
                views[i] = itemView.findViewById(ids[i]);
            }

       /* int taskid = ViewHolder.super.getLayoutPosition();
            Button btn = (Button) views[4];
            ImageView line = (ImageView) views[5];
            if (taskid == (getItemCount()-1)) {
                if (!"服务完".equals(orderState)) {
                    //pos = 0;
                    btn.setVisibility(View.VISIBLE);
                    firstflag = true;
                    mshow = "n";
                }

                //isshow = 2;
                line.setVisibility(View.GONE);

            }
*/
        }

        public View[] getViews() {
            return views;
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
