package com.example.csci3130_w24_group20_quick_cash;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RegExpressoTests {

    public ActivityScenario<RegistrationActivity> scenario;

    @Before
    public void setup() {
        scenario = ActivityScenario.launch(RegistrationActivity.class);
        scenario.onActivity(activity -> {
            activity.loadRoleSpinner();
            activity.setupRegistrationButton();
            activity.initializeDatabaseAccess();
        });
    }


    @Test
    public void checkIfNameIsInvalid() {
        onView(withId(R.id.performanceReview)).perform(typeText(""));
        onView(withId(R.id.editTextEmail)).perform(typeText("abc.123@dal.ca"));
        onView(withId(R.id.editTextPassword)).perform(typeText("111111111"));
        onView(withId(R.id.editTextNumber)).perform(typeText("4169098983"));
        onView(withId(R.id.roleSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Employee"))).perform(click());
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.INVALID_NAME)));
    }

    @Test
    public void checkIfNameIsValid() {
        onView(withId(R.id.performanceReview)).perform(typeText("Ammar Za"));
        onView(withId(R.id.editTextEmail)).perform(typeText("abc.123@dal.ca"));
        onView(withId(R.id.editTextPassword)).perform(typeText("111111111"));
        onView(withId(R.id.editTextNumber)).perform(typeText("4169098983"));
        onView(withId(R.id.roleSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Employee"))).perform(click());
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText("")));
    }


    @Test
    public void checkIfEmailIsInvalid() {
        onView(withId(R.id.performanceReview)).perform(typeText("Ammar Za"));
        onView(withId(R.id.editTextEmail)).perform(typeText("abc.123dal.ca"));
        onView(withId(R.id.editTextPassword)).perform(typeText("111111111"));
        onView(withId(R.id.editTextNumber)).perform(typeText("4169098983"));
        onView(withId(R.id.roleSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Employee"))).perform(click());
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.INVALID_EMAIL_ADDRESS)));
    }


    @Test
    public void checkIfPasswordIsInvalid() {
        onView(withId(R.id.performanceReview)).perform(typeText("Ammar Za"));
        onView(withId(R.id.editTextEmail)).perform(typeText("abc.123@dal.ca"));
        onView(withId(R.id.editTextPassword)).perform(typeText("1"));
        onView(withId(R.id.editTextNumber)).perform(typeText("4169098983"));
        onView(withId(R.id.roleSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Employee"))).perform(click());
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.INVALID_PASSWORD)));
    }

    @Test
    public void checkIfContactNumberIsValid() {
        onView(withId(R.id.performanceReview)).perform(typeText("Ammar Za"));
        onView(withId(R.id.editTextEmail)).perform(typeText("abc.123@dal.ca"));
        onView(withId(R.id.editTextPassword)).perform(typeText("1111111111"));
        onView(withId(R.id.editTextNumber)).perform(typeText("98983"));
        onView(withId(R.id.roleSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Employee"))).perform(click());
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.INVALID_NUMBER)));
    }


    @Test
    public void checkIfRoleIsValid() {
        onView(withId(R.id.performanceReview)).perform(typeText("Ammar Za"));
        onView(withId(R.id.editTextEmail)).perform(typeText("abc.123@dal.ca"));
        onView(withId(R.id.editTextPassword)).perform(typeText("111111111"));
        onView(withId(R.id.editTextNumber)).perform(typeText("4169098983"));
        onView(withId(R.id.roleSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Employee"))).perform(click());
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText("")));
    }


    @Test
    public void checkIfRoleIsInvalid() {
        onView(withId(R.id.performanceReview)).perform(typeText("Ammar Za"));
        onView(withId(R.id.editTextEmail)).perform(typeText("abc.123@dal.ca"));
        onView(withId(R.id.editTextPassword)).perform(typeText("111111111"));
        onView(withId(R.id.editTextNumber)).perform(typeText("4169098983"));
        onView(withId(R.id.roleSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Select your role"))).perform(click());
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.INVALID_ROLE)));
    }

}
