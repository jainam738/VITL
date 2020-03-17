package com.appleto.Vitl.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.appleto.Vitl.R;
import com.appleto.Vitl.model.GetNearByVendorListResponse;
import com.appleto.Vitl.retrofit2.ApiClient;
import com.appleto.Vitl.retrofit2.ApiInterface;
import com.appleto.Vitl.utills.LocationTracker;
import com.appleto.Vitl.utills.Storage;
import com.appleto.Vitl.utills.Utils;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapNewFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    private Context context;
    private Storage storage;

    MapView mapView;
    private GoogleMap googleMap;
    LinearLayout ll_bottom_view_item, ll_favourite_view_item;
    TextView tv_cat_name, tv_cat_desc, tv_viewspecials, tv_distance, tv_openClose_time, tv_ratingValue, tv_totalRating;
    LinearLayout locationView, callView, websiteView;
    ImageView iv_cat_image, iv_favourite_view_item;
    FloatingActionButton fabFilter;


    int clickcount = 0;
    LocationTracker locationTracker;
    List<Marker> markerList = new ArrayList<>();

    double latitude, longitude;


    public MapNewFragment() {
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
        View view = inflater.inflate(R.layout.fragment_map_new, container, false);

        storage = new Storage(context);

        ll_bottom_view_item = view.findViewById(R.id.ll_bottom_view_item);
        locationView = view.findViewById(R.id.ll_location_view);
        ll_favourite_view_item = view.findViewById(R.id.ll_favourite_view_item);
        iv_favourite_view_item = view.findViewById(R.id.iv_favourite_view_item);
        websiteView = view.findViewById(R.id.ll_website_view);
        callView = view.findViewById(R.id.ll_call_view);

        tv_cat_name = view.findViewById(R.id.tv_title);
        tv_cat_desc = view.findViewById(R.id.tv_description);
        iv_cat_image = view.findViewById(R.id.iv_catogery_image);
        tv_viewspecials = view.findViewById(R.id.tv_viewspecials);
        tv_distance = view.findViewById(R.id.tv_distance_away);
        tv_ratingValue = view.findViewById(R.id.tv_rating_value);
//        tv_totalRating = view.findViewById(R.id.tv_totalreview);
        tv_openClose_time = view.findViewById(R.id.tv_open_close_time);

        fabFilter = view.findViewById(R.id.fabFilter);

        locationTracker = new LocationTracker(context);
        latitude = locationTracker.getLatitude();
        longitude = locationTracker.getLongitude();

        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(this);

        fabFilter.setOnClickListener(this);

        return view;
    }

    private void getVendorList() {

        Utils.progress_show(context);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        apiService
                .get_vendor_list(String.valueOf(latitude), String.valueOf(longitude), storage.getUserId())
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
                                insertMarkers(response.getData());
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

    private void insertMarkers(final List<GetNearByVendorListResponse.Datum> list) {
        googleMap.clear();
        markerList.clear();
        for (int i = 0; i < list.size(); i++) {
            LatLng point = new LatLng(Double.parseDouble(list.get(i).getLatitude()), Double.parseDouble(list.get(i).getLongitude()));

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(point).title(list.get(i).getCompanyName());
            Marker marker = googleMap.addMarker(markerOptions);
            markerList.add(marker);

            drawMarker(point);
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13f));
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                for (int i = 0; i < markerList.size(); i++) {
                    if (marker.equals(markerList.get(i))) {
                        clickcount++;
                        if ((clickcount % 2) == 0) {
                            ll_bottom_view_item.setVisibility(View.GONE);
                        } else {
                            ll_bottom_view_item.setVisibility(View.VISIBLE);
                            tv_cat_name.setText(list.get(i).getCompanyName());
                            tv_ratingValue.setText(list.get(i).getTotalStar());
//                            tv_totalRating.setText(" from " + list.get(i).getTotalRating() + " reviews");
                            String openTime = list.get(i).getOpenTime().substring(0, 5);
                            String closeTime = list.get(i).getCloseTime().substring(0, 5);
                            tv_openClose_time.setText(openTime + "-" + closeTime);

                            final double lat1 = locationTracker.getLatitude();
                            final double long1 = locationTracker.getLongitude();
                            final double lat2 = Double.parseDouble(list.get(i).getLatitude());
                            final double long2 = Double.parseDouble(list.get(i).getLongitude());
                            double distance = Utils.distance(lat1, long1, lat2, long2);
                            tv_distance.setText(new DecimalFormat("##.##").format(distance) + " Km");

                            if (!list.get(i).getVendorImage().equals("")) {
                                Glide.with(context).load(list.get(i).getVendorImage()).into(iv_cat_image);
                            }

                            if (list.get(i).getIsFavourite() == 1) {
                                ll_favourite_view_item.setBackground(getResources().getDrawable(R.drawable.circle_image, null));
                                iv_favourite_view_item.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_24dp, null));
                            } else {
                                ll_favourite_view_item.setBackground(getResources().getDrawable(R.drawable.circle_image_grey, null));
                                iv_favourite_view_item.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp, null));
                            }

                            locationView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat2 + "+" + long2);
                                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                    mapIntent.setPackage("com.google.android.apps.maps");
                                    if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                        startActivity(mapIntent);
                                    }
                                }
                            });

                            final int finalI = i;
                            tv_viewspecials.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    HomeDetailFragment homeDetailFragment = new HomeDetailFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("vendorid", list.get(finalI).getVendorId());
                                    homeDetailFragment.setArguments(bundle);

                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.frame, homeDetailFragment)
                                            .addToBackStack(null)
                                            .commit();
                                }
                            });

                            callView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        Intent intent = new Intent(Intent.ACTION_DIAL);
                                        intent.setData(Uri.parse("tel:" + list.get(finalI).getBusinessPhoneNumber()));
                                        startActivity(intent);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            websiteView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (!list.get(finalI).getWebLink().equalsIgnoreCase("")) {
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(Uri.parse(list.get(finalI).getWebLink()));
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(context, "Web link not found!!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }
                return false;
            }
        });
    }

    private void drawMarker(LatLng point) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(point);
        googleMap.addMarker(markerOptions);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        googleMap = mMap;
        try {
            if (storage.getUserId() != null) {
                getVendorList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == fabFilter) {
            showFilterDialog();
        }
    }

    private void showFilterDialog() {
        BottomSheetFilterFragment bottomSheetFragment = new BottomSheetFilterFragment();
        if (getFragmentManager() != null) {
            bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
        }
    }
}
