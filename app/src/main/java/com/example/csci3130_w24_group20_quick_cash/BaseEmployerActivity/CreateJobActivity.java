package com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments.ProfileFragment;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments.SearchFragment;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments.SettingsFragment;
import com.example.csci3130_w24_group20_quick_cash.CredentialValidator;
import com.example.csci3130_w24_group20_quick_cash.FirebaseAuthSingleton;
import com.example.csci3130_w24_group20_quick_cash.R;
import com.example.csci3130_w24_group20_quick_cash.databinding.ActivityBaseEmployeeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CreateJobActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
   // private DatabaseReference root = FirebaseDatabase.getInstance();

    ActivityBaseEmployeeBinding binding;
    //make a recycler viewer

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


    protected String getCompanyName() {
        EditText companyNameBox = findViewById(R.id.company_name);
        return companyNameBox.getText().toString().trim();
    }
    protected String getJobName() {
        EditText jobNameBox = findViewById(R.id.job_name);
        return jobNameBox.getText().toString().trim();
    }

    protected String getDescription() {
        EditText descriptionBox = findViewById(R.id.job_description);
        return descriptionBox.getText().toString().trim();
    }

    protected String getRequirements() {
        EditText requirementBox = findViewById(R.id.job_requirements);
        return requirementBox.getText().toString().trim();
    }

    protected String getInstructions() {
        EditText instructionsBox = findViewById(R.id.job_requirements);
        return instructionsBox.getText().toString().trim();
    }

    @Override
    public void onClick(View view) {
        String companyName = getCompanyName();
        String jobName = getJobName();
        String description = getDescription();
        String requirements = getRequirements();
        String instructions = getInstructions();
        String errorMessage = new String();

        CredentialValidator validator = new CredentialValidator();
        CredentialValidator val = new CredentialValidator();




    }
/*
    protected void saveInfoToFirebase(String companyName, String jobName, String description, String requirements, String instructions) {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){
                if (task.isSuccessful()){
                    FirebaseUser user = task.getResult().getUser();
                    if (user != null) {
                        DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

                        dbr.child("companyName").setValue(companyName);
                        dbr.child("jobName").setValue(jobName);
                        dbr.child("description").setValue(description);
                        dbr.child("requirements").setValue(requirements);
                        dbr.child("instructions").setValue(instructions);


                    }
                }
            }
        });

    }
*/
}
