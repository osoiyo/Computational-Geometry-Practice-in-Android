package com.example.mypaint.leafdraw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.ArrayAdapter;

import androidx.annotation.ColorRes;

import com.example.mypaint.leafgeo.LeafMatrixView;
import com.example.mypaint.leafgeo.LineSegment;
import com.example.mypaint.leafgeo.Matrix4;
import com.example.mypaint.leafgeo.Point;
import com.example.mypaint.leafgeo.Polygon;
import com.example.mypaint.leafgeo.Triangle;

import java.util.ArrayList;

public class LeafDrawer {
    private static final String TAG = "LeafDraw";
    public static final int TYPE_2D = 0;
    public static final int TYPE_3D = 1;

    private  Paint linePaint;// = setPaint(Color.BLACK, Paint.Style.STROKE, 5);
    private  Paint pointPaint;// = setPaint(Color.RED, Paint.Style.FILL, 2);
    private  Canvas canvas;
    private SurfaceHolder surfaceHolder;

    public Paint getLinePaint() {
        return linePaint;
    }

    public Paint getPointPaint() {
        return pointPaint;
    }

    public Canvas getCanvas() {
        return canvas;
    }
    public void setCanvas(Canvas canvas){
        this.canvas = canvas;
    }

    private Paint createPaint(int color,Paint.Style stroke, int width){
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(stroke);
        paint.setStrokeWidth(width);
        return paint;
    }
    public void setLinePaintColor(int color){
        linePaint.setColor(color);
    }
    public void restoreLinePaintColor(){ linePaint.setColor(Color.BLACK); }
    public void setPointPaintColor(int color){
        pointPaint.setColor(color);
    }
    public void restorePointPaintColor(){ pointPaint.setColor(Color.RED); }

    public LeafDrawer(){
        this.linePaint = createPaint(Color.BLACK, Paint.Style.STROKE, 5);
        this.pointPaint = createPaint(Color.RED, Paint.Style.FILL, 2);
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

    public void drawPolygon(Polygon polygon, boolean needCenter){
        if (polygon.getSize() < 2){
            drawPoint(polygon.getVertex(0));
        } else {
            drawLineSet(polygon.toLineSet(), TYPE_2D);
//            drawLine(polygon.getLastPoint(), polygon.getFirstPoint()); // Close Polygon
            if (needCenter){
                setPointPaintColor(Color.GREEN);
                drawPoint(polygon.getCentroid());
                restorePointPaintColor();
            }
        }
    }

    public void drawTriangle(Triangle triangle){
        drawLineSet(triangle.toLineSet(), TYPE_2D);
    }
    public void drawTriangleSet(ArrayList<Triangle> triangles){
        setLinePaintColor(Color.LTGRAY);
        for (Triangle triangle : triangles) {
            drawTriangle(triangle);
        }
        restoreLinePaintColor();
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