package com.appleto.Vitl.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetVendorProfileResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

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
        @SerializedName("fcm_token")
        @Expose
        private String fcmToken;
        @SerializedName("is_delete")
        @Expose
        private String isDelete;

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

    }
}