package com.appleto.Vitl.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appleto.Vitl.R;
import com.appleto.Vitl.adapter.ItemListNewAdapter;
import com.appleto.Vitl.model.GetNearByVendorListResponse;
import com.appleto.Vitl.retrofit2.ApiClient;
import com.appleto.Vitl.retrofit2.ApiInterface;
import com.appleto.Vitl.utills.LocationTracker;
import com.appleto.Vitl.utills.Storage;
import com.appleto.Vitl.utills.Utils;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListItemNewFragment extends Fragment {

    private Context context;
    private Storage storage;
    LocationTracker location;
    RecyclerView rv_new_itemlist;


    public ListItemNewFragment() {
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
        View view = inflater.inflate(R.layout.fragment_list_item_new, container, false);

        storage = new Storage(context);
        location = new LocationTracker(context);

        rv_new_itemlist = view.findViewById(R.id.rv_new_itemlist);
        rv_new_itemlist.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        getVendorList();

        return view;
    }

    /*private void getcategory() {
        Utils.progress_show(context);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService
                .getcategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetCategoryResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GetCategoryResponse getCategoryResponse) {
                        if (getCategoryResponse.getStatus() == 1) {

                            response = getCategoryResponse.getData();
                            ItemListNewAdapter itemListNewAdapter = new ItemListNewAdapter(context,response);
                            rv_new_itemlist.setAdapter(itemListNewAdapter);


                        } else {
                            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                        }
                        Utils.progress_dismiss(context);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.progress_dismiss(context);
                        Toast.makeText(context, R.string.connection_timeout, Toast.LENGTH_SHORT).show();
                    }
                });
    }*/

    private void getVendorList() {
        String latitude = String.valueOf(location.getLatitude());
        String longitude = String.valueOf(location.getLongitude());

        Utils.progress_show(context);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        apiService
                .get_vendor_list(latitude, longitude, storage.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetNearByVendorListResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GetNearByVendorListResponse response) {
                        if (response.getStatus() == 1) {
                            if (response.getData() != null && response.getData().size() > 0) {
                                ItemListNewAdapter itemListNewAdapter = new ItemListNewAdapter(context, response.getData());
                                rv_new_itemlist.setAdapter(itemListNewAdapter);
                            }
                        } else {
                            Toast.makeText(context, "No Vendor Item found", Toast.LENGTH_SHORT).show();
                        }
                        Utils.progress_dismiss(context);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.progress_dismiss(context);
                        Toast.makeText(context, R.string.connection_timeout, Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
