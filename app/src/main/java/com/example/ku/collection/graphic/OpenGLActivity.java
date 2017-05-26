package com.example.ku.collection.graphic;

import android.net.Uri;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;
import android.util.Log;

import com.example.ku.collection.R;
import com.example.ku.collection.activity.BaseActivity;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author Ronan.zhuang
 * @date 8/29/16
 */

public class OpenGLActivity extends BaseActivity {

    private GLSurfaceView mGlSurfaceView;
    private static final String TAG = OpenGLActivity.class.getSimpleName();
    public static final int EGL_VERSION = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengl);
        Log.d(TAG,"1->" + System.currentTimeMillis());
        mGlSurfaceView = (GLSurfaceView) findViewById(R.id.surfaceView);
        mGlSurfaceView.setEGLContextClientVersion(EGL_VERSION);
        Log.d(TAG,"2->" + System.currentTimeMillis());
//        mGlSurfaceView.setEGLContextClientVersion(2);
//        mGlSurfaceView.setRenderer(new MyRender(new Square()));
//        mGlSurfaceView.setRenderer(new MyRender(new Cube(1.0f,1.0f,1.0f)));
//        mGlSurfaceView.setRenderer(new MyRender(new Texture(this)));
        mGlSurfaceView.setRenderer(new Example6_3Renderer(this));



        GLSurfaceView glSurfaceView = (GLSurfaceView) findViewById(R.id.surfaceView2);
        glSurfaceView.setEGLContextClientVersion(EGL_VERSION);
        glSurfaceView.setRenderer(new Example6_3Renderer(this));
        Log.d(TAG,"6->" + System.currentTimeMillis());
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    public class MyRender implements GLSurfaceView.Renderer {
        private Graph mGraph;

        public MyRender(Graph mGraph) {
            this.mGraph = mGraph;

            Log.d(TAG,"4->" + System.currentTimeMillis());
        }


        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {

            Log.d(TAG,"3->" + System.currentTimeMillis());
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            gl.glViewport(0, 0, width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
//            gl.glClearColor(1.0f, 1.0f, 0.0f, 1.0f);
//            Log.d(TAG,"5->" + System.currentTimeMillis());
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
            gl.glTranslatef(0, 0, -4);
            gl.glLoadIdentity();
            mGraph.draw(gl);
        }
    }
}
