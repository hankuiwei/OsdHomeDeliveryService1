package com.lenovo.csd.eservice.activity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.adapter.CommentListViewAdapter;
import com.lenovo.csd.eservice.cache.SharedPrefManager;
import com.lenovo.csd.eservice.entity.base.MyComment;
import com.lenovo.csd.eservice.entity.base.MyCommentList;
import com.lenovo.csd.eservice.http.ErrorCode;
import com.lenovo.csd.eservice.http.HttpClientManager;
import com.lenovo.csd.eservice.http.NetInterface;
import com.lenovo.csd.eservice.http.callback.adapter.JsonHttpCallBack;
import com.lenovo.csd.eservice.util.Utils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hankw on 16-7-20.
 * 我的点评记录
 */
public class MyCommentActivity extends BaseActivity implements OnScrollListener {

    private final String TAG = this.getClass().getSimpleName();

    private TextView mMineHGood;
    private TextView mMineHMiddle;
    private TextView mMineHBad;
    private TextView mMineGood;
    private TextView mMineMiddle;
    private TextView mMineBad;
    private TextView mMineRateComment;
    private ListView mCommentListView;
    private  MyComment.Comment mNowComment;
    private int nallcount;//点评总数
    private List<MyCommentList.Data> commentItems;
    private CommentListViewAdapter mCommentListViewAdapter;
    private int visibleLastIndex = 0;   //最后的可视项索引
    private int visibleItemCount;       // 当前窗口可见项总数
    private Map<String,String> CommentListMap;
    private String start = "1";//起始页
    private String limit = "6";//返回数据条数
    private String type = "all";//all全部，good好评，common中评，bad差评
    private String hascontent= "1";//０无限制，１有内容评论
    private boolean isFlag;
    private boolean isCommentFlag;
   // private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void provideLayout() {
        Log.d(TAG,"provideLayout() is startsing ！");
        setContentView(R.layout.activity_my_comment);
    }


    @Override
    protected void initData() {
        int netState =  Utils.checkInternetStatus(MyCommentActivity.this);
        if (netState == 0) {
            Toast.makeText(MyCommentActivity.this,"网络不可用,请连接网络后重试",Toast.LENGTH_SHORT).show();
            return;
        }
            SharedPreferences preferences = SharedPrefManager.getSystemSharedPref(MyCommentActivity.this);
            final String engineerCode = SharedPrefManager.getStringInSharePref(preferences,SharedPrefManager.LOGIN_USER_CODE,"engineerCode");
            // TODO: 16-8-29  开发测试接口后期上线前，删掉
            //engineerCode = "L19921";
            if ("engineerCode".equals(engineerCode)) {
                Toast.makeText(MyCommentActivity.this,"请登录！",Toast.LENGTH_SHORT).show();
                return;
            }
            showLoadingDialog();
            SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            String now_mounth = dateFormater.format(cal.getTime());
            String h_mounth = getLastDayOfLastMonth(dateFormater);
            /*Map<String,String> params = new HashMap<String, String>();
            params.put("m","GetReviewCount");
            params.put("EngineerCode",engineerCode);
            params.put("datetime",now_mounth);*/
            String m = "GetReviewCount";

            String url = NetInterface.GETREVIEWCOUNT+"?m="+m+"&EngineerCode="+engineerCode+"&datetime="+now_mounth;
           final String h_url = NetInterface.GETREVIEWCOUNT+"?m="+m+"&EngineerCode="+engineerCode+"&datetime="+h_mounth;
           //Log.d(TAG,"initData() ==> now_mounth  urlis ("+url+")");
            HttpClientManager.get(url,new JsonHttpCallBack<MyComment>() {
                @Override
                public void onSuccess(MyComment result) {
                    isFlag = true;
                    if (result != null && result.getStatus() == ErrorCode.STATUS_SUCCESS){
                        mNowComment = result.getData();
                        nallcount = mNowComment.getAllcount();
                       // Log.d(TAG,"initData()1111 == > nallcount is ("+nallcount+")");
                        int goodcount = mNowComment.getGoodcount();
                        int commcount = mNowComment.getCommcount();
                        String badcount = mNowComment.getBadcount();
                        String invateCount = mNowComment.getInvatecount();
                        // Log.d(TAG,"onSuccess() ==> Invatecount is ("+invateCount+")");
                        int invatecount = Integer.parseInt(invateCount);
                        //Log.d(TAG,"onSuccess() == >invatecount is ("+invatecount+")");
                        String rate ="0.0%";
                        if (invatecount != 0) {
                            double res =(double) nallcount / (double) invatecount;
                            //获取格式化对象
                            NumberFormat nt = NumberFormat.getPercentInstance();
                            //设置百分数精确度2即保留两位小数
                            nt.setMinimumFractionDigits(2);
                            rate = nt.format(res);
                            // Log.d(TAG,"onSuccess() == >("+allcount+") / ("+invateCount+")rate is ("+rate+")");
                        }

                        mMineGood.setText(""+goodcount);
                        mMineMiddle.setText(""+commcount);
                        mMineBad.setText(badcount);
                        mMineRateComment.setText(rate);

                        //获取评价列表

                        if (nallcount != 0) {
                          //  Log.d(TAG,"initData() == > nallcount is ("+nallcount+")");
                            CommentListMap.put("m", "GetReviewListByChannelEng");
                            CommentListMap.put("start", start);
                            CommentListMap.put("limit", limit);
                            CommentListMap.put("eng_id", engineerCode);
                            CommentListMap.put("type", type);
                            CommentListMap.put("hascontent", hascontent);

                            HttpClientManager.post(NetInterface.GETREVIEWLIST, CommentListMap, new JsonHttpCallBack<MyCommentList>() {
                                @Override
                                public void onSuccess(MyCommentList result) {
                                    //Log.d(TAG,"onSuccess() == > responseCode is ("+result.getStatus()+")");
                                    if (result !=null && Integer.parseInt(result.getStatus()) == ErrorCode.STATUS_SUCCESS) {
                                        commentItems.addAll(result.getData());
                                        isCommentFlag = true;
                                        mCommentListViewAdapter.notifyDataSetChanged();
                                        dismissLoadingDialog();
                                    } else {
                                        dismissLoadingDialog();
                                       // Log.d(TAG, "onSuccess() == > responseCode is (" + responseCode + ")");
                                    }
                                }

                                @Override
                                public void onError(Exception e) {
                                    dismissLoadingDialog();
                                    Utils.showServerError(MyCommentActivity.this);
                                    Log.d(TAG, "onError() == > Exception is (" + e.getMessage() + ")");
                                }
                            });

                        }
                        //new Astak().execute();
                       // dismissLoadingDialog();
                        // Log.d(TAG,"initData() ==> get comment is success");
                        // Log.d(TAG,"initData() == > allcount is ("+allcount+")");
                    } else {
                       // dismissLoadingDialog();
                       // Toast.makeText(MyCommentActivity.this,result.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                   // Log.d(TAG,"initData() ==> h_mounth  h_Url ("+h_url+")");
                    HttpClientManager.get(h_url,new JsonHttpCallBack<MyComment>() {
                        @Override
                        public void onSuccess(MyComment result) {
                            dismissLoadingDialog();
                            if (result !=null && result.getStatus() == ErrorCode.STATUS_SUCCESS){
                                MyComment.Comment mNowComment = result.getData();
                                int allcount = mNowComment.getAllcount();
                                int goodcount = mNowComment.getGoodcount();
                                int commcount = mNowComment.getCommcount();
                                String badcount = mNowComment.getBadcount();
                               /* Log.d(TAG,"h_mounth == > allcount is ("+allcount+")");
                                Log.d(TAG,"h_mounth == > goodcount is ("+goodcount+")");
                                Log.d(TAG,"h_mounth == > commcount is ("+commcount+")");*/
                                //int invatecount = Integer.parseInt(mNowComment.getInvatecount());
                                mMineHGood.setText(""+mNowComment.getGoodcount());
                                mMineHMiddle.setText(""+mNowComment.getCommcount());
                                mMineHBad.setText(mNowComment.getBadcount());
                                //dismissLoadingDialog();
                                // Log.d(TAG,"initData() ==> get comment is success");
                                // Log.d(TAG,"initData() == > allcount is ("+allcount+")");
                            }else {

                                Toast.makeText(MyCommentActivity.this,result.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onError(Exception e) {
                            dismissLoadingDialog();
                            Utils.showServerError(MyCommentActivity.this);
                            //Log.d(TAG,"h_mounth  onError() == > Exception is ("+e.getMessage()+")");
                        }
                    });

                }

                @Override
                public void onError(Exception e) {
                    dismissLoadingDialog();
                    Utils.showServerError(MyCommentActivity.this);
                   // Log.d(TAG,"now_mounth  onError() == > Exception is ("+e.getMessage()+")");
                }
            });

      //  Log.d(TAG,"initData() == > nallcount222 is ("+nallcount+")");
       // params.put("datetime",h_mounth);
//        String h_url = NetInterface.GETREVIEWCOUNT+"?m="+m+"&EngineerCode="+engineerCode+"&datetime="+"2016-08-01";
//        Log.d(TAG,"initData() ==> h_mounth  h_Url ("+h_url+")");
        /*HttpClientManager.get(h_url,new JsonHttpCallBack<MyComment>() {
            @Override
            public void onSuccess(MyComment result) {
                if (result.getStatus() == ErrorCode.STATUS_SUCCESS){
                    MyComment.Comment mNowComment = result.getData();
                    int allcount = mNowComment.getAllcount();
                    int goodcount = mNowComment.getGoodcount();
                    int commcount = mNowComment.getCommcount();
                    String badcount = mNowComment.getBadcount();
                    Log.d(TAG,"h_mounth == > allcount is ("+allcount+")");
                    Log.d(TAG,"h_mounth == > goodcount is ("+goodcount+")");
                    Log.d(TAG,"h_mounth == > commcount is ("+commcount+")");
                    //int invatecount = Integer.parseInt(mNowComment.getInvatecount());

                    mMineHGood.setText(""+goodcount);
                    mMineHMiddle.setText(""+commcount);
                    mMineHBad.setText(badcount);
                    dismissLoadingDialog();
                    // Log.d(TAG,"initData() ==> get comment is success");
                    // Log.d(TAG,"initData() == > allcount is ("+allcount+")");
                }else {
                    dismissLoadingDialog();
                    Toast.makeText(MyCommentActivity.this,result.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(Exception e) {
                dismissLoadingDialog();
                Log.d(TAG,"h_mounth  onError() == > Exception is ("+e.getMessage()+")");
            }
        });*/

           // Log.d(TAG,"initData() == > h_mounth is ("+h_mounth+")");


    }




    @Override
    protected void setClickLintenner() {
        mCommentListView.setOnScrollListener(this);
        findViewById(R.id.mine_title_Lin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void initView() {
        commentItems = new ArrayList<MyCommentList.Data>();
        mCommentListViewAdapter = new CommentListViewAdapter(MyCommentActivity.this,commentItems);
        mMineHGood = (TextView) findViewById(R.id.Mine_h_good);
        mMineHMiddle = (TextView) findViewById(R.id.Mine_h_middle);
        mMineHBad = (TextView) findViewById(R.id.Mine_h_bad);
        mMineGood = (TextView) findViewById(R.id.Mine_good);
        mMineMiddle = (TextView) findViewById(R.id.Mine_middle);
        mMineBad = (TextView) findViewById(R.id.Mine_bad);
        mMineRateComment = (TextView) findViewById(R.id.mine_rate_comment);
        mCommentListView = (ListView) findViewById(R.id.Mine_CommentList);
        mCommentListView.setAdapter(mCommentListViewAdapter);
        CommentListMap = new HashMap<String, String>();
    }

    /**
     * 滑动状态改变时被调用
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int netState =  Utils.checkInternetStatus(MyCommentActivity.this);
        if (netState == 0) {
            Toast.makeText(MyCommentActivity.this,"网络不可用,请连接网络后重试",Toast.LENGTH_SHORT).show();
            return;
        }
        if (nallcount == 0){
            return;
        }
        int itemsLastIndex = mCommentListViewAdapter.getCount() - 1;    //数据集最后一项的索引
        // int lastIndex = itemsLastIndex + 1;             //加上底部的loadMoreView项
        int lastIndex = itemsLastIndex ;
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex == lastIndex) {
            showLoadingDialog();
            //如果是自动加载,可以在这里放置异步加载数据的代码
           // Log.i("LOADMORE", "loading...");
           /* handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                      //TO DO加载数据
                    mCommentListViewAdapter.notifyDataSetChanged(); //数据集变化后,通知adapter
                    mCommentListView.setSelection(visibleLastIndex - visibleItemCount + 1); //设置选中项
                }
            },2000);*/
            int pageCount = nallcount / 6;
           // Log.d(TAG,"onScrollStateChanged() pageCount is ("+nallcount+")  ("+pageCount+")");

            int pageSurplus = nallcount % 6;
            //Log.d(TAG,"onScrollStateChanged() pageSurplus is ("+pageSurplus+")");
            if (pageSurplus != 0){
                pageCount += 1;
            }

            int pageIndex = Integer.parseInt(start);
                pageIndex ++;
                start = ""+ pageIndex;
           // Log.d(TAG,"onScrollStateChanged()   pageIndex is ("+pageIndex+")");
           // Log.d(TAG,"onScrollStateChanged()   pageCount is ("+pageCount+")");
            if ( pageIndex <= pageCount) {

                CommentListMap.put("start",""+pageIndex);
                HttpClientManager.post(NetInterface.GETREVIEWLIST, CommentListMap, new JsonHttpCallBack<MyCommentList>() {
                    @Override
                    public void onSuccess(MyCommentList result) {
                        String responseCode = result.getStatus();
                        if (Integer.parseInt(responseCode) == ErrorCode.STATUS_SUCCESS) {
                            List<MyCommentList.Data> commentList =  result.getData();
                                commentItems.addAll(commentList);
                                mCommentListViewAdapter.notifyDataSetChanged();
                                dismissLoadingDialog();
                        }else {
                            dismissLoadingDialog();
                            //Log.d(TAG,"onSuccess() == > responseCode is ("+responseCode+")");

                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        dismissLoadingDialog();
                        Utils.showServerError(MyCommentActivity.this);
                       // Log.d(TAG,"onScrollStateChanged() == >onError() == > Exception is ("+e.getMessage()+")");
                    }
                });
                //pageIndex ++;
               // start = ""+ pageIndex;
                //Log.d(TAG,"onSuccess() pageIndex is ("+pageIndex+")");
               // Log.d(TAG,"onSuccess() start is ("+start+")");
            } else {
                dismissLoadingDialog();
                Toast.makeText(MyCommentActivity.this,"没有更多评论",Toast.LENGTH_SHORT).show();
                return;
            }

        }
    }

    /**
     * 滑动时被调用
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.visibleItemCount = visibleItemCount;
        visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
    }


    // 获取上个月的最后一天
    public String getLastDayOfLastMonth(SimpleDateFormat sdf) {
        String str = "";
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//
        lastDate.add(Calendar.MONTH, -1);//
        //lastDate.roll(Calendar.DATE, -1);//
        str = sdf.format(lastDate.getTime());
        return str;
    }

    public static void toComment(Activity activity){
        Intent intent = new Intent(activity,MyCommentActivity.class);
        activity.startActivity(intent);
    }

    /*public class  Astak extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(TAG,"Astak doInBackground() is starting ");
            Log.d(TAG,"Astak isFlag is ("+isFlag+") ");
            Log.d(TAG,"Astak isCommentFlag is ("+isCommentFlag+") ");
            if ( isFlag && isCommentFlag){
                dismissLoadingDialog();
                Log.d(TAG,"Astak doInBackground() is dismissLoadingDialog ");
            }
            return null;
        }


    }*/
}
