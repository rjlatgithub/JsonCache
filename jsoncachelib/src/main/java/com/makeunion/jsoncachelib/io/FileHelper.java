package com.makeunion.jsoncachelib.io;

import com.makeunion.jsoncachelib.api.Configuration;
import com.makeunion.jsoncachelib.log.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class FileHelper {

    private static final String SUFFIX = ".jc";

    private Configuration mConfig;

    private ReadWriteLock mReadWriteLock;

    public FileHelper(Configuration config) {
        mConfig = config;
        mReadWriteLock = new ReentrantReadWriteLock();
    }

    public void writeObject(String key, byte[] bytes) {
        mReadWriteLock.writeLock().lock();
        if (bytes == null) {
            return;
        }
        File rootFile = new File(mConfig.getCacheDir());
        if (!rootFile.exists() && !rootFile.mkdirs()) {
            return;
        }
        if (!rootFile.isDirectory()) {
            return;
        }
        File[] files = rootFile.listFiles();
        if (files != null && files.length >= mConfig.getDiskCacheCount()) {
            Arrays.sort(files, new Comparator<File>() {
                @Override
                public int compare(File f1, File f2) {
                    return (int)(f1.lastModified() - f2.lastModified());
                }
            });
            for (int i = mConfig.getDiskCacheCount() - 1; i < files.length; i++) {
                if (!files[i].delete()) {
                    Logger.w("delete file fail.[" + files[i].getName() + "]");
                }
            }
        }

        File file = new File(mConfig.getCacheDir(), key + SUFFIX);
        FileOutputStream fos;
        try {
            if (!file.exists() && !file.createNewFile()) {
                return;
            }
            fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            mReadWriteLock.writeLock().unlock();
            Logger.e("writeObject IOException=" + e.getMessage());
        }
        mReadWriteLock.writeLock().unlock();
    }

    public byte[] readObject(String key) {
        mReadWriteLock.readLock().lock();
        if (key == null) {
            return null;
        }
        File file = new File(mConfig.getCacheDir(), key + SUFFIX);
        if (!file.exists()) {
            return null;
        }
        FileInputStream fis;
        byte[] data = null;
        int readResult = -1;
        try {
            fis = new FileInputStream(file);
            data = new byte[fis.available()];
            readResult = fis.read(data);
            fis.close();
        } catch (IOException e) {
            mReadWriteLock.readLock().unlock();
            Logger.e("writeObject IOException=" + e.getMessage());
        }
        mReadWriteLock.readLock().unlock();
        return readResult == -1 ? null : data;
    }

    public void deleteFile(String key) {
        File file = new File(mConfig.getCacheDir(), key + SUFFIX);
        if (file.exists()) {
            if (!file.delete()) {
                Logger.w("delete file fail.file=" + file.getName());
            }
        }
    }

    public void deleteAllFiles() {
        File file = new File(mConfig.getCacheDir());
        if (!file.exists() || !file.isDirectory()) {
            return;
        }
        File[] files = file.listFiles();
        if (files != null && files.length > 0) {
            for (File f : files) {
                if (!f.delete()) {
                    Logger.w("delete file fail.file=" + file.getName());
                }
            }
        }
    }
}