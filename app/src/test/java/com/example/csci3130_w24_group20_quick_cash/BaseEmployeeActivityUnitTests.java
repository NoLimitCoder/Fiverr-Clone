package com.example.csci3130_w24_group20_quick_cash;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class BaseEmployeeActivityUnitTests {
    CredentialValidator validator;

    @Before
    public void setup() {
        validator = new CredentialValidator();
    }

    @Test
    public void checkIfNameIsValid() {
        assertTrue(validator.isValidName("ammmar zain"));
        assertFalse(validator.isValidName(""));
    }

    @Test
    public void checkIfEmailIsValid() {
        assertTrue(validator.isValidEmailAddress("abc123@dal.ca"));
        assertFalse(validator.isValidEmailAddress("a.ca"));
        assertFalse(validator.isValidEmailAddress("john.doeexample.com"));
    }


    @Test
    public void checkIfRoleIsValid() {
        assertTrue(validator.isValidRole("Employer"));
        assertTrue(validator.isValidRole("Employee"));
        assertFalse(validator.isValidRole("Select your role"));
    }

    @Test
    public void checkIfPasswordIsValid(){
        assertTrue(validator.isValidPassword("12345678"));
        assertFalse(validator.isValidPassword("2342"));

    }

    @Test
    public void checkIfContactNumberIsValid(){
        assertFalse(validator.isValidContactNumber("45635"));
        assertTrue(validator.isValidContactNumber("4169035743"));
    }


}