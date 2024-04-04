package com.example.csci3130_w24_group20_quick_cash;

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
import android.widget.EditText;

import com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments.ApplicationDetailFragment;
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
 * Use the {@link ViewAppFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewAppFragment extends Fragment implements ApplicationAdapter.OnApplicationItemClickListener{

    private RecyclerView appRecyclerView;
    private ApplicationAdapter appAdapter;

    private String uid;

    private List<ApplicationPosting> appPostingList = new ArrayList<>();

    private FirebaseAuth mAuth;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public ViewAppFragment() {
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
    public static ViewAppFragment newInstance(String param1, String param2) {
        ViewAppFragment fragment = new ViewAppFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Inflates the layout for this fragment and initializes views.
     * Sets up RecyclerView for displaying job applications.
     * Fetches job applications from Firebase for the current employer.
     * Sets up text watcher for filtering job applications.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState The previously saved state of the fragment.
     * @return The root view of the fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_app, container, false);

        appRecyclerView = view.findViewById(R.id.appRecyclerViewer);
        appRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        appAdapter = new ApplicationAdapter(appPostingList, this);
        appRecyclerView.setAdapter(appAdapter);

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        fetchEmployerAppPostings(uid);
        fetchEmployeeAppPostings(uid);


        EditText editTextSearch = view.findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //no application filter before text change
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //no application filter after text change
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = s.toString().toLowerCase();
                List<ApplicationPosting> filteredList = filterJobs(appPostingList, searchText);
                appAdapter.updateApplications(filteredList);
            }
        });
        return view;
    }

    private void fetchEmployeeAppPostings(String uid) {
        DatabaseReference jobApplicationRef = FirebaseDatabase.getInstance().getReference().child("JobApplications");

        jobApplicationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appPostingList.clear();
                for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot appSnapshot : jobSnapshot.getChildren()) {
                        ApplicationPosting appPosting = appSnapshot.getValue(ApplicationPosting.class);
                        if (appPosting != null && appPosting.getApplicantUID().equals(uid)) {
                            appPostingList.add(appPosting);
                            appAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // No OnCancelled logic
            }
        });
    }

    private List<ApplicationPosting> filterJobs(List<ApplicationPosting> appPostings, String searchText) {
        List<ApplicationPosting> filteredList = new ArrayList<>();
        for (ApplicationPosting app : appPostings){
            if (app.getJobTitle().toLowerCase().contains(searchText)
                    || app.getApplicantEmail().toLowerCase().contains(searchText)
                    || app.getApplicantAvailability().toLowerCase().contains(searchText)){
                filteredList.add(app);
            }
        }
        return filteredList;
    }

    private void fetchEmployerAppPostings(String uid){
        DatabaseReference jobApplicationRef = FirebaseDatabase.getInstance().getReference().child("JobApplications");
        DatabaseReference jobPostingRef = FirebaseDatabase.getInstance().getReference().child("JobPostings").child(uid);

        jobPostingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appPostingList.clear();
                for (DataSnapshot jobSnapshot: snapshot.getChildren()){
                    String jobID = jobSnapshot.getKey();

                    jobApplicationRef.child(jobID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot appSnap : snapshot.getChildren()){
                                ApplicationPosting appPosting = appSnap.getValue(ApplicationPosting.class);
                                appPostingList.add(appPosting);
                            }
                            appAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            //no on cancelled logic
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //no on cancelled logic
            }
        });
    }

    private void openApplicationDetailFragments(ApplicationPosting appPosting){

        ApplicationDetailFragment appDetailFragments = ApplicationDetailFragment.newInstance(appPosting);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (uid.equals(appPosting.getApplicantUID())){
            transaction.replace(R.id.baseEmployee, appDetailFragments);
        } else {
            transaction.replace(R.id.baseEmployer, appDetailFragments);
        }
        transaction.addToBackStack("fragment_employer_view_app").commit();
    }

    /**
     * Handles click events on application items in the RecyclerView.
     *
     * @param appPosting The application posting that was clicked.
     */
    @Override
    public void onApplicationItemClick(ApplicationPosting appPosting) {
        openApplicationDetailFragments(appPosting);
    }
}