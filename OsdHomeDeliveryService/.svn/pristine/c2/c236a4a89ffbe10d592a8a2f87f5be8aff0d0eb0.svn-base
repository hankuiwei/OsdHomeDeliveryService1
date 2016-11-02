package com.lenovo.csd.eservice.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.adapter.CustomInfoMoreAdapter;
import com.lenovo.csd.eservice.entity.base.ContactData;
import com.lenovo.csd.eservice.entity.base.OrderDetailAllData;
import com.lenovo.csd.eservice.entity.base.TypeData;
import com.lenovo.csd.eservice.http.HttpClientManager;
import com.lenovo.csd.eservice.http.NetInterface;
import com.lenovo.csd.eservice.http.ResultUtils;
import com.lenovo.csd.eservice.http.callback.NoDoubleClickLinstenner;
import com.lenovo.csd.eservice.http.callback.adapter.JsonHttpCallBack;
import com.lenovo.csd.eservice.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class MoreAboutCustomActivity extends BaseActivity {
    private RelativeLayout mRelBack;
    private RecyclerView mRecycler;

    private CustomInfoMoreAdapter mAdapter;
    private ArrayList<TypeData> datas;//Recycler数据源
    private String token;
    private String userCode;
    private String orderID;
    private OrderDetailAllData.OrderDetailBean detailBean;
    private boolean isLoading;

    public static String CUSTOM_BEAN = "customer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void provideLayout() {
        setContentView(R.layout.activity_more_about_custom);

    }

    @Override
    protected void initView() {
        mRelBack = (RelativeLayout) findViewById(R.id.relative_back);
        mRecycler = (RecyclerView) findViewById(R.id.recycler_contactRecords);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData() {
        //接收客户信息bean
        detailBean = getIntent().getParcelableExtra(CUSTOM_BEAN);
        if (detailBean != null) {
            orderID = detailBean.getOrder_id();
        }
        datas = new ArrayList<>();
        token = Utils.getToken(this);
        userCode = Utils.getUserCode(this);
        mAdapter = new CustomInfoMoreAdapter(this, datas);
        mRecycler.setAdapter(mAdapter);

        //加载数据
        getContactRecord();
    }

    @Override
    protected void setClickLintenner() {
        mRelBack.setOnClickListener(noDoubleclick);
    }

    private NoDoubleClickLinstenner noDoubleclick = new NoDoubleClickLinstenner() {
        @Override
        public void doClick(View view) {
            switch (view.getId()) {
                case R.id.relative_back:
                    finish();
                    break;
//             case R.id.

            }
        }
    };

    /**
     * 获取用户接触记录
     */
    private void getContactRecord() {
        if (Utils.checkInternetStatus(this) == 0) {
            Toast.makeText(this, R.string.text_internet_unavalible, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userCode) || TextUtils.isEmpty(orderID)) {
            Toast.makeText(this, "工单编号有误", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isLoading)
            return;
        HashMap<String, String> params = new HashMap<>();
        params.put("user_code", userCode);
        params.put("order_id", orderID);
        showLoadingDialog();
        isLoading = true;
        HttpClientManager.post(NetInterface.GET_ORDER_CONSULTRECORD + token, params, new JsonHttpCallBack<ContactData>() {
            @Override
            public void onSuccess(ContactData result) {
                dismissLoadingDialog();
                isLoading = false;
                if (ResultUtils.isSuccess(MoreAboutCustomActivity.this, result)) {
                    mRecycler.setVisibility(View.VISIBLE);
                    datas.clear();
                    ArrayList<ContactData.ContactItemData> contactItemDatas = result.getData();
                    if (detailBean == null) {
                        OrderDetailAllData data = new OrderDetailAllData();
                        detailBean = new OrderDetailAllData.OrderDetailBean();
                    }
                    datas.add(detailBean);
                    if (contactItemDatas != null && contactItemDatas.size() > 0) {
                        datas.add(result.new MoreCustomTitle("接触记录"));
                        datas.addAll(contactItemDatas);
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    mRecycler.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Exception e) {
                isLoading = false;
                dismissLoadingDialog();
                mRecycler.setVisibility(View.GONE);
                Utils.showServerError(MoreAboutCustomActivity.this);
            }
        });
    }

    /**
     * 跳转到更多用户信息的界面
     *
     * @param context
     */
    public static void openMoreAboutCustomerAct(Context context, OrderDetailAllData.OrderDetailBean bean) {
        Intent orderIntent = new Intent(context, MoreAboutCustomActivity.class);
        orderIntent.putExtra(CUSTOM_BEAN, bean);
        context.startActivity(orderIntent);
    }
}
