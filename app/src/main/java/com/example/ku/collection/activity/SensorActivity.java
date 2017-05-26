package com.example.ku.collection.activity;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.TextView;

import com.example.ku.collection.R;
import com.example.ku.collection.utils.KLog;

/**
 * @author Ronan.zhuang
 * @Date 12/19/16.
 * All copyright reserved.
 */

public class SensorActivity extends Activity implements SensorEventListener{
    
    private static final String TAG = SensorActivity.class.getSimpleName();

    private SensorManager mSensorManager;
    private Sensor mSensorAccelerometer;
    private int counter = 0;
    public static final int INTERVAL = 10;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        mTv = (TextView) findViewById(R.id.textView);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this,mSensorAccelerometer,SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        counter ++;
        if(counter % INTERVAL != 0) return;
        mTv.setText("x->" + event.values[0] + "   \ny->" + event.values[1] + "  \nz->" + event.values[2]);
        Log.d(TAG,"x->" + event.values[0] + "   y->" + event.values[1] + "  z->" + event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG,"accuracy->" + accuracy );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
    }
}
