package com.opengl.demo;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

/**
 * Created by user on 2018/4/2.
 */
public class MySurfaceView extends GLSurfaceView {

    private MyRenderer renderer;
    private SurfaceTexture sfTexture;

    public MySurfaceView(Context context) {
        super(context);
        init(context);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        super.surfaceCreated(holder);
    }

    /**
     * 初始化
     * author zx
     * version 1.0
     * since 2018/4/2  .
     */
    private void init(Context context) {
        setEGLContextClientVersion(2);
        renderer = new MyRenderer(context);
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }


}
