package zzk.com.utillibrary.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XYM on 2016/6/16.
 */
public class SystemDownloader implements IDownloader{


    private DownloadManager mDownloadManager;
    private Context mContext;
    private CompleteReceiver mCompleteReceiver;
    private IntentFilter mCompleteFilter;
    public static final Uri CONTENT_URI = Uri.parse("content://downloads/my_downloads");
    private List<Long> mDownloadList = new ArrayList<>();
    private DownloadChangeObserver mDownloadChangeObserver;
    private OnDownloadChangedListener mDownloadChangedListener;


    public SystemDownloader(Context context){
        mContext = context;
        mDownloadManager = (DownloadManager) mContext.getSystemService(mContext.DOWNLOAD_SERVICE);
        mCompleteReceiver = new CompleteReceiver();
        mDownloadChangeObserver = new DownloadChangeObserver(null);
        mCompleteFilter = new IntentFilter( );
        mCompleteFilter.addAction("android.intent.action.DOWNLOAD_COMPLETE");
    }

    @Override
    public void addDownloadTasks(List<DownloadTask> list){
        for(DownloadTask task : list){
            if(task.getDownloaderType() == DownloaderFactory.TYPE_SYSTEM_DOWNLOAD
                &&!mDownloadList.contains(task.getDownloadId())){
                mDownloadList.add(task.getDownloadId());
            }
        }
    }

    @Override
    public DownloadTask download(String url, String filePath) {
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        //设置允许使用的网络类型，这里是移动网络和wifi都可以
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //禁止发出通知，既后台下载，如果要使用这一句必须声明一个权限：
        // android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
        //request.setShowRunningNotification(false);
        //不显示下载界面
        request.setVisibleInDownloadsUi(false);
        File dstFile = new File(filePath);
        request.setDestinationInExternalFilesDir(mContext , dstFile.getParent() , dstFile.getName());
        long id = mDownloadManager.enqueue(request);
        mDownloadList.add(id);
        DownloadTask task = new DownloadTask(id, url , filePath , 0 ,0 , System.currentTimeMillis());
        task.setDownloaderType(DownloaderFactory.TYPE_SYSTEM_DOWNLOAD);
        return task;
    }

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
        mContext.unregisterReceiver(mCompleteReceiver);
        mContext.getContentResolver().unregisterContentObserver(mDownloadChangeObserver);
    }

    @Override
    public void register() {
        mContext.registerReceiver(mCompleteReceiver , mCompleteFilter);
        mContext.getContentResolver().registerContentObserver(CONTENT_URI, true, mDownloadChangeObserver);
    }


    class DownloadChangeObserver extends ContentObserver {
        public DownloadChangeObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            queryDownloadStatus();
        }
    }

    public class CompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            Log.v("tag", "download finish:" + id);
            if(mDownloadList.contains(id)){
                queryDownloadStatus();
            }
        }
    }

    private long[] transform(){
        long[] list = new long[mDownloadList.size()];
        for(int i = 0 ; i < mDownloadList.size() ; i++){
            list[i] = mDownloadList.get(i);
        }
        return list;
    }

    private void queryDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(transform());
        Cursor c = mDownloadManager.query(query);
        if(c == null){
            return;
        }
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            int reasonIdx = c.getColumnIndex(DownloadManager.COLUMN_REASON);
            int downloadIdx = c.getColumnIndex(DownloadManager.COLUMN_ID);
            int titleIdx = c.getColumnIndex(DownloadManager.COLUMN_TITLE);
            int fileSizeIdx = c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
            int bytesDLIdx = c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
            int timestampx = c.getColumnIndex(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP);
            int urlx = c.getColumnIndex(DownloadManager.COLUMN_URI);
            int filePathx = c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);

            String title = c.getString(titleIdx);
            int fileSize = c.getInt(fileSizeIdx);
            int bytesDL = c.getInt(bytesDLIdx);
            int reason = c.getInt(reasonIdx);
            long donwloadId = c.getLong(downloadIdx);
            long timestamp = c.getLong(timestampx);
            String url = c.getString(urlx);
            String filePath = c.getString(filePathx);
//            StringBuilder sb = new StringBuilder();
//            sb.append(title).append("\n");
//            sb.append("Downloaded ").append(bytesDL).append(" / " ).append(fileSize);
//
//            // Display the status
//            Log.d("tag", sb.toString());

//            Log.i("xym","" + task);
            DownloadTask task = new DownloadTask(donwloadId , url , filePath , fileSize , bytesDL , -1);

            Log.i("xym","" + task);
            switch(status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.v("tag", "STATUS_PAUSED");
                case DownloadManager.STATUS_PENDING:
                    Log.v("tag", "STATUS_PENDING");
                case DownloadManager.STATUS_RUNNING:
                    //正在下载，不做任何事情
                    Log.v("tag", "STATUS_RUNNING");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    //完成
                    Log.v("tag", "下载完成");
                    task.setFinish(true);
//                  dowanloadmanager.remove(lastDownloadId);
                    break;
                case DownloadManager.STATUS_FAILED:
                    //清除已下载的内容，重新下载
                    Log.v("tag", "STATUS_FAILED");
                    task.setFinish(true);
                    mDownloadManager.remove(donwloadId);
                    break;
            }


            if(mDownloadChangedListener != null){
                mDownloadChangedListener.update(task);
            }

        }

    }



}
