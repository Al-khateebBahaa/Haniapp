package com.ahmedMustafa.Hani.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MResModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("stores")
    @Expose
    private List<NearbyRestaurantModel.Restaurant> stores = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<NearbyRestaurantModel.Restaurant> getStores() {
        return stores;
    }

    public void setStores(List<NearbyRestaurantModel.Restaurant> stores) {
        this.stores = stores;
    }

    public class Store {
        @SerializedName("icon")
        @Expose
        private String icon;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
    }
}
