package com.appleto.Vitl.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.appleto.Vitl.R;
import com.appleto.Vitl.fragment.HomeInnerDetailsFragment;
import com.appleto.Vitl.model.GetNormalItemResponse;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MapAdapters extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<GetNormalItemResponse.Datum> response;


    public MapAdapters(Context context, List<GetNormalItemResponse.Datum> response) {
        this.context = context;
        this.response = response;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.raw_map, parent, false);


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

            holder.ll_view_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HomeInnerDetailsFragment homeInnerDetailsFragment = new HomeInnerDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("itemid", response.get(position).getItemId());
                    bundle.putString("itemname", response.get(position).getItemName());
                    bundle.putString("itemimage", response.get(position).getItemImage());
                    bundle.putString("discountprice", response.get(position).getDiscountPrice());
                    bundle.putString("description", response.get(position).getDescription());
                    bundle.putString("address1", response.get(position).getAddress());
                    bundle.putString("address2", response.get(position).getAddress2());
                    bundle.putString("unitname", response.get(position).getUnit());
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
        TextView tv_itemname, tv_origenal_price, tv_discount;
        ImageView iv_itemimage;


        public ViewHolder(View view) {

            super(view);

            ll_view_details = view.findViewById(R.id.ll_view_details);
            tv_itemname = view.findViewById(R.id.tv_itemname);
            tv_origenal_price = view.findViewById(R.id.tv_origenal_price);
            tv_discount = view.findViewById(R.id.tv_discount);
            iv_itemimage = view.findViewById(R.id.iv_itemimage);

        }

    }

}


