package com.example.mypaint.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.mypaint.leafdraw.LeafDrawer;
import com.example.mypaint.leafgeo.HouseTest;
import com.example.mypaint.leafgeo.LineSegment;
import com.example.mypaint.leafgeo.Point;

import java.util.ArrayList;

public class DrawView extends View {
    private static final String TAG = "DrawView";

    private LeafDrawer drawer = null;
    public ArrayList<LineSegment> lns;
    public ArrayList<Point> pts;

    public DrawView(Context context, AttributeSet attrs){
        super(context, attrs);

        this.lns = new ArrayList<LineSegment>();
        this.pts = new ArrayList<Point>();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //初始化画师
        if(drawer == null){
            drawer = new LeafDrawer(canvas);
            Log.i(TAG, "onDraw: Init a LeafDrawer");
        }

        //绘制
        if(!lns.isEmpty()){
            drawer.drawLineSet(lns);
        }
        if(!pts.isEmpty()){
            drawer.drawPointSet(pts);
        }

    }

}
