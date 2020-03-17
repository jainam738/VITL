package com.appleto.Vitl.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.appleto.Vitl.R;
import com.appleto.Vitl.fragment.MapFragment;
import com.appleto.Vitl.fragment.ReturnAndRefundPolicyActivity;
import com.appleto.Vitl.model.GetCategoryResponse;
import com.appleto.Vitl.model.GetVendorProfileResponse;
import com.appleto.Vitl.retrofit2.ApiClient;
import com.appleto.Vitl.retrofit2.ApiInterface;
import com.appleto.Vitl.utills.LocationTracker;
import com.appleto.Vitl.utills.Storage;
import com.appleto.Vitl.utills.Utils;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class VendorProfileActivity extends FragmentActivity implements View.OnClickListener {

    private Context context;
    ImageView iv_back, iv_item_image;
    EditText edt_compney, edt_businessname, edt_address, edt_address2, edt_phone, edt_email, edt_paypal_link, edt_refund_policy;
    EditText edt_suburb, edt_sociallinks, edt_password, edt_open_time, edt_close_time, edt_website;
    Button btn_submit;
    TextView tv_return_policy;
    String planid, categoryId;
    Spinner spin_category;

    private Storage storage;
    private LocationTracker locTracker;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;

    private Uri itemImageUri, itemImageOriginalUri;
    private String address1 = "", address2 = "";
    private double latitude1, longitude1, latitude2, longitude2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_profile);

        context = VendorProfileActivity.this;

        storage = new Storage(context);
        locTracker = new LocationTracker(context);


        iv_back = findViewById(R.id.iv_back);
        edt_compney = findViewById(R.id.edt_compney);
        edt_businessname = findViewById(R.id.edt_businessname);
        edt_address = findViewById(R.id.edt_address);
        edt_address2 = findViewById(R.id.edt_address2);
        edt_phone = findViewById(R.id.edt_phone);
        edt_email = findViewById(R.id.edt_email);
        edt_website = findViewById(R.id.edt_website);
        edt_suburb = findViewById(R.id.edt_suburb);
        edt_sociallinks = findViewById(R.id.edt_sociallinks);
        edt_password = findViewById(R.id.edt_password);
        edt_paypal_link = findViewById(R.id.edt_paypal_link);
        edt_refund_policy = findViewById(R.id.edt_refund_policy);
        edt_open_time = findViewById(R.id.edt_open_time);
        edt_close_time = findViewById(R.id.edt_close_time);
        tv_return_policy = findViewById(R.id.tv_return_policy);
        iv_item_image = findViewById(R.id.iv_vendor_item_image);
        btn_submit = findViewById(R.id.btn_submit);
        spin_category = findViewById(R.id.spin_category);

        tv_return_policy.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        edt_address.setOnClickListener(this);
        edt_address2.setOnClickListener(this);
        iv_item_image.setOnClickListener(this);
        edt_open_time.setOnClickListener(this);
        edt_close_time.setOnClickListener(this);

        if (getIntent().hasExtra("planid")) {
            planid = getIntent().getExtras().getString("planid");
        } else {
            if (storage.getVenderSignin() == 1) {
                getVendorProfile();
            }
        }

        getFilterCategory();


    }

    private void getFilterCategory() {
        Utils.progress_show(context);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService
                .getFilterCategory(storage.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetCategoryResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GetCategoryResponse getCategoryResponse) {
                        if (getCategoryResponse.getStatus() == 1) {
                            setAdapter(getCategoryResponse);
                        } else {
                            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                        }
                        Utils.progress_dismiss(context);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.progress_dismiss(context);
                        Toast.makeText(context, R.string.connection_timeout, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setAdapter(final GetCategoryResponse getCategoryResponse) {
        final ArrayList<String> categoryIds = new ArrayList<>();
        for (int i = 0; i < getCategoryResponse.getData().size(); i++) {
            categoryIds.add(getCategoryResponse.getData().get(i).getCategoryName());
        }
        final ArrayAdapter categoryid = new ArrayAdapter(context, R.layout.spinner_dropdown, categoryIds);
        categoryid.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_category.setAdapter(categoryid);

        spin_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryId = getCategoryResponse.getData().get(parent.getSelectedItemPosition()).getCategoryId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getVendorProfile() {
        Utils.progress_show(context);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService
                .getvendorprofile(storage.getVenderId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetVendorProfileResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GetVendorProfileResponse response) {
                        if (response.getStatus() == 1) {
                            if (response.getData() != null) {
                                Glide.with(context).load(response.getData().getVendorImage()).into(iv_item_image);

                                edt_compney.setText(response.getData().getCompanyName());
                                edt_businessname.setText(response.getData().getBusinessName());
                                edt_email.setText(response.getData().getEmail());
                                edt_phone.setText(response.getData().getBusinessPhoneNumber());
                                edt_address.setText(response.getData().getAddress());
                                edt_suburb.setText(response.getData().getSuburb());
                                edt_sociallinks.setText(response.getData().getSocialLink());
                                edt_password.setVisibility(View.GONE);
                                edt_open_time.setText(response.getData().getOpenTime());
                                edt_close_time.setText(response.getData().getCloseTime());
                                edt_website.setText("");
                            }
                        } else {
                            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                        }
                        Utils.progress_dismiss(context);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.progress_dismiss(context);
                        Toast.makeText(context, R.string.connection_timeout, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View view) {

        if (view == iv_back) {
            finish();
        } else if (view == tv_return_policy) {
            startActivity(new Intent(context, ReturnAndRefundPolicyActivity.class));

        } else if (view == edt_address) {
            selectLocationDialog("primary");

        } else if (view == edt_address2) {
            selectLocationDialog("secondary");

        } else if (view == iv_item_image) {
            Utils.openGallery(context);

        } else if (view == edt_open_time) {
            openTimePickerDialog("open_time");

        } else if (view == edt_close_time) {
            openTimePickerDialog("close_time");

        } else if (view == btn_submit) {
            String compneyname = edt_compney.getText().toString().trim();
            String businessname = edt_businessname.getText().toString().trim();
            String address = edt_address.getText().toString().trim();
            String addres2 = edt_address2.getText().toString().trim();
            String phone = edt_phone.getText().toString().trim();
            String email = edt_email.getText().toString().trim();
            String suburb = "test";
            String sociallink = "test";
            String password = edt_password.getText().toString().trim();
            String openTime = edt_open_time.getText().toString().trim();
            String closeTime = edt_close_time.getText().toString().trim();
            String website = "test";
            String paypalLink = edt_paypal_link.getText().toString().trim();
            String refundPolicy = edt_refund_policy.getText().toString().trim();

            String addArr[] = Utils.getCountryState(context, latitude1, longitude1).split(",");
            String country = "";
            String state = "";
            String pincode = "";
            if(addArr.length >= 2) {
                country = addArr[0];
                state = addArr[1];
                pincode = addArr[2];
            } else {
                Toast.makeText(VendorProfileActivity.this,"Please select address",Toast.LENGTH_LONG).show();
                return;
            }

            addvendor(compneyname, businessname, address, phone, country, state, email, pincode, addres2, suburb, sociallink, password, openTime, closeTime, website,refundPolicy,paypalLink);
        }
    }

    private void openTimePickerDialog(final String type) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_time_picker);

        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.60);
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.90);

        dialog.getWindow().setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(false);

        final TimePicker picker = dialog.findViewById(R.id.timePicker);
        Button btnSet = dialog.findViewById(R.id.btn_set_time);

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                int hour, minute;
                String am_pm;
                if (Build.VERSION.SDK_INT >= 23) {
                    hour = picker.getHour();
                    minute = picker.getMinute();
                } else {
                    hour = picker.getCurrentHour();
                    minute = picker.getCurrentMinute();
                }

                if (hour > 12) {
                    am_pm = "PM";
                    hour = hour - 12;
                } else {
                    am_pm = "AM";
                }

                if (type.equals("open_time")) {
                    edt_open_time.setText(hour + ":" + minute + " " + am_pm);
                } else {
                    edt_close_time.setText(hour + ":" + minute + " " + am_pm);
                }
            }
        });

        dialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                iv_item_image.setImageURI(result.getUri());
                itemImageUri = result.getUri();
                itemImageOriginalUri = result.getOriginalUri();
            }
        }
    }

    EditText dialogEdtAddress;
    SupportMapFragment mapFragment;

    private void selectLocationDialog(final String type) {
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.dialog_choose_location);

        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.98);
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.98);

        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(true);

        dialogEdtAddress = dialog.findViewById(R.id.dialog_edt_address);
        Button btnSubmit = dialog.findViewById(R.id.dialog_btn_submit);
        Button btnCancel = dialog.findViewById(R.id.dialog_btn_cancel);
        ImageButton btnMyLocation = dialog.findViewById(R.id.btnMyLocation);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.dialog_map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(VendorProfileActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        buildGoogleApiClient();
                        mMap.setMyLocationEnabled(true);
                        mMap.getUiSettings().setMyLocationButtonEnabled(true);
                    }
                } else {
                    buildGoogleApiClient();
                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                }
                setMarkerPoint(new LatLng(locTracker.getLatitude(), locTracker.getLongitude()), type);
            }
        });

        btnMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMarkerPoint(new LatLng(locTracker.getLatitude(), locTracker.getLongitude()), type);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.disconnect();
                }
                if (mapFragment != null)
                    getSupportFragmentManager().beginTransaction().remove(mapFragment).commit();

                dialog.dismiss();

                if (type.equalsIgnoreCase("primary")) {
                    edt_address.setText(dialogEdtAddress.getText().toString().trim());
                } else {
                    edt_address2.setText(dialogEdtAddress.getText().toString().trim());
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.disconnect();
                }
                if (mapFragment != null)
                    getSupportFragmentManager().beginTransaction().remove(mapFragment).commit();

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void setMarkerPoint(LatLng point, final String type) {
        mMap.clear();
        if (type.equalsIgnoreCase("primary")) {
            latitude1 = point.latitude;
            longitude1 = point.longitude;
            address1 = Utils.getAddress(context, latitude1, longitude1);
            dialogEdtAddress.setText(address1);
        } else {
            latitude2 = point.latitude;
            longitude2 = point.longitude;
            address2 = Utils.getAddress(context, latitude2, longitude2);
            dialogEdtAddress.setText(address2);
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(point).title("Your Location");
        Marker marker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13f));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                setMarkerPoint(latLng, type);
            }
        });
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(VendorProfileActivity.this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        LocationRequest locationRequest = new LocationRequest();
                        locationRequest.setInterval(1000);
                        locationRequest.setFastestInterval(1000);
                        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();

    }


    private void addvendor(String compneyname, String businessname, String address, String phone, String country,
                           String state, String email, String pincode, String address2, String suburb,
                           String sociallink, String password, String opentime, String closetime, String website,String refundPolicy,
                           String paypalLink) {

        RequestBody rb_planId = Utils.createPart(context, planid);
        RequestBody rb_userId = Utils.createPart(context, storage.getUserId());
        RequestBody rb_cmpName = Utils.createPart(context, compneyname);
        RequestBody rb_busName = Utils.createPart(context, businessname);
        RequestBody rb_add1 = Utils.createPart(context, address);
        RequestBody rb_contact = Utils.createPart(context, phone);
        RequestBody rb_country = Utils.createPart(context, country);
        RequestBody rb_state = Utils.createPart(context, state);
        RequestBody rb_email = Utils.createPart(context, email);
        RequestBody rb_pincode = Utils.createPart(context, pincode);
        RequestBody rb_add2 = Utils.createPart(context, address2);
        RequestBody rb_suburb = Utils.createPart(context, suburb);
        RequestBody rb_link = Utils.createPart(context, sociallink);
        RequestBody rb_password = Utils.createPart(context, password);
        RequestBody rb_openTime = Utils.createPart(context, opentime);
        RequestBody rb_closeTime = Utils.createPart(context, closetime);
        RequestBody rb_weblink = Utils.createPart(context, website);
        RequestBody rb_lat1 = Utils.createPart(context, String.valueOf(latitude1));
        RequestBody rb_long1 = Utils.createPart(context, String.valueOf(longitude1));
        RequestBody rb_lat2 = Utils.createPart(context, String.valueOf(latitude1));
        RequestBody rb_long2 = Utils.createPart(context, String.valueOf(longitude1));
        RequestBody rb_category = Utils.createPart(context, String.valueOf(categoryId));
        RequestBody rb_refund_policy = Utils.createPart(context, String.valueOf(refundPolicy));
        RequestBody rb_paypal = Utils.createPart(context, String.valueOf(paypalLink));

        MultipartBody.Part vendorImg = Utils.createMultipart(context, itemImageUri, itemImageOriginalUri, "vendor_image");

        Utils.progress_show(context);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService
                .add_vendor(rb_planId, rb_userId, rb_cmpName, rb_busName, rb_add1, rb_contact, rb_country, rb_state,
                        rb_email, rb_pincode, rb_lat1, rb_long1, rb_add2, rb_suburb, rb_link, rb_openTime,
                        rb_closeTime, vendorImg, rb_password, rb_lat2, rb_long2,rb_category, rb_refund_policy,rb_paypal,rb_weblink)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JsonObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onSuccess(JsonObject response) {
                        if (response.get("status").getAsInt() == 1) {
                            Toast.makeText(context, response.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(context, VendorSignInActivity.class));
                            finish();
                        } else {
                            Toast.makeText(context, response.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                        }
                        Utils.progress_dismiss(context);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.progress_dismiss(context);
                        Toast.makeText(context, R.string.connection_timeout, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
