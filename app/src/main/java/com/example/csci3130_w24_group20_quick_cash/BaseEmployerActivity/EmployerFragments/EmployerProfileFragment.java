package com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csci3130_w24_group20_quick_cash.FavoriteEmployeesAdapter;
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
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing the employer's profile.
 * This fragment displays information related to the employer's profile.
 */
public class EmployerProfileFragment extends Fragment implements View.OnClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private FavoriteEmployeesAdapter adapter;
    private List<String> favoriteEmployeesList;

    private FirebaseAuth mAuth;

    private DatabaseReference favoriteEmployeesRef;

    private TextView ratingText;

    public EmployerProfileFragment() {
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
    public static EmployerProfileFragment newInstance(String param1, String param2) {
        EmployerProfileFragment fragment = new EmployerProfileFragment();
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
        favoriteEmployeesRef = FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("favoriteEmployees");
        favoriteEmployeesList = new ArrayList<>();
        adapter = new FavoriteEmployeesAdapter(favoriteEmployeesList);
        fetchRating(mAuth.getCurrentUser().getUid());
    }

    /**
     * Sets up the logout button functionality by finding the button in the provided view and
     * attaching an OnClickListener to it.
     *
     * @param view The parent view containing the logout button.
     */
    protected void setupLogoutButton(View view) {
        Button logoutButton = view.findViewById(R.id.logoutButton); // Correct ID of the logout button
        logoutButton.setOnClickListener(this);
    }

    /**
     * Logs the current user out of the application by signing out from Firebase authentication
     * and redirecting to the MainActivity.
     */
    void logout() {
        mAuth.signOut();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     * Handles the onClick event for the logout button.
     * This method is invoked when the logout button is clicked.
     * It logs the click event and initiates the logout process.
     *
     * @param v The View object that was clicked, in this case, the logout button.
     */
    @Override
    public void onClick(View v) {
        Log.d("clicked", "clicked");
        logout();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_employer_profile, container, false);
        setupLogoutButton(view);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        ratingText = view.findViewById(R.id.ratingText);
        fetchFavoriteEmployees();
        return view;
    }

    private void fetchFavoriteEmployees() {
        favoriteEmployeesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favoriteEmployeesList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String employeeName = snapshot.getValue(String.class);
                    //String rating  = snapshot.getValue()
                    //get the employees rating as well ★
                    favoriteEmployeesList.add(employeeName);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled
            }
        });
    }

    private void fetchRating(String uid) {
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