package com.opengl.demo.util;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by user on 2018/4/2.
 */
public class TextureTool {

    /**
     * 获得纹理ID
     * author zx
     * version 1.0
     * since 2018/4/2  .
     */
    public static int createTexture(Bitmap bitmap) {
        int[] texture = new int[1];
        try {
            if (bitmap != null && !bitmap.isRecycled()) {
                //生成纹理
                GLES20.glGenTextures(1, texture, 0);
                //生成纹理
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0]);
                //设置缩小过滤为使用纹理中坐标最接近的一个像素的颜色作为需要绘制的像素颜色
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
                //设置放大过滤为使用纹理中坐标最接近的若干个颜色，通过加权平均算法得到需要绘制的像素颜色
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
                //设置环绕方向S，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
                //设置环绕方向T，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
                //根据以上指定的参数，生成一个2D纹理
                GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
                //加载完成释放纹理
                GLES20.glBindTexture(GL10.GL_TEXTURE_2D, 0);
            }
        } catch (Exception e) {
            Log.e("ES20_ERROR", "Create texture error .");
            e.printStackTrace();
        } finally {
            if (bitmap != null) {
                bitmap.recycle();
            }
            return texture[0];
        }
    }

}
