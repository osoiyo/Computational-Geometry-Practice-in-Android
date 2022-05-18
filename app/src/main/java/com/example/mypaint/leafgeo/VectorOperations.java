package com.example.mypaint.leafgeo;

public interface VectorOperations {
    public float module();
    public Vector scalarMultiply(float scalar);

    public Vector add(Vector another);
    public Vector subtract(Vector another);

    public float pointMultiply(Vector another);
    public Vector crossMultiply(Vector another);
    public float crossMultiply2D(Vector another);
}