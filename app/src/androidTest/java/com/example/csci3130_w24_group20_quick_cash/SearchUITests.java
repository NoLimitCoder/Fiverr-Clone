package com.example.csci3130_w24_group20_quick_cash;

import androidx.test.core.app.ActivityScenario;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertTrue;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

@RunWith(AndroidJUnit4.class)
public class SearchUITests {

    private UiDevice device;

    private FirebaseAuth mAuth;

    private FirebaseUser currentUser = null;

    @Before
    public void setUp() {

        if (currentUser == null) {
            mAuth = FirebaseAuthSingleton.getInstance();
            currentUser = mAuth.getCurrentUser();
            device = UiDevice.getInstance(getInstrumentation());
            ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
            onView(withId(R.id.email)).perform(typeText("am494441@dal.ca"));
            onView(withId(R.id.password)).perform(typeText("123456789"));
            onView(withId(R.id.loginButton)).perform(click());
        }
    }

    @Test
    public void filterForExistingJob() {
        UiObject2 searchBar = device.wait(Until.findObject(By.res("com.example.csci3130_w24_group20_quick_cash:id/editTextSearch")), 5000);
        assertTrue(searchBar != null && searchBar.isEnabled());

        searchBar.click();

        String searchText = "Engineer";
        searchBar.setText(searchText);

        assertTrue(device.hasObject(By.text(searchText)));
        mAuth.signOut();
    }

    @Test
    public void filterForNonExistingJob() {
        UiObject2 searchBar = device.wait(Until.findObject(By.res("com.example.csci3130_w24_group20_quick_cash:id/editTextSearch")), 5000);
        assertTrue(searchBar != null && searchBar.isEnabled());

        searchBar.click();

        String searchText = "fakers-on";
        searchBar.setText(searchText);

        assertTrue(device.hasObject(By.text(searchText)));
        mAuth.signOut();
    }
}



