package com.lenovo.csd.eservice.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.ServiceApplication;
import com.lenovo.csd.eservice.adapter.HomeSpinnerAdapter;
import com.lenovo.csd.eservice.adapter.OrderTaskAdapter;
import com.lenovo.csd.eservice.adapter.PieceRecordAdapter;
import com.lenovo.csd.eservice.cache.SharedPrefManager;
import com.lenovo.csd.eservice.entity.base.BaseData;
import com.lenovo.csd.eservice.entity.base.ChangeRecordData;
import com.lenovo.csd.eservice.entity.base.CurrentTaskData;
import com.lenovo.csd.eservice.entity.base.FirstEvent;
import com.lenovo.csd.eservice.entity.base.OrderDetailAllData;
import com.lenovo.csd.eservice.entity.base.OrderTask;
import com.lenovo.csd.eservice.entity.base.QRcodeUrlData;
import com.lenovo.csd.eservice.entity.base.SimpleMsgData;
import com.lenovo.csd.eservice.entity.base.TaskHistorys;
import com.lenovo.csd.eservice.fragment.QRCodeFragment;
import com.lenovo.csd.eservice.http.ErrorCode;
import com.lenovo.csd.eservice.http.HttpClientManager;
import com.lenovo.csd.eservice.http.NetInterface;
import com.lenovo.csd.eservice.http.ResultUtils;
import com.lenovo.csd.eservice.http.callback.NoDoubleClickLinstenner;
import com.lenovo.csd.eservice.http.callback.adapter.JsonHttpCallBack;
import com.lenovo.csd.eservice.services.LocationService;
import com.lenovo.csd.eservice.util.BaiduNavigation;
import com.lenovo.csd.eservice.util.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OperateOrderActivity extends BaseActivity implements PieceRecordAdapter.DeleteLinstenner {
    private DrawerLayout mDrawerLayout;//根视图
    private ScrollView mScrollView;//主视图
    private LinearLayout mDrawLinear;//抽屉视图
    private RelativeLayout mRelaBack;//回退
    private TextView mTxtOrderId;//订单ID
    private RelativeLayout mRelaMenu;//菜单
    //工单信息
    private TextView mTxtOrderTile;//工单名称
    private TextView mTxtOrderStatus;//工单状态
    private TextView mTxtOrderDate;//工单预约时间
    private View mViewOrderTable;//工单信息view
    //工单table中的hildView
    private TextView mTxtCustomer;
    private TableRow mTableCustom;
    private TableRow mTablePhones;
    private TableRow mTableAddress;
    private TableRow mTableMachineInfo;
    private LinearLayout mLinearPhones;
    private TextView mTxtPhones;
    private LinearLayout mLinearAdd;
    private TextView mTxtAddress;
    private TextView mTxtDeviceInfo;
    private TextView mTxtProblemDes;
    private LinearLayout linearPartTopLine;
    private TableRow mTablePieces;
    private LinearLayout mLinearParts;//备件容器
    private TableRow mTableProblem;
    private LinearLayout linearCallbackTopline;
    private TableRow mTableCallback;
    private TextView mTxtCallBack;


    //回填上门结果Linear
    private LinearLayout mLinearPiecesRecord;//
    private LinearLayout mLinearChangePiecesItems;//换件记录
    //    private TextView mTxtNoRecords;
    private LinearLayout mLinearBtns;//换件非换件按钮父容器
    private RecyclerView mRecyclerView;//换件记录Recycler
    private LinearLayout mLinearAddChangePiece;//添加换件按钮
    private LinearLayout mLinearAddUnchangePiece;//添加非换件按钮
    private PieceRecordAdapter pieceAdapter;
    private LinearLayout mLinearCostFee;
    private TextView mTxtCostFee;


    //操作部分
//    private PopupWindow mPopWindow;
    private LinearLayout mLinearGap;
    private LinearLayout mLinearOperate;//
    private TextView mTxtOperateName;//操作名称
    private LinearLayout mLinearContent;//动态添加容器
    private EditText editMark;//备注
    private EditText editPrice;//价格
    //    private EditText editPrice;//价格填写
    private Spinner spinner1;//一级sinner
    private Spinner spinner2;//二级sinner
    //    private LinearLayout mTimeLinear;
    private TextView text1;//一级Text
    private TextView text2;//二级Text
    private View reasonLayout;
    private RadioGroup reasonGroup;//改约原因Parent
    private View timeLayout;//时间选择layout


    //时间和日期的选择对话框
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private RecyclerView mRecyclerRecords;
    private LinearLayout mLinearInstructions;
    private TextView mTxtInstruction;//说明
    private ImageView mImageQR;
    private TextView mTxtNoQRdesc;
    private Button mBtnCommit;//提交按钮
    private LinearLayout mLinearBlankView;

    //drawer中的视图
    private LinearLayout mLinear_drawerMenu;//菜单Linear
    private LinearLayout mLinear_drawerAttachment;//附件
    private LinearLayout mLinear_drawerOrderFlow;//流程
    private LinearLayout mLinear_drawerMoreInfoCustomer;//更多客户信息
    private LinearLayout mLinear_drawerMoreInfoMachine;//更多机器信息
    private LinearLayout mLinear_drawerRelatedTech;//相关技术通报
    private LinearLayout mLinear_drawerCopyOrderId;//复制主机编号
    private LinearLayout mLinearCopyParent;//复制主机编号外层布局


    //变量
    //工单详情中的数据
    private List<TaskHistorys> mTaskHistoryList;//工单历史数据
    private OrderTaskAdapter mTaskAdapter;
    private OrderDetailAllData.OrderDetailBean detailBean;
    private OrderDetailAllData.OrderOperation operation;
    private ArrayList<OrderDetailAllData.BackupPiece> backupPieces;
    private ArrayList<ChangeRecordData.RecordItem> changePieceRecords;
    //当前任务中的数据
    private CurrentTaskData.CurrentTaskBean taskBean;
    private String taskName;//任务名称
    private ArrayList<CurrentTaskData.TaskSource> taskSources;//任务控件资源

    private String mOrderID;//工单信息实体
    private String mProductSN;
    private SharedPreferences sharedPreferences;
    private String token;
    private String userCode;//
    private HomeSpinnerAdapter mSpinnerAdapter;//Spinner的adapter
    private HomeSpinnerAdapter mSecondeSpinnerAdapter;//Spinner2的adapter
    private ArrayList<String> contactResult;
    private String currentTaskId;//当前的任务步骤

    //获取下一步的操作参数
    private String msg = "";//提示信息
    private String action = "";//提价任务的信息
    private String remark = "";//备注
    private String nextTaskCode = "";//工单下个任务
    private String currentTaskName;
    private String result = "";//选择的值
    private String onSitePosition = "";//现场位置
    private double cusLatitude;//现场纬度
    private double cusLongtitude;//现场经度
    private String onSiteAddress = "";//现场地址
    private double myLatitude;
    private double myLongtitude;
    private String myPosition = "";
    private String myAddress = "";
    private String instance = "";//距离
    private String reason = "";//改约原因
    private ArrayList<String> ids;
    private int markRule;//备注的规则
    private int resultRule;//结果规则
    private String state;
    private int reservationView;


    public static final String ORDER_ID = "order_id";
    private static final String ONLY_ID = "only_id";
    private static final String ORDER_STATE = "order_state";
    //获取任务类型常量
    public static final String RULE_NULL = "0";//无规则
    public static final String RULE_NON_EDIT = "1";//不可编辑
    public static final String RULE_NOT_NULL = "3";//必填
    public static final String RULE_MORETHAN_CURRENTTIME = "4";//大于当前时间
    public static final String RULE_MORETHAN_ZERO = "5";//大于0
    public static final String RULE_NOTE = "6";//备注

    //数据UI类型
    public static final String UI_TYPE_TEXTVIEW = "10";
    public static final String UI_DROPDOWN_SELECT = "20";
    public static final String UI_TYPE_SYSTEMTIME = "30";
    public static final String UI_TYPE_EDITTIME = "40";
    public static final String UI_TYPE_EIDTPRICE = "50";
    public static final String UI_TYPE_EDITDATE = "60";
    public static final String UI_TYPE_SPINNER = "70";
    public static final String UI_TYPE_DROPDOWN_MULTICHOICE1 = "80";
    public static final String UI_TYPE_DROPDWON_MULTICHOICE2 = "90";
    public static final String UI_TYPE_DROPDOWN_MULTICHOICE3 = "100";
    public static final String UI_TYPE_DROPDOWN_MULTICHOICE4 = "110";
    public static final String UI_TYPE_INTEGER = "120";
    public static final String UI_TYPE_BUTTON = "130";
    public static final String UI_TYPE_WORKORDER_IDENTIFY = "140";

    private static final int REQUEST_CHANGERECORD = 10;
    private static final int REQUEST_UNCHANGERECORD = 11;
    private static final int REQUEST_ORDER_FLOW = 12;
    private static final int DATE_TAG = 0;
    private static final int TIME_TAG = 1;
    private static final int REQUST_COLLECT = 13;

    //定位
    public LocationClient mLocationClient = null;
    private LocationClientOption mOption = null;
    private boolean isLoading;
    private boolean showQRFrag;//需要显示过渡二维码？
    private String qrUrl;//二维码链接

    //private ServiceApplication mApp;
    // private OrderUnFinishFragment.UnHandler mHandler;
    // private OrderFinishFragment.FHandler mFHandler;
    private boolean isRefresh;
    private LocationService locService;
    private boolean isNavigation = true;//是否是导航时定位
    private boolean isResume;//activity是否处于活动状态
//    private boolean isRefreshUIfail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void provideLayout() {
        setContentView(R.layout.activity_operate_order);
    }

    @Override
    protected void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mScrollView = (ScrollView) findViewById(R.id.scroll_operateActivity);
        mDrawLinear = (LinearLayout) findViewById(R.id.drawer_view);
        mRelaBack = (RelativeLayout) findViewById(R.id.relative_back);
        mTxtOrderId = (TextView) findViewById(R.id.textView_operateOrderTitleId);
        mRelaMenu = (RelativeLayout) findViewById(R.id.relative_menuMore);
        mTxtOrderTile = (TextView) findViewById(R.id.text_orderName);
        mTxtOrderStatus = (TextView) findViewById(R.id.text_orderStatus);
        mTxtOrderDate = (TextView) findViewById(R.id.text_orderTime);
        mViewOrderTable = findViewById(R.id.view_orderTable);
        mTxtCustomer = (TextView) findViewById(R.id.txTName);
        mTableCustom = (TableRow) findViewById(R.id.tableRowName);
        mTablePhones = (TableRow) findViewById(R.id.tableRowTel);
        mLinearPhones = (LinearLayout) findViewById(R.id.relativePhones);
        mTxtPhones = (TextView) findViewById(R.id.txtPhones);
        mTableAddress = (TableRow) findViewById(R.id.tableRowAddress);
        mLinearAdd = (LinearLayout) findViewById(R.id.linear_customerAdd);
        mTxtAddress = (TextView) findViewById(R.id.txtAddress);
        mTableMachineInfo = (TableRow) findViewById(R.id.tableRowProduct);
        mTxtDeviceInfo = (TextView) findViewById(R.id.txtProduct);
        linearPartTopLine = (LinearLayout) findViewById(R.id.tablePieces_topLine);
        mTablePieces = (TableRow) findViewById(R.id.tableRowParts);
        mLinearParts = (LinearLayout) findViewById(R.id.relative_backUpPieces);
        mTableProblem = (TableRow) findViewById(R.id.tableRowFailure);
        mTxtProblemDes = (TextView) findViewById(R.id.txtFailure);
        linearCallbackTopline = (LinearLayout) findViewById(R.id.tableRowCallback_topLine);
        mTableCallback = (TableRow) findViewById(R.id.tableRowCallback);
        mTxtCallBack = (TextView) findViewById(R.id.txtCallBack);
        linearCallbackTopline.setVisibility(View.VISIBLE);
        mTableCallback.setVisibility(View.VISIBLE);


        mLinearPiecesRecord = (LinearLayout) findViewById(R.id.linear_piecesRecord);
        mLinearChangePiecesItems = (LinearLayout) findViewById(R.id.linearChangePieceItems);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_piecesRecord);
        mLinearAddChangePiece = (LinearLayout) findViewById(R.id.linear_linearAddChangedPiece);
        mLinearAddUnchangePiece = (LinearLayout) findViewById(R.id.linear_linearAddUnchangedPiece);
        mLinearCostFee = (LinearLayout) findViewById(R.id.linear_costFee);
        mTxtCostFee = (TextView) findViewById(R.id.text_costFee);

        mLinearGap = (LinearLayout) findViewById(R.id.linearGap);
        mLinearOperate = (LinearLayout) findViewById(R.id.linear_operatePart);
        mTxtOperateName = (TextView) findViewById(R.id.text_operateName);
        mLinearContent = (LinearLayout) findViewById(R.id.linear_operateContent);
        mLinearBtns = (LinearLayout) findViewById(R.id.linear_AddBtns);
        mLinearInstructions = (LinearLayout) findViewById(R.id.linear_Instruction);
        mTxtInstruction = (TextView) findViewById(R.id.text_operatInstructs);
        mImageQR = (ImageView) findViewById(R.id.image_qrCode);
        mTxtNoQRdesc = (TextView) findViewById(R.id.textNoQRcodeDesc);

        mBtnCommit = (Button) findViewById(R.id.btn_commit);
        mRecyclerRecords = (RecyclerView) findViewById(R.id.recyclerRecords);
        mLinearBlankView = (LinearLayout) findViewById(R.id.linearBlankView);

        mLinear_drawerMenu = (LinearLayout) findViewById(R.id.linear_drawer_menu);
        mLinear_drawerAttachment = (LinearLayout) findViewById(R.id.linear_backupPieces);
        mLinear_drawerOrderFlow = (LinearLayout) findViewById(R.id.linear_workOrderFlow);
        mLinear_drawerMoreInfoCustomer = (LinearLayout) findViewById(R.id.linear_customerMoreInfo);
        mLinear_drawerMoreInfoMachine = (LinearLayout) findViewById(R.id.linear_deviceMoreInfo);
        mLinear_drawerRelatedTech = (LinearLayout) findViewById(R.id.linear_technologyInfoRelated);
        mLinear_drawerCopyOrderId = (LinearLayout) findViewById(R.id.linear_copyOrderId);
        mLinearCopyParent = (LinearLayout) findViewById(R.id.linearCopySN);
    }

    @Override
    protected void initData() {
        //  mApp = (ServiceApplication) getApplication();
        //mHandler = mApp.getHandler();
        //mFHandler = mApp.getFhandler();
        //变量初始化
        sharedPreferences = SharedPrefManager.getSystemSharedPref(this);
        token = sharedPreferences.getString(SharedPrefManager.LOGIN_TOKEN, "");
        userCode = sharedPreferences.getString(SharedPrefManager.LOGIN_USER_CODE, "");
        //接收Intent
        try {
            Bundle bundle = getIntent().getExtras();
            mOrderID = bundle.getString(ORDER_ID);
            mTxtOrderId.setText(mOrderID);
            state = bundle.getString(ORDER_STATE);
        } catch (Exception e) {
            mOrderID = "";
        }
        changePieceRecords = new ArrayList<>();
        pieceAdapter = new PieceRecordAdapter(this, changePieceRecords);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(pieceAdapter);

        //定位初始化
//        mLocationClient = new LocationClient(getApplicationContext());
//        mLocationClient.registerLocationListener(this);
        locService = ((ServiceApplication) getApplication()).locationService;
//        mPopWindow = new PopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        mPopWindow.setFocusable(true);
//        mPopWindow.setOutsideTouchable(false);
//        mPopWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        getOrderInfo(mOrderID);

    }

    public void showPicker(int TAG) {
        datePickerDialog = null;
        timePickerDialog = null;
        int year = 0;
        int month = 0;
        int day = 0;
        int minute = 0;
        int hour = 0;
        Calendar calendar = Calendar.getInstance();
        try {
            String time = text1.getText().toString().trim() + " " + text2.getText().toString().trim();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
            Date date = sdf.parse(time);
            calendar.setTime(date);
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
        } catch (ParseException e) {
            e.printStackTrace();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            minute = calendar.get(Calendar.MINUTE);
            hour = calendar.get(Calendar.HOUR);
            calendar = null;
        }

        if (TAG == DATE_TAG) {
            datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    text1.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                    if (ids != null && ids.contains(result)) {//如果当前是联系客户结果，时间由time字段承接

                    } else {
                        result = text1.getText().toString() + " " + text2.getText().toString().toString();
                    }
                }
            }, year, month, day);
            datePickerDialog.show();
        } else if (TAG == TIME_TAG) {
            timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    if (ids != null && ids.contains(result)) {//如果当前是联系客户结果，时间由time字段承接

                    } else {
                        result = text1.getText().toString() + " " + text2.getText().toString().toString();
                    }
                    text2.setText(hourOfDay + "时" + minute + "分");
                }
            }, hour, minute, true);
            timePickerDialog.show();
        }


    }

    @Override
    protected void setClickLintenner() {
        mRelaBack.setOnClickListener(noDoubleClickLinstenner);
        mRelaMenu.setOnClickListener(noDoubleClickLinstenner);
        mLinearAdd.setOnClickListener(noDoubleClickLinstenner);

        mLinearAddChangePiece.setOnClickListener(noDoubleClickLinstenner);
        mLinearAddUnchangePiece.setOnClickListener(noDoubleClickLinstenner);
        mBtnCommit.setOnClickListener(noDoubleClickLinstenner);

        mLinear_drawerMenu.setOnClickListener(noDoubleClickLinstenner);
        mLinear_drawerAttachment.setOnClickListener(noDoubleClickLinstenner);
        mLinear_drawerOrderFlow.setOnClickListener(noDoubleClickLinstenner);
        mLinear_drawerMoreInfoCustomer.setOnClickListener(noDoubleClickLinstenner);
        mLinear_drawerMoreInfoMachine.setOnClickListener(noDoubleClickLinstenner);
        mLinear_drawerRelatedTech.setOnClickListener(noDoubleClickLinstenner);
        mLinear_drawerCopyOrderId.setOnClickListener(noDoubleClickLinstenner);
        mImageQR.setOnClickListener(noDoubleClickLinstenner);
        mLinearPhones.setOnClickListener(noDoubleClickLinstenner);
        mTxtAddress.setOnClickListener(noDoubleClickLinstenner);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    /**
     * 点击事件的监听
     */
    private NoDoubleClickLinstenner noDoubleClickLinstenner = new NoDoubleClickLinstenner() {
        @Override
        public void doClick(View view) {
            switch (view.getId()) {
                case R.id.relative_back:
                    // mHandler.sendEmptyMessage(1);
                    // mFHandler.sendEmptyMessage(1);

                    EventBus.getDefault().post(new FirstEvent("refish"));
                    finish();
                    break;
                case R.id.relative_menuMore:
                    if (!mDrawerLayout.isDrawerOpen(mDrawLinear)) {
                        mDrawerLayout.openDrawer(mDrawLinear);
                    }
                    break;
                case R.id.linear_linearAddChangedPiece://添加换件
                    AddChangeRecordActivity.toAddChangeRecord(OperateOrderActivity.this, mOrderID, mProductSN, REQUEST_CHANGERECORD);
                    break;
                case R.id.linear_linearAddUnchangedPiece://添加非换件
                    AddUnChangeRecordActivity.toAddUnChange(OperateOrderActivity.this, mOrderID, REQUEST_UNCHANGERECORD);
                    break;
                case R.id.btn_commit:
                    isRefresh = true;
//                    if (isLoading)//正在加载，拒绝请求
//                        return;
                    if (Utils.checkInternetStatus(OperateOrderActivity.this) == 0) {//网络判断
                        Toast.makeText(OperateOrderActivity.this, getResources().getString(R.string.text_internet_unavalible), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (detailBean == null) {
                        Toast.makeText(OperateOrderActivity.this, "未获取到工单信息", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mBtnCommit.getText().toString().trim().equals("刷 新")) {
                        getOrderInfo(detailBean.getOrder_id());
                        return;
                    }
                    if (detailBean.getOrder_state_id().equals("200") && mBtnCommit.getText().toString().equals("回填信息")) {
                        CollectUserinfoActivity.toCollect(REQUST_COLLECT,OperateOrderActivity.this, mOrderID, detailBean.getOrder_type_name());
                    } else {//工单步骤处理
                        if (result.equals("10701") || result.equals("10702")) {
                            getConnectWithCustom();
                        } else {
                            /*注释掉老版本中只在这三步获取位置的判断*/
//                            if (taskName.contains("现在出发") || taskName.contains("确认到达") || taskName.contains("确认离开")) {
                                getLocation();
//                            } else {
//                                getNextStep();
//                            }
                        }
                    }
                    break;
                case R.id.linear_drawer_menu:
                    mDrawerLayout.closeDrawers();
                    break;
                case R.id.linear_backupPieces:
                    mDrawerLayout.closeDrawers();
                    if (detailBean != null) {
                        OrderAttachmentActivity.openBackUpPiecesActivity(OperateOrderActivity.this, mOrderID);
                    } else {
                        Toast.makeText(OperateOrderActivity.this, R.string.text_unget_orderinfo, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.linear_workOrderFlow:
                    mDrawerLayout.closeDrawers();
                    if (detailBean != null)
                        OrdertaskActivity.toOrderTask(OperateOrderActivity.this, mOrderID, detailBean.getOrder_state_name(), REQUEST_ORDER_FLOW);
                    else {
                        Toast.makeText(OperateOrderActivity.this, R.string.text_unget_orderinfo, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.linear_customerMoreInfo:
                    mDrawerLayout.closeDrawers();
                    if (detailBean != null) {
                        MoreAboutCustomActivity.openMoreAboutCustomerAct(OperateOrderActivity.this, detailBean);
                    } else {
                        Toast.makeText(OperateOrderActivity.this, R.string.text_unget_orderinfo, Toast.LENGTH_SHORT).show();
                    }
//                    Toast.makeText(OperateOrderActivity.this, R.string.text_function_tobeopened, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.linear_deviceMoreInfo:
                    mDrawerLayout.closeDrawers();
                    if (detailBean != null)
                        MoreAboutMachineActivity.openMoreAboutMachineAct(OperateOrderActivity.this, detailBean.getProduct_sn(), MoreAboutMachineActivity.TYPE_BOTH);
                    else {
                        Toast.makeText(OperateOrderActivity.this, R.string.text_unget_orderinfo, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.linear_technologyInfoRelated:
                    mDrawerLayout.closeDrawers();
                    if (detailBean != null)
                        TechRelatedActivity.openTechRelatedAct(OperateOrderActivity.this, detailBean.getProduct_type());
                    else {
                        Toast.makeText(OperateOrderActivity.this, R.string.text_unget_orderinfo, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.linear_copyOrderId:
                    mDrawerLayout.closeDrawers();
                    if (detailBean != null) {
                        Utils.copyStr(OperateOrderActivity.this, detailBean.getProduct_sn(), R.string.text_ordersn_copied);
                    } else {
                        Toast.makeText(OperateOrderActivity.this, R.string.text_unget_orderinfo, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.image_qrCode:
                    if (qrUrl != null)
                        showQRDialog(qrUrl);
                    break;
                case R.id.relativePhones:
                    String num = mTxtPhones.getText().toString().trim();
                    Utils.callNumber(OperateOrderActivity.this, num);
                    break;
                case R.id.txtAddress:
                    new BaiduNavigation(OperateOrderActivity.this, OperateOrderActivity.this, onSiteAddress);
                    break;
            }
        }
    };

    /**
     * 联系客户结果
     */
    public void getConnectWithCustom() {
        if (text1 == null || text2 == null) {
            Toast.makeText(OperateOrderActivity.this, "param error", Toast.LENGTH_SHORT).show();
            return;
        }
        String preTime = text1.getText().toString() + " " + text2.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
        try {
            Date date = sdf.parse(preTime);
            long pickedTime = date.getTime();
            long currentTime = System.currentTimeMillis();
            if (pickedTime < currentTime) {
                Toast.makeText(OperateOrderActivity.this, "预约时间不能早于当前时间", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        remark = editMark.getText().toString().trim();
        if (markRule == 3) {
//            if (remark == null || TextUtils.isEmpty(remark)) {
//                Toast.makeText(OperateOrderActivity.this, "请输入备注内容", Toast.LENGTH_SHORT).show();
//                return;
//            }
        }
        if (result == null || TextUtils.isEmpty(result)) {
            Toast.makeText(OperateOrderActivity.this, "请选择一项结果", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("user_code", userCode);
        params.put("order_id", mOrderID);
        params.put("task_id", currentTaskId);
        params.put("pre_time", preTime);
        params.put("contact_result", result);
        params.put("reason", reason);
        params.put("remark", remark);
        showLoadingDialog();
//        isLoading = true;
        HttpClientManager.post(NetInterface.GET_CONNECTT0_CUSTOMER + "token=" + token, params, new JsonHttpCallBack<SimpleMsgData>() {
            @Override
            public void onSuccess(SimpleMsgData result) {
                if (result != null && Integer.parseInt(result.getStatus_code()) == ErrorCode.STATUS_SUCCESS) {//联系客户结果成功，获取下一步状态
                    //重置所有的字段
                    remark = "";//备注
                    nextTaskCode = "";//工单下个任务
                    OperateOrderActivity.this.result = "";//选择的值
                    onSitePosition = "";//现场位置
                    onSiteAddress = "";//现场地址
                    instance = "";//距离
                    reason = "";//改约原因
                    markRule = 0;//备注的规则
                    //成功后获取当前状态
                    getOrderInfo(mOrderID);
                } else {
                    if (result != null && result.getData() != null) {
                        SimpleMsgData.Data data = result.getData();
                        Toast.makeText(OperateOrderActivity.this, data.getResult_msg(), Toast.LENGTH_SHORT).show();
                    }
//                    isLoading = false;
                    dismissLoadingDialog();
                }
            }

            @Override
            public void onError(Exception e) {
                dismissLoadingDialog();
//                isLoading = false;
                Utils.showServerError(OperateOrderActivity.this);
            }
        });

    }

    /**
     * 提交进入到下一步
     */
    public void getNextStep() {
        if (isLoading)
            return;
        //判断当前所处的步骤是否是后台去操作的步骤
        if (detailBean == null) {
            Toast.makeText(OperateOrderActivity.this, R.string.text_unget_orderinfo, Toast.LENGTH_SHORT).show();
            dismissLoadingDialog();
            return;
        }
        try {
            int orderId = Integer.parseInt(detailBean.getOrder_state_id());
            if (orderId != 40 && orderId != 60 && orderId != 65 && orderId != 70 && orderId != 80 && orderId != 115 && orderId != 120 && orderId != 130
                    && orderId != 140 && orderId != 190 && orderId != 335 && orderId != 330) {
                Toast.makeText(OperateOrderActivity.this, R.string.text_order_handlebackground, Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(OperateOrderActivity.this, "数据发生错误", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editMark != null) {
            remark = editMark.getText().toString().trim();
        }
        if (editPrice != null) {
            result = editPrice.getText().toString().trim();
            switch (resultRule) {
                case 1:
                    break;
                case 3:
                    break;
                case 5:
                    if (!TextUtils.isEmpty(result)) {
                        try {
                            float price = Float.parseFloat(result);
                            if (price < 0) {
                                Toast.makeText(OperateOrderActivity.this, "输入金额有误，请重新输入", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (Exception e) {
                            Toast.makeText(OperateOrderActivity.this, "输入金额有误，请重新输入", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    break;
            }
        }
        if (text1 != null && text2 != null && timeLayout.getVisibility() == View.VISIBLE) {
            result = text1.getText().toString() + " " + text2.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
            //去除时间验证
//            try {
//                Date date = sdf.parse(result);
//                long pickedTime = date.getTime();
//                if (pickedTime < new Date().getTime()) {
//                    Toast.makeText(OperateOrderActivity.this, "预约时间不能早于当前时间", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//                return;
//            }
        }

//        markRule

        HashMap<String, String> params = new HashMap<>();
        params.put("msg", msg);
        params.put("action", action);
        params.put("user_code", userCode);
        params.put("order_id", mOrderID);
        params.put("remark", remark);
        params.put("next_task_code", nextTaskCode);
        params.put("result", result);
        params.put("on_site_position", myPosition);
        params.put("on_site_address", myAddress);
        params.put("instance", instance);
        params.put("latitude", myLatitude+"");
        params.put("longitude", myLongtitude+"");
        params.put("name",taskName);
        params.put("code", detailBean.getCurrent_task_code());
        showLoadingDialog();
        isLoading = true;
        HttpClientManager.post(NetInterface.GET_NEXT_STEP + "token=" + token, params, new JsonHttpCallBack<SimpleMsgData>() {
            @Override
            public void onSuccess(SimpleMsgData result) {
                isLoading = false;
                if (result != null && Integer.parseInt(result.getStatus_code()) == ErrorCode.STATUS_SUCCESS) {
                    //重置所有字段
                    remark = "";//备注
                    nextTaskCode = "";//工单下个任务
                    OperateOrderActivity.this.result = "";//选择的值
                    onSitePosition = "";//现场位置
                    onSiteAddress = "";//现场地址
                    instance = "";//距离
                    reason = "";//改约原因
                    markRule = 0;//备注的规则
                    resultRule = 0;
                    myAddress = "";
                    myPosition = "";
                    //拉取当前状态
                    getOrderInfo(mOrderID);
                } else {
                    dismissLoadingDialog();
//                    isLoading = false;
                    if (result != null) {
                        Toast.makeText(OperateOrderActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(OperateOrderActivity.this, "提交任务发生异常，请稍后重试", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onError(Exception e) {
                dismissLoadingDialog();
                isLoading = false;
                Utils.showServerError(OperateOrderActivity.this);
            }
        });
    }

    /**
     * 获取当前工单任务
     */
    public void getCurrentTask() {
        if (Utils.checkInternetStatus(this) == 0) {//网络判断
            Toast.makeText(this, getResources().getString(R.string.text_internet_unavalible), Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("user_code", userCode);
        params.put("order_id", mOrderID);
        showLoadingDialog();
//        isLoading = true;
        HttpClientManager.post(NetInterface.GET_ORDER_TASK + "token=" + token, params, new JsonHttpCallBack<CurrentTaskData>() {
            @Override
            public void onSuccess(CurrentTaskData result) {
//                dismissLoadingDialog();
                if (ResultUtils.isSuccess(OperateOrderActivity.this, result)) {
//                    isLoading = false;
                    //显示步骤状态
                    taskBean = result.getTaskBean().get(0);
                    taskName = taskBean.getTask_name() == null ? "" : taskBean.getTask_name();
                    mTxtOperateName.setText(taskName);
//                    String orderType = detailBean.getOrder_type_name();
                    //判断订单状态
                    mBtnCommit.setText("提交");
                    mLinearPiecesRecord.setVisibility(View.GONE);//先隐藏
                    if (taskName.contains("上门结果")) {//回填上门结果，添加附件
                        if (reservationView == 1) {
                            mLinearPiecesRecord.setVisibility(View.VISIBLE);
                            mLinearChangePiecesItems.setVisibility(View.VISIBLE);
                            getChangePiecesRecord(false);//拉取换件记录
                        } else {
                            mLinearPiecesRecord.setVisibility(View.GONE);
                            mLinearChangePiecesItems.setVisibility(View.GONE);
                            dismissLoadingDialog();
                        }
                    } else {
                        //只渲染页面不加载网络
                        if (taskBean.getTask_sources() != null && taskBean.getTask_sources().size() != 0) {
                            dismissLoadingDialog();
//                            isLoading = false;
                        }

                        if (taskName.equals("预约结果")) {//预约结果

                        } else if (taskName.equals("联系客户结果")) {//联系客户结果
                            mBtnCommit.setText(getResources().getString(R.string.text_commit));
                        } else if (taskName.equals("确认到达现场")) {//确认到达现场
                            mBtnCommit.setText(taskName);
                        } else if (taskName.equals("确认离开现场")) {//确认离开现场
                            mBtnCommit.setText(taskName);
                        } else if (taskName.equals("收费金额")) {//收费金额
                            mBtnCommit.setText(getResources().getString(R.string.text_commit));
                        } else if (taskName.equals("现在出发")) {//现在出发
                            mBtnCommit.setText(getResources().getString(R.string.text_start_out));
                        }
                    }

                    //填写工单详情信息
                    fillOrderDetailInfo();
                    //如果任务为空或者数组长度是0.任务服务完成
                    if (taskBean.getTask_sources() == null || taskBean.getTask_sources().size() == 0) {
                        //清空任务控件,隐藏不需要的组件
                        mLinearContent.removeAllViews();
                        hideAll();
                        //显示记录recyclerview
                        mRecyclerRecords.setVisibility(View.VISIBLE);
                        mRecyclerRecords.setLayoutManager(new LinearLayoutManager(OperateOrderActivity.this));
                        mTaskHistoryList = new ArrayList<TaskHistorys>();
                        mTaskAdapter = new OrderTaskAdapter(mTaskHistoryList, OperateOrderActivity.this);
                        mTaskAdapter.setNeedBack(false);
                        mRecyclerRecords.setAdapter(mTaskAdapter);
                        getHistory();
                        mBtnCommit.setText("服务完");
                        return;
                    }
                    mRecyclerRecords.setVisibility(View.GONE);
                    //继续绘制控件
                    taskSources = (ArrayList<CurrentTaskData.TaskSource>) taskBean.getTask_sources();
                    drawWeidgets(taskSources);
                } else {
                    dismissLoadingDialog();
//                    isLoading = false;
                    Utils.showServerError(OperateOrderActivity.this);
                    mBtnCommit.setText("刷 新");
                }
            }

            @Override
            public void onError(Exception e) {
                dismissLoadingDialog();
//                isLoading = false;
                Utils.showServerError(OperateOrderActivity.this);
                mBtnCommit.setText("刷 新");
            }
        });
    }


    /**
     * 获取工单详情
     */
    private void getOrderInfo(final String orderId) {
        if (Utils.checkInternetStatus(this) == 0) {
            Toast.makeText(this, R.string.text_internet_unavalible, Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("user_code", userCode);
        params.put("order_id", orderId);
        showLoadingDialog();
//        isLoading = true;
        HttpClientManager.post(NetInterface.ORDER_DETAIL_ALLINFO + "token=" + token, params, new JsonHttpCallBack<OrderDetailAllData>() {
            @Override
            public void onSuccess(OrderDetailAllData result) {
                if (ResultUtils.isSuccess(OperateOrderActivity.this, result)) {
                    OrderDetailAllData.OrderBaseData baseData = result.getData();
                    if (baseData != null) {
                        detailBean = baseData.getOrder_detail();
                        operation = baseData.getOrder_operations();
                        backupPieces = baseData.getBorrow_order_info();
                        if (operation != null) {
                            try {
                                reservationView = operation.getReservation_record_view();
                            } catch (Exception e) {
                                reservationView = 1;
                            }
                        }
                        //需要的工单数据
                        if (detailBean == null) {
                            Toast.makeText(OperateOrderActivity.this, "工单数据异常", Toast.LENGTH_SHORT).show();
                            dismissLoadingDialog();
                            return;
                        }
                        nextTaskCode = detailBean.getCurrent_task_code() == null ? "" : detailBean.getCurrent_task_code();
                        onSiteAddress = detailBean.getCustomer_address();
                        onSitePosition = detailBean.getCustomer_baidu_location() == null ? "" : detailBean.getCustomer_baidu_location();
                        //判断有无SN码，在侧边栏中动态显示或隐藏复制SN码功能
                        if (!TextUtils.isEmpty(detailBean.getProduct_sn())) {
                            mLinearCopyParent.setVisibility(View.VISIBLE);
                        } else {
                            mLinearCopyParent.setVisibility(View.GONE);
                        }
                    }
                    if (detailBean == null) {
                        dismissLoadingDialog();
                        return;
                    }
                    //如果工单状态是已完成，显示已完成状态
                    String orderStateId = detailBean.getOrder_state_id();
                    if (TextUtils.isEmpty(orderStateId)) {
                        mScrollView.setVisibility(View.GONE);
                        dismissLoadingDialog();
                        mRelaMenu.setEnabled(false);
                        Toast.makeText(OperateOrderActivity.this, R.string.text_unget_orderinfo, Toast.LENGTH_SHORT).show();
//                        mDrawerLayout.setEnabled(false);
                        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        return;
                    } else {
                        mRelaMenu.setEnabled(true);
                        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    }

                    if (orderStateId.equals("200")) {
                        dismissLoadingDialog();//结束分支
//                        isLoading = false;
                        //工单已完成显示各种具体信息
                        hideAll();
                        fillOrderDetailInfo();
                        getQRcodeUrl();//加载二维码
                        //隐藏工单操作视图，
                        if (TextUtils.isEmpty(detailBean.getAmount())) {//判断是否有费用显示
                            mLinearCostFee.setVisibility(View.GONE);
                        } else {
                            mLinearPiecesRecord.setVisibility(View.VISIBLE);
                            mLinearCostFee.setVisibility(View.VISIBLE);
                            mTxtCostFee.setText("￥" + detailBean.getAmount());
                        }
                        getChangePiecesRecord(true);
                        mLinearInstructions.setVisibility(View.GONE);
//判断是否可点击回填
                        mBtnCommit.setEnabled(false);
                        if (!TextUtils.isEmpty(detailBean.getIs_submit_questionnaire()) && detailBean.getIs_submit_questionnaire().equals("0") && !TextUtils.isEmpty(detailBean.getQuestionnaire_url())) {
                            mBtnCommit.setText("回填信息");
                            mBtnCommit.setEnabled(true);
                            mBtnCommit.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_bg_redbtn));
                        } else if (!TextUtils.isEmpty(detailBean.getIs_submit_questionnaire()) && detailBean.getIs_submit_questionnaire().equals("0") && TextUtils.isEmpty(detailBean.getQuestionnaire_url())) {
                            mBtnCommit.setText("已超过回填信息时效");
                            mBtnCommit.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_bg_graybtn));
                        } else if (!TextUtils.isEmpty(detailBean.getIs_submit_questionnaire()) && detailBean.getIs_submit_questionnaire().equals("1")) {
                            mBtnCommit.setText("已回填信息");
                            mBtnCommit.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_bg_graybtn));
                        } else if (!TextUtils.isEmpty(detailBean.getIs_submit_questionnaire()) && detailBean.getIs_submit_questionnaire().equals("2")) {
                            mBtnCommit.setText("无需回填信息");
                            mBtnCommit.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_bg_graybtn));
                        } else if (!TextUtils.isEmpty(detailBean.getIs_submit_questionnaire()) && detailBean.getIs_submit_questionnaire().equals("3")) {
                            mBtnCommit.setText("未到需回填信息时间");
                            mBtnCommit.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_bg_graybtn));
                        } else {
                            mBtnCommit.setVisibility(View.GONE);
                        }
                    } else {//未完成状态，拉取任务接口
                        //只有在工单完成页的所有自己都隐藏
                        showQRFrag = true;
                        mLinearCostFee.setVisibility(View.GONE);
                        mImageQR.setVisibility(View.GONE);
                        getCurrentTask();
                    }
                } else {// 请求错误
//                    Utils.showServerError(OperateOrderActivity.this);
                    dismissLoadingDialog();
//                    isLoading = false;
                }
            }

            @Override
            public void onError(Exception e) {
                dismissLoadingDialog();
//                isLoading = false;
                Utils.showServerError(OperateOrderActivity.this);
            }
        });
    }

    /**
     * 绘制当前任务所需要的控件信息
     *
     * @param taskSources
     */
    private void drawWeidgets(ArrayList<CurrentTaskData.TaskSource> taskSources) {
        //绘制前清空所有的变量
        result = "";
        resultRule = 0;
        remark = "";
        markRule = 0;
        reason = "";
        editMark = null;
        editPrice = null;
        text1 = null;
        text2 = null;
        timeLayout = null;
        //
        mLinearOperate.setVisibility(View.VISIBLE);
        mLinearContent.setVisibility(View.VISIBLE);

        if (taskSources == null) {
            return;
        }
        mLinearContent.removeAllViews();
        for (CurrentTaskData.TaskSource source : taskSources) {
            if (source.getCome_types() == null || source.getCome_types().size() == 0) {
                Toast.makeText(OperateOrderActivity.this, "没有任务信息", Toast.LENGTH_SHORT).show();
                return;
            }
            //遍历ComeType
            for (CurrentTaskData.ComeType comeType : source.getCome_types()) {
                String comeName = comeType.getCome();
                ArrayList<CurrentTaskData.TabControl> tabControls = (ArrayList<CurrentTaskData.TabControl>) comeType.getTab_controls();
                String rule = comeType.getRule();
                String type = comeType.getType();

                //根据类型绘制控件
                switch (type) {
                    case UI_TYPE_TEXTVIEW://文本型
                        if (rule.equals(RULE_NOTE)) {
                            View viewEdit = getLayoutInflater().inflate(R.layout.operate_item_edit, null);
                            mLinearContent.addView(viewEdit);
                            editMark = (EditText) viewEdit.findViewById(R.id.editItem);
                            editMark.setHint(comeName);
                            markRule = Integer.parseInt(rule);
                            if (markRule == 1) {
                                editMark.setEnabled(false);
                            }
                        } else {
                            View viewEdit = getLayoutInflater().inflate(R.layout.operate_item_edit, null);
                            mLinearContent.addView(viewEdit);
                            resultRule = Integer.parseInt(rule);
                            editPrice = (EditText) viewEdit.findViewById(R.id.editItem);
                            editPrice.setHint(comeName);
                            if (resultRule == 1) {
                                editPrice.setEnabled(false);
                            }
                        }

                        break;
                    case UI_DROPDOWN_SELECT:
                        View spinnerLayout = getLayoutInflater().inflate(R.layout.operate_item_spinner, null);
                        final ArrayList<String> conditions = new ArrayList<>();
                        resultRule = Integer.parseInt(rule);

                        if (ids != null) {
                            ids.clear();
                        } else {
                            ids = new ArrayList<>();
                        }
                        for (CurrentTaskData.TabControl control : tabControls) {
                            conditions.add(control.getName());
                            ids.add(control.getId());
                        }
                        spinner1 = (Spinner) spinnerLayout.findViewById(R.id.spinner_operate);
//                        final RelativeLayout arrow = (RelativeLayout) spinnerLayout.findViewById(R.id.relArrow);
//                        LinearLayout linearParent = (LinearLayout) spinnerLayout.findViewById(R.id.linearSelectParent);
                        HomeSpinnerAdapter adapter = new HomeSpinnerAdapter(this, conditions);
                        spinner1.setAdapter(adapter);
                        mLinearContent.addView(spinnerLayout);
                        if (conditions != null && conditions.size() > 0 && ids != null && ids.size() > 0) {
                            result = ids.get(0);
//                            spinner1.setText(conditions.get(0));/*不需要手动设置显示内容*/
                        }
                        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                                spinner1.setBackgroundDrawable(null);
                                result = ids.get(position);
                                //联系客户结果，监听spinner选择状态
                                if (reasonGroup == null || timeLayout == null)
                                    return;
                                if (result.equals("10701") || result.equals("10702")) {//显示时间和原因
                                    reasonLayout.setVisibility(View.VISIBLE);
                                    timeLayout.setVisibility(View.VISIBLE);
                                } else {
                                    reasonLayout.setVisibility(View.GONE);
                                    timeLayout.setVisibility(View.GONE);
                                }
                            }

                            //
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
//                        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                result = ids.get(position);
//
//                                //联系客户结果，监听spinner选择状态
//                                if (reasonGroup == null || timeLayout == null)
//                                    return;
//                                if (result.equals("10701") || result.equals("10702")) {//显示时间和原因
//                                    reasonLayout.setVisibility(View.VISIBLE);
//                                    timeLayout.setVisibility(View.VISIBLE);
//                                } else {
//                                    reasonLayout.setVisibility(View.GONE);
//                                    timeLayout.setVisibility(View.GONE);
//                                }
//                                spinner1.setText(conditions.get(position));
//                                mPopWindow.dismiss();
//                            }
//                        };
//                        final View view = initSelectionList(listener, adapter);
//                        linearParent.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                mPopWindow.setContentView(view);
//                                mPopWindow.showAsDropDown(arrow, 0, 0);
//                            }
//                        });


                        //联系客户结果会有的控件
                        reasonLayout = getLayoutInflater().inflate(R.layout.operate_item_singlechoice, null);
                        reasonGroup = (RadioGroup) reasonLayout.findViewById(R.id.radioReasons);
                        reasonLayout.setVisibility(View.GONE);
                        reason = getString(R.string.text_reason_user);
                        reasonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                RadioButton button = (RadioButton) reasonLayout.findViewById(checkedId);
                                reason = button.getText().toString();
                            }
                        });
                        mLinearContent.addView(reasonLayout);
                        timeLayout = getLayoutInflater().inflate(R.layout.operate_item_textview, null);
                        timeLayout.setVisibility(View.GONE);
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH) + 1;
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int minute = calendar.get(Calendar.MINUTE);
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        calendar = null;
                        text1 = (TextView) timeLayout.findViewById(R.id.textItemDate);
                        text1.setText(year + "年" + month + "月" + day + "日");
                        text1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (datePickerDialog != null && datePickerDialog.isShowing()) {
                                    datePickerDialog.dismiss();
                                } else {
//                                    datePickerDialog.show();
                                    showPicker(DATE_TAG);
                                }
                            }
                        });
                        text2 = (TextView) timeLayout.findViewById(R.id.textItemTime);
                        text2.setText(hour + "时" + minute + "分");
                        text2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (timePickerDialog != null && timePickerDialog.isShowing()) {
                                    timePickerDialog.dismiss();
                                } else {
//                                    timePickerDialog.show();
                                    showPicker(TIME_TAG);
                                }
                            }
                        });
                        if (result.equals("10701") || result.equals("10702")) {//显示时间和原因
                            reasonLayout.setVisibility(View.VISIBLE);
                            timeLayout.setVisibility(View.VISIBLE);
                        } else {
                            reasonLayout.setVisibility(View.GONE);
                            timeLayout.setVisibility(View.GONE);
                        }
                        mLinearContent.addView(timeLayout);
                        break;
                    case UI_TYPE_SYSTEMTIME:
//                        break;
                    case UI_TYPE_EDITDATE://修改时间和修改日期同理
//                        break;
                    case UI_TYPE_EDITTIME:
                        timeLayout = getLayoutInflater().inflate(R.layout.operate_item_textview, null);
                        Calendar calendar2 = Calendar.getInstance();
                        int year2 = calendar2.get(Calendar.YEAR);
                        int month2 = calendar2.get(Calendar.MONTH) + 1;
                        int day2 = calendar2.get(Calendar.DAY_OF_MONTH);
                        int minute2 = calendar2.get(Calendar.MINUTE);
                        int hour2 = calendar2.get(Calendar.HOUR_OF_DAY);
                        calendar2 = null;
                        text1 = (TextView) timeLayout.findViewById(R.id.textItemDate);
                        text1.setText(year2 + "年" + month2 + "月" + day2 + "日");
                        text1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (datePickerDialog != null && datePickerDialog.isShowing()) {
                                    datePickerDialog.dismiss();
                                } else {
                                    showPicker(DATE_TAG);
                                }
                            }
                        });
                        text2 = (TextView) timeLayout.findViewById(R.id.textItemTime);
                        text2.setText(hour2 + "时" + minute2 + "分");
                        text2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (timePickerDialog != null && timePickerDialog.isShowing()) {
                                    timePickerDialog.dismiss();
                                } else {
//                                    timePickerDialog.show();
                                    showPicker(TIME_TAG);
                                }
                            }
                        });
                        resultRule = Integer.parseInt(rule);
                        mLinearContent.addView(timeLayout);
                        break;
                    case UI_TYPE_DROPDWON_MULTICHOICE2:
                        View SpinnerLayout1 = getLayoutInflater().inflate(R.layout.operate_item_spinner, null);
                        final ArrayList<String> conditions1 = new ArrayList<>();
                        final ArrayList<ArrayList<String>> conditions2 = new ArrayList<>();
                        final ArrayList<ArrayList<String>> ids2 = new ArrayList<>();
                        //此处tabcontrols可能报空，加判断
                        tabControls = tabControls == null ? new ArrayList<CurrentTaskData.TabControl>() : tabControls;
                        for (CurrentTaskData.TabControl control : tabControls) {
                            conditions1.add(control.getName());
                            ArrayList<String> secondList = new ArrayList<>();
                            ArrayList<String> idList = new ArrayList<>();
                            if (control.getControls() != null) {//control内容判空
                                for (CurrentTaskData.TabControl tabControl : control.getControls()) {
                                    secondList.add(tabControl.getName());
                                    idList.add(tabControl.getId());
                                }
                            }
                            conditions2.add(secondList);
                            ids2.add(idList);
                        }
//                        mSpinnerAdapter = new HomeSpinnerAdapter(OperateOrderActivity.this, conditions1);
                        HomeSpinnerAdapter adapter1 = new HomeSpinnerAdapter(OperateOrderActivity.this, conditions1);
//                        final HomeSpinnerAdapter adapter2 = new HomeSpinnerAdapter(OperateOrderActivity.this, conditions2.get(0));
                        spinner1 = (Spinner) SpinnerLayout1.findViewById(R.id.spinner_operate);
//                        final RelativeLayout arrow1 = (RelativeLayout) SpinnerLayout1.findViewById(R.id.relArrow);
//                        LinearLayout linearParent1 = (LinearLayout) SpinnerLayout1.findViewById(R.id.linearSelectParent);
                        spinner1.setAdapter(adapter1);
//                        mLinearContent.addView(SpinnerLayout1);
                        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                mSecondeSpinnerAdapter.clear();
                                mSecondeSpinnerAdapter.addAll(conditions2.get(position));
//                                spinner1.setBackgroundDrawable(null);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
//                        AdapterView.OnItemClickListener listener1 = new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                                mSecondeSpinnerAdapter.clear();
//                                adapter2.clear();
//                                adapter2.addAll(conditions2.get(position));
//                                spinner1.setText(conditions1.get(position));
//                                spinner2.setText(conditions2.get(position).get(0));
////                                result = conditions2.get(position).get
//                                mPopWindow.dismiss();
//                            }
//                        };

//                        final View view1 = initSelectionList(listener1, adapter1);
//                        linearParent1.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                mPopWindow.setContentView(view1);
//                                mPopWindow.showAsDropDown(arrow1, 0, 0);
//                            }
//                        });
//                        spinner1.setText(conditions1.get(0));


                        View SpinnerLayout2 = getLayoutInflater().inflate(R.layout.operate_item_spinner, null);
//                        LinearLayout linearParent2 = (LinearLayout) SpinnerLayout2.findViewById(R.id.linearSelectParent);
//                        final RelativeLayout arrow3 = (RelativeLayout) SpinnerLayout2.findViewById(R.id.relArrow);
                        mSecondeSpinnerAdapter = new HomeSpinnerAdapter(OperateOrderActivity.this, conditions2.get(0));
                        spinner2 = (Spinner) SpinnerLayout2.findViewById(R.id.spinner_operate);
                        spinner2.setAdapter(mSecondeSpinnerAdapter);
                        mLinearContent.addView(SpinnerLayout2);
                        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                spinner2.setBackgroundDrawable(null);
                                result = conditions2.get(spinner1.getSelectedItemPosition()).get(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
//                        AdapterView.OnItemClickListener listener2 = new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                int index = conditions1.indexOf(spinner1.getText().toString().trim());
//                                spinner2.setText(conditions2.get(index).get(position));
//                                result = conditions2.get(index).get(position);
//                            }
//                        };
//                        final View view2 = initSelectionList(listener2, adapter2);
//                        linearParent2.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                mPopWindow.setContentView(view2);
//                                mPopWindow.showAsDropDown(arrow3, 0, 0);
//                            }
//                        });
//                        spinner2.setText(result);
//                        result = conditions2.get(0).get(0);
                        resultRule = Integer.parseInt(rule);
                        break;
                    case UI_TYPE_INTEGER:
                        View viewEdit1 = getLayoutInflater().inflate(R.layout.operate_item_edit, null);
                        editPrice = (EditText) viewEdit1.findViewById(R.id.editItem);
                        editPrice.setHint(comeName);
                        mLinearContent.addView(viewEdit1);
                        resultRule = 5;
                        break;
                    case UI_TYPE_WORKORDER_IDENTIFY:
                        break;
                }
            }//遍历Type END
        }//遍历SourceEnd
    }

    /**
     * 打开工单详情
     */
    public static void openOrderDetail(Context context, String orderID, boolean onlyID) {
        Intent intentOrder = new Intent(context, OperateOrderActivity.class);
        if (onlyID) {
            intentOrder.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean(ONLY_ID, true);
        bundle.putString(ORDER_ID, orderID);
        intentOrder.putExtras(bundle);
        context.startActivity(intentOrder);
    }

    public static void openOrderDetailForResult(Activity context, String orderID, boolean onlyID, int request, String state) {
        Intent intentOrder = new Intent(context, OperateOrderActivity.class);
        if (onlyID) {
            intentOrder.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean(ONLY_ID, true);
        bundle.putString(ORDER_ID, orderID);
        bundle.putString(ORDER_STATE, state);
        intentOrder.putExtras(bundle);
        context.startActivityForResult(intentOrder, request);
    }

    //添加换件和非换件回传值

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CHANGERECORD:
                    getChangePiecesRecord(false);
                    break;
                case REQUEST_UNCHANGERECORD:
                    getChangePiecesRecord(false);
                    break;
                case REQUEST_ORDER_FLOW:
                    getOrderInfo(mOrderID);
                    break;
                case REQUST_COLLECT:
                    getOrderInfo(mOrderID);
                    break;
            }
        } else {

        }

    }

    /**
     * 拉取换件记录
     */
    private void getChangePiecesRecord(final boolean finished) {
        if (Utils.checkInternetStatus(this) == 0) {
            Toast.makeText(OperateOrderActivity.this, R.string.text_internet_unavalible, Toast.LENGTH_SHORT).show();
            return;
        }
        //记录组件可见
        mLinearPiecesRecord.setVisibility(View.VISIBLE);

        HashMap<String, String> params = new HashMap<>();
        params.put("user_code", Utils.getUserCode(this));
        params.put("order_code", mOrderID);
        showLoadingDialog();
//        isLoading = true;
        HttpClientManager.post(NetInterface.GET_CHANGEPIECE_RECORD + "token=" + Utils.getToken(this), params, new JsonHttpCallBack<ChangeRecordData>() {
            @Override
            public void onSuccess(ChangeRecordData result) {
                dismissLoadingDialog();
//                isLoading = false;
                if (ResultUtils.isSuccess(OperateOrderActivity.this, result)) {
                    ChangeRecordData.RecordBean bean = result.getData();
                    if (bean != null) {
//                        mTxtNoRecords.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        ArrayList<ChangeRecordData.RecordItem> list = bean.getList();
                        if (list != null) {
                            pieceAdapter.refreshData(list);
                        }
                    } else {
//                        mTxtNoRecords.setVisibility(View.VISIBLE);
//                        mRecyclerView.setVisibility(View.GONE);
                    }
                }

                if (finished) {//工单完成判断是否需要显示换件记录
                    if (changePieceRecords.size() == 0) {//没有记录。隐藏记录视图
                        mLinearChangePiecesItems.setVisibility(View.GONE);
                        if (mLinearCostFee.getVisibility() == View.GONE) {
                            mLinearPiecesRecord.setVisibility(View.GONE);
                        }
                    } else {//有记录。展示recycler
                        mLinearChangePiecesItems.setVisibility(View.VISIBLE);
                    }
                    mLinearBtns.setVisibility(View.GONE);
                    pieceAdapter.hideDeleteBtn(true);
                } else {
                    pieceAdapter.hideDeleteBtn(false);
                    mLinearPiecesRecord.setVisibility(View.VISIBLE);
                    mLinearChangePiecesItems.setVisibility(View.VISIBLE);
                    mLinearBtns.setVisibility(View.VISIBLE);
                    mLinearCostFee.setVisibility(View.GONE);
                }


            }

            @Override
            public void onError(Exception e) {
                dismissLoadingDialog();
//                isLoading = false;
                if (finished) {
                    mLinearChangePiecesItems.setVisibility(View.GONE);
                }
                Utils.showServerError(OperateOrderActivity.this);
            }
        });

    }

    /**
     * 删除
     *
     * @param id
     */

    @Override
    public void onDelete(String id, boolean unchange) {
        deleteChagePiece(id, unchange);
    }

    /**
     * 删除换件信息
     */
    public void deleteChagePiece(String changeID, boolean unchange) {
        if (Utils.checkInternetStatus(OperateOrderActivity.this) == 0) {
            Toast.makeText(OperateOrderActivity.this, R.string.text_internet_unavalible, Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        String url = null;
        if (unchange) {
            url = NetInterface.DELETE_UNCHANGEPIECE + Utils.getToken(this);
            params.put("unchange_info_id", changeID);
        } else {
            url = NetInterface.DELETE_CHANGEPIECE + Utils.getToken(this);
            params.put("reason", "no_reason");
            params.put("change_id", changeID);

        }
        params.put("order_code", mOrderID);
        showLoadingDialog();
        HttpClientManager.post(url, params, new JsonHttpCallBack<BaseData>() {
            @Override
            public void onSuccess(BaseData result) {
                dismissLoadingDialog();
//                isLoading = false;
                if (ResultUtils.isSuccess(OperateOrderActivity.this, result)) {
                    //刷新页面
                    getChangePiecesRecord(false);
                }
            }

            @Override
            public void onError(Exception e) {
                dismissLoadingDialog();

                Utils.showServerError(OperateOrderActivity.this);
            }
        });

    }

    /**
     * 发送一次定位
     */
    private void getLocation() {
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(getApplicationContext());
        }
        if (mOption == null) {
            mOption = new LocationClientOption();
            mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
            mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
            mOption.setScanSpan(10 * 1000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
            mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
            mOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
            mOption.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
            mOption.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
            mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
            mOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
            mOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
            mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
            mOption.setIsNeedAltitude(false);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        }
        mLocationClient.setLocOption(mOption);
        mLocationClient.registerLocationListener(lintenner);
        showLoadingDialog();
        mLocationClient.start();
    }

    private BDLocationListener lintenner = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
//            dismissLoadingDialog();
            int type = bdLocation.getLocType();
            if (type == BDLocation.TypeGpsLocation || type == BDLocation.TypeNetWorkLocation || type == BDLocation.TypeOffLineLocation) {//回调成功获取定位
                myLatitude = bdLocation.getLatitude();
                myLongtitude = bdLocation.getLongitude();
                final LatLng myPos = new LatLng(myLatitude, myLongtitude);
                myPosition = myLatitude + "," + myLongtitude;
                myAddress = bdLocation.getAddrStr();
                String city = bdLocation.getCity();
//                //工单流程继续执行
                if (TextUtils.isEmpty(onSitePosition)) {//客户地址为空，去地理反编码获取经纬度
                    final GeoCoder mSearch = GeoCoder.newInstance();
                    mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                        @Override
                        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                            if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                                //没有检索到结果
                                instance = "";
                                getNextStep();
                            } else {
                                //获取地理编码结果
                                LatLng cusPos = geoCodeResult.getLocation();
                                onSitePosition = cusPos.latitude+","+cusPos.longitude;
                                instance = DistanceUtil.getDistance(myPos, cusPos) + "";
                                getNextStep();
                            }
                            mSearch.destroy();

                        }

                        @Override
                        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                            reverseGeoCodeResult.getLocation();
                            mSearch.destroy();
                        }
                    });
                    if (TextUtils.isEmpty(onSiteAddress) || TextUtils.isEmpty(city)) {
                        instance = "";
                        getNextStep();
                    } else {
                        mSearch.geocode(new GeoCodeOption().city(city).address(onSiteAddress));
                    }
                } else {
                    //客户地址不为空
                    LatLng cusPos = new LatLng(cusLatitude, cusLongtitude);
                    instance = DistanceUtil.getDistance(cusPos, myPos) + "";
                    getNextStep();
                }

            } else {//回调获取定位失败
                instance = "";
                myPosition = "";
                myAddress = "";
                getNextStep();
            }
            if (mLocationClient != null) {
                mLocationClient.stop();
                mLocationClient.unRegisterLocationListener(lintenner);
            }
        }
    };

    /**
     * 获取工单历史任务
     */
    public void getHistory() {
        if (Utils.checkInternetStatus(this) == 0) {
            Toast.makeText(this, R.string.text_internet_unavalible, Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        params.put("user_code", userCode);
        params.put("order_id", mOrderID);
        showLoadingDialog();
//        isLoading = true;
        HttpClientManager.post(NetInterface.GET_ORDER_TASK_HIS, params, new JsonHttpCallBack<OrderTask>() {
            @Override
            public void onSuccess(OrderTask result) {
                dismissLoadingDialog();
//                isLoading = false;
                if (ResultUtils.isSuccess(OperateOrderActivity.this, result)) {
                    List<OrderTask.TaskData> taskDatas = result.getData();
                    mTaskHistoryList.addAll(taskDatas.get(0).getTask_historys());
                    mTaskAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Exception e) {
                dismissLoadingDialog();
//                isLoading = false;
                Utils.showServerError(OperateOrderActivity.this);
            }
        });
    }

    public void fillOrderDetailInfo() {
        //先显示需要的组件
        mScrollView.setVisibility(View.VISIBLE);
        mViewOrderTable.setVisibility(View.VISIBLE);
        mLinearOperate.setVisibility(View.VISIBLE);
        mLinearInstructions.setVisibility(View.VISIBLE);
        mLinearGap.setVisibility(View.VISIBLE);


        //工单信息数据填充
        if (detailBean != null) {
            mTxtOrderTile.setText(detailBean.getOrder_type_name());
            mTxtOrderStatus.setText(detailBean.getOrder_state_name());
            if (TextUtils.isEmpty(detailBean.getPre_time())) {
                mTxtOrderDate.setVisibility(View.GONE);
            } else {
                mTxtOrderDate.setVisibility(View.VISIBLE);
                mTxtOrderDate.setText(getResources().getString(R.string.text_order_date) + detailBean.getPre_time());
            }
            mTxtCustomer.setText(detailBean.getCustomer_name());
            ArrayList<OrderDetailAllData.CustomPhone> phones = detailBean.getCustomer_phones();
            currentTaskId = detailBean.getCurrent_task_code();
            mProductSN = detailBean.getProduct_sn();
            onSiteAddress = detailBean.getCustomer_address();
            if (TextUtils.isEmpty(detailBean.getContact_phone())) {
                mTxtPhones.setText("无");
                mLinearPhones.setEnabled(false);
            } else {
                mTxtPhones.setText(detailBean.getContact_phone());
                mLinearPhones.setEnabled(true);
            }
            mTxtPhones.setText(TextUtils.isEmpty(detailBean.getContact_phone()) ? "无" : detailBean.getContact_phone());
            try {
                String pos[] = onSitePosition.split(",");
                cusLatitude = Double.parseDouble(pos[0]);
                cusLongtitude = Double.parseDouble(pos[1]);
            } catch (Exception e) {
                cusLatitude = 0.0;
                cusLongtitude = 0.0;
            }
            //循环添加电话
//            mLinearPhones.removeAllViews();
////            if (phones.size() == 0) {
//            if (TextUtils.isEmpty(detailBean.getContact_phone())) {
//                mTablePhones.setVisibility(View.GONE);
//            } else {
//                mTablePhones.setVisibility(View.VISIBLE);
//            }
////            for (OrderDetailAllData.CustomPhone phone : phones) {
//            LinearLayout tel_Lin = new LinearLayout(OperateOrderActivity.this);
//            tel_Lin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//            tel_Lin.setOrientation(LinearLayout.HORIZONTAL);
//            tel_Lin.setGravity(Gravity.CENTER_VERTICAL);
//            tel_Lin.setPadding(0, OperateOrderActivity.this.getResources().getDimensionPixelSize(R.dimen.layout_13dp), 0, OperateOrderActivity.this.getResources().getDimensionPixelSize(R.dimen.layout_13dp));
//            ImageView callImg = new ImageView(OperateOrderActivity.this);
//            callImg.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f));
//            callImg.setImageResource(R.drawable.icon_orders_unfinished_phone);
//            TextView mTxtTel = new TextView(OperateOrderActivity.this);
//            mTxtTel.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 7.0f));
//            mTxtTel.setTextColor(getResources().getColor(R.color.gray_666666));
//            mTxtTel.setTextSize(Utils.px2dip(OperateOrderActivity.this, getResources().getDimension(R.dimen.text_15sp)));
////                final String num = phone.getPhone();
//            final String num = detailBean.getContact_phone();
//            mTxtTel.setText(num);
//            tel_Lin.addView(mTxtTel);
//            tel_Lin.addView(callImg);
//            tel_Lin.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Utils.callNumber(OperateOrderActivity.this, num);
//                }
//            });
//            mLinearPhones.addView(tel_Lin);
////            }
            mTxtAddress.setText(onSiteAddress);
            if (TextUtils.isEmpty(detailBean.getProduct_type()) && TextUtils.isEmpty(detailBean.getProduct_sn())) {
                mTableMachineInfo.setVisibility(View.GONE);
            } else {
                mTableMachineInfo.setVisibility(View.VISIBLE);
                mTxtDeviceInfo.setText(detailBean.getProduct_sn() + "【" + detailBean.getProduct_type() + "】");
            }
            mTxtProblemDes.setText(detailBean.getFailure_description());

            //备件信息填充
            mLinearParts.removeAllViews();
            if (backupPieces != null && backupPieces.size() > 0) {
                mTablePieces.setVisibility(View.VISIBLE);
                linearPartTopLine.setVisibility(View.VISIBLE);
                mTablePieces.setVisibility(View.VISIBLE);
                for (OrderDetailAllData.BackupPiece data : backupPieces) {
                    TextView mTxtPiece = new TextView(OperateOrderActivity.this);
                    mTxtPiece.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    mTxtPiece.setTextColor(getResources().getColor(R.color.gray_666666));
//                    mTxtPiece.setTextSize(Utils.px2dip(OperateOrderActivity.this, getResources().getDimension(R.dimen.text_15sp)));
                    mTxtPiece.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_15sp));
                    String content = data.getMaterial_name().trim() + "【" + data.getPr_status() + "】";
                    mTxtPiece.setText(content);
                    mLinearParts.addView(mTxtPiece);
                    mTableProblem.setBackgroundColor(getResources().getColor(R.color.white));
                }
            } else {
                mTablePieces.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(detailBean.getCallback())) {
                linearCallbackTopline.setVisibility(View.VISIBLE);
                mTableCallback.setVisibility(View.VISIBLE);
                mTxtCallBack.setText(detailBean.getCallback());
            } else {
                linearCallbackTopline.setVisibility(View.GONE);
                mTableCallback.setVisibility(View.GONE);
            }
            setTableBackColor();
            //任务说明
            if (TextUtils.isEmpty(detailBean.getTask_descriptions())) {
                mTxtInstruction.setText("此步骤按照服务规范进行操作");
            } else {
                mTxtInstruction.setText(detailBean.getTask_descriptions());
            }
        }
    }

    /**
     * 设定tableview的背景色
     */
    private void setTableBackColor() {
        ArrayList<View> views = new ArrayList<>();
        if (mTableCustom.getVisibility() == View.VISIBLE) {
            views.add(mTableCustom);
        }
        if (mTablePhones.getVisibility() == View.VISIBLE) {
            views.add(mTablePhones);
        }
        if (mTableAddress.getVisibility() == View.VISIBLE) {
            views.add(mTableAddress);
        }
        if (mTableMachineInfo.getVisibility() == View.VISIBLE) {
            views.add(mTableMachineInfo);
        }
        if (mTablePieces.getVisibility() == View.VISIBLE) {
            views.add(mTablePieces);
        }
        if (mTableProblem.getVisibility() == View.VISIBLE) {
            views.add(mTableProblem);
        }
        if (mTableCallback.getVisibility() == View.VISIBLE) {
            views.add(mTableCallback);
        }
        int[] ids = new int[]{R.color.gray_F8F8F8, R.color.white};
        int i = 0;
        for (View view : views) {
            view.setBackgroundColor(getResources().getColor(ids[i % 2]));
            i++;
        }
    }

    /**
     * 隐藏所有的views
     */
    private void hideAll() {
        mViewOrderTable.setVisibility(View.GONE);
        mLinearPiecesRecord.setVisibility(View.GONE);
        mLinearChangePiecesItems.setVisibility(View.GONE);
        mLinearBtns.setVisibility(View.GONE);
        mLinearCostFee.setVisibility(View.GONE);
        mLinearGap.setVisibility(View.GONE);
        mLinearOperate.setVisibility(View.GONE);
        mLinearContent.setVisibility(View.GONE);
        mLinearInstructions.setVisibility(View.GONE);
        mImageQR.setVisibility(View.GONE);
        mTxtNoQRdesc.setVisibility(View.GONE);
        mRecyclerRecords.setVisibility(View.GONE);
    }

    /**
     * 单独拉取二维码接口的
     */
    public void getQRcodeUrl() {
        mLinearOperate.setVisibility(View.VISIBLE);
        mTxtOperateName.setVisibility(View.VISIBLE);
        mTxtOperateName.setVisibility(View.VISIBLE);
        mTxtOperateName.setText("邀评二维码");
        if (Utils.checkInternetStatus(OperateOrderActivity.this) == 0) {
            return;
        }
        String token = Utils.getToken(OperateOrderActivity.this);
        HttpClientManager.get(NetInterface.GET_QRCODE_URL + mOrderID + "?token=" + token, new JsonHttpCallBack<QRcodeUrlData>() {
            @Override
            public void onSuccess(QRcodeUrlData result) {
                if (ResultUtils.isSuccess(OperateOrderActivity.this, result)) {
                    QRcodeUrlData.QRData data = result.getData();
                    qrUrl = data.getQrcode_url();
                    if (!TextUtils.isEmpty(qrUrl)) {//二维码不为空
                        mImageQR.setVisibility(View.VISIBLE);
//                        HttpClientManager.displayImage(mImageQR, qrUrl);
                        Utils.loadImageWithGlide(OperateOrderActivity.this.getApplication(), qrUrl, mImageQR, R.drawable.default_load_img);
                        if (showQRFrag)//条转过程中需要显示二维码
                            showQRDialog(qrUrl);

                    } else {//二维码为空
                        mTxtNoQRdesc.setVisibility(View.VISIBLE);
                        mImageQR.setVisibility(View.GONE);
                    }
                } else {
                    mTxtNoQRdesc.setVisibility(View.VISIBLE);
                    mImageQR.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Exception e) {
                mTxtOperateName.setText("邀评二维码");
                mLinearOperate.setVisibility(View.GONE);
                mImageQR.setVisibility(View.GONE);
                Utils.showServerError(OperateOrderActivity.this);
            }
        });

    }

    /**
     * 二维码浮层
     *
     * @param qrUrl
     */
    private void showQRDialog(String qrUrl) {
        QRCodeFragment qrFrag = QRCodeFragment.newInstance(qrUrl);
        qrFrag.setCancelable(true);
        if (isResume)
            qrFrag.show(getFragmentManager(), "QR");
        showQRFrag = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        addTopDistance();
    }

    /**
     * 去除drawerview的顶部边距
     */
    private void addTopDistance() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mLinear_drawerMenu.getLayoutParams();
            params.setMargins(0, result, 0, 0);
            mLinear_drawerMenu.setLayoutParams(params);
        }
    }

    @Override
    public void onBackPressed() {
        if (state != null && !"1".equals(state)) {
            EventBus.getDefault().post(new FirstEvent("refish"));
        } else {
            if (isRefresh) {
                EventBus.getDefault().post(new FirstEvent("refish"));
            }
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResume = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isResume = false;
    }

    /**
     * @param listener
     * @param adapter
     * @return
     */
    private View initSelectionList(AdapterView.OnItemClickListener listener, HomeSpinnerAdapter adapter) {
        View view = LayoutInflater.from(this).inflate(R.layout.operate_item_listview, null, false);
        ListView listView = (ListView) view.findViewById(R.id.listSelections);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listener);
        return view;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
