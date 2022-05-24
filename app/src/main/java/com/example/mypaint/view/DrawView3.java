package com.example.mypaint.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mypaint.activities.Paint3Activity;
import com.example.mypaint.leafdraw.LeafDrawer;
import com.example.mypaint.leafgeo.LineSegment;
import com.example.mypaint.leafgeo.Point;
import com.example.mypaint.leafgeo.Polygon;
import com.example.mypaint.leafgeo.Triangle;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class DrawView3 extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private static final String TAG = "DrawView3 Polygon";

    // Initialize environment
    private LeafDrawer drawer = null;
    private SurfaceHolder m_surfaceHolder;
    private Canvas m_canvas;
    private boolean m_isDrawing;

    // Data: polygon & points need to be draw
    private Polygon polygon = null;
    private ArrayList<Point> pts_outer = null;
    private ArrayList<Point> pts_inner = null;
    private ArrayList<Triangle> triangles = null;

    // Result: Polygon properties
    Paint3Activity m_context;


    // Draw Preference
    private boolean needCenter = true;
    private boolean is_mode_draw = true;
    private boolean is_mode_point = false;
    private boolean is_mode_triangulation = false;
    private boolean is_mode_clear = false;


    public DrawView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        m_context = (Paint3Activity) getContext();

        initView();
        //initTestPolygon();
    }

    public void initView(){
        m_surfaceHolder = getHolder();
        m_surfaceHolder.addCallback(this);
        drawer = new LeafDrawer();
        setFocusable(true);
        setKeepScreenOn(true);
        setFocusableInTouchMode(true);

        polygon = new Polygon();
        pts_inner = new ArrayList<>();
        pts_outer = new ArrayList<>();
        triangles = new ArrayList<>();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        m_isDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        m_isDrawing = false;
    }

    @Override
    public void run() {
        while (m_isDrawing) {
            long start = System.currentTimeMillis();
            myDraw();
            long end = System.currentTimeMillis();
            if (end - start < 100) {
                try {
                    Thread.sleep(100 - (end - start));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressLint("ShowToast")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            if(is_mode_draw){
                polygon.addPoint(new Point(event.getX(), event.getY()));
                if (is_mode_triangulation){
                    genTriangulation();
                }
            }
            if (is_mode_point){
                Point pt = new Point(event.getX(), event.getY());
                switch (polygon.whereIsPoint(pt)){
                    case -1:
                        break;
                    case 0:
                        pts_outer.add(pt);
                        break;
                    case 1:
                        pts_inner.add(pt);
                        break;
                }
            }
        }
        return false;
    }

    public void myDraw(){
        try {
            //获得canvas对象
            m_canvas = m_surfaceHolder.lockCanvas();
            drawer.setCanvas(m_canvas);
            //绘制背景
            m_canvas.drawColor(Color.WHITE);
            //绘制

            // 只要不是三角剖分模式，都把多边形画出来。
            if(!is_mode_triangulation){
                drawer.drawPolygon(polygon, needCenter);
            }

            // 如果是判断点与多边形关系，就把点集合画出来
            if(is_mode_point){
                drawer.setPointPaintColor(Color.BLUE);
                drawer.drawPointSet(pts_inner);
                drawer.setPointPaintColor(Color.LTGRAY);
                drawer.drawPointSet(pts_outer);
                drawer.restorePointPaintColor();
            }

            // 如果是三角剖分模式，先画三角形，再画polygon
            if(is_mode_triangulation) {
                // 画三角剖分
                drawer.drawTriangleSet(triangles);
                drawer.drawPolygon(polygon, false);
            }

            // 填充计算结果
            m_context.setText(polygon.isConvex(), polygon.isSimple(), polygon.getArea2(), polygon.isCCW());

        }catch (Exception ignored){

        }finally {
            if (m_canvas != null){
                //释放canvas对象并提交画布
                m_surfaceHolder.unlockCanvasAndPost(m_canvas);
            }
        }
    }

    public void initTestPolygon(){
        Point A = new Point(10, 10);
        Point B = new Point(20, 500);
        Point C = new Point(600, 30);
        Point D = new Point(400, 400);
        polygon.addPoint(B);
        polygon.addPoint(A);
        polygon.addPoint(C);
        polygon.addPoint(D);
    }

    public void switchMode(String mode){
        switch (mode){
            case "draw":
                is_mode_draw = true;
                is_mode_point = false;
                is_mode_clear = false;
                is_mode_triangulation = false;
                break;

            case "point":
                is_mode_draw = false;
                is_mode_point = true;
                is_mode_clear = false;
                is_mode_triangulation = false;
                break;

            case "clear":
                is_mode_draw = false;
                is_mode_point = false;
                is_mode_clear = true;
                is_mode_triangulation = false;
                break;

            case "triangulation":
                is_mode_draw = true;
                is_mode_point = false;
                is_mode_clear = false;
                is_mode_triangulation = true;
                break;
        }
    }

    public void genTriangulation(){
        this.triangles.clear();
        if (polygon.isSimple()){
            this.triangles = polygon.getTriangulation();
        }else {
            Toast.makeText(getContext(), "需要简单多边形", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean hasRightPolygon(){
        return polygon.getSize() > 2;
    }
    public void clearPts(){
        pts_inner.clear();
        pts_outer.clear();
    }
    public void clearPolygon(){
        polygon.clear();
    }
}
