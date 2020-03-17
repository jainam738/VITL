package com.appleto.Vitl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appleto.Vitl.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;


    public MapAdapter(Context context) {
        this.context = context;

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

            //   Glide.with(context).load(list.get(position).getProfileImage()).into(holder.ivProfile);

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

        return 8;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CircleImageView ivProfile;


        public ViewHolder(View view) {

            super(view);

            //  ivProfile = view.findViewById(R.id.ivProfilepastcall);

        }

    }

}


