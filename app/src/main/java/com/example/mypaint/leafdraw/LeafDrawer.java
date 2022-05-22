package com.example.mypaint.leafdraw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.Log;

import com.example.mypaint.leafgeo.LeafMatrixView;
import com.example.mypaint.leafgeo.LineSegment;
import com.example.mypaint.leafgeo.Matrix4;
import com.example.mypaint.leafgeo.Point;

import java.util.ArrayList;

public class LeafDrawer {
    private static final String TAG = "LeafDraw";
    public static final int TYPE_2D = 0;
    public static final int TYPE_3D = 1;

    private final Paint linePaint;// = setPaint(Color.BLACK, Paint.Style.STROKE, 5);
    private final Paint pointPaint;// = setPaint(Color.RED, Paint.Style.FILL, 2);
    private final Canvas canvas;

    private Paint createPaint(int color,Paint.Style stroke, int width){
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(stroke);
        paint.setStrokeWidth(width);
        return paint;
    }
    private void setLinePaintColor(int color){
        linePaint.setColor(color);
    }
    private void restoreLinePaintColor(){
        linePaint.setColor(Color.BLACK);
    }

    public LeafDrawer(Canvas canvas){
        this.canvas = canvas;
        this.linePaint = createPaint(Color.BLACK, Paint.Style.STROKE, 5);
        this.pointPaint = createPaint(Color.RED, Paint.Style.FILL, 2);
    }

    public void drawLine(LineSegment ln){
        canvas.drawLine(ln.A.x, ln.A.y, ln.B.x, ln.B.y, linePaint);
        drawPoint(ln.A);
        drawPoint(ln.B);
//        Log.d(TAG, "drawLine: " + ln.toString());
    }

    public void drawLine(Point A, Point B){
        LineSegment ln = new LineSegment(A, B);
        drawLine(ln);
    }

    public void drawPoint(Point pt){
        canvas.drawCircle(pt.x, pt.y, 5, pointPaint);
//        Log.d(TAG, "drawPoint: " + pt.toString());
    }

    public void drawLineSet(ArrayList<LineSegment> lns, int TYPE_3D){
        if(TYPE_3D != 1) {
            for (LineSegment ln : lns) {
                drawLine(ln);
            }
        }
        else{
            for (LineSegment ln : lns) {
                drawLine(ln, TYPE_3D);
            }
        }
    }
    public void drawPointSet(ArrayList<Point> pts){
        for (Point pt: pts) {
            drawPoint(pt);
        }
    }

    public void drawPoint(Point pt, int TYPE_3D){
        if(TYPE_3D == 1){
            pt = pt.observe(LeafMatrixView.mm);
        }
        drawPoint(pt);
    }
    public void drawLine(LineSegment ln, int TYPE_3D){
        if (TYPE_3D == 1){
            ln = ln.observe(LeafMatrixView.mm);
        }
        drawLine(ln);
    }
    public void drawLine(Point A, Point B, int TYPE_3D){
        LineSegment ln = new LineSegment(A, B);
        drawLine(ln, TYPE_3D);
    }

    public void drawAxis3D(float length){
        Point origin = new Point(0, 0, 0);
        LineSegment axisX = new LineSegment(origin, new Point(length, 0, 0));
        LineSegment axisY = new LineSegment(origin, new Point(0, length, 0));
        LineSegment axisZ = new LineSegment(origin, new Point(0, 0, length));
        setLinePaintColor(Color.RED);
        drawLine(axisX, TYPE_3D);
        setLinePaintColor(Color.GREEN);
        drawLine(axisY, TYPE_3D);
        setLinePaintColor(Color.BLUE);
        drawLine(axisZ, TYPE_3D);
        restoreLinePaintColor();
    }

    public void clear(){
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }
}