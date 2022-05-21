package com.example.mypaint.leafgeo;

import com.google.android.material.transition.MaterialSharedAxis;

public class Point extends Vector{

    public Point(){
        super();
    }

    public Point(float x, float y){
        super(x, y);
    }
    
    public Point(float x, float y, float z){
        super(x, y, z);
    }

    public Point(Vector p){
        super(p);
    }
    public Point(float[] pt) {super(pt);}
    
    @Override
    public String toString(){
        return super.toString();
    }

    public float distanceToPoint(Point another){
        return another.subtract(this).module();
    }

    public boolean is_OnLine(LineSegment ln){
        return ((ln.toVector()) .crossMultiply((this.subtract(ln.A)))) .is_NullVector() ;
    }

    public boolean is_OnLine(Point A, Point B){
        return (B.subtract(A).crossMultiply((B.subtract(this)))).is_NullVector();
    }

    public boolean is_OnLineSegment(LineSegment ln){
        return this.is_OnLine(ln) &&
            (x > Math.min(ln.A.x, ln.B.x)) && (x < Math.max(ln.A.x, ln.B.x)) &&
            (y > Math.min(ln.A.y, ln.B.y)) && (y < Math.max(ln.A.y, ln.B.y));
    }
    public boolean is_OnLineSegment(Point A, Point B){
        return this.is_OnLine(A, B) &&
                (x > Math.min(A.x, B.x)) && (x < Math.max(A.x, B.x)) &&
                (y > Math.min(A.y, B.y)) && (y < Math.max(A.y, B.y));
    } 

    public float dis_toLineSegment(LineSegment ln){
        return GeoDecider.dis_PointToSegment(this, ln);
    }
    public float dis_toLineSegment(Point A, Point B){
        return GeoDecider.dis_PointToSegment(this, A, B);
    }

    public boolean isNear2D(Point pt2){
        return (this.x == pt2.x && this.y == pt2.y);
    }

}
