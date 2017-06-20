package com.example.aditya.profiler;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

@IgnoreExtraProperties
public class User {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public String username;
    public String email;

    public User() {

    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public void writeNewUser(String uid, String username, String email){
        database.child("users").child(uid).child("Username").setValue(username);
        database.child("users").child(uid).child("Email").setValue(email);
    }

    public String getUsername (final String uid){
        final String[] username = new String[1];
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getKey().equals("Username")){
                    username[0] = dataSnapshot.getValue().toString();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return username[0];
    }
}
