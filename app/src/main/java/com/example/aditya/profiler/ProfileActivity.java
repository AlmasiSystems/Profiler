package com.example.aditya.profiler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    String ownerName, emailAdd, phoneNum, Address;
    TextView ownName, phn, add, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent i = getIntent();
        String name = i.getStringExtra("Name");
        setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ownName = (TextView) findViewById(R.id.ownerName);
        phn = (TextView) findViewById(R.id.phoneNo);
        add = (TextView) findViewById(R.id.addProf);
        email = (TextView) findViewById(R.id.emailProf);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child("data").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ownerName = dataSnapshot.child("Owner").getValue(String.class);
                emailAdd = dataSnapshot.child("Email").getValue(String.class);
                phoneNum = dataSnapshot.child("Phone").getValue(String.class);
                Address = dataSnapshot.child("Address").getValue(String.class);

                email.setText(emailAdd);
                ownName.setText(ownerName);
                phn.setText(phoneNum);
                add.setText(Address);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
