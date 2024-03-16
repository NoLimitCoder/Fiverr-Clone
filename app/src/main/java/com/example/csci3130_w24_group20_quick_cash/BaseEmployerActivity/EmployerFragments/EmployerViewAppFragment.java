package com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.csci3130_w24_group20_quick_cash.JobAdapter;
import com.example.csci3130_w24_group20_quick_cash.JobPosting;
import com.example.csci3130_w24_group20_quick_cash.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployerViewAppFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployerViewAppFragment extends Fragment {

    private RecyclerView appRecyclerView;
    private JobAdapter appAdapter;

    private List<JobPosting> appPostingList = new ArrayList<>();

    private FirebaseAuth mAuth;
    private DatabaseReference appPostingRef;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EmployerViewAppFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmployerViewAppFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmployerViewAppFragment newInstance(String param1, String param2) {
        EmployerViewAppFragment fragment = new EmployerViewAppFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employer_view_app, container, false);

        appRecyclerView = view.findViewById(R.id.appRecyclerViewer);
        appRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        appAdapter = new JobAdapter(appPostingList, new JobAdapter.OnJobItemClickListener() {
            @Override
            public void onJobItemClick(JobPosting jobPosting) {

            }
        });
        appRecyclerView.setAdapter(appAdapter);

        mAuth = FirebaseAuth.getInstance();
        String employerUID = mAuth.getCurrentUser().getUid();

        fetchAppPostingForEmployer(employerUID);
        return view;
    }

    private void fetchAppPostingForEmployer(String employerUID){
        DatabaseReference jobApplicationRef = FirebaseDatabase.getInstance().getReference().child("JobApplications");
        DatabaseReference jobPostingRef = FirebaseDatabase.getInstance().getReference().child("JobPostings").child(employerUID);

        jobPostingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot jobSnapshot: snapshot.getChildren()){
                    String jobID =jobSnapshot.getKey();

                    jobApplicationRef.child(jobID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot appSnap : snapshot.getChildren()){
                                JobPosting appPosting = appSnap.getValue(JobPosting.class);
                                appPostingList.add(appPosting);
                            }
                            appAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}