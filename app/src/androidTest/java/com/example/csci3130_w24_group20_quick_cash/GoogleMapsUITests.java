package com.example.csci3130_w24_group20_quick_cash;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class GoogleMapsUITests {

    List<JobPosting> mockJobPostings;
    public String launcherPackage = "ca.dal.cs.csci3130.w24_group20_quick_cash";
    public int TIME_OUT = 5000;
    UiDevice device;

    @Before
    public void setup() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent launcherIntent = new Intent(context, MapsActivity.class);
        mockJobPostings = createMockJobPostings();
        launcherIntent.putExtra("jobPostings", (Serializable) mockJobPostings);
        launcherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Add the flag here
        context.startActivity(launcherIntent);
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), TIME_OUT);
    }

    private List<JobPosting> createMockJobPostings() {
        mockJobPostings = new ArrayList<>();
        mockJobPostings.add(new JobPosting("Employer 1", "ID1", "Job Title", "Canada", "Halifax", "6059 Shirley Street", "$1000", "Description 1", "Type 1", "Other details 1"));
        mockJobPostings.add(new JobPosting("Employer 2", "ID2", "Job Title", "Canada", "Halifax", "6052 Shirley Street", "$2000", "Description 2", "Type 2", "Other details 2"));
        return mockJobPostings;
    }

    @Test
    public void testMarkersDisplayedCorrectly() throws UiObjectNotFoundException {
        device.wait(Until.hasObject(By.res("com.google.android.gms:id/map")), TIME_OUT);
        for (JobPosting job : mockJobPostings) {
            UiObject posting = device.findObject(new UiSelector().descriptionContains("Job Title"));
            posting.click();
        }
    }


}