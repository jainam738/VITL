package com.appleto.Vitl.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.appleto.Vitl.R;
import com.appleto.Vitl.fragment.HomeDetailFragment;
import com.appleto.Vitl.model.GetCategoryResponse;
import com.bumptech.glide.Glide;

import java.util.List;


public class FilterCateogryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<GetCategoryResponse.Datum> response;

    public interface onFilterClickListener {
        void onItemClick(String categoryId);
    }

    onFilterClickListener onFilterClickListener;


    public FilterCateogryAdapter(Context context, List<GetCategoryResponse.Datum> response, onFilterClickListener onFilterClickListener) {
        this.context = context;
        this.response = response;
        this.onFilterClickListener = onFilterClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.raw_filter_category, parent, false);


        // set the view's size, margins, paddings and layout parameters
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof ViewHolder) {
            final ViewHolder holder = (ViewHolder) viewHolder;

            holder.tvName.setText(response.get(position).getCategoryName());
            if (response.get(position).getIs_category_active() == 1) {
                holder.frame.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_image));
            } else {
                holder.frame.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_image_grey));
            }

            holder.frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (response.get(position).getIs_category_active() == 1) {
                        holder.frame.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_image_grey));
                        response.get(position).setIs_category_active(0);
                    } else {
                        response.get(position).setIs_category_active(1);
                        holder.frame.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_image));
                    }
                    notifyDataSetChanged();
                    onFilterClickListener.onItemClick(response.get(position).getCategoryId());
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
        TextView tvName;
        ImageView ivImage;
        FrameLayout frame;

        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_item_name);
            ivImage = view.findViewById(R.id.iv_image);
            frame = view.findViewById(R.id.frame);
        }

    }

}


