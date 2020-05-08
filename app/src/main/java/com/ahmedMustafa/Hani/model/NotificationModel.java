package com.ahmedMustafa.Hani.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class NotificationModel {


    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("notifications")
    @Expose
    private List<Notification> notifications = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public class Notification {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("image_path")
        @Expose
        private String imagePath;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("to_user_id")
        @Expose
        private Integer toUserId;
        @SerializedName("from_user_id")
        @Expose
        private Integer fromUserId;
        @SerializedName("order_id")
        @Expose
        private Integer orderId;
        @SerializedName("delivery_request_id")
        @Expose
        private Integer deliveryRequestId;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("seen")
        @Expose
        private Integer seen;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("order_status")
        @Expose
        private Integer order_status;

        public Integer getOrder_status() {
            return order_status;
        }

        public void setOrder_status(Integer order_status) {
            this.order_status = order_status;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getToUserId() {
            return toUserId;
        }

        public void setToUserId(Integer toUserId) {
            this.toUserId = toUserId;
        }

        public Integer getFromUserId() {
            return fromUserId;
        }

        public void setFromUserId(Integer fromUserId) {
            this.fromUserId = fromUserId;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public Integer getDeliveryRequestId() {
            return deliveryRequestId;
        }

        public void setDeliveryRequestId(Integer deliveryRequestId) {
            this.deliveryRequestId = deliveryRequestId;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getSeen() {
            return seen;
        }

        public void setSeen(Integer seen) {
            this.seen = seen;
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
