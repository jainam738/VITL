package com.appleto.Vitl.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.appleto.Vitl.R;
import com.appleto.Vitl.utills.Storage;

public class UserOtpActivity extends AppCompatActivity {

    private Context context;
    private Storage storage;

    EditText edt_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_otp);

        context = UserOtpActivity.this;
        storage = new Storage(context);

        edt_code = findViewById(R.id.edt_code);

        edt_code.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {

                    startActivity(new Intent(context, MainActivity.class));
                    finish();

                    String otp = edt_code.getText().toString().trim();
                    // otpvarification(otp);
                }

                return false;
            }
        });
    }
}
