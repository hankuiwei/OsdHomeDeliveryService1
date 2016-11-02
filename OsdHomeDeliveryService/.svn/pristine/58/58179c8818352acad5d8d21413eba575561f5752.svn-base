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
import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.adapter.UnusedPartAdapter;
import com.lenovo.csd.eservice.cache.SharedPrefManager;
import com.lenovo.csd.eservice.entity.base.UnusedPartData;
import com.lenovo.csd.eservice.http.HttpClientManager;
import com.lenovo.csd.eservice.http.NetInterface;
import com.lenovo.csd.eservice.http.ResultUtils;
import com.lenovo.csd.eservice.http.callback.adapter.JsonHttpCallBack;
import com.lenovo.csd.eservice.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class UnusedPartsFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {
    private SwipeToLoadLayout mSwipRefresh;
    private RecyclerView mRecyclerView;
    private LinearLayout mLinearNull;

    //变量
    private ArrayList datas;//数据
    private UnusedPartAdapter unUsedAdapter;
    private SharedPreferences mSharedPreference;
    private String token;
    private String userCode;
    private int pageIndex = 1;//加载的页码
    private boolean isLoading = false;

    private boolean isParentSelected;
    private boolean isSelfSelected = true;
    private boolean isFirstLoad = true;


    public UnusedPartsFragment() {
        // Required empty public constructor
    }

    public static UnusedPartsFragment newInstance() {
        UnusedPartsFragment fragment = new UnusedPartsFragment();
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
        View view = inflater.inflate(R.layout.fragment_parts_list, container, false);
        mSwipRefresh = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.swipe_target);
        mLinearNull = (LinearLayout) view.findViewById(R.id.linear_nullPartsMsg);
        initData();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mSharedPreference = SharedPrefManager.getSystemSharedPref(getActivity());
        userCode = mSharedPreference.getString(SharedPrefManager.LOGIN_USER_CODE, "");
        token = mSharedPreference.getString(SharedPrefManager.LOGIN_TOKEN, "");
        //设置RecyclerView
        datas = new ArrayList();
        unUsedAdapter = new UnusedPartAdapter(getContext(), datas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(unUsedAdapter);

        //设置监听
        mSwipRefresh.setOnRefreshListener(this);
        mSwipRefresh.setOnLoadMoreListener(this);
        mSwipRefresh.setLoadMoreEnabled(true);
        //网络加载数据
//        getUnusedParts();
    }

    /**
     * 获取备件列表
     */
    private void getUnusedParts() {
        if (Utils.checkInternetStatus(getActivity()) == 0) {
            if (mSwipRefresh != null) {
                mSwipRefresh.setLoadingMore(false);
                mSwipRefresh.setRefreshing(false);
            }
            if (isResumed()) {
                Toast.makeText(getActivity(), R.string.text_internet_unavalible, Toast.LENGTH_SHORT).show();
            }
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("user_code", userCode);
        params.put("type", SparePartFragment.UNUSED_TYPE);
        params.put("page_index", pageIndex + "");
        params.put("page_size", SparePartFragment.PAGE_SIZE);
        showLoadingDialog();
        isLoading = true;
        HttpClientManager.post(NetInterface.GET_PARTS_LIST + "token=" + token, params, new JsonHttpCallBack<UnusedPartData>() {
            @Override
            public void onSuccess(UnusedPartData result) {
                dismissLoadingDialog();
                isFirstLoad = false;
                ;
                mSwipRefresh.setLoadingMore(false);
                mSwipRefresh.setRefreshing(false);
                isLoading = false;
                if (ResultUtils.isSuccess(getContext(), result)) {
                    UnusedPartData.UnusedPartBean bean = result.getBean();
                    ArrayList<UnusedPartData.UnusedPartItemData> list = bean.getBorrow_order_info();
                    if (list != null && list.size() > 0) {//数据正确返回
                        if (pageIndex == 1) {//下拉刷新的数据
                            unUsedAdapter.refreshData(list);
                        } else {//上拉加载的数据
                            unUsedAdapter.addData(list);
                        }
                    } else {//state为200，但数据有问题
                        if (pageIndex != 1) {//如果页面不是首页，页码减去1
                            pageIndex--;
                        }
                    }

                }
                showNull();
            }

            @Override
            public void onError(Exception e) {
                isFirstLoad = false;
                dismissLoadingDialog();
                mSwipRefresh.setLoadingMore(false);
                mSwipRefresh.setRefreshing(false);
                isLoading = false;
                showNull();
                try {
                    Utils.showServerError(getContext());
                } catch (Exception e1) {
                }
            }
        });

    }

    private void showNull() {
        if (datas == null || datas.size() == 0) {
            mLinearNull.setVisibility(View.VISIBLE);
        } else {
            mLinearNull.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadMore() {
        if (isLoading)
            return;
        pageIndex++;
        getUnusedParts();
    }

    @Override
    public void onRefresh() {
        if (isLoading)
            return;
        pageIndex = 1;
        getUnusedParts();

    }

    public void setParentSelet(boolean parentSelect) {
        isParentSelected = parentSelect;
        if (isFirstLoad && isParentSelected && isSelfSelected) {
            getUnusedParts();
        }
    }

    public void setSelfSelect(boolean selfSelect) {
        isSelfSelected = selfSelect;
        if (isFirstLoad && isParentSelected && isSelfSelected) {
            getUnusedParts();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }
}
