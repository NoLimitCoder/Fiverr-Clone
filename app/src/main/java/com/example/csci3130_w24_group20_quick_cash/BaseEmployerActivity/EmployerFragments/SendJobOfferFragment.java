package com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments;

import static com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments.JobUploadFragment.FIREBASE_SERVER_KEY;
import static com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments.JobUploadFragment.PUSH_NOTIFICATION_ENDPOINT;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.csci3130_w24_group20_quick_cash.ApplicationPosting;
import com.example.csci3130_w24_group20_quick_cash.CredentialValidator;
import com.example.csci3130_w24_group20_quick_cash.JobOffer;
import com.example.csci3130_w24_group20_quick_cash.R;
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
 * A simple {@link Fragment} subclass.
 * Use the {@link SendJobOfferFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SendJobOfferFragment extends Fragment {

    private ApplicationPosting appPosting;

    private String []employerNameARR = new String[1];

    RequestQueue requestQueue;

    public static SendJobOfferFragment newInstance(ApplicationPosting appPosting) {
        SendJobOfferFragment fragment = new SendJobOfferFragment();
        Bundle args = new Bundle();
        args.putSerializable("appPosting", appPosting);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initOfferNotif();
        if (getArguments() != null) {
            appPosting = (ApplicationPosting) getArguments().getSerializable("appPosting");
            fetchEmployerName();
        }
    }

    private void fetchEmployerName() {
        DatabaseReference employerRef = FirebaseDatabase.getInstance().getReference("users").child(appPosting.getEmployerUID());
        employerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    employerNameARR[0] = snapshot.child("name").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // No Error Handling for OnCancelled
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_job_offer, container, false);
        EditText salaryEditText = view.findViewById(R.id.salary);
        EditText startDateEditText = view.findViewById(R.id.startDate);
        EditText otherTermsEditText = view.findViewById(R.id.otherTerms);
        Button sendButton = view.findViewById(R.id.SubmitApplicationButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String salary = salaryEditText.getText().toString();
                String startDate = startDateEditText.getText().toString();
                String otherTerms = otherTermsEditText.getText().toString();

                CredentialValidator cred = new CredentialValidator();
                if (!cred.areFieldsFilled(salary, startDate, otherTerms)) {
                    Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                JobOffer jobOffer = new JobOffer(appPosting, salary, startDate, otherTerms, employerNameARR[0]);

                uploadJobOffer(jobOffer);
            }
        });

        return view;
    }

    private void uploadJobOffer(JobOffer jobOffer) {
        DatabaseReference jobOffersRef = FirebaseDatabase.getInstance().getReference().child("JobOffers");
        String jobOfferId = jobOffersRef.push().getKey();

        if (jobOfferId != null) {
            jobOffer.setJobID(jobOfferId);
            jobOffersRef.child(jobOfferId).setValue(jobOffer)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), "Job offer sent successfully", Toast.LENGTH_SHORT).show();
                        appPosting.setApplicationStatus("Job Offer Sent");
                        sendJobOfferNotification();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Failed to send job offer", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void initOfferNotif() {
        requestQueue = Volley.newRequestQueue(getContext());
        FirebaseMessaging.getInstance().subscribeToTopic("JOBOFFER");
    }

    private void sendJobOfferNotification() {
        try {
            JSONObject notificationBody = new JSONObject();

            notificationBody.put("title", "Job Offer!");
            notificationBody.put("body", "An employer as sent you an offer!");

            JSONObject dataBody = new JSONObject();
            dataBody.put("Job name", appPosting.getJobTitle());

            JSONObject pushnotiBody = new JSONObject();
            pushnotiBody.put("to", "/topics/jobs");
            pushnotiBody.put("notification", notificationBody);
            pushnotiBody.put("data", dataBody);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                    PUSH_NOTIFICATION_ENDPOINT,
                    pushnotiBody,
                    response -> {
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
                getParentFragmentManager().popBackStack();
        } catch (Exception e) {
            Log.e("Notification", "Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
