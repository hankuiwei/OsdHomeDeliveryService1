package com.lenovo.csd.eservice.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.adapter.HomeSpinnerAdapter;
import com.lenovo.csd.eservice.cache.SharedPrefManager;
import com.lenovo.csd.eservice.entity.base.InitUnChange;
import com.lenovo.csd.eservice.entity.base.LoginData;
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
public class AddUnChangeRecordActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{

    private final String TAG = getClass().getSimpleName();

    private LinearLayout mnotChangeBack;
    private Spinner mSpinComponentClass;
    private Spinner mSpinNonChangParts;
    private Button  mAddNotChangeCommit;
    private static final String NOTCHANGEORDERID = "notChangeOrderId";
    public static final String NOTCHANGETYPE = "notChangeSelectType";
    public static final String NOTCHANGEACTION = "notChangeSelectAction";
    private String orderId;
    private Map<String, String> params;
    private int ccSentstate;
    private  List<InitUnChange.Action> actionList;
    /*private List<String> mSPTDescList;
    private List<String> actionValueList;*/
    private ArrayList<String> mSPTDescList;
    private ArrayList<String> actionValueList;
    private Map<String,String> mSPTypeMap;
    private Map<String,List<InitUnChange.Action>> actionMap;
    //private Map<String,String> ActionMap;

    /*private ArrayAdapter<String> cClassAdapter;
    private ArrayAdapter<String> cPartsAdapter;*/

    private HomeSpinnerAdapter cClassSpinnerAdapter;
    private HomeSpinnerAdapter cPartsSpinnerAdapter;


    @Override
    protected void provideLayout() {
            setContentView(R.layout.activity_addnotchange);
    }

    @Override
    protected void initView() {
        mnotChangeBack = (LinearLayout) findViewById(R.id.notChangeBack);
        mSpinComponentClass = (Spinner) findViewById(R.id.Spin_componentClass);
        mSpinNonChangParts = (Spinner) findViewById(R.id.Spin_nonChangingParts);
        mAddNotChangeCommit = (Button) findViewById(R.id.Btn_AddNotChangeCommit);
        actionList = new ArrayList<InitUnChange.Action>();
        mSPTDescList = new ArrayList<String>();
        actionValueList = new ArrayList<String>();
        mSPTypeMap = new HashMap<String, String>();
        actionMap = new HashMap<String, List<InitUnChange.Action>>();
        //ActionMap = new HashMap<String, String>();
        mSPTDescList.add("请选择");
        //actionValueList.add("请选择");

       /* cClassAdapter = new ArrayAdapter<String>(AddUnChangeRecordActivity.this,R.layout.item_change_spinner_select,R.id.text1,mSPTDescList);
        cPartsAdapter = new ArrayAdapter<String>(AddUnChangeRecordActivity.this,R.layout.item_change_spinner_select,R.id.text1,actionValueList);*/
        cClassSpinnerAdapter = new HomeSpinnerAdapter(AddUnChangeRecordActivity.this,mSPTDescList);
        cPartsSpinnerAdapter = new HomeSpinnerAdapter(AddUnChangeRecordActivity.this,actionValueList);
        mSpinComponentClass.setAdapter(cClassSpinnerAdapter);
        mSpinNonChangParts.setAdapter(cPartsSpinnerAdapter);
    }

    @Override
    protected void initData() {
        // TODO: 16-8-25 网络状态放在工单处理页点击添加换件时进行校验，无网络将不跳转到此页面
        int netState =  Utils.checkInternetStatus(AddUnChangeRecordActivity.this);
        if (netState == 0) {
            Toast.makeText(AddUnChangeRecordActivity.this,"网络不可用,请连接网络后重试",Toast.LENGTH_SHORT).show();
            return;
        }
        showLoadingDialog();
        params = new HashMap<String, String>();
        Intent intent = getIntent();
        orderId = intent.getStringExtra(NOTCHANGEORDERID);
        SharedPreferences preferences = SharedPrefManager.getSystemSharedPref(AddUnChangeRecordActivity.this);
        String userCode = SharedPrefManager.getStringInSharePref(preferences, SharedPrefManager.LOGIN_USER_CODE, "userCode");
        String token = SharedPrefManager.getStringInSharePref(preferences, SharedPrefManager.LOGIN_TOKEN, "mToken");

        //token = "g38SmC5uY7sUeg31RJtsGLrCywrXkDsC8asCKRv08vNLgOWdmvYIKLDYGcluDANw";
        //userCode = "C47018";
       // orderId = "201607132100310002";//"201604282100310003";//"201601212100310001";//"201605052100319002";//"201605262100310001";

        if (!"userCode".equals(userCode) && !"mToken".equals(token)){
            params.put("token", token);
            params.put("user_code", userCode);
        } else {
            dismissLoadingDialog();
            Toast.makeText(AddUnChangeRecordActivity.this,"请登录！",Toast.LENGTH_LONG).show();
            return;
        }

        if (orderId != null) {
            params.put("order_code", orderId);

            String url = NetInterface.INIT_UNCHANGE +"/"+orderId +"/"+userCode+"?token="+token;
            HttpClientManager.get(url, new JsonHttpCallBack<InitUnChange>() {
                @Override
                public void onSuccess(InitUnChange result) {
                    if (ResultUtils.isSuccess(AddUnChangeRecordActivity.this,result)) {
                        InitUnChange.UnChangeInfo unChangeInfo = result.getData();
                        ccSentstate = unChangeInfo.getCCSend();
                        List<InitUnChange.MatelialClass> matelialClasses = unChangeInfo.getMatelialClasss();
                        for (InitUnChange.MatelialClass mclass : matelialClasses) {
                            //actionList.addAll(mclass.getActions());
                            String typeCode = mclass.getSPType();
                            String typeDesc = mclass.getSPTypeDesc();
                            mSPTDescList.add(typeDesc);
                            mSPTypeMap.put(typeDesc, typeCode);
                            actionMap.put(typeDesc,mclass.getActions());
                        }
                        //cClassAdapter.notifyDataSetChanged();
                        cClassSpinnerAdapter.notifyDataSetChanged();
                        /*for (InitUnChange.Action action : actionList) {
                            String aValue = action.getValue();
                            actionValueList.add(aValue);
                        }
                        cPartsAdapter.notifyDataSetChanged();*/
                        dismissLoadingDialog();
                    } else {
                        dismissLoadingDialog();
                    }
                }

                @Override
                public void onError(Exception e) {
                    dismissLoadingDialog();
                    Utils.showServerError(AddUnChangeRecordActivity.this);
                }
            });
        }
    }

    @Override
    protected void setClickLintenner() {
        mnotChangeBack.setOnClickListener(this);
        mSpinComponentClass.setOnItemSelectedListener(this);
        mAddNotChangeCommit.setOnClickListener(this);


    }

    public static void toAddUnChange(Activity activity,String orderCode,int reqCode){
        Intent intent = new Intent(activity,AddUnChangeRecordActivity.class);
        intent.putExtra(NOTCHANGEORDERID,orderCode);
        activity.startActivityForResult(intent,reqCode);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.notChangeBack:
                    finish();
                    break;
                case R.id.Btn_AddNotChangeCommit:
                    int netState =  Utils.checkInternetStatus(AddUnChangeRecordActivity.this);
                    if (netState == 0) {
                        Toast.makeText(AddUnChangeRecordActivity.this,"网络不可用,请连接网络后重试",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (null == orderId || "".equals(orderId)){
                        return;
                    }
                    showLoadingDialog();
                    if (ccSentstate == 2) {
                        dismissLoadingDialog();
                        Toast.makeText(AddUnChangeRecordActivity.this,"只能查看，不能提交",Toast.LENGTH_SHORT).show();
                        return;
                    } else if (ccSentstate == 1) {
                        dismissLoadingDialog();
                        Toast.makeText(AddUnChangeRecordActivity.this,"不能进行操作",Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        final String selectType = mSpinComponentClass.getSelectedItem().toString();
                        final String selectAction = mSpinNonChangParts.getSelectedItem().toString();
                        if ("请选择".equals(selectType) || "请选择".equals(selectAction)){
                            dismissLoadingDialog();
                            Toast.makeText(AddUnChangeRecordActivity.this,"请选择部件大类或非换件动作",Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            final String typeCode = mSPTypeMap.get(selectType);
                            params.put("sp_type",typeCode);
                            params.put("action",selectAction);
                            HttpClientManager.post(NetInterface.ADD_UNCHANGE, params, new JsonHttpCallBack<LoginData>() {
                                @Override
                                public void onSuccess(LoginData result) {
                                    if (ResultUtils.isSuccess(AddUnChangeRecordActivity.this,result)) {
                                        dismissLoadingDialog();
                                        /*Intent intent = new Intent();
                                        Bundle bundle = new Bundle();
                                        bundle.putString(NOTCHANGETYPE,selectType);
                                        bundle.putString(NOTCHANGEACTION,selectAction);
                                        intent.putExtras(bundle);
                                        */
                                        setResult(RESULT_OK,new Intent());
                                        finish();
                                    } else {
                                        dismissLoadingDialog();
                                       // dismissLoadingDialog();
                                        String err = result.getMessage();
                                        Toast.makeText(AddUnChangeRecordActivity.this,err,Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onError(Exception e) {
                                    dismissLoadingDialog();
                                    Utils.showServerError(AddUnChangeRecordActivity.this);
                                }
                            });
                        }
                    }
                break;
            }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.Spin_componentClass:
                actionValueList.clear();
                if (position != 0){
                    String type = mSPTDescList.get(position);

                    if (type !=null && !"".equals(type)){
                        List<InitUnChange.Action> actions = actionMap.get(type);
                        actionList.clear();

                        for (InitUnChange.Action action : actions){
                            actionValueList.add(action.getValue());
                        }

                    }

                } else {
                    actionValueList.add("请选择");
                }
                //cPartsAdapter.notifyDataSetChanged();
                cPartsSpinnerAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
