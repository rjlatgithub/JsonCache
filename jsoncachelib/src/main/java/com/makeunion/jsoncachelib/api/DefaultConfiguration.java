package com.makeunion.jsoncachelib.api;

import android.content.Context;


public class DefaultConfiguration extends Configuration {

    private static final int DEFAULT_MEMORY_CACHE_COUNT = 10;
    private static final int DEFAULT_DISK_CACHE_COUNT = 20;

    public DefaultConfiguration(Context context) {
        super(context.getCacheDir().getAbsolutePath(),
                DEFAULT_MEMORY_CACHE_COUNT, Configuration.DAY,
                DEFAULT_DISK_CACHE_COUNT, Configuration.MONTH);
    }
}
