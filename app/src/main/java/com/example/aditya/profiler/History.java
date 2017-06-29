package com.example.aditya.profiler;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class History extends Fragment {

    ListView listView;
    ArrayList<String> history = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_history, container, false);
        listView = (ListView) v.findViewById(R.id.list_history);

        FirebaseDatabase.getInstance().getReference().keepSynced(true);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        history.clear();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final String userID = mAuth.getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference("history").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String time = snapshot.getKey();
                    char[] arr = time.toCharArray();

                    String year = "", month = "", date = "", hours = "";

                    for (int i=0; i<4; i++) {
                        year = year + String.valueOf(arr[i]);
                    }
                    for (int i=4; i<6; i++) {
                        month = month + String.valueOf(arr[i]);
                    }
                    for (int i=6; i<8; i++) {
                        date = date + String.valueOf(arr[i]);
                    }
                    for (int i=9; i<14; i++) {
                        hours = hours + String.valueOf(arr[i]);
                    }

                    time = date + "/" + month + "/" + year + " " + hours;

                    history.add(snapshot.getValue(String.class) + " on " + time);
                }
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_item, history);
                listView.setAdapter(arrayAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String intentName = history.get(position);
                        String arr[] = intentName.split("on");
                        intentName = arr[0].trim();

                        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                        Date date = new Date();

                        FirebaseDatabase.getInstance().getReference("history").child(userID).child(dateFormat.format(date).toString()).setValue(intentName);

                        Intent intent = new Intent(getActivity().getApplicationContext(), ProfileActivity.class);
                        intent.putExtra("Name", intentName);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}