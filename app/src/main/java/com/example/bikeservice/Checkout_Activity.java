package com.example.bikeservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Checkout_Activity extends AppCompatActivity {

    private Button bookmech;
    private EditText address;
    String biketype,servicetype,mailgetx;
    private ProgressBar progressBar;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_);
        bookmech=(Button)findViewById(R.id.bookmechanic);
        address=(EditText)findViewById(R.id.address);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);

        mailgetx=getIntent().getStringExtra("mail");
        biketype=getIntent().getStringExtra("biketype");
        servicetype=getIntent().getStringExtra("service");
        //time
//        Date c = Calendar.getInstance().getTime();
//
//        //date
//        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
//        String formattedDate = df.format(c);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
         Log.d("wwwwww",mailgetx);
        bookmech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                     Map<String, Object> note2=new HashMap<>();
                    note2.put("biketype",biketype);
                    note2.put("servicetype",servicetype);
                    note2.put("date",currentDate);
                    note2.put("time",currentTime);
                    note2.put("address",address.getText().toString());

                    db.collection(mailgetx).document("Orders").collection("Ordersdata").
                            document("aaa").set(note2)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(),"Order Placed successfully",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getApplicationContext(),"Order failed",Toast.LENGTH_SHORT).show();
                                Log.d("TAG",e.toString());
                            });



            }
        });
    }
}