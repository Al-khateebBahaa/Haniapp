package com.ahmedMustafa.Hani.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyOrderModel {


    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("active_orders")
    @Expose
    private List<Order> activeOrders = null;
    @SerializedName("finished_orders")
    @Expose
    private List<Order> finishedOrders = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Order> getActiveOrders() {
        return activeOrders;
    }

    public void setActiveOrders(List<Order> activeOrders) {
        this.activeOrders = activeOrders;
    }

    public List<Order> getFinishedOrders() {
        return finishedOrders;
    }

    public void setFinishedOrders(List<Order> finishedOrders) {
        this.finishedOrders = finishedOrders;
    }

    public class Order {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("restaurant_name")
        @Expose
        private String restaurantName;
        @SerializedName("delivery_duration")
        @Expose
        private Integer deliveryDuration;
        @SerializedName("order_details")
        @Expose
        private String orderDetails;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("agent_id")
        @Expose
        private Integer agentId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("image")
        @Expose
        private String image;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getRestaurantName() {
            return restaurantName;
        }

        public void setRestaurantName(String restaurantName) {
            this.restaurantName = restaurantName;
        }

        public Integer getDeliveryDuration() {
            return deliveryDuration;
        }

        public void setDeliveryDuration(Integer deliveryDuration) {
            this.deliveryDuration = deliveryDuration;
        }

        public String getOrderDetails() {
            return orderDetails;
        }

        public void setOrderDetails(String orderDetails) {
            this.orderDetails = orderDetails;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getAgentId() {
            return agentId;
        }

        public void setAgentId(Integer agentId) {
            this.agentId = agentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }
}
