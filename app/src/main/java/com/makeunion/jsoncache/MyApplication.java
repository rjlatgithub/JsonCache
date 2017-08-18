package com.makeunion.jsoncache;

import android.app.Application;

import com.makeunion.jsoncachelib.api.JsonCache;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JsonCache.getInstance().init(getApplicationContext());
    }
}
