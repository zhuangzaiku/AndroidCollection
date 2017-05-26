package com.example.ku.collection.net;

import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Ronan.zhuang
 * @date 8/30/16
 */

public class MessageInThread extends MessageThread {

    private ServerSocket mServerSocket;
    private SocketMessageListener mSocketMessageListener;
    private String mMessage;

    public MessageInThread(SocketMessageListener mSocketMessageListener) {
        this.mSocketMessageListener = mSocketMessageListener;

    }

    @Override
    public void run() {
        try {
            if(mServerSocket == null)
                mServerSocket = new ServerSocket(PORT);
                Socket socket = mServerSocket.accept();
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                String msg ;
                while ((msg = dis.readUTF()) != null){
                    Log.i("zzk", "收到消息:" + msg);
                    mSocketMessageListener.onMessage(msg);
                    dos.writeUTF("我收到了:" + msg);
                }

//                byte[] buffer = new byte[1024];
//                int temp = 0;
//                StringBuffer sb = new StringBuffer();
//                while ((temp = inputStream.read(buffer)) != -1) {
//                    sb.append(new String(buffer, 0, temp));
//                }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doTask(String msg) {
        mMessage = msg;
    }

    @Override
    protected void release() {
        super.release();
        if(mServerSocket != null && !mServerSocket.isClosed()) {
            try {
                mServerSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mServerSocket = null;
        }
    }
}
