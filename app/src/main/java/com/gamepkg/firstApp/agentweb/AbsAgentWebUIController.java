package com.gamepkg.firstApp.agentweb;

import android.app.Activity;
import android.app.Dialog;
import android.net.http.SslError;
import android.os.Handler;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;

public abstract class AbsAgentWebUIController {

    public static boolean HAS_DESIGN_LIB = false;
    private Activity mActivity;
    private WebParentLayout mWebParentLayout;
    private volatile boolean mIsBindWebParent = false;
    protected AbsAgentWebUIController mAgentWebUIControllerDelegate;
    protected String TAG = this.getClass().getSimpleName();

    static {
        try {
            Class.forName("com.google.android.material.snackbar.Snackbar");
            Class.forName("com.google.android.material.bottomsheet.BottomSheetDialog");
            HAS_DESIGN_LIB = true;
        } catch (Throwable ignore) {
            HAS_DESIGN_LIB = false;
            if (LogUtils.isDebug()) {
                ignore.printStackTrace();
            }
        }
	}

    protected AbsAgentWebUIController create() {
        return HAS_DESIGN_LIB ? new DefaultDesignUIController() : new DefaultUIController();
    }

    protected AbsAgentWebUIController getDelegate() {
        AbsAgentWebUIController mAgentWebUIController = this.mAgentWebUIControllerDelegate;
        if (mAgentWebUIController == null) {
            this.mAgentWebUIControllerDelegate = mAgentWebUIController = create();
        }
        return mAgentWebUIController;
    }

    final synchronized void bindWebParent(WebParentLayout webParentLayout, Activity activity) {
        if (!mIsBindWebParent) {
            mIsBindWebParent = true;
            this.mWebParentLayout = webParentLayout;
            this.mActivity = activity;
            bindSupportWebParent(webParentLayout, activity);
        }
    }

    protected void toDismissDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    protected void toShowDialog(Dialog dialog) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    protected abstract void bindSupportWebParent(WebParentLayout webParentLayout, Activity activity);

    /**
     * WebChromeClient#onJsAlert
     *
     * @param view
     * @param url
     * @param message
     */
    public abstract void onJsAlert(WebView view, String url, String message);

    /**
     * ????????????????????????????????????
     *
     * @param view
     * @param url
     * @param callback
     */
    public abstract void onOpenPagePrompt(WebView view, String url, Handler.Callback callback);

    /**
     * WebChromeClient#onJsConfirm
     *
     * @param view
     * @param url
     * @param message
     * @param jsResult
     */
    public abstract void onJsConfirm(WebView view, String url, String message, JsResult jsResult);

    public abstract void onSelectItemsPrompt(WebView view, String url, String[] ways, Handler.Callback callback);

    /**
     * ??????????????????
     *
     * @param url      ?????????????????????
     * @param callback ????????????????????????
     */
    public abstract void onForceDownloadAlert(String url, Handler.Callback callback);

    /**
     * WebChromeClient#onJsPrompt
     *
     * @param view
     * @param url
     * @param message
     * @param defaultValue
     * @param jsPromptResult
     */
    public abstract void onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult jsPromptResult);

    /**
     * ???????????????
     *
     * @param view
     * @param errorCode
     * @param description
     * @param failingUrl
     */
    public abstract void onMainFrameError(WebView view, int errorCode, String description, String failingUrl);

    /**
     * ???????????????
     */
    public abstract void onShowMainFrame();

    /**
     * ????????????...
     *
     * @param msg
     */
    public abstract void onLoading(String msg);

    /**
     * ??????????????????...
     */
    public abstract void onCancelLoading();

    /**
     * @param message ??????
     * @param intent  ??????message??????????????????
     */
    public abstract void onShowMessage(String message, String intent);

    /**
     * ??????????????????????????????
     *
     * @param permissions
     * @param permissionType
     * @param action
     */
    public abstract void onPermissionsDeny(String[] permissions, String permissionType, String action);

	/**
	 *
	 * @param view
	 * @param handler
	 * @param error
	 */
	public abstract void onShowSslCertificateErrorDialog(WebView view, SslErrorHandler handler, SslError error);

	/**
	 * ????????????
	 * @param request
	 */
	public abstract void onPermissionRequest(PermissionRequest request);
}
