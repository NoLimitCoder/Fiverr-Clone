package com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


/**
 * Activity class for viewing notifications.
 */

public class ViewNotificationActivity extends AppCompatActivity {
    private TextView title;
    private TextView body;
    private TextView jobID;
    private TextView jobLocation;

    /**
     * Called when the activity is created.
     *
     * @param savedInstance A Bundle containing the activity's previously saved state, if any.
     */

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }

}
