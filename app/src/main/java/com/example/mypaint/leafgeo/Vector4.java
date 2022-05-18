package com.example.mypaint.leafgeo;

public class Vector4 {
    public float[] v4 = new float[4];

    public Vector4(){
        for (int i = 0; i < 3; i++) {
            this.v4[i] = 0;
        }
        this.v4[3] = 1;
    }

    public Vector4(Vector v){
        this.v4[0] = v.x;
        this.v4[1] = v.y;
        this.v4[2] = v.z;
        this.v4[3] = 1;
    }

    public float getW(){
        return this.v4[3];
    }
    public void setW(float w){
        this.v4[3] = w;
    }

    public Vector toVector(){
        this.normalize();
        return new Vector(v4[0], v4[1], v4[2]);
    }

    public void normalize(){
        for (int i = 0; i < 3; i++) {
            this.v4[i] *= this.getW();
        }
        this.setW(1);
    }

    public Vector4 mulMatrix(Matrix4 A){
        return A.mulVector(this);
    }

    @Override
    public String toString(){
        String s = "";
        for (int i = 0; i < 4; i++) {
            s = s.concat("| " + String.valueOf(this.v4[i]) + " |\n" );
        }
        return s;
    }
}