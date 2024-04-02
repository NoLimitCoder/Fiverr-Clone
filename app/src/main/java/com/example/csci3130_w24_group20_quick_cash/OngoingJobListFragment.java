package com.example.csci3130_w24_group20_quick_cash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OngoingJobListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<JobOffer> ongoingJobList;
    private JobOfferAdapter adapter;
    FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ongoing_job_list, container, false);

        mAuth = FirebaseAuthSingleton.getInstance();
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ongoingJobList = new ArrayList<>();
        adapter = new JobOfferAdapter(ongoingJobList);
        recyclerView.setAdapter(adapter);

        fetchOngoingJobOffers();

        return view;
    }

    private void fetchOngoingJobOffers() {
        DatabaseReference jobOffersRef = FirebaseDatabase.getInstance().getReference("JobOffers");
            jobOffersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ongoingJobList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        JobOffer jobOffer = snapshot.getValue(JobOffer.class);
                        if (jobOffer != null) {
                            ongoingJobList.add(jobOffer);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle onCancelled
                }
            });
        }
    }

