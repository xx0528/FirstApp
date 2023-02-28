package com.gamepkg.firstApp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;


import com.gamepkg.firstApp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        //创建子线程
        Thread mThread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);    // 使程序休眠3秒
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

}