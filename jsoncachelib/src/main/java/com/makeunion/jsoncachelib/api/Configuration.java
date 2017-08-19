package com.makeunion.jsoncachelib.api;


public class Configuration {

    public static final long SECOND = 1000;
    public static final long MINUTE = SECOND * 60;
    public static final long HOUR = MINUTE * 60;
    public static final long DAY = HOUR * 24;
    public static final long WEEK = DAY * 7;
    public static final long MONTH = DAY * 30;
    public static final long YEAR = MONTH * 12;

    private int diskCacheCount;

    private long diskCacheTime;

    private int memoryCacheCount;

    private long memoryCacheTime;

    private String cacheDir;

    public Configuration(){}

    public Configuration(String cacheDir,
            int memoryCacheCount, long memoryCacheTime,
            int diskCacheCount, long diskCacheTime) {
        this.cacheDir = cacheDir;
        this.memoryCacheCount = memoryCacheCount;
        this.memoryCacheTime = memoryCacheTime;
        this.diskCacheCount = diskCacheCount;
        this.diskCacheTime = diskCacheTime;
    }

    public int getDiskCacheCount() {
        return diskCacheCount;
    }

    public void setDiskCacheCount(int diskCacheCount) {
        this.diskCacheCount = diskCacheCount;
    }

    public long getDiskCacheTime() {
        return diskCacheTime;
    }

    public void setDiskCacheTime(long diskCacheTime) {
        this.diskCacheTime = diskCacheTime;
    }

    public int getMemoryCacheCount() {
        return memoryCacheCount;
    }

    public void setMemoryCacheCount(int memoryCacheCount) {
        this.memoryCacheCount = memoryCacheCount;
    }

    public long getMemoryCacheTime() {
        return memoryCacheTime;
    }

    public void setMemoryCacheTime(long memoryCacheTime) {
        this.memoryCacheTime = memoryCacheTime;
    }

    public String getCacheDir() {
        return cacheDir;
    }

    public void setCacheDir(String cacheDir) {
        this.cacheDir = cacheDir;
    }

    public static class Builder {

        private String cacheDir;

        private int diskCacheCount;

        private long diskCacheSize;

        private long diskCacheTime;

        private int memoryCacheCount;

        private long memoryCacheTime;

        private boolean doAsync;

        public Builder cacheDir(String dir) {
            cacheDir = dir;
            return this;
        }

        public Builder diskCacheCount(int count) {
            diskCacheCount = count;
            return this;
        }

        public Builder diskCacheSize(long size) {
            diskCacheSize = size;
            return this;
        }

        public Builder diskCacheTime(long time) {
            diskCacheTime = time;
            return this;
        }

        public Builder memoryCacheCount(int count) {
            memoryCacheCount = count;
            return this;
        }

        public Builder memoryCacheTime(long time) {
            memoryCacheTime = time;
            return this;
        }

        public Builder async(boolean async) {
            doAsync = async;
            return this;
        }

        public Configuration build() {
            return new Configuration(cacheDir, diskCacheCount, diskCacheTime,
                    memoryCacheCount, memoryCacheTime);
        }
    }
}
