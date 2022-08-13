package com.example.hackathon_pmposhan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class schoolUI extends AppCompatActivity
{
    private FirebaseAuth mauth;
    private TextView logout;
    private ImageView schoollogo;
    private TextView schoolname;
    private Button manage;
    private Button menu;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_ui);

        //getting email of the user currently signed in
        mauth = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();
        String email = user.getEmail();
        String[] em = email.split("@");
        String school_name = em[0];



        //setting logo according to the user signed invs
        Resources res = getResources();
        int resid = res.getIdentifier(school_name,"drawable",getPackageName());
        Drawable drawable = res.getDrawable(resid);
        schoollogo = (ImageView)findViewById(R.id.schoologo);
        schoollogo.setImageDrawable(drawable);


        //setting name of school underneath logo
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Schools").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                //task is successfull comes here later
                List<String> list1 =  new ArrayList<>();
                for(QueryDocumentSnapshot document : task.getResult())
                {
                    String[] split_name = document.getId().split(" ");
                    String school_compare = "";
                    for(String i: split_name)
                    {
                        school_compare+=i.toLowerCase(Locale.ROOT);
                    }
                    if(school_name.equals(school_compare))
                    {
                        schoolname = (TextView) findViewById(R.id.school_name); //school1@school.user.com
                        schoolname.setText(document.getId());
                    }
                    else
                    {
                        continue;
                    }
                }

            }
        });


        //going to manage students activity
        manage = (Button)findViewById(R.id.manage);
        manage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(schoolUI.this,manage_students.class));
            }
        });

        //logging out a user
        logout = (TextView) findViewById(R.id.logoutschool);
        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mauth.signOut();
                finish();
            }
        });

        //going to set menu activity
        menu = (Button) findViewById(R.id.set_menu);
        menu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });



    }
}