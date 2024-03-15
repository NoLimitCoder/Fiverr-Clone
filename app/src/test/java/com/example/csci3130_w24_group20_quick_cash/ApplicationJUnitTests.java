package com.example.csci3130_w24_group20_quick_cash;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
public class ApplicationJUnitTests {
    CredentialValidator validator;
    /**
     * Setup method to initialize objects before running tests.
     */
    @Before
    public void setup() {
        validator = new CredentialValidator();
    }

    @Test
    public void checkIfApplicationIsValid() {
        assertTrue(validator.isApplicationFilledOut("Alex Roy", "aa112233@dal.ca", "Full Time", "6299 South Street", "Canada",
                "Halifax", "Bachelors of Science at Dalhousie University", "McDonald's cashier 2020-2023", "Ready to get to work!"));
        assertFalse(validator.isApplicationFilledOut("Alex Roy", "aa112233@dal.ca", "", "6299 South Street", "Canada",
                "Halifax", "Bachelors of Science at Dalhousie University", "", "Ready to get to work!"));
    }
}
