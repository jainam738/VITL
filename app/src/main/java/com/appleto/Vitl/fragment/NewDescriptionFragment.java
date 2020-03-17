package com.appleto.Vitl.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.appleto.Vitl.R;
import com.appleto.Vitl.retrofit2.ApiClient;
import com.appleto.Vitl.retrofit2.ApiInterface;
import com.appleto.Vitl.utills.Storage;
import com.appleto.Vitl.utills.Utils;
import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewDescriptionFragment extends Fragment implements View.OnClickListener {

    private Context context;
    ImageView iv_share, iv_back;
    TextView tv_buy, tv_itemname, tv_discountprice, tv_description, tv_quantity;
    ImageView iv_itemimage, iv_remove, iv_add;
    String venderid, itemId, itemname, itemdiscountprice, itemdescription, itemimg, unit;

    int qty;
    private Storage storage;

    public NewDescriptionFragment() {
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
        View view = inflater.inflate(R.layout.fragment_new_description, container, false);

        storage = new Storage(context);

        venderid = getArguments().getString("venderid");
        itemId = getArguments().getString("itemid");
        itemname = getArguments().getString("itemname");
        itemimg = getArguments().getString("itemimage");
        itemdiscountprice = getArguments().getString("discountprice");
        itemdescription = getArguments().getString("description");
        unit = getArguments().getString("unitname");
        qty = Integer.parseInt(getArguments().getString("itnameqty"));

        iv_share = view.findViewById(R.id.iv_share);
        tv_buy = view.findViewById(R.id.tv_buy);
        tv_itemname = view.findViewById(R.id.tv_itemname);
        tv_discountprice = view.findViewById(R.id.tv_discountprice);
        tv_description = view.findViewById(R.id.tv_description);
        iv_itemimage = view.findViewById(R.id.iv_itemimage);
        tv_quantity = view.findViewById(R.id.tv_quantity);
        iv_remove = view.findViewById(R.id.iv_remove);
        iv_add = view.findViewById(R.id.iv_add);
        iv_back = view.findViewById(R.id.iv_back);

        tv_itemname.setText(itemname);
        tv_discountprice.setText("$" + itemdiscountprice + unit);
        tv_description.setText(itemdescription);

        Glide.with(context).load(itemimg).into(iv_itemimage);

        tv_quantity.setText(String.valueOf(qty));

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty++;
                tv_quantity.setText(String.valueOf(qty));
            }
        });
        iv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qty == 0) {
                    Toast.makeText(context, "item removed", Toast.LENGTH_SHORT).show();
                } else {
                    qty--;
                    tv_quantity.setText(String.valueOf(qty));
                }
            }
        });

        iv_share.setOnClickListener(this);
        tv_buy.setOnClickListener(this);
        iv_back.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == iv_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareBodyText = "Your shearing message goes here";
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject/Title");
            intent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(intent, "Choose sharing method"));

        } else if (view == iv_back) {
            getActivity().getSupportFragmentManager().popBackStack();

        } else if (view == tv_buy) {
            if (qty == 0) {
                Toast.makeText(context, "Please select at least 1 item", Toast.LENGTH_SHORT).show();
            } else {
                JsonArray itemArray = new JsonArray();
                JsonObject object = new JsonObject();
                object.addProperty("item_id", itemId);
                object.addProperty("qty", qty);
                float amount = Float.parseFloat(itemdiscountprice) * qty;
                object.addProperty("amount", String.valueOf(amount));
                itemArray.add(object);
                placeOrder(itemArray, String.valueOf(amount));
            }
        }

    }

    private void placeOrder(JsonArray itemArray, String totalAmt) {
        Utils.progress_show(context);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        apiService
                .add_order(storage.getUserId(), venderid, String.valueOf(totalAmt), itemArray.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JsonObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(JsonObject response) {
                        if (response.get("status").getAsInt() == 1) {
                            PaymentFragment fragment = new PaymentFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("itemid", itemId);
                            bundle.putString("itemname", itemname);
                            bundle.putString("itemimage", itemimg);
                            bundle.putString("itemqty", String.valueOf(qty));
                            bundle.putString("prize", itemdiscountprice);
                            fragment.setArguments(bundle);

                            ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frame, fragment)
                                    .addToBackStack(null)
                                    .commit();
                        } else {
                            Toast.makeText(context, "Problem occured while placing order. Try again", Toast.LENGTH_SHORT).show();
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
