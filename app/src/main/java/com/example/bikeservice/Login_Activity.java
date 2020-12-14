package com.example.bikeservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends AppCompatActivity {
    private TextView signup1;
    private Button login1;
    private FirebaseAuth mAuth;
    private EditText username1,password1;

    String usnx;
    private CheckBox remember;

    private ImageView showpass;

    private boolean show=true;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        mAuth = FirebaseAuth.getInstance();
        signup1=(TextView)findViewById(R.id.signup);
        login1=(Button)findViewById(R.id.login);
        username1=(EditText)findViewById(R.id.ed1);
        password1=(EditText)findViewById(R.id.ed2);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        remember=(CheckBox)findViewById(R.id.remember);
        //showpass=(ImageView)findViewById(R.id.showpassword);

        /*showpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show)
                {
                    password1.setTransformationMethod(null);
                    show=false;
                    showpass.setImageResource(R.drawable.ic_pass_red_eye_);
                }
                else {
                    password1.setTransformationMethod(new PasswordTransformationMethod());
                    show=true;
                    showpass.setImageResource(R.drawable.ic_visibility_off);
                }
            }
        });*/

        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
        String user=preferences.getString("username","");
        username1.setText(user);
        String pass=preferences.getString("passsword","");
        password1.setText(pass);
        String check=preferences.getString("remember","");

        if (check.equals("true"))
        {
            Intent intent=new Intent(getApplicationContext(),Home_Activity.class);
            intent.putExtra("mail",user);

            startActivity(intent);
            finish();
        }
        else if (check.equals("true")) {
            Toast.makeText(getApplicationContext(),"Please sign in",Toast.LENGTH_SHORT).show();

        }

        remember.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isChecked())
            {
                SharedPreferences preferences1 =getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor= preferences1.edit();
                editor.putString("remember","true");
                editor.putString("username",username1.getText().toString());
                editor.putString("password",password1.getText().toString());
                editor.apply();

            }
            else if (!buttonView.isChecked())
            {
                SharedPreferences preferences1 =getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor= preferences1.edit();
                editor.putString("remember","false");
                editor.apply();

            }
        });





        signup1.setOnClickListener(v -> {
            Intent intent=new Intent(getApplicationContext(),SignUp_Activity.class);
            startActivity(intent);
            finish();
        });

        login1.setOnClickListener(v -> userlogin());
    }
    private void userlogin()
    {
        final String username=username1.getText().toString().trim();
        String password=password1.getText().toString().trim();

        if (username.isEmpty())
        {
            username1.setError("Email is required!");
            username1.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(username).matches())
        {
            username1.setError("Valid Email is required!");
            username1.requestFocus();
            return;
        }
        if (password.isEmpty())
        {
            password1.setError("Password is required!");
            password1.requestFocus();
            return;
        }

        if (password.length()<6)
        {
            password1.setError("Minimum length is 6");
            password1.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful())
                {

                    // String mailget=getIntent().getStringExtra("mail");

                    Intent intent=new Intent(getApplicationContext(),Home_Activity.class);
                    intent.putExtra("mail",username);
                    startActivity(intent);

                }
                else {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}