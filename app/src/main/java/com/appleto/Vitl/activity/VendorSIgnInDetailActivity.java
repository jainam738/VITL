package com.appleto.Vitl.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.appleto.Vitl.R;
import com.appleto.Vitl.fragment.NotificationFragment;
import com.appleto.Vitl.utills.Storage;

public class VendorSIgnInDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    ImageView iv_back, iv_contactus;

    TextView tvRating, tvReview;
    LinearLayout ll_list_special, ll_profile, ll_list_history, ll_notification;

    private Storage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_sign_in_detail);

        context = VendorSIgnInDetailActivity.this;
        storage = new Storage(context);

        iv_back = findViewById(R.id.iv_back);
        ll_profile = findViewById(R.id.ll_profile);
        iv_contactus = findViewById(R.id.iv_contactus);
        tvRating = findViewById(R.id.tv_vendor_rating);
        tvReview = findViewById(R.id.tv_vendor_total_review);

        ll_list_special = findViewById(R.id.ll_list_special);
        ll_list_history = findViewById(R.id.ll_list_history);
        ll_notification = findViewById(R.id.ll_notification);

        ll_list_special.setOnClickListener(this);
        ll_profile.setOnClickListener(this);
        ll_list_history.setOnClickListener(this);
        iv_contactus.setOnClickListener(this);
        ll_notification.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == iv_back) {
            finish();

        } else if (view == ll_profile) {
            startActivity(new Intent(context, VendorProfileActivity.class));

        } else if (view == ll_list_history) {
            startActivity(new Intent(context, ListHistoryActivity.class));

        } else if (view == ll_list_special) {
            startActivity(new Intent(context, VendorListSpecialActivity.class));

        } else if (view == ll_notification) {
            startActivity(new Intent(context, NotificationActivity.class));

        } else if (view == iv_contactus) {
            startActivity(new Intent(context, ContactUsActivity.class));

        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Vendor Pofile")
                .setMessage("Are you sure you want to close vendor pofile?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        storage.saveVenderSignin(0);
                        storage.saveVenderId(null);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
