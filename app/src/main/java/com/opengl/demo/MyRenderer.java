package com.opengl.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.opengl.demo.util.BufferUtil;
import com.opengl.demo.util.ShaderTool;
import com.opengl.demo.util.TextureTool;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import demo.opengl.com.opengldemo.R;


/**
 * Created by user on 2018/4/2.
 */
public class MyRenderer implements GLSurfaceView.Renderer {

    private int program;
    private int aPosition;
    private int aTexCoord;

    private int uColor;
    private int sTexture;

    private Context context;
    private int idTexture;

    public static MyRenderer build(Context context) {
        return new MyRenderer(context);
    }

    private MyRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        Log.e("MyRendererTest", "|onSurfaceCreated");

        // 基于顶点着色器与片元着色器创建程序
        program = ShaderTool.createProgram(ShaderScript.verticesShader, ShaderScript.fragmentShader);

        aPosition = GLES20.glGetAttribLocation(program, "aPosition");
        aTexCoord = GLES20.glGetAttribLocation(program, "aTexCoord");

        uColor = GLES20.glGetUniformLocation(program, "uColor");
        sTexture = GLES20.glGetUniformLocation(program, "sTexture");

        // 设置清屏颜色
        GLES20.glClearColor(0f, 0f, 1f, 0f);
        // 初始化纹理
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_3d);
        idTexture = TextureTool.createTexture(bm);
        // 启用2D纹理贴图
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.e("MyRendererTest", "|onSurfaceChanged:" + width + "*" + height);
        // 设置3D视窗的大小及位置
        gl.glViewport(0, 0, width, height);
    }


    @Override
    public void onDrawFrame(GL10 gl) {
        Log.e("MyRendererTest", "|onDrawFrame:" + idTexture);

        // 清屏
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        // 使用shader程序
        GLES20.glUseProgram(program);

        // 颜色------------------------------------------↓↓↓↓↓↓↓
        // 设置属性uColor(颜色 索引,R,G,B,A)
        GLES20.glUniform4f(uColor, 1.0f, 0.0f, 0.0f, 1.0f);

        // 形状------------------------------------------↓↓↓↓↓↓↓
        // 允许顶点位置数据数组
        GLES20.glEnableVertexAttribArray(aPosition);
        // 为画笔指定顶点位置数据(aPosition)
        GLES20.glVertexAttribPointer(aPosition, 2, GLES20.GL_FLOAT, false, 0, getVertices());

        // 纹理------------------------------------------↓↓↓↓↓↓↓
        // 允许顶点位置数据数组
        GLES20.glEnableVertexAttribArray(aTexCoord);
        // 为画笔指定顶点位置数据(aTexCoord)
        GLES20.glVertexAttribPointer(aTexCoord, 2, GLES20.GL_FLOAT, false, 0, getTextures());

        // 执行纹理贴图
        gl.glActiveTexture(GLES20.GL_TEXTURE0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, idTexture);
        GLES20.glUniform1i(sTexture, 0);

        // 绘制
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 3);

        // 关闭顶点数组
        GLES20.glEnableVertexAttribArray(aPosition);
        GLES20.glEnableVertexAttribArray(aTexCoord);
    }


    /**
     * 获取图形的顶点
     * 特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
     * 转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
     *
     * @return 顶点Buffer
     */
    private FloatBuffer getVertices() {
        float vertices[] = {
                -0.5f, 0.5f,
                0.5f, 0.5f,
                -0.5f, -0.5f
        };
        return BufferUtil.floatToBuffer(vertices);
    }


    /**
     * 获取图形的顶点
     * 特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
     * 转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
     *
     * @return 顶点Buffer
     */
    private FloatBuffer getTextures() {
        float vertices[] = {
                0.0f, 0.0f,
                1f, 0.0f,
                0f, 1f,

        };
        return BufferUtil.floatToBuffer(vertices);
    }


}
