package com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.csci3130_w24_group20_quick_cash.CredentialValidator;
import com.example.csci3130_w24_group20_quick_cash.FirebaseAuthSingleton;
import com.example.csci3130_w24_group20_quick_cash.FirebaseCRUD;
import com.example.csci3130_w24_group20_quick_cash.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * This fragment it used for an employer to rate their employees,
 * using a star system.
 */
public class EmployerRating extends Fragment implements View.OnClickListener{

    Button submitReview;
    RatingBar ratingBar;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String employerUID;
    private String employeeUID;
    private double userRating;
    private int numRatings;
    private DatabaseReference ratingReference;
    private DatabaseReference numRatingReference;

    protected FirebaseAuth mAuth;
    FirebaseDatabase database = null;

    FirebaseCRUD crud = null;



    public EmployerRating() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment employer_rating.
     */

    public static EmployerRating newInstance(String param1, String param2) {
        EmployerRating fragment = new EmployerRating();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Initializes necessary components including database access and Firebase authentication.
     * Retrieves user IDs from arguments and fetches ratings for the specified employee user.
     *
     * @param savedInstanceState Contains the data most recently supplied by onSaveInstanceState(),
     *                           or null if this is the first time.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDatabaseAccess();
        mAuth = FirebaseAuthSingleton.getInstance();
        employerUID = getArguments().getString(ARG_PARAM2);
        employeeUID = getArguments().getString(ARG_PARAM1);
        fetchUserRatings(employeeUID);
    }

    /**
     * Initializes access to the Firebase Database.
     *
     * This method sets up the Firebase Database instance using the provided URL
     * and initializes a FirebaseCRUD instance for performing CRUD operations.
     */
    protected void initializeDatabaseAccess() {
        database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_DB_URL));
        crud = new FirebaseCRUD(database);
    }


    /**
     * Called to create the view hierarchy associated with this fragment.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Returns the View for the fragment's UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_employer_rating, container, false);
        ratingBar = view.findViewById(R.id.employee_rating_bar);
        submitReview = view.findViewById(R.id.submitReviewButton);
        ratingReference = FirebaseDatabase.getInstance().getReference().child("users").child(employeeUID).child("rating");
        numRatingReference = FirebaseDatabase.getInstance().getReference().child("users").child(employeeUID).child("numRatings");
        submitReview.setOnClickListener(this);
        return view;
    }

    /**
     * Handles the onClick event for a view, triggering the upload of an employee review.
     *
     * This method is called when a view is clicked, and it initiates the process
     * of uploading a review for an employee.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        uploadEmployeeReview();
    }
    protected void uploadEmployeeReview(){
        double rating = getRatingNum();
        double newAve = (userRating * numRatings) + rating;
        newAve = newAve / (numRatings + 1);

        ratingReference.setValue(newAve);
        numRatings++;
        numRatingReference.setValue(numRatings);
        Toast.makeText(getContext(), "You rated: " + rating, Toast.LENGTH_SHORT).show();
        fetchUserRatings(employeeUID);
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    /**
     * Fetches the user ratings for the specified user ID from the Firebase Realtime Database.
     *
     * This method retrieves the number of ratings and the average rating for the specified user
     * from the Firebase Realtime Database using the provided user ID.
     *
     * @param uid The unique ID of the user for whom ratings are being fetched.
     */
    private void fetchUserRatings(String uid) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Check if numRatings and rating exist
                    if (dataSnapshot.hasChild("numRatings") && dataSnapshot.hasChild("rating")) {
                        numRatings = dataSnapshot.child("numRatings").getValue(Integer.class);
                        userRating = dataSnapshot.child("rating").getValue(Double.class);
                    } else {
                        numRatings = 0;
                        userRating = 0.0;

                        userRef.child("numRatings").setValue(numRatings);
                        userRef.child("rating").setValue(userRating);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }

    /**
     * Retrieves the number of stars selected by the user.
     * @return The stars selected by the user.
     */
    protected double getRatingNum() {
        return ratingBar.getRating();
    }
}