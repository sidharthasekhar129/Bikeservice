package com.example.bikeservice;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class HomeFragment extends Fragment {
    private CardView card1,card2,card3,card4;
     public static Context context;
    String mailgetx;
ImageView imageView;
     private static final String keyUpcoming1="upcomingpic1";
    private static final String keyUpcoming2="upcomingpic2";

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_home, container, false);
        imageView=(ImageView)view.findViewById(R.id.headimg);
        setSliderViews();
        return view;


    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        card4=(CardView)getActivity().findViewById(R.id.card4);
        card3=(CardView)getActivity().findViewById(R.id.card3);
        card2=(CardView)getActivity().findViewById(R.id.card2);
        card1=(CardView)getActivity().findViewById(R.id.card1);
        mailgetx=getActivity().getIntent().getStringExtra("mail");
        Log.d("wwwwww",mailgetx);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Bikeservices_Activity.class);
                intent.putExtra("mail",mailgetx);
                 intent.putExtra("biketype","Hero");
                 startActivity(intent);
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, Bikeservices_Activity.class);
                intent.putExtra("mail",mailgetx);
                intent.putExtra("biketype","Honda");
                startActivity(intent);
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Bikeservices_Activity.class);
                intent.putExtra("mail",mailgetx);
                intent.putExtra("biketype","Bajaj");
                startActivity(intent);
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Bikeservices_Activity.class);
                intent.putExtra("mail",mailgetx);
                intent.putExtra("biketype","Suzuki");
                startActivity(intent);
            }
        });



    }

    private void setSliderViews()
    {
         DocumentReference noteref2 = db.collection("Offers").document("Upcoming");
        noteref2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    try {
                        String slide1 = documentSnapshot.getString("upcomingpic1");
                        Glide.with(context).load(slide1).into(imageView);
                    }catch (Exception e){}


                }
            }
        });




    }
}