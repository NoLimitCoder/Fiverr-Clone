/**
 * This class contains JUnit tests for the logout functionality.
 */
package com.example.csci3130_w24_group20_quick_cash;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class LogoutJUnitTests {
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
     * Test case to verify logout functionality.
     * A valid username being empty indicates successful logout.
     */
    @Test
    public void testLogout() {
        assertTrue(validator.isEmptyUserName(""));
    }
}
