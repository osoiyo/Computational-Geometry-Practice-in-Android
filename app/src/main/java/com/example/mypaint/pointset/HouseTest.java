package com.example.mypaint.pointset;

import com.example.mypaint.leafdraw.LeafMatrixView;
import com.example.mypaint.leafgeo.LineSegment;
import com.example.mypaint.leafgeo.Point;

import java.util.ArrayList;

public class HouseTest {

    public static float[][] house = {
            // floor
            {-300, -200, 0},        //f1
            {300, -200, 0},         //f2
            {300, 200, 0},       //f3
            {-300, 200, 0},      //f4

            // ceiling
            {-300, -200, 300},      //c1
            {300, -200, 300},       //c2
            {300, 200, 300},     //c3
            {-300, 200, 300},    //c4

            // roof
            {-300, 0, 450},        // r1
            {300, 0, 450}       // r2
    };

    public static ArrayList<LineSegment> house_ln = getHouseLn(house);

    public static ArrayList<LineSegment> getHouseLn(float[][] house){
        ArrayList<Point> house_pt = new ArrayList<Point>();
        for (int i = 0; i < house.length; i++) {
            house_pt.add(new Point(house[i]).observe(LeafMatrixView.mm));
        }
        ArrayList<LineSegment> house_ln = new ArrayList<>();
        // floor
        house_ln.add(new LineSegment(house_pt.get(0), house_pt.get(1)));
        house_ln.add(new LineSegment(house_pt.get(1), house_pt.get(2)));
        house_ln.add(new LineSegment(house_pt.get(2), house_pt.get(3)));
        house_ln.add(new LineSegment(house_pt.get(3), house_pt.get(0)));

        // ceiling
        house_ln.add(new LineSegment(house_pt.get(4), house_pt.get(5)));
        house_ln.add(new LineSegment(house_pt.get(5), house_pt.get(6)));
        house_ln.add(new LineSegment(house_pt.get(6), house_pt.get(7)));
        house_ln.add(new LineSegment(house_pt.get(7), house_pt.get(4)));

        // wall
        house_ln.add(new LineSegment(house_pt.get(0), house_pt.get(4)));
        house_ln.add(new LineSegment(house_pt.get(1), house_pt.get(5)));
        house_ln.add(new LineSegment(house_pt.get(2), house_pt.get(6)));
        house_ln.add(new LineSegment(house_pt.get(3), house_pt.get(7)));

        // roof
        house_ln.add(new LineSegment(house_pt.get(4), house_pt.get(8)));
        house_ln.add(new LineSegment(house_pt.get(7), house_pt.get(8)));
        house_ln.add(new LineSegment(house_pt.get(5), house_pt.get(9)));
        house_ln.add(new LineSegment(house_pt.get(6), house_pt.get(9)));
        house_ln.add(new LineSegment(house_pt.get(8), house_pt.get(9)));

        return house_ln;
    }

}
