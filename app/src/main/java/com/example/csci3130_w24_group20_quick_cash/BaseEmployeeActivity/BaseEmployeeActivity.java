package com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments.EmployeeProfileFragment;
import com.example.csci3130_w24_group20_quick_cash.OnGoingJobListFragment;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments.JobDetailsFragment;
import com.example.csci3130_w24_group20_quick_cash.JobPosting;
import com.example.csci3130_w24_group20_quick_cash.R;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments.JobSearchFragment;
import com.example.csci3130_w24_group20_quick_cash.UserChatOverviewFragment;
import com.example.csci3130_w24_group20_quick_cash.ViewAppFragment;
import com.example.csci3130_w24_group20_quick_cash.databinding.ActivityBaseEmployeeBinding;

public class BaseEmployeeActivity extends AppCompatActivity{

    /**
     * Base activity for the employee interface.
     */
    ActivityBaseEmployeeBinding binding;


    /**
     * Called when the activity is created.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBaseEmployeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        switchFragment(new JobSearchFragment());

        binding.navLayout.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();
            if (itemId == R.id.searchJobs) {
                switchFragment(new JobSearchFragment());
            } else if (itemId == R.id.viewProfile) {
                switchFragment((new EmployeeProfileFragment()));
            } else if (itemId == R.id.employeeChats) {
                switchFragment(new UserChatOverviewFragment());
            } else if (itemId == R.id.currentJobs) {
                switchFragment(new OnGoingJobListFragment());
            } else if (itemId == R.id.viewEmployeeApplications){
                switchFragment(new ViewAppFragment());
            }
            return true;
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("jobDetails")) {
            JobPosting job = (JobPosting) intent.getSerializableExtra("jobDetails");

            // Create and display the JobDetailsFragment
            JobDetailsFragment jobDetailsFragment = JobDetailsFragment.newInstance(job);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.baseEmployee, jobDetailsFragment);
            transaction.addToBackStack("fragment_job_search").commit();
        }
    }

    /**
     * Dispatches touch events to the appropriate views.
     *
     * @param event The motion event.
     * @return True if the event was handled, false otherwise.
     */

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);
        if (view instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            if (event.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight()
                    || y < w.getTop() || y > w.getBottom()) ) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }
    /**
     * Switches the current fragment to the given fragment.
     *
     * @param fragment The fragment to switch to.
     */

    private void switchFragment(Fragment fragment){
        FragmentManager  fragManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragManager.beginTransaction();
        fragmentTransaction.replace(R.id.baseEmployee, fragment);
        fragmentTransaction.commit();
    }

}



