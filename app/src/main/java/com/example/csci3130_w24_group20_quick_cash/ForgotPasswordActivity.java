package com.example.csci3130_w24_group20_quick_cash;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

import java.util.function.Consumer;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        FirebaseApp.initializeApp(this);

        //auth = FirebaseAuth.getInstance();  // Initialize FirebaseAuth

        // Set up the click listener for the send email button
        Button sendEmail = findViewById(R.id.sendemail);
        sendEmail.setOnClickListener(this);
    }

    // Method to check if a user exists
    protected void userExists(final String email, final Consumer<Boolean> callback) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

        usersRef.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                callback.accept(dataSnapshot.exists());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadUser:onCancelled", databaseError.toException());
            }
        });
    }

    // Click event handler
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sendemail) {
            // Get user's email from the EditText
            EditText userEmail = findViewById(R.id.email);
            String userEmailString = userEmail.getText().toString().trim();

            // Check if the user exists and send a password reset email
            userExists(userEmailString, new Consumer<Boolean>() {
                @Override
                public void accept(Boolean exists) {
                    if (exists) {
                        // User exists, send a password reset email
                        auth.sendPasswordResetEmail(userEmailString).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotPasswordActivity.this, "Check your email to reset password", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(ForgotPasswordActivity.this, "Error sending reset email", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        // User doesn't exist, show an error message
                        Toast.makeText(ForgotPasswordActivity.this, "Error: Email not found", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
