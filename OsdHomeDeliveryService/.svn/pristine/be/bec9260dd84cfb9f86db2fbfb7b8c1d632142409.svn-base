package com.lenovo.csd.eservice.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.adapter.PackingListAdapter;
import com.lenovo.csd.eservice.cache.SharedPrefManager;
import com.lenovo.csd.eservice.entity.base.ChangeBoxsInfo;
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
 * Created by hankw on 16-8-24.
 */
public class ChangePackingListActivity extends BaseActivity {

    private final String TAG = getClass().getSimpleName();
    private LinearLayout mDeviceinfoBack;
    private TextView mPackinputOCode;
    //private Button mPacksearchOCode;
    private RecyclerView mPackingList;
    private PackingListAdapter mPackingAdapter;
    private List<ChangeBoxsInfo.BoxList> mBoxList;
    private String mPackOrderId;
    private static final String PACKINGORDERID = "packOrderCode";
    private  Map<String,String> params;
    @Override
    protected void provideLayout() {
        setContentView(R.layout.activity_changepackinglist);
    }
    @Override
    protected void initView() {
        mDeviceinfoBack = (LinearLayout) findViewById(R.id.Lin_deviceMoreinfoBack);
        mPackinputOCode = (TextView) findViewById(R.id.edit_PackinputOCode);
       // mPacksearchOCode = (Button) findViewById(R.id.btn_PacksearchOCode);
        mPackingList = (RecyclerView) findViewById(R.id.Recycle_PackingList);
        mBoxList = new ArrayList<ChangeBoxsInfo.BoxList>();
        mPackingAdapter = new PackingListAdapter(ChangePackingListActivity.this,mBoxList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ChangePackingListActivity.this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mPackingList.setLayoutManager(layoutManager);
        mPackingList.setAdapter(mPackingAdapter);
    }
    @Override
    protected void initData() {
        int netState =  Utils.checkInternetStatus(ChangePackingListActivity.this);
        if (netState == 0) {
            Toast.makeText(ChangePackingListActivity.this,"网络不可用,请连接网络后重试",Toast.LENGTH_SHORT).show();
            return;
        }
        params = new HashMap<String, String>();
        Intent intent = getIntent();
        mPackOrderId = intent.getStringExtra(PACKINGORDERID);
        showLoadingDialog();
        SharedPreferences preferences = SharedPrefManager.getSystemSharedPref(ChangePackingListActivity.this);
        String userCode = SharedPrefManager.getStringInSharePref(preferences,SharedPrefManager.LOGIN_USER_CODE,"userCode");
        String token = SharedPrefManager.getStringInSharePref(preferences,SharedPrefManager.LOGIN_TOKEN,"mToken");
        //token = "g38SmC5uY7sUeg31RJtsGLrCywrXkDsC8asCKRv08vNLgOWdmvYIKLDYGcluDANw";
        //userCode = "C47018";
        if ( !"userCode".equals(userCode) && !"mToken".equals(token)){
            params.put("token",token);
            params.put("user_code",userCode);
        }
        if (mPackOrderId != null) {
           // mPackinputOCode.setText(mPackOrderId);
            params.put("order_code",mPackOrderId);
           HttpClientManager.post(NetInterface.GET_CHANGE_BOXS, params, new JsonHttpCallBack<ChangeBoxsInfo>() {
               @Override
               public void onSuccess(ChangeBoxsInfo result) {
                   if (ResultUtils.isSuccess(ChangePackingListActivity.this,result)){
                       ChangeBoxsInfo.Data boxsData = result.getData();
                        if (mBoxList.size() > 0){
                            mBoxList.clear();
                        }
                       if (boxsData != null){
                           List<ChangeBoxsInfo.BoxList> lists = boxsData.getBoxList();
                           if (lists != null && lists.size() > 0){
                               mBoxList.addAll(lists);
                               mPackingAdapter.notifyDataSetChanged();
                           }else {
                               mPackinputOCode.setVisibility(View.VISIBLE);
                           }

                       }

                       dismissLoadingDialog();
                   }
               }

               @Override
               public void onError(Exception e) {
                       dismissLoadingDialog();
                       mPackinputOCode.setVisibility(View.VISIBLE);
                   Utils.showServerError(ChangePackingListActivity.this);
               }
           });
        } else {
            dismissLoadingDialog();
        }


    }

    @Override
    protected void setClickLintenner() {

       /* mPacksearchOCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String orderCode =  mPackinputOCode.getText().toString();
                if ( orderCode == null || "".equals(orderCode)){
                    Toast.makeText(ChangePackingListActivity.this,"输入不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    if(!Pattern.matches("[0-9]*", orderCode)){
                        Toast.makeText(ChangePackingListActivity.this,"必须是数字！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                showLoadingDialog();
                params.put("order_code",orderCode);
                HttpClientManager.post(NetInterface.GET_CHANGE_BOXS, params, new JsonHttpCallBack<ChangeBoxsInfo>() {
                    @Override
                    public void onSuccess(ChangeBoxsInfo result) {
                        if (ResultUtils.isSuccess(ChangePackingListActivity.this,result)){
                            ChangeBoxsInfo.Data boxsData = result.getData();
                            mBoxList.addAll(boxsData.getBoxList());
                            mPackingAdapter.notifyDataSetChanged();
                            dismissLoadingDialog();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        dismissLoadingDialog();
                        Utils.showServerError(ChangePackingListActivity.this);
                    }
                });
            }
        });*/

        mPackingAdapter.setOnItemClickListener(new PackingListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, ChangeBoxsInfo.BoxList data) {

               // AddChangeRecordActivity.toAddChangeRecord(ChangePackingListActivity.this,data);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(AddChangeRecordActivity.PACKINGLIST,data);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();

            }
        });

        mDeviceinfoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static void toChangePack(Activity activity, String orderId){
        Intent intent = new Intent(activity,ChangePackingListActivity.class);
        intent.putExtra(PACKINGORDERID,orderId);
       // activity.startActivity(intent);
        activity.startActivityForResult(intent,0);
    }




}
