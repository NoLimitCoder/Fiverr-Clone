package com.example.csci3130_w24_group20_quick_cash;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.csci3130_w24_group20_quick_cash.BaseEmployerActivity.BaseEmployerActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BaseEmployeerActivityExpressoTests {

    public ActivityScenario<BaseEmployerActivity> scenario;

    @Before
    public void setup() throws Exception {
        scenario = ActivityScenario.launch(BaseEmployerActivity.class);
    }

    @Test
    public void testJobSearchFragmentIsDisplayedOnStart() {
        // Verify that the job search fragment is displayed by checking the visibility of its view
        Espresso.onView(withId(R.id.baseEmployer))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testSwitchToProfileFragment() {
        // Click on the profile navigation item
        Espresso.onView(ViewMatchers.withId(R.id.viewEmployerProfile)).perform(ViewActions.click());

        // Verify that the profile fragment is displayed by checking the visibility of its view
        Espresso.onView(withId(R.id.fragment_employer_profile))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testSwitchToSettingsFragment() {
        // Click on the settings navigation item
        Espresso.onView(ViewMatchers.withId(R.id.employerNavSettings)).perform(ViewActions.click());

        // Verify that the settings fragment is displayed by checking the visibility of its view
        Espresso.onView(withId(R.id.fragment_employer_settings))
                .check(matches(isDisplayed()));
    }
}