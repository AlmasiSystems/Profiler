package com.example.aditya.profiler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProfile extends AppCompatActivity {

    EditText nameP, emailP, addressP, phoneP, ownerP;
    Button saveB;

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add a profile");

        nameP = (EditText) findViewById(R.id.name_prof);
        emailP = (EditText) findViewById(R.id.email_prof);
        addressP = (EditText) findViewById(R.id.address_prof);
        phoneP = (EditText) findViewById(R.id.phone_prof);
        ownerP = (EditText) findViewById(R.id.ownername_prof);
        saveB = (Button) findViewById(R.id.save_prof);

        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameS, emailS, addressS, phoneS, ownerS;
                nameS = nameP.getText().toString();
                emailS = emailP.getText().toString();
                addressS = addressP.getText().toString();
                phoneS = phoneP.getText().toString();
                ownerS = ownerP.getText().toString();

                database.child("data").child(nameS).child("Email").setValue(emailS);
                database.child("data").child(nameS).child("Address").setValue(addressS);
                database.child("data").child(nameS).child("Phone").setValue(phoneS);
                database.child("data").child(nameS).child("Owner").setValue(ownerS);

                finish();
            }
        });
    }
}
