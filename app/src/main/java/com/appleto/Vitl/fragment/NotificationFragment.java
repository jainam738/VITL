package com.appleto.Vitl.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appleto.Vitl.R;
import com.appleto.Vitl.adapter.PushNotificationAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment implements View.OnClickListener {

    private Context context;
    RecyclerView rv_pushnoti;
    ImageView iv_back;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        iv_back = view.findViewById(R.id.iv_back);
        rv_pushnoti = view.findViewById(R.id.rv_pushnoti);

        LinearLayoutManager linearLayout = new LinearLayoutManager(context);
        rv_pushnoti.setLayoutManager(linearLayout);
        PushNotificationAdapter infoCenterAdapter = new PushNotificationAdapter(context);
        rv_pushnoti.setAdapter(infoCenterAdapter);

        iv_back.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

        if (view == iv_back) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
