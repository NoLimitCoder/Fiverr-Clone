package com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.csci3130_w24_group20_quick_cash.CredentialValidator;
import com.example.csci3130_w24_group20_quick_cash.FirebaseAuthSingleton;
import com.example.csci3130_w24_group20_quick_cash.FirebaseCRUD;
import com.example.csci3130_w24_group20_quick_cash.JobPosting;
import com.example.csci3130_w24_group20_quick_cash.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JobApplyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobApplyFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private ActivityResultLauncher<String> mGetContent;

    private String mParam2;

    private String jobID;

    private Button applyButton, uploadResume;

    private EditText applyName, applyEmail, applyAvailability, applyAddress, applyCountry,
            applyCity, applyEducation, applyExperience, applyDetails;

    private DatabaseReference jobApplicationReference;

    protected FirebaseAuth mAuth;

    FirebaseDatabase database = null;

    private String employeeUID;

    FirebaseCRUD crud = null;

    public JobApplyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JobApplyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JobApplyFragment newInstance(String param1, String param2) {
        JobApplyFragment fragment = new JobApplyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void setJobID(String jobID){
        this.jobID = jobID;
    }

    protected void initializeDatabaseAccess() {
        database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_DB_URL));
        crud = new FirebaseCRUD(database);
    }


    protected void setupResumeButton(View view) {
        Button resumeButton = view.findViewById(R.id.uploadResume);
        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("application/pdf");
            }
        });
    }

    protected void setupApplyButton(View view) {
        Button applyButton = view.findViewById(R.id.SubmitApplicationButton);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadJobApplication();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        initializeDatabaseAccess();
        mAuth = FirebaseAuthSingleton.getInstance();
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            // Now you can upload this file to Firebase Storage

                        }
                    }
                });
    }

    public void uploadJobApplication() {
        CredentialValidator credChecker = new CredentialValidator();

        String name = applyName.getText().toString().trim();
        String email = applyEmail.getText().toString().trim();
        String availability = applyAvailability.getText().toString().trim();
        String address = applyAddress.getText().toString().trim();
        String Country = applyCountry.getText().toString().trim();
        String City = applyCity.getText().toString().trim();
        String education = applyEducation.getText().toString().trim();
        String experience = applyExperience.getText().toString().trim();
        String details = applyDetails.getText().toString().trim();

        employeeUID = mAuth.getCurrentUser().getUid();

        JobPosting jobPosting = new JobPosting(name, employeeUID, email, availability, address, Country,
                City, education, experience, details);

        jobPosting.setJobID(jobID);

        if (credChecker.isJobFilledOut(name, employeeUID, email, availability, address, Country,
                City, education, experience, details)) {

            if (credChecker.isValidAddress(getContext(), Country, City, address)){
                jobApplicationReference.child(jobPosting.getJobID()).child(employeeUID).setValue(jobPosting);
                Toast.makeText(getContext(), "Job Application Uploaded Successfully", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(getContext(), "Address Is Not Valid", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Please Fill Out All The Fields", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job_apply, container, false);

        applyButton = view.findViewById(R.id.SubmitApplicationButton);
        applyName = view.findViewById(R.id.editTextApplyFullName);
        applyEmail = view.findViewById(R.id.editTextApplyEmail);
        applyAvailability = view.findViewById(R.id.editTextAvailability);
        applyAddress = view.findViewById(R.id.editTextApplyAddress);
        applyCountry = view.findViewById(R.id.editTextApplyCountry);
        applyCity = view.findViewById(R.id.editTextApplyCity);
        applyEducation = view.findViewById(R.id.editTextEducation);
        applyExperience = view.findViewById(R.id.editTextJobExperience);
        applyDetails = view.findViewById(R.id.editTextApplyOtherDetails);
        uploadResume = view.findViewById(R.id.uploadResume);
        jobApplicationReference = FirebaseDatabase.getInstance().getReference().child("JobApplications");

        setupResumeButton(view);
        setupApplyButton(view);
        return view;
    }
}