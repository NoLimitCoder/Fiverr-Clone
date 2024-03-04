package com.example.csci3130_w24_group20_quick_cash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.csci3130_w24_group20_quick_cash.databinding.ActivityEmployeeWelcomeBinding;
import com.google.firebase.auth.FirebaseAuth;

public class EmployeeWelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button logoutButton;
    private FirebaseAuth mAuth;

    ActivityEmployeeWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        switchFragment(new SearchFragment());


        binding.navLayout.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();
            if (itemId == R.id.searchJobs) {
                switchFragment(new SearchFragment());
            } else if (itemId == R.id.viewProfile) {
                switchFragment((new ProfileFragment()));
            } else if (itemId == R.id.employeeSettings) {
                switchFragment(new SettingsFragment());
            }

            return true;
        });
        this.setupLogoutButton();
        mAuth = FirebaseAuthSingleton.getInstance();
    }

    private void switchFragment(Fragment fragment){
        FragmentManager  fragManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
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



