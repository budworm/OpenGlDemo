package com.opengl.demo.util;

import android.opengl.GLES20;
import android.util.Log;


/**
 * Created by user on 2018/4/2.
 */
public class ShaderTool {

    /**
     * 创建shader程序的方法
     */
    public static int createProgram(String vertexSource, String fragmentSource) {
        // 加载顶点着色器
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == 0) {
            return 0;
        }
        // 加载片元着色器
        int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        if (pixelShader == 0) {
            return 0;
        }
        // 创建程序
        int program = GLES20.glCreateProgram();
        // 若程序创建成功则向程序中加入顶点着色器与片元着色器
        if (program != 0) {
            // 向程序中加入顶点着色器
            GLES20.glAttachShader(program, vertexShader);
            // 向程序中加入片元着色器
            GLES20.glAttachShader(program, pixelShader);
            // 链接程序
            GLES20.glLinkProgram(program);
            // 存放链接成功program数量的数组
            int[] linkStatus = new int[1];
            // 获取program的链接情况
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            // 若链接失败则报错并删除程序
            if (linkStatus[0] != GLES20.GL_TRUE) {
                Log.e("ES20_ERROR", "+----[Could not link program :]----+");
                Log.e("ES20_ERROR", "|");
                Log.e("ES20_ERROR", "|");
                Log.e("ES20_ERROR", GLES20.glGetProgramInfoLog(program));
                Log.e("ES20_ERROR", "|");
                Log.e("ES20_ERROR", "|");
                Log.e("ES20_ERROR", "+----[          end           ]----+");
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        }
        return program;
    }


    /**
     * 加载制定shader的方法
     *
     * @param shaderType shader的类型  GLES20.GL_VERTEX_SHADER   GLES20.GL_FRAGMENT_SHADER
     * @param sourceCode shader的脚本
     * @return shader索引
     */
    public static int loadShader(int shaderType, String sourceCode) {
        // 创建一个新shader
        int shader = GLES20.glCreateShader(shaderType);
        // 若创建成功则加载shader
        if (shader != 0) {
            // 加载shader的源代码
            GLES20.glShaderSource(shader, sourceCode);
            // 编译shader
            GLES20.glCompileShader(shader);
            // 存放编译成功shader数量的数组
            int[] compiled = new int[1];
            // 获取Shader的编译情况
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {//若编译失败则显示错误日志并删除此shader

                Log.e("ES20_ERROR", "+----[Could not compile shader :]----+");
                Log.e("ES20_ERROR", "|");
                Log.e("ES20_ERROR", "|");
                Log.e("ES20_ERROR", GLES20.glGetShaderInfoLog(shader));
                Log.e("ES20_ERROR", "|");
                Log.e("ES20_ERROR", "|");
                Log.e("ES20_ERROR", "+----[          end             ]----+");

                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

}
