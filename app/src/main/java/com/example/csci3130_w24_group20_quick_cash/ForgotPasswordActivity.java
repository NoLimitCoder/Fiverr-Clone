package com.example.csci3130_w24_group20_quick_cash;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPasswordActivity extends AppCompatActivity{
    FirebaseDatabase database = null;

    FirebaseCRUD crud = null;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.enter_email);

        EditText userEmail = findViewById(R.id.emailEntry);
        Button send = findViewById(R.id.verifyButton);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.sendPasswordResetEmail(userEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(forgotPasswordActivity.this, "Check your email to reset password", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }

    protected void setNewPassword(String email, String password) {
        database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_DB_URL));
        DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("User Inmation").child(email);
        dbr.child("password").setValue(password);
    }

    protected void confirmMatch() {
        EditText passwordBox = findViewById(R.id.new_password);
        EditText confirmPassword = findViewById(R.id.confirm_password);
        if(passwordBox.getText().toString().equals(confirmPassword.getText().toString())){

        }
    }

}
