package com.example.ku.collection.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.media.session.MediaButtonReceiver;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import com.example.ku.collection.R;

/**
 * @author Ronan.zhuang
 * @Date 12/19/16.
 * All copyright reserved.
 */

public class EarphoneActivity extends Activity{
    
    private static final String TAG = EarphoneActivity.class.getSimpleName();

    private AudioManager mAudioManager;
    private ComponentName mComponentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        // AudioManager注册一个MediaButton对象
        mComponentName = new ComponentName(getPackageName(), MediaButtonReceiver.class.getName());
    }



    @Override
    protected void onResume() {
        mAudioManager.registerMediaButtonEventReceiver(mComponentName);
        registerReceiver(headSetReceiver, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
        super.onResume();
    }

    @Override
    protected void onPause() {
        // 取消注册
        mAudioManager.unregisterMediaButtonEventReceiver(mComponentName);
        unregisterReceiver(headSetReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mAudioManager = null;
        mComponentName = null;
        super.onDestroy();
    }

    private final BroadcastReceiver headSetReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_HEADSET_PLUG)) {
                // phone headset plugged
                if (intent.getIntExtra("state", 0) == 1) {
                    // do something
                  Log.d(TAG, "耳机检测：插入");
//                  Toast.makeText(context, "耳机检测：插入", Toast.LENGTH_SHORT) .show();
                    mAudioManager.registerMediaButtonEventReceiver(mComponentName);
                    // phone head unplugged
                } else {
                    // do something
                  Log.d(TAG, "耳机检测：没有插入");
//                  Toast.makeText(context, "耳机检测：没有插入", Toast.LENGTH_SHORT).show();
                    mAudioManager.unregisterMediaButtonEventReceiver(mComponentName);
                }
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG,"keyCode->" + keyCode);
        if (KeyEvent.KEYCODE_HEADSETHOOK == keyCode) { //按下了耳机键
            if (event.getRepeatCount() == 0) {  //如果长按的话，getRepeatCount值会一直变大
                //短按
                Log.i(TAG,"短按->");
            } else {
                //长按
                Log.i(TAG,"长按->" + event.getRepeatCount());
            }
        }
        return super.onKeyDown(keyCode,event);
    }
}
