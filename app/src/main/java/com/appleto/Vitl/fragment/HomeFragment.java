package com.appleto.Vitl.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appleto.Vitl.R;
import com.appleto.Vitl.adapter.HomeAdapter;
import com.appleto.Vitl.adapter.HomeSpecialAdapter;
import com.appleto.Vitl.model.GetCategoryResponse;
import com.appleto.Vitl.model.GetSpecialsItemResponse;
import com.appleto.Vitl.retrofit2.ApiClient;
import com.appleto.Vitl.retrofit2.ApiInterface;
import com.appleto.Vitl.utills.Storage;
import com.appleto.Vitl.utills.Utils;
import com.rd.PageIndicatorView;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.angeldevil.autoscrollviewpager.AutoScrollViewPager;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private Storage storage;

    RecyclerView rv_home, rv_home_specials;
    AutoScrollViewPager scroll_pager;
    PageIndicatorView pageIndicatorView;
    HomeSpecialAdapter homeSpecialAdapter;
    ImageView iv_notification, iv_search;

    int autoscrollposition = 0;

    List<GetCategoryResponse.Datum> response;
    List<GetSpecialsItemResponse.Datum> list;

    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        storage = new Storage(context);

        iv_notification = view.findViewById(R.id.iv_notification);
        iv_search = view.findViewById(R.id.iv_search);
        rv_home = view.findViewById(R.id.rv_home);
        rv_home_specials = view.findViewById(R.id.rv_home_specials);
        scroll_pager = view.findViewById(R.id.scroll_pager);

        iv_search.setOnClickListener(this);
        iv_notification.setOnClickListener(this);

        getcategory();
        getspecials();

        return view;
    }

    private void getcategory() {
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

                            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
                            rv_home.setLayoutManager(gridLayoutManager);
                            HomeAdapter homeAdapter = new HomeAdapter(context, response);
                            rv_home.setAdapter(homeAdapter);


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

    private void getspecials() {
        Utils.progress_show(context);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService
                .getspecialitem(storage.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetSpecialsItemResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(GetSpecialsItemResponse getSpecialsItemResponse) {
                        if (getSpecialsItemResponse.getStatus() == 1) {

                            if (getSpecialsItemResponse.getData() != null) {

                                list = getSpecialsItemResponse.getData();

//                                ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(context, list);
//                                scroll_pager.setAdapter(imageSliderAdapter);
//                                scroll_pager.startAutoScroll(1500);

                                LinearLayoutManager linearLayout = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
                                rv_home_specials.setLayoutManager(linearLayout);
                                homeSpecialAdapter = new HomeSpecialAdapter(context, list);
                                rv_home_specials.setAdapter(homeSpecialAdapter);

                                rv_home_specials.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        for (int i = 0; i < list.size() - 1; i++) {
                                            if (i == list.size() - 1) {

                                                autoscrollposition = 0;

                                                homeSpecialAdapter.notifyItemChanged(i);

                                            } else {

                                                autoscrollposition = i;

                                                homeSpecialAdapter.notifyItemChanged(i);

                                            }
                                        }

                                        rv_home_specials.smoothScrollToPosition(autoscrollposition);
                                    }

                                }, 1500);

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

        if (view == iv_notification) {

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame, new NotificationFragment())
                    .addToBackStack(null)
                    .commit();

        } else if (view == iv_search) {

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame, new SearchFragment())
                    .addToBackStack(null)
                    .commit();

        }

    }
}
