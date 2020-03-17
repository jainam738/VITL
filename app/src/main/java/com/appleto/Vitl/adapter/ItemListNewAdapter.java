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
import com.appleto.Vitl.fragment.HomeDetailFragment;
import com.appleto.Vitl.model.GetNearByVendorListResponse;
import com.bumptech.glide.Glide;

import java.util.List;


public class ItemListNewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<GetNearByVendorListResponse.Datum> response;


    public ItemListNewAdapter(Context context, List<GetNearByVendorListResponse.Datum> response) {
        this.context = context;
        this.response = response;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.raw_mapitemlist, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof ViewHolder) {
            final ViewHolder holder = (ViewHolder) viewHolder;

            holder.tv_title.setText(response.get(position).getCompanyName());
            Glide.with(context).load(response.get(position).getVendorImage()).into(holder.iv_catogery_image);

            holder.tv_rating.setText(response.get(position).getTotalStar());
            holder.tv_review.setText(" from " + response.get(position).getTotalRating() + " reviews");

            holder.tv_viewspecials.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeDetailFragment homeDetailFragment = new HomeDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("vendorid", response.get(position).getVendorId());
                    homeDetailFragment.setArguments(bundle);

                    ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame, homeDetailFragment)
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
        ImageView iv_catogery_image;
        LinearLayout ll_view;
        TextView tv_title, tv_description, tv_viewspecials, tv_rating, tv_review;


        public ViewHolder(View view) {
            super(view);
            ll_view = view.findViewById(R.id.ll_view);
            iv_catogery_image = view.findViewById(R.id.iv_catogery_image);
            tv_title = view.findViewById(R.id.tv_title);
            tv_description = view.findViewById(R.id.tv_description);
            tv_viewspecials = view.findViewById(R.id.tv_viewspecials);
            tv_rating = view.findViewById(R.id.tv_rating);
            tv_review = view.findViewById(R.id.tv_totalreview);
        }
    }
}


