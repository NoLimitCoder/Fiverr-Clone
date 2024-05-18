package com.example.csci3130_w24_group20_quick_cash;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.BaseEmployeeActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class BaseEmployeeActivityExpressoTests{

    public ActivityScenario<BaseEmployeeActivity> scenario;

    @Before
    public void setup() throws Exception {
        scenario = ActivityScenario.launch(BaseEmployeeActivity.class);
    }

    @Test
    public void testJobSearchFragmentIsDisplayedOnStart() {
        // Verify that the job search fragment is displayed by checking the visibility of its view
        Espresso.onView(withId(R.id.jobRecyclerView))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testSwitchToProfileFragment() {
        // Click on the profile navigation item
        Espresso.onView(ViewMatchers.withId(R.id.viewProfile)).perform(ViewActions.click());

        // Verify that the profile fragment is displayed by checking the visibility of its view
        Espresso.onView(withId(R.id.fragment_employer_settings))
                .check(matches(isDisplayed()));
    }

}