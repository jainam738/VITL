package com.appleto.Vitl.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.appleto.Vitl.R;

public class ReturnAndRefundPolicyActivity extends AppCompatActivity {

    private Context context;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_and_refund_policy);

        context = ReturnAndRefundPolicyActivity.this;

        iv_back = findViewById(R.id.iv_back);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

    }
}
