package com.gamepkg.firstApp.agentweb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.ViewGroup;
import android.webkit.WebView;

public interface IWebLayout<T extends WebView,V extends ViewGroup> {

    /**
     *
     * @return WebView 的父控件
     */
    @NonNull V getLayout();

    /**
     *
     * @return 返回 WebView  或 WebView 的子View ，返回null AgentWeb 内部会创建适当 WebView
     */
    @Nullable T getWebView();
}
