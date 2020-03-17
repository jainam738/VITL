package com.appleto.Vitl.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPlanResponse {

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

        @SerializedName("plan_id")
        @Expose
        private String planId;
        @SerializedName("plan_name")
        @Expose
        private String planName;
        @SerializedName("plan_price")
        @Expose
        private String planPrice;
        @SerializedName("plan_description")
        @Expose
        private String planDescription;
        @SerializedName("valid_day")
        @Expose
        private String validDay;
        @SerializedName("is_active")
        @Expose
        private String isActive;
        @SerializedName("is_delete")
        @Expose
        private String isDelete;

        public String getPlanId() {
            return planId;
        }

        public void setPlanId(String planId) {
            this.planId = planId;
        }

        public String getPlanName() {
            return planName;
        }

        public void setPlanName(String planName) {
            this.planName = planName;
        }

        public String getPlanPrice() {
            return planPrice;
        }

        public void setPlanPrice(String planPrice) {
            this.planPrice = planPrice;
        }

        public String getPlanDescription() {
            return planDescription;
        }

        public void setPlanDescription(String planDescription) {
            this.planDescription = planDescription;
        }

        public String getValidDay() {
            return validDay;
        }

        public void setValidDay(String validDay) {
            this.validDay = validDay;
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

    }

}
