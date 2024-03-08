package com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments.ProfileFragment;
import com.example.csci3130_w24_group20_quick_cash.R;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments.SearchFragment;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments.SettingsFragment;
import com.example.csci3130_w24_group20_quick_cash.databinding.ActivityBaseEmployeeBinding;

public class BaseEmployeeActivity extends AppCompatActivity{

    ActivityBaseEmployeeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBaseEmployeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        switchFragment(new SearchFragment());


        binding.navLayout.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();
            if (itemId == R.id.searchJobs) {
                switchFragment(new SearchFragment());
            } else if (itemId == R.id.viewProfile) {
                switchFragment((new ProfileFragment()));
            } else if (itemId == R.id.employeeSettings) {
                switchFragment(new SettingsFragment());
            }

            return true;
        });
    }

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

    private void switchFragment(Fragment fragment){
        FragmentManager  fragManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }


}


