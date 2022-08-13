package com.example.hackathon_pmposhan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class singleadd extends AppCompatActivity
{
    private Button save;
    private Button cancel;
    private FirebaseAuth mauth;
    private EditText name;
    private EditText dob;
    private EditText address;
    private EditText gender;
    private EditText bloodgroup;
    private EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleadd);

        //setting instances
        name = (EditText) findViewById(R.id.name_fill);
        dob = (EditText) findViewById(R.id.dob_fill);
        address = (EditText)findViewById(R.id.address_fill);
        gender = (EditText)findViewById(R.id.gender_fill);
        bloodgroup = (EditText)findViewById(R.id.blood_fill);
        phone = (EditText)findViewById(R.id.phone_fill);

        //school name
        mauth = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();
        String email = user.getEmail();
        String[] em = email.split("@");
        String school_name = em[0];

        //setting up instances
        save = (Button) findViewById(R.id.save);
        cancel = (Button) findViewById(R.id.cancel);

        //setting button actions
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (name.getText().toString().equals("") || dob.getText().toString().equals("") ||address.getText().toString().equals("") ||gender.getText().toString().equals("") ||bloodgroup.getText().toString().equals("") ||phone.getText().toString().equals(""))
                {
                    Toast.makeText(singleadd.this,"Text Field is Empty",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //will check later if student already exists in database *imp*
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
                                    Map<String,Object> map1 = new HashMap<>();
                                    map1.put("Name",name.getText().toString());
                                    map1.put("DOB",dob.getText().toString());
                                    map1.put("Address",address.getText().toString());
                                    map1.put("Gender",gender.getText().toString());
                                    map1.put("Blood Group",bloodgroup.getText().toString());
                                    map1.put("Phone No.",phone.getText().toString());
                                    db.collection("Schools").document(document.getId()).collection("Students").document(name.getText().toString().toLowerCase(Locale.ROOT)).set(map1);
                                    //make collections here retard
                                    Map<String,Object> Emptymap = new HashMap<>();
                                    db.collection("Schools").document(document.getId()).collection("Students").document(name.getText().toString().toLowerCase(Locale.ROOT)).collection("Medical Info").document("Immunization").set(Emptymap);
                                    db.collection("Schools").document(document.getId()).collection("Students").document(name.getText().toString().toLowerCase(Locale.ROOT)).collection("Medical Info").document("Key Info").set(Emptymap);


                                    finish();


                                }
                                else
                                {
                                    continue;
                                }
                            }

                        }
                    });
                }
            }
        });

    }
}