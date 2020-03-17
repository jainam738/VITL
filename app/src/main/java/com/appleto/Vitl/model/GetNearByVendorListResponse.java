package com.appleto.Vitl.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetNearByVendorListResponse {

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

        @SerializedName("vendor_id")
        @Expose
        private String vendorId;
        @SerializedName("plan_id")
        @Expose
        private String planId;
        @SerializedName("company_name")
        @Expose
        private String companyName;
        @SerializedName("business_name")
        @Expose
        private String businessName;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("address2")
        @Expose
        private String address2;
        @SerializedName("suburb")
        @Expose
        private String suburb;
        @SerializedName("social_link")
        @Expose
        private String socialLink;
        @SerializedName("business_phone_number")
        @Expose
        private String businessPhoneNumber;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("pincode")
        @Expose
        private String pincode;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("latitude2")
        @Expose
        private String latitude2;
        @SerializedName("longitude2")
        @Expose
        private String longitude2;
        @SerializedName("open_time")
        @Expose
        private String openTime;
        @SerializedName("close_time")
        @Expose
        private String closeTime;
        @SerializedName("vendor_image")
        @Expose
        private String vendorImage;
        @SerializedName("plan_end_date")
        @Expose
        private String planEndDate;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("web_link")
        @Expose
        private String webLink;
        @SerializedName("fcm_token")
        @Expose
        private String fcmToken;
        @SerializedName("is_delete")
        @Expose
        private String isDelete;
        @SerializedName("distance")
        @Expose
        private String distance;
        @SerializedName("is_favourite")
        @Expose
        private Integer isFavourite;
        @SerializedName("category_name")
        @Expose
        private String categoryName;
        @SerializedName("total_rating")
        @Expose
        private Integer totalRating;
        @SerializedName("total_star")
        @Expose
        private String totalStar;

        public String getVendorId() {
            return vendorId;
        }

        public void setVendorId(String vendorId) {
            this.vendorId = vendorId;
        }

        public String getPlanId() {
            return planId;
        }

        public void setPlanId(String planId) {
            this.planId = planId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
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

        public String getSuburb() {
            return suburb;
        }

        public void setSuburb(String suburb) {
            this.suburb = suburb;
        }

        public String getSocialLink() {
            return socialLink;
        }

        public void setSocialLink(String socialLink) {
            this.socialLink = socialLink;
        }

        public String getBusinessPhoneNumber() {
            return businessPhoneNumber;
        }

        public void setBusinessPhoneNumber(String businessPhoneNumber) {
            this.businessPhoneNumber = businessPhoneNumber;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
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

        public String getLatitude2() {
            return latitude2;
        }

        public void setLatitude2(String latitude2) {
            this.latitude2 = latitude2;
        }

        public String getLongitude2() {
            return longitude2;
        }

        public void setLongitude2(String longitude2) {
            this.longitude2 = longitude2;
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

        public String getVendorImage() {
            return vendorImage;
        }

        public void setVendorImage(String vendorImage) {
            this.vendorImage = vendorImage;
        }

        public String getPlanEndDate() {
            return planEndDate;
        }

        public void setPlanEndDate(String planEndDate) {
            this.planEndDate = planEndDate;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getWebLink() {
            return webLink;
        }

        public void setWebLink(String webLink) {
            this.webLink = webLink;
        }

        public String getFcmToken() {
            return fcmToken;
        }

        public void setFcmToken(String fcmToken) {
            this.fcmToken = fcmToken;
        }

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public Integer getIsFavourite() {
            return isFavourite;
        }

        public void setIsFavourite(Integer isFavourite) {
            this.isFavourite = isFavourite;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public Integer getTotalRating() {
            return totalRating;
        }

        public void setTotalRating(Integer totalRating) {
            this.totalRating = totalRating;
        }

        public String getTotalStar() {
            return totalStar;
        }

        public void setTotalStar(String totalStar) {
            this.totalStar = totalStar;
        }

    }
}