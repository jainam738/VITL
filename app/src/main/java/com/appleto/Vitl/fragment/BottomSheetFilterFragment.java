package com.appleto.Vitl.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appleto.Vitl.R;
import com.appleto.Vitl.activity.ProfileActivity;
import com.appleto.Vitl.adapter.FilterCateogryAdapter;
import com.appleto.Vitl.model.GetCategoryResponse;
import com.appleto.Vitl.retrofit2.ApiClient;
import com.appleto.Vitl.retrofit2.ApiInterface;
import com.appleto.Vitl.utills.Storage;
import com.appleto.Vitl.utills.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.JsonObject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomSheetFilterFragment extends BottomSheetDialogFragment {

    private BottomNavigationView navigation;

    public BottomSheetFilterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottomsheet_filter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.setSelectedItemId(R.id.nav_filter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (navigation != null) {
            navigation.setSelectedItemId(R.id.nav_filter);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {

                case R.id.nav_profile:
                    startActivity(new Intent(getActivity(), ProfileActivity.class));
//                    fragment = new ProfileFragment();
//                    loadFragment(fragment);
                    return true;
                case R.id.nav_filter:
                    fragment = new FilterFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.nav_favourite:
                    fragment = new FavouritesFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }

    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = null;
        if (getFragmentManager() != null) {
            transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_filter, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

}
