package com.neal.repairer.util;

import android.widget.Toast;

import com.neal.repairer.MyApplication;


/**
 * 简易吐司
 * Created by lichao on 17/5/3.
 */

public class Toasts {
    public static void makeText(String content) {
        Toast.makeText(MyApplication.applicationContext, content, Toast.LENGTH_SHORT).show();
    }
}
