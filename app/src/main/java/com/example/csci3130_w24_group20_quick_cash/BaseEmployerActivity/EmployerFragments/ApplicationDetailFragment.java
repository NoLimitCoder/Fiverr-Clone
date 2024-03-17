package com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csci3130_w24_group20_quick_cash.ApplicationPosting;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments.JobApplyFragment;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments.JobDetailsFragment;
import com.example.csci3130_w24_group20_quick_cash.JobPosting;
import com.example.csci3130_w24_group20_quick_cash.R;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApplicationDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplicationDetailFragment extends Fragment {

    private static final String ARG_APP_POSTING = "argAppPosting";
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
        if (getArguments() != null){
            appPosting = (ApplicationPosting) getArguments().getSerializable(ARG_APP_POSTING);
        }
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

        LinearLayout filesLayout = view.findViewById(R.id.filesLayout);

        retrieveFileUrls(filesLayout);

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


    private void configTextViews(TextView textView){
        textView.setTextSize(18);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextColor(Color.BLUE);
        textView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        textView.setPadding(0, 0, 0, 20);
    }

}