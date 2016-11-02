package com.lenovo.csd.eservice.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.activity.MoreAboutMachineActivity;
import com.lenovo.csd.eservice.adapter.MachineMoreAdapter;
import com.lenovo.csd.eservice.cache.SharedPrefManager;
import com.lenovo.csd.eservice.entity.base.MachineInfoData;
import com.lenovo.csd.eservice.entity.base.TypeData;
import com.lenovo.csd.eservice.http.HttpClientManager;
import com.lenovo.csd.eservice.http.NetInterface;
import com.lenovo.csd.eservice.http.ResultUtils;
import com.lenovo.csd.eservice.http.callback.NoDoubleClickLinstenner;
import com.lenovo.csd.eservice.http.callback.adapter.JsonHttpCallBack;
import com.lenovo.csd.eservice.util.Utils;
import com.lenovo.csd.eservice.zxing.MipcaActivityCapture;

import java.util.ArrayList;
import java.util.HashMap;


public class MachineMoreFragment extends BaseFragment {
    private EditText mEditSN;
    private ImageView mImgShowScan;
    private Button mBtnSearch;
    private RecyclerView mRecycler;
    //变量
    private String productSN;//主机SN号码
    private SharedPreferences mSharedPreference;
    private String token;
    private ArrayList<TypeData> datas;
    private MachineMoreAdapter machineMoreAdapter;
    private boolean showScan;
    //常量
    public static final String PARAM_SN = "product_sn";
    public static final String PARAM_SCAN = "show_scan";

    public MachineMoreFragment() {
        // Required empty public constructor
    }

    public static MachineMoreFragment newInstance(String sn, boolean showScan) {
        MachineMoreFragment fragment = new MachineMoreFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_SN, sn);
        args.putBoolean(PARAM_SCAN, showScan);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productSN = getArguments().getString(PARAM_SN) == null ? "" : getArguments().getString(PARAM_SN);
            showScan = getArguments().getBoolean(PARAM_SCAN);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_machine_more, container, false);
        mEditSN = (EditText) view.findViewById(R.id.edit_inputSN);
        mImgShowScan = (ImageView) view.findViewById(R.id.imgScanQRcode);
        mBtnSearch = (Button) view.findViewById(R.id.btn_searchSN);
        mRecycler = (RecyclerView) view.findViewById(R.id.recyclerKeepRepair);

        mBtnSearch.setOnClickListener(noDoubleClickLinstenner);
        mImgShowScan.setOnClickListener(noDoubleClickLinstenner);
        mEditSN.setText(productSN);
        mEditSN.setSelection(productSN.length());
        initData();
        getMachineInfo(productSN);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void initData() {
        mSharedPreference = SharedPrefManager.getSystemSharedPref(getContext());
        token = mSharedPreference.getString(SharedPrefManager.LOGIN_TOKEN, "");
        datas = new ArrayList<>();
        machineMoreAdapter = new MachineMoreAdapter(getActivity(), datas);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.setAdapter(machineMoreAdapter);
        if (showScan) {
            mImgShowScan.setVisibility(View.VISIBLE);
        }
    }

    public void getMachineInfo(String SN) {
        mEditSN.setText("");
        mEditSN.setText(SN);
        mEditSN.setSelection(SN.length());
        if (Utils.checkInternetStatus(getContext()) == 0) {
            Toast.makeText(getContext(), R.string.text_internet_unavalible, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(SN)) {
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("product_no", SN);
        showLoadingDialog();
        HttpClientManager.post(NetInterface.GET_MACHINE_INIFO + "token=" + token, params, new JsonHttpCallBack<MachineInfoData>() {
            @Override
            public void onSuccess(MachineInfoData result) {
                dismissLoadingDialog();
                if (ResultUtils.isSuccess(getContext(), result)) {
                    mRecycler.setVisibility(View.VISIBLE);
                    mRecycler.setVisibility(View.VISIBLE);
                    datas.clear();
                    MachineInfoData.MachineDataArray array = result.getData();
                    MachineInfoData.MachineData machineData = array.getMachine_info();
                    ArrayList<MachineInfoData.WarrantyData> warrantyDatas = array.getWarranty_info();

                    if (machineData != null) {
                        datas.add(machineData);
                    }
                    if (warrantyDatas != null && warrantyDatas.size() > 0) {
                        datas.add(result.new TitleData("保修信息"));
                        datas.addAll(warrantyDatas);
                    }
                    machineMoreAdapter.notifyDataSetChanged();
                } else {
                    mRecycler.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Exception e) {
                dismissLoadingDialog();
                mRecycler.setVisibility(View.GONE);
                try {
                    Utils.showServerError(getContext());

                } catch (Exception el) {
                }

            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private NoDoubleClickLinstenner noDoubleClickLinstenner = new NoDoubleClickLinstenner() {
        @Override
        public void doClick(View view) {
            switch (view.getId()) {
                case R.id.btn_searchSN:
                    String sn = mEditSN.getText().toString().trim();
                    if (sn != null && !TextUtils.isEmpty(sn)) {
                        getMachineInfo(sn);
                    } else {
                        Toast.makeText(getContext(), R.string.text_input_sn, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.imgScanQRcode:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            if (isResumed() && getActivity() != null)
                                getActivity().requestPermissions(new String[]{Manifest.permission.CAMERA}, MoreAboutMachineActivity.REQUEST_MACHINECAMARA_ALLOW);
                        } else {
                            openCamera();
                        }
                    } else {
                        openCamera();
                    }
                    break;
            }
        }
    };

    /**
     * 打开相机扫码
     */
    public void openCamera() {
        Intent scanIntent = new Intent(getActivity(), MipcaActivityCapture.class);
        getActivity().startActivityForResult(scanIntent, MoreAboutMachineActivity.REQUEST_MACHINE);
    }
}
