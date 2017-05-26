package com.example.ku.collection.net;

import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.ku.collection.R;
import com.example.ku.collection.activity.BaseActivity;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.internal.Utils;

/**
 * @author Ronan.zhuang
 * @date 8/22/16
 */

public class TcpActivity extends BaseActivity {

    @BindView(R.id.msgEditText)
    EditText mMsgEditText;

    @BindView(R.id.msgContent)
    TextView mMsgContent;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.msgSend) void submit(){
        String text = mMsgEditText.getText().toString();
        if(StringUtils.isEmpty(text))
            return;
        SocketManager.getInstance().addTask(text);
    }

    @OnClick(R.id.tcpServer) void asServer() {
        SocketManager.getInstance().initServer(new SocketMessageListener() {
            @Override
            public void onMessage(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        String text = mMsgContent.getText().toString() + "\n";
                        mMsgContent.setText(text + msg);
                    }
                });

            }
        });
    }
    @OnClick(R.id.tcpClient) void asClient() {
        SocketManager.getInstance().initClient(new SocketMessageListener() {
            @Override
            public void onMessage(String msg) {
                String text = mMsgContent.getText().toString() + "\n";
                mMsgContent.setText(text + msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SocketManager.getInstance().release();
    }
}
