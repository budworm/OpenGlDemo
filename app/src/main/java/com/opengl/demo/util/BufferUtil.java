package com.opengl.demo.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by user on 2018/4/2.
 */
public class BufferUtil {

    /**
     * 获取图形的顶点
     * 特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
     * 转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
     *
     * @return 顶点Buffer
     */
    public static FloatBuffer floatToBuffer(float[] vertices) {
        // 创建顶点坐标数据缓冲
        // vertices.length*4是因为一个float占四个字节
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());             //设置字节顺序
        FloatBuffer vertexBuf = vbb.asFloatBuffer();    //转换为Float型缓冲
        vertexBuf.put(vertices);                        //向缓冲区中放入顶点坐标数据
        vertexBuf.position(0);                          //设置缓冲区起始位置
        return vertexBuf;
    }


    // 将数组a转化为intbuffer
    public static IntBuffer intToBuffer(int[] a) {
        //先初始化buffer，数组的长度*4，因为一个float占4个字节
        ByteBuffer mbb = ByteBuffer.allocateDirect(a.length * 4);
        //数组排序用nativeOrder
        mbb.order(ByteOrder.nativeOrder());
        IntBuffer mBuffer2 = mbb.asIntBuffer();
        mBuffer2.put(a);
        mBuffer2.position(0);
        return mBuffer2;
    }

    //创建一个长度为length的Floatbuffer,存储方式为opengl的存储方式，在每次调用put加入点后position都会加1，因此加入点后在绘图时候将position重置为0
    public static FloatBuffer getFloatBuffer(int length) {
        ByteBuffer mbb = ByteBuffer.allocateDirect(length * 4);
        mbb.order(ByteOrder.nativeOrder());
        FloatBuffer mBuffer = mbb.asFloatBuffer();
        mBuffer.position(0);
        return mBuffer;
    }

    public static IntBuffer getIntBuffer(int length) {
        ByteBuffer mbb = ByteBuffer.allocateDirect(length * 4);
        mbb.order(ByteOrder.nativeOrder());
        IntBuffer mBuffer = mbb.asIntBuffer();
        mBuffer.position(0);
        return mBuffer;
    }

}
