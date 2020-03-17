package com.appleto.Vitl.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appleto.Vitl.R;
import com.appleto.Vitl.adapter.HomeDetailsAdapter;
import com.appleto.Vitl.adapter.ListHistoryAdapter;
import com.appleto.Vitl.model.GetUserItemResponse;
import com.appleto.Vitl.model.GetVendorItemsListResponse;
import com.appleto.Vitl.retrofit2.ApiClient;
import com.appleto.Vitl.retrofit2.ApiInterface;
import com.appleto.Vitl.utills.Storage;
import com.appleto.Vitl.utills.Utils;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListHistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private Storage storage;
    private Context context;
    RecyclerView list_history;
    ImageView iv_back;
    Button btn_additem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_history);

        context = ListHistoryActivity.this;
        storage = new Storage(context);

        iv_back = findViewById(R.id.iv_back);
        btn_additem = findViewById(R.id.btn_additem);
        list_history = findViewById(R.id.list_history);

        btn_additem.setOnClickListener(this);
        iv_back.setOnClickListener(this);

        getVendorItem();

    }

    private void getVendorItem() {

        Utils.progress_show(context);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService
                .get_vendor_items(storage.getVenderId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetVendorItemsListResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onSuccess(GetVendorItemsListResponse response) {
                        if (response.getStatus() == 1) {

                            if (response.getData().size() > 0) {
                                LinearLayoutManager linearLayout = new LinearLayoutManager(context);
                                list_history.setLayoutManager(linearLayout);
                                ListHistoryAdapter listHistoryAdapter = new ListHistoryAdapter(context, response.getData());
                                list_history.setAdapter(listHistoryAdapter);
                            }
                        } else {
                            Toast.makeText(context, "no data found", Toast.LENGTH_SHORT).show();
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

    /*private void getitem() {
        Utils.progress_show(context);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService
                .getuseritem(storage.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetUserItemResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onSuccess(GetUserItemResponse getUserItemResponse) {
                        if (getUserItemResponse.getStatus() == 1) {

                            list = getUserItemResponse.getData();

                            LinearLayoutManager linearLayout = new LinearLayoutManager(context);
                            list_history.setLayoutManager(linearLayout);
                            ListHistoryAdapter listHistoryAdapter = new ListHistoryAdapter(context, list);
                            list_history.setAdapter(listHistoryAdapter);


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

    @Override
    public void onClick(View view) {
        if (view == iv_back) {
            finish();
        } else if(view == btn_additem){
            startActivity(new Intent(context, AddItemActivity.class));
        }
    }
}
