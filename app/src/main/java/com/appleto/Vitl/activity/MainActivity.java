package com.appleto.Vitl.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.appleto.Vitl.R;
import com.appleto.Vitl.fragment.CartFragment;
import com.appleto.Vitl.fragment.ListItemNewFragment;
import com.appleto.Vitl.fragment.MapNewFragment;
import com.appleto.Vitl.fragment.NotificationFragment;
import com.appleto.Vitl.fragment.ProfileFragment;
import com.appleto.Vitl.utills.Storage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    Context context;

    int clickcount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        loadFragment(new MapNewFragment());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {

                case R.id.nav_map:
                    clickcount++;
                    if ((clickcount % 2) == 0) {
                        fragment = new MapNewFragment();
                        loadFragment(fragment);
                        item.setIcon(getDrawable(R.drawable.home));
                    } else {
                        fragment = new ListItemNewFragment();
                        loadFragment(fragment);
                        item.setIcon(getDrawable(R.drawable.world));
                    }
                    return true;

                case R.id.nav_notification:
                    //  toolbar.setTitle("Dashboard");
                    fragment = new NotificationFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.nav_cart:
                    // toolbar.setTitle("My courses");
                    fragment = new CartFragment();
                    loadFragment(fragment);
                    return true;

//                case R.id.nav_filter:
//                    //   toolbar.setTitle("Settings");
//                    fragment = new BottomSheetFilterFragment();
//                    loadFragment(fragment);
//                    return true;
            }
            return false;
        }

    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {

        BottomNavigationView mBottomNavigationView = findViewById(R.id.navigation);
        if (mBottomNavigationView.getSelectedItemId() != R.id.nav_notification) {
            mBottomNavigationView.setSelectedItemId(R.id.nav_notification);
        }

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
        if (fragment instanceof MapNewFragment) {
            super.onBackPressed();
            finish();
        } else {
            loadFragment(new MapNewFragment());
        }
    }
}
