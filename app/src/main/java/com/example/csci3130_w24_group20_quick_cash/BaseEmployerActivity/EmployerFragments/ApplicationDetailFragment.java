package com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.csci3130_w24_group20_quick_cash.ApplicationPosting;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments.JobApplyFragment;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments.JobDetailsFragment;
import com.example.csci3130_w24_group20_quick_cash.JobPosting;
import com.example.csci3130_w24_group20_quick_cash.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApplicationDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplicationDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_APP_POSTING = "argAppPosting";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private static final int PICK_PDF_REQUEST = 1;

    private ApplicationPosting appPosting;
    private String mParam2;

    public ApplicationDetailFragment() {
        // Required empty public constructor
    }

    public static ApplicationDetailFragment newInstance(ApplicationPosting appPosting) {
        ApplicationDetailFragment fragment = new ApplicationDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_APP_POSTING, appPosting);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            appPosting = (ApplicationPosting) getArguments().getSerializable(ARG_APP_POSTING);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_application_detail, container, false);

        TextView textJobTitle = view.findViewById(R.id.textJobTitle);
        TextView textApplicantName = view.findViewById(R.id.textApplicantName);
        TextView textApplicantEmail = view.findViewById(R.id.textApplicantEmail);
        TextView textApplicantAvailability = view.findViewById(R.id.textApplicantAvailability);
        TextView textApplicantCountry = view.findViewById(R.id.textApplicantCountry);
        TextView textApplicantCity = view.findViewById(R.id.textApplicantCity);
        TextView textApplicantAddress = view.findViewById(R.id.textApplicantAddress);
        TextView textApplicantEducation = view.findViewById(R.id.textApplicantEducation);
        TextView textApplicantExperience = view.findViewById(R.id.textApplicantExperience);
        TextView textApplicantOtherDetails = view.findViewById(R.id.textApplicantOtherDetails);
        TextView textApplicantDateApplied = view.findViewById(R.id.textApplicantDateApplied);

        // Populate TextViews with job details
        if (appPosting != null) {
            textJobTitle.setText(appPosting.getJobTitle());
            textApplicantName.setText(appPosting.getApplicantName());
            textApplicantEmail.setText(appPosting.getApplicantEmail());
            textApplicantAvailability.setText(appPosting.getApplicantAvailability());
            textApplicantCountry.setText(appPosting.getApplicantCountry());
            textApplicantCity.setText(appPosting.getApplicantCity());
            textApplicantAddress.setText(appPosting.getApplicantAddress());
            textApplicantEducation.setText(appPosting.getApplicantEducation());
            textApplicantExperience.setText(appPosting.getApplicantExperience());
            textApplicantOtherDetails.setText(appPosting.getAppOtherDetails());
            textApplicantDateApplied.setText(appPosting.getDateReceived());
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