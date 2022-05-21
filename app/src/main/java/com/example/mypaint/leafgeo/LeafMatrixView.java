package com.example.mypaint.leafgeo;

import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.mypaint.leafgeo.Matrix4;

public class LeafMatrixView {
    public static float w = 1080;
    public static float h = 1794;

    public static float o_right = 500;
    public static float o_top = 500;
    public static float o_near = 1;
    public static float o_far = 1000;

    // 这是一个向量的角度, a+向左转，c+向上转， b+拉伸？？？
    public static float a = 80, b = 200, c = 10;
    public static Matrix4 mm = null;

    public static void initDevice(float w, float h){
        LeafMatrixView.w = w;
        LeafMatrixView.h = h;
    }

    public static Matrix4 initVOE(){
        Matrix4 mv = Matrix4.genViewport(0, 0, w, h);
        Matrix4 mo = Matrix4.genOrtho(-o_right, o_right, -o_top, o_top, o_near, o_far);
        Matrix4 me = Matrix4.genLookat(a , b, c);
        Matrix4 mVOE = Matrix4.genObserveMatrix4(mv, mo, me);
        LeafMatrixView.mm = mVOE;
        return mVOE;
    }

    public static Matrix4 initVO(){
        Matrix4 mv = Matrix4.genViewport(0, 0, w, h);
        Matrix4 mo = Matrix4.genOrtho(-o_right, o_right, -o_top, o_top, o_near, o_far);
        Matrix4 mVO = Matrix4.IDENTITY.mulMatrix(mv).mulMatrix(mo);
        return mVO;
    }

    public static Matrix4 initByEye(float a, float b, float c){
        Matrix4 me = Matrix4.genLookat(a, b, c);
        return initVO().mulMatrix(me);
    }

}
