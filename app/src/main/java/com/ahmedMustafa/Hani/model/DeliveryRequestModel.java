package com.ahmedMustafa.Hani.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryRequestModel {


    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("request_info")
    @Expose
    private RequestInfo requestInfo;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }
    public class RequestInfo {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("agent_id")
        @Expose
        private Integer agentId;
        @SerializedName("agent_image")
        @Expose
        private String agentImage;
        @SerializedName("agent_name")
        @Expose
        private String agentName;
        @SerializedName("agent_lat")
        @Expose
        private String agentLat;
        @SerializedName("agent_lng")
        @Expose
        private String agentLng;
        @SerializedName("total_price")
        @Expose
        private String totalPrice;
        @SerializedName("order_id")
        @Expose
        private Integer orderId;
        @SerializedName("confirmed")
        @Expose
        private Integer confirmed;
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

        public Integer getAgentId() {
            return agentId;
        }

        public void setAgentId(Integer agentId) {
            this.agentId = agentId;
        }

        public String getAgentImage() {
            return agentImage;
        }

        public void setAgentImage(String agentImage) {
            this.agentImage = agentImage;
        }

        public String getAgentName() {
            return agentName;
        }

        public void setAgentName(String agentName) {
            this.agentName = agentName;
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

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
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

    }
}
