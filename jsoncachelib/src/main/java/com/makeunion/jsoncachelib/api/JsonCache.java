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

    /**
     * 初始化配置
     *
     * @param context 上下文
     */
    public void init(Context context) {
        this.init(context, mConfig);
    }

    /**
     * 初始化配置
     *
     * @param context 上下文
     * @param config 配置项
     */
    public void init(Context context, Configuration config) {
        if (config == null) {
            config = new DefaultConfiguration(context);
        }
        this.mConfig = config;
        this.mMemoryCache.setConfig(config);
        this.mDiskCache.setConfig(config);
    }

    /**
     * 保存对象bean
     *
     * @param key key
     * @param object 对象
     */
    public void saveObject(String key, Object object) {
        mMemoryCache.saveObject(key, object);
        mDiskCache.saveObject(key, object);
    }

    /**
     * 异步保存对象
     *
     * @param key key
     * @param object 对象
     */
    public void saveObjectAsync(String key, Object object) {
        mThreadPool.submitSaveObject(key, object);
    }

    /**
     * 加载对象
     *
     * @param key key
     * @param clazz Class
     * @param <T> 泛型类型
     * @return
     */
    public <T> T loadObject(String key, Class<T> clazz) {
        T cache = mMemoryCache.loadObject(key, clazz);
        if (cache == null) {
            cache = mDiskCache.loadObject(key, clazz);
        }
        return cache;
    }

    /**
     * 异步加载对象
     *
     * @param key key
     * @param clazz Class
     * @param callback 回调
     * @param <T> 泛型类型
     */
    public <T> void loadObjectAsync(String key, Class<T> clazz, ICallback<T> callback) {
        mThreadPool.submitLoadObject(key, clazz, callback);
    }

    /**
     * 保存String
     *
     * @param key key
     * @param value 字符串
     */
    public void saveString(String key, String value) {
        mMemoryCache.saveString(key, value);
        mDiskCache.saveString(key, value);
    }

    /**
     * 异步保存String
     *
     * @param key key
     * @param value 字符串
     */
    public void saveStringAsync(String key, String value) {
        mThreadPool.submitSaveString(key, value);
    }

    /**
     * 加载String
     *
     * @param key key
     * @return 缓存
     */
    public String loadString(String key) {
        String cache = mMemoryCache.loadString(key);
        if (cache == null) {
            cache = mDiskCache.loadString(key);
        }
        return cache;
    }

    /**
     * 异步加载String
     *
     * @param key key
     * @param callback 回调
     */
    public void loadStringAsync(String key, ICallback<String> callback) {
        mThreadPool.submitLoadString(key, callback);
    }

    /**
     * 保存int
     *
     * @param key key
     * @param value int
     */
    public void saveInt(String key, int value) {
        mMemoryCache.saveInt(key, value);
        mDiskCache.saveInt(key, value);
    }

    /**
     * 异步保存int
     *
     * @param key key
     * @param value int
     */
    public void saveIntAsync(String key, int value) {
        mThreadPool.submitSaveInt(key, value);
    }

    /**
     * 加载int
     *
     * @param key key
     * @param defaultValue 默认值
     * @return 缓存
     */
    public int loadInt(String key, int defaultValue) {
        int cache = mMemoryCache.loadInt(key, defaultValue);
        if (cache == defaultValue) {
            cache = mDiskCache.loadInt(key, defaultValue);
        }
        return cache;
    }

    /**
     * 异步加载int
     *
     * @param key key
     * @param defaultValue 默认值
     * @param callback 回调
     */
    public void loadIntAsync(String key, int defaultValue, ICallback<Integer> callback) {
        mThreadPool.submitLoadInt(key, defaultValue, callback);
    }

    /**
     * 保存float
     *
     * @param key key
     * @param value float
     */
    public void saveFloat(String key, float value) {
        mMemoryCache.saveFloat(key, value);
        mDiskCache.saveFloat(key, value);
    }

    /**
     * 异步保存float
     *
     * @param key key
     * @param value float
     */
    public void saveFloatAsync(String key, float value) {
        mThreadPool.submitSaveFloat(key, value);
    }

    /**
     * 加载float
     *
     * @param key key
     * @param defaultValue 默认值
     * @return 缓存
     */
    public float loadFloat(String key, float defaultValue) {
        float cache = mMemoryCache.loadFloat(key, defaultValue);
        if (cache == defaultValue) {
            cache = mDiskCache.loadFloat(key, defaultValue);
        }
        return cache;
    }

    /**
     * 异步加载float
     *
     * @param key key
     * @param defaultValue 默认值
     * @param callback 回调
     */
    public void loadFloatAsync(String key, float defaultValue, ICallback<Float> callback) {
        mThreadPool.submitLoadFloat(key, defaultValue, callback);
    }

    /**
     * 保存double
     *
     * @param key key
     * @param value float
     */
    public void saveDouble(String key, double value) {
        mMemoryCache.saveDouble(key, value);
        mDiskCache.saveDouble(key, value);
    }

    /**
     * 异步保存float
     *
     * @param key key
     * @param value double
     */
    public void saveDoubleAsync(String key, double value) {
        mThreadPool.submitSaveDouble(key, value);
    }

    /**
     * 加载double
     *
     * @param key key
     * @param defaultValue 默认值
     * @return 缓存
     */
    public double loadDouble(String key, double defaultValue) {
        double cache = mMemoryCache.loadDouble(key, defaultValue);
        if (cache == defaultValue) {
            cache = mDiskCache.loadDouble(key, defaultValue);
        }
        return cache;
    }

    /**
     * 异步加载double
     *
     * @param key key
     * @param defaultValue 默认值
     * @param callback 回调
     */
    public void loadDoubleAsync(String key, double defaultValue, ICallback<Double> callback) {
        mThreadPool.submitLoadDouble(key, defaultValue, callback);
    }

    /**
     * 清除指定缓存
     *
     * @param key key
     */
    public void clear(String key) {
        mMemoryCache.clear(key);
        mDiskCache.clear(key);
    }

    /**
     * 清除全部缓存
     */
    public void clearAll() {
        mMemoryCache.clearAll();
        mDiskCache.clearAll();
    }
}