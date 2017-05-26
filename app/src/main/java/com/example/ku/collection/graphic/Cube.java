package com.example.ku.collection.graphic;

import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author Ronan.zhuang
 * @Date 11/18/16.
 * All copyright reserved.
 */

public class Cube extends Graph{

    private float width = 0.5f;
    private float height = 0.5f;
    private float depth = 0.5f;
    private float[] vertices = {
            -width,-height,-depth,
            width,-height,-depth,
            width, height,-depth,
            -width,height,-depth,
            -width,-height, depth,
            width,-height, depth,
            width, height, depth,
            -width, height, depth,
    };

    private float[] colors = {
            1.0f,0.0f,0.0f,1.0f,
            0.0f,1.0f,0.0f,1.0f,
            0.0f,0.0f,1.0f,1.0f,
    };

    private short[] indices = {
            0, 4, 5,
            0, 5, 1,
            1, 5, 6,
            1, 6, 2,
            2, 6, 7,
            2, 7, 3,
            3, 7, 4,
            3, 4, 0,
            4, 7, 6,
            4, 6, 5,
            3, 0, 1,
            3, 1, 2,

    };

    public Cube(float width, float height, float depth) {
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
        this.width = width / 2;
        this.height = height / 2;
        this.depth = depth / 2;
    }

    @Override
    public void draw(GL10 gl){
        gl.glRotatef(30f, 1, 0, 0);
        gl.glRotatef(30f, 0, 1, 0);
//        gl.glRotatef(45f, 0, 0, 1);
        gl.glFrontFace(GL10.GL_CCW);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT,0,mVertexBuffer);
        gl.glColorPointer(4,GL10.GL_FIXED,0,mColorBuffer);
//        gl.glDrawArrays(GL10.GL_TRIANGLES,0,3);
        gl.glDrawElements(GL10.GL_TRIANGLES,indices.length,GL10.GL_UNSIGNED_SHORT,mIndexBuffer);
//        gl.glDrawElements(GL10.GL_COLOR_ARRAY,colors.length,GL10.GL_FLOAT,mColorBuffer);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);
    }
}
