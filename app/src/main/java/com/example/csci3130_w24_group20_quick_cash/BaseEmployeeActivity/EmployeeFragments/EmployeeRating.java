package com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments;

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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
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
 * Use the {@link EmployeeRating#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeRating extends Fragment implements View.OnClickListener{

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

    private DatabaseReference reviewPostingReference;

    protected FirebaseAuth mAuth;


    FirebaseDatabase database = null;

    final String[] employerName = new String[1];
    private String employerUID;

    FirebaseCRUD crud = null;

    private RequestQueue requestQueue;


    public EmployeeRating() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment employer_rating.
     */
    // TODO: Rename and change types and number of parameters
    public static EmployeeRating newInstance(String param1, String param2) {
        EmployeeRating fragment = new EmployeeRating();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    protected void initializeDatabaseAccess() {
        database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_DB_URL));
        crud = new FirebaseCRUD(database);
    }
    private void init(){
        requestQueue = Volley.newRequestQueue(getActivity());
        FirebaseMessaging.getInstance().subscribeToTopic("JOBS");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDatabaseAccess();
        mAuth = FirebaseAuthSingleton.getInstance();
        init();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_employer_rating, container, false);
        ratingBar = view.findViewById(R.id.employee_rating_bar);
        submitReview = view.findViewById(R.id.submitReviewButton);
        performanceReview = view.findViewById(R.id.performanceReview);
        reviewPostingReference = FirebaseDatabase.getInstance().getReference().child("JobPostings");

        submitReview.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        uploadEmployeeReview();


    }
    protected void uploadEmployeeReview(){
        CredentialValidator credChecker = new CredentialValidator();

        String reviewDescription = getPerformanceReview();
        double rating = getRatingNum();
        employerUID = mAuth.getCurrentUser().getUid();
        fetchEmployerName(employerUID);
        Review review = new Review(employerName[0], employerUID, reviewDescription, rating);
        reviewPostingReference.child(employerUID).child(review.getreviewID()).setValue(review);

        //create
        return;
    }

    public void fetchEmployerName(String employerUID){

        DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("users").child(employerUID);
        dbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    employerName[0] = snapshot.child("name").getValue(String.class);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //no onCancelled logic
            }
        });

    }

    protected String getPerformanceReview(){
        return performanceReview.getText().toString().trim();
    }

    /**
     * Retrieves the role selected by the user.
     * @return The role selected by the user.
     */
    protected double getRatingNum() {
        return ratingBar.getRating();
    }
}
