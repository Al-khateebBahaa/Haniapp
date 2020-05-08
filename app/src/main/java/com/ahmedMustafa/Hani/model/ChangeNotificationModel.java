package com.ahmedMustafa.Hani.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeNotificationModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("user")
    @Expose
    private UserInfoModel.User user;
    @SerializedName("msg")
    @Expose
    private String msg;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UserInfoModel.User getUser() {
        return user;
    }

    public void setUser(UserInfoModel.User user) {
        this.user = user;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
