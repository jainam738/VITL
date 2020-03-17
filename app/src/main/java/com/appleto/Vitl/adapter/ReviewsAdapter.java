package com.appleto.Vitl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appleto.Vitl.R;
import com.appleto.Vitl.model.GetRatingResponse;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<GetRatingResponse.Datum> list;

    public ReviewsAdapter(Context context, List<GetRatingResponse.Datum> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.raw_reviews, parent, false);


        // set the view's size, margins, paddings and layout parameters
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof ViewHolder) {
            final ViewHolder holder = (ViewHolder) viewHolder;

            holder.tv_usernmae.setText(list.get(position).getName());
            holder.tv_rating.setText(list.get(position).getStar());
            holder.tv_comment.setText(list.get(position).getComment());

            Glide.with(context).load(list.get(position).getProfileImage()).into(holder.iv_profile);

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
        CircleImageView iv_profile;
        TextView tv_usernmae, tv_rating, tv_comment;

        public ViewHolder(View view) {

            super(view);

            iv_profile = view.findViewById(R.id.iv_profile);
            tv_usernmae = view.findViewById(R.id.tv_usernmae);
            tv_rating = view.findViewById(R.id.tv_rating);
            tv_comment = view.findViewById(R.id.tv_comment);

        }

    }

}


