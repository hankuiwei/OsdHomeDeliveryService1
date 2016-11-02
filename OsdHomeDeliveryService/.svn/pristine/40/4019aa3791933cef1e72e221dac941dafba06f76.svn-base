package com.lenovo.csd.eservice.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.adapter.OrderTaskAdapter;
import com.lenovo.csd.eservice.cache.SharedPrefManager;
import com.lenovo.csd.eservice.entity.base.OrderTask;
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

//import com.lenovo.csd.eservice.adapter.TaskListViewAdapter;

/**
 * Created by hankw on 16-8-12.
 */
public class OrdertaskActivity extends BaseActivity{

    private final String TAG = getClass().getSimpleName();
    private final static String ORDERID = "OrderCode";
    private final static String STATENAME = "OrderState";
    private RecyclerView mOrderTask;
   // private ListView mOrderTask;
    private List<OrderTask.TaskData> mTaskDataList;
    private List<TaskHistorys> mTaskHistoryList;
    private OrderTaskAdapter mAdapter;
    //private TaskListViewAdapter taskListViewAdapter;


    @Override
    protected void provideLayout() {
        setContentView(R.layout.activity_ordertask);
    }

    @Override
    protected void initView() {
       mOrderTask = (RecyclerView) findViewById(R.id.orders_task_Rec);
        //mOrderTask = (ListView) findViewById(R.id.orders_task_Rec);
        mTaskHistoryList = new ArrayList<TaskHistorys>();

        final LinearLayoutManager layoutManager = new LinearLayoutManager(OrdertaskActivity.this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mOrderTask.setLayoutManager(layoutManager);
        mAdapter = new OrderTaskAdapter(mTaskHistoryList,OrdertaskActivity.this);
        mOrderTask.setAdapter(mAdapter);
        //taskListViewAdapter = new TaskListViewAdapter(mTaskHistoryList,OrdertaskActivity.this);
       // mOrderTask.setAdapter(taskListViewAdapter);
    }

    @Override
    protected void initData() {
        int netState =  Utils.checkInternetStatus(OrdertaskActivity.this);
        if (netState == 0) {
            Toast.makeText(OrdertaskActivity.this,"网络不可用,请连接网络后重试",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = getIntent();
        String code =  intent.getStringExtra(ORDERID);

        String state = intent.getStringExtra(STATENAME);
        mAdapter.setOrderState(state);
        //taskListViewAdapter.setOrderState(state);
        SharedPreferences preferences = SharedPrefManager.getSystemSharedPref(OrdertaskActivity.this);
        String userCode = SharedPrefManager.getStringInSharePref(preferences,SharedPrefManager.LOGIN_USER_CODE,"userCode");
        String token = SharedPrefManager.getStringInSharePref(preferences,SharedPrefManager.LOGIN_TOKEN,"mToken");
        //code = "301606242100319002";//"201605092100319001";//"201609082100319001";//"201607132100319002";
        //token = "g38SmC5uY7sUeg31RJtsGLrCywrXkDsC8asCKRv08vNLgOWdmvYIKLDYGcluDANw";
        //userCode = "C47018";
        if (code != null && !"userCode".equals(userCode) && !"mToken".equals(token)){
            showLoadingDialog();
            final Map<String,String> params = new HashMap<String, String>();
            params.put("token",token);
            params.put("user_code",userCode);
            params.put("order_id",code);
            HttpClientManager.post(NetInterface.GET_ORDER_TASK_HIS, params, new JsonHttpCallBack<OrderTask>() {
                @Override
                public void onSuccess(OrderTask result) {
                    if (ResultUtils.isSuccess(OrdertaskActivity.this,result)){
                        //mTaskDataList = result.getData();
                        mTaskHistoryList.addAll(result.getData().get(0).getTask_historys());
                        mAdapter.setNeedBack(true);
                        mAdapter.notifyDataSetChanged();
                        mAdapter.setTaskRevokeInfo(params);
                       // taskListViewAdapter.notifyDataSetChanged();
                        //taskListViewAdapter.setTaskRevokeInfo(params);
                    }else {
                        if (result != null){
                            String err = result.getMessage();
                            Toast.makeText(OrdertaskActivity.this,err,Toast.LENGTH_SHORT).show();
                        }

                    }
                    dismissLoadingDialog();
                }

                @Override
                public void onError(Exception e) {
                    dismissLoadingDialog();
                    Utils.showServerError(OrdertaskActivity.this);
                }
            });
        }

    }



    @Override
    protected void setClickLintenner() {
            findViewById(R.id.orders_task).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        mAdapter.setmTaskReback(new OrderTaskAdapter.TaskReback() {
            @Override
            public void onRebackSuccess() {
                setResult(RESULT_OK,new Intent());
                finish();
            }
        });
    }

    public static void toOrderTask(Activity activity,String orderid,String orderStateName,int req){
        Intent intent = new Intent(activity,OrdertaskActivity.class);
        intent.putExtra(ORDERID,orderid);
        intent.putExtra(STATENAME,orderStateName);
        activity.startActivityForResult(intent,req);
    }
}
