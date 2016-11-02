package com.lenovo.csd.eservice.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.homedao.bean.Attachment;
import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.adapter.ServerAttachAdapter;
import com.lenovo.csd.eservice.cache.SharedPrefManager;
import com.lenovo.csd.eservice.entity.base.AttachmentData;
import com.lenovo.csd.eservice.http.HttpClientManager;
import com.lenovo.csd.eservice.http.NetInterface;
import com.lenovo.csd.eservice.http.ResultUtils;
import com.lenovo.csd.eservice.http.callback.adapter.JsonHttpCallBack;
import com.lenovo.csd.eservice.util.Utils;

import java.util.ArrayList;

public class UploadAttachFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {
    //UI
    private SwipeToLoadLayout mSwipLayout;
    private RecyclerView mRecyclerView;
    private LinearLayout mLinearNull;
    //变量声明
    private ArrayList<Attachment> attachments;
    private ServerAttachAdapter adapter;
    private SharedPreferences mSharedpreference;
    private String userCode;
    private String token;
    private int page = 1;

    //常量
    public static final String PAGE_SIZE = "10";

    public UploadAttachFragment() {
    }

    public static UploadAttachFragment newInstance() {
        UploadAttachFragment fragment = new UploadAttachFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_upload_attach, container, false);
        mSwipLayout = (SwipeToLoadLayout) root.findViewById(R.id.swipeToLoadLayout);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.swipe_target);
        mLinearNull = (LinearLayout) root.findViewById(R.id.linear_nullMsg);
        setClickLinstenner();
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 监听事件
     */
    private void setClickLinstenner() {
        mSwipLayout.setOnRefreshListener(this);
        mSwipLayout.setOnLoadMoreListener(this);
        mSwipLayout.setLoadMoreEnabled(true);
    }

    /**
     * 初始化
     */
    public void initData() {
        mSharedpreference = SharedPrefManager.getSystemSharedPref(getContext());
        userCode = mSharedpreference.getString(SharedPrefManager.LOGIN_USER_CODE, "");
        token = mSharedpreference.getString(SharedPrefManager.LOGIN_TOKEN, "");
        //recyclerview设置
        attachments = new ArrayList<>();
        adapter = new ServerAttachAdapter(getContext(), attachments);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        mLinearNull.setVisibility(View.VISIBLE);
        //开始加载
        getUploadAttachments();
        mSwipLayout.setRefreshing(true);
        getUploadAttachments();
    }

    /**
     * 加载已上传的数据
     */
    private void getUploadAttachments() {
        //网络条件判断
        if (Utils.checkInternetStatus(getContext()) == 0) {
            Toast.makeText(getContext(), R.string.text_internet_unavalible, Toast.LENGTH_SHORT).show();
            mSwipLayout.setRefreshing(false);
            return;
        }
//        Utils.showLoadingDialog(getContext());
        String url = NetInterface.GET_USERUPLOAD_ATTACHMENTS + userCode + "/" + page + "/" + PAGE_SIZE + "?token=" + token;
        HttpClientManager.get(url, new JsonHttpCallBack<AttachmentData>() {
            @Override
            public void onSuccess(AttachmentData result) {
//                dismissLoadingDialog();
                mSwipLayout.setRefreshing(false);
                mSwipLayout.setLoadingMore(false);
                if (ResultUtils.isSuccess(getContext(),result)){
                    AttachmentData.AttachDataBean attachDataBean = result.getData();
                    ArrayList<Attachment> list = attachDataBean.getList();
                    if (page == 1) {//刷新
                        adapter.refreshData(list, true);
                    } else if (page > 1) {//加载更多
                        adapter.refreshData(list, false);
                        if (list == null || list.size() == 0) {
                            //加载过程中数据为空或者数据错误，页码置未上一页
                            page--;
                        }
                    }
                    if (attachments.size() != 0) {
                        mLinearNull.setVisibility(View.GONE);
                    } else {
                        mLinearNull.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (page > 1) {
                        page--;
//                        Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onError(Exception e) {
//                dismissLoadingDialog();
                mSwipLayout.setRefreshing(false);
                mSwipLayout.setLoadingMore(false);
                try {
                Utils.showServerError(getContext());
                }catch (Exception e1){
                }
            }
        });
    }


    @Override
    public void onLoadMore() {
        if (Utils.checkInternetStatus(getContext()) == 0) {
            Toast.makeText(getContext(), R.string.text_internet_unavalible, Toast.LENGTH_SHORT).show();
            mSwipLayout.setLoadingMore(false);
            return;
        }
        page++;
        getUploadAttachments();
    }

    @Override
    public void onRefresh() {
        if (Utils.checkInternetStatus(getContext()) == 0) {
            Toast.makeText(getContext(), R.string.text_internet_unavalible, Toast.LENGTH_SHORT).show();
            mSwipLayout.setRefreshing(false);
            return;
        }
        page = 1;
        getUploadAttachments();
    }

    /**
     * 外部调用刷新操作
     */
    public void performRefresh() {
        if(Utils.checkInternetStatus(getContext()) == 0){
            Toast.makeText(getContext(),R.string.text_internet_unavalible,Toast.LENGTH_SHORT).show();
            return;
        }
        page = 1;
        mSwipLayout.setRefreshing(true);
        getUploadAttachments();
    }

}
