package com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments.EmployerViewAppFragment;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments.JobUploadFragment;
import com.example.csci3130_w24_group20_quick_cash.R;

import com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments.EmployerProfileFragment;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.EmployerFragments.EmployerSettingsFragment;
import com.example.csci3130_w24_group20_quick_cash.databinding.ActivityBaseEmployerBinding;


public class BaseEmployerActivity extends AppCompatActivity  {
    public ActivityBaseEmployerBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBaseEmployerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // Use correct layout file here
        switchFragment(new JobUploadFragment());

        binding.navLayout.setOnItemSelectedListener( item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.uploadJobs){
                switchFragment(new JobUploadFragment());
            } else if (itemId == R.id.viewEmployerProfile){
                switchFragment(new EmployerProfileFragment());
            } else if (itemId == R.id.employerNavSettings){
                switchFragment(new EmployerSettingsFragment());
            } else if (itemId == R.id.viewEmployeeApplications){
                switchFragment(new EmployerViewAppFragment());
            }

            return true;
        });
    }

    /**
     * Switches the current fragment with the provided fragment.
     *
     * @param fragment The fragment to be displayed.
     */
    public void switchFragment(Fragment fragment){
        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragManager.beginTransaction();
        fragmentTransaction.replace(R.id.baseEmployer, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Overrides the dispatchTouchEvent method to handle touch events within the activity.
     *  *
     *  * @param event The MotionEvent object representing the touch event.
     *  * @return Returns true if the event was handled successfully, otherwise returns false.
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


}



