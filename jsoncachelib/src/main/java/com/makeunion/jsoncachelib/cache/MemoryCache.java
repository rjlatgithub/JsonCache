package com.makeunion.jsoncachelib.cache;


import android.util.LruCache;

import com.makeunion.jsoncachelib.api.Configuration;
import com.makeunion.jsoncachelib.log.Logger;

import java.io.UnsupportedEncodingException;
import java.util.List;


public class MemoryCache extends BaseCache {

    private LruCache<String, String> mLruCache;

    @Override
    public <T> void saveList(String key, List<T> list) {
        saveObject(key, list);
    }

    @Override
    public <T> List<T> loadList(String key, Class<T> clazz) {
        if (key == null) {
            return null;
        }
        if (clazz == null) {
            return null;
        }
        String loadString = mLruCache.get(key);
        if (loadString == null) {
            return null;
        }
        byte[] bytes = null;
        try {
            bytes = loadString.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            Logger.e("load UnsupportedEncodingException=" + e.getMessage());
        }
        return parseBytesToList(key, bytes, clazz);
    }

    @Override
    public void saveObject(String key, Object object) {
        if (key == null) {
            return;
        }
        byte[] bytes = parseObjectToBytes(object);
        if (bytes == null) {
            return;
        }
        String jsonString = null;
        try {
            jsonString = new String(bytes, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            Logger.e("save UnsupportedEncodingException=" + e.getMessage());
        }
        if (jsonString != null) {
            mLruCache.put(key, jsonString);
        }
    }

    @Override
    public <T> T loadObject(String key, Class<T> clazz) {
        if (key == null) {
            return null;
        }
        if (clazz == null) {
            return null;
        }
        String loadString = mLruCache.get(key);
        if (loadString == null) {
            return null;
        }
        byte[] bytes = null;
        try {
            bytes = loadString.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            Logger.e("load UnsupportedEncodingException=" + e.getMessage());
        }
        return parseBytesToObject(key, bytes, clazz);
    }

    @Override
    public void saveString(String key, String value) {
        if (key == null) {
            return;
        }
        byte[] bytes = parseStringToBytes(value);
        if (bytes == null) {
            return;
        }
        String jsonString = null;
        try {
            jsonString = new String(bytes, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            Logger.e("save UnsupportedEncodingException=" + e.getMessage());
        }
        if (jsonString != null) {
            mLruCache.put(key, jsonString);
        }
    }

    @Override
    public String loadString(String key) {
        if (key == null) {
            return null;
        }
        String loadString = mLruCache.get(key);
        if (loadString == null) {
            return null;
        }
        byte[] bytes = null;
        try {
            bytes = loadString.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            Logger.e("load UnsupportedEncodingException=" + e.getMessage());
        }
        return parseBytesToString(key, bytes);
    }

    @Override
    public void saveInt(String key, int value) {
        saveString(key, String.valueOf(value));
    }

    @Override
    public int loadInt(String key, int defaultValue) {
        String valueString = loadString(key);
        if (valueString == null) {
            return defaultValue;
        }
        return Integer.parseInt(valueString);
    }

    @Override
    public void saveFloat(String key, float value) {
        saveString(key, String.valueOf(value));
    }

    @Override
    public float loadFloat(String key, float defaultValue) {
        String valueString = loadString(key);
        if (valueString == null) {
            return defaultValue;
        }
        return Float.parseFloat(valueString);
    }

    @Override
    public void saveDouble(String key, double value) {
        saveString(key, String.valueOf(value));
    }

    @Override
    public double loadDouble(String key, double defaultValue) {
        String valueString = loadString(key);
        if (valueString == null) {
            return defaultValue;
        }
        return Double.parseDouble(valueString);
    }

    @Override
    protected void overdue(String key) {
        mLruCache.remove(key);
    }

    @Override
    public void clear(String key) {
        mLruCache.remove(key);
    }

    @Override
    public void clearAll() {
        mLruCache.evictAll();
    }

    @Override
    public void setConfig(Configuration config) {
        super.setConfig(config);
        mLruCache = new LruCache<>(config.getMemoryCacheCount());
    }
}