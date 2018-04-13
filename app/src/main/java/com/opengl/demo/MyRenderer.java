package com.opengl.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.opengl.demo.util.BufferUtil;
import com.opengl.demo.util.ShaderTool;
import com.opengl.demo.util.TextureTool;

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
    private SurfaceTexture surfaceTexture;
    private MySurfaceView.SurfaceCallBack callBack;


    public static MyRenderer build(Context context) {
        return new MyRenderer(context);
    }

    private MyRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        Log.e("MyRendererTest1", "|onSurfaceCreated");

        // 基于顶点着色器与片元着色器创建程序
        program = ShaderTool.createProgram(ShaderScript.verticesShader, ShaderScript.fragmentShader);
        aPosition = GLES20.glGetAttribLocation(program, "aPosition");
        aTexCoord = GLES20.glGetAttribLocation(program, "aTexCoord");
        uColor = GLES20.glGetUniformLocation(program, "uColor");
        sTexture = GLES20.glGetUniformLocation(program, "sTexture");
        // 设置清屏颜色
        GLES20.glClearColor(0f, 0f, 1f, 0f);
        // 启用2D纹理贴图
        gl.glEnable(GL10.GL_TEXTURE_2D);
        // 生成纹理ID
        createTexture(context, callBack);
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
        GLES20.glUniform4f(uColor, 1.0f, 1.0f, 0.0f, 1.0f);

        // 形状------------------------------------------↓↓↓↓↓↓↓
        // 允许顶点位置数据数组
        GLES20.glEnableVertexAttribArray(aPosition);
        // 为画笔指定顶点位置数据(aPosition)
        GLES20.glVertexAttribPointer(aPosition, 2, GLES20.GL_FLOAT, false, 0, BufferUtil.floatToBuffer(vertices));

        // 纹理------------------------------------------↓↓↓↓↓↓↓
        // 允许顶点位置数据数组
        GLES20.glEnableVertexAttribArray(aTexCoord);
        // 为画笔指定顶点位置数据(aTexCoord)
        GLES20.glVertexAttribPointer(aTexCoord, 2, GLES20.GL_FLOAT, false, 0, BufferUtil.floatToBuffer(textures));

        // 执行纹理贴图
        gl.glActiveTexture(GLES20.GL_TEXTURE0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, idTexture);
        GLES20.glUniform1i(sTexture, 0);

        // 绘制
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertices.length / 2);
        //GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_INT, BufferUtil.intToBuffer(drawOrder));

        // 关闭顶点数组
        GLES20.glEnableVertexAttribArray(aPosition);
        GLES20.glEnableVertexAttribArray(aTexCoord);
        // 结束绘制
        GLES20.glFinish();
    }


    /**
     * 获取图形的顶点
     * 特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
     * 转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
     *
     * @return 顶点Buffer
     */
    private float vertices[] = {
            -1.0f, 0.5f,// 三角形1
            0.0f, 0.5f,
            -1f, -0.5f,

            -1f, -0.5f,// 三角形2
            0.0f, -0.5f,
            0.0f, 0.5f,

            0.0f, 0.5f,// 三角形3
            1.0f, 0.5f,
            0.0f, -0.5f,

            0.0f, -0.5f,// 三角形4
            1.0f, -0.5f,
            1.0f, 0.5f,

    };


    /**
     * 获取图形的顶点
     * 特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
     * 转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
     *
     * @return 顶点Buffer
     */
    private float textures[] = {
            0.0f, 0.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,

            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,

            0.0f, 0.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,

            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,
    };


    /**
     * 获取画图顺序
     * 特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
     * 转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
     *
     * @return 顶点Buffer
     */
    private int drawOrder[] = {
            0, 1, 2,
            2, 3, 0
    };


    /**
     * 创建Texture
     * author zx
     * version 1.0
     * since 2018/4/2  .
     */
    private int createTexture(Context context, MySurfaceView.SurfaceCallBack callBack) {
        // 初始化纹理
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_3d);
        idTexture = TextureTool.createTexture(bm);
        // 初始化SurfaceTexture
        surfaceTexture = new SurfaceTexture(idTexture);
        surfaceTexture.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() {
            @Override
            public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                Log.e("MyRendererTest", "|onFrameAvailable:");
            }
        });
        if (callBack != null) {
            callBack.onCreated(idTexture, surfaceTexture);
        }
        return idTexture;
    }


    /**
     * author zx
     * version 1.0
     * since 2018/4/13  .
     */
    public void setCallBack(MySurfaceView.SurfaceCallBack callBack) {
        this.callBack = callBack;
    }

}
