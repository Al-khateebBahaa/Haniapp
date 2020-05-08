package com.ahmedMustafa.Hani.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PopupModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("order_delivery_requests")
    @Expose
    private List<OrderDeliveryRequest> orderDeliveryRequests = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<OrderDeliveryRequest> getOrderDeliveryRequests() {
        return orderDeliveryRequests;
    }

    public void setOrderDeliveryRequests(List<OrderDeliveryRequest> orderDeliveryRequests) {
        this.orderDeliveryRequests = orderDeliveryRequests;
    }

    public class OrderDeliveryRequest {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("order_id")
        @Expose
        private Integer orderId;
        @SerializedName("agent_id")
        @Expose
        private Integer agentId;
        @SerializedName("delivery_duration")
        @Expose
        private String deliveryDuration;
        @SerializedName("delivery_fee")
        @Expose
        private String deliveryFee;
        @SerializedName("order_price")
        @Expose
        private Integer orderPrice;
        @SerializedName("total_price")
        @Expose
        private Integer totalPrice;
        @SerializedName("agent_lat")
        @Expose
        private String agentLat;
        @SerializedName("agent_lng")
        @Expose
        private String agentLng;
        @SerializedName("confirmed")
        @Expose
        private Integer confirmed;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("verified")
        @Expose
        private Integer verified;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public Integer getAgentId() {
            return agentId;
        }

        public void setAgentId(Integer agentId) {
            this.agentId = agentId;
        }

        public String getDeliveryDuration() {
            return deliveryDuration;
        }

        public void setDeliveryDuration(String deliveryDuration) {
            this.deliveryDuration = deliveryDuration;
        }

        public String getDeliveryFee() {
            return deliveryFee;
        }

        public void setDeliveryFee(String deliveryFee) {
            this.deliveryFee = deliveryFee;
        }

        public Integer getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(Integer orderPrice) {
            this.orderPrice = orderPrice;
        }

        public Integer getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Integer totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getAgentLat() {
            return agentLat;
        }

        public void setAgentLat(String agentLat) {
            this.agentLat = agentLat;
        }

        public String getAgentLng() {
            return agentLng;
        }

        public void setAgentLng(String agentLng) {
            this.agentLng = agentLng;
        }

        public Integer getConfirmed() {
            return confirmed;
        }

        public void setConfirmed(Integer confirmed) {
            this.confirmed = confirmed;
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

        public Integer getVerified() {
            return verified;
        }

        public void setVerified(Integer verified) {
            this.verified = verified;
        }

    }

}
