package com.appleto.Vitl.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetOrderListResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("order_id")
        @Expose
        private String orderId;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("vendor_id")
        @Expose
        private String vendorId;
        @SerializedName("total_amount")
        @Expose
        private String totalAmount;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("profile_image")
        @Expose
        private String profileImage;
        @SerializedName("vendor_name")
        @Expose
        private String vendorName;
        @SerializedName("vendor_image")
        @Expose
        private String vendorImage;
        @SerializedName("order_detail")
        @Expose
        private List<OrderDetail> orderDetail = null;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getVendorId() {
            return vendorId;
        }

        public void setVendorId(String vendorId) {
            this.vendorId = vendorId;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getVendorName() {
            return vendorName;
        }

        public void setVendorName(String vendorName) {
            this.vendorName = vendorName;
        }

        public String getVendorImage() {
            return vendorImage;
        }

        public void setVendorImage(String vendorImage) {
            this.vendorImage = vendorImage;
        }

        public List<OrderDetail> getOrderDetail() {
            return orderDetail;
        }

        public void setOrderDetail(List<OrderDetail> orderDetail) {
            this.orderDetail = orderDetail;
        }

        public class OrderDetail {

            @SerializedName("order_detail_id")
            @Expose
            private String orderDetailId;
            @SerializedName("item_id")
            @Expose
            private String itemId;
            @SerializedName("qty")
            @Expose
            private String qty;
            @SerializedName("amount")
            @Expose
            private String amount;
            @SerializedName("order_id")
            @Expose
            private String orderId;
            @SerializedName("item_name")
            @Expose
            private String itemName;
            @SerializedName("item_image")
            @Expose
            private String itemImage;

            public String getOrderDetailId() {
                return orderDetailId;
            }

            public void setOrderDetailId(String orderDetailId) {
                this.orderDetailId = orderDetailId;
            }

            public String getItemId() {
                return itemId;
            }

            public void setItemId(String itemId) {
                this.itemId = itemId;
            }

            public String getQty() {
                return qty;
            }

            public void setQty(String qty) {
                this.qty = qty;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public String getItemImage() {
                return itemImage;
            }

            public void setItemImage(String itemImage) {
                this.itemImage = itemImage;
            }

        }
    }
}
