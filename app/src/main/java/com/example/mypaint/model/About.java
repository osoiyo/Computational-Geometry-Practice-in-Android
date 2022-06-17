package com.example.mypaint.model;

import org.litepal.crud.LitePalSupport;

public class About extends LitePalSupport {
    private String title;
    private String detail;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
