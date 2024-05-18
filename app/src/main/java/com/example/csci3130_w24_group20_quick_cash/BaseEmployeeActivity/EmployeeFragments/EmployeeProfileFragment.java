package com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.csci3130_w24_group20_quick_cash.FirebaseAuthSingleton;
import com.example.csci3130_w24_group20_quick_cash.MainActivity;
import com.example.csci3130_w24_group20_quick_cash.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployeeProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeProfileFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView ratingText;

    FirebaseAuth mAuth;

    public EmployeeProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    public static EmployeeProfileFragment newInstance(String param1, String param2) {
        EmployeeProfileFragment fragment = new EmployeeProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuthSingleton.getInstance();
        DisplayRating(mAuth.getCurrentUser().getUid());
    }

    protected void setupLogoutButton(View view) {
        Button logoutButton = view.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(this);
    }

    void logout() {
        mAuth.signOut();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Log.d("clicked", "clicked");
        logout();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employee_profile, container, false);
        setupLogoutButton(view);
        ratingText = view.findViewById(R.id.ratingText);

        return view;
    }

    private void DisplayRating(String uid) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String existingText = ratingText.getText().toString();
                    if (dataSnapshot.hasChild("rating")) {
                        double rating = dataSnapshot.child("rating").getValue(Double.class);

                        DecimalFormat df = new DecimalFormat("#.#");

                        // Format the rating value to one decimal place
                        String formattedRating = df.format(rating);

                        // Concatenate the rating value with existing text
                        String newText = existingText + formattedRating + " ★";

                        // Set the concatenated text back to the TextView
                        ratingText.setText(newText);
                        // Do something with the numRatings and rating, like updating UI or storing in variables
                    } else {
                        ratingText.setText(existingText + "0.0" + " ★");
                    }
                }
                else {
                    //no else action
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled
            }
        });
    }
}