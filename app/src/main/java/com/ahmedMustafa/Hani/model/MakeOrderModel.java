package com.ahmedMustafa.Hani.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MakeOrderModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("order")
    @Expose
    private Order order;
    @SerializedName("restaurant_agents")
    @Expose
    private List<RestaurantAgent> restaurantAgents = null;
    @SerializedName("errors")
    @Expose
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<RestaurantAgent> getRestaurantAgents() {
        return restaurantAgents;
    }

    public void setRestaurantAgents(List<RestaurantAgent> restaurantAgents) {
        this.restaurantAgents = restaurantAgents;
    }

    public class Order {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("restaurant_id")
        @Expose
        private String restaurantId;
        @SerializedName("restaurant_name")
        @Expose
        private String restaurantName;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("order_details")
        @Expose
        private String orderDetails;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("agent_id")
        @Expose
        private Integer agentId;
        @SerializedName("agent_lat")
        @Expose
        private String agentLat;
        @SerializedName("agent_lng")
        @Expose
        private String agentLng;
        @SerializedName("delivery_duration")
        @Expose
        private Integer deliveryDuration;
        @SerializedName("delivery_fee")
        @Expose
        private Integer deliveryFee;
        @SerializedName("order_price")
        @Expose
        private Integer orderPrice;
        @SerializedName("total_price")
        @Expose
        private Integer totalPrice;
        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("lng")
        @Expose
        private String lng;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("res_lat")
        @Expose
        private String resLat;
        @SerializedName("res_lng")
        @Expose
        private String resLng;
        @SerializedName("res_address")
        @Expose
        private String resAddress;
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

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
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

        public Integer getDeliveryDuration() {
            return deliveryDuration;
        }

        public void setDeliveryDuration(Integer deliveryDuration) {
            this.deliveryDuration = deliveryDuration;
        }

        public Integer getDeliveryFee() {
            return deliveryFee;
        }

        public void setDeliveryFee(Integer deliveryFee) {
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

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getResLat() {
            return resLat;
        }

        public void setResLat(String resLat) {
            this.resLat = resLat;
        }

        public String getResLng() {
            return resLng;
        }

        public void setResLng(String resLng) {
            this.resLng = resLng;
        }

        public String getResAddress() {
            return resAddress;
        }

        public void setResAddress(String resAddress) {
            this.resAddress = resAddress;
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

    public class RestaurantAgent {

        @SerializedName("id")
        @Expose
        private Integer id;
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
        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("lng")
        @Expose
        private String lng;
        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("_token")
        @Expose
        private String token;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

    }
}
