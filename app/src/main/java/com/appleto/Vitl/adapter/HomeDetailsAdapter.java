package com.appleto.Vitl.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.appleto.Vitl.R;
import com.appleto.Vitl.fragment.NewDescriptionFragment;
import com.appleto.Vitl.model.GetVendorItemsListResponse;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<GetVendorItemsListResponse.Datum> response;

    public HomeDetailsAdapter(Context context, List<GetVendorItemsListResponse.Datum> response) {
        this.context = context;
        this.response = response;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.raw_home_detail, parent, false);


        // set the view's size, margins, paddings and layout parameters
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof ViewHolder) {
            final ViewHolder holder = (ViewHolder) viewHolder;
    
            holder.tv_itemname.setText(response.get(position).getItemName());
            holder.tv_origenal_price.setText("$" + response.get(position).getOriginalPrice());
            holder.tv_discount.setText("$" + response.get(position).getDiscountPrice() + response.get(position).getUnit());

            Glide.with(context).load(response.get(position).getItemImage()).into(holder.iv_itemimage);

            holder.tv_origenal_price.setBackgroundResource(R.drawable.strike_through);
            //holder.tv_distance.setText(new DecimalFormat("##.##").format("3.22")+" km");

            final int[] count = {0};

            holder.iv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    count[0]++;
                    holder.tv_quantity.setText(String.valueOf(count[0]));
                }
            });
            holder.iv_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (count[0] == 0) {
                        Toast.makeText(context, "item removed", Toast.LENGTH_SHORT).show();
                    } else {
                        count[0]--;
                        holder.tv_quantity.setText(String.valueOf(count[0]));
                    }
                }
            });

            holder.iv_itemimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewDescriptionFragment homeInnerDetailsFragment = new NewDescriptionFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("venderid", response.get(position).getVendorId());
                    bundle.putString("itemid", response.get(position).getItemId());
                    bundle.putString("itemname", response.get(position).getItemName());
                    bundle.putString("itemimage", response.get(position).getItemImage());
                    bundle.putString("discountprice", response.get(position).getDiscountPrice());
                    bundle.putString("description", response.get(position).getDescription());
                    bundle.putString("unitname", response.get(position).getUnit());
                    bundle.putString("itnameqty", holder.tv_quantity.getText().toString().trim());
                    homeInnerDetailsFragment.setArguments(bundle);

                    ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame, homeInnerDetailsFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });

        }
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    @Override
    public int getItemCount() {

        return response.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CircleImageView ivProfile;
        LinearLayout ll_view_details;
        TextView tv_itemname, tv_origenal_price, tv_discount, tv_quantity, tv_distance;
        ImageView iv_itemimage, iv_add, iv_remove;

        public ViewHolder(View view) {

            super(view);

            ll_view_details = view.findViewById(R.id.ll_view_details);
            tv_itemname = view.findViewById(R.id.tv_itemname);
            tv_origenal_price = view.findViewById(R.id.tv_origenal_price);
            tv_discount = view.findViewById(R.id.tv_discount);
            iv_itemimage = view.findViewById(R.id.iv_itemimage);
            iv_add = view.findViewById(R.id.iv_add);
            iv_remove = view.findViewById(R.id.iv_remove);
            tv_quantity = view.findViewById(R.id.tv_quantity);
            tv_distance = view.findViewById(R.id.raw_tv_distance);

        }

    }

}


