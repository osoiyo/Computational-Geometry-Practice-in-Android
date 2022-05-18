package com.example.mypaint.leafgeo;

import java.util.Arrays;

public class Matrix4 {
    public static final Matrix4 IDENTITY = new Matrix4();
    public float[][] matrix = new float[4][4];

    public static Matrix4 genTranslate(float x, float y, float z){
        float[][] m = {
            {1, 0, 0, x},
            {0, 1, 0, y},
            {0, 0, 1, z},
            {0, 0, 0, 1}
        };
        return new Matrix4(m);
    }

    public static Matrix4 genScale(float x, float y, float z){
        float[][] m = {
            {x, 0, 0, 0},
            {0, y, 0, 0},
            {0, 0, z, 0},
            {0, 0, 0, 1}
        };
        return new Matrix4(m);
    }


    public static Matrix4 genRotate(String axis, float angle){
        Matrix4 m = null;
        angle = (float)(angle / 180.0 * Math.PI);
        switch (axis) {
            case "x":
                float[][] mx = {
                    {1, 0,               0,                0},
                    {0, (float) Math.cos(angle), (float) -Math.sin(angle), 0},
                    {0, (float) Math.sin(angle), (float) Math.cos(angle),  0},
                    {0, 0,               0,                1}
                };
                m = new Matrix4(mx);
                break;
        
            case "y":
                float[][] my = {
                    {(float) Math.cos(angle),  0, (float) Math.sin(angle), 0},
                    {0,                1, 0,               0},
                    {(float) -Math.sin(angle), 0, (float) Math.cos(angle), 0},
                    {0,                0, 0,               1}
                };
                m = new Matrix4(my);
                break;
                
            case "z":
                float[][] mz = {
                    {(float) Math.cos(angle), (float) -Math.sin(angle), 0, 0},
                    {(float) Math.sin(angle), (float) Math.cos(angle),  0, 0},
                    {0,               0,                1, 0},
                    {0,               0,                0, 1}
                };
                m = new Matrix4(mz);
                break;

            default:
                System.out.println("Wrong Argument: Use x, y or z");
                break;
        }

        return m;
    }

    public static Matrix4 genViewport(float x0, float y0, float w, float h){
        float mv[][] = {
            {w/(float) 2.0,     0,          0,      x0+w/(float) 2.0},
            {0,         -h/(float) 2.0,     0,      y0+h/(float) 2.0},
            {0,         0,          1,      0},
            {0,         0,          0,      1}
        };
        return new Matrix4(mv);
    }
    public static Matrix4 genLookat(float a, float b, float c){
        float u = (float) Math.sqrt(a*a + b*b + c*c);
        float v = (float) Math.sqrt(a*a + b*b);
        float uv = u * v;
        float me[][] = {
            {-b/v,      a/v,        0,      0},
            {-a*c/uv,   -b*c/uv,    v/u,    0},
            {a/u,       b/u,        c/u,    -u},
            {0,         0,          0,      1}
        };
        return new Matrix4(me);
    }
    public static Matrix4 genOrtho(float l, float r, float b, float t, float n, float f){
        float mo[][] = {
            {(float) 2.0/(r-l), 0,          0,          -(r+l)/(r-l)},
            {0,         (float) 2.0/(t-b),  0,          -(t+b)/(t-b)},
            {0,         0,          (float) -2.0/(f-n), -(f+n)/(f-n)},
            {0,         0,          0,          1}
        };
        return new Matrix4(mo);
    }

    public static Matrix4 genObserveMatrix4(Matrix4 mv, Matrix4 mo, Matrix4 me){
        return Matrix4.IDENTITY.mulMatrix(mv).mulMatrix(mo).mulMatrix(me);
    }

    public Matrix4(){
        this.identify();
    }

    public Matrix4(float[][] matrix){
        this.matrix = Arrays.copyOf(matrix, 4);
    }

    public void identify(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.matrix[i][j] = (i == j ? 1 : 0);
            }
        }
    }

    public Matrix4 mulMatrix(Matrix4 A){
        Matrix4 B = new Matrix4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    B.matrix[i][j] += this.matrix[i][k] * A.matrix[k][j];
                }
            }
        }
        return B;
    }

    public Vector4 mulVector(Vector4 u){
        //if(u.getW() != 1) { u.normalize(); }

        Vector4 v = new Vector4();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                v.v4[i] += this.matrix[i][j] * u.v4[j];
            }
            v.v4[i] += this.matrix[i][3];
        }

        if (v.getW() != 0){
            for (int i = 0; i < 3; i++) {
                v.v4[i] /= v.getW();
            }
        }

        if(v.getW() != 1){ v.normalize(); }

        return v;
    }

    @Override
    public String toString(){
        String s = "";

        for (int i = 0; i < 4; i++) {
            s = s.concat("| ");
            for (int j = 0; j < 4; j++) {
                s = s.concat(String.valueOf((int)this.matrix[i][j]) + " ");
            }
            s = s.concat("|\n");
        }
        return s;
    }
    
}
