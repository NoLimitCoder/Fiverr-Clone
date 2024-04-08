package com.example.csci3130_w24_group20_quick_cash;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments.JobUploadFragment.FIREBASE_SERVER_KEY;
import static com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments.JobUploadFragment.PUSH_NOTIFICATION_ENDPOINT;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments.PaymentIntegrationFragment;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments.EmployeeRating;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments.EmployerRating;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments.SendJobOfferFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class OnGoingJobDetailsFragments extends Fragment implements View.OnClickListener {

    private JobOffer jobOffer;
    private Button completeJobButton;
    private Button acceptJobButton;
    private Button declineJobButton;
    private Button favoriteEmployeeButton;
    private Button makePaymentButton;
    private Button ratingButton;
    private TextView textJobTitle;
    private TextView textJobSalary;
    private TextView textJobStartDate;
    private TextView textJobEmployer;

    private TextView textJobOtherTerms;

    private TextView textJobEmployee;

    private TextView acceptanceStatus;

    private TextView completionStatus;

    RequestQueue requestQueue;
    private String UID;

    FirebaseAuth mAuth;

    public OnGoingJobDetailsFragments() {
        // Required empty public constructor
    }

    /**
     * Creates a new instance of OnGoingJobDetailsFragments with provided JobOffer.
     *
     * @param jobOffer The ongoing job offer to display details for.
     * @return A new instance of OnGoingJobDetailsFragments.
     */

    public static OnGoingJobDetailsFragments newInstance(JobOffer jobOffer) {
        OnGoingJobDetailsFragments fragment = new OnGoingJobDetailsFragments();
        Bundle args = new Bundle();
        args.putSerializable("jobOffer", jobOffer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHireddNotif();
        mAuth = FirebaseAuthSingleton.getInstance();
        if (getArguments() != null) {
            jobOffer = (JobOffer) getArguments().getSerializable("jobOffer");
        }
    }

    private void initHireddNotif() {
        requestQueue = Volley.newRequestQueue(getActivity());
        FirebaseMessaging.getInstance().subscribeToTopic("HIREDJOBS");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ongoing_job_details, container, false);

        acceptJobButton = rootView.findViewById(R.id.acceptJobButton);
        declineJobButton = rootView.findViewById(R.id.declineJobButton);
        completeJobButton = rootView.findViewById(R.id.completeJobButton);
        favoriteEmployeeButton = rootView.findViewById(R.id.favoriteEmployeeButton);
        makePaymentButton = rootView.findViewById(R.id.makePaymentButton);
        ratingButton = rootView.findViewById(R.id.ratingButton);


        textJobTitle = rootView.findViewById(R.id.textJobTitle);
        textJobSalary = rootView.findViewById(R.id.textJobSalary);
        textJobStartDate = rootView.findViewById(R.id.textJobStartDate);
        textJobEmployer = rootView.findViewById(R.id.textJobEmployer);
        textJobEmployee = rootView.findViewById(R.id.textJobEmployee);
        textJobOtherTerms = rootView.findViewById(R.id.textJobOtherTerms);
        acceptanceStatus = rootView.findViewById(R.id.textJobAcceptance);
        completionStatus = rootView.findViewById(R.id.textJobCompletion);

        acceptJobButton.setOnClickListener(this);
        declineJobButton.setOnClickListener(this);
        completeJobButton.setOnClickListener(this);
        favoriteEmployeeButton.setOnClickListener(this);
        makePaymentButton.setOnClickListener(this);
        ratingButton.setOnClickListener(this);

        updateUI();

        if (jobOffer != null){
            textJobTitle.setText(jobOffer.getJobTitle());
            textJobSalary.setText(jobOffer.getSalary());
            textJobStartDate.setText(jobOffer.getStartDate());
            textJobEmployer.setText(jobOffer.getEmployerName());
            textJobEmployee.setText(jobOffer.getEmployeeName());
            textJobOtherTerms.setText(jobOffer.getOtherTerms());
            acceptanceStatus.setText(jobOffer.getIsAccepted());
            completionStatus.setText(jobOffer.getIsComplete());
        }

        return rootView;
    }

    /**
     * Updates the UI based on the current state of the job offer and the user's role.
     * If the user is the employee associated with the job offer:
     * - Shows the complete job button if the offer is accepted.
     * - Hides the decline job button if the offer is accepted.
     * - Hides all buttons if the offer is completed or declined.
     * If the user is not the employee associated with the job offer:
     * - Hides all buttons except the favorite employee button.
     */

    private void updateUI() {
        acceptanceStatus.setText(jobOffer.getIsAccepted());
        completionStatus.setText(jobOffer.getIsComplete());
        String currentUID = mAuth.getCurrentUser().getUid();
        String employeeUID = jobOffer.getApplicantUID();
        if (currentUID.equals(employeeUID)) {
            ConstraintLayout.LayoutParams layoutParams =
                    (ConstraintLayout.LayoutParams) ratingButton.getLayoutParams();
            layoutParams.topToBottom = R.id.completeJobButton;
            if ("accepted".equals(jobOffer.getIsAccepted())) {
                completeJobButton.setVisibility(View.VISIBLE);
                declineJobButton.setVisibility(View.GONE);
                acceptJobButton.setVisibility(View.GONE);
            } else {
                completeJobButton.setVisibility(View.GONE);
            }
            if ("complete".equals(jobOffer.getIsComplete())){
                completeJobButton.setVisibility(View.GONE);
                acceptJobButton.setVisibility(View.GONE);
                declineJobButton.setVisibility(View.GONE);
                ratingButton.setVisibility(View.VISIBLE);
            }
            if ("declined".equals(jobOffer.getIsAccepted())){
                completeJobButton.setVisibility(View.GONE);
                acceptJobButton.setVisibility(View.GONE);
                declineJobButton.setVisibility(View.GONE);
            }
        } else {
            completeJobButton.setVisibility(View.GONE);
            acceptJobButton.setVisibility(View.GONE);
            declineJobButton.setVisibility(View.GONE);
            favoriteEmployeeButton.setVisibility(View.VISIBLE);

            if ("complete".equals(jobOffer.getIsComplete())){
                makePaymentButton.setVisibility(View.VISIBLE);
                ratingButton.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.acceptJobButton) {
            jobOffer.setAccepted("accepted");
            sendAcceptedNotification();
            updateUI();
        } else if (v.getId() == R.id.declineJobButton) {
            jobOffer.setAccepted("declined");
            updateUI();
            Toast.makeText(getContext(), "Job offer declined", Toast.LENGTH_SHORT).show();
            DeleteJobOffer();
        } else if (v.getId() == R.id.completeJobButton) {
            jobOffer.setComplete("complete");
            updateUI();
        } else if (v.getId() == R.id.favoriteEmployeeButton){
            favoriteEmployee();
        } else if (v.getId() == R.id.makePaymentButton){
            openPaymentFragment();
        } else if(v.getId() == R.id.ratingButton) {
            navigateToRatingFragment();
        }
    }

    private void navigateToRatingFragment() {
        UID = mAuth.getCurrentUser().getUid();
        if(UID.equals(jobOffer.getApplicantUID())) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.baseEmployee, EmployeeRating.newInstance(jobOffer.getApplicantUID(), jobOffer.getEmployerUID()))
                    .addToBackStack("fragment_ongoing_job_details")
                    .commit();
        } else {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.baseEmployer, EmployerRating.newInstance(jobOffer.getApplicantUID(), UID))
                    .addToBackStack("fragment_ongoing_job_details")
                    .commit();
        }
    }

    private void openPaymentFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.baseEmployer, new PaymentIntegrationFragment())
                .addToBackStack("fragment_ongoing_job_details")
                .commit();
    }

    private void favoriteEmployee() {
        DatabaseReference employeesRef = FirebaseDatabase.getInstance().getReference().child("users").child(jobOffer.getApplicantUID());
        employeesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String employeeName = dataSnapshot.child("name").getValue(String.class);
                    String employeeUID = jobOffer.getApplicantUID();
                    DatabaseReference employerRef = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
                    employerRef.child("favoriteEmployees").child(employeeUID).setValue(employeeName)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(), "Employee added to favorites", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Failed to add employee to favorites", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(getContext(), "Employee data not found", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //No OnCancelled Activity
            }
        });
    }


    /**
     * Deletes the current job offer from the Firebase database.
     * Upon successful deletion, pops the back stack to return to the previous fragment.
     */

    private void DeleteJobOffer() {
        DatabaseReference jobOfferRef = FirebaseDatabase.getInstance().getReference().child("JobOffers").child(jobOffer.getJobID());
        jobOfferRef.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        requireActivity().getSupportFragmentManager().popBackStack();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error removing applicant from job offer", e);
                    }
                });
    }

    /**
     * Sends a notification to the subscribed topic "jobs" indicating that the job offer has been accepted.
     * Shows a toast message indicating success or failure.
     */


    private void sendAcceptedNotification() {
        try {
            JSONObject notificationBody = new JSONObject();

            notificationBody.put("title", "Accepted!");
            notificationBody.put("body", jobOffer.getEmployeeName() + "has been accepted the job: " + jobOffer.getJobTitle());

            JSONObject dataBody = new JSONObject();
            dataBody.put("Job name", jobOffer.getJobTitle());

            JSONObject pushnotiBody = new JSONObject();
            pushnotiBody.put("to", "/topics/jobs");
            pushnotiBody.put("notification", notificationBody);
            pushnotiBody.put("data", dataBody);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                    PUSH_NOTIFICATION_ENDPOINT,
                    pushnotiBody,
                    response -> {
                        Toast.makeText(getActivity(),
                                "Accepted Notification Sent",
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
    // fjaiodjfsa put the button
}
