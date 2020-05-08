package com.ahmedMustafa.Hani.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TroodsModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("companies")
    @Expose
    private List<Company> companies = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public class Company {

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
        @SerializedName("type")
        @Expose
        private Integer type;
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
        @SerializedName("verified_as_troods_agent")
        @Expose
        private Integer verifiedAsTroodsAgent;
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

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
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

        public Integer getVerifiedAsTroodsAgent() {
            return verifiedAsTroodsAgent;
        }

        public void setVerifiedAsTroodsAgent(Integer verifiedAsTroodsAgent) {
            this.verifiedAsTroodsAgent = verifiedAsTroodsAgent;
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
