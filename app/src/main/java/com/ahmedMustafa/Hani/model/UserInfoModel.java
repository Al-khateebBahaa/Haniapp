package com.ahmedMustafa.Hani.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserInfoModel {

    @SerializedName("status")
    @Expose
    private Integer status ;
    @SerializedName("user")
    @Expose
    private User user = null;
    @SerializedName("errors")
    @Expose
    private String error = null;


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public class User {

        @SerializedName("type")
        @Expose
        private Integer type;
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
        @SerializedName("social_id")
        @Expose
        private String socialId;
        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("lng")
        @Expose
        private String lng;
        @SerializedName("notifications")
        @Expose
        private Integer notifications;
        @SerializedName("closeby_orders_notifications")
        @Expose
        private Integer closebyOrdersNotifications;
        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("_token")
        @Expose
        private String token;
        @SerializedName("device_type")
        @Expose
        private Integer deviceType;
        @SerializedName("verified")
        @Expose
        private Integer verified;
        @SerializedName("verified_as_heavy_shipping_agent")
        @Expose
        private Integer verifiedAsHeavyShippingAgent;
        @SerializedName("verified_as_travel_agent")
        @Expose
        private Integer verifiedAsTravelAgent;
        @SerializedName("verified_as_trader_agent")
        @Expose
        private Integer verifiedAsTraderAgent;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("rating_votes_count")
        @Expose
        private Integer ratingVotesCount;
        @SerializedName("orders_count_as_user")
        @Expose
        private Integer ordersCountAsUser;
        @SerializedName("orders_count_as_agent")
        @Expose
        private Integer ordersCountAsAgent;
        @SerializedName("rating_comments")
        @Expose
        private List<Object> ratingComments = null;
        @SerializedName("total_delivery_fee")
        @Expose
        private Integer totalDeliveryFee;
        @SerializedName("user_percentage")
        @Expose
        private Integer userPercentage;
        @SerializedName("app_percentage")
        @Expose
        private Integer appPercentage;

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

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

        public String getSocialId() {
            return socialId;
        }

        public void setSocialId(String socialId) {
            this.socialId = socialId;
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

        public Integer getNotifications() {
            return notifications;
        }

        public void setNotifications(Integer notifications) {
            this.notifications = notifications;
        }

        public Integer getClosebyOrdersNotifications() {
            return closebyOrdersNotifications;
        }

        public void setClosebyOrdersNotifications(Integer closebyOrdersNotifications) {
            this.closebyOrdersNotifications = closebyOrdersNotifications;
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

        public Integer getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(Integer deviceType) {
            this.deviceType = deviceType;
        }

        public Integer getVerified() {
            return verified;
        }

        public void setVerified(Integer verified) {
            this.verified = verified;
        }

        public Integer getVerifiedAsHeavyShippingAgent() {
            return verifiedAsHeavyShippingAgent;
        }

        public void setVerifiedAsHeavyShippingAgent(Integer verifiedAsHeavyShippingAgent) {
            this.verifiedAsHeavyShippingAgent = verifiedAsHeavyShippingAgent;
        }

        public Integer getVerifiedAsTravelAgent() {
            return verifiedAsTravelAgent;
        }

        public void setVerifiedAsTravelAgent(Integer verifiedAsTravelAgent) {
            this.verifiedAsTravelAgent = verifiedAsTravelAgent;
        }

        public Integer getVerifiedAsTraderAgent() {
            return verifiedAsTraderAgent;
        }

        public void setVerifiedAsTraderAgent(Integer verifiedAsTraderAgent) {
            this.verifiedAsTraderAgent = verifiedAsTraderAgent;
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

        public Integer getRatingVotesCount() {
            return ratingVotesCount;
        }

        public void setRatingVotesCount(Integer ratingVotesCount) {
            this.ratingVotesCount = ratingVotesCount;
        }

        public Integer getOrdersCountAsUser() {
            return ordersCountAsUser;
        }

        public void setOrdersCountAsUser(Integer ordersCountAsUser) {
            this.ordersCountAsUser = ordersCountAsUser;
        }

        public Integer getOrdersCountAsAgent() {
            return ordersCountAsAgent;
        }

        public void setOrdersCountAsAgent(Integer ordersCountAsAgent) {
            this.ordersCountAsAgent = ordersCountAsAgent;
        }

        public List<Object> getRatingComments() {
            return ratingComments;
        }

        public void setRatingComments(List<Object> ratingComments) {
            this.ratingComments = ratingComments;
        }

        public Integer getTotalDeliveryFee() {
            return totalDeliveryFee;
        }

        public void setTotalDeliveryFee(Integer totalDeliveryFee) {
            this.totalDeliveryFee = totalDeliveryFee;
        }

        public Integer getUserPercentage() {
            return userPercentage;
        }

        public void setUserPercentage(Integer userPercentage) {
            this.userPercentage = userPercentage;
        }

        public Integer getAppPercentage() {
            return appPercentage;
        }

        public void setAppPercentage(Integer appPercentage) {
            this.appPercentage = appPercentage;
        }


    }
}
