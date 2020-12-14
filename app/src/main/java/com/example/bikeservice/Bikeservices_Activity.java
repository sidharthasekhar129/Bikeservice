package com.example.bikeservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Bikeservices_Activity extends AppCompatActivity {

    private TextView onex,twox,threex,fourx,fivex,sixx,sevenx;
    String biketype,mailx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bikeservices_);

        onex=(TextView)findViewById(R.id.one);
        twox=(TextView)findViewById(R.id.two);
        threex=(TextView)findViewById(R.id.three);
        fourx=(TextView)findViewById(R.id.four);
        fivex=(TextView)findViewById(R.id.five);
        sixx=(TextView)findViewById(R.id.six);
        sevenx=(TextView)findViewById(R.id.seven);

        biketype=getIntent().getStringExtra("biketype");
        mailx=getIntent().getStringExtra("mail");
        onex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Checkout_Activity.class);
                intent.putExtra("mail",mailx);

                intent.putExtra("biketype",biketype);
                intent.putExtra("service","Brake System(Brake cam/pedal)");
                startActivity(intent);
            }
        });
        twox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Checkout_Activity.class);
                intent.putExtra("mail",mailx);
                intent.putExtra("biketype",biketype);
                intent.putExtra("service","Headlamp Focus");
                startActivity(intent);
            }
        });
        threex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Checkout_Activity.class);
                intent.putExtra("mail",mailx);
                intent.putExtra("biketype",biketype);
                intent.putExtra("service","Clutch Lever Free Play");
                startActivity(intent);
            }
        });
        fourx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Checkout_Activity.class);
                intent.putExtra("mail",mailx);
                intent.putExtra("biketype",biketype);
                intent.putExtra("service","Nut, Bolts and Fasteners");
                startActivity(intent);
            }
        });
        fivex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Checkout_Activity.class);
                intent.putExtra("mail",mailx);
                intent.putExtra("biketype",biketype);
                intent.putExtra("service","Wheel Bearings");
                startActivity(intent);
            }
        });
        sixx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Checkout_Activity.class);
                intent.putExtra("mail",mailx);
                intent.putExtra("biketype",biketype);
                intent.putExtra("service","Front Suspension/oil");
                startActivity(intent);
            }
        });
        sevenx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Checkout_Activity.class);
                intent.putExtra("mail",mailx);
                intent.putExtra("biketype",biketype);
                intent.putExtra("service","Full Servicing");
                startActivity(intent);
            }
        });


    }
}