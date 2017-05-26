package com.example.ku.collection.graphic;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author Ronan.zhuang
 * @Date 11/18/16.
 * All copyright reserved.
 */

public abstract class Graph {
    protected FloatBuffer mVertexBuffer;

    protected ShortBuffer mIndexBuffer;

    protected FloatBuffer mColorBuffer;

    public abstract void draw(GL10 gl);
}
