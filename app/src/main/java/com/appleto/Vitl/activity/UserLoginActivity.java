package com.appleto.Vitl.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.appleto.Vitl.R;
import com.appleto.Vitl.utills.Storage;
import com.appleto.Vitl.utills.Utils;
import com.rilixtech.CountryCodePicker;

public class UserLoginActivity extends AppCompatActivity {

    private Context context;
    private Storage storage;

    EditText edt_phone;

    CountryCodePicker ccp;
    AppCompatEditText edtPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        context = UserLoginActivity.this;
        storage = new Storage(context);

//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(LoginActivity.this, new OnSuccessListener<InstanceIdResult>() {
//            @Override
//            public void onSuccess(InstanceIdResult instanceIdResult) {
//                String fcmToken = instanceIdResult.getToken();
//                storage.saveFirebaseToken(fcmToken);
//            }
//        });

        ccp = findViewById(R.id.ccp);
        edtPhoneNumber = findViewById(R.id.phone_number_edt);

        ccp.registerPhoneNumberTextView(edtPhoneNumber);


        edtPhoneNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    String mobile = edtPhoneNumber.getText().toString().trim();
                    String countrycode = ccp.getDefaultCountryCode();
                    String country = ccp.getDefaultCountryName();
                    // login(mobile,countrycode,country);

                    Utils.navigateTo(UserLoginActivity.this, OtpActivity.class);

                }
                return false;
            }
        });
    }
}
