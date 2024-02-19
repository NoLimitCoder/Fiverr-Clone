package com.example.csci3130_w24_group20_quick_cash;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class LogInJUnitTests {
    CredentialValidator validator;

    @Before
    public void setup() {
        validator = new CredentialValidator();
    }

    @Test
    public void checkIfEmailIsValid() {
        assertTrue(validator.isValidEmailAddress("ab123456@dal.ca"));
        assertFalse(validator.isValidEmailAddress("a.ca"));
        assertFalse(validator.isValidEmailAddress("jo.hndoeexample.com"));
    }

    @Test
    public void checkIfPasswordIsValid(){
        assertTrue(validator.isValidPassword("12345678"));
        assertFalse(validator.isValidPassword("2342"));

    }
}