package com.ahmedMustafa.Hani.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutModel {
    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("about_ma5dom")
    @Expose
    private String about_ma5dom;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAbout_ma5dom() {
        return about_ma5dom;
    }

    public void setAbout_ma5dom(String about_ma5dom) {
        this.about_ma5dom = about_ma5dom;
    }
}
