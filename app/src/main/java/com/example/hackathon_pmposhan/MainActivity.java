package com.example.hackathon_pmposhan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
{

    private FirebaseAuth mauth;
    private EditText email;
    private EditText pass;
    private Button login;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //for logging in users
        mauth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //getting email and pass in the box
                String e = email.getText().toString();
                String p = pass.getText().toString();

                //checking if the email and password boxes are empty or not
                if(e.equals("") || p.equals(""))
                {
                    Toast.makeText(MainActivity.this,"Email or Password Field cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //authenticating with firebase
                    mauth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                //getting the end of the email id to check which kind of user signed in
                                String[] em = e.split("@");
                                String[] em1 = em[1].split(".user.com");
                                String user = em1[0];

                                if(user.equals("school"))
                                {
                                    startActivity(new Intent(MainActivity.this,schoolUI.class));
                                }

                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"Incorrect Email or Password",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}