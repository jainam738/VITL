package com.appleto.Vitl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.appleto.Vitl.R;
import com.appleto.Vitl.model.GetSpecialsItemResponse;
import com.bumptech.glide.Glide;

import java.util.List;


public class ImageSliderAdapter extends PagerAdapter {

    List<GetSpecialsItemResponse.Datum> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public ImageSliderAdapter(Context context, List<GetSpecialsItemResponse.Datum> list) {
        this.context = context;
        this.list = list;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(this.context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = layoutInflater.inflate(R.layout.raw_home_special, container, false);

        ImageView mImageView1 = view.findViewById(R.id.iv_image1);
        TextView itemname1 = view.findViewById(R.id.tv_itemname1);
        TextView itemdesc1 = view.findViewById(R.id.tv_description1);

        itemname1.setText(list.get(position).getItemName());
        itemdesc1.setText(list.get(position).getDescription());
        Glide.with(context).load(list.get(position).getItemImage()).into(mImageView1);


        ImageView mImageView2 = view.findViewById(R.id.iv_image2);
        TextView itemname2 = view.findViewById(R.id.tv_itemname2);
        TextView itemdesc2 = view.findViewById(R.id.tv_description2);

        itemname2.setText(list.get(position + 1).getItemName());
        itemdesc2.setText(list.get(position + 1).getDescription());
        Glide.with(context).load(list.get(position + 1).getItemImage()).into(mImageView2);

        // mImageView.setImageResource(list.get(position).getItemImage());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
