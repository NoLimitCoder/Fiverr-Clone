package com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.csci3130_w24_group20_quick_cash.ApplicationPosting;
import com.example.csci3130_w24_group20_quick_cash.CredentialValidator;
import com.example.csci3130_w24_group20_quick_cash.FirebaseAuthSingleton;
import com.example.csci3130_w24_group20_quick_cash.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


/**
 * A fragment for applying to a job posting.
 */

public class JobApplyFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private ActivityResultLauncher<String> mGetContent;


    private String jobID;

    private Button applyButton, uploadFileButton;
    private EditText applyName, applyEmail, applyAvailability, applyAddress, applyCountry,
            applyCity, applyEducation, applyExperience, applyDetails;

    private DatabaseReference jobApplicationReference;
    private FirebaseAuth mAuth;

    private StorageReference storageRef;

    private String employeeUID;
    private String employerUID;

    private String jobTitle;

    public JobApplyFragment() {
        // Required empty public constructor
    }


    /**
     * Creates a new instance of JobApplyFragment.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of JobApplyFragment.
     */
    public static JobApplyFragment newInstance(String param1, String param2) {
        JobApplyFragment fragment = new JobApplyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Sets the job ID.
     *
     * @param jobID The job ID to set.
     */
    public void setJobID(String jobID) {
        this.jobID = jobID;
    }


    /**
     * Gets the employee ID.
     *
     * @return The employee ID.
     */

    public String getEmployeeID(){
        return employeeUID = mAuth.getCurrentUser().getUid();
    }

    public String getJobID(){
        return jobID;
    }

    /**
     * Sets the employer UID.
     *
     * @param employerUID The employer UID to set.
     */

    public void setEmployerUID(String employerUID) {
        this.employerUID = employerUID;
    }


    /**
     * Sets the job title.
     *
     * @param jobTitle The job title to set.
     */


    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * Sets up the upload file button.
     *
     * @param view The fragment view.
     */


    protected void setupUploadFileButton(View view) {
        uploadFileButton = view.findViewById(R.id.uploadFile);
        uploadFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("application/pdf");
            }
        });
    }

    /**
     * Sets up the apply button.
     *
     * @param view The fragment view.
     */

    protected void setupApplyButton(View view) {
        applyButton = view.findViewById(R.id.SubmitApplicationButton);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadJobApplication();
            }
        });
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuthSingleton.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            uploadFile(result);
                        }
                    }
                });
    }

    /**
     * Uploads the job application.
     */

    public void uploadJobApplication() {
        CredentialValidator credChecker = new CredentialValidator();

        String name = applyName.getText().toString().trim();
        String email = applyEmail.getText().toString().trim();
        String availability = applyAvailability.getText().toString().trim();
        String address = applyAddress.getText().toString().trim();
        String country = applyCountry.getText().toString().trim();
        String city = applyCity.getText().toString().trim();
        String education = applyEducation.getText().toString().trim();
        String experience = applyExperience.getText().toString().trim();
        String details = applyDetails.getText().toString().trim();

        employeeUID = getEmployeeID();

        if (credChecker.areFieldsFilled(name, employeeUID, email, availability, address, country,
                city, education, experience, details)) {
            if (credChecker.isValidEmailAddress(email) && credChecker.isValidName(name)) {
                if (credChecker.isValidAddress(getContext(), country, city, address)) {
                    uploadApplicationDetails(name, email, availability, address, country, city,
                            education, experience, details);
                } else {
                    Toast.makeText(getContext(), "Address Is Not Valid", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Email Or Name Is Not Valid", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(getContext(), "Please Fill Out All The Fields", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Uploads the application details.
     *
     * @param name        The name of the applicant.
     * @param email       The email of the applicant.
     * @param availability The availability of the applicant.
     * @param address     The address of the applicant.
     * @param country     The country of the applicant.
     * @param city        The city of the applicant.
     * @param education   The education of the applicant.
     * @param experience  The experience of the applicant.
     * @param details     The details of the applicant.
     */

    private void uploadApplicationDetails(String name, String email, String availability, String address,
                                          String country, String city, String education, String experience,
                                          String details) {
        ApplicationPosting appPosting = new ApplicationPosting(name, employeeUID, email, country,
                city, address, availability, education, experience, details);
        appPosting.setJobID(jobID);
        appPosting.setJobTitle(jobTitle);
        appPosting.setEmployerUID(employerUID);

        jobApplicationReference.child(appPosting.getJobID()).child(employeeUID).setValue(appPosting)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Job Application Uploaded Successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to upload job application", Toast.LENGTH_SHORT).show();
                });
    }

    private void uploadFile(Uri fileUri) {
        if (fileUri != null) {
            employeeUID = getEmployeeID();
            jobID = getJobID();
            String fileName = getFileName(fileUri);
            StorageReference applicantFiles = storageRef.child("ApplicantFiles").child(jobID)
                    .child(employeeUID).child(fileName);

            applicantFiles.putFile(fileUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        applicantFiles.getDownloadUrl().addOnSuccessListener(uri -> {
                            Toast.makeText(getContext(), "File Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Failed to upload file", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(getContext(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (columnIndex != -1) {
                        result = cursor.getString(columnIndex);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_apply, container, false);

        applyName = view.findViewById(R.id.editTextApplyFullName);
        applyEmail = view.findViewById(R.id.editTextApplyEmail);
        applyAvailability = view.findViewById(R.id.editTextAvailability);
        applyAddress = view.findViewById(R.id.editTextApplyAddress);
        applyCountry = view.findViewById(R.id.editTextApplyCountry);
        applyCity = view.findViewById(R.id.editTextApplyCity);
        applyEducation = view.findViewById(R.id.editTextEducation);
        applyExperience = view.findViewById(R.id.editTextJobExperience);
        applyDetails = view.findViewById(R.id.editTextApplyOtherDetails);
        jobApplicationReference = FirebaseDatabase.getInstance().getReference().child("JobApplications");

        setupUploadFileButton(view);
        setupApplyButton(view);
        return view;
    }
}
