package zzk.com.utillibrary.download;

import android.content.Context;

/**
 * Created by XYM on 2016/6/16.
 */
public class DownloaderFactory {

    public static final int TYPE_SYSTEM_DOWNLOAD = 100;
    public static final int TYPE_FILE_DOWNLOAD_lingochamp = 101;
    public static final int TYPE_FILE_DOWNLOAD_wlfcolin = 102;


    public static IDownloader makeDownloader(Context context , int type){
        switch (type){
            case TYPE_SYSTEM_DOWNLOAD:
                return new SystemDownloader(context);

            case TYPE_FILE_DOWNLOAD_lingochamp:
                return new LCFileDownloader(context);

            case TYPE_FILE_DOWNLOAD_wlfcolin:
                return new WCFileDownloader(context);
            default:return null;
        }
    }

}
