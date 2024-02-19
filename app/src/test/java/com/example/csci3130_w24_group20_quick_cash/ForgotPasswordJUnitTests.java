package com.example.csci3130_w24_group20_quick_cash;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class ForgotPasswordJUnitTests {
    CredentialValidator validator;

    @Before
    public void setup() {
        validator = new CredentialValidator();
    }

    @Test
    public void checkIfEmailIsValid() {
        assertTrue(validator.isValidEmailAddress("abc123@dal.ca"));
        assertFalse(validator.isValidEmailAddress("a.ca"));
        assertFalse(validator.isValidEmailAddress("john.doeexample.com"));
    }

}