package com.example.mypaint.leafdraw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.mypaint.leafgeo.LineSegment;
import com.example.mypaint.leafgeo.Point;

public class LeafDraw {
    private static final String TAG = "LeafDraw";
    private static Paint linePaint = setPaint(Color.BLACK, Paint.Style.STROKE, 5);
    private static Paint pointPaint = setPaint(Color.RED, Paint.Style.FILL, 2);
    public static final int TYPE_2D = 0;
    public static final int TYPE_3D = 1;
    private static Canvas canvas;

    private static Paint setPaint(int color,Paint.Style stroke, int width){
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(stroke);
        paint.setStrokeWidth(width);
        return paint;
    }

    public static void setCanvas(Canvas canvas){
        LeafDraw.canvas = canvas;
    }

    public static void drawLine(LineSegment ln){
        canvas.drawLine(ln.A.x, ln.A.y, ln.B.x, ln.B.y, linePaint);
        drawPoint(ln.A);
        drawPoint(ln.B);
        Log.d(TAG, "drawLine: " + ln.toString());
    }

    public static void drawLine(Point A, Point B){
        LineSegment ln = new LineSegment(A, B);
        drawLine(ln);
    }

    public static void drawPoint(Point pt){
        canvas.drawCircle(pt.x, pt.y, 5, pointPaint);
        Log.d(TAG, "drawPoint: " + pt.toString());
    }

    public static void drawPoint(Point pt, int TYPE_3D){
        if(TYPE_3D == 1){
            pt = pt.observe(LeafMatrixView.mm);
        }
        drawPoint(pt);
    }
    public static void drawLine(LineSegment ln, int TYPE_3D){
        if (TYPE_3D == 1){
            ln = ln.observe(LeafMatrixView.mm);
        }
        drawLine(ln);
    }
    public static void drawLine(Point A, Point B, int TYPE_3D){
        LineSegment ln = new LineSegment(A, B);
        drawLine(ln, TYPE_3D);
    }

    public void clean(){

    }

}