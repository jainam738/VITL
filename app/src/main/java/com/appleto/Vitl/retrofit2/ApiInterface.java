package com.appleto.Vitl.retrofit2;

import com.appleto.Vitl.model.GetCategoryResponse;
import com.appleto.Vitl.model.GetGeneralSettingResponse;
import com.appleto.Vitl.model.GetLoginResponse;
import com.appleto.Vitl.model.GetNearByVendorListResponse;
import com.appleto.Vitl.model.GetNormalItemResponse;
import com.appleto.Vitl.model.GetOrderListResponse;
import com.appleto.Vitl.model.GetOtpResponse;
import com.appleto.Vitl.model.GetPlanResponse;
import com.appleto.Vitl.model.GetProfileResponse;
import com.appleto.Vitl.model.GetRatingResponse;
import com.appleto.Vitl.model.GetSpecialsItemResponse;
import com.appleto.Vitl.model.GetUserItemResponse;
import com.appleto.Vitl.model.GetVendorItemsListResponse;
import com.appleto.Vitl.model.GetVendorLoginResponse;
import com.appleto.Vitl.model.GetVendorProfileResponse;
import com.google.gson.JsonObject;


import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Simon on 11/02/2019s.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    Single<GetLoginResponse> login(@Field("mobile_no") String mobile,
                                   @Field("country_code") String countrycode,
                                   @Field("country") String country,
                                   @Field("fcm_token") String fcmtoken);


    @FormUrlEncoded
    @POST("otp_verification")
    Single<GetOtpResponse> otpverification(@Field("user_id") String userid,
                                           @Field("otp") String otp);


    @FormUrlEncoded
    @POST("get_profile")
    Single<GetProfileResponse> getprofile(@Field("user_id") String userid);


    @FormUrlEncoded
    @POST("update_profile")
    Single<JsonObject> updateprofile(@Field("user_id") String userid,
                                     @Field("name") String name,
                                     @Field("email") String email,
                                     @Field("text_tone") String texttone);


    @GET("get_plan")
    Single<GetPlanResponse> getplan();


    @GET("get_category")
    Single<GetCategoryResponse> getcategory();


    @Multipart
    @POST("add_item")
    Single<JsonObject> additem(@Part("vendor_id") RequestBody vendor_id,
                               @Part("item_name") RequestBody projecttype,
                               @Part("description") RequestBody projecttitle,
                               @Part("original_price") RequestBody projectdetails,
                               @Part("discount_price") RequestBody projectlocation,
                               @Part MultipartBody.Part item_image,
                               @Part("type") RequestBody type,
                               @Part("category_id") RequestBody categoryid,
                               @Part("unit") RequestBody unit,
                               @Part("end_date") RequestBody enddate,
                               @Part("quantity") RequestBody quantity);


    @FormUrlEncoded
    @POST("get_user_item")
    Single<GetUserItemResponse> getuseritem(@Field("vendor_id") String vendor_id);

    @Multipart
    @POST("add_vendor")
    Single<JsonObject> add_vendor(@Part("plan_id") RequestBody planid,
                                  @Part("user_id") RequestBody userid,
                                  @Part("company_name") RequestBody companyname,
                                  @Part("business_name") RequestBody businessname,
                                  @Part("address") RequestBody address,
                                  @Part("business_phone_number") RequestBody phone,
                                  @Part("country") RequestBody country,
                                  @Part("state") RequestBody state,
                                  @Part("email") RequestBody email,
                                  @Part("pincode") RequestBody pincode,
                                  @Part("latitude") RequestBody latitude,
                                  @Part("longitude") RequestBody longitude,
                                  @Part("address2") RequestBody address2,
                                  @Part("suburb") RequestBody suburb,
                                  @Part("social_link") RequestBody social_link,
                                  @Part("open_time") RequestBody open_time,
                                  @Part("close_time") RequestBody close_time,
                                  @Part MultipartBody.Part vendor_image,
                                  @Part("password") RequestBody password,
                                  @Part("latitude2") RequestBody latitude2,
                                  @Part("longitude2") RequestBody longitude2,
                                  @Part("category_id") RequestBody rb_category,
                                  @Part("refund_policy") RequestBody rb_refund_policy,
                                  @Part("paypal") RequestBody rb_paypal,
                                  @Part("web_link") RequestBody web_link);


    @FormUrlEncoded
    @POST("get_vendor_profile")
    Single<GetVendorProfileResponse> getvendorprofile(@Field("vendor_id") String vendor_id);


    @FormUrlEncoded
    @POST("get_specials_items")
    Single<GetSpecialsItemResponse> getspecialitem(@Field("user_id") String userid);


    @FormUrlEncoded
    @POST("get_rating")
    Single<GetRatingResponse> getrating(@Field("item_id") String itemid);


    @FormUrlEncoded
    @POST("add_rating")
    Single<JsonObject> addratng(@Field("user_id") String userid,
                                @Field("item_id") String itemid,
                                @Field("comment") String comment,
                                @Field("star") String star);

    @FormUrlEncoded
    @POST("general_setting")
    Single<GetGeneralSettingResponse> getgeneralsetting(@Field("vendor_id") String vendor_id);

    @FormUrlEncoded
    @POST("vendor_login")
    Single<GetVendorLoginResponse> vendor_login(@Field("email") String email,
                                                @Field("password") String password,
                                                @Field("fcm_token") String fcm_token);

    @FormUrlEncoded
    @POST("get_order")
    Single<GetOrderListResponse> get_order(@Field("id") String id,
                                           @Field("status") String status,
                                           @Field("type") String type);

    @FormUrlEncoded
    @POST("add_order")
    Single<JsonObject> add_order(@Field("user_id") String user_id,
                                 @Field("vendor_id") String vendor_id,
                                 @Field("total_amount") String total_amount,
                                 @Field("items") String items);

    @FormUrlEncoded
    @POST("get_vendor_list")
    Single<GetNearByVendorListResponse> get_vendor_list(@Field("latitude") String latitude,
                                                        @Field("longitude") String longitude,
                                                        @Field("login_user_id") String login_user_id);

    @FormUrlEncoded
    @POST("get_vendor_items")
    Single<GetVendorItemsListResponse> get_vendor_items(@Field("vendor_id") String vendor_id);

    @FormUrlEncoded
    @POST("get_category")
    Single<GetCategoryResponse> getFilterCategory(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("user_active_category")
    Single<JsonObject> userActiveCategory(@Field("user_id") String user_id,
                                          @Field("category_id") String category_id);
}
