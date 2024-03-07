package com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments.ProfileFragment;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments.SearchFragment;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments.SettingsFragment;
import com.example.csci3130_w24_group20_quick_cash.FirebaseAuthSingleton;
import com.example.csci3130_w24_group20_quick_cash.R;
import com.example.csci3130_w24_group20_quick_cash.databinding.ActivityBaseEmployeeBinding;
import com.google.firebase.auth.FirebaseAuth;


public class CreateJobActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ActivityBaseEmployeeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBaseEmployeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // Use correct layout file here
        switchFragment(new SearchFragment());

        mAuth = FirebaseAuthSingleton.getInstance();

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
    }

    private void switchFragment(Fragment fragment){
        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}
