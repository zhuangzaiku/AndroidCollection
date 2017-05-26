package com.example.ku.collection.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ku.collection.R;
import com.example.ku.collection.utils.EmulatorDetector;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author Ronan.zhuang
 * @date 8/30/16
 */

public class EmulatorDetectorActivity extends BaseActivity {
    private static final String TAG = EmulatorDetectorActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emulator_detector);

        Toast.makeText(this, EmulatorDetector.isEmulator() ? "模拟器!!!" : "真机", Toast.LENGTH_SHORT).show();
        EmulatorDetector.logcat();
        TextView tv = (TextView)findViewById(R.id.tvv);
        tv.setText(EmulatorDetector.getDeviceListing());
    }

    protected void reflectDetect(){
        //detect emulater
        String[] props2 = {
                "ro.build.host",
                "ro.build.fingerprint",
                "ro.product.model",
                "ro.product.cpu.abi",
                "ro.product.cpu.abi2",
                "ro.product.manufacturer",
                "ro.sf.lcd_density",
        };
        String sResult = "";

        long before = new Date().getTime();
        Log.i(TAG,"time begin >>" + before);
        for(String prop : props2) {
            try {
                @SuppressWarnings("rawtype")
                Class sp = Class.forName("android.os.SystemProperties");

                @SuppressWarnings("rawtypes")
                Class[] paramTypes = new Class[1];
                paramTypes[0] = String.class;
                Method get = sp.getMethod("get", paramTypes);
                Object[] params = new Object[1];
                params[0] = prop;
                Object o = get.invoke(sp, prop);
                String ret = (String) get.invoke(sp, prop);

                if (ret.equals("buildbot.soft.genymobile.com")) {
                    android.widget.Toast.makeText(getApplicationContext(), "Emulator Detected", android.widget.Toast.LENGTH_LONG).show();
                }

                String s = String.format("%s: %s", prop, ret);
                //android.widget.Toast.makeText(getApplicationContext(),
                //    s, android.widget.Toast.LENGTH_LONG).show();

                android.util.Log.d("[zlf] Reflect Detect", s);
                sResult += s;
                sResult += "\n";

            }catch(Exception e){
                e.printStackTrace();
            }
        }

        long end = new Date().getTime();
        Log.i(TAG,"time end >>" + end);

        Toast.makeText(this,"before >>" + before +"\n end >>" + end,Toast.LENGTH_LONG).show();


    }




    String[] props = {

            "ro.build.host", "ro.product.name",
            "ro.build.id",
            "ro.build.display.id",
            "ro.build.version.incremental",
            "ro.build.version.sdk",
            "ro.build.version.codename",
            "ro.build.version.release",
            "ro.build.date",
            "ro.build.date.utc",
            "ro.build.type",
            "ro.build.user",
            "ro.build.host",
            "ro.build.tags",
            "ro.product.model",
            "ro.product.brand",
            "ro.product.name",
            "ro.product.device",
            "ro.product.board",
            "ro.product.cpu.abi",
            "ro.product.cpu.abi2",
            "ro.product_ship",
            "ro.product.manufacturer",
            "ro.product.locale.language",
            "ro.product.locale.region",
            "ro.wifi.channels",
            "ro.board.platform",
            "ro.build.product",
            "ro.build.description",
            "ro.build.fingerprint",
            "ro.build.characteristics",
            "ro.build.PDA",
            "ro.build.hidden_ver",
            "ro.build.changelist",
            "ro.sf.lcd_density",
            "ro.kernel.qemu",
            "ro.tvout.enable",
            "ro.carrier",
            "ro.lcd_brightness",
            "ro.opengles.version",
            "ro.sf.lcd_density",
            "ro.error.receiver.default",
            "ro.hdcp2.rx",
            "ro.secwvk",
            "ro.sec.fle.encryption",
            "ro.config.ringtone",
            "ro.config.notification_sound",
            "ro.config.alarm_alert",
            "ro.config.media_sound",
            "ro.com.android.dateformat",
            "ro.carrier",
            "ro.com.google.clientidbase",
            "ro.ril.hsxpa",
            "ro.ril.gprsclass",
            "ro.adb.qemud",
            "ro.com.google.gmsversion"
    };
}
