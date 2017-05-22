package com.neal.repairer.activity;
/**
 * 活动基类
 * Created by lichao on 17/5/2.
 */

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public abstract class BaseActivity extends FragmentActivity {

    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        context = getApplicationContext();
        setContentView(setContent());
        initViews();
        initResource();
        initTitleBar();
        setTitleBar();
        setViews();
        hideBottomUIMenu();

    }

    /**
     * 设置布局文件
     *
     * @return 布局ID
     */
    protected abstract int setContent();


    /**
     * 初始化资源
     */
    protected abstract void initResource();

    /**
     * 初始化控件
     */
    protected abstract void initViews();

    /**
     * 设置控件
     */
    protected abstract void setViews();


    /**
     * 初始化标题
     */
    protected abstract void initTitleBar();

    /**
     * 设置标题
     */
    protected abstract void setTitleBar();
    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //隐藏虚拟按键，并且全屏
                if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
                    View v = BaseActivity.this.getWindow().getDecorView();
                    v.setSystemUiVisibility(View.GONE);
                } else if (Build.VERSION.SDK_INT >= 19) {
                    //for new api versions.
                    View decorView = getWindow().getDecorView();
                    int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                    decorView.setSystemUiVisibility(uiOptions);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        hideBottomUIMenu();
    }
}
