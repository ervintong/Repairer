package com.neal.repairer.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telecom.Call;

import com.hyphenate.chat.EMClient;
import com.neal.repairer.ease.receiver.CallReceiver;

/**
 * 监听环信通话，发起通知
 * Create by lichao 2016/5/19
 */

public class ChatService extends Service {
    public ChatService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //监听呼入电话
        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        CallReceiver callReceiver = new CallReceiver();
        registerReceiver(callReceiver, callFilter);

        return super.onStartCommand(intent, flags, startId);
    }


}
