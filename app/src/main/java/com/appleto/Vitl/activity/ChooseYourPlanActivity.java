package com.appleto.Vitl.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appleto.Vitl.R;
import com.appleto.Vitl.adapter.PlanAdapter;
import com.appleto.Vitl.model.GetPlanResponse;
import com.appleto.Vitl.retrofit2.ApiClient;
import com.appleto.Vitl.retrofit2.ApiInterface;
import com.appleto.Vitl.utills.Utils;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChooseYourPlanActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;

    ImageView iv_back;
    LinearLayout ll_price_view;
    RecyclerView rv_plan;

    List<GetPlanResponse.Datum> response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_your_plan);

        context = ChooseYourPlanActivity.this;

        iv_back = findViewById(R.id.iv_back);
        rv_plan = findViewById(R.id.rv_plan);


        iv_back.setOnClickListener(this);

        getplan();
    }


    @Override
    public void onClick(View view) {

        if (view == iv_back) {
            finish();
        }
    }

    private void getplan() {
        Utils.progress_show(context);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService
                .getplan()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetPlanResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GetPlanResponse getPlanResponse) {
                        if (getPlanResponse.getStatus() == 1) {

                            response = getPlanResponse.getData();

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                            rv_plan.setLayoutManager(linearLayoutManager);
                            PlanAdapter planAdapter = new PlanAdapter(context, response);
                            rv_plan.setAdapter(planAdapter);


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
    }
}
