package com.example.mypaint.leafgeo;

import java.lang.Math;

public class Vector implements VectorOperations, GeoTransforms{
    public float x;
    public float y;
    public float z = 0;
    public boolean is3D = false;

    public Vector() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.is3D = false;
    }

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
        this.is3D = false;
    }

    public Vector(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.is3D = true;
    }

    public Vector(float[] pt){
        this(pt[0], pt[1], pt[2]);
    }

    public Vector(Vector v){
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.is3D = v.is3D;
    }

    public Point toPoint(){
        return new Point(this);
    }

    @Override
    public float module() {
        if (!is3D) z = 0;
        double moduleSquare = Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2);
        return (float) Math.sqrt(moduleSquare);
    }

    @Override
    public Vector scalarMultiply(float scalar) {
        return this.is3D ? 
                new Vector(x * scalar, y * scalar, z * scalar)
                :
                new Vector(x * scalar, y * scalar);
    }

    @Override
    public Vector add(Vector another) {
        return this.is3D ? 
                new Vector(x + another.x, y + another.y)
                :
                new Vector(x + another.x, y + another.y, z + another.z);
    }

    @Override
    public Vector subtract(Vector another) {
        return this.is3D ? 
                new Vector(x - another.x, y - another.y)
                :
                new Vector(x - another.x, y - another.y, z - another.z);
    }

    @Override
    public float pointMultiply(Vector another) {
        if (!is3D) z = 0;
        return x * another.x + y * another.y + z * another.z;
    }

    @Override
    public Vector crossMultiply(Vector another) {
        float tempX = y * another.z - z * another.y;
        float tempY = z * another.x - x * another.z;
        float tempZ = x * another.y - y * another.x;
        return new Vector(tempX, tempY, tempZ);
    }

    @Override
    public float crossMultiply2D(Vector another){
        if (this.is3D || another.is3D){
            System.out.println("3D Point Cannot Using crossMultiply2D!");
        }
        return x*another.y - y*another.x;
    }

    @Override
    public String toString(){

        return is3D ?  
            "(" + String.valueOf(getApproximation(x)) + ", " + String.valueOf(getApproximation(y)) + ", " + String.valueOf(getApproximation(z)) + ")" :
            "(" + String.valueOf(getApproximation(x)) + ", " + String.valueOf(getApproximation(y)) + ")";
    }
    public static float getApproximation(float x){

        //return (float)(Math.round(x*100.0) / 100.0);
        return x;
    }

    public boolean is_NullVector(){
        final double EPS = 1e-5;
        
        return (-EPS < this.x && this.x < EPS &&
                -EPS < this.y && this.y < EPS &&
                -EPS < this.z && this.z < EPS );
    }

    public Vector4 toVector4(){
        return new Vector4(this);
    }

    @Override
    public Point translate(float x, float y, float z) {
        return this.toVector4().mulMatrix(Matrix4.genTranslate(x, y, z)).toVector().toPoint();
    }

    @Override
    public Point scale(float x, float y, float z) {
        return this.toVector4().mulMatrix(Matrix4.genScale(x, y, z)).toVector().toPoint();
    }

    @Override
    public Point rotate(String axis, float angle) {
        return this.toVector4().mulMatrix(Matrix4.genRotate(axis, angle)).toVector().toPoint();
    }

    @Override
    public Point observe(Matrix4 m){
        return GeoObserve.observe(this.toPoint(), m);
    }

}