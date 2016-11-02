package com.lenovo.csd.eservice.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.homedao.bean.Attachment;
import com.homedao.dao.AttachmentDao;
import com.homedao.dao.DaoSession;
import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.adapter.AttachmentAdapter;
import com.lenovo.csd.eservice.cache.SharedPrefManager;
import com.lenovo.csd.eservice.entity.base.AttachmentData;
import com.lenovo.csd.eservice.http.HttpClientManager;
import com.lenovo.csd.eservice.http.NetInterface;
import com.lenovo.csd.eservice.http.ResultUtils;
import com.lenovo.csd.eservice.http.callback.NoDoubleClickLinstenner;
import com.lenovo.csd.eservice.http.callback.adapter.JsonHttpCallBack;
import com.lenovo.csd.eservice.util.DaoUtils;
import com.lenovo.csd.eservice.util.Utils;

import java.util.ArrayList;

import de.greenrobot.dao.query.Query;

public class OrderAttachmentActivity extends BaseActivity {
    private RelativeLayout relativeBack;
    private RecyclerView mRecyclerView;//列表
    private LinearLayout mLinearAddPieces;//添加按钮
    //变量
    private AttachmentAdapter attachmentAdapter;
    private SharedPreferences sharedPreferences;
    private String orderID;//工单ID
    private String token;
    private String userCode;
    private ArrayList<Attachment> datas;

    //常量
    public static final String ORDER_CODE = "order_code";//传递OrderCode key
    public static final int REQUEST_CODE = 101;//请求添加附件请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void provideLayout() {
        setContentView(R.layout.activity_order_attachment);

    }

    @Override
    protected void initView() {
        relativeBack = (RelativeLayout) findViewById(R.id.relative_back);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_AttachmentList);
        mLinearAddPieces = (LinearLayout) findViewById(R.id.linear_addPieces);

    }

    @Override
    protected void initData() {
        sharedPreferences = SharedPrefManager.getSystemSharedPref(this);
        userCode = sharedPreferences.getString(SharedPrefManager.LOGIN_USER_CODE, "");
        token = sharedPreferences.getString(SharedPrefManager.LOGIN_TOKEN, "");
        //获取OrderCode
        try {
            orderID = getIntent().getStringExtra(ORDER_CODE);
        } catch (Exception e) {
            orderID = "";
        }
        //Recyclerview Init
        datas = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        attachmentAdapter = new AttachmentAdapter(this, datas);
        mRecyclerView.setAdapter(attachmentAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        getAttachmentFiles();

    }

    @Override
    protected void setClickLintenner() {
        relativeBack.setOnClickListener(noDoubleClickLinstenner);
        mLinearAddPieces.setOnClickListener(noDoubleClickLinstenner);
    }

    /**
     * 获取已上传的附件列表
     */
    private void getAttachmentFiles() {
        //1，判断网络
        if (Utils.checkInternetStatus(this) == 0) {
            Toast.makeText(this, R.string.text_internet_unavalible, Toast.LENGTH_SHORT).show();
        }
        showLoadingDialog();
        String url = NetInterface.GET_ATTACHMENTLIST_INIT + userCode + "/" + orderID + "?token=" + token;
        HttpClientManager.get(url, new JsonHttpCallBack<AttachmentData>() {
            @Override
            public void onSuccess(AttachmentData result) {
                dismissLoadingDialog();
                if (ResultUtils.isSuccess(OrderAttachmentActivity.this,result)) {//成功返回
                    ArrayList<Attachment> list = result.getData().getList();
                    datas.clear();
                    if (list != null && list.size() > 0) {
                        datas.addAll(list);
                    }
                    //添加数据库数据，并且刷新列表
                    if (getLocalAttaches() != null) {
                        datas.addAll(getLocalAttaches());
                    }
                    attachmentAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Exception e) {
                dismissLoadingDialog();
                Utils.showServerError(OrderAttachmentActivity.this);
            }
        });
    }

    private NoDoubleClickLinstenner noDoubleClickLinstenner = new NoDoubleClickLinstenner() {
        @Override
        public void doClick(View view) {
            switch (view.getId()) {
                case R.id.relative_back:
                    finish();
                    break;
                case R.id.linear_addPieces:
                    AddAttachmentActivity.openAddAttachMentActivity(OrderAttachmentActivity.this, orderID, REQUEST_CODE);
                    break;
            }
        }
    };

    public static void openBackUpPiecesActivity(Context context, String orderID) {
        Intent intent_backPieces = new Intent(context, OrderAttachmentActivity.class);
        intent_backPieces.putExtra(OrderAttachmentActivity.ORDER_CODE, orderID);
        context.startActivity(intent_backPieces);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            //附件添加成功，重新加载附近列表和从数据库中获取未上传的附件列表
            getAttachmentFiles();
        }
    }

    /**
     * 数据库查询数据
     */
    private ArrayList<Attachment> getLocalAttaches() {
        DaoSession session = DaoUtils.getDaoSession(this);
        AttachmentDao atDao = session.getAttachmentDao();
        Query query = atDao.queryBuilder().where(AttachmentDao.Properties.Order_code.eq(orderID)).build();
        if (query.list() != null && query.list().size() != 0) {
            ArrayList<Attachment> attaches = (ArrayList<Attachment>) query.list();
            return attaches;
        }
        return null;
    }
}
