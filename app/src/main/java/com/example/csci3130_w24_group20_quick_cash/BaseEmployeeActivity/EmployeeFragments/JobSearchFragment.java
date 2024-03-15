package com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.csci3130_w24_group20_quick_cash.JobAdapter;
import com.example.csci3130_w24_group20_quick_cash.JobPosting;
import com.example.csci3130_w24_group20_quick_cash.MapsActivity;
import com.example.csci3130_w24_group20_quick_cash.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JobSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobSearchFragment extends Fragment implements JobAdapter.OnJobItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private JobAdapter jobAdapter;

    private List<JobPosting> jobPostings = new ArrayList<>();

    public JobSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JobSearchFragment newInstance(String param1, String param2) {
        JobSearchFragment fragment = new JobSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_search, container, false);


        RecyclerView jobRecyclerView = view.findViewById(R.id.jobRecyclerView);
        jobRecyclerView.setLayoutManager((new LinearLayoutManager(getActivity())));

        DatabaseReference jobPostingsRef = FirebaseDatabase.getInstance().getReference("JobPostings");

        jobPostingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jobPostings.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    for (DataSnapshot jobSnapShot : snapshot.getChildren()){
                        JobPosting job = jobSnapShot.getValue(JobPosting.class);
                        if (job != null){
                            jobPostings.add(job);
                        }
                    }

                }

                if (jobAdapter == null){
                    jobAdapter = new JobAdapter(jobPostings, JobSearchFragment.this);
                } else {
                    jobAdapter.updateJobPostings(jobPostings);
                }
                jobRecyclerView.setAdapter(jobAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button btnShowMap = view.findViewById(R.id.btnShowMap);
        btnShowMap.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_map_24, 0, 0, 0);
        btnShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (new Intent(getActivity(), MapsActivity.class));
                intent.putExtra("jobPostings", (Serializable) jobPostings);
                startActivity(intent);
            }
        });

        EditText editTextSearch = view.findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = s.toString().toLowerCase();
                List<JobPosting> filteredList = filterJobs(jobPostings, searchText);
                jobAdapter.updateJobPostings(filteredList);
            }
        });

        return view;

    }

    private void openJobDetailsFragment(JobPosting jobposting){

        JobDetailsFragment jobDetailsFragment = JobDetailsFragment.newInstance(jobposting);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, jobDetailsFragment);
        transaction.addToBackStack("fragment_job_search").commit();
    }

    private List<JobPosting> filterJobs(List<JobPosting> jobPostings, String searchText) {
        List<JobPosting> filteredList = new ArrayList<>();
        for (JobPosting job : jobPostings){
            if (job.getJobTitle().toLowerCase().contains(searchText)
                    || job.getJobType().toLowerCase().contains(searchText)
                    || job.getJobSalary().toLowerCase().contains(searchText)
                    || job.getJobCountry().toLowerCase().contains(searchText)
                    || job.getJobCity().toLowerCase().contains(searchText)){
                filteredList.add(job);
            }
        }
        return filteredList;
    }
    @Override
    public void onJobItemClick(JobPosting jobPosting) {
        openJobDetailsFragment(jobPosting);
    }
}
