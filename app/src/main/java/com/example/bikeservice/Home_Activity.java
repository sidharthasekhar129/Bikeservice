package com.example.bikeservice;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.os.Bundle;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home_Activity extends AppCompatActivity {
    String mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);

          mail=getIntent().getStringExtra("mail");

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            item -> {
                Fragment selectedfragment=null;
                switch (item.getItemId()){
                    case R.id.navigation_home:
                       selectedfragment=new HomeFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("mail", mail);
                        selectedfragment.setArguments(bundle);
                        break;
                    case R.id.navigation_booking:
                        selectedfragment=new BookingFragment();
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("mail", mail);
                        selectedfragment.setArguments(bundle1);
                        break;
                    case R.id.navigation_help:
                        selectedfragment=new HelpFragment();
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("mail", mail);
                        selectedfragment.setArguments(bundle2);
                        break;
                    case R.id.navigation_profile:
                        selectedfragment=new ProfileFragment();
                        Bundle bundle3= new Bundle();
                        bundle3.putString("mail", mail);
                        selectedfragment.setArguments(bundle3);
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedfragment).commit();

                return true;

            };
}