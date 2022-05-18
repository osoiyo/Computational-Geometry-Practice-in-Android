package com.example.mypaint.leafgeo;

public class GeoObserve {
    public static Point observe(Point pt, Matrix4 m){
        return pt.toVector4().mulMatrix(m).toVector().toPoint();
    }
}