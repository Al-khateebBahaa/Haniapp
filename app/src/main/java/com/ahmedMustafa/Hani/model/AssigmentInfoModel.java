package com.ahmedMustafa.Hani.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssigmentInfoModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("assignment_info")
    @Expose
    private AssignmentInfo assignmentInfo;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public AssignmentInfo getAssignmentInfo() {
        return assignmentInfo;
    }

    public void setAssignmentInfo(AssignmentInfo assignmentInfo) {
        this.assignmentInfo = assignmentInfo;
    }

    public class AssignmentInfo {

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
        @SerializedName("company_id")
        @Expose
        private Integer companyId;
        @SerializedName("order_id")
        @Expose
        private Integer orderId;
        @SerializedName("total_price")
        @Expose
        private Integer totalPrice;
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

        public Integer getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Integer companyId) {
            this.companyId = companyId;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public Integer getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Integer totalPrice) {
            this.totalPrice = totalPrice;
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
