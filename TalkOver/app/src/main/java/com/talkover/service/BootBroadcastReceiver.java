package com.talkover.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        /* 服务开机自启动 */
        Intent service = new Intent(context, VoiceToTextService.class);
        context.startService(service);
    }
}
