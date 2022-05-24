package com.example.mypaint.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.example.mypaint.R;
import com.example.mypaint.activities.tricks.MyUI;
import com.example.mypaint.leafgeo.Matrix4;
import com.example.mypaint.leafgeo.MyHouse;
import com.example.mypaint.leafgeo.LeafMatrixView;
import com.example.mypaint.leafgeo.LineSegment;
import com.example.mypaint.view.DrawView1_2;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Paint2Activity extends AppCompatActivity {

    private static final String TAG = "Paint2";

    public DrawView1_2 myView;
    public Paint2Activity m_context;
    public ArrayList<LineSegment> lns3D;
    ArrayList<LineSegment> temp;
    private float angle = 1;
    private boolean autoRotate = true;
    private int refreshRate;
    private Matrix4 rotateY;
    Thread rotateThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint2);

        initMatrixView();
        MyHouse.initHouse();
        lns3D = new ArrayList<>();

        m_context = this;
        this.myView = findViewById(R.id.draw_view_2);
        myView.axisLength = 1000;

        // ==================== 事件监听 ====================
        CheckBox auto_rotate = findViewById(R.id.auto_rotate);

        /* =================== 绘制房子部分 ===================*/
        ArrayList<LineSegment> houseLns = MyHouse.getRawHouseLns();

        rotateY = MyHouse.genRotateHouseZ(angle);
        temp = MyHouse.rotateHouse(houseLns, rotateY);
        lns3D.clear();
        lns3D.addAll(temp);
        myView.lns3D = lns3D;
        myView.postInvalidate();

        refreshRate = 100;
        rotateThread = new Thread("refresh"){
            @Override
            public void run(){
                while (autoRotate) {
                    if (Thread.currentThread().isInterrupted()){
                        break;
                    }
                    //Matrix4 rotateY = MyHouse.genRotateHouseZ(angle);
                    temp = MyHouse.rotateHouse(temp, rotateY);
                    lns3D.clear();
                    lns3D.addAll(temp);
                    myView.lns3D = lns3D;
                    myView.postInvalidate();
                    try {
                        Thread.sleep(refreshRate);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        };

        rotateThread.start();

        Snackbar.make(m_context, myView, "刷新间隔：" + String.valueOf(refreshRate) + " 毫秒", Snackbar.LENGTH_SHORT).show();

        // ===================== 事件监听 ===========================
        auto_rotate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                m_context.autoRotate = isChecked;
                if(isChecked){
                    rotateThread.start();
                }else {
                }
            }
        });

        SeekBar refresh_rate = findViewById(R.id.refresh_rate);
        refresh_rate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                refreshRate = 20 + progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Snackbar.make(m_context, myView, "刷新间隔：" + String.valueOf(refreshRate) + " 毫秒", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        clearAll();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        clearAll();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearAll();
    }

    public void initMatrixView(){
        float[] screenSize = MyUI.getScreenSize(this);
        LeafMatrixView.initDevice(screenSize[0], screenSize[1]);
        LeafMatrixView.initVOE();
    }

    public void clearAll(){
        rotateThread.interrupt();
        myView.setVisibility(View.INVISIBLE);
    }
}