 
package com.nike.ntc_cn.fragment;

import com.nike.ntc_cn.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebViewFragment extends Fragment {
  public final static String TAG = WebViewFragment.class.getSimpleName();

  private WebView viewContentWebView;
  private String url;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View v = inflater.inflate(R.layout.webview, container, false);

    final ProgressBar viewContentProgress = (ProgressBar) v.findViewById(R.id.progress);
    viewContentWebView = (WebView) v.findViewById(R.id.webview);
    viewContentWebView.getSettings().setJavaScriptEnabled(true);
    viewContentWebView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return super.shouldOverrideUrlLoading(view, url);
      }
    });
    viewContentWebView.setWebChromeClient(new WebChromeClient() {
      @Override
      public void onProgressChanged(WebView view, int newProgress) {
        viewContentProgress.setProgress(newProgress);
        viewContentProgress.setVisibility(newProgress == 100 ? View.GONE : View.VISIBLE);
      }
    });
    return v;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    reload();
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (hidden)
      viewContentWebView.stopLoading();
    else
      reload();
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void reload() {
    if (TextUtils.isEmpty(url))
      return;

    viewContentWebView.loadUrl(url);
  }
}
