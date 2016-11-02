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
import com.lenovo.csd.eservice.adapter.PackingListAdapter2;
import com.lenovo.csd.eservice.cache.SharedPrefManager;
import com.lenovo.csd.eservice.entity.base.ChangeBoxTypeData;
import com.lenovo.csd.eservice.entity.base.ChangeBoxsInfo;
import com.lenovo.csd.eservice.entity.base.ChangeBoxsInfo2;
import com.lenovo.csd.eservice.http.HttpClientManager;
import com.lenovo.csd.eservice.http.NetInterface;
import com.lenovo.csd.eservice.http.ResultUtils;
import com.lenovo.csd.eservice.http.callback.adapter.JsonHttpCallBack;
import com.lenovo.csd.eservice.util.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hankw on 16-8-24.
 */
public class ChangePackingListActivity2 extends BaseActivity {

    private final String TAG = getClass().getSimpleName();
    private LinearLayout mDeviceinfoBack;
    private TextView mPackinputOCode;
    //private Button mPacksearchOCode;
    private RecyclerView mPackingList;
    //private PackingListAdapter mPackingAdapter;
    private PackingListAdapter2 mPackingAdapter2;
    private List<ChangeBoxsInfo.BoxList> mBoxList;
    private List<ChangeBoxTypeData> datas;
    private String mPackOrderId;
    private static final String PACKINGORDERID = "packOrderCode";
    private Map<String, String> params;

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
        datas = new ArrayList<ChangeBoxTypeData>();
        //mPackingAdapter = new PackingListAdapter(ChangePackingListActivity2.this, mBoxList);
        mPackingAdapter2 = new PackingListAdapter2(ChangePackingListActivity2.this, datas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ChangePackingListActivity2.this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mPackingList.setLayoutManager(layoutManager);
        mPackingList.setAdapter(mPackingAdapter2);
    }

    @Override
    protected void initData() {
        int netState = Utils.checkInternetStatus(ChangePackingListActivity2.this);
        if (netState == 0) {
            Toast.makeText(ChangePackingListActivity2.this, "网络不可用,请连接网络后重试", Toast.LENGTH_SHORT).show();
            return;
        }
        params = new HashMap<String, String>();
        Intent intent = getIntent();
        mPackOrderId = intent.getStringExtra(PACKINGORDERID);
        showLoadingDialog();
        SharedPreferences preferences = SharedPrefManager.getSystemSharedPref(ChangePackingListActivity2.this);
        String userCode = SharedPrefManager.getStringInSharePref(preferences, SharedPrefManager.LOGIN_USER_CODE, "userCode");
        String token = SharedPrefManager.getStringInSharePref(preferences, SharedPrefManager.LOGIN_TOKEN, "mToken");
        //token = "g38SmC5uY7sUeg31RJtsGLrCywrXkDsC8asCKRv08vNLgOWdmvYIKLDYGcluDANw";
        //userCode = "C47018";
        if (!"userCode".equals(userCode) && !"mToken".equals(token)) {
            params.put("token", token);
            params.put("user_code", userCode);
        }
        if (mPackOrderId != null) {
            // mPackinputOCode.setText(mPackOrderId);
            params.put("order_code", mPackOrderId);
      /*     HttpClientManager.post(NetInterface.GET_CHANGE_BOXS, params, new JsonHttpCallBack<ChangeBoxsInfo>() {
               @Override
               public void onSuccess(ChangeBoxsInfo result) {
                   if (ResultUtils.isSuccess(ChangePackingListActivity2.this,result)){
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
                   Utils.showServerError(ChangePackingListActivity2.this);
               }
           });*/


            HttpClientManager.post(NetInterface.GET_CHANGE_BOXS, params, new JsonHttpCallBack<ChangeBoxsInfo2>() {
                @Override
                public void onSuccess(ChangeBoxsInfo2 result) {
                    dismissLoadingDialog();
                    if (ResultUtils.isSuccess(ChangePackingListActivity2.this, result)) {
                        ChangeBoxsInfo2.Data cData = result.getData();
                        /*if (mBoxList.size() > 0){
                            mBoxList.clear();
                        }*/
                        if (cData != null) {
                            if ((cData.getChange_histories() != null && cData.getChange_histories().size() > 0) || (cData.getBoxList() != null && cData.getBoxList().size() > 0) || (cData.getBare_bone_material() != null && cData.getBare_bone_material().size() > 0)) {
                                datas.clear();
                                ChangeBoxsInfo2 mBoxsInfo = new ChangeBoxsInfo2();
                                List<ChangeBoxsInfo2.ChangeHistoriy> historiyList = cData.getChange_histories();
                                if (historiyList != null && historiyList.size() > 0){
                                    datas.add(mBoxsInfo.new Title("历史换件记录"));
                                    datas.addAll(historiyList);
                                }
                                List<ChangeBoxsInfo2.BoxList> boxList = cData.getBoxList();
                                if (boxList != null && boxList.size() > 0) {
                                    datas.add(mBoxsInfo.new Title("装箱单"));
                                    datas.addAll(boxList);
                                }
                                List<ChangeBoxsInfo2.BareBone> boneList = cData.getBare_bone_material();
                                if (boneList != null && boneList.size() > 0){
                                    datas.add(mBoxsInfo.new Title("BareBone"));
                                    datas.addAll(boneList);
                                }
                                mPackingAdapter2.notifyDataSetChanged();
                            } else {
                                mPackinputOCode.setVisibility(View.VISIBLE);
                            }
                        } else {
                            mPackinputOCode.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onError(Exception e) {
                    dismissLoadingDialog();
                    mPackinputOCode.setVisibility(View.VISIBLE);
                    Utils.showServerError(ChangePackingListActivity2.this);
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

        mPackingAdapter2.setOnItemClickListener(new PackingListAdapter2.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, ChangeBoxsInfo2.BoxList data) {

                // AddChangeRecordActivity.toAddChangeRecord(ChangePackingListActivity.this,data);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(AddChangeRecordActivity.PACKINGLIST, data);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        mPackingAdapter2.setOnHisClickListener(new PackingListAdapter2.OnRecyclerViewHisClickListener(){
            @Override
            public void onItemClick(View view, ChangeBoxsInfo2.ChangeHistoriy data) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(AddChangeRecordActivity.PACKINGHIS, data);
                intent.putExtras(bundle);
                setResult(-2, intent);
                finish();
            }
        });

        mPackingAdapter2.setOnBareClickListener(new PackingListAdapter2.OnRecyclerViewBareClickListener(){
            @Override
            public void onItemClick(View view, ChangeBoxsInfo2.BareBone data) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(AddChangeRecordActivity.PACKINGBARE, data);
                intent.putExtras(bundle);
                setResult(-3, intent);
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

    public static void toChangePack(Activity activity, String orderId) {
        Intent intent = new Intent(activity, ChangePackingListActivity2.class);
        intent.putExtra(PACKINGORDERID, orderId);
        // activity.startActivity(intent);
        activity.startActivityForResult(intent, 0);
    }


}
