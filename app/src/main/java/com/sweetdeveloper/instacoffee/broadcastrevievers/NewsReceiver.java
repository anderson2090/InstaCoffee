package com.sweetdeveloper.instacoffee.broadcastrevievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NewsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
       String news = intent.getExtras().getString("news");
       NewsObservable.getInstance().updateValue(intent);
    }
}
