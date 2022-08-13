package com.example.hackathon_pmposhan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class editprofile extends AppCompatActivity
{
    private FirebaseFirestore db;
    private EditText name;
    private EditText age;
    private EditText gender;
    private EditText Class;
    private EditText contact;
    private Button cancel;
    private Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        //making instances
        db = FirebaseFirestore.getInstance();
        name = (EditText)findViewById(R.id.name_fill);
        age = (EditText)findViewById(R.id.age_fill);
        gender = (EditText)findViewById(R.id.gender_fill);
        Class = (EditText)findViewById(R.id.class_fill);
        contact = (EditText)findViewById(R.id.contact_fill);
        cancel = (Button)findViewById(R.id.cancel);
        save = (Button)findViewById(R.id.save);

        //getting string from another activity
        Intent intent = getIntent();
        String student_name = intent.getStringExtra("key");
        String school_name = intent.getStringExtra("key1");

        //getting data for editing
        db.collection("admin").document("schools")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
                {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot)
                    {
                        Map<String,Object> map = documentSnapshot.getData();
                        for(Map.Entry<String,Object> m1 : map.entrySet())
                        {
                            if(m1.getKey().equals(school_name))
                            {
                                Map<String,Object> map1 = (Map<String, Object>) m1.getValue();
                                for(Map.Entry<String,Object> m2 : map1.entrySet())
                                {
                                    if(m2.getKey().equals("students"))
                                    {
                                        Map<String,Object> map2 = (Map<String, Object>) m2.getValue();
                                        for(Map.Entry<String,Object> m3 : map2.entrySet())
                                        {
                                            if(m3.getKey().equals(student_name))
                                            {
                                                Map<String,Object> map3 = (Map<String, Object>) m3.getValue();
                                                name.setHint(map3.get("name").toString());
                                                age.setHint(map3.get("age").toString());
                                                Class.setHint(map3.get("class").toString());
                                                gender.setHint(map3.get("gender").toString());
                                                contact.setHint(map3.get("contact").toString());

                                                //checking if cancel is clicked
                                                cancel.setOnClickListener(new View.OnClickListener()
                                                {
                                                    @Override
                                                    public void onClick(View view)
                                                    {
                                                        finish();
                                                    }
                                                });

                                                //checking if save is clicked
                                                save.setOnClickListener(new View.OnClickListener()
                                                {
                                                    @Override
                                                    public void onClick(View view)
                                                    {
                                                        String common = school_name+".students."+student_name;

                                                        //updating fields of students in respectice school
                                                        if(name.getText().toString().equals(""))
                                                        {
                                                            db.collection("admin").document("schools")
                                                                    .update(common+".name",name.getHint().toString());
                                                        }
                                                        else
                                                        {
                                                            db.collection("admin").document("schools")
                                                                    .update(common+".name",name.getText().toString());
                                                        }

                                                        if(age.getText().toString().equals(""))
                                                        {
                                                            db.collection("admin").document("schools")
                                                                    .update(common+".age",age.getHint().toString());
                                                        }
                                                        else
                                                        {
                                                            db.collection("admin").document("schools")
                                                                    .update(common+".age",age.getText().toString());
                                                        }

                                                        if(gender.getText().toString().equals(""))
                                                        {
                                                            db.collection("admin").document("schools")
                                                                    .update(common+".gender",gender.getHint().toString());
                                                        }
                                                        else
                                                        {
                                                            db.collection("admin").document("schools")
                                                                    .update(common+".gender",gender.getText().toString());
                                                        }

                                                        if(Class.getText().toString().equals(""))
                                                        {
                                                            db.collection("admin").document("schools")
                                                                    .update(common+".class",Class.getHint().toString());
                                                        }
                                                        else
                                                        {
                                                            db.collection("admin").document("schools")
                                                                    .update(common+".class",Class.getText().toString());
                                                        }

                                                        if(contact.getText().toString().equals(""))
                                                        {
                                                            db.collection("admin").document("schools")
                                                                    .update(common+".contact",contact.getHint().toString());
                                                        }
                                                        else
                                                        {
                                                            db.collection("admin").document("schools")
                                                                    .update(common+".contact",contact.getText().toString());
                                                        }

                                                        finish();


                                                    }
                                                });






                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });






    }
}