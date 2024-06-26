package com.example.csci3130_w24_group20_quick_cash;

import static com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments.JobUploadFragment.FIREBASE_SERVER_KEY;
import static com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments.JobUploadFragment.PUSH_NOTIFICATION_ENDPOINT;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments.SendJobOfferFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApplicationDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplicationDetailFragment extends Fragment {

    private static final String ARG_APP_POSTING = "argAppPosting";

    String[] employerNameARR = new String[1];
    RequestQueue requestQueue;

    FirebaseAuth mAuth;

    Button offerButton;

    Button rejectButton;

    Button shortlistButton;
    private ApplicationPosting appPosting;

    public ApplicationDetailFragment() {
        // Required empty public constructor
    }

    public static ApplicationDetailFragment newInstance(ApplicationPosting appPosting) {
        ApplicationDetailFragment fragment = new ApplicationDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_APP_POSTING, appPosting);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuthSingleton.getInstance();
        initShortlistedNotif();
        if (getArguments() != null) {
            appPosting = (ApplicationPosting) getArguments().getSerializable(ARG_APP_POSTING);
            fetchEmployerName();
        }
    }

    private void updateEmployeeUI() {
        String currentUid = mAuth.getCurrentUser().getUid();
        if (currentUid.equals(appPosting.getApplicantUID())){
            offerButton.setVisibility(View.GONE);
            rejectButton.setVisibility(View.GONE);
            shortlistButton.setVisibility(View.GONE);
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

    protected void setupShortlistButton(View view) {
         shortlistButton = view.findViewById(R.id.shortlistButton);
        shortlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appPosting.setApplicationStatus("Shortlisted");
                createChatInstance();
            }
        });
    }

    protected void setupRejectButton(View view) {
         rejectButton = view.findViewById(R.id.rejectButton);
        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appPosting.setApplicationStatus("Rejected");
                Toast.makeText(getActivity(), "Applicant Rejected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void setupOfferButton(View view) {
        offerButton = view.findViewById(R.id.sendOfferButton);
        offerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSendJobOfferFragment();
            }
        });
    }

    private void navigateToSendJobOfferFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.baseEmployer, SendJobOfferFragment.newInstance(appPosting))
                .addToBackStack("fragment_application_detail")
                .commit();
    }


    private void initShortlistedNotif() {
        requestQueue = Volley.newRequestQueue(getActivity());
        FirebaseMessaging.getInstance().subscribeToTopic("SHORTLISTING");
    }

    private void createChatInstance() {

        String jobID = appPosting.getJobID();
        String employerUID = appPosting.getEmployerUID();
        String applicantUID = appPosting.getApplicantUID();
        String applicantName = appPosting.getApplicantName();
        String jobTitle = appPosting.getJobTitle();
        String employerName = employerNameARR[0];

        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference().child("Chats");
        Query query = chatRef.orderByChild("jobID").equalTo(jobID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean conversationExists = false;
                for (DataSnapshot chatSnapshot : dataSnapshot.getChildren()) {
                    ChatData chatData = chatSnapshot.getValue(ChatData.class);
                    if (chatData != null && chatData.getApplicantUID().equals(applicantUID)) {
                        conversationExists = true;
                        break;
                    }
                }
                if (!conversationExists) {
                    ChatData chatData = new ChatData(jobID, employerUID, applicantUID, applicantName, employerName, jobTitle);
                    DatabaseReference newChatRef = chatRef.push();
                    String chatID = newChatRef.getKey();
                    chatData.setChatID(chatID);
                    newChatRef.setValue(chatData);
                    sendShortlistNotification();
                    Toast.makeText(getActivity(), "Connected! Please Check Chats Section", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "You have Already Shortlisted this applicant", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //No OnCancelled activity
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_application_detail, container, false);

        TextView textJobTitle = view.findViewById(R.id.textJobTitle);
        TextView textApplicantName = view.findViewById(R.id.textApplicantName);
        TextView textApplicantEmail = view.findViewById(R.id.textApplicantEmail);
        TextView textApplicantAvailability = view.findViewById(R.id.textApplicantAvailability);
        TextView textApplicantCountry = view.findViewById(R.id.textApplicantCountry);
        TextView textApplicantCity = view.findViewById(R.id.textApplicantCity);
        TextView textApplicantAddress = view.findViewById(R.id.textApplicantAddress);
        TextView textApplicantEducation = view.findViewById(R.id.textApplicantEducation);
        TextView textApplicantExperience = view.findViewById(R.id.textApplicantExperience);
        TextView textApplicantOtherDetails = view.findViewById(R.id.textApplicantOtherDetails);
        TextView textApplicantDateApplied = view.findViewById(R.id.textApplicantDateApplied);
        TextView textApplicationStatus = view.findViewById(R.id.textApplicantStatus);

        LinearLayout filesLayout = view.findViewById(R.id.filesLayout);

        retrieveFileUrls(filesLayout);
        setupShortlistButton(view);
        setupOfferButton(view);
        setupRejectButton(view);

        updateEmployeeUI();

        if (appPosting != null) {
            textJobTitle.setText(appPosting.getJobTitle());
            textApplicantName.setText(appPosting.getApplicantName());
            textApplicantEmail.setText(appPosting.getApplicantEmail());
            textApplicantAvailability.setText(appPosting.getApplicantAvailability());
            textApplicantCountry.setText(appPosting.getApplicantCountry());
            textApplicantCity.setText(appPosting.getApplicantCity());
            textApplicantAddress.setText(appPosting.getApplicantAddress());
            textApplicantEducation.setText(appPosting.getApplicantEducation());
            textApplicantExperience.setText(appPosting.getApplicantExperience());
            textApplicantOtherDetails.setText(appPosting.getAppOtherDetails());
            textApplicantDateApplied.setText(appPosting.getDateReceived());
            textApplicationStatus.setText(appPosting.getApplicationStatus());
        }
        return view;
    }

    private void retrieveFileUrls(LinearLayout filesLayout) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        String path = "ApplicantFiles/" + appPosting.getJobID() + "/" + appPosting.getApplicantUID();

        StorageReference applicantFilesRef = storageRef.child(path);

        applicantFilesRef.listAll()
                .addOnSuccessListener(listResult -> {
                    for (StorageReference fileRef : listResult.getItems()) {
                        fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            displayFileLink(filesLayout, uri.toString());
                        }).addOnFailureListener(exception -> {
                        });
                    }
                })
                .addOnFailureListener(exception -> {
                });
    }

    private void displayFileLink(LinearLayout filesLayout, String fileUrl) {
        String[] fileName = {Uri.decode(Uri.parse(fileUrl).getLastPathSegment())};
        fileName[0] = fileName[0].substring(fileName[0].lastIndexOf('/') + 1);
        TextView fileTextView = new TextView(getContext());
        fileTextView.setText(fileName[0]);
        configTextViews(fileTextView);
        fileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(fileName[0], fileUrl);
            }
        });
        filesLayout.addView(fileTextView);
    }

    private void downloadFile(String fileName, String fileUrl) {
        if (fileUrl != null) {
            Uri downloadUri = Uri.parse(Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DOWNLOADS).toString() + "/" + fileName);

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl(fileUrl);

            storageRef.getFile(downloadUri).addOnSuccessListener(taskSnapshot -> {
                Toast.makeText(getContext(), "File downloaded successfully", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(exception -> {
                Toast.makeText(getContext(), "Failed to download file", Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(getContext(), "Invalid file URL", Toast.LENGTH_SHORT).show();
        }
    }


    private void configTextViews(TextView textView) {
        textView.setTextSize(18);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextColor(Color.BLUE);
        textView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        textView.setPadding(0, 0, 0, 20);
    }

    private void sendShortlistNotification() {
        try {
            JSONObject notificationBody = new JSONObject();

            notificationBody.put("title", "Shortlisted!");
            notificationBody.put("body", "You've been shortlisted for a job you've applied to!");

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
                        Toast.makeText(getActivity(),
                                "Shortlist Notification Sent",
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