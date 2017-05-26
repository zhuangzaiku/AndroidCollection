package com.example.ku.collection.net;

import android.os.Handler;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author Ronan.zhuang
 * @date 8/30/16
 */

public class MessageOutThread extends MessageThread {

    private Socket mSocket;

    private String mMessage = "";

    private SocketMessageListener mSocketMessageListener;

    public MessageOutThread(SocketMessageListener mSocketMessageListener) {
        this.mSocketMessageListener = mSocketMessageListener;
    }

    @Override
    public void run() {
        try {
            if (mSocket == null)
                mSocket = new Socket(SERVER_IP, PORT);
            while (true)
                if (!StringUtils.isBlank(mMessage)) {
                    DataOutputStream dos = new DataOutputStream(mSocket.getOutputStream());
                    dos.writeUTF(mMessage);
                    Log.i("zzk", "发送消息" + mMessage);
                    mMessage = "";
                }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected synchronized void doTask(String msg) {
//        if(mSocket == null || StringUtils.isEmpty(msg))
//            return;
        mMessage = msg;
    }

    @Override
    protected void release() {
        super.release();
        try {
            if (mSocket != null && !mSocket.isClosed()) {
                mSocket.close();
                mSocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
