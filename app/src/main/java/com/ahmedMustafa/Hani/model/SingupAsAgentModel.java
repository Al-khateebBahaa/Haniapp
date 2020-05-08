package com.ahmedMustafa.Hani.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingupAsAgentModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("agent_info")
    @Expose
    private AgentInfo agentInfo;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public AgentInfo getAgentInfo() {
        return agentInfo;
    }

    public void setAgentInfo(AgentInfo agentInfo) {
        this.agentInfo = agentInfo;
    }


    public class AgentInfo {

        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("restaurant_id")
        @Expose
        private String restaurantId;
        @SerializedName("restaurant_name")
        @Expose
        private String restaurantName;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getRestaurantId() {
            return restaurantId;
        }

        public void setRestaurantId(String restaurantId) {
            this.restaurantId = restaurantId;
        }

        public String getRestaurantName() {
            return restaurantName;
        }

        public void setRestaurantName(String restaurantName) {
            this.restaurantName = restaurantName;
        }

    }
}
