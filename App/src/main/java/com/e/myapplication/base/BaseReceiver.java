package com.e.myapplication.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;

public abstract class BaseReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        onReceive((ContextWrapper) context, intent);
    }

    protected abstract void onReceive(ContextWrapper context, Intent intent);
}
