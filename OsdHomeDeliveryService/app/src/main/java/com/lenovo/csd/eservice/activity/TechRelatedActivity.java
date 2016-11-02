package com.lenovo.csd.eservice.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.entity.base.KnowledgeData;
import com.lenovo.csd.eservice.http.HttpClientManager;
import com.lenovo.csd.eservice.http.NetInterface;
import com.lenovo.csd.eservice.http.ResultUtils;
import com.lenovo.csd.eservice.http.callback.adapter.JsonHttpCallBack;
import com.lenovo.csd.eservice.util.Utils;

public class TechRelatedActivity extends BaseActivity {
    private RelativeLayout relBackLayout;
    private WebView mWebView;//加载H5页面的webview
//    private EditText mEditSn;
//    private TextView mTxtSearch;

    //加载请求参数
    private String knowledgeUrl;
    private String productSN;

    //常量
    public static final String PRODUCT_SN = "product_sn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void provideLayout() {
        setContentView(R.layout.activity_tech_related);

    }

    @Override
    protected void initView() {
        relBackLayout = (RelativeLayout) findViewById(R.id.relative_back);
        mWebView = (WebView) findViewById(R.id.webview_infomationTechRelated);
//        mEditSn = (EditText) findViewById(R.id.edit_techProductSN);
//        mTxtSearch = (TextView) findViewById(R.id.text_searchTxt);

    }

    @Override
    protected void initData() {
        productSN = getIntent().getStringExtra(PRODUCT_SN);
//        if (productSN != null && !TextUtils.isEmpty(productSN))
//            mEditSn.setText(productSN);

        WebSettings settings = mWebView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(false);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        getKnowledgeUrl();
    }

    @Override
    protected void setClickLintenner() {
        relBackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        mTxtSearch.setOnClickListener(new NoDoubleClickLinstenner() {
//            @Override
//            public void doClick(View view) {
//                if (knowledgeUrl != null) {
//                    loadH5();
//                } else {
//                    getKnowledgeUrl();
//                }
//            }
//        });
    }

    public static void openTechRelatedAct(Context context, String orderID) {
        Intent intent_backPieces = new Intent(context, TechRelatedActivity.class);
        intent_backPieces.putExtra(PRODUCT_SN, orderID);
        context.startActivity(intent_backPieces);
    }

    /**
     * 网络加载知识库链接
     */
    public void getKnowledgeUrl() {
        if (Utils.checkInternetStatus(this) == 0) {
            Toast.makeText(this, R.string.text_internet_unavalible, Toast.LENGTH_SHORT).show();
            return;
        }
        showLoadingDialog();
        HttpClientManager.get(NetInterface.KNOWLEDGE_DB_URL + Utils.getUserCode(this) + "?token=" + Utils.getToken(this), new JsonHttpCallBack<KnowledgeData>() {
            @Override
            public void onSuccess(KnowledgeData result) {
                dismissLoadingDialog();
                if (ResultUtils.isSuccess(TechRelatedActivity.this,result)) {
                    KnowledgeData.Knowledge knowledge = result.getData();
                    knowledgeUrl = knowledge.getUrl();
                    loadH5();
                }
            }

            @Override
            public void onError(Exception e) {
                dismissLoadingDialog();
               Utils.showServerError(TechRelatedActivity.this);
            }
        });
    }

    public void loadH5() {
        if (knowledgeUrl == null || TextUtils.isEmpty(knowledgeUrl)) {
            Toast.makeText(TechRelatedActivity.this, "网页链接有误", Toast.LENGTH_SHORT).show();
            return;
        }
//        productSN = mEditSn.getText().toString().trim();
//        if (productSN == null || TextUtils.isEmpty(productSN)) {
//            Toast.makeText(TechRelatedActivity.this, R.string.text_input_sn, Toast.LENGTH_SHORT).show();
//            return;
//        }
        mWebView.loadUrl(knowledgeUrl + "&k=" + productSN);
    }


}
