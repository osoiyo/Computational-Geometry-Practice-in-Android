package com.example.mypaint.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mypaint.R;
import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter {

    private Activity m_context = null;
    private int m_resouceId;
    private ArrayList<Task> m_tasks;

    public TaskAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<Task> objects) {
        super(context, resource, objects);

        m_context = context;
        m_resouceId = resource;
        m_tasks = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View item_view;
        LayoutInflater inflater = m_context.getLayoutInflater();

        // 初始化 item_view
        if (convertView !=null){
            item_view = convertView;
        } else {
            item_view = inflater.inflate(m_resouceId, null);
        }

        // 获取 item
        Task item = getItem(position);
        assert item != null;

        // 用 item 设置 item_view 的值
        TextView task_title = (TextView) item_view.findViewById(R.id.task_title);
        ImageView task_img = (ImageView) item_view.findViewById(R.id.task_img);

        task_title.setText(item.getName());
        task_img.setImageResource(R.drawable.ic_launcher_background);

        return item_view;
    }

    @Nullable
    @Override
    public Task getItem(int position) {
        return m_tasks.get(position);
    }
}
