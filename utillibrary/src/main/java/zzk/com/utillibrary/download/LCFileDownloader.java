package zzk.com.utillibrary.download;

import android.content.Context;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XYM on 2016/6/16.
 */
public class LCFileDownloader implements IDownloader{


    private Context mContext;
    private List<Long> mDownloadList = new ArrayList<>();
    private OnDownloadChangedListener mDownloadChangedListener;



    public LCFileDownloader(Context context){
        mContext = context;
    }

    @Override
    public void addDownloadTasks(List<DownloadTask> list){
        for(DownloadTask task : list){
            if(task.getDownloaderType() == DownloaderFactory.TYPE_FILE_DOWNLOAD_lingochamp
                    &&!mDownloadList.contains(task.getDownloadId())){
                mDownloadList.add(task.getDownloadId());
            }
        }
    }

    @Override
    public DownloadTask download(String url, String filePath) {
        int id = FileDownloader.getImpl().create(url)
                .setPath(filePath)
                .setListener(mFileDownloadListener).start();
        mDownloadList.add((long)id);
        DownloadTask task = new DownloadTask(id, url , filePath , 0 ,0,0);
        task.setDownloaderType(DownloaderFactory.TYPE_FILE_DOWNLOAD_lingochamp);
        return task;

    }

    private FileDownloadListener mFileDownloadListener = new FileDownloadListener() {
        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        }

        @Override
        protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
            DownloadTask downloadTask = new DownloadTask(task.getDownloadId() , task.getUrl() , task.getPath() ,
                    task.getLargeFileTotalBytes() , task.getLargeFileSoFarBytes() , System.currentTimeMillis());
            if(mDownloadChangedListener != null){
                mDownloadChangedListener.update(downloadTask);
            }
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            DownloadTask downloadTask = new DownloadTask(task.getDownloadId() , task.getUrl() , task.getPath() ,
                    task.getLargeFileTotalBytes() , task.getLargeFileSoFarBytes() ,-1);
            if(mDownloadChangedListener != null){
                mDownloadChangedListener.update(downloadTask);
            }
        }

        @Override
        protected void blockComplete(BaseDownloadTask task) {
        }

        @Override
        protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            DownloadTask downloadTask = new DownloadTask(task.getDownloadId() , task.getUrl() , task.getPath() ,
                    task.getLargeFileTotalBytes() , task.getLargeFileSoFarBytes() , -1);
            downloadTask.setFinish(true);
            if(mDownloadChangedListener != null){
                mDownloadChangedListener.update(downloadTask);
            }
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            DownloadTask downloadTask = new DownloadTask(task.getDownloadId() , task.getUrl() , task.getPath() ,
                    task.getLargeFileTotalBytes() , task.getLargeFileSoFarBytes() ,-1);
            downloadTask.setFinish(true);
            if(mDownloadChangedListener != null){
                mDownloadChangedListener.update(downloadTask);
            }
        }

        @Override
        protected void warn(BaseDownloadTask task) {
        }
    };

    @Override
    public boolean pause(String url) {
        return false;
    }

    @Override
    public boolean resume(String url) {
        return false;
    }

    @Override
    public void setOnDownloadChangedListener(OnDownloadChangedListener listener) {
        mDownloadChangedListener = listener;
    }

    @Override
    public void unregister() {
    }

    @Override
    public void register() {
    }









}
