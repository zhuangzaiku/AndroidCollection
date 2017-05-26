package com.example.ku.collection.utils;


import android.os.Build;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ku.collection.R;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author Ronan.zhuang
 * @date 8/16/16
 */

public class EmulatorDetector {


    private static final String TAG = "EmulatorDetector";

    private static int rating = -1;

    /**
     * Detects if app is currenly running on emulator, or real device.
     *
     * @return true for emulator, false for real devices
     */
    public static boolean isEmulator() {

        if (rating < 0) { // rating is not calculated yet
            int newRating = 0;

            if (Build.PRODUCT.equals("sdk") ||
                    Build.PRODUCT.equals("google_sdk") ||
                    Build.PRODUCT.equals("sdk_x86") ||
                    Build.PRODUCT.equals("sdk_x86_64") ||
                    Build.PRODUCT.equals("sdk_google_phone_x86") ||
                    Build.PRODUCT.equals("sdk_google_phone_x86_64") ||
                    Build.PRODUCT.equals("sdk_google_phone_arm64") ||
                    Build.PRODUCT.equals("sdk_google_phone_armv7") ||
                    Build.PRODUCT.equals("vbox86p")) {
                newRating++;
            }

            if (Build.MANUFACTURER.equals("unknown") ||
                    Build.MANUFACTURER.equals("Genymotion")) {
                newRating++;
            }

            if (Build.BRAND.equals("generic") ||
                    Build.BRAND.equalsIgnoreCase("android")||
                    Build.BRAND.equals("generic_arm64")||
                    Build.BRAND.equals("generic_x86")||
                    Build.BRAND.equals("generic_x86_64")) {
                newRating++;
            }

            if (Build.DEVICE.equals("generic") ||
                    Build.DEVICE.equals("generic_arm64") ||
                    Build.DEVICE.equals("generic_x86") ||
                    Build.DEVICE.equals("generic_x86_64") ||
                    Build.DEVICE.equals("vbox86p")) {
                newRating++;
            }

            if (Build.MODEL.equals("sdk") ||
                    Build.MODEL.equals("google_sdk") ||
                    Build.MODEL.equals("google_sdk_64") ||
                    Build.MODEL.equals("Android SDK built for arm64")||
                    Build.MODEL.equals("Android SDK built for armv7")||
                    Build.MODEL.equals("Android SDK built for x86")||
                    Build.MODEL.equals("Android SDK built for x86_64")) {
                newRating++;
            }

            if (Build.HARDWARE.equals("goldfish") ||
                    Build.HARDWARE.equals("vbox86") ||
                    Build.HARDWARE.equals("ranchu")) {
                newRating++;
            }

            if (Build.FINGERPRINT.contains("generic/sdk/generic") ||
                    Build.FINGERPRINT.contains("generic/sdk/generic_64") ||
                    Build.FINGERPRINT.contains("generic_arm64") ||
                    Build.FINGERPRINT.contains("sdk_google_phone_arm64") ||
                    Build.FINGERPRINT.contains("sdk_google_phone_armv7") ||
                    Build.FINGERPRINT.contains("generic_x86/sdk_x86/generic_x86") ||
                    Build.FINGERPRINT.contains("generic_x86_64/sdk_x86_64/generic_x86_64") ||
                    Build.FINGERPRINT.contains("generic/google_sdk/generic") ||
                    Build.FINGERPRINT.contains("generic/google_sdk/generic_64") ||
                    Build.FINGERPRINT.contains("generic/vbox86p/vbox86p") ||
                    Build.FINGERPRINT.contains("generic/vbox86p/vbox86p_64")) {
                newRating++;
            }

            /** 市面上特定模拟器需要针对性的区分*/
            if(Build.HOST.contains("Droid4x-BuildStation")){
                newRating = 5;
            }


        }

        return rating > 4;
    }

    /**
     *  函数 isEmulator 中{@link Build} 的静态变量跟本函数读取的属性一致
     *  ro.build.fingerprint:brand/name/device(product):build.version.release/build.id/build.version.incremental/build.type/build.tags
     *  去除与 ro.build.fingerprint 中包含字段重复的属性,最终读取 props2 中罗列出的7个属性
     *  记录函数执行起止时间,计算耗时
     */
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

        long begin = new Date().getTime();
        Log.i(TAG,"time begin >>" + begin);
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
//                    android.widget.Toast.makeText(getApplicationContext(), "Emulator Detected", android.widget.Toast.LENGTH_LONG).show();
                }

                String s = String.format("%s: %s", prop, ret);

                android.util.Log.d("[zlf] Reflect Detect", s);
                sResult += s;
                sResult += "\n";

            }catch(Exception e){
                e.printStackTrace();
            }
        }

        long end = new Date().getTime();
        Log.i(TAG,"time end >> " + end);
        Log.i(TAG,"time consume >> " + (end - begin));

    }

    /**
     *
     * @return all involved Build.* parameters and its values
     */
    public static String getDeviceListing() {
        return "Build.PRODUCT: " + Build.PRODUCT + "\n" +
                "Build.MANUFACTURER: " + Build.MANUFACTURER + "\n" +
                "Build.BRAND: " + Build.BRAND + "\n" +
                "Build.DEVICE: " + Build.DEVICE + "\n" +
                "Build.MODEL: " + Build.MODEL + "\n" +
                "Build.HARDWARE: " + Build.HARDWARE + "\n" +
                "Build.FINGERPRINT: " + Build.FINGERPRINT + "\n" +
                "Build.CPU_ABI: " + Build.CPU_ABI + "\n" +
                "Build.CPU_ABI2: " + Build.CPU_ABI2 + "\n" +
                "Build.HOST: " + Build.HOST;
    }

    /**
     * Prints all Build.* parameters used in {@link #isEmulator()} method to logcat.
     */
    public static void logcat() {
        Log.d(TAG, getDeviceListing());
    }
}
