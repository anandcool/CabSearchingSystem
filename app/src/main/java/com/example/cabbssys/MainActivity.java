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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    EditText email,password;
    Button signup;
    TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.s_email);
        password=findViewById(R.id.s_password);
        signup=findViewById(R.id.s_signup);
        login=findViewById(R.id.s_login);
        mAuth=FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userlogin();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(MainActivity.this,Login.class);
                startActivity(in);
                finish();
            }
        });

    }
    void userlogin()
    {
        String e=email.getText().toString();
        String p=password.getText().toString();

        if(e.isEmpty())
        {
            email.setError("Please enter email");
            email.requestFocus();
        }
        else if(p.isEmpty())
        {
            password.setError("Please enter email");
            password.requestFocus();
        }
        else if(p.isEmpty() && e.isEmpty())
        {
            email.setError("Enter email");
            password.setError("Enter password");
        }
        else if(p.length()<6)
        {
          password.setError("Enter More than 6 characters");
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Intent in = new Intent(MainActivity.this, userdetails.class);
                                startActivity(in);
                                finish();
                            }

                            else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Authentication failed.Enter valid Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}