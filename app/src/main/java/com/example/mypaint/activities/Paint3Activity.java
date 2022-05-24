package com.example.mypaint.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypaint.R;
import com.example.mypaint.view.DrawView3;
import com.google.android.material.snackbar.Snackbar;

public class Paint3Activity extends AppCompatActivity {

    private static final String TAG = "Paint3Activity";

    private TextView is_convex_tv;
    private TextView is_simple_tv;
    private TextView direction_tv;
    private TextView area_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint3);
        Context m_context = this;

        TextView tipTextView = (TextView) findViewById(R.id.tool_tips);
        DrawView3 drawView3 = (DrawView3) findViewById(R.id.draw_view_3);
        RadioGroup mode_switcher = (RadioGroup) findViewById(R.id.mode_switcher);
        mode_switcher.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.mode_draw:;
                        tipTextView.setText(R.string.draw_mode);
                        drawView3.clearPts();
                        drawView3.switchMode("draw");
                        break;
                    case R.id.mode_point:
                        if (!drawView3.hasRightPolygon()){
                            Toast.makeText(m_context, "请先绘制一个多边形", Toast.LENGTH_SHORT).show();
                        }
                        tipTextView.setText(R.string.point_mode);
                        drawView3.switchMode("point");
                        break;
                    case R.id.mode_triangulation:
                        tipTextView.setText(R.string.triangulation_mode);
                        drawView3.clearPts();
                        drawView3.genTriangulation();
                        drawView3.switchMode("triangulation");
                        break;
                    case R.id.mode_clear:
                        Toast.makeText(m_context, "已清屏", Toast.LENGTH_SHORT).show();
                        clearText();
                        drawView3.clearPts();
                        drawView3.clearPolygon();
                        drawView3.switchMode("clear");
                        mode_switcher.check(R.id.mode_draw);
                        break;
                    default:
                }
            }
        });

        // 多边形计算结果的 TextView
        is_convex_tv = (TextView) findViewById(R.id.is_convex_text);
        is_simple_tv = (TextView) findViewById(R.id.is_simple_text);
        area_tv = (TextView) findViewById(R.id.area_text);
        direction_tv = (TextView) findViewById(R.id.direction_text);
    }

    public void setText(boolean is_convex, boolean is_simple, float area2, boolean is_ccw){
        is_convex_tv.setText( is_convex ? "YES" : "NO");
        is_simple_tv.setText( is_simple ? "YES" : "NO");
        direction_tv.setText( is_ccw ? "CW" : "CCW");
        area_tv.setText(String.valueOf((int) Math.abs(area2/2.0f)));
    }
    public void clearText(){
        is_convex_tv.setText("...");
        is_simple_tv.setText("...");
        direction_tv.setText("...");
        area_tv.setText("...");
    }

}