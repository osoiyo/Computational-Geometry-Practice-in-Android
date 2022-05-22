package com.example.mypaint.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.mypaint.leafdraw.LeafDrawer;
import com.example.mypaint.leafgeo.LineSegment;
import com.example.mypaint.leafgeo.Point;

import java.util.ArrayList;

public class DrawView extends View {
    private static final String TAG = "DrawView";

    private LeafDrawer drawer = null;
    public ArrayList<LineSegment> lns;
    public ArrayList<Point> pts;
    public ArrayList<LineSegment> lns3D;
    public LineSegment tempLn = null;
    public boolean autoFresh = false;
    public float axisLength = 0;

    public DrawView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.lns = new ArrayList<LineSegment>();
        this.pts = new ArrayList<Point>();
        this.lns3D = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //初始化画师
        if(drawer == null){
            drawer = new LeafDrawer(canvas);
            Log.d(TAG, "onDraw: Init a LeafDrawer");
        }

        //绘制
        if(!lns.isEmpty()){
            drawer.drawLineSet(lns, LeafDrawer.TYPE_2D);
        }
        if(!pts.isEmpty()){
            drawer.drawPointSet(pts);
        }
        if(tempLn != null){
            drawer.drawLine(tempLn);
        }
        if(axisLength > 0){
            drawer.drawAxis3D(axisLength);
        }
        if(!lns3D.isEmpty()){
            drawer.drawLineSet(lns3D, LeafDrawer.TYPE_3D);
        }
        if(autoFresh){
            invalidate();
        }
    }

    public void clear(){
        drawer.clear();
    }

}
