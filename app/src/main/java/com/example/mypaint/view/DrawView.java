package com.example.mypaint.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.example.mypaint.leafdraw.LeafDraw;
import com.example.mypaint.leafgeo.LineSegment;
import com.example.mypaint.leafgeo.Matrix4;
import com.example.mypaint.leafgeo.Point;
import com.example.mypaint.pointset.HouseTest;

public class DrawView extends View {
    private static final String TAG = "DrawView";

    public DrawView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        LeafDraw.setCanvas(canvas);

        Point A = new Point(1, 1);
        Point B = new Point(300, 500);
        LineSegment ln = new LineSegment(A, B);

        Point pt1 = new Point(0, 0, 0);
        Point pt2 = new Point(0, 400, 0);
        Point pt3 = new Point(400, 400, 0);
        Point pt4 = new Point(400, 0, 0);

        for (LineSegment h_ln: HouseTest.house_ln
             ) {
            LeafDraw.drawLine(h_ln);
        }

        //Path myPath = new Path();
        //myPath.moveTo(100, 200);
        //myPath.lineTo(300, 500);
        //myPath.lineTo(600, 700);
        //canvas.drawPath(myPath, paint);



    }

}
