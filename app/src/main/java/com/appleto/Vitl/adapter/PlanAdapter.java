package com.appleto.Vitl.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appleto.Vitl.R;
import com.appleto.Vitl.activity.VendorProfileActivity;
import com.appleto.Vitl.model.GetGeneralSettingResponse;
import com.appleto.Vitl.model.GetPlanResponse;
import com.appleto.Vitl.retrofit2.ApiClient;
import com.appleto.Vitl.retrofit2.ApiInterface;
import com.appleto.Vitl.utills.Storage;
import com.appleto.Vitl.utills.Utils;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class PlanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    private Storage storage;

    List<GetPlanResponse.Datum> response;

    public PlanAdapter(Context context, List<GetPlanResponse.Datum> response) {
        this.context = context;
        this.response = response;
        storage = new Storage(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.raw_plan, parent, false);


        // set the view's size, margins, paddings and layout parameters
        ViewHolder viewHolder = new ViewHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof ViewHolder) {
            final ViewHolder holder = (ViewHolder) viewHolder;

            //  final int position = holder.getAdapterPosition();

            holder.tv_plan_name.setText(response.get(position).getPlanName());
            holder.tv_plan_price.setText(response.get(position).getPlanPrice());
            holder.tv_plan_description.setText(response.get(position).getPlanDescription());
            holder.tv_valid_day.setText(response.get(position).getValidDay() + " days");

            holder.ll_price_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getProfile(position);
                }
            });

        }
    }

    private void getProfile(final int position) {
        Utils.progress_show(context);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService
                .getgeneralsetting(storage.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetGeneralSettingResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onSuccess(GetGeneralSettingResponse getGeneralSettingResponse) {
                        if (getGeneralSettingResponse.getStatus() == 1) {
                            if (getGeneralSettingResponse.getData() != null) {
                                Intent myactivity = new Intent(context.getApplicationContext(), VendorProfileActivity.class);
                                myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
                                Bundle bundle = new Bundle();
                                bundle.putString("planid", response.get(position).getPlanId());
                                myactivity.putExtras(bundle);
                                context.getApplicationContext().startActivity(myactivity);
                            }

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

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    @Override
    public int getItemCount() {

        return response.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView tv_plan_name, tv_plan_price, tv_plan_description, tv_valid_day;
        LinearLayout ll_price_view;


        public ViewHolder(View view) {

            super(view);

            view.setOnClickListener(this);

            ll_price_view = view.findViewById(R.id.ll_price_view);
            tv_plan_name = view.findViewById(R.id.tv_plan_name);
            tv_plan_price = view.findViewById(R.id.tv_plan_price);
            tv_plan_description = view.findViewById(R.id.tv_plan_description);
            tv_valid_day = view.findViewById(R.id.tv_valid_day);

        }

        @Override
        public void onClick(View view) {


        }
    }

}


