package com.example.bikeservice;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class BookingFragment extends Fragment {

 private Context context;
 String mailgetx;
 private TextView datex,timex,addressx,biketypex,servicetypex,empty;
 private LinearLayout linearLayout;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    public BookingFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        mailgetx=getActivity().getIntent().getStringExtra("mail");
        datex=(TextView)getActivity().findViewById(R.id.date);
        timex=(TextView)getActivity().findViewById(R.id.time);
        addressx=(TextView)getActivity().findViewById(R.id.address);
        biketypex=(TextView)getActivity().findViewById(R.id.biketype);
        servicetypex=(TextView)getActivity().findViewById(R.id.service);
        empty=(TextView)getActivity().findViewById(R.id.emptytext);
        linearLayout=(LinearLayout) getActivity().findViewById(R.id.orderlayput);
        try {
            Query noteref=db.collection(mailgetx).document("Orders").collection("Ordersdata").
                    orderBy("date", Query.Direction.DESCENDING);
            noteref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()) {


                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            if (documentSnapshot.exists()){
                                linearLayout.setVisibility(View.VISIBLE);
                                empty.setVisibility(View.GONE);
                                String dat=documentSnapshot.getString("date");
                                String tim=documentSnapshot.getString("time");
                                String addres=documentSnapshot.getString("address");
                                String servicetyp=documentSnapshot.getString("servicetype");
                                String biketyp=documentSnapshot.getString("biketype");


                                datex.setText("Date:"+dat);
                                timex.setText("Time:"+tim);

                                addressx.setText("Address:"+addres);

                                biketypex.setText("Bike Type:"+biketyp);

                                servicetypex.setText("Service Name:"+servicetyp);
                            }


                            //  Log.d("resulys are", dat+tim+addres+servicetyp+biketyp);


                        }



                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
//                    progressBar.setVisibility(View.GONE);
//                    swipeRefresh.setEnabled(true);
//                    errorText.setVisibility(View.VISIBLE);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Exception Alert", e.getMessage());
                }
            });
        }catch (Exception e){
            Log.d("Exception Alert", e.getMessage());

        }




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
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }
}