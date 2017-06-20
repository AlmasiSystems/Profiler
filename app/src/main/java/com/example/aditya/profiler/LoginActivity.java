package com.example.aditya.profiler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText email, passwd;
    TextView signup;
    Button login;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        final SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        String shared_email = sharedPreferences.getString("email", null);

        if (shared_email != null){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.username);
        passwd = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.loginbtn);
        signup = (TextView) findViewById(R.id.signup);

        final User userc = new User();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailstr = email.getText().toString().trim();
                String passwdstr = passwd.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(emailstr, passwdstr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String uid = mAuth.getCurrentUser().getUid();
                            String username = userc.getUsername(uid);
                            Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();
                            /*SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("email", emailstr);
                            editor.commit();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);*/
                        }
                    }
                });
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
