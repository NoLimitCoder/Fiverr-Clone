package com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.csci3130_w24_group20_quick_cash.FirebaseAuthSingleton;
import com.example.csci3130_w24_group20_quick_cash.MainActivity;
import com.example.csci3130_w24_group20_quick_cash.R;
import com.google.firebase.auth.FirebaseAuth;

public class BaseEmployerActivity extends AppCompatActivity implements View.OnClickListener {
    private Button logoutButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_employer); // Use correct layout file here
        this.setupLogoutButton();
        mAuth = FirebaseAuthSingleton.getInstance();
    }


    protected void setupLogoutButton() {
        logoutButton = findViewById(R.id.logoutButton); // Correct ID of the logout button
        logoutButton.setOnClickListener(this);
    }


    void logout() {
        mAuth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Log.d("clicked", "clicked");
        logout();
    }
}


