package com.example.mypaint.activities;

public class Task {
    private int imgId;
    private String name;
    private String activityName;

    public Task(String name, String activityName) {
        this.name = name;
        this.activityName = activityName;
    }

    public Task(int imgId, String name, String activityName) {
        this.imgId = imgId;
        this.name = name;
        this.activityName = activityName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}


