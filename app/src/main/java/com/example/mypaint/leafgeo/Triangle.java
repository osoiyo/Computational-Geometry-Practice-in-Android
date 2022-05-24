package com.example.mypaint.leafgeo;

import java.util.ArrayList;

public class Triangle extends Polygon{
    public Triangle(ArrayList<Point> pts) {
        super(pts);
    }

    public Triangle() {
        super();
    }

    public Triangle(Point A, Point B, Point C){
        this.vertexes.add(A);
        this.vertexes.add(B);
        this.vertexes.add(C);
    }

    public Triangle(LineSegment ln, Point pt){
        this.vertexes.add(ln.A);
        this.vertexes.add(ln.B);
        this.vertexes.add(pt);
    }

}
