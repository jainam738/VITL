package com.appleto.Vitl.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.appleto.Vitl.R;
import com.appleto.Vitl.model.GetOtpResponse;
import com.appleto.Vitl.retrofit2.ApiClient;
import com.appleto.Vitl.retrofit2.ApiInterface;
import com.appleto.Vitl.utills.Storage;
import com.appleto.Vitl.utills.Utils;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OtpActivity extends AppCompatActivity {

    private Context context;
    private Storage storage;

    EditText edt_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        context = OtpActivity.this;
        storage = new Storage(context);

        edt_code = findViewById(R.id.edt_code);

        edt_code.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    String otp = edt_code.getText().toString().trim();
                    otpvarification(otp);
                }
                return false;
            }
        });
    }

    private void otpvarification(String otp) {
        Utils.progress_show(context);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService
                .otpverification(storage.getUserId(), otp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetOtpResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GetOtpResponse response) {
                        if (response.getStatus() == 1) {
                            storage.saveLogInSate(1);

                            storage.saveUserId(response.getData().getUserId());
                            storage.saveUserName(response.getData().getName());
                            storage.saveUserEmail(response.getData().getEmail());
                            storage.saveUserPhone(response.getData().getMobileNo());

                            Utils.navigateTo(OtpActivity.this, MainActivity.class);

                        } else {
                            Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
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
