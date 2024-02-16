package com.example.csci3130_w24_group20_quick_cash;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Button;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogoutActivity extends AppCompatActivity{

    private FirebaseAuth firebaseAuth;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        firebaseAuth = FirebaseAuth.getInstance();

        logout = (Button) findViewById(R.id.logoutButton);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });
        //logout.setOnClickListener(view -> Logout());
    }

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(LogoutActivity.this, MainActivity.class));
    }


}

