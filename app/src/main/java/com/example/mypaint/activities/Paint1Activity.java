package com.example.mypaint.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mypaint.R;
import com.example.mypaint.leafgeo.LineSegment;
import com.example.mypaint.leafgeo.Point;
import com.example.mypaint.view.DrawView;

import java.util.ArrayList;

public class Paint1Activity extends AppCompatActivity {

    private static final String TAG = "Paint1";

    private ArrayList<LineSegment> lns = new ArrayList<>();
    private ArrayList<Point> pts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint1);
        DrawView view = findViewById(R.id.draw_view_1);

        /* ============  绘制部分  ======================== */
        Point A = new Point(1, 1);
        Point B = new Point(300, 500);
        LineSegment ln = new LineSegment(A, B);
        Point pt1 = new Point(0, 0, 0);
        Point pt2 = new Point(0, 400, 0);
        Point pt3 = new Point(400, 400, 0);
        Point pt4 = new Point(400, 0, 0);
        lns.add(ln);
        pts.add(pt1);
        pts.add(pt2);
        pts.add(pt3);
        pts.add(pt4);

        /* ==============================================*/

        updateView(view);
    }

    public void updateView(DrawView view){
        view.lns = this.lns;
        view.pts = this.pts;
        view.invalidate();
    }
}