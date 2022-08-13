package com.example.hackathon_pmposhan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class studentprofilesearch extends AppCompatActivity
{
    private ImageView search_icon;
    private EditText student_name_search;
    private FirebaseAuth mauth;
    private TextView name;
    private TextView age;
    private TextView gender;
    private TextView Class;
    private TextView contact;
    private Button clear;
    private Button edit;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentprofilesearch);

        //presetting some variables
        name = (TextView)findViewById(R.id.name);
        age = (TextView)findViewById(R.id.age);
        gender = (TextView)findViewById(R.id.gender);
        Class = (TextView) findViewById(R.id.studentclass);
        contact = (TextView) findViewById(R.id.contact);

        //connecting to firebase
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //getting email id of user logged in
        mauth = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();
        String email = user.getEmail();
        String[] em = email.split("@");
        String school_name = em[0];


        student_name_search = (EditText)findViewById(R.id.editTextTextEmailAddress);

        //to check if search icon has been clicked
        search_icon = (ImageView) findViewById(R.id.search_icon);

        //updating info of the student
        search_icon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(student_name_search.getText().toString().equals(""))
                {
                    Toast.makeText(studentprofilesearch.this,"Search field is empty",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    db.collection("admin").document("schools")
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
                            {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot)
                                {
                                    Map<String,Object> map1 = documentSnapshot.getData();
                                    for(Map.Entry<String,Object> m : map1.entrySet())
                                    {
                                        if(m.getKey().equals(school_name))
                                        {
                                            Map<String,Object> map2 = (Map<String, Object>) m.getValue();
                                            for(Map.Entry<String,Object> m1 : map2.entrySet())
                                            {
                                                if(m1.getKey().equals("students"))
                                                {
                                                    count = 0;
                                                    Map<String,Object> map3 = (Map<String, Object>) m1.getValue();
                                                    for(Map.Entry<String,Object> m2 : map3.entrySet())
                                                    {


                                                        if(m2.getKey().equals(student_name_search.getText().toString()))
                                                        {
                                                            count+=1;
                                                            Map<String,Object> map4 = (Map<String, Object>) m2.getValue();
                                                            name.setText("Name: "+map4.get("name").toString());
                                                            age.setText("Age: "+map4.get("age").toString());
                                                            gender.setText("Gender: "+map4.get("gender").toString());
                                                            Class.setText("Class: "+map4.get("class").toString());
                                                            contact.setText("Contact: "+map4.get("contact").toString());

                                                        }
                                                    }

                                                }
                                                if(count == 0)
                                                {
                                                    Toast.makeText(studentprofilesearch.this,"Student name not found in the database",Toast.LENGTH_SHORT).show();
                                                }

                                            }

                                        }

                                    }

                                }
                            });
                }
            }
        });

        //clearing the data
        clear = (Button)findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                name.setText("Name: ");
                age.setText("Age: ");
                gender.setText("Gender: ");
                Class.setText("Class: ");
                contact.setText("Contact: ");
                student_name_search.setText("");
            }
        });

        edit = (Button)findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent name_passed = new Intent(studentprofilesearch.this,editprofile.class);
                if(student_name_search.getText().toString().equals(""))
                {
                    Toast.makeText(studentprofilesearch.this,"Search Field is Empty",Toast.LENGTH_SHORT).show();
                }

                else if(count == 0)
                {
                    Toast.makeText(studentprofilesearch.this,"Student name not in the database hence cannot be edited",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String passed = student_name_search.getText().toString();
                    String passed1 = school_name;
                    name_passed.putExtra("key",passed);
                    name_passed.putExtra("key1",passed1);
                    startActivity(name_passed);
                }

            }
        });


    }
}