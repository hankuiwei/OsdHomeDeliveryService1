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

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.homedao.bean.Attachment;
import com.homedao.dao.AttachmentDao;
import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.adapter.LocalAttachAdapter;
import com.lenovo.csd.eservice.cache.SharedPrefManager;
import com.lenovo.csd.eservice.util.DaoUtils;

import java.util.ArrayList;

import de.greenrobot.dao.query.Query;


public class UnuploadAttachFragment extends BaseFragment implements OnRefreshListener {
    //UI
    private SwipeToLoadLayout mSwipLayout;
    private RecyclerView mRecyclerView;
    private LinearLayout mLinearNull;

    //变量声明
    private ArrayList<Attachment> attachments;
    private LocalAttachAdapter adapter;
    private AttachmentDao attachmentDao;
    private SharedPreferences mSharedpreference;
    private String userCode;

    private String token;
//    private


    public UnuploadAttachFragment() {
    }

    public static UnuploadAttachFragment newInstance() {
        UnuploadAttachFragment fragment = new UnuploadAttachFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_unupload_attach, container, false);
        mSwipLayout = (SwipeToLoadLayout) root.findViewById(R.id.swipeToLoadLayout);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.swipe_target);
        mLinearNull = (LinearLayout) root.findViewById(R.id.linear_nullMsg);
        setClickLinstenner();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
    }

    /**
     * 初始化
     */
    private void initData() {
        mSharedpreference = SharedPrefManager.getSystemSharedPref(getContext());
        userCode = mSharedpreference.getString(SharedPrefManager.LOGIN_USER_CODE, "");
        token = mSharedpreference.getString(SharedPrefManager.LOGIN_TOKEN, "");
        attachments = new ArrayList<>();
        attachmentDao = DaoUtils.getDaoSession(getContext()).getAttachmentDao();
        adapter = new LocalAttachAdapter(getActivity(), attachments, token);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        getDatafromDB();
    }

    /**
     * 监听事件
     */
    private void setClickLinstenner() {
        mSwipLayout.setOnRefreshListener(this);
//        mSwipLayout.setOnLoadMoreListener(this);
        mSwipLayout.setLoadMoreEnabled(false);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

//    //加载更多
//    @Override
//    public void onLoadMore() {
//
//    }

    //下拉刷新
    @Override
    public void onRefresh() {
        getDatafromDB();
        mSwipLayout.setRefreshing(false);
    }

    /**
     * 从数据库拿到最新的数据适配
     */
    public void getDatafromDB() {
        Query query = attachmentDao.queryBuilder().where(AttachmentDao.Properties.User_code.eq(userCode)).build();
        ArrayList<Attachment> list = query.list().size() == 0 ? null : (ArrayList<Attachment>) query.list();
        adapter.refreshData(list);
        if (attachments.size() == 0) {
            mLinearNull.setVisibility(View.VISIBLE);
        } else {
            mLinearNull.setVisibility(View.GONE);
        }
    }

    public void removeItem(int position) {
        attachments.remove(position);
        adapter.notifyItemRemoved(position);
    }

    /**
     * 退出前清除所有任务
     */
    public void clearTasks() {
        adapter.cancelAllTask();
    }
}
