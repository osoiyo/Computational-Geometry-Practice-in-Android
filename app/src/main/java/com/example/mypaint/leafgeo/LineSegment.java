package com.example.mypaint.leafgeo;

// import com.example.leafgeo.Point;

public class LineSegment implements GeoTransforms{

    public Point A;
    public Point B;

    public LineSegment(Point A, Point B){
        this.A = new Point(A);
        this.B = new Point(B);
    }
    // public LineSegment(Vector A, Vector B){
    //     this.A = new Point(A);
    //     this.B = new Point(B);
    // }
    
    public Vector toVector(){
        return B.subtract(A);
    }

    public float getLength(){
        return this.toVector().module();
    }

    @Override
    public String toString(){
        return this.A.toString() + " --- " + this.B.toString();
    }

    @Override
    public LineSegment translate(float x, float y, float z) {
        Point a = this.A.translate(x, y, z);
        Point b = this.B.translate(x, y, z);
        return new LineSegment(a,b);
    }

    @Override
    public LineSegment scale(float x, float y, float z) {
        Point a = this.A.scale(x, y, z);
        Point b = this.B.scale(x, y, z);
        return new LineSegment(a,b);
    }

    @Override
    public LineSegment rotate(String axis, float angle) {
        Point a = this.A.rotate(axis, angle);
        Point b = this.B.rotate(axis, angle);
        return new LineSegment(a,b);
    }

    @Override
    public LineSegment observe(Matrix4 m){
        Point A = this.A.observe(m);
        Point B = this.B.observe(m);
        return new LineSegment(A, B);
    }

    public void mulMatrixSelf(Matrix4 m){
        this.A = A.toVector4().mulMatrix(m).toVector().toPoint();
        this.B = B.toVector4().mulMatrix(m).toVector().toPoint();
    }


}
