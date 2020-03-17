package com.appleto.Vitl.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.appleto.Vitl.R;
import com.appleto.Vitl.model.GetVendorProfileResponse;
import com.appleto.Vitl.retrofit2.ApiClient;
import com.appleto.Vitl.retrofit2.ApiInterface;
import com.appleto.Vitl.utills.Storage;
import com.appleto.Vitl.utills.Utils;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private Storage storage;
    ImageView iv_back;
    TextView tv_compneyname, tv_business, tv_address, tv_phone, tv_country, tv_email;


    GetVendorProfileResponse.Data response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        context = ContactUsActivity.this;
        storage = new Storage(context);

        iv_back = findViewById(R.id.iv_back);
        tv_compneyname = findViewById(R.id.tv_compneyname);
        tv_business = findViewById(R.id.tv_business);
        tv_address = findViewById(R.id.tv_address);
        tv_phone = findViewById(R.id.tv_phone);
        tv_country = findViewById(R.id.tv_country);
        tv_email = findViewById(R.id.tv_email);


        iv_back.setOnClickListener(this);

        getProfile();
    }

    private void getProfile() {
        Utils.progress_show(context);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService
                .getvendorprofile(storage.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetVendorProfileResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onSuccess(GetVendorProfileResponse getProfileResponse) {
                        if (getProfileResponse.getStatus() == 1) {


                            if (getProfileResponse.getData() != null) {

                                response = getProfileResponse.getData();

                                tv_compneyname.setText(response.getCompanyName());
                                tv_business.setText(response.getBusinessName());
                                tv_address.setText(response.getAddress());
                                tv_phone.setText(response.getBusinessPhoneNumber());
                                tv_country.setText(response.getCountry());
                                tv_email.setText(response.getEmail());

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
        }
    }
}
