package com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csci3130_w24_group20_quick_cash.FirebaseAuthSingleton;
import com.example.csci3130_w24_group20_quick_cash.FirebaseCRUD;
import com.example.csci3130_w24_group20_quick_cash.JobPosting;
import com.example.csci3130_w24_group20_quick_cash.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JobDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobDetailsFragment extends Fragment {

    private static final String ARG_JOB_POSTING = "argJobPosting";

    private JobPosting jobPosting;

    private FirebaseAuth mAuth;

    private FirebaseDatabase database = null;

    public JobDetailsFragment() {
        // Required empty public constructor
    }

    protected void initializeDatabaseAccess() {
        database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_DB_URL));
    }

    protected void setupApplyButton(View view) {
        Button applyButton = view.findViewById(R.id.applyJobButton);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobApplyFragment jobApplyFragment = new JobApplyFragment();
                jobApplyFragment.setJobID(jobPosting.getJobID());
                jobApplyFragment.setJobTitle(jobPosting.getJobTitle());
                jobApplyFragment.setEmployerUID(jobPosting.getEmployerUID());
                switchFragment(jobApplyFragment);
            }
        });
    }

    protected void setupFavoriteButton(View view) {
        Button favoriteButton = view.findViewById(R.id.favoriteJobButton);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String preferredJobType = jobPosting.getJobType();
                FirebaseUser user = mAuth.getCurrentUser();

                if (user != null) {
                    String userID = user.getUid();
                    DatabaseReference favoriteRef = database.getReference("users").child(userID);

                    favoriteRef.child("favoriteJobTypes").push().setValue(preferredJobType)
                            .addOnSuccessListener(aVoid -> Toast.makeText(getActivity(), "Job favorited!", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed to favorite job!", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    public static JobDetailsFragment newInstance(JobPosting jobPosting) {
        JobDetailsFragment fragment = new JobDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_JOB_POSTING, jobPosting);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
             jobPosting = (JobPosting) getArguments().getSerializable(ARG_JOB_POSTING);
        }
        initializeDatabaseAccess();
        mAuth = FirebaseAuthSingleton.getInstance();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_details, container, false);

        TextView textJobTitle = view.findViewById(R.id.textJobTitle);
        TextView textJobType = view.findViewById(R.id.textJobType);
        TextView textJobSalary = view.findViewById(R.id.textJobSalary);
        TextView textJobLocation = view.findViewById(R.id.textJobLocation);
        TextView textJobDescription = view.findViewById(R.id.textJobDescription);
        TextView textOtherDetails = view.findViewById(R.id.textOtherDetails);
        TextView textEmployerName = view.findViewById(R.id.textEmployerName);
        TextView textJobCountry = view.findViewById(R.id.textJobCountry);
        TextView textJobCity = view.findViewById(R.id.textJobCity);

        setupApplyButton(view);
        setupFavoriteButton(view);


        if (jobPosting != null){
            textJobTitle.setText(jobPosting.getJobTitle());
            textJobType.setText(jobPosting.getJobType());
            textJobSalary.setText(jobPosting.getJobSalary());
            textJobLocation.setText(jobPosting.getJobAddress());
            textJobDescription.setText(jobPosting.getJobDescription());
            textOtherDetails.setText(jobPosting.getOtherDetails());
            textEmployerName.setText(jobPosting.getEmployerName());
            textJobCountry.setText(jobPosting.getJobCountry());
            textJobCity.setText(jobPosting.getJobCity());

        }
        return view;
    }

    private void switchFragment(Fragment fragment){
        FragmentManager fragManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragManager.beginTransaction();
        fragmentTransaction.replace(R.id.baseEmployee, fragment);
        fragmentTransaction.addToBackStack("fragment_job_details").commit();
    }

}