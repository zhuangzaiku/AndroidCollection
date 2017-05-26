package com.example.ku.collection.graphic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import com.example.ku.collection.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author Ronan.zhuang
 * @Date 11/29/16.
 * All copyright reserved.
 */

public class Texture extends Graph{

    private int mTextureId = -1;
    private Bitmap mBitmap = null;
    private Context mContext;
    private boolean shouldLoadTexture = true;
    private FloatBuffer mTextureBuffer;

    private float[] coordinates = new float[]{
            -0.5f,0.5f,
            0.5f,0.5f,
            0.5f,-0.5f,
            -0.5f,-0.5f
    };

    private float[] texture = new float[]{
            0.0f,0.0f
    };

    private short[] indices = {
            0,1,2,0,2,3
    };

    @Override
    public void draw(GL10 gl) {
        gl.glClearColor(1.0f,1.0f,1.0f,1.0f);
        if(shouldLoadTexture){
            loadTexture(gl);
            shouldLoadTexture = false;
        }
        if(mTextureId != -1 && mVertexBuffer != null){
            gl.glEnable(GL10.GL_TEXTURE_2D);
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl.glTexCoordPointer(2,GL10.GL_FLOAT,0,mVertexBuffer);
            gl.glBindTexture(GL10.GL_TEXTURE_2D,mTextureId);
        }
//        gl.glRotatef(180.0f,1,0,0);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(2,GL10.GL_FLOAT,0,mVertexBuffer);
        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
                GL10.GL_UNSIGNED_SHORT, mIndexBuffer);
//        gl.glDrawArrays(GL10.GL_TRIANGLES,0,4);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        if(mTextureId != -1 && mVertexBuffer != null){
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl.glDisable(GL10.GL_TEXTURE_2D);
        }

    }

    public Texture(Context mContext) {
        this.mContext = mContext;
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        mTextureBuffer = byteBuffer.asFloatBuffer();
        mTextureBuffer.put(texture);
        mTextureBuffer.position(0);

        ByteBuffer vByteBuffer = ByteBuffer.allocateDirect(coordinates.length * 4);
        vByteBuffer.order(ByteOrder.nativeOrder());
        mVertexBuffer = vByteBuffer.asFloatBuffer();
        mVertexBuffer.put(coordinates);
        mVertexBuffer.position(0);

        ByteBuffer iByteBuffer = ByteBuffer.allocateDirect(indices.length * 2);
        iByteBuffer.order(ByteOrder.nativeOrder());
        mIndexBuffer = iByteBuffer.asShortBuffer();
        mIndexBuffer.put(indices);
        mIndexBuffer.position(0);
    }

    private void loadTexture(GL10 gl){
        int[] textures = new int[1];
        gl.glGenTextures(1,textures,0);
        mTextureId = textures[0];
        gl.glBindTexture(GL10.GL_TEXTURE_2D,mTextureId);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D,0,mBitmap,0);
        mBitmap.recycle();
    }
}
