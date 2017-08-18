package com.makeunion.jsoncachelib.log;

import android.util.Log;

public class Logger {
    private static final String LOG_TAG = "JsonCache";

    public static void v(String log) {
        Log.v(LOG_TAG, log);
    }

    public static void i(String log) {
        Log.i(LOG_TAG, log);
    }

    public static void d(String log) {
        Log.d(LOG_TAG, log);
    }

    public static void w(String log) {
        Log.w(LOG_TAG, log);
    }

    public static void e(String log) {
        Log.e(LOG_TAG, log);
    }
}
