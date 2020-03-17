package com.appleto.Vitl.fragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.appleto.Vitl.R;
import com.appleto.Vitl.utills.LocationTracker;
import com.appleto.Vitl.utills.Utils;
import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {

    private Context context;

    ImageView ivBack, ivItemPic;
    TextView tvItemName, tvItemQty, tvTotalAmt;
    EditText edtDeleviryAdd;
    Button btnPayNow;
    String itemId, itemName, itemImage, itemQty, totalAmt;

    private LocationTracker locTracker;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;


    public PaymentFragment() {
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
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        locTracker = new LocationTracker(context);

        ivItemPic = view.findViewById(R.id.iv_item_image);
        tvItemName = view.findViewById(R.id.tv_item_name);
        tvItemQty = view.findViewById(R.id.tv_item_qty);
        tvTotalAmt = view.findViewById(R.id.tv_total_amt);
        edtDeleviryAdd = view.findViewById(R.id.edt_delivery_add);
        btnPayNow = view.findViewById(R.id.btn_pay_now);

        if (getArguments() != null) {
            itemId = getArguments().getString("itemid");
            itemName = getArguments().getString("itemname");
            itemImage = getArguments().getString("itemimage");
            itemQty = getArguments().getString("itemqty");
            String prize = getArguments().getString("prize");

            float total = Float.parseFloat(prize) * Integer.parseInt(itemQty);
            totalAmt = String.valueOf(total);

            Glide.with(context).load(itemImage).into(ivItemPic);
            tvItemName.setText(itemName);
            tvItemQty.setText(itemQty);
            tvTotalAmt.setText(totalAmt);
        }

        ivBack = view.findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DropInRequest dropInRequest = new DropInRequest()
//                        .clientToken(mClientToken);
//                startActivityForResult(dropInRequest.getIntent(context), DROP_IN_REQUEST);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, new OrderPlacedFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        /*SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        buildGoogleApiClient();
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    buildGoogleApiClient();
                    mMap.setMyLocationEnabled(true);
                }
                setMarkerPoint(new LatLng(locTracker.getLatitude(), locTracker.getLongitude()));
            }
        });*/

        return view;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        LocationRequest locationRequest = new LocationRequest();
                        locationRequest.setInterval(1000);
                        locationRequest.setFastestInterval(1000);
                        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    private void setMarkerPoint(LatLng point) {
        mMap.clear();
        String address1 = Utils.getAddress(context, point.latitude, point.longitude);
        edtDeleviryAdd.setText(address1);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(point).title("Your Location");
        Marker marker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13f));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                setMarkerPoint(latLng);
            }
        });
    }

    @Override
    public void onDestroyView() {
    /*    if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }*/
        super.onDestroyView();
    }
}
