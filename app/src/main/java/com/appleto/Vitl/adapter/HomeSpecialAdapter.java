package com.appleto.Vitl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appleto.Vitl.R;
import com.appleto.Vitl.model.GetSpecialsItemResponse;
import com.bumptech.glide.Glide;

import java.util.List;

public class HomeSpecialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<GetSpecialsItemResponse.Datum> list;


    public HomeSpecialAdapter(Context context, List<GetSpecialsItemResponse.Datum> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.raw_home_special, parent, false);


        // set the view's size, margins, paddings and layout parameters
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof ViewHolder) {
            final ViewHolder holder = (ViewHolder) viewHolder;

            holder.tv_itemname.setText(list.get(position).getItemName());
            holder.tv_description.setText(list.get(position).getDescription());

            Glide.with(context).load(list.get(position).getItemImage()).into(holder.iv_image);

//            holder.ivProfile.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
////                    HotTagsDetailsFragment hotTagsDetailsFragment  = new HotTagsDetailsFragment();
////                    // Bundle bundle = new Bundle();
////                    //bundle.putString("set_id", responses.get(position).getSetId());
////                    //  fragment.setArguments(bundle);
////
////                    ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
////                            .addToBackStack(null)
////                            .replace(R.id.frame, hotTagsDetailsFragment)
////                            .commit();
//
////
////                Intent intent=new Intent(context, CommentActivity.class);
////                context.startActivity(intent);
//
//
//                }
//            });

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
        ImageView iv_image;
        TextView tv_itemname, tv_description;


        public ViewHolder(View view) {

            super(view);

            iv_image = view.findViewById(R.id.iv_image1);
            tv_itemname = view.findViewById(R.id.tv_itemname1);
            tv_description = view.findViewById(R.id.tv_description1);

        }

    }

}


