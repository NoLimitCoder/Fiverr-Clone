package com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csci3130_w24_group20_quick_cash.JobPosting;
import com.example.csci3130_w24_group20_quick_cash.MainActivity;
import com.example.csci3130_w24_group20_quick_cash.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JobDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobDetailsFragment extends Fragment {
    private ActivityResultLauncher<String> mGetContent;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_JOB_POSTING = "argJobPosting";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private static final int PICK_PDF_REQUEST = 1;

    private JobPosting jobPosting;
    private String mParam2;

    public JobDetailsFragment() {
        // Required empty public constructor
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

    protected void setupApplyButton(View view) {
        Button applyButton = view.findViewById(R.id.applyButton);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("application/pdf");
            }
        });
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
        setupApplyButton(view);
        return view;
    }
}