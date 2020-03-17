package com.appleto.Vitl.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.appleto.Vitl.R;
import com.appleto.Vitl.model.GetVendorLoginResponse;
import com.appleto.Vitl.retrofit2.ApiClient;
import com.appleto.Vitl.retrofit2.ApiInterface;
import com.appleto.Vitl.utills.Storage;
import com.appleto.Vitl.utills.Utils;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VendorSignInActivity extends AppCompatActivity implements View.OnClickListener {


    private Context context;
    private Storage storage;

    ImageView iv_back;
    EditText edtEmail, edtPassword;

    CardView cardLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_sign_in);

        context = VendorSignInActivity.this;
        storage = new Storage(context);

        iv_back = findViewById(R.id.iv_back);
        cardLogin = findViewById(R.id.card_login_view);
        edtEmail = findViewById(R.id.edt_vender_email);
        edtPassword = findViewById(R.id.edt_vender_password);

        cardLogin.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == iv_back) {
            finish();
        } else if (view == cardLogin) {
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            if (email.equalsIgnoreCase("")) {
                Toast.makeText(context, "Invalid email id", Toast.LENGTH_SHORT).show();
            } else if (password.equalsIgnoreCase("")) {
                Toast.makeText(context, "Invalid password", Toast.LENGTH_SHORT).show();
            } else {
                if (storage.getFirebaseToken() != null) {
                    venderLogin(email, password, storage.getFirebaseToken());
                } else {
                    Toast.makeText(context, "Invalid FCM Token", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void venderLogin(String email, String password, String token) {
        Utils.progress_show(context);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService
                .vendor_login(email, password, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetVendorLoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GetVendorLoginResponse response) {
                        if (response.getStatus() == 1) {
                            storage.saveVenderSignin(1);
                            storage.saveVenderId(response.getData().getVendorId());
                            startActivity(new Intent(context, VendorSIgnInDetailActivity.class));
                            finish();
                        } else {
                            Toast.makeText(context, "Vendor login failed!!!", Toast.LENGTH_SHORT).show();
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
