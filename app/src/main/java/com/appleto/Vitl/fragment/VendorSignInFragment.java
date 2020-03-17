package com.appleto.Vitl.fragment;


import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.appleto.Vitl.R;
import com.appleto.Vitl.activity.ChooseYourPlanActivity;
import com.appleto.Vitl.utills.Storage;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendorSignInFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private Storage storage;

    ImageView iv_back;
    Button btn_subcribenow,btn_signin;
    VideoView videoView;

    ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"));

    int index = 0;

    public VendorSignInFragment() {
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
        View view = inflater.inflate(R.layout.fragment_vendor_sign_in, container, false);

        storage = new Storage(context);

        iv_back = view.findViewById(R.id.iv_back);
        btn_subcribenow = view.findViewById(R.id.btn_subcribenow);
        videoView = view.findViewById(R.id.video_view);

        //  btn_signin = findViewById(R.id.btn_signin);

        btn_subcribenow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(context, ChooseYourPlanActivity.class));

            }
        });

        final MediaController mediacontroller = new MediaController(context);
        mediacontroller.setAnchorView(videoView);

        videoView.setMediaController(mediacontroller);
        videoView.setVideoURI(Uri.parse(arrayList.get(index)));
        videoView.requestFocus();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(context, "Video over", Toast.LENGTH_SHORT).show();
                if (index++ == arrayList.size()) {
                    index = 0;
                    mp.release();
                    Toast.makeText(context, "Video over", Toast.LENGTH_SHORT).show();

                } else {
                    videoView.setVideoURI(Uri.parse(arrayList.get(index)));
                    videoView.start();
                }

            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d("API123", "What " + what + " extra " + extra);
                return false;
            }
        });

//        btn_signin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //startActivity(new Intent(context, VendorSIgnInDetailActivity.class));
//
//                startActivity(new Intent(context, VendorLoginActivity.class));
//
//
//
//            }
//        });



        iv_back.setOnClickListener(this);



        return view;
    }

    @Override
    public void onClick(View view) {

        if (view==iv_back){

            getActivity().getSupportFragmentManager().popBackStack();

        }

    }
}
