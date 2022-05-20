package com.example.mypaint.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypaint.R;
import com.example.mypaint.activities.tricks.MyUI;
import com.example.mypaint.leafgeo.GeoDecider;
import com.example.mypaint.leafgeo.LineSegment;
import com.example.mypaint.leafgeo.Point;
import com.example.mypaint.view.DrawView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Paint1Activity extends AppCompatActivity {

    private static final String TAG = "Paint1";

    public ArrayList<LineSegment> lns;
    public ArrayList<Point> pts;
    public LineSegment tempLn;
    public LineSegment ln1, ln2;
    public Point A, B, C, D;
    public float shift = 0;
    public DrawView myView;
    public Paint1Activity m_context;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint1);
        m_context = Paint1Activity.this;

        shift = MyUI.getStatusBarHeight(this);

        lns = new ArrayList<LineSegment>();
        pts = new ArrayList<Point>();
        this.myView = findViewById(R.id.draw_view_1);
        CheckBox autoFresh = findViewById(R.id.autoRefresh);
        CheckBox clearAll = findViewById(R.id.clearAll);
        CheckBox refreshOnce = findViewById(R.id.refreshOnce);

        /*============= 按钮事件处理 ===========================*/
        autoFresh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                myView.autoFresh = isChecked;
                m_context.updateView(myView);
                if(isChecked){
                    Toast.makeText(m_context, "自动刷新已开启", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(m_context, "自动刷新已关闭", Toast.LENGTH_SHORT).show();
                }
            }
        });
        clearAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                m_context.clearAll();
                clearAll.setChecked(false);
                Toast.makeText(m_context, "已清屏", Toast.LENGTH_SHORT).show();
            }
        });
        refreshOnce.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                m_context.updateView(myView);
                refreshOnce.setChecked(false);
                Toast.makeText(m_context, "已刷新", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }

    public void updateView(DrawView view){
        view.lns = this.lns;
        view.pts = this.pts;
        view.tempLn = this.tempLn;
        view.postInvalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent e){
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:

                if(A == null){
                    A = new Point(e.getX(), e.getY()-shift);
                }else {
                    C = new Point(e.getX(), e.getY()-shift);
                }
                updateView(myView);
                break;

            case MotionEvent.ACTION_MOVE:
                if (B == null){
                    tempLn = new LineSegment(A, new Point(e.getX(), e.getY()-shift));
                } else {
                    tempLn = new LineSegment(C, new Point(e.getX(), e.getY()-shift));
                }

                myView.tempLn = this.tempLn;
                updateView(myView);
                break;

            case MotionEvent.ACTION_UP:
                tempLn = null;

                if(B == null){
                    B = new Point(e.getX(), e.getY()-shift);
                    lns.add(new LineSegment(A, B));
                }else {
                    D = new Point(e.getX(), e.getY()-shift);
                    lns.add(new LineSegment(C, D));
                    checkIntersected(A, B, C, D);
                    clearABCD();
                }

                updateView(myView);
                break;

            default:
        }
        return false;
    }

    public void clearABCD(){
        A = null;
        B = null;
        C = null;
        D = null;
    }
    public void clearAll(){
        this.lns.clear();
        this.pts.clear();
        updateView(myView);
    }
    public void checkIntersected(Point A, Point B, Point C, Point D){
        Log.d(TAG, "checkIntersected: " + A.toString() + B.toString() + C.toString() + D.toString());
        if(A.isNear2D(B) || C.isNear2D(D)){
            Snackbar.make(m_context, myView, "[ #_# 别闹只能画线！ ]",Snackbar.LENGTH_SHORT).show();
        }else if(GeoDecider.is_Intersected2D(A, B, C, D)){
            Snackbar.make(m_context, myView, "[ >_< 相交啦~ ]",Snackbar.LENGTH_SHORT).show();
        } else{
            Snackbar.make(m_context, myView, "[ ^_^ 没香蕉 ]", Snackbar.LENGTH_SHORT).show();
        }
    }
}