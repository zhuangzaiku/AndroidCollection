package com.example.ku.collection.utils;

import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Ronan.zhuang
 * @date 8/30/16
 */

public class KTimer {
    private static Timer s_timer = new Timer();

    private static Handler s_handler;

    private static void init(){
        if(s_handler == null)
            s_handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    TimeoutListener listener = (TimeoutListener) msg.obj;
                    if(listener != null)
                        listener.onTimeOut();
                }
            };
    }

    /**
     * @param delay
     * @param interval
     * @param timeoutListener
     */
    public static void start(long delay,long interval,final TimeoutListener timeoutListener){
        init();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Message msg = s_handler.obtainMessage(0,timeoutListener);
                s_handler.sendMessage(msg);
            }
        };
        s_timer.schedule(task, delay, interval);

    }

    /**
     * @param delay
     * @param timeoutListener
     */
    public static void start(long delay,final TimeoutListener timeoutListener){
        init();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Message msg = s_handler.obtainMessage(0,timeoutListener);
                s_handler.sendMessage(msg);
            }
        };
        s_timer.schedule(task, delay);

    }

    public interface TimeoutListener{
        void onTimeOut();
    }
}
