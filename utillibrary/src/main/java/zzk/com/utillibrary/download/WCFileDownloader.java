package zzk.com.utillibrary.download;

import android.content.Context;

import org.wlf.filedownloader.DownloadFileInfo;
import org.wlf.filedownloader.FileDownloader;
import org.wlf.filedownloader.listener.OnDownloadFileChangeListener;
import org.wlf.filedownloader.listener.OnFileDownloadStatusListener;
import org.wlf.filedownloader.listener.simple.OnSimpleFileDownloadStatusListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XYM on 2016/6/16.
 */
public class WCFileDownloader implements IDownloader{


    private Context mContext;
    private List<Long> mDownloadList = new ArrayList<>();
    private OnDownloadChangedListener mDownloadChangedListener;



    public WCFileDownloader(Context context){
        mContext = context;
//        FileDownloadConfiguration.Builder builder = new FileDownloadConfiguration.Builder(context);
//
//        // 2.配置Builder
//        // 配置下载文件保存的文件夹
//        builder.configFileDownloadDir(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
//                "FileDownloader");
//        // 配置同时下载任务数量，如果不配置默认为2
//        builder.configDownloadTaskSize(3);
//        // 配置失败时尝试重试的次数，如果不配置默认为0不尝试
//        builder.configRetryDownloadTimes(5);
//        // 开启调试模式，方便查看日志等调试相关，如果不配置默认不开启
//        builder.configDebugMode(true);
//         // 配置连接网络超时时间，如果不配置默认为15秒
//        builder.configConnectTimeout(25000);// 25秒
//
//        // 3、使用配置文件初始化FileDownloader
//        FileDownloadConfiguration configuration = builder.build();
//        FileDownloader.init(configuration);
    }

    @Override
    public void addDownloadTasks(List<DownloadTask> list){
        for(DownloadTask task : list){
            if(task.getDownloaderType() == DownloaderFactory.TYPE_FILE_DOWNLOAD_wlfcolin
                    &&!mDownloadList.contains(task.getDownloadId())){
                mDownloadList.add(task.getDownloadId());
            }
        }
    }

    @Override
    public DownloadTask download(String url, final String filePath) {
        int id = url.hashCode();
//        FileDownloader.detect(url, new OnDetectBigUrlFileListener() {
//            @Override
//            public void onDetectNewDownloadFile(String url, String fileName, String saveDir, long fileSize) {
//                File dstFile = new File(filePath);
//                FileDownloader.createAndStart(url, dstFile.getParent(), dstFile.getName());
//            }
//            @Override
//            public void onDetectUrlFileExist(String url) {
//                FileDownloader.start(url);
//            }
//            @Override
//            public void onDetectUrlFileFailed(String url, DetectBigUrlFileFailReason failReason) {
//            }
//        });
        FileDownloader.start(url);
        mDownloadList.add((long)id);
        DownloadTask task = new DownloadTask(id, url , filePath , 0 ,0,0);
        task.setDownloaderType(DownloaderFactory.TYPE_FILE_DOWNLOAD_wlfcolin);
        return task;

    }


    private OnFileDownloadStatusListener mOnFileDownloadStatusListener = new OnSimpleFileDownloadStatusListener() {
        @Override
        public void onFileDownloadStatusRetrying(DownloadFileInfo downloadFileInfo, int retryTimes) {
            // 正在重试下载（如果你配置了重试次数，当一旦下载失败时会尝试重试下载），retryTimes是当前第几次重试
        }
        @Override
        public void onFileDownloadStatusWaiting(DownloadFileInfo downloadFileInfo) {
            // 等待下载（等待其它任务执行完成，或者FileDownloader在忙别的操作）
        }
        @Override
        public void onFileDownloadStatusPreparing(DownloadFileInfo downloadFileInfo) {
            // 准备中（即，正在连接资源）
            DownloadTask downloadTask = new DownloadTask(downloadFileInfo.getUrl().hashCode() , downloadFileInfo.getUrl() , downloadFileInfo.getFilePath() ,
                    downloadFileInfo.getFileSizeLong() , downloadFileInfo.getDownloadedSizeLong() , System.currentTimeMillis());
            if(mDownloadChangedListener != null){
                mDownloadChangedListener.update(downloadTask);
            }
        }
        @Override
        public void onFileDownloadStatusPrepared(DownloadFileInfo downloadFileInfo) {
            // 已准备好（即，已经连接到了资源）
        }
        @Override
        public void onFileDownloadStatusDownloading(DownloadFileInfo downloadFileInfo, float downloadSpeed, long
                remainingTime) {
            // 正在下载，downloadSpeed为当前下载速度，单位KB/s，remainingTime为预估的剩余时间，单位秒
        }
        @Override
        public void onFileDownloadStatusPaused(DownloadFileInfo downloadFileInfo) {
            // 下载已被暂停
        }
        @Override
        public void onFileDownloadStatusCompleted(DownloadFileInfo downloadFileInfo) {
            // 下载完成（整个文件已经全部下载完成）
            DownloadTask downloadTask = new DownloadTask(downloadFileInfo.getUrl().hashCode() , downloadFileInfo.getUrl() , downloadFileInfo.getFilePath() ,
                    downloadFileInfo.getFileSizeLong() , downloadFileInfo.getDownloadedSizeLong() ,-1);
            downloadTask.setFinish(true);
            if(mDownloadChangedListener != null){
                mDownloadChangedListener.update(downloadTask);
            }
            File file = new File(downloadFileInfo.getFilePath());
            if(file != null && file.exists()){
                file.delete();
            }
        }
        @Override
        public void onFileDownloadStatusFailed(String url, DownloadFileInfo downloadFileInfo, FileDownloadStatusFailReason failReason) {
            // 下载失败了，详细查看失败原因failReason，有些失败原因你可能必须关心
            DownloadTask downloadTask = new DownloadTask(downloadFileInfo.getUrl().hashCode() , downloadFileInfo.getUrl() , downloadFileInfo.getFilePath() ,
                    downloadFileInfo.getFileSizeLong() , downloadFileInfo.getDownloadedSizeLong() ,-1);
            downloadTask.setFinish(true);
            if(mDownloadChangedListener != null){
                mDownloadChangedListener.update(downloadTask);
            }
            String failType = failReason.getType();
            String failUrl = failReason.getUrl();// 或：failUrl = url，url和failReason.getUrl()会是一样的

            if(FileDownloadStatusFailReason.TYPE_URL_ILLEGAL.equals(failType)){
                // 下载failUrl时出现url错误
            }else if(FileDownloadStatusFailReason.TYPE_STORAGE_SPACE_IS_FULL.equals(failType)){
                // 下载failUrl时出现本地存储空间不足
            }else if(FileDownloadStatusFailReason.TYPE_NETWORK_DENIED.equals(failType)){
                // 下载failUrl时出现无法访问网络
            }else if(FileDownloadStatusFailReason.TYPE_NETWORK_TIMEOUT.equals(failType)){
                // 下载failUrl时出现连接超时
            }else{
                // 更多错误....
            }

            // 查看详细异常信息
            Throwable failCause = failReason.getCause();// 或：failReason.getOriginalCause()

            // 查看异常描述信息
            String failMsg = failReason.getMessage();// 或：failReason.getOriginalCause().getMessage()
        }
    };

    private OnDownloadFileChangeListener mOnDownloadFileChangeListener = new OnDownloadFileChangeListener() {
        @Override
        public void onDownloadFileCreated(DownloadFileInfo downloadFileInfo) {
            // 一个新下载文件被创建，也许你需要同步你自己的数据存储，比如在你的业务数据库中增加一条记录

        }
        @Override
        public void onDownloadFileUpdated(DownloadFileInfo downloadFileInfo, Type type) {
            // 一个下载文件被更新，也许你需要同步你自己的数据存储，比如在你的业务数据库中更新一条记录
            DownloadTask downloadTask = new DownloadTask(downloadFileInfo.getUrl().hashCode() , downloadFileInfo.getUrl() , downloadFileInfo.getFilePath() ,
                    downloadFileInfo.getFileSizeLong() , downloadFileInfo.getDownloadedSizeLong() ,-1);
            if(mDownloadChangedListener != null){
                mDownloadChangedListener.update(downloadTask);
            }
        }
        @Override
        public void onDownloadFileDeleted(DownloadFileInfo downloadFileInfo) {
            // 一个下载文件被删除，也许你需要同步你自己的数据存储，比如在你的业务数据库中删除一条记录
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
        FileDownloader.unregisterDownloadStatusListener(mOnFileDownloadStatusListener);
        FileDownloader.unregisterDownloadFileChangeListener(mOnDownloadFileChangeListener);
    }

    @Override
    public void register() {
        FileDownloader.registerDownloadStatusListener(mOnFileDownloadStatusListener);
        FileDownloader.registerDownloadFileChangeListener(mOnDownloadFileChangeListener);
    }


}
