package com.opengl.demo;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;


/**
 * Created by user on 2018/4/2.
 */
public class MySurfaceView extends GLSurfaceView {

    private MyRenderer renderer;
    private SurfaceCallBack callBack;

    public void setCallBack(SurfaceCallBack callBack) {
        this.callBack = callBack;
    }

    public MySurfaceView(Context context) {
        super(context);
        //initRenderer(context);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //initRenderer(context);
    }


    /**
     * 初始化Renderer
     * author zx
     * version 1.0
     * since 2018/4/2  .
     */
    public void initRenderer(Context context) {
        // open gl 编译版本
        setEGLContextClientVersion(2);
        renderer = MyRenderer.build(context);
        renderer.setCallBack(callBack);
        setRenderer(renderer);
        // 渲染模式
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }


    /**
     * author zx
     * version 1.0
     * since 2018/4/13  .
     */
    interface SurfaceCallBack {
        void onCreated(int id, SurfaceTexture texture);
    }

}
