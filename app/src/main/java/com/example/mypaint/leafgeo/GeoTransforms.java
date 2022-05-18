package com.example.mypaint.leafgeo;

public interface GeoTransforms {
    public  Object translate(float x, float y, float z);
    public  Object scale(float x, float y, float z);
    public  Object rotate(String axis, float angle);
    public Object observe(Matrix4 observe);
}