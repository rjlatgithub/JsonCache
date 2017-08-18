package com.makeunion.jsoncachelib.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.makeunion.jsoncachelib.api.Configuration;
import com.makeunion.jsoncachelib.compress.CompressUtil;
import com.makeunion.jsoncachelib.util.StringUtil;


public abstract class BaseCache {

    protected static final String TIMESTAMP = "timestamp";

    protected static final String VALUE = "value";

    protected Configuration mConfig;

    public abstract void saveObject(String key, Object object);

    public abstract <T> T loadObject(String key, Class<T> clazz);

    public abstract void saveString(String key, String value);

    public abstract String loadString(String key);

    public abstract void saveInt(String key, int value);

    public abstract int loadInt(String key, int defaultValue);

    public abstract void saveFloat(String key, float value);

    public abstract float loadFloat(String key, float defaultValue);

    public abstract void saveDouble(String key, double value);

    public abstract double loadDouble(String key, double defaultValue);

    public abstract void clear(String key);

    public abstract void clearAll();

    protected abstract void overdue(String key);

    private byte[] buildBytes(Object object) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TIMESTAMP, System.currentTimeMillis());
        jsonObject.put(VALUE, object);
        String jsonString = jsonObject.toJSONString();
        return CompressUtil.compress(jsonString);
    }

    private JSONObject parseJsonObject(String key, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        String loadString = CompressUtil.decompress(bytes);
        JSONObject jsonObject = JSONObject.parseObject(loadString);
        if (jsonObject == null) {
            return null;
        }
        long cacheTimestamp = jsonObject.getLong(TIMESTAMP);
        if (System.currentTimeMillis() - cacheTimestamp > mConfig.getMemoryCacheTime()) {
            overdue(key);
            return null;
        }
        return jsonObject;
    }

    protected final byte[] parseObjectToBytes(Object object) {
        return buildBytes(object == null ? null : JSON.toJSONString(object));
    }

    protected final <T> T parseBytesToObject(String key, byte[] bytes, Class<T> clazz) {
        JSONObject jsonObject = parseJsonObject(key, bytes);
        if (jsonObject == null) {
            return null;
        }
        String value = jsonObject.getString(VALUE);
        if (StringUtil.isNull(value)) {
            return null;
        }
        return JSON.parseObject(value, clazz);
    }

    protected final byte[] parseStringToBytes(String value) {
        return buildBytes(value);
    }

    protected final String parseBytesToString(String key, byte[] bytes) {
        JSONObject jsonObject = parseJsonObject(key, bytes);
        if (jsonObject == null) {
            return null;
        }
        return jsonObject.getString(VALUE);
    }

    public void setConfig(Configuration config) {
        mConfig = config;
    }
}
