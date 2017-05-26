package com.example.ku.collection.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.example.ku.collection.R;
import com.example.ku.collection.utils.Constants;
import com.example.ku.collection.utils.DeviceUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Ronan.zhuang
 * @date 8/5/16
 */

public class IOActivity extends BaseActivity {

    private static final String TAG = IOActivity.class.getSimpleName();

    File mFile = new File(Constants.TEST_FILE);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_io);
        testOutputStream();
        testInputStream();
        testReader();
        testWriter();
        bufferWriter();
        bufferReader();
    }

    public void testOutputStream(){
        FileOutputStream fos = null;
        if(!DeviceUtils.isSDCardEnable())
            return;
        try {
            if (!mFile.exists())
                mFile.createNewFile();
            fos = new FileOutputStream(mFile);
            String content = "what a good day!";
            fos.write(content.getBytes());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }



    }

    public void testInputStream(){

        try {
            FileInputStream fis = new FileInputStream(mFile);
            byte []buffer = new byte[1024];
            StringBuffer sb = new StringBuffer();
            while(fis.read(buffer) != -1){
                sb.append(new String(buffer));
            };
            Log.d(TAG,"content >> " + sb.toString().trim());
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }


    }
//
    public void testReader(){
        try {
            FileInputStream fis = new FileInputStream(mFile);
            InputStreamReader reader = new InputStreamReader(fis);
            char[] buffer = new char[1024];
            StringBuffer sb = new StringBuffer();
            while(reader.read(buffer,0,buffer.length - 1) != -1){
                sb.append(String.valueOf(buffer));
            };
            Log.d(TAG,"reader content >> " + sb.toString().trim());
            reader.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//
//
    public void testWriter(){
        try {
            FileOutputStream fos = new FileOutputStream(mFile);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            String str = "yes,absolutely!";
            writer.write(str, 0,str.length());
            writer.flush();
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void bufferReader(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(mFile));
            StringBuffer sb = new StringBuffer();
            String str = null;
            while ((str = reader.readLine()) != null){
                sb.append(str);
            }
            Log.d(TAG,"bufferReader content >> " + sb.toString().trim());
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bufferWriter(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(mFile,true));
            writer.newLine();
            writer.write("how are you?");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public class ImageTask extends AsyncTask<ImageView,Void,Bitmap> {
        private ImageView imageView;

        @Override
        protected Bitmap doInBackground(ImageView... imageViews) {
            Bitmap bitmap;
            imageView = imageViews[0];
            try{
                URL url = new URL("http://rescdn.qqmail.com/dyimg/20140409/72B8663B7F23.jpg");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap != null && !bitmap.isRecycled() && imageView != null)
                imageView.setImageBitmap(bitmap);
            super.onPostExecute(bitmap);
        }
    }

}
