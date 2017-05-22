/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.neal.repairer.ease.receiver;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.hyphenate.util.EMLog;
import com.neal.repairer.R;
import com.neal.repairer.ease.ui.VideoCallActivity;

import java.io.File;

import static android.content.Context.NOTIFICATION_SERVICE;

public class CallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //username
        String from = intent.getStringExtra("from");

        EMLog.d("CallReceiver", "app received a incoming call");
        //判断程序是否在前台运行
        if (isRunningForeground(context)) {
            context.startActivity(new Intent(context, VideoCallActivity.class).
                    putExtra("username", from).putExtra("isComingCall", true).
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else {
            // 创建一个PendingIntent，和Intent类似，不同的是由于不是马上调用，需要在下拉状态条出发的activity，所以采用的是PendingIntent,即点击Notification跳转启动到哪个Activity
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    new Intent(context, VideoCallActivity.class).
                            putExtra("username", from).putExtra("isComingCall", true).
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK), 0);
            //构造通知
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentIntent(pendingIntent);
            builder.setSmallIcon(R.mipmap.ic_launcher_round);
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
            builder.setAutoCancel(true);
            builder.setTicker("电梯出现意外，请接听！");
            builder.setContentText("点击接听");
            builder.setContentTitle("紧急通话");
            builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
            //发送通知
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(0, builder.build());
        }
    }

    private boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if (!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName())) {
            return true;
        }

        return false;
    }

}
