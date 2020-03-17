package com.appleto.Vitl.fragment;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appleto.Vitl.R;
import com.appleto.Vitl.adapter.HomeDetailsAdapter;
import com.appleto.Vitl.model.GetVendorItemsListResponse;
import com.appleto.Vitl.retrofit2.ApiClient;
import com.appleto.Vitl.retrofit2.ApiInterface;
import com.appleto.Vitl.utills.Storage;
import com.appleto.Vitl.utills.Utils;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeDetailFragment extends Fragment implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private Context context;
    private Storage storage;

    ImageView iv_back, iv_filter, iv_view;
    RecyclerView rv_details, rv_map;
    FrameLayout fram_map;
    TextView tv_buy;
    int minvalue, maxvalue;
    List<GetVendorItemsListResponse.Datum> vendorItemsList;
    List<GetVendorItemsListResponse.Datum> filterVendorItemList;

    String vendorId;
    String viewid;

    int count = 0;

    SupportMapFragment mapFragment;
    private Marker marker;
    GoogleMap mGoogleMap;
    double latitude = 0;
    double longitude = 0;
    Location lastLocation;
    GoogleApiClient mGoogleApiClient;
    FusedLocationProviderClient mFusedLocationClient;

    public HomeDetailFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home_detail, container, false);

        storage = new Storage(context);
        vendorId = getArguments().getString("vendorid");

        tv_buy = view.findViewById(R.id.tv_buy);
        iv_back = view.findViewById(R.id.iv_back);
        iv_view = view.findViewById(R.id.iv_view);
        rv_details = view.findViewById(R.id.rv_details);
        rv_map = view.findViewById(R.id.rv_map);
        fram_map = view.findViewById(R.id.fram_map);
        iv_filter = view.findViewById(R.id.iv_filter);

        tv_buy.setVisibility(View.GONE);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        iv_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if ((count % 3) == 0) {
                    viewid = "1";
                    rv_details.setVisibility(View.VISIBLE);
                    rv_map.setVisibility(View.GONE);
                    fram_map.setVisibility(View.GONE);
                    getVendorItem();

                } else if ((count % 3) == 1) {
                    viewid = "2";
                    rv_details.setVisibility(View.VISIBLE);
                    rv_map.setVisibility(View.GONE);
                    fram_map.setVisibility(View.GONE);
                    getVendorItem();

                } else {
                    viewid = "3";
                    rv_details.setVisibility(View.GONE);
                    rv_map.setVisibility(View.VISIBLE);
                    fram_map.setVisibility(View.VISIBLE);
                    getVendorItem();
                }
            }
        });
        iv_back.setOnClickListener(this);
        iv_filter.setOnClickListener(this);
        tv_buy.setOnClickListener(this);

        getVendorItem();

        return view;
    }

    private void getVendorItem() {

        Utils.progress_show(context);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService
                .get_vendor_items(vendorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GetVendorItemsListResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onSuccess(GetVendorItemsListResponse response) {
                        if (response.getStatus() == 1) {

                            if (response.getData() != null) {
                                vendorItemsList = new ArrayList<>();
                                LinearLayoutManager linearLayout = new LinearLayoutManager(context);
                                rv_details.setLayoutManager(linearLayout);
                                double minValue = Double.parseDouble(storage.getMinValue());
                                double maxValue = Double.parseDouble(storage.getMaxValue());

                                for (int i = 0; i < response.getData().size(); i++) {
                                    double prize = Double.parseDouble(response.getData().get(i).getDiscountPrice());
                                    if(prize > minValue && prize < maxValue){
                                        vendorItemsList.add(response.getData().get(i));
                                    }
                                }

                                if(vendorItemsList.size() > 0) {
                                    HomeDetailsAdapter homeSpecialAdapter = new HomeDetailsAdapter(context, vendorItemsList);
                                    rv_details.setAdapter(homeSpecialAdapter);
                                }

                                /*if (viewid == "2") {
                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
                                    rv_details.setLayoutManager(gridLayoutManager);
                                    HomeDetailsAdapter homeSpecialAdapter2 = new HomeDetailsAdapter(context, response);
                                    rv_details.setAdapter(homeSpecialAdapter2);

                                } else if (viewid == "1") {
                                    LinearLayoutManager linearLayout1 = new LinearLayoutManager(context);
                                    rv_details.setLayoutManager(linearLayout1);
                                    HomeDetailsAdapter homeSpecialAdapter1 = new HomeDetailsAdapter(context, response);
                                    rv_details.setAdapter(homeSpecialAdapter1);

                                } else if (viewid == "3") {
                                    LinearLayoutManager linearLayout1 = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
                                    rv_map.setLayoutManager(linearLayout1);
                                    MapAdapters homeSpecialAdapter1 = new MapAdapters(context, response);
                                    rv_map.setAdapter(homeSpecialAdapter1);
                                }*/
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

    @Override
    public void onClick(View view) {

        if (view == iv_back) {
            getActivity().getSupportFragmentManager().popBackStack();

        } else if (view == tv_buy) {
            Toast.makeText(context, "work in progress...", Toast.LENGTH_SHORT).show();

        } else if (view == iv_filter) {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_filter);

            final CrystalRangeSeekbar rangeSeekbar = dialog.findViewById(R.id.rangeSeekbar);
            final TextView tvMin = dialog.findViewById(R.id.textMin);
            final TextView tvMax = dialog.findViewById(R.id.textMax);
            final Button apply = dialog.findViewById(R.id.btn_apply);
            final Button clear = dialog.findViewById(R.id.btn_clearfilter);
            final ImageView close = dialog.findViewById(R.id.iv_close);

            int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.60);
            int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.90);

            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setGravity(Gravity.CENTER);

            dialog.setCanceledOnTouchOutside(true);

            rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                @Override
                public void valueChanged(Number minValue, Number maxValue) {
                    tvMin.setText(String.valueOf(minValue));
                    tvMax.setText(String.valueOf(maxValue));

                }
            });

            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filterVendorItemList = new ArrayList<>();
                    minvalue = Integer.parseInt(String.valueOf(tvMin.getText()));
                    maxvalue = Integer.parseInt(String.valueOf(tvMax.getText()));
                    for (int i = 0; i < vendorItemsList.size(); i++) {
                        if (minvalue <= Integer.parseInt(vendorItemsList.get(i).getDiscountPrice()) && maxvalue >= Integer.parseInt(vendorItemsList.get(i).getDiscountPrice())) {
                            filterVendorItemList.add(vendorItemsList.get(i));
                        }
                    }
                    filteritem();
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

    }

    private void filteritem() {

        LinearLayoutManager linearLayout = new LinearLayoutManager(context);
        rv_details.setLayoutManager(linearLayout);
        HomeDetailsAdapter homeSpecialAdapter = new HomeDetailsAdapter(context, filterVendorItemList);
        rv_details.setAdapter(homeSpecialAdapter);

        /*if (viewid == "2") {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
            rv_details.setLayoutManager(gridLayoutManager);
            HomeDetailsAdapter homeSpecialAdapter2 = new HomeDetailsAdapter(context, filterVendorItemList);
            rv_details.setAdapter(homeSpecialAdapter2);
        } else if (viewid == "1") {
            LinearLayoutManager linearLayout1 = new LinearLayoutManager(context);
            rv_details.setLayoutManager(linearLayout1);
            HomeDetailsAdapter homeSpecialAdapter1 = new HomeDetailsAdapter(context, filterVendorItemList);
            rv_details.setAdapter(homeSpecialAdapter1);

        } else if (viewid == "3") {
            LinearLayoutManager linearLayout1 = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            rv_map.setLayoutManager(linearLayout1);
            MapAdapters homeSpecialAdapter1 = new MapAdapters(context, filterVendorItemList);
            rv_map.setAdapter(homeSpecialAdapter1);
        }*/


    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


    }

    @Override
    public void onLocationChanged(Location location) {

        lastLocation = location;

        if (marker != null) {
            marker.remove();
        }

//        latitude = location.getLatitude();
//        longitude = location.getLongitude();

        setMarkerPoint(location.getLatitude(), location.getLongitude());

    }

    private void setMarkerPoint(double latitude, double longitude) {
        MarkerOptions dropLocMarkerOpt = new MarkerOptions();
        if (marker != null) {

            marker.remove();
        }

        LatLng latLng = new LatLng(latitude, longitude);
        dropLocMarkerOpt.position(latLng);
        dropLocMarkerOpt.title("Current Position");
        dropLocMarkerOpt.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
        marker = mGoogleMap.addMarker(dropLocMarkerOpt);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 13));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                googleMap.setMyLocationEnabled(true);

            }

        } else {

            buildGoogleApiClient();
            googleMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

}
