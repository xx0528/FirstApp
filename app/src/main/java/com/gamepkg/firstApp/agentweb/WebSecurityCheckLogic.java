package com.gamepkg.firstApp.agentweb;

import androidx.collection.ArrayMap;
import android.webkit.WebView;

public interface WebSecurityCheckLogic {
    void dealHoneyComb(WebView view);
    void dealJsInterface(ArrayMap<String, Object> objects,AgentWeb.SecurityType securityType);
}
