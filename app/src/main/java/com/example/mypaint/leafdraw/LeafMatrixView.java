package com.example.mypaint.leafdraw;

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
    public static float a = 200, b = 500, c = 100;
    public static Matrix4 mm = init();

    public static Matrix4 init(){
        Matrix4 mv = Matrix4.genViewport(0, 0, w, h);
        Matrix4 mo = Matrix4.genOrtho(-o_right, o_right, -o_top, o_top, o_near, o_far);
        Matrix4 me = Matrix4.genLookat(a , b, c);
        Matrix4 mm = Matrix4.genObserveMatrix4(mv, mo, me);
        return mm;
    }

}
