package com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.csci3130_w24_group20_quick_cash.CredentialValidator;
import com.example.csci3130_w24_group20_quick_cash.FirebaseAuthSingleton;
import com.example.csci3130_w24_group20_quick_cash.FirebaseCRUD;
import com.example.csci3130_w24_group20_quick_cash.JobPosting;
import com.example.csci3130_w24_group20_quick_cash.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A fragment responsible for uploading job postings to the Firebase Realtime Database.
 * This fragment provides UI elements for entering job posting details and functionality
 * for uploading the job posting data to the database.
 */
public class JobUploadFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String FIREBASE_SERVER_KEY = "AAAAJULKPZc:APA91bH7AZ59ApuLLtTpHUiC4l3Mu5CoKerK7CD8UGqEQXj0RmUE5x0JCkm1nMh8FwBo5O3lBoF3KK7cOifd-9ZNyoks7R7jKXHi26qwgfFTDLMOUS2hdnJ9vbs-1WLoM4kNg-P71GRB";
    private static final String PUSH_NOTIFICATION_ENDPOINT = "https://fcm.googleapis.com/fcm/send";

    private EditText jobTitleEditText, jobSalaryEditText, jobTypeEditText, jobCountryEditText,
    jobCityEditText, jobAdressEditText, jobDescriptionEditText, jobOtherDetailsEditText;

    private DatabaseReference jobPostingReference;

    protected FirebaseAuth mAuth;

    FirebaseDatabase database = null;

    final String[] employerName = new String[1];
    private String employerUID;

    Button uploadButton;
    FirebaseCRUD crud = null;

    private RequestQueue requestQueue;

    public JobUploadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadFragment.
     */
    public static JobUploadFragment newInstance(String param1, String param2) {
        JobUploadFragment fragment = new JobUploadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Initializes database access by creating instances of FirebaseDatabase and FirebaseCRUD.
     */
    protected void initializeDatabaseAccess() {
        database = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_DB_URL));
        crud = new FirebaseCRUD(database);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDatabaseAccess();
        mAuth = FirebaseAuthSingleton.getInstance();
        init();
        if (userIsLoggedIn()) {
            subscribeToJobTopic();
        }
    }

    private boolean userIsLoggedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    private void subscribeToJobTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("jobs")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Successfully subscribed to topic 'jobs'");
                    } else {
                        Log.e(TAG, "Failed to subscribe to topic 'jobs'", task.getException());
                    }
                });
    }

    private void init(){
        requestQueue = Volley.newRequestQueue(getActivity());
        FirebaseMessaging.getInstance().subscribeToTopic("JOBS");
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
        View view = inflater.inflate(R.layout.fragment_job_upload, container, false);
        uploadButton = view.findViewById(R.id.uploadJobButton);
        jobTitleEditText = view.findViewById(R.id.performanceReview);
        jobSalaryEditText = view.findViewById(R.id.editTextEmail);
        jobTypeEditText = view.findViewById(R.id.editTextPassword);
        jobCountryEditText = view.findViewById(R.id.editTextNumber);
        jobCityEditText = view.findViewById(R.id.editTextJobCity);
        jobAdressEditText = view.findViewById(R.id.editTextJobAddress);
        jobDescriptionEditText = view.findViewById(R.id.editTextJobDescription);
        jobOtherDetailsEditText = view.findViewById(R.id.editTextJobOtherDetails);
        jobPostingReference = FirebaseDatabase.getInstance().getReference().child("JobPostings");

        uploadButton.setOnClickListener(this);

        return view;
    }

    /**
     * Uploads a job posting to the Firebase Realtime Database.
     * This method retrieves the job posting details from the input fields, validates them,
     * and then uploads the job posting if all required fields are filled out and the address is valid.
     * It displays appropriate toast messages to inform the user about the outcome of the upload process.
     */
    public void uploadJobPosting() {
        CredentialValidator credChecker = new CredentialValidator();

        String jobTitle = jobTitleEditText.getText().toString().trim();
        String jobSalary = jobSalaryEditText.getText().toString().trim();
        String jobType = jobTypeEditText.getText().toString().trim();
        String jobCountry = jobCountryEditText.getText().toString().trim();
        String jobCity = jobCityEditText.getText().toString().trim();
        String jobAddress = jobAdressEditText.getText().toString().trim();
        String jobDescription = jobDescriptionEditText.getText().toString().trim();
        String jobOtherDetails = jobOtherDetailsEditText.getText().toString().trim();

        employerUID = mAuth.getCurrentUser().getUid();
        fetchEmployerName(employerUID);

        JobPosting jobPosting = new JobPosting(employerName[0], employerUID, jobTitle, jobCountry, jobCity, jobAddress, jobSalary,
                jobDescription, jobType, jobOtherDetails);

        if (credChecker.isJobFilledOut(jobTitle, jobCountry, jobCity, jobAddress, jobSalary,
                jobDescription, jobType, jobOtherDetails)) {

            if (credChecker.isValidAddress(getContext(),jobCountry, jobCity, jobAddress)){
                jobPostingReference.child(employerUID).child(jobPosting.getJobID()).setValue(jobPosting);
                Toast.makeText(getContext(), "Job Posting Uploaded Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Address Is Not Valid", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Please Fill Out All The Fields", Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * Fetches the employer's name from the Firebase Realtime Database using their unique user ID.
     * This method retrieves the employer's name associated with the provided user ID and stores it
     * in the employerName array.
     *
     * @param employerUID The unique user ID of the employer whose name is to be fetched.
     */
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

    /**
     * Handles click events for the upload button.
     * This method is invoked when the upload button is clicked,
     * triggering the process of uploading a job posting.
     *
     * @param v The View object that was clicked, in this case, the upload button.
     */
    @Override
    public void onClick(View v) {
        uploadJobPosting();
        sendNotification();
    }

    private void sendNotification() {
        try {
            // Logging job title
            Log.d("Notification", "Job Title: " + jobTitleEditText.getText().toString().trim());
            Log.d("Notification", "Job Description: " + jobDescriptionEditText.getText().toString().trim());
            Log.d("Notification", "Job Location: " + jobCityEditText.getText().toString().trim());

            JSONObject notificationBody = new JSONObject();
            notificationBody.put("title", "New Job Created"); // Assuming "title" is the key for job title
            notificationBody.put("body", "New job postings in your area"); // Assuming "body" is the key for job description

            // Logging job description
            Log.d("Notification", "Job Title: " + jobTitleEditText.getText().toString().trim());
            Log.d("Notification", "Job Description: " + jobDescriptionEditText.getText().toString().trim());
            Log.d("Notification", "Job Location: " + jobCityEditText.getText().toString().trim());


            JSONObject dataBody = new JSONObject();
            dataBody.put("jobID", "");
            dataBody.put("jobLocation", jobCityEditText.getText().toString().trim());

            // Logging job location
            Log.d("Notification", "Job Location: " + jobCityEditText.getText().toString().trim());

            JSONObject pushnotiBody = new JSONObject();
            pushnotiBody.put("to", "/topics/jobs");
            pushnotiBody.put("notification", notificationBody);
            pushnotiBody.put("data", dataBody);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                    PUSH_NOTIFICATION_ENDPOINT,
                    pushnotiBody,
                    response -> {
                        Log.d("Notification", "Notification Sent");
                        Toast.makeText(getActivity(),
                                "Notification Sent",
                                Toast.LENGTH_SHORT).show();
                    },
                    error -> {
                        Log.e("Notification", "Notification Sending Failed: " + error.toString());
                        error.printStackTrace();
                    }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "key=" + FIREBASE_SERVER_KEY);
                    return headers;
                }
            };
            requestQueue.add(request);

        } catch (Exception e) {
            Log.e("Notification", "Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

}