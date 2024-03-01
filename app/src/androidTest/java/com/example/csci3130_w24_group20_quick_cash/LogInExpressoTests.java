package com.example.csci3130_w24_group20_quick_cash;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LogInExpressoTests {

    public ActivityScenario<MainActivity> scenario;


    @Before
    public void setup() throws Exception {
        scenario = ActivityScenario.launch(MainActivity.class);
        scenario.onActivity(activity -> {
            activity.setupLoginButton();
            activity.setupSignUpButton();
            activity.setupForgotButton();
            activity.initializeDatabaseAccess();
        });

    }

    @Test
    public void checkIfEmailIsInvalid() {
        onView(withId(R.id.email)).perform(typeText("abc.123dal.ca"));
        onView(withId(R.id.password)).perform(typeText("111111111"));
        onView(withId(R.id.loginButton)).perform(click());
    }
    @Test
    public void checkIfPasswordIsInvalid() {
        onView(withId(R.id.email)).perform(typeText("abc.123@dal.ca"));
        onView(withId(R.id.password)).perform(typeText("1"));
        onView(withId(R.id.loginButton)).perform(click());
    }

}