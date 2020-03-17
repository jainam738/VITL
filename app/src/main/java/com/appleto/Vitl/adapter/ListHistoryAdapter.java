package com.appleto.Vitl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appleto.Vitl.R;
import com.appleto.Vitl.model.GetUserItemResponse;
import com.appleto.Vitl.model.GetVendorItemsListResponse;
import com.bumptech.glide.Glide;

import java.util.List;


public class ListHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<GetVendorItemsListResponse.Datum> list;

    public ListHistoryAdapter(Context context, List<GetVendorItemsListResponse.Datum> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.raw_list_histry_details, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof ViewHolder) {
            final ViewHolder holder = (ViewHolder) viewHolder;

            holder.tv_itemname.setText(list.get(position).getItemName());
            holder.tv_origenal_price.setText("$" + list.get(position).getOriginalPrice());
            holder.tv_discount.setText("$" + list.get(position).getDiscountPrice() + list.get(position).getUnit());

            holder.tv_origenal_price.setBackgroundResource(R.drawable.strike_through);
            Glide.with(context).load(list.get(position).getItemImage()).into(holder.iv_itemimage);
        }
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        LinearLayout ll_view_details;
        TextView tv_itemname, tv_origenal_price, tv_discount;
        ImageView iv_itemimage;

        public ViewHolder(View view) {
            super(view);
            ll_view_details = view.findViewById(R.id.ll_view_details);
            tv_origenal_price = view.findViewById(R.id.tv_origenal_price);
            tv_discount = view.findViewById(R.id.tv_discount);
            tv_itemname = view.findViewById(R.id.tv_itemname);
            iv_itemimage = view.findViewById(R.id.iv_itemimage);

        }
    }
}


