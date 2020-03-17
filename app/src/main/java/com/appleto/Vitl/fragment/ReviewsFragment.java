package com.appleto.Vitl.fragment;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appleto.Vitl.R;
import com.appleto.Vitl.adapter.ReviewsAdapter;
import com.appleto.Vitl.model.GetRatingResponse;
import com.appleto.Vitl.retrofit2.ApiClient;
import com.appleto.Vitl.retrofit2.ApiInterface;
import com.appleto.Vitl.utills.Storage;
import com.appleto.Vitl.utills.Utils;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private Storage storage;
    RecyclerView rv_reviews;
    TextView tv_rating, tv_totalreview;

    ImageView iv_image, img_pluse;

    GetRatingResponse response;
    List<GetRatingResponse.Datum> list;

    String itemid, image;

    public ReviewsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);

        storage = new Storage(context);

        rv_reviews = view.findViewById(R.id.rv_reviews);
        iv_image = view.findViewById(R.id.iv_image);
        img_pluse = view.findViewById(R.id.img_pluse);
        tv_rating = view.findViewById(R.id.tv_rating);
        tv_totalreview = view.findViewById(R.id.tv_totalreview);


        itemid = getArguments().getString("itemid");
        image = getArguments().getString("itemimage");
        Glide.with(context).load(image).into(iv_image);


        img_pluse.setOnClickListener(this);

        getrating();
        return view;
    }

    private void getrating() {
        Utils.progress_show(context);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService
                .getrating(itemid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetRatingResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onSuccess(GetRatingResponse getRatingResponse) {
                        if (getRatingResponse.getStatus() == 1) {

                            response = getRatingResponse;

                            String rat = Float.toString(response.getRating());
                            tv_rating.setText(rat);

                            tv_totalreview.setText("from " + response.getTotalRating() + " reviews");

                            if (getRatingResponse.getData() != null) {


                                list = getRatingResponse.getData();

                                LinearLayoutManager linearLayout = new LinearLayoutManager(context);
                                rv_reviews.setLayoutManager(linearLayout);
                                ReviewsAdapter reviewsAdapter = new ReviewsAdapter(context, list);
                                rv_reviews.setAdapter(reviewsAdapter);

                            }

                        } else {
                            Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View view) {

        if (view == img_pluse) {

            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_rating);

            final RatingBar rating = dialog.findViewById(R.id.rating);
            final EditText comment = dialog.findViewById(R.id.edt_comment);
            final Button submit = dialog.findViewById(R.id.btn_submit);
            final ImageView close = dialog.findViewById(R.id.iv_close);

            int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.60);
            int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.90);

            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.setCanceledOnTouchOutside(true);

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }

            });

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    float rat = rating.getRating();
                    String itemcomment = comment.getText().toString().trim();

                    addrating(itemcomment, rat);
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }

    private void addrating(String itemcomment, float rat) {

        Utils.progress_show(context);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService
                .addratng(storage.getUserId(), itemid, itemcomment, String.valueOf(rat))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JsonObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onSuccess(JsonObject response) {
                        if (response.get("status").getAsInt() == 1) {

                            Toast.makeText(context, response.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            getrating();

                        } else {
                            Toast.makeText(context, response.get("message").getAsString(), Toast.LENGTH_SHORT).show();
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
