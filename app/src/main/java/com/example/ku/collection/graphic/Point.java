package com.example.ku.collection.graphic;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author Ronan.zhuang
 * @Date 11/25/16.
 * All copyright reserved.
 */

public class Point extends Graph {

    private float[] vertex = new float[]{
//            -0.8f , -0.4f * 1.732f , 0.0f ,
//            0.8f , -0.4f * 1.732f , 0.0f ,
//            0.0f , 0.4f * 1.732f , 0.0f ,
            -1.0f * 0.5f, 1.0f * 0.5f, 0.0f,
            -1.0f * 0.5f, -1.0f * 0.5f, 0.0f,
            1.0f * 0.5f, -1.0f * 0.5f, 0.0f,
    };

    private float[] colors = {
            1.0f,0.0f,0.0f,1.0f,
            0.0f,1.0f,0.0f,1.0f,
            0.0f,0.0f,1.0f,1.0f,
    };

    public Point() {

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertex.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        mVertexBuffer = byteBuffer.asFloatBuffer();
        mVertexBuffer.put(vertex);
        mVertexBuffer.position(0);

        ByteBuffer cByteBuffer = ByteBuffer.allocateDirect(colors.length * 4);
        cByteBuffer.order(ByteOrder.nativeOrder());
        mColorBuffer = cByteBuffer.asFloatBuffer();
        mColorBuffer.put(colors);
        mColorBuffer.position(0);
    }

    @Override
    public void draw(GL10 gl) {
//        gl.glColor4f(1.0f,0.0f,0.0f,1.0f);
//        gl.glPointSize(8.0f);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glVertexPointer(3, GL10.GL_VERTEX_ARRAY,0,mVertexBuffer);
        gl.glColorPointer(4, GL10.GL_FIXED,0,mColorBuffer);
        gl.glDrawArrays(GL10.GL_POINTS,0,3);

        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
