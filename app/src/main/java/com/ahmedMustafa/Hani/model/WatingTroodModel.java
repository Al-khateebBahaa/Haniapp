package com.ahmedMustafa.Hani.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WatingTroodModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("my_orders")
    @Expose
    private List<MyOrder> myOrders = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<MyOrder> getMyOrders() {
        return myOrders;
    }

    public void setMyOrders(List<MyOrder> myOrders) {
        this.myOrders = myOrders;
    }


    public class MyOrder {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("user_id_image")
        @Expose
        private String userIdImage;
        @SerializedName("order_details")
        @Expose
        private String orderDetails;
        @SerializedName("company_id")
        @Expose
        private Integer companyId;
        @SerializedName("agent_id")
        @Expose
        private Integer agentId;
        @SerializedName("total_price")
        @Expose
        private Integer totalPrice;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("company_name")
        @Expose
        private String companyName;
        @SerializedName("company_lat")
        @Expose
        private String companyLat;
        @SerializedName("company_lng")
        @Expose
        private String companyLng;
        @SerializedName("from_lat")
        @Expose
        private String fromLat;
        @SerializedName("from_lng")
        @Expose
        private String fromLng;
        @SerializedName("from_address")
        @Expose
        private String fromAddress;
        @SerializedName("to_lat")
        @Expose
        private String toLat;
        @SerializedName("to_lng")
        @Expose
        private String toLng;
        @SerializedName("to_address")
        @Expose
        private String toAddress;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getUserIdImage() {
            return userIdImage;
        }

        public void setUserIdImage(String userIdImage) {
            this.userIdImage = userIdImage;
        }

        public String getOrderDetails() {
            return orderDetails;
        }

        public void setOrderDetails(String orderDetails) {
            this.orderDetails = orderDetails;
        }

        public Integer getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Integer companyId) {
            this.companyId = companyId;
        }

        public Integer getAgentId() {
            return agentId;
        }

        public void setAgentId(Integer agentId) {
            this.agentId = agentId;
        }

        public Integer getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Integer totalPrice) {
            this.totalPrice = totalPrice;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCompanyLat() {
            return companyLat;
        }

        public void setCompanyLat(String companyLat) {
            this.companyLat = companyLat;
        }

        public String getCompanyLng() {
            return companyLng;
        }

        public void setCompanyLng(String companyLng) {
            this.companyLng = companyLng;
        }

        public String getFromLat() {
            return fromLat;
        }

        public void setFromLat(String fromLat) {
            this.fromLat = fromLat;
        }

        public String getFromLng() {
            return fromLng;
        }

        public void setFromLng(String fromLng) {
            this.fromLng = fromLng;
        }

        public String getFromAddress() {
            return fromAddress;
        }

        public void setFromAddress(String fromAddress) {
            this.fromAddress = fromAddress;
        }

        public String getToLat() {
            return toLat;
        }

        public void setToLat(String toLat) {
            this.toLat = toLat;
        }

        public String getToLng() {
            return toLng;
        }

        public void setToLng(String toLng) {
            this.toLng = toLng;
        }

        public String getToAddress() {
            return toAddress;
        }

        public void setToAddress(String toAddress) {
            this.toAddress = toAddress;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
}
