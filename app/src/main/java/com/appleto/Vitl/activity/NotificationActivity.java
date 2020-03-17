package com.appleto.Vitl.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.appleto.Vitl.R;
import com.appleto.Vitl.adapter.PushNotificationAdapter;

public class NotificationActivity extends AppCompatActivity {

    ImageView iv_back;
    RecyclerView rv_pushnoti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        iv_back = findViewById(R.id.iv_back);
        rv_pushnoti = findViewById(R.id.rv_pushnoti);

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        rv_pushnoti.setLayoutManager(linearLayout);
        PushNotificationAdapter infoCenterAdapter = new PushNotificationAdapter(this);
        rv_pushnoti.setAdapter(infoCenterAdapter);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
