package com.example.mypaint.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mypaint.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ArrayList<Task> tasks = null;
    private ListView tasks_LV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent("Paint1");
//        startActivity(intent);

        initialize();

        // 为 ListView 配置 ArrayAdapter
        tasks_LV = (ListView) findViewById(R.id.task_list);
        TaskAdapter adapter = new TaskAdapter(this, R.layout.item_task, tasks);
        tasks_LV.setAdapter(adapter);

        tasks_LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(tasks.get(position).getActivityName());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initialize(){
        tasks = new ArrayList<>();
        tasks.add(new Task("1. 线段相交性的判别", "Paint1"));
        tasks.add(new Task("2. 三维房子绘制（线框）", "Paint2"));
        tasks.add(new Task("3. 点在多边形内外的判别", "Paint3"));
    }
}