package com.example.csci3130_w24_group20_quick_cash;

import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class forgotPasswordActivity extends AppCompatActivity{
    FirebaseDatabase database = null;
    FirebaseCRUD crud = null;

    protected void onCreate

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
