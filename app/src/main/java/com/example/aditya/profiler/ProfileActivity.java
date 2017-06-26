package com.example.aditya.profiler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent i = getIntent();
        String name = i.getStringExtra("Name");
        setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
