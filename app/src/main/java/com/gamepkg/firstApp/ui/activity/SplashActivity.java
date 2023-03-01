package com.gamepkg.firstApp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.gamepkg.firstApp.R;
import com.gamepkg.firstApp.utils.okhttp.CallBackUtil;
import com.gamepkg.firstApp.utils.okhttp.OkhttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import okhttp3.Call;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {

    public static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        //http://ip-api.com/json  https://iplist.cc/api
        OkhttpUtil.okHttpGet("http://ip-api.com/json", new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Log.e(TAG, e.toString());
            }
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jSONObject = new JSONObject(response);
                    String countryCodeStr = "countryCode";
                    if (jSONObject.has(countryCodeStr)) {
                        String countryCode = jSONObject.getString(countryCodeStr);
                        onGetConfig(countryCode);
                    }
                    else {
                        Log.d(TAG, "没有记录的国家" + response);
                    }
                } catch (Exception e2) {
                    Log.e(TAG, "请求获取IP对应国家失败");
                    e2.printStackTrace();
                    return;
                }
            }
        });



        //创建子线程
        Thread mThread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(5000);    // 使程序休眠3秒
                    if (WebActivity.sInstance != null && WebActivity.sInstance.getWindow().getDecorView().getVisibility() == View.VISIBLE)
                        return;

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        mThread.start();
    }

    void onGetConfig(String countryCode) {
        String url = "http://192.168.3.27:8080/config.json";
        OkhttpUtil.okHttpGet(url, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Log.e(TAG, e.toString());
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jSONObject = new JSONObject(response);
                    if (jSONObject.has(countryCode)) {
                        JSONObject jObj = jSONObject.getJSONObject(countryCode);
                        if (jObj.getBoolean("isOpen")) {
                            //开启
                            Log.e(TAG, String.format( "国家%s 开启", countryCode));
                            Toast.makeText(SplashActivity.this,"Success",Toast.LENGTH_SHORT).show();
                            String url = jObj.getString("url");
                            Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(WebActivity.URL_KEY, url);
                            startActivity(intent);
                        }
                        else {
                            Log.e(TAG, "未开启");
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}