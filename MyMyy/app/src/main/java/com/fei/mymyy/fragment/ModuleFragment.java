package com.fei.mymyy.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fei.mymyy.R;
import com.fei.mymyy.entity.PageModule;
import com.fei.mymyy.utils.GlobalConstants;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModuleFragment extends Fragment {

    private PageModule mPageModule;
    private WebView mWebView;

    public ModuleFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ModuleFragment(PageModule pageModule) {
        mPageModule = pageModule;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_module, container, false);
        mWebView = (WebView) view.findViewById(R.id.webView);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.loadUrl(GlobalConstants.BASE_URL + "module.jsp");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mWebView.loadUrl("javascript:appendpage('"+mPageModule.getContent()+"')");
            }
        });

        return view;
    }


}
