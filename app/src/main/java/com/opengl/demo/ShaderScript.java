package com.opengl.demo;

/**
 * Created by user on 2018/4/2.
 */
public class ShaderScript {

    // 顶点着色器的脚本
    public static final String verticesShader
            = "attribute vec2 aPosition;               \n" // 顶点位置属性aPosition
            + "attribute vec2 aTexCoord;               \n" // 顶点位置属性aTexCoord
            + "varying vec2 vTexCoord;                 \n"
            + "void main(){                            \n"
            + "   vTexCoord = aTexCoord;               \n"
            + "   gl_Position = vec4(aPosition,0,1);   \n" // 确定顶点位置
            + "}";


    // 片元着色器的脚本
    public static final String fragmentShader
            = "precision mediump float;                \n" // 声明float类型的精度为中等(精度越高越耗资源)
            + "uniform vec4 uColor;                    \n" // uniform的属性uColor
            + "uniform sampler2D sTexture;             \n"
            + "varying vec2 vTexCoord;                 \n"
            + "void main(){                            \n"
            + "   gl_FragColor = uColor;               \n" // 给此片元的填充色
            + "   gl_FragColor = texture2D(sTexture, vTexCoord); \n"
            + "}";

}
