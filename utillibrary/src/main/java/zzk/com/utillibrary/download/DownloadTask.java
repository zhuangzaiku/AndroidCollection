package zzk.com.utillibrary.download;

import java.io.Serializable;

/**
 * Created by XYM on 2016/6/16.
 */
public class DownloadTask implements Serializable {
    long downloadId;
    boolean isFinish;
    String url;
    String filePath;
    long totalSize;
    long currentSize;
    long startTime;
    long elapsedTime;
    int downloaderType;

    public void clone(DownloadTask task){
        setUrl(task.getUrl());
        setFilePath(task.getFilePath());
        setTotalSize(task.getTotalSize());
        setCurrentSize(task.getCurrentSize());
        if(task.getStartTime() != -1){
            setStartTime(task.getStartTime());
        }
        if(!task.isFinish()){
            elapsedTime = System.currentTimeMillis() - startTime;
        }
    }

    public DownloadTask(long id , String url, String filePath, long totalSize, long currentSize, long startTime) {
        downloadId = id;
        this.url = url;
        this.filePath = filePath;
        this.totalSize = totalSize;
        this.currentSize = currentSize;
        this.startTime = startTime;
    }

    public int getDownloaderType() {
        return downloaderType;
    }

    public void setDownloaderType(int downloaderType) {
        this.downloaderType = downloaderType;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public long getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(long downloadId) {
        this.downloadId = downloadId;
    }

    public String getUrl() {
        return url;
    }

    public String getFilePath() {
        return filePath;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public long getCurrentSize() {
        return currentSize;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public void setCurrentSize(long currentSize) {
        this.currentSize = currentSize;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "DownloadTask{" +
                "downloadId=" + downloadId +
                ", url='" + url + '\'' +
                ", filePath='" + filePath + '\'' +
                ", totalSize=" + totalSize +
                ", currentSize=" + currentSize +
                ", startTime=" + startTime +
                '}';
    }
}
