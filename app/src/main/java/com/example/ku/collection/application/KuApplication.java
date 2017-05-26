package com.example.ku.collection.application;

import android.app.Application;

import com.example.ku.collection.utils.Constants;

import java.io.File;

/**
 * @author Ronan.zhuang
 * @date 8/16/16
 */

public class KuApplication extends Application {

    private static KuApplication s_instance;

    @Override
    public void onCreate() {
        super.onCreate();
        s_instance = this;
        init();
    }

    public static KuApplication getInstance() {
        return s_instance;
    }

    public static void init(){
        File file = new File(Constants.TEST_PATH);
        if(!file.exists())
            file.mkdir();
    }
}
