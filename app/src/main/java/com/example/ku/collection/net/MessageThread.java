package com.example.ku.collection.net;


import android.os.Handler;
import android.os.Looper;

/**
 * @author Ronan.zhuang
 * @date 8/30/16
 */

public abstract class MessageThread extends Thread {

    public static final int PORT = 5555;

    public static final String SERVER_IP = "10.0.6.114";


    protected MessageThread() {
    }

    protected void release(){

    }

    protected void addTask(String msg){
        doTask(msg);
    }

    protected  abstract void doTask(String msg);
}
