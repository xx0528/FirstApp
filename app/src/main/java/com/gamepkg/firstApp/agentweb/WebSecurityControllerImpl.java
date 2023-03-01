package com.gamepkg.firstApp.agentweb;

import android.os.Build;
import androidx.collection.ArrayMap;
import android.webkit.WebView;


public class WebSecurityControllerImpl implements WebSecurityController<WebSecurityCheckLogic> {

	private WebView mWebView;
	private ArrayMap<String, Object> mMap;
	private AgentWeb.SecurityType mSecurityType;

	public WebSecurityControllerImpl(WebView view, ArrayMap<String, Object> map, AgentWeb.SecurityType securityType) {
		this.mWebView = view;
		this.mMap = map;
		this.mSecurityType = securityType;
	}

	@Override
	public void check(WebSecurityCheckLogic webSecurityCheckLogic) {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
			webSecurityCheckLogic.dealHoneyComb(mWebView);
		}
		if (mMap != null && mSecurityType == AgentWeb.SecurityType.STRICT_CHECK && !mMap.isEmpty()) {
			webSecurityCheckLogic.dealJsInterface(mMap, mSecurityType);
		}
	}
}
