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

import androidx.fragment.app.Fragment;

import com.appleto.Vitl.R;
import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 */
public class DescriptionFragment extends Fragment implements View.OnClickListener {

    private Context context;
    ImageView iv_share;
    TextView tv_buy, tv_itemname, tv_discountprice, tv_description, tv_quantity, tv_address1, tv_address2;
    ImageView iv_itemimage, iv_remove, iv_add;
    String itemname, itemdiscountprice, desc, image, address1, address2, unit;


    final int[] count = {0};

    public DescriptionFragment() {
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
        View view = inflater.inflate(R.layout.fragment_description, container, false);

        itemname = getArguments().getString("itemname");
        itemdiscountprice = getArguments().getString("discountprice");
        desc = getArguments().getString("description");
        image = getArguments().getString("itemimage");
        address1 = getArguments().getString("address1");
        address2 = getArguments().getString("address2");
        unit = getArguments().getString("unit");

        iv_share = view.findViewById(R.id.iv_share);
        tv_buy = view.findViewById(R.id.tv_buy);
        tv_itemname = view.findViewById(R.id.tv_itemname);
        tv_discountprice = view.findViewById(R.id.tv_discountprice);
        tv_description = view.findViewById(R.id.tv_description);
        iv_itemimage = view.findViewById(R.id.iv_itemimage);
        tv_quantity = view.findViewById(R.id.tv_quantity);
        iv_remove = view.findViewById(R.id.iv_remove);
        iv_add = view.findViewById(R.id.iv_add);
        tv_address1 = view.findViewById(R.id.tv_address1);
        tv_address2 = view.findViewById(R.id.tv_address2);

        tv_itemname.setText(itemname);
        tv_discountprice.setText("$" + itemdiscountprice + unit);
        tv_description.setText(desc);
        tv_address1.setText(address1);
        tv_address2.setText(address2);

        Glide.with(context).load(image).into(iv_itemimage);

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                count[0]++;
                tv_quantity.setText(String.valueOf(count[0]));
            }
        });
        iv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (count[0] == 0) {

                    Toast.makeText(context, "item removed", Toast.LENGTH_SHORT).show();

                } else {

                    count[0]--;
                    tv_quantity.setText(String.valueOf(count[0]));
                }
            }
        });

        iv_share.setOnClickListener(this);
        tv_buy.setOnClickListener(this);

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

        } else if (view == tv_buy) {


        }

    }
}
