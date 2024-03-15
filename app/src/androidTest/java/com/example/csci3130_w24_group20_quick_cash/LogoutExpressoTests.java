package com.example.csci3130_w24_group20_quick_cash;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.BaseEmployeeActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LogoutExpressoTests {

    public ActivityScenario<BaseEmployeeActivity> scenario;

    @Before
    public void setup() {
        scenario = ActivityScenario.launch(BaseEmployeeActivity.class);
        scenario.onActivity(activity -> {
            //activity.setupLogoutButton();
        });
    }

    @Test
    public void checkIfUserIsLoggedOut() {
        Espresso.onView(ViewMatchers.withId(R.id.logoutButton)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.loginButton))
                .check(matches(isDisplayed()));
    }

}