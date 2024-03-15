package com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.csci3130_w24_group20_quick_cash.FirebaseAuthSingleton;
import com.example.csci3130_w24_group20_quick_cash.FirebaseCRUD;
import com.example.csci3130_w24_group20_quick_cash.MainActivity;
import com.example.csci3130_w24_group20_quick_cash.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EmployeeJobApplication implements View.OnClickListener {
    private EditText jobTitleEditText, jobSalaryEditText, jobTypeEditText, jobCountryEditText,
            jobCityEditText, jobAdressEditText, jobDescriptionEditText, jobOtherDetailsEditText;

    private DatabaseReference jobPostingReference;

    protected FirebaseAuth mAuth;

    FirebaseDatabase database = null;

    final String[] employerName = new String[1];
    private String employerUID;

    Button uploadButton;
    FirebaseCRUD crud = null;
}
