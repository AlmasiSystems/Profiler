package com.example.aditya.profiler;

import android.os.Handler;
import android.util.Log;
import android.util.StringBuilderPrinter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

@IgnoreExtraProperties
public class User extends HashMap<String, String> {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public String username;
    public String email;

    public String name;

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public void writeNewUser(String uid, String username, String email){
        database.child("users").child(uid).child("Username").setValue(username);
        database.child("users").child(uid).child("Email").setValue(email);
    }
}
