package com.example.hackathon_pmposhan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class manage_students extends AppCompatActivity {

    private Button multiple_add;
    private Button single_add;
    private Button edit_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_students);

        //setting instances for all three buttons
        multiple_add = (Button)findViewById(R.id.multiple);
        single_add = (Button) findViewById(R.id.single);
        edit_info = (Button) findViewById(R.id.edit);

        //setting actions
        multiple_add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(manage_students.this,multipleadd.class));
            }
        });

        single_add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(manage_students.this,singleadd.class));
            }
        });


    }
}