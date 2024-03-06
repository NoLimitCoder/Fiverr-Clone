package com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.example.csci3130_w24_group20_quick_cash.MockJobPostingRepo;
import com.example.csci3130_w24_group20_quick_cash.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
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
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
    View view = inflater.inflate(R.layout.fragment_search, container, false);

        RecyclerView jobRecyclerView = view.findViewById(R.id.jobRecyclerView);
    jobRecyclerView.setLayoutManager((new LinearLayoutManager(getActivity())));

    List<JobPosting> jobPostings = MockJobPostingRepo.getInstance().getJobPostings();

        JobAdapter jobAdapter = new JobAdapter(jobPostings);
    jobRecyclerView.setAdapter(jobAdapter);

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

    private List<JobPosting> filterJobs(List<JobPosting> jobPostings, String searchText) {
        List<JobPosting> filteredList = new ArrayList<>();
        for (JobPosting job : jobPostings){
            if (job.getJobTitle().toLowerCase().contains(searchText)
            || job.getJobType().toLowerCase().contains(searchText)
            || job.getJobSalary().toLowerCase().contains(searchText)
            || job.getJobLocation().toLowerCase().contains(searchText)
            || job.getEmployerName().toLowerCase().contains(searchText)){
                filteredList.add(job);
            }
        }
        return filteredList;
    }
}