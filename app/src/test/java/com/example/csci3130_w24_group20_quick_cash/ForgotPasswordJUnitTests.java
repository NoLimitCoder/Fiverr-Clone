/**
 * This class contains JUnit tests for the forgot password functionality.
 */
package com.example.csci3130_w24_group20_quick_cash;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ForgotPasswordJUnitTests {
    // CredentialValidator instance for testing
    CredentialValidator validator;

    /**
     * Setup method to initialize objects before running tests.
     */
    @Before
    public void setup() {
        validator = new CredentialValidator();
    }

    /**
     * Test case to check if the email address is valid.
     * Validates whether the email address format is correct.
     */
    @Test
    public void checkIfApplicationIsValid() {
        assertTrue(validator.isApplicationFilledOut("abc123@dal.ca"));
        assertFalse(validator.isValidEmailAddress("a.ca"));
        assertFalse(validator.isValidEmailAddress("john.doeexample.com"));
    }
}
