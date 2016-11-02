package com.lenovo.csd.eservice.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.entity.base.KnowledgeData;
import com.lenovo.csd.eservice.http.HttpClientManager;
import com.lenovo.csd.eservice.http.NetInterface;
import com.lenovo.csd.eservice.http.ResultUtils;
import com.lenovo.csd.eservice.http.callback.adapter.JsonHttpCallBack;
import com.lenovo.csd.eservice.util.Utils;

/**
 * 学习tab页面
 * Created by shengtao
 * on 2016/7/26
 * 23:48
 */
public class LearningFragment extends BaseFragment {
    private WebView lenovoKnowledgeRepository;
    //	String baseURL = "http://servicekb.lenovo.com.cn/_layouts/MobileWeb/CSKBLogin.aspx?user=";
//	String tokenString="";
    String absolutelyURL = "";


    private boolean isSelfSelected;
    private boolean isFirstLoad = true;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//		tokenString=Utils.getToken(context);
//		absolutelyURL=baseURL+tokenString;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_learning, null);
        lenovoKnowledgeRepository = (WebView) view.findViewById(R.id.lenovo_knowledge_repository);
        //处理返回键
        lenovoKnowledgeRepository.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && lenovoKnowledgeRepository.canGoBack()) {  //表示按返回键 时的操作
                        lenovoKnowledgeRepository.goBack();   //后退
                        return true;    //已处理
                    }
                }
                return false;
            }
        });
        setWebView();
        return view;
    }

    private void setWebView() {
        lenovoKnowledgeRepository.getSettings().setDomStorageEnabled(true);
        lenovoKnowledgeRepository.getSettings().setJavaScriptEnabled(true);
        lenovoKnowledgeRepository.getSettings().setUseWideViewPort(true);
        lenovoKnowledgeRepository.getSettings().setLoadWithOverviewMode(true);
        lenovoKnowledgeRepository.getSettings().setBuiltInZoomControls(true);
        lenovoKnowledgeRepository.getSettings().setSupportZoom(false);
        lenovoKnowledgeRepository.requestFocus();
        // myWebview.loadUrl(URL);
        // myWebview.loadUrl(url, additionalHttpHeaders)
//        getKnowledgeUrl();
        WebViewClient wvc = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                return true;
            }
        };
        lenovoKnowledgeRepository.setWebViewClient(wvc);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.isHidden()) {
            return;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * tab切换要用，预留
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
        }
    }

    /**
     * 获取知识库链接
     */
    public void getKnowledgeUrl() {
        if (Utils.checkInternetStatus(getActivity()) == 0) {
            if(isResumed())
            Toast.makeText(getActivity(), R.string.text_internet_unavalible, Toast.LENGTH_SHORT).show();
            return;
        }
        showLoadingDialog();
        HttpClientManager.get(NetInterface.KNOWLEDGE_DB_URL + Utils.getUserCode(getContext()) + "?token=" + Utils.getToken(getContext()), new JsonHttpCallBack<KnowledgeData>() {
            @Override
            public void onSuccess(KnowledgeData result) {
                dismissLoadingDialog();

                isFirstLoad = false;
                if (ResultUtils.isSuccess(getContext(),result)) {
                    KnowledgeData.Knowledge knowledge = result.getData();
                    absolutelyURL = knowledge.getUrl();
                    if (absolutelyURL == null || TextUtils.isEmpty(absolutelyURL)) {
                        Toast.makeText(getContext(), "网页链接有误", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    lenovoKnowledgeRepository.loadUrl(absolutelyURL);
                }
            }

            @Override
            public void onError(Exception e) {
                dismissLoadingDialog();
                isFirstLoad = false;
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setSelected(boolean selected) {
        isSelfSelected = selected;
     if(isSelfSelected && isFirstLoad){
         getKnowledgeUrl();
     }
    }
}
