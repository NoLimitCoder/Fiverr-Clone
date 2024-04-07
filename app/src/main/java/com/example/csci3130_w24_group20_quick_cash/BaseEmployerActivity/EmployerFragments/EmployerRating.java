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
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments.EmployeeRating;
import com.example.csci3130_w24_group20_quick_cash.CredentialValidator;
import com.example.csci3130_w24_group20_quick_cash.FirebaseAuthSingleton;
import com.example.csci3130_w24_group20_quick_cash.FirebaseCRUD;
import com.example.csci3130_w24_group20_quick_cash.R;
import com.example.csci3130_w24_group20_quick_cash.Review;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployerRating#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployerRating extends Fragment implements View.OnClickListener{

    Button submitReview;
    RatingBar ratingBar;
    EditText performanceReview;
    private static final String FIREBASE_SERVER_KEY = "AAAAJULKPZc:APA91bH7AZ59ApuLLtTpHUiC4l3Mu5CoKerK7CD8UGqEQXj0RmUE5x0JCkm1nMh8FwBo5O3lBoF3KK7cOifd-9ZNyoks7R7jKXHi26qwgfFTDLMOUS2hdnJ9vbs-1WLoM4kNg-P71GRB";
    private static final String PUSH_NOTIFICATION_ENDPOINT = "https://fcm.googleapis.com/fcm/send";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String employerUID;
    private String employeeUID;
    private double userRating;
    private double numRatings;
    private DatabaseReference ratingReference;
    private DatabaseReference numRatingReference;


    private DatabaseReference reviewPostingReference;

    protected FirebaseAuth mAuth;
    FirebaseDatabase database = null;

    final String[] employerName = new String[1];


    FirebaseCRUD crud = null;

    private RequestQueue requestQueue;


    public EmployerRating() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment employer_rating.
     */
    // TODO: Rename and change types and number of parameters

    public static EmployerRating newInstance(String param1, String param2) {
        EmployerRating fragment = new EmployerRating();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDatabaseAccess();
        mAuth = FirebaseAuthSingleton.getInstance();
        employerUID = getArguments().getString(ARG_PARAM2);
        employeeUID = getArguments().getString(ARG_PARAM1);
        fetchUserRatings(employeeUID);
    }

    protected void initializeDatabaseAccess() {
        database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_DB_URL));
        crud = new FirebaseCRUD(database);
    }



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


    @Override
    public void onClick(View v) {
        uploadEmployeeReview();
    }
    protected void uploadEmployeeReview(){
        CredentialValidator credChecker = new CredentialValidator();
        double rating = getRatingNum();
        //get the num of ratings and rating

        double newAve = (userRating * numRatings) + rating;
        newAve = newAve / (numRatings + 1);

        ratingReference.setValue(newAve);
        numRatingReference.setValue(numRatings + 1);
        Toast.makeText(getContext(), "You rated: " + rating, Toast.LENGTH_SHORT).show();
        //create
        return;
    }

    private void fetchUserRatings(String uid) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    numRatings = dataSnapshot.child("numRatings").getValue(Double.class);
                    userRating = dataSnapshot.child("rating").getValue(Double.class);

                } else {
                    return;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }



    /**
     * Retrieves the role selected by the user.
     * @return The role selected by the user.
     */
    protected double getRatingNum() {
        return ratingBar.getRating();
    }
}