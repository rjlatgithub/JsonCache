package com.makeunion.jsoncachelib.api;


import android.content.Context;

import com.makeunion.jsoncachelib.cache.BaseCache;
import com.makeunion.jsoncachelib.cache.DiskCache;
import com.makeunion.jsoncachelib.cache.MemoryCache;
import com.makeunion.jsoncachelib.callback.ICallback;
import com.makeunion.jsoncachelib.threadpool.ThreadPool;

public class JsonCache {

    private static JsonCache sInstance;

    private Configuration mConfig;

    private BaseCache mMemoryCache;

    private BaseCache mDiskCache;

    private ThreadPool mThreadPool;

    private JsonCache() {
        mMemoryCache = new MemoryCache();
        mDiskCache = new DiskCache();
        mThreadPool = new ThreadPool(mMemoryCache, mDiskCache);
    }

    public static JsonCache getInstance() {
        if (sInstance == null) {
            synchronized (JsonCache.class) {
                if (sInstance == null) {
                    sInstance = new JsonCache();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        this.init(context, mConfig);
    }

    public void init(Context context, Configuration config) {
        if (config == null) {
            config = new DefaultConfiguration(context);
        }
        this.mConfig = config;
        this.mMemoryCache.setConfig(config);
        this.mDiskCache.setConfig(config);
    }

    public void saveObject(String key, Object object) {
        mMemoryCache.saveObject(key, object);
        mDiskCache.saveObject(key, object);
    }

    public void saveObjectAsync(String key, Object object) {
        mThreadPool.submitSaveObject(key, object);
    }

    public <T> T loadObject(String key, Class<T> clazz) {
        T cache = mMemoryCache.loadObject(key, clazz);
        if (cache == null) {
            cache = mDiskCache.loadObject(key, clazz);
        }
        return cache;
    }

    public <T> void loadObjectAsync(String key, Class<T> clazz, ICallback<T> callback) {
        mThreadPool.submitLoadObject(key, clazz, callback);
    }

    public void saveString(String key, String value) {
        mMemoryCache.saveString(key, value);
        mDiskCache.saveString(key, value);
    }

    public void saveStringAsync(String key, String value) {
        mThreadPool.submitSaveString(key, value);
    }

    public String loadString(String key) {
        String cache = mMemoryCache.loadString(key);
        if (cache == null) {
            cache = mDiskCache.loadString(key);
        }
        return cache;
    }

    public void loadStringAsync(String key, ICallback<String> callback) {
        mThreadPool.submitLoadString(key, callback);
    }

    public void saveInt(String key, int value) {
        mMemoryCache.saveInt(key, value);
        mDiskCache.saveInt(key, value);
    }

    public void saveIntAsync(String key, int value) {
        mThreadPool.submitSaveInt(key, value);
    }

    public int loadInt(String key, int defaultValue) {
        int cache = mMemoryCache.loadInt(key, defaultValue);
        if (cache == defaultValue) {
            cache = mDiskCache.loadInt(key, defaultValue);
        }
        return cache;
    }

    public void loadIntAsync(String key, int defaultValue, ICallback<Integer> callback) {
        mThreadPool.submitLoadInt(key, defaultValue, callback);
    }

    public void saveFloat(String key, float value) {
        mMemoryCache.saveFloat(key, value);
        mDiskCache.saveFloat(key, value);
    }

    public void saveFloatAsync(String key, float value) {
        mThreadPool.submitSaveFloat(key, value);
    }

    public float loadFloat(String key, float defaultValue) {
        float cache = mMemoryCache.loadFloat(key, defaultValue);
        if (cache == defaultValue) {
            cache = mDiskCache.loadFloat(key, defaultValue);
        }
        return cache;
    }

    public void loadFloatAsync(String key, float defaultValue, ICallback<Float> callback) {
        mThreadPool.submitLoadFloat(key, defaultValue, callback);
    }

    public void saveDouble(String key, double value) {
        mMemoryCache.saveDouble(key, value);
        mDiskCache.saveDouble(key, value);
    }

    public void saveDoubleAsync(String key, double value) {
        mThreadPool.submitSaveDouble(key, value);
    }

    public double loadDouble(String key, double defaultValue) {
        double cache = mMemoryCache.loadDouble(key, defaultValue);
        if (cache == defaultValue) {
            cache = mDiskCache.loadDouble(key, defaultValue);
        }
        return cache;
    }

    public void loadDoubleAsync(String key, double defaultValue, ICallback<Double> callback) {
        mThreadPool.submitLoadDouble(key, defaultValue, callback);
    }

    public void clear(String key) {
        mMemoryCache.clear(key);
        mDiskCache.clear(key);
    }

    public void clearAll() {
        mMemoryCache.clearAll();
        mDiskCache.clearAll();
    }
}