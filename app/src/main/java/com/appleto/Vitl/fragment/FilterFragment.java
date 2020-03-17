package com.appleto.Vitl.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.appleto.Vitl.R;
import com.appleto.Vitl.adapter.FilterCateogryAdapter;
import com.appleto.Vitl.model.GetCategoryResponse;
import com.appleto.Vitl.retrofit2.ApiClient;
import com.appleto.Vitl.retrofit2.ApiInterface;
import com.appleto.Vitl.utills.Storage;
import com.appleto.Vitl.utills.Utils;
import com.google.gson.JsonObject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends Fragment {

    private Storage storage;
    private RecyclerView filterRecyclerView;
    private ProgressBar progressBar;

    public FilterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        storage = new Storage(getActivity());
        filterRecyclerView = view.findViewById(R.id.filterRecyclerView);
        progressBar = view.findViewById(R.id.progress_circular);

        getFilterCategory();
    }

    private void getFilterCategory() {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService
                .getFilterCategory(storage.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetCategoryResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GetCategoryResponse getCategoryResponse) {
                        progressBar.setVisibility(View.GONE);
                        if (getCategoryResponse.getStatus() == 1) {
                            setAdapter(getCategoryResponse);
                        } else {
                            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), R.string.connection_timeout, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void setAdapter(GetCategoryResponse getCategoryResponse) {
        FilterCateogryAdapter adapter = new FilterCateogryAdapter(getActivity(), getCategoryResponse.getData(), new FilterCateogryAdapter.onFilterClickListener() {
            @Override
            public void onItemClick(String categoryId) {
                userActiveCategory(categoryId);
            }
        });
        filterRecyclerView.setAdapter(adapter);
        filterRecyclerView.setNestedScrollingEnabled(false);
        filterRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void userActiveCategory(String categoryId) {
//        Utils.progress_show(getActivity());

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService
                .userActiveCategory(storage.getUserId(), categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JsonObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(JsonObject jsonObject) {
                        Toast.makeText(getActivity(), jsonObject.get("message").getAsString(), Toast.LENGTH_LONG).show();
//                        Utils.progress_dismiss(getActivity());
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Utils.progress_dismiss(getActivity());
                        Toast.makeText(getActivity(), R.string.connection_timeout, Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
