package com.example.ku.collection.graphic;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author Ronan.zhuang
 * @Date 11/18/16.
 * All copyright reserved.
 */

public class Square20 extends Graph{

    private float[] vertices = {
            -1.0f * 0.5f, 1.0f * 0.5f, 1.0f,
            -1.0f * 0.5f, -1.0f * 0.5f, 1.0f,
            1.0f * 0.5f, -1.0f * 0.5f, 1.0f,
            1.0f * 0.5f, 1.0f * 0.5f, 1.0f,
    };

    private float[] colors = {
            1.0f,0.0f,0.0f,1.0f,
            0.0f,1.0f,0.0f,1.0f,
            0.0f,0.0f,1.0f,1.0f,
            1.0f,1.0f,1.0f,1.0f,
    };

    private short[] indices = {
            0,1,2,0,2,3
    };



    public Square20() {
        ByteBuffer vByteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        vByteBuffer.order(ByteOrder.nativeOrder());
        mVertexBuffer = vByteBuffer.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);

        ByteBuffer iByteBuffer = ByteBuffer.allocateDirect(indices.length * 2);
        iByteBuffer.order(ByteOrder.nativeOrder());
        mIndexBuffer = iByteBuffer.asShortBuffer();
        mIndexBuffer.put(indices);
        mIndexBuffer.position(0);

        ByteBuffer cByteBuffer = ByteBuffer.allocateDirect(colors.length * 4);
        cByteBuffer.order(ByteOrder.nativeOrder());
        mColorBuffer = cByteBuffer.asFloatBuffer();
        mColorBuffer.put(colors);
        mColorBuffer.position(0);
    }

    @Override
    public void draw(GL10 gl){
        gl.glRotatef(45f,0,0,0);
        GLES20.glFrontFace(GL10.GL_CCW);
        GLES20.glEnable(GL10.GL_CULL_FACE);
        GLES20.glCullFace(GL10.GL_BACK);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT,0,mVertexBuffer);
        gl.glColorPointer(4,GL10.GL_FIXED,0,mColorBuffer);
//        gl.glDrawArrays(GL10.GL_TRIANGLES,0,3);
        GLES20.glDrawElements(GL10.GL_TRIANGLES,indices.length,GL10.GL_UNSIGNED_SHORT,mIndexBuffer);
//        gl.glDrawElements(GL10.GL_COLOR_ARRAY,colors.length,GL10.GL_FLOAT,mColorBuffer);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        GLES20.glDisable(GL10.GL_CULL_FACE);
    }
}
