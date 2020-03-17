package com.appleto.Vitl.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appleto.Vitl.R;
import com.appleto.Vitl.model.GetProfileResponse;
import com.appleto.Vitl.retrofit2.ApiClient;
import com.appleto.Vitl.retrofit2.ApiInterface;
import com.appleto.Vitl.utills.Storage;
import com.appleto.Vitl.utills.Utils;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private Storage storage;

    LinearLayout ll_vendor_signin, ll_vendor_logout;
    ImageView iv_back;
    TextView tv_name, tv_phone, tv_texttone;
    LinearLayout ll_logout;
    TextView tvBusRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.context = this;

        storage = new Storage(context);

        iv_back = findViewById(R.id.iv_back);
        tv_name = findViewById(R.id.tv_name);
        tvBusRegister = findViewById(R.id.tv_register_business);
        tv_phone = findViewById(R.id.tv_phone);
        tv_texttone = findViewById(R.id.tv_texttone);
        ll_logout = findViewById(R.id.ll_logout);
        ll_vendor_signin = findViewById(R.id.ll_vendor_signin);
        ll_vendor_logout = findViewById(R.id.ll_vendor_logout);

        iv_back.setOnClickListener(this);
        tvBusRegister.setOnClickListener(this);
        ll_vendor_signin.setOnClickListener(this);
        ll_logout.setOnClickListener(this);
        ll_vendor_logout.setOnClickListener(this);

        getProfile();
    }

    @Override
    public void onClick(View view) {

        if (view == iv_back) {
            onBackPressed();
        } else if (view == tvBusRegister) {
            Intent intent = new Intent(context, ChooseYourPlanActivity.class);
            startActivity(intent);

        } else if (view == ll_vendor_signin) {
            if (storage.getVenderSignin() == 1) {
                startActivity(new Intent(context, VendorSIgnInDetailActivity.class));
            } else {
                startActivity(new Intent(context, VendorSignInActivity.class));
            }

        } else if (view == ll_vendor_logout) {
            storage.saveVenderSignin(0);
            Toast.makeText(context, "Vendor logout successfully", Toast.LENGTH_LONG).show();
        } else if (view == ll_logout) {
            ((AppCompatActivity) context).getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            storage.saveLogInSate(0);
            Intent intent = new Intent(context, StartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void getProfile() {
        Utils.progress_show(context);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService
                .getprofile(storage.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetProfileResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GetProfileResponse getProfileResponse) {
                        if (getProfileResponse.getStatus() == 1) {
                            if (getProfileResponse.getData() != null) {
                                if (!getProfileResponse.getData().getName().equalsIgnoreCase("")) {
                                    tv_name.setText(getProfileResponse.getData().getName());
                                }
                                if (!getProfileResponse.getData().getTextTone().equalsIgnoreCase("")) {
                                    tv_texttone.setText(getProfileResponse.getData().getTextTone());
                                }
                                tv_phone.setText(getProfileResponse.getData().getMobileNo());
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
}
