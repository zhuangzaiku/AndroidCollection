package com.example.ku.collection.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static com.example.ku.collection.net.MessageThread.PORT;

/**
 * @author Ronan.zhuang
 * @date 8/30/16
 */

public class SocketManager {

    private MessageInThread mMsgInThread;
    private MessageOutThread mMsgOutThread;

    private static SocketManager mSocketManager;

    private SocketManager() {
    }

    public static SocketManager getInstance(){
        if(mSocketManager == null){
            mSocketManager = new SocketManager();
        }
        return mSocketManager;
    }

    public enum CONNECT_STATE{
        CONNECTING,CONNECTED,CLOSED,NONE,RELEASE
    }

    public void initServer( SocketMessageListener mSocketMessageListener){
        mMsgInThread = new MessageInThread(mSocketMessageListener);
        mMsgInThread.start();
    }


    public void initClient(SocketMessageListener mSocketMessageListener){
        mMsgOutThread = new MessageOutThread(mSocketMessageListener);
        mMsgOutThread.start();
    }

    public void addTask(String msg){
        mMsgOutThread.addTask(msg);
    }

    public void release(){
        if(mMsgInThread != null)
            mMsgInThread.release();
        if(mMsgOutThread != null)
            mMsgOutThread.release();
    }
}
