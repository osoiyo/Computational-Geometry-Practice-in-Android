package com.example.mypaint.leafgeo;

import com.example.mypaint.leafgeo.LeafMatrixView;
import com.example.mypaint.leafgeo.LineSegment;
import com.example.mypaint.leafgeo.Point;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyHouse {
    public static ArrayList<Point> m_rawHousePts = null;
    public static ArrayList<LineSegment> m_rawHouseLns = null;
    public static ArrayList<LineSegment> m_houseLns2D = null;

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

    public static ArrayList<Point> getRawHousePts(){

        if(m_rawHousePts != null){
            return m_rawHousePts;
        }

        ArrayList<Point> rawHousePts = new ArrayList<>();
        for (float[] pt: house ) {
            rawHousePts.add(new Point(pt[0], pt[1], pt[2]));
        }
        m_rawHousePts = rawHousePts;

        return rawHousePts;
    }

    public static ArrayList<LineSegment> getRawHouseLns(){

        if(m_rawHouseLns != null){
            return m_rawHouseLns;
        }

        ArrayList<LineSegment> rawHouseLns = new ArrayList<>();
        ArrayList<Point> rawHousePts = getRawHousePts();

        rawHouseLns.add(new LineSegment(rawHousePts.get(0), rawHousePts.get(1)));
        rawHouseLns.add(new LineSegment(rawHousePts.get(1), rawHousePts.get(2)));
        rawHouseLns.add(new LineSegment(rawHousePts.get(2), rawHousePts.get(3)));
        rawHouseLns.add(new LineSegment(rawHousePts.get(3), rawHousePts.get(0)));

        // ceiling
        rawHouseLns.add(new LineSegment(rawHousePts.get(4), rawHousePts.get(5)));
        rawHouseLns.add(new LineSegment(rawHousePts.get(5), rawHousePts.get(6)));
        rawHouseLns.add(new LineSegment(rawHousePts.get(6), rawHousePts.get(7)));
        rawHouseLns.add(new LineSegment(rawHousePts.get(7), rawHousePts.get(4)));

        // wall
        rawHouseLns.add(new LineSegment(rawHousePts.get(0), rawHousePts.get(4)));
        rawHouseLns.add(new LineSegment(rawHousePts.get(1), rawHousePts.get(5)));
        rawHouseLns.add(new LineSegment(rawHousePts.get(2), rawHousePts.get(6)));
        rawHouseLns.add(new LineSegment(rawHousePts.get(3), rawHousePts.get(7)));

        // roof
        rawHouseLns.add(new LineSegment(rawHousePts.get(4), rawHousePts.get(8)));
        rawHouseLns.add(new LineSegment(rawHousePts.get(7), rawHousePts.get(8)));
        rawHouseLns.add(new LineSegment(rawHousePts.get(5), rawHousePts.get(9)));
        rawHouseLns.add(new LineSegment(rawHousePts.get(6), rawHousePts.get(9)));
        rawHouseLns.add(new LineSegment(rawHousePts.get(8), rawHousePts.get(9)));

        m_rawHouseLns = rawHouseLns;
        return rawHouseLns;
    }

    public static ArrayList<LineSegment> getHouseLns2D(){
        if(m_houseLns2D != null){
            return m_houseLns2D;
        }
        ArrayList<LineSegment> rawHouseLns = getRawHouseLns();
        ArrayList<LineSegment> houseLns2D = new ArrayList<>();

        for (LineSegment ln: rawHouseLns ) {
            houseLns2D.add(ln.observe(LeafMatrixView.mm));
        }
        return houseLns2D;
    }

    public static void initHouse(){
        m_rawHousePts = getRawHousePts();
        m_rawHouseLns = getRawHouseLns();
        m_houseLns2D = getHouseLns2D();
    }

    public static Matrix4 genRotateHouseZ(float angle){
        return Matrix4.genRotate("z", angle);
    }
    public static ArrayList<LineSegment> rotateHouse(ArrayList<LineSegment> lns, Matrix4 mr){
        ArrayList<LineSegment> res = new ArrayList<>();
        for (LineSegment ln : lns) {
            res.add(ln.observe(mr));
        }
        return res;
    }
}
