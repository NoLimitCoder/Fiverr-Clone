package com.example.csci3130_w24_group20_quick_cash;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

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
public class ForgotPasswordExpressoTests {

    public ActivityScenario<ForgotPasswordActivity> scenario;

    @Before
    public void setup() {
        scenario = ActivityScenario.launch(ForgotPasswordActivity.class);
        scenario.onActivity(activity -> {
            activity.setupSendButton();
        });
    }

    @Test
    public void checkIfEmailIsInvalid() {
        onView(withId(R.id.emailEntry)).perform(typeText("abc.123dal.ca"));
        onView(withId(R.id.verifyButton)).perform(click());

    }

}