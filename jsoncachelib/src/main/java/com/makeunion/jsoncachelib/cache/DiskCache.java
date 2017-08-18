package com.makeunion.jsoncachelib.cache;


import com.makeunion.jsoncachelib.api.Configuration;
import com.makeunion.jsoncachelib.io.FileHelper;


public class DiskCache extends BaseCache {

    private FileHelper mFileHelper;

    @Override
    public void saveObject(String key, Object object) {
        if (key == null) {
            return;
        }
        byte[] bytes = parseObjectToBytes(object);
        if (bytes == null) {
            return;
        }
        mFileHelper.writeObject(key, bytes);
    }

    @Override
    public <T> T loadObject(String key, Class<T> clazz) {
        if (key == null) {
            return null;
        }
        if (clazz == null) {
            return null;
        }
        byte[] data = mFileHelper.readObject(key);
        return parseBytesToObject(key, data, clazz);
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
        mFileHelper.writeObject(key, bytes);
    }

    @Override
    public String loadString(String key) {
        if (key == null) {
            return null;
        }
        byte[] data = mFileHelper.readObject(key);
        return parseBytesToString(key, data);
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
        mFileHelper.deleteFile(key);
    }

    @Override
    public void clear(String key) {
        mFileHelper.deleteFile(key);
    }

    @Override
    public void clearAll() {
        mFileHelper.deleteAllFiles();
    }

    @Override
    public void setConfig(Configuration config) {
        super.setConfig(config);
        mFileHelper = new FileHelper(mConfig);
    }
}
