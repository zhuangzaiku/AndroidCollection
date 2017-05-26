package zzk.com.utillibrary.download;

import java.util.List;

/**
 * Created by XYM on 2016/6/16.
 */
public interface IDownloader {

    DownloadTask download(String url, String filePath);

    boolean pause(String url);

    boolean resume(String url);

    void register();

    void unregister();

    void setOnDownloadChangedListener(OnDownloadChangedListener listener);

    void addDownloadTasks(List<DownloadTask> list);
}
