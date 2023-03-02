package com.gamepkg.firstApp.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.gamepkg.firstApp.R;
import com.gamepkg.firstApp.ui.base.BaseAgentWebActivity;

public class WebActivity extends BaseAgentWebActivity {

    public static final String TAG = WebActivity.class.getSimpleName();
    public static final String URL_KEY = "url_key";
    public static WebActivity sInstance = null;
    private TextView mTitleTextView;
    private String mUrl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //第一步：找到 swipeRefresh 控件
        SwipeRefreshLayout swip_refresh = findViewById(R.id.swip_refresh);
        //第二步，设置 下拉刷新时的颜色
        swip_refresh.setColorSchemeColors(Color.parseColor("#ff0000"),Color.parseColor("#00ff00"));
        swip_refresh.setProgressBackgroundColorSchemeColor(Color.parseColor("#0000ff"));
        //监听 刷新是回调
        swip_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAgentWeb.getUrlLoader().reload();
                //判断是否在刷新
//                Toast.makeText(WebActivity.this, swip_refresh.isRefreshing()?"正在刷新":"刷新完成"
//                        ,Toast.LENGTH_SHORT).show();
                swip_refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //关闭刷新
                        swip_refresh.setRefreshing(false);
                    }
                },500);
            }
        });

        sInstance = this;
    }


    @NonNull
    @Override
    protected ViewGroup getAgentWebParent() {
        return (ViewGroup) this.findViewById(R.id.container);
    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
//            return true;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    protected int getIndicatorColor() {
        return Color.parseColor("#ff0000");
    }

    @Override
    protected void setTitle(WebView view, String title) {
        super.setTitle(view, title);
        if (!TextUtils.isEmpty(title)) {
            if (title.length() > 10) {
                title = title.substring(0, 10).concat("...");
            }
        }
    }
    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected int getIndicatorHeight() {
        return 3;
    }

    @Nullable
    @Override
    protected String getUrl() {
        String url = getIntent().getStringExtra("url_key");
        Log.e(TAG, " url:" + url);
        return url;
    }
}
