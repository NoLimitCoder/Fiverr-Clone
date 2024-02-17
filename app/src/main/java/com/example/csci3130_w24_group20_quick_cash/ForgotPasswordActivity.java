package com.example.csci3130_w24_group20_quick_cash;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ForgotPasswordActivity extends AppCompatActivity {
    protected FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_email);
        mAuth = FirebaseAuthSingleton.getInstance();

        EditText userEmail = findViewById(R.id.emailEntry);
        Button send = findViewById(R.id.verifyButton);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.sendPasswordResetEmail(userEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ForgotPasswordActivity.this, "Check your email to reset password", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }


}
