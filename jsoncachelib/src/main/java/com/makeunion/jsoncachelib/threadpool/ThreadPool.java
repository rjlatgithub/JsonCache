package com.makeunion.jsoncachelib.threadpool;


import android.os.Handler;

import com.makeunion.jsoncachelib.cache.BaseCache;
import com.makeunion.jsoncachelib.callback.ICallback;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    private ExecutorService mExecutor;

    private BaseCache mMemoryCache;

    private BaseCache mDiskCache;

    private Handler mHandler = new Handler();

    private static class SyncRunnable<T> implements Runnable {

        private T object;
        private ICallback<T> callback;

        public SyncRunnable(T object, ICallback<T> callback) {
            this.object = object;
            this.callback = callback;
        }

        @Override
        public void run() {
            callback.onResult(object);
        }
    }

    public ThreadPool(BaseCache memoryCache, BaseCache diskCache) {
        mMemoryCache = memoryCache;
        mDiskCache = diskCache;
        mExecutor = Executors.newCachedThreadPool(
                new DefaultThreadFactory(Thread.NORM_PRIORITY, "cache-pool-d-"));
    }

    public <T> void submitSaveList(String key, List<T> list) {
        mExecutor.execute(new SaveListTask<T>(key, list));
    }

    public <T> void submitLoadList(String key, Class<T> clazz, ICallback<List<T>> callback) {
        mExecutor.execute(new LoadListTask<T>(key, clazz, callback));
    }

    public void submitSaveObject(String key, Object object) {
        mExecutor.execute(new SaveObjectTask(key, object));
    }

    public <T> void submitLoadObject(String key, Class<T> clazz, ICallback<T> callback) {
        mExecutor.execute(new LoadObjectTask<T>(key, clazz, callback));
    }

    public void submitSaveString(String key, String value) {
        mExecutor.execute(new SaveStringTask(key, value));
    }

    public <T> void submitLoadString(String key, ICallback<String> callback) {
        mExecutor.execute(new LoadStringTask(key, callback));
    }

    public void submitSaveInt(String key, int value) {
        mExecutor.execute(new SaveIntTask(key, value));
    }

    public void submitLoadInt(String key, int defaultValue, ICallback<Integer> callback) {
        mExecutor.execute(new LoadIntTask(key, defaultValue, callback));
    }

    public void submitSaveFloat(String key, float value) {
        mExecutor.execute(new SaveFloatTask(key, value));
    }

    public void submitLoadFloat(String key, float defaultValue, ICallback<Float> callback) {
        mExecutor.execute(new LoadFloatTask(key, defaultValue, callback));
    }

    public void submitSaveDouble(String key, double value) {
        mExecutor.execute(new SaveDoubleTask(key, value));
    }

    public void submitLoadDouble(String key, double defaultValue, ICallback<Double> callback) {
        mExecutor.execute(new LoadDoubleTask(key, defaultValue, callback));
    }

    private class SaveListTask<T> implements Runnable {

        String key;
        List<T> list;

        public SaveListTask(String key, List<T> list) {
            this.key = key;
            this.list = list;
        }

        @Override
        public void run() {
            mMemoryCache.saveList(key, list);
            mDiskCache.saveList(key, list);
        }
    }

    private class LoadListTask<T> implements Runnable {

        String key;
        Class<T> clazz;
        ICallback<List<T>> callback;

        public LoadListTask(String key, Class<T> clazz, ICallback<List<T>> callback) {
            this.key = key;
            this.clazz = clazz;
            this.callback = callback;
        }

        @Override
        public void run() {
            List<T> cache = mMemoryCache.loadList(key, clazz);
            if (cache == null) {
                cache = mDiskCache.loadList(key, clazz);
            }
            mHandler.post(new SyncRunnable<List<T>>(cache, callback));
        }
    }

    private class SaveObjectTask implements Runnable {

        String key;
        Object object;

        public SaveObjectTask(String key, Object object) {
            this.key = key;
            this.object = object;
        }

        @Override
        public void run() {
            mMemoryCache.saveObject(key, object);
            mDiskCache.saveObject(key, object);
        }
    }

    private class LoadObjectTask<T> implements Runnable {

        String key;
        Class<T> clazz;
        ICallback<T> callback;

        public LoadObjectTask(String key, Class<T> clazz, ICallback<T> callback) {
            this.key = key;
            this.clazz = clazz;
            this.callback = callback;
        }

        @Override
        public void run() {
            T cache = mMemoryCache.loadObject(key, clazz);
            if (cache == null) {
                cache = mDiskCache.loadObject(key, clazz);
            }
            mHandler.post(new SyncRunnable<T>(cache, callback));
        }
    }

    private class SaveStringTask implements Runnable {

        String key;
        String value;

        public SaveStringTask(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public void run() {
            mMemoryCache.saveString(key, value);
            mDiskCache.saveString(key, value);
        }
    }

    private class LoadStringTask implements Runnable {

        String key;
        ICallback<String> callback;

        public LoadStringTask(String key, ICallback<String> callback) {
            this.key = key;
            this.callback = callback;
        }

        @Override
        public void run() {
            String cache = mMemoryCache.loadString(key);
            if (cache == null) {
                cache = mDiskCache.loadString(key);
            }
            mHandler.post(new SyncRunnable<String>(cache, callback));
        }
    }

    private class SaveIntTask implements Runnable {

        String key;
        int value;

        public SaveIntTask(String key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public void run() {
            mMemoryCache.saveInt(key, value);
            mDiskCache.saveInt(key, value);
        }
    }

    private class LoadIntTask implements Runnable {

        String key;
        int defaultValue;
        ICallback<Integer> callback;

        public LoadIntTask(String key, int defaultValue, ICallback<Integer> callback) {
            this.key = key;
            this.defaultValue = defaultValue;
            this.callback = callback;
        }

        @Override
        public void run() {
            int cache = mMemoryCache.loadInt(key, defaultValue);
            if (cache == defaultValue) {
                cache = mDiskCache.loadInt(key, defaultValue);
            }
            mHandler.post(new SyncRunnable<Integer>(cache, callback));
        }
    }

    private class SaveFloatTask implements Runnable {

        String key;
        float value;

        public SaveFloatTask(String key, float value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public void run() {
            mMemoryCache.saveFloat(key, value);
            mDiskCache.saveFloat(key, value);
        }
    }

    private class LoadFloatTask implements Runnable {

        String key;
        float defaultValue;
        ICallback<Float> callback;

        public LoadFloatTask(String key, float defaultValue, ICallback<Float> callback) {
            this.key = key;
            this.defaultValue = defaultValue;
            this.callback = callback;
        }

        @Override
        public void run() {
            float cache = mMemoryCache.loadFloat(key, defaultValue);
            if (cache == defaultValue) {
                cache = mDiskCache.loadFloat(key, defaultValue);
            }
            mHandler.post(new SyncRunnable<Float>(cache, callback));
        }
    }

    private class SaveDoubleTask implements Runnable {

        String key;
        double value;

        public SaveDoubleTask(String key, double value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public void run() {
            mMemoryCache.saveDouble(key, value);
            mDiskCache.saveDouble(key, value);
        }
    }

    private class LoadDoubleTask implements Runnable {

        String key;
        double defaultValue;
        ICallback<Double> callback;

        public LoadDoubleTask(String key, double defaultValue, ICallback<Double> callback) {
            this.key = key;
            this.defaultValue = defaultValue;
            this.callback = callback;
        }

        @Override
        public void run() {
            double cache = mMemoryCache.loadDouble(key, defaultValue);
            if (cache == defaultValue) {
                cache = mDiskCache.loadDouble(key, defaultValue);
            }
            mHandler.post(new SyncRunnable<Double>(cache, callback));
        }
    }
}