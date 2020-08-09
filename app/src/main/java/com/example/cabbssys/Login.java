package com.example.cabbssys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cabbssys.AdminSide.Dashboard;
import com.example.cabbssys.UserSide.User_Map;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity
{
    EditText email, password;
    Button login;
    FirebaseAuth mAuth2,mauth;
    TextView signup;
    String e,p;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mauth= FirebaseAuth.getInstance();
        if(mauth.getCurrentUser()!= null)
        {
            Intent in =new Intent(Login.this,User_Map.class);
            startActivity(in);
            finish();
        }
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.l_email);
        password = findViewById(R.id.l_password);
        login = findViewById(R.id.l_login);
        mAuth2 = FirebaseAuth.getInstance();
        signup = findViewById(R.id.l_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 e = email.getText().toString();
                 p = password.getText().toString();
                if (e.isEmpty()) {
                    email.setError("Please enter email");
                    email.requestFocus();
                } else if (p.isEmpty()) {
                    password.setError("Please enter email");
                    password.requestFocus();
                } else if (p.isEmpty() && e.isEmpty()) {
                    email.setError("Enter email");
                    password.setError("Enter password");
                } else if (p.length() < 6) {
                    password.setError("Enter a password longer than 6 characters");
                }
                else {
                    mAuth2.signInWithEmailAndPassword(e, p)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("e ki value",""+e+"-->"+p);
                                        Toast.makeText(Login.this, "e=>"+e+"<="+p, Toast.LENGTH_SHORT).show();
                                        if(!(e.equalsIgnoreCase("admin@gmail.com") &&  p.equalsIgnoreCase("admin@123")))
                                        {
                                            Intent ina = new Intent(Login.this, User_Map.class);
                                            startActivity(ina);
                                            finish();
                                        }
                                        else {
                                            Intent in = new Intent(Login.this, Dashboard.class);
                                            startActivity(in);
                                            finish();
                                        }
                                    } else {
                                        Toast.makeText(Login.this, "Wrong Email or Password. Please Do check", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}