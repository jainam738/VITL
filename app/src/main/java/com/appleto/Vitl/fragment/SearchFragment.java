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
import com.appleto.Vitl.adapter.SearchlistAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {

    private Context context;

    RecyclerView rv_list;

    ImageView iv_back;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        iv_back = view.findViewById(R.id.iv_back);
        rv_list = view.findViewById(R.id.rv_list);


        LinearLayoutManager linearLayout = new LinearLayoutManager(context);
        rv_list.setLayoutManager(linearLayout);
        SearchlistAdapter searchlistAdapter = new SearchlistAdapter(context);
        rv_list.setAdapter(searchlistAdapter);


        iv_back.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == iv_back) {

            getActivity().getSupportFragmentManager().popBackStack();

//            if (getFragmentManager().getBackStackEntryCount() != 0) {
//                getFragmentManager().popBackStack();
//            }

        }
    }
}
