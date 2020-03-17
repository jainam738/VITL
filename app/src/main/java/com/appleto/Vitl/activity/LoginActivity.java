package com.appleto.Vitl.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.appleto.Vitl.R;
import com.appleto.Vitl.model.GetLoginResponse;
import com.appleto.Vitl.retrofit2.ApiClient;
import com.appleto.Vitl.retrofit2.ApiInterface;
import com.appleto.Vitl.utills.Storage;
import com.appleto.Vitl.utills.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.rilixtech.Country;
import com.rilixtech.CountryCodePicker;

import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LoginActivity extends AppCompatActivity {

    private Context context;
    private Storage storage;

    EditText edt_code;

    CountryCodePicker ccp;
    AppCompatEditText edtPhoneNumber;
    ImageView img_right;
    FirebaseAuth mAuth;
    LinearLayout ll_otp, ll_phone;
    private String mVerificationId;

    String mobile, countryCode, countryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = LoginActivity.this;
        storage = new Storage(context);

        mAuth = FirebaseAuth.getInstance();

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(LoginActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String fcmToken = instanceIdResult.getToken();
                storage.saveFirebaseToken(fcmToken);
            }
        });

        ccp = findViewById(R.id.ccp);
        edtPhoneNumber = findViewById(R.id.phone_number_edt);
        edt_code = findViewById(R.id.edt_code);
        ll_otp = findViewById(R.id.ll_otp);
        ll_phone = findViewById(R.id.ll_phone);
        img_right = findViewById(R.id.img_right);

        ll_phone.setVisibility(View.VISIBLE);
        ll_otp.setVisibility(View.GONE);

        ccp.registerPhoneNumberTextView(edtPhoneNumber);
        countryCode = ccp.getDefaultCountryCode();
        countryName = ccp.getDefaultCountryName();
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                countryCode = selectedCountry.getPhoneCode();
                countryName = selectedCountry.getName();
            }
        });

        edtPhoneNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    mobile = edtPhoneNumber.getText().toString().trim();
                    login();
                }

                return false;
            }
        });
    }

    private void login() {
        Utils.progress_show(context);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService
                .login(mobile, countryCode, countryName, storage.getFirebaseToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetLoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GetLoginResponse response) {
                        if (response.getStatus() == 1) {
                            storage.saveUserId(response.getUserId());
                            loginotp();
                        } else {
                            Toast.makeText(context, "enter phone no.", Toast.LENGTH_SHORT).show();
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

    public void loginotp() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+" + countryCode + mobile,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                edt_code.setText(code);
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
            ll_phone.setVisibility(View.GONE);
            ll_otp.setVisibility(View.VISIBLE);
            img_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String otp = edt_code.getText().toString().trim();
                    if (!otp.equalsIgnoreCase("")) {
                        verifyVerificationCode(otp);
                    } else {
                        Toast.makeText(context, "please enter otp", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    };

    private void verifyVerificationCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            storage.saveLogInSate(1);
                            startActivity(new Intent(context, MainActivity.class));
                            finish();

                        } else {
                            Toast.makeText(context, "OTP verification failed. try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
