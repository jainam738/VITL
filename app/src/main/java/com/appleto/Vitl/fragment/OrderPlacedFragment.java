package com.appleto.Vitl.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.appleto.Vitl.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderPlacedFragment extends Fragment {


    public OrderPlacedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_placed, container, false);
    }

}
