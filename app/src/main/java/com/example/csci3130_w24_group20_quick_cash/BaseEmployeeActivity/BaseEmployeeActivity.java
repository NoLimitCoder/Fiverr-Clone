package com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments.EmployeeProfileFragment;
import com.example.csci3130_w24_group20_quick_cash.R;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments.JobSearchFragment;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments.EmployeeSettingsFragment;
import com.example.csci3130_w24_group20_quick_cash.databinding.ActivityBaseEmployeeBinding;

public class BaseEmployeeActivity extends AppCompatActivity{

    ActivityBaseEmployeeBinding binding;

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
            } else if (itemId == R.id.employeeSettings) {
                switchFragment(new EmployeeSettingsFragment());
            }

            return true;
        });
    }

    private void switchFragment(Fragment fragment){
        FragmentManager  fragManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

}



