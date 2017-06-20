package com.example.aditya.profiler;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference database;

    Button signup;
    EditText user, email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");
        getSupportActionBar().setHomeButtonEnabled(true);

        signup = (Button) findViewById(R.id.reg);
        user = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

        final User userc = new User();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user_txt = user.getText().toString().trim();
                final String email_txt = email.getText().toString().trim();
                String pass_txt = pass.getText().toString().trim();

                mAuth.createUserWithEmailAndPassword(email_txt, pass_txt)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Signin Failed", Toast.LENGTH_SHORT).show();
                                } else {
                                    String userid = mAuth.getCurrentUser().getUid();
                                    userc.writeNewUser(userid, user_txt, email_txt);
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}

