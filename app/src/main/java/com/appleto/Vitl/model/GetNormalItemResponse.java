package com.appleto.Vitl.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetNormalItemResponse {

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

        @SerializedName("item_id")
        @Expose
        private String itemId;
        @SerializedName("vendor_id")
        @Expose
        private String vendorId;
        @SerializedName("category_id")
        @Expose
        private String categoryId;
        @SerializedName("item_name")
        @Expose
        private String itemName;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("original_price")
        @Expose
        private String originalPrice;
        @SerializedName("discount_price")
        @Expose
        private String discountPrice;
        @SerializedName("item_image")
        @Expose
        private String itemImage;
        @SerializedName("unit")
        @Expose
        private String unit;
        @SerializedName("end_date")
        @Expose
        private String endDate;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("is_active")
        @Expose
        private String isActive;
        @SerializedName("is_delete")
        @Expose
        private String isDelete;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("address2")
        @Expose
        private String address2;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("business_phone_number")
        @Expose
        private String businessPhoneNumber;
        @SerializedName("web_link")
        @Expose
        private String webLink;
        @SerializedName("open_time")
        @Expose
        private String openTime;
        @SerializedName("close_time")
        @Expose
        private String closeTime;
        @SerializedName("is_rating")
        @Expose
        private Integer isRating;
        @SerializedName("total_rating")
        @Expose
        private Integer totalRating;

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getVendorId() {
            return vendorId;
        }

        public void setVendorId(String vendorId) {
            this.vendorId = vendorId;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }

        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getItemImage() {
            return itemImage;
        }

        public void setItemImage(String itemImage) {
            this.itemImage = itemImage;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getBusinessPhoneNumber() {
            return businessPhoneNumber;
        }

        public void setBusinessPhoneNumber(String businessPhoneNumber) {
            this.businessPhoneNumber = businessPhoneNumber;
        }

        public String getWebLink() {
            return webLink;
        }

        public void setWebLink(String webLink) {
            this.webLink = webLink;
        }

        public String getOpenTime() {
            return openTime;
        }

        public void setOpenTime(String openTime) {
            this.openTime = openTime;
        }

        public String getCloseTime() {
            return closeTime;
        }

        public void setCloseTime(String closeTime) {
            this.closeTime = closeTime;
        }

        public Integer getIsRating() {
            return isRating;
        }

        public void setIsRating(Integer isRating) {
            this.isRating = isRating;
        }

        public Integer getTotalRating() {
            return totalRating;
        }

        public void setTotalRating(Integer totalRating) {
            this.totalRating = totalRating;
        }

    }
}